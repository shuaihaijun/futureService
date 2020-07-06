package com.future.service.user;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.toolkit.MapUtils;
import com.future.common.constants.*;
import com.future.common.enums.GlobalResultCode;
import com.future.common.enums.TradeErrorEnum;
import com.future.common.exception.BusinessException;
import com.future.common.exception.DataConflictException;
import com.future.common.exception.ParameterInvalidException;
import com.future.common.helper.PageInfoHelper;
import com.future.common.util.RedisManager;
import com.future.common.util.StringUtils;
import com.future.entity.product.FuProductSignal;
import com.future.entity.product.FuProductSignalPermit;
import com.future.entity.user.FuUserFollows;
import com.future.mapper.product.FuProductSignalMapper;
import com.future.mapper.user.FuUserFollowsMapper;
import com.future.pojo.bo.account.UserMTAccountBO;
import com.future.pojo.vo.signal.FuFollowStateVO;
import com.future.pojo.vo.signal.FuFollowUserVO;
import com.future.service.account.FuAccountMtService;
import com.future.service.product.FuProductSignalPermitService;
import com.future.service.product.FuProductSignalService;
import com.future.service.trade.FuTradeAccountService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.util.*;

@Service
public class FuUserFollowsService extends ServiceImpl<FuUserFollowsMapper, FuUserFollows> {

    Logger log= LoggerFactory.getLogger(FuUserFollowsService.class);
    @Autowired
    RedisManager redisManager;
    @Autowired
    FuProductSignalService fuProductSignalService;
    @Autowired
    FuProductSignalMapper fuProductSignalMapper;
    @Autowired
    FuAccountMtService fuAccountMtService;
    @Autowired
    UserCommonService userCommonService;
    @Autowired
    FuTradeAccountService fuTradeAccountService;
    @Autowired
    FuProductSignalPermitService fuProductSignalPermitService;
    @Autowired
    FuUserFollowsMapper fuUserFollowsMapper;

    /**
     * 查询跟随列表
     * @param conditionMap
     * @return
     */
    public PageInfo<FuFollowStateVO> querySignalFollowState(Map conditionMap, PageInfoHelper helper){

        Page<FuUserFollows> follows=querySignalFollows(conditionMap,helper);

        List<FuFollowStateVO> fuFollowStateVOS=new ArrayList<>();
        /*设置监听状态*/
        for(FuUserFollows follow:follows.getResult()){
            FuFollowStateVO followStateVO=new FuFollowStateVO();
            BeanUtils.copyProperties(follow,followStateVO);
            /*信号源状态*/
            if(redisManager.hget(RedisConstant.H_ACCOUNT_CONNECT_INFO,followStateVO.getSignalMtAccId())!=null){
                followStateVO.setSignalConnectState(CommonConstant.COMMON_YES);
            }else {
                followStateVO.setSignalConnectState(CommonConstant.COMMON_NO);
            }
            /*跟随账号状态*/
            if(redisManager.hget(RedisConstant.H_ACCOUNT_CONNECT_INFO,followStateVO.getUserMtAccId())!=null){
                followStateVO.setUserConnectState(CommonConstant.COMMON_YES);
            }else {
                followStateVO.setUserConnectState(CommonConstant.COMMON_NO);
            }
            fuFollowStateVOS.add(followStateVO);
        }
        PageInfo<FuFollowStateVO> stateVOPage=new PageInfo<>(fuFollowStateVOS);
        stateVOPage.setSize(follows.size());
        stateVOPage.setPageSize(follows.getPageSize());
        stateVOPage.setTotal(follows.getTotal());
        stateVOPage.setPages(follows.getPages());
        stateVOPage.setPageNum(follows.getPageNum());
        return stateVOPage;
    }

    /**
     * 查询跟随信息
     * @param conditionMap
     * @return
     */
    public FuFollowStateVO getSignalFollowByCondition(Map conditionMap){
        /*校验参数*/
        Map serchMap=new HashMap();
        if(!StringUtils.isEmpty(conditionMap.get("userId"))){
            serchMap.put(FuUserFollows.USER_ID,conditionMap.get("userId"));
        }
        if(!StringUtils.isEmpty(conditionMap.get("signalId"))){
            serchMap.put(FuUserFollows.SIGNAL_ID,conditionMap.get("signalId"));
        }
        if(!StringUtils.isEmpty(conditionMap.get("userMtAccId"))){
            serchMap.put(FuUserFollows.USER_MT_ACC_ID,conditionMap.get("userMtAccId"));
        }
        if(!StringUtils.isEmpty(conditionMap.get("signalMtAccId"))){
            serchMap.put(FuUserFollows.SIGNAL_MT_ACC_ID,conditionMap.get("signalMtAccId"));
        }
        if(!StringUtils.isEmpty(conditionMap.get("followState"))){
            serchMap.put(FuUserFollows.FOLLOW_STATE,conditionMap.get("followState"));
        }
        if(!StringUtils.isEmpty(conditionMap.get("id"))){
            serchMap.put("id",conditionMap.get("id"));
        }

        List<FuUserFollows> fuUserFollows = fuUserFollowsMapper.selectByMap(serchMap);
        if(fuUserFollows==null||fuUserFollows.size()==0){
            return null;
        }
        /*设置监听状态*/
        FuFollowStateVO followStateVO=new FuFollowStateVO();
        BeanUtils.copyProperties(fuUserFollows.get(0),followStateVO);
        /*信号源状态*/
        if(redisManager.hget(RedisConstant.H_ACCOUNT_CONNECT_INFO,followStateVO.getSignalMtAccId())!=null){
            followStateVO.setSignalConnectState(CommonConstant.COMMON_YES);
        }else {
            followStateVO.setSignalConnectState(CommonConstant.COMMON_NO);
        }
        /*跟随账号状态*/
        if(redisManager.hget(RedisConstant.H_ACCOUNT_CONNECT_INFO,followStateVO.getUserMtAccId())!=null){
            followStateVO.setUserConnectState(CommonConstant.COMMON_YES);
        }else {
            followStateVO.setUserConnectState(CommonConstant.COMMON_NO);
        }
        return followStateVO;
    }

    /**
     * 查询跟随列表
     * @param conditionMap
     * @param helper
     * @return
     */
    public Page<FuUserFollows> querySignalFollows(Map conditionMap,PageInfoHelper helper){
        /*校验参数*/
        Map serchMap=new HashMap();
        if(conditionMap == null||conditionMap.get("operUserId")==null){
            log.error("查询列表,获取参数为空！");
            throw new ParameterInvalidException("查询列表,获取参数为空！");
        }
        /*判断权限*/
        String operId=String.valueOf(conditionMap.get("operUserId"));
        if(StringUtils.isEmpty(operId)){
            log.error("查询列表,用户未登录！");
            throw new ParameterInvalidException("查询列表,获取参数为空！");
        }
        /*校验信息*/
        Integer operUserProj=userCommonService.getUserProjKey(Integer.parseInt(operId));
        Boolean isProjAdmin=userCommonService.isAdministrator(Integer.parseInt(operId),operUserProj);
        if(operUserProj==null){
            log.error("查询用户列表,用户权限有误！");
            throw new ParameterInvalidException("查询用户列表,用户权限有误！");
        }

        if(isProjAdmin&&operUserProj==0){
            /*超管查询*/
        }else if(isProjAdmin){
            /*资源组管理员查找*/
            conditionMap.put("projKey",operUserProj);
            return queryProjectSignalFollows(conditionMap,helper);
        }else {
            /*普通用户查找*/
            conditionMap.put("userId",operId);
        }

        if(!StringUtils.isEmpty(conditionMap.get("userId"))){
            serchMap.put(FuUserFollows.USER_ID,conditionMap.get("userId"));
        }
        if(!StringUtils.isEmpty(conditionMap.get("signalId"))){
            serchMap.put(FuUserFollows.SIGNAL_ID,conditionMap.get("signalId"));
        }
        if(!StringUtils.isEmpty(conditionMap.get("userMtAccId"))){
            serchMap.put(FuUserFollows.USER_MT_ACC_ID,conditionMap.get("userMtAccId"));
        }
        if(!StringUtils.isEmpty(conditionMap.get("signalMtAccId"))){
            serchMap.put(FuUserFollows.SIGNAL_MT_ACC_ID,conditionMap.get("signalMtAccId"));
        }
        if(!StringUtils.isEmpty(conditionMap.get("followState"))){
            serchMap.put(FuUserFollows.FOLLOW_STATE,conditionMap.get("followState"));
        }

        if(helper==null){
            helper=new PageInfoHelper();
        }
        Page<FuUserFollows> userFollows= PageHelper.startPage(helper.getPageNo(),helper.getPageSize());
        fuUserFollowsMapper.selectByMap(serchMap);
        return userFollows;
    }

    /**
     * 查询跟随列表
     * @param conditionMap
     * @param helper
     * @return
     */
    public Page<FuUserFollows> queryProjectSignalFollows(Map conditionMap,PageInfoHelper helper){
        if(conditionMap == null||conditionMap.get("projKey")==null){
            log.error("查询列表,获取参数为空！");
            throw new ParameterInvalidException("查询列表,获取参数为空！");
        }

        if(helper==null){
            helper=new PageInfoHelper();
        }
        Page<FuUserFollows> userFollows= PageHelper.startPage(helper.getPageNo(),helper.getPageSize());
        fuUserFollowsMapper.queryProjectSignalFollows(conditionMap);
        return userFollows;
    }

    /**
     * 信号源订阅
     * @param dataMap
     * @return
     */
    @Transactional
    public void signalFollowsSaveOrUpdate(Map dataMap){
        /*校验参数*/
        if(MapUtils.isEmpty(dataMap)){
            log.error("查询跟随列表 参数为空！");
            throw new ParameterInvalidException(GlobalResultCode.PARAM_NULL_POINTER);
        }
        if(dataMap.get("userId")==null
                ||dataMap.get("userMtAccId")==null||dataMap.get("signalId")==null){
            log.error("查询跟随列表 参数为空！");
            throw new ParameterInvalidException(GlobalResultCode.PARAM_NULL_POINTER);
        }

        String operUserId = String.valueOf(dataMap.get("operUserId"));
        String userId = String.valueOf(dataMap.get("userId"));
        String userMtAccId = String.valueOf(dataMap.get("userMtAccId"));
        String signalId = String.valueOf(dataMap.get("signalId"));

        /*判断权限 1 userId是不是信号源，2 oerUserId userId是不是同一团队，3 signalId是不是有权限， 4校验是否已存在， 5上限跟3个*/
        /*查看 申请人和用户 是否属于同一团队工程*/
        Integer userProjKey= userCommonService.getUserProjKey(Integer.parseInt(userId));
        Integer operProjKey= userCommonService.getUserProjKey(Integer.parseInt(operUserId));
        if(userProjKey==null||operProjKey==null){
            log.error("查询用户和申请人团队信息失败！");
            throw new BusinessException(GlobalResultCode.RESULE_DATA_NONE,"查询用户和申请人团队信息失败！");
        }
        Boolean isProjAdmin=userCommonService.isAdministrator(Integer.parseInt(operUserId),operProjKey);
        if(isProjAdmin&&operProjKey==0){
            /*超管查询*/
        }else if(isProjAdmin){
            /*资源组管理员查找*/
            if(userProjKey!=operProjKey){
                log.error("无权限操作该用户，只能设置自己团队用户跟随！");
                throw new BusinessException("无权限操作该用户，只能设置自己团队用户跟随！");
            }
        }else {
            /*普通用户 只能操作自己账号*/
            if(!operUserId.equalsIgnoreCase(userId)){
                log.error("无权限操作该用户，只能设置自己账户跟随！");
                throw new BusinessException("无权限操作该用户，只能设置自己账户跟随！");
            }
        }

        /* userId是不是信号源 信号源不能跟随信号源！*/
        Map conMap=new HashMap();
        conMap.put("userId",userId);
        conMap.put("mtAccId",userMtAccId);
        List<UserMTAccountBO> userAccounts= fuAccountMtService.getUserMTAccByCondition(conMap);
        FuProductSignal signal=fuProductSignalMapper.selectByPrimaryKey(Integer.parseInt(signalId));
        if(ObjectUtils.isEmpty(signal)){
            log.error("根据信号源ID 查询信号源失败！");
            throw new ParameterInvalidException("根据信号源ID 查询信号源失败！");
        }
        if(ObjectUtils.isEmpty(userAccounts)){
            log.error("根据用户ID 查询用户账户信息失败！");
            throw new ParameterInvalidException("根据用户ID 查询用户账户信息失败！");
        }
        UserMTAccountBO userMTAccountBO=userAccounts.get(0);
        if(userMTAccountBO.getUserType()== UserConstant.USER_TYPE_SIGNAL){
            log.error("信号源不能跟随信号源！");
            throw new ParameterInvalidException("该用户为信号源 信号源不能跟随信号源！");
        }

        /*signalId是不是有权限*/
        conMap.clear();
        conMap.put(FuProductSignalPermit.SIGNAL_ID,signalId);
        conMap.put(FuProductSignalPermit.PROJ_KEY,userProjKey);
        List<FuProductSignalPermit> permits= fuProductSignalPermitService.selectByMap(conMap);
        if(permits==null||permits.isEmpty()){
            log.error("无跟随该信号源的权限！");
            throw new BusinessException("无跟随该信号源的权限！");
        }

        /*校验是否已存在 上限跟三个*/
        Boolean isNew=true;
        int followNum=0;
        boolean isDelete=false;
        int isReStart=0; // 0 其他操作，1 正常订单废弃，2 废弃订单改正常
        int followAction=FollowConstant.FOLLOW_STATE_HIDE;
        if(!ObjectUtils.isEmpty(dataMap.get("followState"))){
            followAction=Integer.parseInt(String.valueOf(dataMap.get("followState")));
        }
        FuUserFollows follows = new FuUserFollows();

        Wrapper<FuUserFollows> followsWrapper=new EntityWrapper<>();
        followsWrapper.eq(FuUserFollows.USER_ID,userId);
        followsWrapper.eq(FuUserFollows.USER_MT_ACC_ID,userMtAccId);
        /*查询用户 该账户下的跟随关系*/
        List<FuUserFollows> followList= selectList(followsWrapper);
        for (FuUserFollows oldFollows:followList){
            if(!oldFollows.getFollowState().equals(FollowConstant.FOLLOW_STATE_DELETE)){
                followNum++;
            }
            if(oldFollows.getSignalId()==Integer.parseInt(signalId)){
                // 根据条件查询到跟单关系 已存在
                isNew=false;
                follows=oldFollows;
                if(oldFollows.getFollowState().equals(FollowConstant.FOLLOW_STATE_DELETE)){
                    /*正在修改废弃的 订单*/
                    isDelete=true;
                    if(followAction!=FollowConstant.FOLLOW_STATE_DELETE){
                        /*废弃订单改为正常订单*/
                        isReStart=2;
                    }
                }
                if(!oldFollows.getFollowState().equals(FollowConstant.FOLLOW_STATE_DELETE)){
                    if(followAction==FollowConstant.FOLLOW_STATE_DELETE){
                        /*正常订单废弃*/
                        isReStart=1;
                    }
                }
            }
        }
        if(followNum>=FollowConstant.FOLLOW_MAX_NUM && isNew){
            //当前已经有3个跟单关系 并且还在新增
            log.error("该账户已跟随3个信号源，已达到跟随上限！");
            throw new BusinessException("该账户已跟随3个信号源，已达到跟随上限！");
        }
        if(followNum>=FollowConstant.FOLLOW_MAX_NUM && isDelete){
            //当前已经有3个跟单关系 并且还在修改废弃的订单
            log.error("该账户已跟随3个信号源，已达到跟随上限，不可以添加或修改！");
            throw new BusinessException("该账户已跟随3个信号源，已达到跟随上限，不可以添加或修改！");
        }

        follows.setUserId(userMTAccountBO.getUserId());
        follows.setSignalId(signal.getId());
        follows.setUserServerName(userMTAccountBO.getServerName());
        follows.setUserMtAccId(userMTAccountBO.getMtAccId());
        follows.setSignalServerName(signal.getServerName());
        follows.setSignalMtAccId(signal.getMtAccId());
        follows.setFollowState(followAction);

        if(!ObjectUtils.isEmpty(dataMap.get("followDirect"))){
            follows.setFollowDirect(Integer.parseInt(String.valueOf(dataMap.get("followDirect"))));
        }
        if(!ObjectUtils.isEmpty(dataMap.get("followMode"))){
            follows.setFollowMode(Integer.parseInt(String.valueOf(dataMap.get("followMode"))));
        }
        if(!ObjectUtils.isEmpty(dataMap.get("followType"))){
            follows.setFollowType(Integer.parseInt(String.valueOf(dataMap.get("followType"))));
        }
        if(!ObjectUtils.isEmpty(dataMap.get("followAmount"))){
            follows.setFollowAmount(new BigDecimal(String.valueOf(dataMap.get("followAmount"))));
        }
        if(!ObjectUtils.isEmpty(dataMap.get("accountEquityAmount"))){
            follows.setAccountEquityAmount(new BigDecimal(String.valueOf(dataMap.get("accountEquityAmount"))));
        }
        if(!ObjectUtils.isEmpty(dataMap.get("accountEquityPercentage"))){
            follows.setAccountEquityPercentage(new BigDecimal(String.valueOf(dataMap.get("accountEquityPercentage"))));
        }
        if(!ObjectUtils.isEmpty(dataMap.get("followAlarmAmount"))){
            follows.setFollowAlarmAmount(new BigDecimal(String.valueOf(dataMap.get("followAlarmAmount"))));
        }
        if(!ObjectUtils.isEmpty(dataMap.get("followMaxHands"))){
            follows.setFollowMaxHands(new BigDecimal(String.valueOf(dataMap.get("followMaxHands"))));
        }
        if(!ObjectUtils.isEmpty(dataMap.get("followMinHands"))){
            follows.setFollowMinHands(new BigDecimal(String.valueOf(dataMap.get("followMinHands"))));
        }
        if(!ObjectUtils.isEmpty(dataMap.get("followMaxAmount"))){
            follows.setFollowMaxAmount(new BigDecimal(String.valueOf(dataMap.get("followMaxAmount"))));
        }
        if(!ObjectUtils.isEmpty(dataMap.get("followMinAmount"))){
            follows.setFollowMinAmount(new BigDecimal(String.valueOf(dataMap.get("followMinAmount"))));
        }

        if(isNew){
            /*添加订阅人数*/
            fuProductSignalService.signalFollowsNumIncrease(follows.getSignalId(),true,1);
            /*新增订阅关系*/
            fuUserFollowsMapper.insertSelective(follows);
        }else {
            if(isReStart==1){
                //正常订单废弃
                fuProductSignalService.signalFollowsNumIncrease(follows.getSignalId(),false,-1);
            }else if(isReStart==2){
                //废弃订单改正常
                fuProductSignalService.signalFollowsNumIncrease(follows.getSignalId(),false,1);
            }
            follows.setModifyDate(new Date());
            fuUserFollowsMapper.updateByPrimaryKeySelective(follows);
        }

        if(follows.getFollowState()==FollowConstant.FOLLOW_STATE_NORMAL){
            /*启动用户账户监听*/
            int clientId=fuTradeAccountService.setAccountConnectTradeAllowed(userMTAccountBO.getServerName(),Integer.parseInt(userMTAccountBO.getMtAccId()),userMTAccountBO.getMtPasswordTrade());
            if(clientId== 0){
                log.error("用户账号监听失败！"+TradeErrorEnum.getMessage(1));
                throw new BusinessException("用户账号监听失败！");
            }

            // /*更新监听数据*/
            if(!fuTradeAccountService.addAccountFollowRelation(follows.getSignalMtAccId(),follows.getUserMtAccId(),follows)){
                log.error("设置跟单关系失败：signalMtAccId:"+follows.getSignalMtAccId()+",userMtAccId"+follows.getUserMtAccId());
                throw new BusinessException("设置跟单关系失败：signalMtAccId:"+follows.getSignalMtAccId()+",userMtAccId"+follows.getUserMtAccId());
            }
        }else {
            // /*取消监听数据*/
            if(!fuTradeAccountService.removeAccountFollowRelation(follows.getSignalMtAccId(),follows.getUserMtAccId())){
                log.error("取消跟单关系失败：signalMtAccId:"+follows.getSignalMtAccId()+",userMtAccId"+follows.getUserMtAccId());
                throw new BusinessException("取消跟单关系失败：signalMtAccId:"+follows.getSignalMtAccId()+",userMtAccId"+follows.getUserMtAccId());
            }
        }
    }


    /**
     * 信号源取消订阅
     * @param dataMap
     * @return
     */
    @Transactional
    public void signalFollowsRemove(Map dataMap){
        /*校验参数*/
        if(MapUtils.isEmpty(dataMap)){
            log.error("查询跟随列表 参数为空！");
            throw new ParameterInvalidException(GlobalResultCode.PARAM_NULL_POINTER);
        }
        if(dataMap.get("userId")==null|| dataMap.get("signalId")==null
                ||dataMap.get("mtAccId")==null){
            log.error("查询跟随列表 参数为空！");
            throw new ParameterInvalidException(GlobalResultCode.PARAM_NULL_POINTER);
        }

        String mtAccId = String.valueOf(dataMap.get("mtAccId"));
        String userId = String.valueOf(dataMap.get("userId"));
        String signalId = String.valueOf(dataMap.get("signalId"));
        /*校验是否已存在*/
        FuUserFollows follows = new FuUserFollows();
        Map followConditon=new HashMap();
        followConditon.put(FuUserFollows.USER_ID,userId);
        followConditon.put(FuUserFollows.USER_MT_ACC_ID,mtAccId);
        followConditon.put(FuUserFollows.SIGNAL_ID,signalId);
        List<FuUserFollows> followList= fuUserFollowsMapper.selectByMap(followConditon);
        if(followList!=null && !followList.isEmpty() && followList.size()>0){
            follows=followList.get(0);
        }else {
            log.error("跟单关系不存在！");
            throw new BusinessException(GlobalResultCode.RESULE_DATA_NONE);
        }
        follows.setFollowState(FollowConstant.FOLLOW_STATE_DELETE);
        follows.setModifyDate(new Date());

        //修改信号源：正常订单废弃
        fuProductSignalService.signalFollowsNumIncrease(follows.getSignalId(),false,-1);

        //修改跟随信息
        fuUserFollowsMapper.updateByPrimaryKeySelective(follows);

        /*更新监听数据*/
        if(!fuTradeAccountService.removeAccountFollowRelation(follows.getSignalMtAccId(),follows.getUserMtAccId())){
            log.error("设置跟单关系失败：signalMtAccId:"+follows.getSignalMtAccId()+",userMtAccId"+follows.getUserMtAccId());
            throw new BusinessException("设置跟单关系失败：signalMtAccId:"+follows.getSignalMtAccId()+",userMtAccId"+follows.getUserMtAccId());
        }
    }


    /**
     * 查询跟随用户信息
     * @param requestMap
     */
    public PageInfo<FuFollowUserVO> queryFollowUsers(JSONObject requestMap){
        /*校验信息*/
        int pageSize=20;
        int pageNum=1;

        if(StringUtils.isEmpty(pageSize)||StringUtils.isEmpty(pageNum)){
            log.error("分页数据为空！");
            new DataConflictException("分页数据为空！");
        }
        if(requestMap.getString("pageSize")!=null){
            pageSize=Integer.parseInt(requestMap.getString("pageSize"));
        }
        if(requestMap.getString("pageNum")!=null){
            pageNum=Integer.parseInt(requestMap.getString("pageNum"));
        }

        Map conditionMap =requestMap.getInnerMap();
        PageHelper.startPage(pageNum,pageSize);
        List<FuFollowUserVO>followUsers= fuUserFollowsMapper.queryFollowUsers(conditionMap);
        return new PageInfo<FuFollowUserVO>(followUsers);
    }

}
