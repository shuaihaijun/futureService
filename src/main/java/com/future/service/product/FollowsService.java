package com.future.service.product;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.toolkit.MapUtils;
import com.future.common.enums.GlobalResultCode;
import com.future.common.exception.BusinessException;
import com.future.common.exception.DataConflictException;
import com.future.common.exception.ParameterInvalidException;
import com.future.common.util.StringUtils;
import com.future.entity.product.FuProductSignal;
import com.future.entity.user.FuUserFollows;
import com.future.mapper.product.FuProductSignalMapper;
import com.future.mapper.user.FuUserFollowsMapper;
import com.future.pojo.bo.order.UserMTAccountBO;
import com.future.pojo.vo.signal.FuFollowUserVO;
import com.future.pojo.vo.signal.FuUserSignalVO;
import com.future.service.account.FuAccountMtService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FollowsService extends ServiceImpl<FuUserFollowsMapper, FuUserFollows> {

    Logger log= LoggerFactory.getLogger(FollowsService.class);
    @Autowired
    FuProductSignalService fuProductSignalService;
    @Autowired
    FuProductSignalMapper fuProductSignalMapper;
    @Autowired
    FuAccountMtService fuAccountMtService;
    @Autowired
    FuUserFollowsMapper fuUserFollowsMapper;

    /**
     * 查询跟随列表
     * @param conditionMap
     * @return
     */
    public List<FuUserFollows> signalFollowsQuery(Map conditionMap){
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
        if(!StringUtils.isEmpty(conditionMap.get("ruleState"))){
            serchMap.put(FuUserFollows.RULE_STATE,conditionMap.get("ruleState"));
        }
        return selectByMap(serchMap);
    }

    /**
     * 信号源订阅
     * @param dataMap
     * @return
     */
    public void signalFollowsSaveOrUpdate(Map dataMap){
        /*校验参数*/
        if(MapUtils.isEmpty(dataMap)){
            log.error("查询跟随列表 参数为空！");
            throw new ParameterInvalidException(GlobalResultCode.PARAM_NULL_POINTER);
        }
        if(StringUtils.isEmpty(dataMap.get("userId"))||StringUtils.isEmpty(dataMap.get("signalId"))){
            log.error("查询跟随列表 参数为空！");
            throw new ParameterInvalidException(GlobalResultCode.PARAM_NULL_POINTER);
        }

        String userId = String.valueOf(dataMap.get("userId"));
        String signalId = String.valueOf(dataMap.get("signalId"));

        Map conMap=new HashMap();
        conMap.put("userId",userId);
        FuProductSignal signal=fuProductSignalMapper.selectByPrimaryKey(Integer.parseInt(signalId));
        List<UserMTAccountBO> userAccounts= fuAccountMtService.getUserMTAccByCondition(conMap);
        if(ObjectUtils.isEmpty(signal)){
            log.error("根据信号源ID 查询信号源失败！");
            throw new ParameterInvalidException(GlobalResultCode.PARAM_IS_INVALID);
        }
        if(ObjectUtils.isEmpty(userAccounts)){
            log.error("根据用户ID 查询用户账户信息失败！");
            throw new ParameterInvalidException(GlobalResultCode.PARAM_IS_INVALID);
        }

        /*校验是否已存在*/
        Boolean isNew=true;
        FuUserFollows follows = new FuUserFollows();
        Map followConditon=new HashMap();
        followConditon.put(FuUserFollows.USER_ID,userId);
        followConditon.put(FuUserFollows.SIGNAL_ID,signalId);
        List<FuUserFollows> followList= fuUserFollowsMapper.selectByMap(followConditon);
        if(followList!=null && !followList.isEmpty() && followList.size()>0){
            // 根据条件查询到跟单关系 已存在
            if(followList.get(0).getRuleState()!=0){
                /*跟新*/
                isNew=false;
                follows=followList.get(0);
            }else {
                log.error("跟单关系已存在！");
                throw new BusinessException(GlobalResultCode.DATA_ALREADY_EXISTED);
            }
        }

        follows.setUserId(userAccounts.get(0).getUserId());
        follows.setSignalId(signal.getId());
        follows.setUserServerName(userAccounts.get(0).getServerName());
        follows.setUserMtAccId(userAccounts.get(0).getMtAccId());
        follows.setSignalServerName(signal.getServerName());
        follows.setSignalMtAccId(signal.getMtAccId());
        follows.setRuleState(0);
        if(!StringUtils.isEmpty(dataMap.get("ruleType"))){
            follows.setRuleType(Integer.parseInt(String.valueOf(dataMap.get("ruleType"))));
        }
        if(!StringUtils.isEmpty(dataMap.get("amount"))){
            follows.setAmount(new BigDecimal(String.valueOf(dataMap.get("amount"))));
        }
        if(!StringUtils.isEmpty(dataMap.get("'limitUpperAmount"))){
            follows.setLimitUpperAmount(new BigDecimal(String.valueOf(dataMap.get("'limitUpperAmount"))));
        }
        if(!StringUtils.isEmpty(dataMap.get("'accountEquityAmount'"))){
            follows.setAccountEquityAmount(new BigDecimal(String.valueOf(dataMap.get("'accountEquityAmount'"))));
        }
        if(!StringUtils.isEmpty(dataMap.get("'accountEquityPercentage'"))){
            follows.setAccountEquityPercentage(new BigDecimal(String.valueOf(dataMap.get("'accountEquityPercentage'"))));
        }
        if(!StringUtils.isEmpty(dataMap.get("'followAlarmAmount'"))){
            follows.setFollowAlarmAmount(new BigDecimal(String.valueOf(dataMap.get("'followAlarmAmount'"))));
        }

        if(isNew){
            fuUserFollowsMapper.insertSelective(follows);
        }else {
            follows.setModifyDate(new Date());
            fuUserFollowsMapper.updateByPrimaryKeySelective(follows);
        }
    }


    /**
     * 信号源取消订阅
     * @param dataMap
     * @return
     */
    public void signalFollowsRemove(Map dataMap){
        /*校验参数*/
        if(MapUtils.isEmpty(dataMap)){
            log.error("查询跟随列表 参数为空！");
            throw new ParameterInvalidException(GlobalResultCode.PARAM_NULL_POINTER);
        }
        if(StringUtils.isEmpty(dataMap.get("userId"))||StringUtils.isEmpty(dataMap.get("signalId"))){
            log.error("查询跟随列表 参数为空！");
            throw new ParameterInvalidException(GlobalResultCode.PARAM_NULL_POINTER);
        }

        String userId = String.valueOf(dataMap.get("userId"));
        String signalId = String.valueOf(dataMap.get("signalId"));
        /*校验是否已存在*/
        FuUserFollows follows = new FuUserFollows();
        Map followConditon=new HashMap();
        followConditon.put(FuUserFollows.USER_ID,userId);
        followConditon.put(FuUserFollows.SIGNAL_ID,signalId);
        List<FuUserFollows> followList= fuUserFollowsMapper.selectByMap(followConditon);
        if(followList!=null && !followList.isEmpty() && followList.size()>0){
            follows=followList.get(0);
        }else {
            log.error("跟单关系不存在！");
            throw new BusinessException(GlobalResultCode.RESULE_DATA_NONE);
        }
        follows.setRuleState(2);
        follows.setModifyDate(new Date());
        fuUserFollowsMapper.updateByPrimaryKeySelective(follows);
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
        if(!StringUtils.isEmpty(requestMap.getString("pageSize"))){
            pageSize=Integer.parseInt(requestMap.getString("pageSize"));
        }
        if(!StringUtils.isEmpty(requestMap.getString("pageNum"))){
            pageNum=Integer.parseInt(requestMap.getString("pageNum"));
        }

        Map conditionMap =requestMap.getInnerMap();
        PageHelper.startPage(pageNum,pageSize);
        List<FuFollowUserVO>followUsers= fuUserFollowsMapper.queryFollowUsers(conditionMap);
        return new PageInfo<FuFollowUserVO>(followUsers);
    }

}
