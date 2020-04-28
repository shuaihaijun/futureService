package com.future.service.account;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.toolkit.MapUtils;
import com.future.common.constants.AccountConstant;
import com.future.common.constants.CommonConstant;
import com.future.common.constants.RedisConstant;
import com.future.common.constants.UserConstant;
import com.future.common.enums.GlobalResultCode;
import com.future.common.exception.BusinessException;
import com.future.common.exception.DataConflictException;
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
import com.future.pojo.bo.account.MtAccountInfoBo;
import com.future.pojo.bo.account.UserMTAccountBO;
import com.future.pojo.vo.signal.FuFollowStateVO;
import com.future.service.com.FuComServerService;
import com.future.service.trade.FuTradeAccountService;
import com.future.service.user.FuUserFollowsService;
import com.future.service.user.UserCommonService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
    FuComServerService fuComServerService;
    @Autowired
    UserCommonService userCommonService;
    @Autowired
    FuTradeAccountService fuTradeAccountService;
    @Autowired
    FuUserFollowsService fuUserFollowsService;
    @Autowired
    FuAccountMtMapper fuAccountMtMapper;
    @Autowired
    FuUserMapper fuUserMapper;
    @Autowired
    FuProductSignalMapper fuProductSignalMapper;
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
        List<UserMTAccountBO> accounts=fuAccountMtMapper.selectUserMTAccByCondition(condition);
        Object clientId;
        /*循环设置状态*/
        for(int i=0;i<accounts.size();i++){
            if(accounts.get(i).getMtAccId()==null){
                accounts.get(i).setConnectState(CommonConstant.COMMON_NO);
                continue;
            }
            clientId=redisManager.hget(RedisConstant.H_ACCOUNT_CONNECT_INFO,accounts.get(i).getMtAccId());
            if(!ObjectUtils.isEmpty(clientId)&&(Integer)clientId>0){
                accounts.get(i).setConnectState(CommonConstant.COMMON_YES);
            }else {
                accounts.get(i).setConnectState(CommonConstant.COMMON_NO);
            }
        }
        return accounts;
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

        String mtAccId="";
        Object clientId=0;
        /*循环设置状态*/
        for(int i=0;i<accountPage.getResult().size();i++){
            mtAccId=accountPage.getResult().get(i).getMtAccId();
            clientId=redisManager.hget(RedisConstant.H_ACCOUNT_CONNECT_INFO,mtAccId);
            if(!ObjectUtils.isEmpty(clientId)&&(Integer)clientId>0){
                accountPage.getResult().get(i).setConnectState(CommonConstant.COMMON_YES);
            }else {
                accountPage.getResult().get(i).setConnectState(CommonConstant.COMMON_NO);
            }
        }
        return accountPage;
    }

    /**
     * 保存/绑定用户MT账户信息
     * @param accMapList
     */
    @Transactional
    public void saveUserMTAccount(String userId, List<Map> accMapList){
        if(ObjectUtils.isEmpty(accMapList)){
            log.error("保存/绑定用户MT账户信息,传入参数为空！");
            throw new ParameterInvalidException("保存/绑定用户MT账户信息,传入参数为空！");
        }

        // 先查询出用户当前所有的账户
        List<FuAccountMt> accountMts= selectList(new EntityWrapper<FuAccountMt>().eq(FuAccountMt.USER_ID,userId));

        for (Map accMap:accMapList){
            Object accObject= ConvertUtil.MapToJavaBean((HashMap) accMap,FuAccountMt.class);
            FuAccountMt fuAccountMt=(FuAccountMt)accObject;

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
            if(mt!=null && mt.getUserId()!=null&& mt.getUserId().intValue()!=fuAccountMt.getUserId().intValue()){
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

            /*移除列表中 已经处理过的账户*/
            for(int i=0;i<accountMts.size();i++){
                if(accountMts.get(i).getMtAccId().equalsIgnoreCase(fuAccountMt.getMtAccId())){
                    accountMts.remove(i);
                }
            }
        }

        /*没有处理过的数据需要删除*/
        for(int i=0;i<accountMts.size();i++){
            deleteById(accountMts.get(i).getId());
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
        try {
            /*1/登录MT账号*/
            int clientId= fuTradeAccountService.setMtAccountConnect(mtServer, Integer.parseInt(mtAccId),password);
            if(clientId==0){
                log.error("user connect failed");
                return false;
            }
            /*2 、监听初始化?*/
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

        /*断开MT账号*/
        try {
            boolean isClose= fuTradeAccountService.setMtAccountDisConnnect(mtServer, Integer.parseInt(mtAccId),password);
            if(!isClose){
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

        try {
            /*1/登录MT账号*/
            int clientId= fuTradeAccountService.setMtSignalMonitor(mtServer, Integer.parseInt(mtAccId),password);
            if(clientId==0){
                log.error("user connect failed");
                return false;
            }
            /*2 、监听初始化?*/
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
        /*断开MT账号*/
        try {
            boolean isClose= fuTradeAccountService.setMtAccountDisConnnect(mtServer, Integer.parseInt(mtAccId),password);
            if(!isClose){
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
     */
    public void updateAccountInfoFromMt(Integer userId){
        if (userId==0){
            log.error("传入参数为空！");
            throw new RuntimeException("根据MT账户信息更新；传入参数为空！");
        }

        Wrapper<FuAccountMt> wrapper=new EntityWrapper<>();
        wrapper.eq(FuAccountMt.USER_ID,userId);
        List<FuAccountMt> accountMts=selectList(wrapper);

        for(FuAccountMt accountMt:accountMts){
            /*查询数据*/
           MtAccountInfoBo infoBo= fuTradeAccountService.getMtAccountInfo(accountMt.getServerName(),Integer.parseInt(accountMt.getMtAccId()),accountMt.getMtPasswordWatch());
           if(infoBo==null){
               continue;
           }
            accountMt.setBalance(new BigDecimal(infoBo.getBalance()).setScale(AccountConstant.BigDecimal_Scale,BigDecimal.ROUND_HALF_UP));
            accountMt.setLeverage(new BigDecimal(infoBo.getLeverage()).setScale(AccountConstant.BigDecimal_Scale,BigDecimal.ROUND_HALF_UP));
            accountMt.setCredit(new BigDecimal(infoBo.getCredit()).setScale(AccountConstant.BigDecimal_Scale,BigDecimal.ROUND_HALF_UP));
            accountMt.setProfit(new BigDecimal(infoBo.getProfit()).setScale(AccountConstant.BigDecimal_Scale,BigDecimal.ROUND_HALF_UP));
            accountMt.setEquity(new BigDecimal(infoBo.getEquity()).setScale(AccountConstant.BigDecimal_Scale,BigDecimal.ROUND_HALF_UP));
            accountMt.setMargin(new BigDecimal(infoBo.getMargin()).setScale(AccountConstant.BigDecimal_Scale,BigDecimal.ROUND_HALF_UP));
            fuAccountMtMapper.updateByPrimaryKey(accountMt);
        }
    }


    /**
     * 移除用户MT账户校验
     * @param userId
     * @param mtAccId
     * @return
     */
    public boolean mtAccRemoveCheck(Integer userId,Integer mtAccId){
        if(userId==null||mtAccId==null||userId==0||StringUtils.isEmpty(mtAccId)){
            log.error("移除用户MT账户校验失败，用户信息为空！");
            throw new DataConflictException(GlobalResultCode.PARAM_NULL_POINTER.message());
        }
        /*1、查询是否正在跟单*/
        Map conditionMap=new HashMap();
        conditionMap.put("userId",userId);
        conditionMap.put("userMtAccId",mtAccId);
        FuFollowStateVO userFollows= fuUserFollowsService.getSignalFollowByCondition(conditionMap);
        if(userFollows!=null&& userFollows.getUserId()!=null){
            log.error("移除用户MT账户校验失败，用户账户正在跟单 不能移除！");
            throw new BusinessException("移除用户MT账户校验失败，用户账户正在跟单 不能移除！");
        }

        /*2、查询是否是信号源账户*/
        conditionMap.clear();
        conditionMap.put("signalMtAccId",mtAccId);
        FuFollowStateVO signalFollows= fuUserFollowsService.getSignalFollowByCondition(conditionMap);
        if(signalFollows!=null&& signalFollows.getUserId()!=null){
            log.error("移除用户MT账户校验失败，信号源账户正在监听 不能移除！");
            throw new BusinessException("移除用户MT账户校验失败，信号源账户正在监听 不能移除！");
        }

        /*3.其他*/
        return true;
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
