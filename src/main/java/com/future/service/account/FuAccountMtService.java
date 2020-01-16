package com.future.service.account;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.toolkit.MapUtils;
import com.future.common.constants.CommonConstant;
import com.future.common.constants.RedisConstant;
import com.future.common.constants.UserConstant;
import com.future.common.enums.GlobalResultCode;
import com.future.common.exception.BusinessException;
import com.future.common.exception.ParameterInvalidException;
import com.future.common.helper.PageInfoHelper;
import com.future.common.util.ConvertUtil;
import com.future.common.util.RedisManager;
import com.future.common.util.StringUtils;
import com.future.entity.account.FuAccountMt;
import com.future.entity.com.FuComServer;
import com.future.entity.product.FuProductSignal;
import com.future.mapper.account.FuAccountMtMapper;
import com.future.mapper.product.FuProductSignalMapper;
import com.future.mapper.user.FuUserMapper;
import com.future.pojo.bo.order.UserMTAccountBO;
import com.future.service.com.FuComServerService;
import com.future.service.com.FuComService;
import com.future.service.mt.MTAccountService;
import com.future.service.user.UserCommonService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.jfx.AccountInfo;
import com.jfx.Broker;
import com.jfx.strategy.Strategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FuAccountMtService extends ServiceImpl<FuAccountMtMapper, FuAccountMt> {

    Logger log= LoggerFactory.getLogger(FuAccountMtService.class);

    @Autowired
    FuAccountMtMapper fuAccountMtMapper;
    @Autowired
    FuUserMapper fuUserMapper;
    @Autowired
    MTAccountService mtAccountService;
    @Autowired
    FuProductSignalMapper fuProductSignalMapper;
    @Autowired
    FuComServerService fuComServerService;
    @Autowired
    UserCommonService userCommonService;
    @Autowired
    RedisManager redisManager;
    @Value("${pubUserServerUrl1}")
    String pubUserServerUrl1;
    @Value("${pubUserServerUrl2}")
    String pubUserServerUrl2;
    @Value("${pubUserServerPort}")
    Integer pubUserServerPort;
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
    public Page<UserMTAccountBO> queryUsersMtAccount(Map condition, PageInfoHelper helper){

        /*判断查询条件*/
        if(condition == null||condition.get("operUserId")==null){
            log.error("查询用户列表,获取参数为空！");
            throw new ParameterInvalidException("查询用户列表,获取参数为空！");
        }
        /*判断权限*/
        String operUserId=String.valueOf(condition.get("operUserId"));
        if(com.alibaba.druid.util.StringUtils.isEmpty(operUserId)){
            log.error("查询用户列表,用户未登录！");
            throw new ParameterInvalidException("查询用户列表,获取参数为空！");
        }
        Integer operUserProj=userCommonService.getUserProjKey(Integer.parseInt(operUserId));
        Boolean isProjAdmin=userCommonService.isAdministrator(Integer.parseInt(operUserId),operUserProj);
        if(operUserProj==null){
            log.error("查询用户列表,用户权限有误！");
            throw new ParameterInvalidException("查询用户列表,用户权限有误！");
        }

        /*查询账户值*/
        List<UserMTAccountBO> accounts=new ArrayList<UserMTAccountBO>();
        if(helper==null){
            helper=new PageInfoHelper();
        }
        Page<UserMTAccountBO> accountPage=PageHelper.startPage(helper.getPageNo(),helper.getPageSize());
        if(isProjAdmin&&operUserProj==0){
            /*超管查询*/
            accounts= fuAccountMtMapper.selectUserMTAccByCondition(condition);
        }else if(isProjAdmin){
            /*资源组管理员查找*/
            condition.put("projKey",operUserProj);
            accounts= fuAccountMtMapper.selectUserMTAccByProjectCondition(condition);
        }else {
            /*普通用户查找*/
            condition.put("userId",operUserId);
            accounts= fuAccountMtMapper.selectUserMTAccByCondition(condition);
        }

        String userAccout="";
        Integer accountState=0;
        /*循环设置状态*/
        for(int i=0;i<accountPage.getResult().size();i++){
            userAccout=accountPage.getResult().get(i).getServerName()+"&"+accountPage.getResult().get(i).getMtAccId();
            accountState=(Integer) redisManager.hget(RedisConstant.H_ACCOUNT_CONNECT_STATE,userAccout);
            accountPage.getResult().get(i).setConnectState(accountState==null?0:accountState);
        }
        return accountPage;
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

        /*校验必要参数*/
        if(fuAccountMt.getUserId()==null
                ||fuAccountMt.getUserId()==0){
            log.error("保存/绑定用户MT账户信息,用户信息为空！");
            throw new ParameterInvalidException("保存/绑定用户MT账户信息,用户信息为空！");
        }
        if(fuAccountMt.getServerName()==null
            ||StringUtils.isEmpty(fuAccountMt.getMtAccId())){
            log.error("保存/绑定用户MT账户信息,账户信息为空！");
            throw new ParameterInvalidException("保存/绑定用户MT账户信息,账户信息为空！");
        }
        if(StringUtils.isEmpty(fuAccountMt.getMtPasswordTrade())
            ||StringUtils.isEmpty(fuAccountMt.getMtPasswordWatch())){
            log.error("保存/绑定用户MT账户信息,密码信息为空！");
            throw new ParameterInvalidException("保存/绑定用户MT账户信息,密码信息为空！");
        }

        /*根据serverName 查询server信息*/
        FuComServer server= fuComServerService.selectOne(new EntityWrapper<FuComServer>().eq(FuComServer.SERVER_NAME,fuAccountMt.getServerName()));
        if(server==null){
            log.error("保存/绑定用户MT账户信息,服务器信息查询失败！");
            throw new ParameterInvalidException("保存/绑定用户MT账户信息,服务器信息查询失败！");
        }
        fuAccountMt.setServerId(server.getId());
        fuAccountMt.setServerName(server.getServerName());
        fuAccountMt.setBrokerId(server.getBrokerId());
        fuAccountMt.setBrokerName(server.getBrokerName());

        FuAccountMt mt=selectOne(new EntityWrapper<FuAccountMt>().eq(FuAccountMt.MT_ACC_ID,fuAccountMt.getMtAccId()));
        if(mt!=null && mt.getUserId()!=null&& mt.getUserId()!=fuAccountMt.getUserId()){
            log.error("该账户已经被绑定，请重新选择账户！");
            throw new ParameterInvalidException("该账户已经被绑定，请重新选择账户！");
        }

        /*保存账户信息*/
        if(fuAccountMt.getId()!=null&&fuAccountMt.getId()>0){
            fuAccountMtMapper.updateByPrimaryKeySelective(fuAccountMt);
        }else if(mt!=null){
            fuAccountMt.setId(mt.getId());
            fuAccountMtMapper.updateByPrimaryKeySelective(fuAccountMt);
        }else {
            fuAccountMtMapper.insertSelective(fuAccountMt);
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
        String stateKey=mtServer+"&"+mtAccId;
        /*登录MT账号*/
        try {
            Strategy strategy=new Strategy();
            Broker broker = new Broker(mtServer);
            /*1 、连接数据*/
            if(!mtAccountService.getConnect(strategy,broker,mtAccId,password)){
                redisManager.hset(RedisConstant.H_ACCOUNT_CONNECT_STATE,stateKey, CommonConstant.COMMON_NO);
                log.error("user connect failed");
                return false;
            }
            /*2 、监听初始化*/
            if(accountMts.get(0).getUserType()== UserConstant.USER_TYPE_SIGNAL){

            }

            /*3、修改缓存中的状态*/
            redisManager.hset(RedisConstant.H_ACCOUNT_CONNECT_STATE,stateKey, CommonConstant.COMMON_YES);
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
            redisManager.hset(RedisConstant.H_ACCOUNT_CONNECT_STATE,stateKey, CommonConstant.COMMON_NO);
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
            redisManager.hset(RedisConstant.H_ACCOUNT_CONNECT_STATE,stateKey, CommonConstant.COMMON_YES);
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
            redisManager.hset(RedisConstant.H_ACCOUNT_CONNECT_STATE,stateKey, CommonConstant.COMMON_NO);
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
        return pubUserServerPort;
    }


}
