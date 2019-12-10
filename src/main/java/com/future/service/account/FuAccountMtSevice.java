package com.future.service.account;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.toolkit.MapUtils;
import com.future.common.exception.BusinessException;
import com.future.common.exception.DataConflictException;
import com.future.common.exception.ParameterInvalidException;
import com.future.common.util.ConvertUtil;
import com.future.common.util.StringUtils;
import com.future.entity.account.FuAccountMt;
import com.future.entity.product.FuProductSignal;
import com.future.entity.user.FuUser;
import com.future.mapper.account.FuAccountMtMapper;
import com.future.mapper.product.FuProductSignalMapper;
import com.future.mapper.user.FuUserMapper;
import com.future.pojo.bo.order.UserMTAccountBO;
import com.future.service.mt.MTAccountService;
import com.jfx.Broker;
import com.jfx.strategy.OrderInfo;
import com.jfx.strategy.Strategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FuAccountMtSevice extends ServiceImpl<FuAccountMtMapper, FuAccountMt> {

    Logger log= LoggerFactory.getLogger(FuAccountMtSevice.class);

    @Autowired
    FuAccountMtMapper fuAccountMtMapper;
    @Autowired
    FuUserMapper fuUserMapper;
    @Autowired
    MTAccountService mtAccountService;
    @Autowired
    FuProductSignalMapper fuProductSignalMapper;
    /**
     * 根据条件查询用户MT账户信息
     * @param condition
     * @return
     */
    public List<UserMTAccountBO> getUserMTAccByCondition(Map condition){

        if(MapUtils.isEmpty(condition)){
            log.warn("根据条件查询用户MT账户信息，查询条件为空！");
            return null;
        }
        return fuAccountMtMapper.selectUserMTAccByCondition(condition);
    }

    /**
     * 查询用户MT账户列表
     * @param condition
     * @return
     */
    public List<UserMTAccountBO> queryUsersMtAccount(Map condition){
        return fuAccountMtMapper.selectUserMTAccByCondition(condition);
    }

    /**
     * 保存/绑定用户MT账户信息
     * @param jsonData
     */
    public void saveUserMTAccount(JSONObject jsonData){
        if(ObjectUtils.isEmpty(jsonData)){
            log.error("保存/绑定用户MT账户信息,传入参数为空！");
            throw new ParameterInvalidException("保存/绑定用户MT账户信息,传入参数为空！");
        }

        FuAccountMt fuAccountMt=JSONObject.toJavaObject(jsonData,FuAccountMt.class);

        log.info(jsonData.toJSONString());

        if(!ObjectUtils.isEmpty(jsonData.get("accountId"))){
            fuAccountMt.setId(jsonData.getInteger("accountId"));
        }

        /*校验必要参数*/
        if(fuAccountMt.getUserId()==null
                ||fuAccountMt.getUserId()==0){
            log.error("保存/绑定用户MT账户信息,用户信息为空！");
            throw new ParameterInvalidException("保存/绑定用户MT账户信息,用户信息为空！");
        }
        if(fuAccountMt.getServerId()==0
            ||StringUtils.isEmpty(fuAccountMt.getMtAccId())){
            log.error("保存/绑定用户MT账户信息,账户信息为空！");
            throw new ParameterInvalidException("保存/绑定用户MT账户信息,账户信息为空！");
        }
        if(StringUtils.isEmpty(fuAccountMt.getMtPasswordTrade())
            ||StringUtils.isEmpty(fuAccountMt.getMtPasswordWatch())){
            log.error("保存/绑定用户MT账户信息,密码信息为空！");
            throw new ParameterInvalidException("保存/绑定用户MT账户信息,密码信息为空！");
        }

        try {
            /*保存账户信息*/
            if(fuAccountMt.getId()>0){
                fuAccountMtMapper.updateByPrimaryKeySelective(fuAccountMt);
            }else {
                fuAccountMtMapper.insertSelective(fuAccountMt);
            }
        }catch (Exception e){
            log.error(e.getMessage(),e);
            throw new BusinessException(e.getMessage());
        }
    }

    /**
     * 修改用户MT账户信息
     * @param dataMap
     */
    public void modifyUserMTAccount(Map dataMap){
        if(ObjectUtils.isEmpty(dataMap)){
            log.error("修改用户MT账户信息,传入参数为空！");
        }
        FuAccountMt fuAccountMt=(FuAccountMt) ConvertUtil.MapToJavaBean((HashMap) dataMap,FuAccountMt.class);
        /*校验必要参数*/
        if(fuAccountMt.getId()==null||fuAccountMt.getId()==0
                ||fuAccountMt.getUserId()==null||fuAccountMt.getUserId()==0){
            log.error("保存/绑定用户MT账户信息,用户信息为空！");
            throw new ParameterInvalidException("保存/绑定用户MT账户信息,用户信息为空！");
        }

        try {
            fuAccountMtMapper.updateByPrimaryCondition(fuAccountMt);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            throw new BusinessException(e.getMessage());
        }
    }

    /**
     * 登录/连接MT账户
     * @param userId
     * @param mtAccId
     * @param username
     * @param serverName
     * @return
     */
    public Boolean connectUserMTAccount(Integer userId,String mtAccId,String username,String serverName){

        /*校验参数*/
        if(((userId ==null ||userId==0 )&& StringUtils.isEmpty(username)) ||StringUtils.isEmpty(mtAccId)){
            log.error("登录/连接MT账户,传入参数不完整！");
        }

        /*查出用户MT账户*/
        String mtServer="";
        String password="";

        Map condition=new HashMap();
        if(userId>0){
            condition.put("userId",userId);
        }
        if(!StringUtils.isEmpty(mtAccId)){
            condition.put("mtAccId",mtAccId);
        }
        if(!StringUtils.isEmpty(username)){
            condition.put("username",username);
        }
        if(!StringUtils.isEmpty(serverName)){
            condition.put("serverName",serverName);
        }
        List<UserMTAccountBO> accountMts=fuAccountMtMapper.selectUserMTAccByCondition(condition);
        if(accountMts==null || accountMts.size()==0){
            log.error("登录/连接MT账户,根据条件查询用户MT账户失败！");
            return false;
        }
        mtServer=accountMts.get(0).getServerName();

        /*判断是否为信号源*/
        FuUser fuUser=fuUserMapper.selectByPrimaryKey(userId);
        if(ObjectUtils.isEmpty(fuUser)){
            log.error("登录/连接MT账户,根据账户号查询用户信息为空！");
            return false;
        }
        if(fuUser.getUserType()==11){
            //信号源用观摩密码验证
            password=accountMts.get(0).getMtPasswordWatch();
        }
        password=accountMts.get(0).getMtPasswordTrade();

        /*登录MT账号*/
        try {
            Strategy strategy=new Strategy();
            Broker broker = new Broker(mtServer);
            /*连接数据*/
            if(!mtAccountService.getConnect(strategy,broker,mtAccId,password)){
                log.error("user connect failed");
                return false;
            }
        }catch (Exception e){
            log.error(e.getMessage(),e);
            throw new BusinessException(e);
        }
        return true;
    }

    /**
     * 断开连接-MT账户
     * @param userId
     * @param mtAccId
     * @param username
     * @param serverName
     * @return
     */
    public Boolean disConnectUserMTAccount(Integer userId,String mtAccId,String username,String serverName){

        /*校验参数*/
        if(((userId ==null ||userId==0 )&& StringUtils.isEmpty(username)) ||StringUtils.isEmpty(mtAccId)){
            log.error("登录/连接MT账户,传入参数不完整！");
        }

        /*查出用户MT账户*/
        String mtServer="";
        String password="";

        Map condition=new HashMap();
        if(userId>0){
            condition.put("userId",userId);
        }
        if(!StringUtils.isEmpty(mtAccId)){
            condition.put("mtAccId",mtAccId);
        }
        if(!StringUtils.isEmpty(username)){
            condition.put("username",username);
        }
        if(!StringUtils.isEmpty(serverName)){
            condition.put("serverName",serverName);
        }
        List<UserMTAccountBO> accountMts=fuAccountMtMapper.selectUserMTAccByCondition(condition);
        if(accountMts==null || accountMts.size()==0){
            log.error("登录/连接MT账户,根据条件查询用户MT账户失败！");
            return false;
        }
        mtServer=accountMts.get(0).getServerName();

        /*判断是否为信号源*/
        FuUser fuUser=fuUserMapper.selectByPrimaryKey(userId);
        if(ObjectUtils.isEmpty(fuUser)){
            log.error("登录/连接MT账户,根据账户号查询用户信息为空！");
            return false;
        }
        if(fuUser.getUserType()==11){
            //信号源用观摩密码验证
            password=accountMts.get(0).getMtPasswordWatch();
        }
        password=accountMts.get(0).getMtPasswordTrade();

        /*登录MT账号*/
        try {
            Strategy strategy=new Strategy();
            Broker broker = new Broker(mtServer);
            /*连接数据*/
            if(!mtAccountService.disConnect(strategy,broker,mtAccId,password)){
                log.error("user disconnect failed");
                return false;
            }
        }catch (Exception e){
            log.error(e.getMessage(),e);
            throw new BusinessException(e);
        }
        return true;
    }


    /**
     * 登录/连接信号源MT账户
     * @param signalId
     * @return
     */
    public Boolean connectSignalMTAccount(Integer signalId){

        /*校验参数*/
        if(signalId ==null ||signalId==0 ){
            log.error("登录/连接MT账户,传入参数不完整！");
        }

        /*查出用户MT账户*/
        String mtServer="";
        String password="";
        String mtAccId="";

        FuProductSignal signal=fuProductSignalMapper.selectByPrimaryKey(signalId);
        if(ObjectUtils.isEmpty(signal)){
            log.error("根据信号源ID 查询信号源信息失败！");
            throw new BusinessException("根据信号源ID 查询信号源信息失败！");
        }
        mtServer=signal.getServerName();
        password=signal.getMtPasswordWatch();
        mtAccId=signal.getMtAccId();
        /*登录MT账号*/
        try {
            Strategy strategy=new Strategy();
            Broker broker = new Broker(mtServer);
            /*连接数据*/
            if(!mtAccountService.getConnect(strategy,broker,mtAccId,password)){
                log.error("user connect failed");
                return false;
            }
        }catch (Exception e){
            log.error(e.getMessage(),e);
            throw new BusinessException(e);
        }
        return true;
    }

    /**
     * 断开信号源MT账户
     * @param signalId
     * @return
     */
    public Boolean disConnectSignalMTAccount(Integer signalId){
        /*校验参数*/
        if(signalId ==null ||signalId==0 ){
            log.error("登录/连接MT账户,传入参数不完整！");
        }
        /*查出用户MT账户*/
        String mtServer="";
        String password="";
        String mtAccId="";

        FuProductSignal signal=fuProductSignalMapper.selectByPrimaryKey(signalId);
        if(ObjectUtils.isEmpty(signal)){
            log.error("根据信号源ID 查询信号源信息失败！");
            throw new BusinessException("根据信号源ID 查询信号源信息失败！");
        }
        mtServer=signal.getServerName();
        password=signal.getMtPasswordWatch();
        mtAccId=signal.getMtAccId();
        /*登录MT账号*/
        try {
            Strategy strategy=new Strategy();
            Broker broker = new Broker(mtServer);
            /*连接数据*/
            if(!mtAccountService.disConnect(strategy,broker,mtAccId,password)){
                log.error("user connect failed");
                return false;
            }
        }catch (Exception e){
            log.error(e.getMessage(),e);
            throw new BusinessException(e);
        }
        return true;
    }
}
