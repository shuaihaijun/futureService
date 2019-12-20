package com.future.service.account;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.toolkit.MapUtils;
import com.future.common.constants.CommonConstant;
import com.future.common.constants.RedisConstant;
import com.future.common.constants.UserConstant;
import com.future.common.enums.GlobalResultCode;
import com.future.common.exception.BusinessException;
import com.future.common.exception.DataConflictException;
import com.future.common.exception.ParameterInvalidException;
import com.future.common.util.ConvertUtil;
import com.future.common.util.RedisManager;
import com.future.common.util.StringUtils;
import com.future.entity.account.FuAccountMt;
import com.future.entity.product.FuProductSignal;
import com.future.entity.user.FuUser;
import com.future.mapper.account.FuAccountMtMapper;
import com.future.mapper.product.FuProductSignalMapper;
import com.future.mapper.user.FuUserMapper;
import com.future.pojo.bo.order.UserMTAccountBO;
import com.future.service.mt.MTAccountService;
import com.jfx.AccountInfo;
import com.jfx.Broker;
import com.jfx.strategy.OrderInfo;
import com.jfx.strategy.Strategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
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
    @Autowired
    RedisManager redisManager;
    @Value("pubUserServerUrl1")
    String pubUserServerUrl1;
    @Value("pubUserServerUrl2")
    String pubUserServerUrl2;
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
        /*查询账户值*/
        List<UserMTAccountBO> accounts= fuAccountMtMapper.selectUserMTAccByCondition(condition);

        String userAccout="";
        Integer accountState=0;
        /*循环设置状态*/
        for(int i=0;i<accounts.size();i++){
            userAccout=accounts.get(i).getServerName()+"&"+accounts.get(i).getMtAccId();
            accountState=(Integer) redisManager.hget(RedisConstant.ACCOUNT_CONNECT_STATE,userAccout);
            accounts.get(i).setConnectState(accountState==null?0:accountState);
        }
        return accounts;
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
        password=accountMts.get(0).getMtPasswordTrade();
        if(accountMts.get(0).getUserType()== UserConstant.USER_TYPE_SIGNAL){
            //信号源用观摩密码验证
            password=accountMts.get(0).getMtPasswordWatch();
        }

        /*登录MT账号*/
        try {
            Strategy strategy=new Strategy();
            Broker broker = new Broker(mtServer);
            /*1 、连接数据*/
            if(!mtAccountService.getConnect(strategy,broker,mtAccId,password)){
                log.error("user connect failed");
                return false;
            }
            /*2 、监听初始化*/
            if(accountMts.get(0).getUserType()== UserConstant.USER_TYPE_SIGNAL){

            }

            /*3、修改缓存中的状态*/
            String stateKey=mtServer+"&"+mtAccId;
            redisManager.hset(RedisConstant.ACCOUNT_CONNECT_STATE,stateKey, CommonConstant.COMMON_YES);
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
        password=accountMts.get(0).getMtPasswordTrade();
        if(accountMts.get(0).getUserType()== UserConstant.USER_TYPE_SIGNAL){
            //信号源用观摩密码验证
            password=accountMts.get(0).getMtPasswordWatch();
        }

        /*登录MT账号*/
        try {
            Strategy strategy=new Strategy();
            Broker broker = new Broker(mtServer);
            /*连接数据*/
            if(!mtAccountService.disConnect(strategy,broker,mtAccId,password)){
                log.error("user disconnect failed");
                return false;
            }
            /*修改缓存中的状态*/
            String stateKey=mtServer+"&"+mtAccId;
            redisManager.hset(RedisConstant.ACCOUNT_CONNECT_STATE,stateKey, CommonConstant.COMMON_NO);
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
            /*修改缓存中的状态*/
            String stateKey=mtServer+"&"+mtAccId;
            redisManager.hset(RedisConstant.ACCOUNT_CONNECT_STATE,stateKey, CommonConstant.COMMON_YES);
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
            /*修改缓存中的状态*/
            String stateKey=mtServer+"&"+mtAccId;
            redisManager.hset(RedisConstant.ACCOUNT_CONNECT_STATE,stateKey, CommonConstant.COMMON_NO);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            throw new BusinessException(e);
        }
        return true;
    }

    /**
     * 审核用户MT账户（设置状态和端口）
     * @param userId
     */
    public void checkUserMtAccount(Integer userId){
        Map conditionMap=new HashMap();
        conditionMap.put(FuAccountMt.USER_ID,userId);
        List<FuAccountMt> accountMts=fuAccountMtMapper.selectByMap(conditionMap);
        for(FuAccountMt account:accountMts){
            if(account.getPasswordTradeChecked()>CommonConstant.COMMON_NO && account.getPasswordWatchChecked()>CommonConstant.COMMON_NO){
                //. 该账号已校验
                continue;
            }
            Integer accountPort=getUserAccountPort(account.getUserId(),account.getId());
            String accountUrl=getUserAccountUrl(accountPort);
            account.setPasswordTradeChecked(CommonConstant.COMMON_YES);
            account.setPasswordWatchChecked(CommonConstant.COMMON_YES);
            account.setAccountPort(accountPort);
            account.setAccountUrl(accountUrl);
            fuAccountMtMapper.updateByPrimaryKeySelective(account);
        }
    }

    /**
     * 审核用户MT账户（设置状态和端口）
     * @param userId
     */
    public void checkSignalMtAccount(Integer userId,String serverName,String mtAccId){
        Map conditionMap=new HashMap();
        conditionMap.put(FuAccountMt.USER_ID,userId);
        conditionMap.put(FuAccountMt.MT_ACC_ID,mtAccId);
        List<FuAccountMt> accountMts=fuAccountMtMapper.selectByMap(conditionMap);
        if(accountMts==null || accountMts.isEmpty()){
            log.error("未找到相关用户MT账户信息！");
            throw new BusinessException(GlobalResultCode.RESULE_DATA_NONE,"未找到相关用户MT账户信息！");
        }
        accountMts.get(0).setIsSignal(CommonConstant.COMMON_YES);
        if(accountMts.get(0).getPasswordWatchChecked()==CommonConstant.COMMON_NO
            ||accountMts.get(0).getPasswordWatchChecked()==CommonConstant.COMMON_NO){
            Integer accountPort=getUserAccountPort(accountMts.get(0).getUserId(),accountMts.get(0).getId());
            String accountUrl=getUserAccountUrl(accountPort);
            accountMts.get(0).setPasswordTradeChecked(CommonConstant.COMMON_YES);
            accountMts.get(0).setPasswordWatchChecked(CommonConstant.COMMON_YES);
            accountMts.get(0).setAccountPort(accountPort);
            accountMts.get(0).setAccountUrl(accountUrl);
        }
        fuAccountMtMapper.updateByPrimaryKeySelective(accountMts.get(0));
    }

    /**
     * 根据MT账户信息 更新
     * @param userId
     * @param mtAccId
     * @param accountInfo
     */
    public void updateAccFromMt(Integer userId,String mtAccId, AccountInfo accountInfo){
        if (userId==0
            ||StringUtils.isEmpty(mtAccId)
                ||ObjectUtils.isEmpty(accountInfo)){
            log.error("传入参数为空！");
            throw new RuntimeException("根据MT账户信息更新；传入参数为空！");
        }
        Map conditionMap=new HashMap();
        conditionMap.put(FuAccountMt.USER_ID,userId);
        conditionMap.put(FuAccountMt.MT_ACC_ID,mtAccId);
        List<FuAccountMt> accountMts= fuAccountMtMapper.selectByMap(conditionMap);
        if(accountMts==null||accountMts.size()==0){
            log.error("根据传入参数，查询结果为空！");
            throw new RuntimeException("根据MT账户信息更新；根据传入参数，查询结果为空！");
        }
        FuAccountMt accountMt=accountMts.get(0);
        accountMt.setBalance(new BigDecimal(accountInfo.getBalance()));
        accountMt.setLeverage(new BigDecimal(accountInfo.getLeverage()));
        accountMt.setCredit(new BigDecimal(accountInfo.getCredit()));
        accountMt.setProfit(new BigDecimal(accountInfo.getProfit()));
        accountMt.setEquity(new BigDecimal(accountInfo.getEquity()));
        accountMt.setMargin(new BigDecimal(accountInfo.getMargin()));
        fuAccountMtMapper.updateByPrimaryKey(accountMt);
    }



    /**
     * 根据端口号 判断初始服务地址
     * @param accountPort
     * @return
     */
    private String getUserAccountUrl(Integer accountPort){
        /*判断 服务器1是否满*/
        return pubUserServerUrl1;
    }

    /**
     * 根據用户ID 账户初始端口
     * @param userId
     * @return
     */
    private Integer getUserAccountPort(Integer userId,Integer accountId){
        return 10000+accountId;
    }


}
