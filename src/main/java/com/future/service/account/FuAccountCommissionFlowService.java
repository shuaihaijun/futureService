package com.future.service.account;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.future.common.constants.CommonConstant;
import com.future.common.exception.BusinessException;
import com.future.common.helper.PageInfoHelper;
import com.future.entity.account.FuAccountCommissionFlow;
import com.future.mapper.account.FuAccountCommissionFlowMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FuAccountCommissionFlowService extends ServiceImpl<FuAccountCommissionFlowMapper, FuAccountCommissionFlow> {

    Logger log= LoggerFactory.getLogger(FuAccountCommissionFlowService.class);
    @Autowired
    FuAccountCommissionFlowMapper fuAccountCommissionFlowMapper;

    /**
     * 根据用户信息查找上次佣金结算日志
     * @param userId
     * @param accountId
     * @param limit
     * @return
     */
    public List<FuAccountCommissionFlow> findLastSumFlowByUserId(Integer userId,Integer accountId,Integer limit){

        if(userId==null||userId==0){
            log.error("计算佣金日结,传入数据为空！");
            throw new BusinessException("计算佣金日结,传入数据为空！");
        }

        Map conditionMap = new HashMap();
        conditionMap.put("userId",userId);
        if(accountId!=null && accountId>0){
            conditionMap.put("accountId",accountId);
        }
        if(limit!=null && limit>0){
            conditionMap.put("limit",limit);
        }
        return fuAccountCommissionFlowMapper.findLastSumFlowByUserId(conditionMap);
    }


    /**
     * 根据条件 查询佣金流水信息
     * @param conditionMap
     * @return
     */
    public List<FuAccountCommissionFlow> findFlowByCondition(Map conditionMap){

        Wrapper<FuAccountCommissionFlow> wrapper=new EntityWrapper<>();
        wrapper.eq(FuAccountCommissionFlow.COMMISSION_STATE, CommonConstant.COMMON_SERVER_STATE_NORMAL);

        if(conditionMap.get("userId")!=null){
            wrapper.and().eq(FuAccountCommissionFlow.USER_ID,conditionMap.get("userId"));
        }
        if(conditionMap.get("accountId")!=null){
            wrapper.and().eq(FuAccountCommissionFlow.ACCOUNT_ID,conditionMap.get("accountId"));
        }
        if(conditionMap.get("commissionType")!=null){
            wrapper.and().eq(FuAccountCommissionFlow.COMMISSION_TYPE,conditionMap.get("commissionType"));
        }
        if(conditionMap.get("commissionUserType")!=null){
            wrapper.and().eq(FuAccountCommissionFlow.COMMISSION_USER_TYPE,conditionMap.get("commissionUserType"));
        }
        if(conditionMap.get("commissionLevel")!=null){
            wrapper.and().eq(FuAccountCommissionFlow.COMMISSION_LEVEL,conditionMap.get("commissionLevel"));
        }
        if(conditionMap.get("beginDate")!=null){
            wrapper.ge(FuAccountCommissionFlow.COMMISSION_DATE,conditionMap.get("beginDate"));
        }
        if(conditionMap.get("endDate")!=null){
            wrapper.le(FuAccountCommissionFlow.COMMISSION_DATE,conditionMap.get("endDate"));
        }
       return selectList(wrapper);
    }


    /**
     * 根据条件 查询佣金流水信息
     * @param conditionMap
     * @param helper
     * @return
     */
    public PageInfo<FuAccountCommissionFlow> findPageFlowByCondition(Map conditionMap, PageInfoHelper helper){

        Wrapper<FuAccountCommissionFlow> wrapper=new EntityWrapper<>();
        wrapper.eq(FuAccountCommissionFlow.COMMISSION_STATE, CommonConstant.COMMON_SERVER_STATE_NORMAL);

        if(conditionMap.get("userId")!=null){
            wrapper.and().eq(FuAccountCommissionFlow.USER_ID,conditionMap.get("userId"));
        }
        if(conditionMap.get("accountId")!=null){
            wrapper.and().eq(FuAccountCommissionFlow.ACCOUNT_ID,conditionMap.get("accountId"));
        }
        if(conditionMap.get("commissionType")!=null){
            wrapper.and().eq(FuAccountCommissionFlow.COMMISSION_TYPE,conditionMap.get("commissionType"));
        }
        if(conditionMap.get("commissionUserType")!=null){
            wrapper.and().eq(FuAccountCommissionFlow.COMMISSION_USER_TYPE,conditionMap.get("commissionUserType"));
        }
        if(conditionMap.get("commissionLevel")!=null){
            wrapper.and().eq(FuAccountCommissionFlow.COMMISSION_LEVEL,conditionMap.get("commissionLevel"));
        }
        if(conditionMap.get("beginDate")!=null){
            wrapper.ge(FuAccountCommissionFlow.COMMISSION_DATE,conditionMap.get("commissionDate"));
        }
        if(conditionMap.get("endDate")!=null){
            wrapper.le(FuAccountCommissionFlow.COMMISSION_DATE,conditionMap.get("commissionDate"));
        }

        if(helper==null){
            helper=new PageInfoHelper();
        }
        PageHelper.startPage(helper.getPageNo(),helper.getPageSize());
        List<FuAccountCommissionFlow> flows=selectList(wrapper);
        return new PageInfo<>(flows);
    }
}
