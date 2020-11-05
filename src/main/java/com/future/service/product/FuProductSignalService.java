package com.future.service.product;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.future.common.constants.*;
import com.future.common.enums.GlobalResultCode;
import com.future.common.exception.DataConflictException;
import com.future.common.exception.ParameterInvalidException;
import com.future.common.helper.PageInfoHelper;
import com.future.common.util.ConvertUtil;
import com.future.common.util.RedisManager;
import com.future.common.util.StringUtils;
import com.future.entity.product.FuProductSignal;
import com.future.mapper.product.FuProductSignalMapper;
import com.future.pojo.vo.signal.FuUserSignalVO;
import com.future.service.user.FuUserIdentityService;
import com.future.service.user.UserCommonService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FuProductSignalService extends ServiceImpl<FuProductSignalMapper,FuProductSignal> {

    @Autowired
    UserCommonService userCommonService;
    @Autowired
    FuProductSignalMapper fuProductSignalMapper;
    @Autowired
    RedisManager redisManager;
    @Autowired
    FuUserIdentityService fuUserIdentityService;

    Logger log= LoggerFactory.getLogger(FuProductSignalService.class);


    /**
     * 查询申请列表
     * @param conditionMap
     * @param helper
     * @return
     */
    public Page<FuProductSignal> querySignalInfos(Map conditionMap, PageInfoHelper helper){

        /*判断查询条件*/
        if(conditionMap == null||conditionMap.get("operId")==null){
            log.error("查询列表,获取参数为空！");
            throw new ParameterInvalidException("查询列表,获取参数为空！");
        }
        /*判断权限*/
        String operId=String.valueOf(conditionMap.get("operId"));
        if(StringUtils.isEmpty(operId)){
            log.error("查询用户列表,用户未登录！");
            throw new ParameterInvalidException("查询用户列表,获取参数为空！");
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
            return findSignalByCondition(conditionMap,helper);
        }else if(isProjAdmin){
            /*资源组管理员查找*/
            conditionMap.put("projKey",operUserProj);
            return findSignalByCondition(conditionMap,helper);
        }else {
            /*普通用户查找*/
            conditionMap.put("userId",operId);
            return findSignalByCondition(conditionMap,helper);
        }
    }


    /**
     * 根据条件查找信号源信息
     * @param conditionMap
     * @param helper
     * @return
     */
    public Page<FuProductSignal> findSignalByCondition(Map conditionMap, PageInfoHelper helper){
        /*校验信息*/
        Wrapper<FuProductSignal> wrapper=new EntityWrapper<FuProductSignal>();

        if(!ObjectUtils.isEmpty(conditionMap.get("signalId"))){
            wrapper.eq(FuProductSignal.SIGNAL_ID,String.valueOf(conditionMap.get("signalId")));
        }
        if(!ObjectUtils.isEmpty(conditionMap.get("userId"))){
            wrapper.eq(FuProductSignal.USER_ID,String.valueOf(conditionMap.get("userId")));
        }
        if(!ObjectUtils.isEmpty(conditionMap.get("signalName"))){
            wrapper.eq(FuProductSignal.SIGNAL_NAME,String.valueOf(conditionMap.get("signalName")));
        }
        if(!ObjectUtils.isEmpty(conditionMap.get("projKey"))){
            wrapper.eq(FuProductSignal.PROJ_KEY,String.valueOf(conditionMap.get("projKey")));
        }
        if(!ObjectUtils.isEmpty(conditionMap.get("signalState"))){
            if(String.valueOf(conditionMap.get("signalState")).indexOf(",")<0){
                wrapper.eq(FuProductSignal.SIGNAL_STATE,String.valueOf(conditionMap.get("signalState")));
            }else {
                //多状态
                String[] state=String.valueOf(conditionMap.get("signalState")).split(",");
                Wrapper<FuProductSignal> stateWrapper=new EntityWrapper<FuProductSignal>();
                wrapper.andNew().eq(FuProductSignal.SIGNAL_STATE,state[0]);
                for (int i=1;i<state.length;i++){
                    wrapper.or().eq(FuProductSignal.SIGNAL_STATE,state[i]);
                }
            }
        }
        if(!ObjectUtils.isEmpty(conditionMap.get("serverName"))){
            wrapper.eq(FuProductSignal.SERVER_NAME,String.valueOf(conditionMap.get("serverName")));
        }
        if(!ObjectUtils.isEmpty(conditionMap.get("mtAccId"))){
            wrapper.eq(FuProductSignal.MT_ACC_ID,String.valueOf(conditionMap.get("mtAccId")));
        }
        if(!ObjectUtils.isEmpty(conditionMap.get("createDate"))){
            if(String.valueOf(conditionMap.get("createDate")).indexOf(",")<0){
                wrapper.eq(FuProductSignal.CREATE_DATE,conditionMap.get("createDate"));
            }else {
                //时间段
                String[] dateClose=String.valueOf(conditionMap.get("createDate")).split(",");
                if(dateClose.length!=2){
                    log.error("创建时间段数据传入错误！"+conditionMap.get("orderCloseDate"));
                    throw new DataConflictException(GlobalResultCode.PARAM_VERIFY_ERROR,"创建时间段数据传入错误！"+conditionMap.get("orderCloseDate"));
                }
                wrapper.gt(FuProductSignal.CREATE_DATE,dateClose[0]);
                wrapper.lt(FuProductSignal.CREATE_DATE,dateClose[1]);
            }
        }
        if(!ObjectUtils.isEmpty(conditionMap.get("checkDate"))){
            if(String.valueOf(conditionMap.get("checkDate")).indexOf(",")<0){
                wrapper.eq(FuProductSignal.CHECK_DATE,conditionMap.get("checkDate"));
            }else {
                //时间段
                String[] dateClose=String.valueOf(conditionMap.get("checkDate")).split(",");
                if(dateClose.length!=2){
                    log.error("创建时间段数据传入错误！"+conditionMap.get("checkDate"));
                    throw new DataConflictException(GlobalResultCode.PARAM_VERIFY_ERROR,"创建时间段数据传入错误！"+conditionMap.get("checkDate"));
                }
                wrapper.gt(FuProductSignal.CHECK_DATE,dateClose[0]);
                wrapper.lt(FuProductSignal.CHECK_DATE,dateClose[1]);
            }
        }
        if(helper==null){
            helper=new PageInfoHelper();
        }
        Page<FuProductSignal> signals= PageHelper.startPage(helper.getPageNo(),helper.getPageSize());
        fuProductSignalMapper.selectList(wrapper);
        /*设置监听状态*/
        for(FuProductSignal signal:signals.getResult()){
            if(redisManager.hget(RedisConstant.H_ACCOUNT_CONNECT_INFO,signal.getMtAccId())!=null){
                signal.setConnectState(CommonConstant.COMMON_YES);
            }
        }
        return signals;
    }

    /**
     * 根据ID获取信号源信息
     * @param signalId
     * @return
     */
    public FuProductSignal findSignalById(int signalId){
        /*校验信息*/
        return selectById(signalId);
    }


    /**
     * 保存信号源信息
     * @param signalMap
     */
    public Boolean saveProductSignal(Map signalMap){
        /*校验信息*/
        FuProductSignal signal=new FuProductSignal();
        /*组装信息*/
        return insertOrUpdate(signal);
    }

    /**
     * 查询用户信号源信息
     * @param requestMap
     */
    public PageInfo<FuUserSignalVO> querySignalUsers(JSONObject requestMap){
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
        if(conditionMap.get("signalState")==null){
            conditionMap.put("signalState", AccountConstant.ACCOUNT_STATE_NORMAL);
        }
        PageHelper.startPage(pageNum,pageSize);
        List<FuUserSignalVO> signals= fuProductSignalMapper.querySignalUsers(conditionMap);
        return new PageInfo<FuUserSignalVO>(signals);
    }

    /**
     * 查询权限信号源信息
     * @param conditionMap
     * @param helper
     * @return
     */
    public Page<FuUserSignalVO> querySignalUsersPermit(Map conditionMap, PageInfoHelper helper){

        if(conditionMap==null){
            log.error(" 查询权限信号源信息 数据为空！");
            new DataConflictException("查询权限信号源信息 数据为空！");
        }

        if(conditionMap.get("signalState")==null){
            conditionMap.put("signalState", AccountConstant.ACCOUNT_STATE_NORMAL);
        }
        if(conditionMap.get("projKey")==null){
            conditionMap.put("projKey", 0);
        }

        if(!ObjectUtils.isEmpty(conditionMap.get("historyWithdraw"))){
            if(String.valueOf(conditionMap.get("historyWithdraw")).indexOf(",")<0){
                conditionMap.put(FuProductSignal.HISTORY_WITHDRAW,conditionMap.get("historyWithdraw"));
            }else {
                //时间段
                String[] dateClose=String.valueOf(conditionMap.get("historyWithdraw")).split(",");
                if(dateClose.length!=2){
                    log.error("创建时间段数据传入错误！"+conditionMap.get("historyWithdraw"));
                    throw new DataConflictException(GlobalResultCode.PARAM_VERIFY_ERROR,"创建时间段数据传入错误！"+conditionMap.get("historyWithdraw"));
                }
                conditionMap.put("withdrawBegin",dateClose[0]);
                conditionMap.put("withdrawEnd",dateClose[1]);
            }
        }
        if(helper==null){
            helper=new PageInfoHelper();
        }
        Page<FuUserSignalVO> signals= PageHelper.startPage(helper.getPageNo(),helper.getPageSize());
        fuProductSignalMapper.querySignalUsersPermit(conditionMap);
        return signals;
    }

    /**
     * 修改信号源状态
     * @param signalId
     */
    @Transactional
    public Boolean signalStateUpdate(Integer signalId, Integer signalState){
        /**校验信息*/
        if(signalId==null||signalId==0){
            log.error("修改信号源状态 传入参数为空！");
            throw new DataConflictException("修改信号源状态 传入参数为空！");
        }

        FuProductSignal signal=selectById(signalId);
        signal.setSignalState(signalState);

        /*判断删除信号源*/
        if(signalState== SignalConstant.SIGNAL_STATE_DELETE){
            // 判断用户是否需要重新定义身份,如果用户没有其他信号源，就删除用户信号源的身份
            Wrapper<FuProductSignal> wrapper=new EntityWrapper<>();
            wrapper.eq(FuProductSignal.USER_ID,signal.getUserId());
            wrapper.ne(FuProductSignal.SIGNAL_ID,signal.getId());
            List<FuProductSignal> signals= fuProductSignalMapper.selectList(wrapper);
            if(signals==null||signals.size()==0){
                /*该用户已经没有其他信号源账户了  移除用户信号源身份*/
                fuUserIdentityService.removeIdentity(signal.getUserId(),UserConstant.USER_IDENTITY_SIGNAL);
            }
            // 删除信号源
            return deleteById(signalId);
        }

        return updateById(signal);
    }

    /**
     * 根据条件查找可跟随信号源
     * @param conditionMap
     * @param helper
     * @return
     */
    public Page<FuProductSignal> querySignalAllowed(Map conditionMap, PageInfoHelper helper){
        /*校验信息*/
        if(ObjectUtils.isEmpty(conditionMap.get("operId"))){
            throw new DataConflictException(GlobalResultCode.PARAM_NULL_POINTER.message());
        }
        /*判断权限*/
        String operId=String.valueOf(conditionMap.get("operId"));
        if(StringUtils.isEmpty(operId)){
            log.error("查询用户列表,用户未登录！");
            throw new ParameterInvalidException("查询用户列表,获取参数为空！");
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
        }else {
            /*普通用户查找*/
            conditionMap.put("projKey",operUserProj);
        }

        if(helper==null){
            helper=new PageInfoHelper();
        }
        Page<FuProductSignal> signals= PageHelper.startPage(helper.getPageNo(),helper.getPageSize());
        fuProductSignalMapper.querySignalAllowed(conditionMap);
        /*设置监听状态*/
        for(FuProductSignal signal:signals.getResult()){
            if(redisManager.hget(RedisConstant.H_ACCOUNT_CONNECT_INFO,signal.getMtAccId())!=null){
                signal.setConnectState(CommonConstant.COMMON_YES);
            }
        }
        return signals;
    }

    /**
     * 根据ProjKey查找可跟随信号源
     * @param conditionMap
     * @param helper
     * @return
     */
    public Page<FuProductSignal> querySignalAllowedByProjKey(Map conditionMap, PageInfoHelper helper){
        /*校验信息*/
        if(ObjectUtils.isEmpty(conditionMap.get("projKey"))){
            throw new DataConflictException(GlobalResultCode.PARAM_NULL_POINTER.message());
        }

        if(helper==null){
            helper=new PageInfoHelper();
        }
        Page<FuProductSignal> signals= PageHelper.startPage(helper.getPageNo(),helper.getPageSize());
        fuProductSignalMapper.querySignalAllowed(conditionMap);

        return signals;
    }

    /**
     * 根据条件修改信号源信息
     * @param dataMap
     * @param condition
     * @return
     */
    public Boolean updateSignalByCondition(Map dataMap,Map condition){
        if(ObjectUtils.isEmpty(condition)||ObjectUtils.isEmpty(dataMap)){
            log.error("根据条件修改信号源信息，传入参数为空！");
            throw new DataConflictException("根据条件修改信号源信息，传入参数为空！");
        }
        FuProductSignal signal=new FuProductSignal();
        try {
            signal=(FuProductSignal) ConvertUtil.MapToJavaBean((HashMap) dataMap,FuProductSignal.class);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            throw new DataConflictException(e.getMessage());
        }
        if(signal.getId()!=null&&signal.getId()>0){
            signal.setId(null);
        }

        Wrapper<FuProductSignal> signalWrapper=new EntityWrapper<>();
        if(condition.get("id")!=null){
            signalWrapper.eq(FuProductSignal.SIGNAL_ID,condition.get("id"));
        }
        if(condition.get("signalId")!=null){
            signalWrapper.eq(FuProductSignal.SIGNAL_ID,condition.get("signalId"));
        }
        if(condition.get("userId")!=null){
            signalWrapper.eq(FuProductSignal.USER_ID,condition.get("userId"));
        }
        if(condition.get("mtAccId")!=null){
            signalWrapper.eq(FuProductSignal.MT_ACC_ID,condition.get("mtAccId"));
        }
        fuProductSignalMapper.update(signal,signalWrapper);

        return true;
    }

    /**
     * 添加/减少 信号源订阅人数
     * @param signalId
     * @param isNewIncrease 是否是新增关系
     * @param increase 增量
     * @return
     */
    public boolean signalFollowsNumIncrease(Integer signalId,boolean isNewIncrease, Integer increase){
        if(signalId==null|| increase==null||signalId==0){
            log.error("添加/减少 信号源订阅人数失败，参数为空！");
            throw new DataConflictException("添加/减少 信号源订阅人数失败，参数为空！");
        }
        if(increase==0){
            return true;
        }
        FuProductSignal signal=selectById(signalId);
        if(signal==null){
            log.error("添加/减少 信号源订阅人数失败，信号源不存在！");
            throw new DataConflictException("添加/减少 信号源订阅人数失败，信号源不存在！");
        }
        signal.setSignalFollows(signal.getSignalFollows()+increase);
        if(isNewIncrease){
            signal.setSignalFollowsHistory(signal.getSignalFollowsHistory()+increase);
        }
        fuProductSignalMapper.updateByPrimaryKeySelective(signal);
        return true;
    }
}
