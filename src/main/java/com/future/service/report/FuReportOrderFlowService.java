package com.future.service.report;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.future.common.constants.AccountConstant;
import com.future.common.util.DateUtil;
import com.future.common.util.StringUtils;
import com.future.entity.account.FuAccountMt;
import com.future.entity.report.FuReportOrderFlow;
import com.future.mapper.report.FuReportOrderFlowMapper;
import com.future.pojo.bo.report.FuReportOrderFlowBo;
import com.future.service.account.FuAccountMtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FuReportOrderFlowService extends ServiceImpl<FuReportOrderFlowMapper,FuReportOrderFlow> {
    Logger log=LoggerFactory.getLogger(FuReportOrderFlowService.class);

    @Autowired
    FuAccountMtService fuAccountMtService;
    @Autowired
    FuReportOrderFlowMapper fuReportOrderFlowMapper;

    /**
     * 用户订单分析
     * @param userId
     * @param tradeDate
     * @param serverName
     * @param mtAccId
     */
    @Transactional
    public FuReportOrderFlow orderFlowAnalysis(Integer userId, Date tradeDate,String serverName,Integer mtAccId){
        if(userId==null||userId==0
            ||tradeDate==null|| StringUtils.isEmpty(serverName)
            ||mtAccId==null||mtAccId==0){
            log.error("用户订单分析 用户数据为空！");
        }

        FuReportOrderFlow flow=new FuReportOrderFlow();
        flow.setUserId(userId);
        flow.setServerName(serverName);
        flow.setMtAccId(String.valueOf(mtAccId));

        /*处理日期*/ //
        String tradeDateString =DateUtil.toDateString(tradeDate);
        tradeDate=DateUtil.toDate(tradeDateString);
        Date endDate=DateUtil.getFutureDate(tradeDate,1);
        String endDataString=DateUtil.toDateString(endDate);
        flow.setTradeDate(tradeDate);

        /*判断是否已存在*/
        /*查询历史流水数据*/
        FuReportOrderFlow lastOrderFlow=new FuReportOrderFlow();
        Wrapper<FuReportOrderFlow> flowWrapper=new EntityWrapper<>();
        flowWrapper.eq(FuReportOrderFlow.USER_ID,userId);
        flowWrapper.eq(FuReportOrderFlow.MT_ACC_ID,mtAccId);
        flowWrapper.le(FuReportOrderFlow.TRADE_DATE,tradeDate);//当前日期及以前的数据（有可能从时间不是当前时间，所以不能查所有的）
        flowWrapper.orderBy("trade_date desc");
        flowWrapper.last("limit 2");
        List<FuReportOrderFlow> flows= fuReportOrderFlowMapper.selectList(flowWrapper);
        for(FuReportOrderFlow orderFlow:flows){
            if(DateUtil.toDateString(orderFlow.getTradeDate()).equals(DateUtil.toDateString(tradeDate))){
                //同一天 不做重新计算
                return null;
            } else {
                //上一个交易日 累加收益
                lastOrderFlow=orderFlow;
                break;
            }
        }

        /*查询数据*/
        Map condition=new HashMap();
        condition.put("userId",userId);
        condition.put("mtAccId",mtAccId);
        condition.put("dateBegin", tradeDateString);
        condition.put("dateEnd",endDataString);
        FuReportOrderFlowBo flowBo= fuReportOrderFlowMapper.getOrderFlowDaily(condition);

        if(ObjectUtils.isEmpty(flowBo)){
            log.info("用户订单分析,时间段内没有交易记录，startTime:"+tradeDateString+",endTime"+endDataString);
            return null;
        }

        /*组装数据*/
        flow.setOrderCount(flowBo.getOrderCount());
        flow.setOrderLots(flowBo.getOrderLots());

        /*收益数据*/
        flow.setOrderProfit(flowBo.getOrderProfit());
        flow.setOrderProfitCount(flowBo.getOrderProfitCount());
        flow.setOrderProfitLots(flowBo.getOrderProfitLots());
        flow.setOrderProfitMax(flowBo.getOrderProfitMax());
        if(flowBo.getOrderProfitCount()>0){
            flow.setOrderProfitAvg(flowBo.getOrderProfit().divide(new BigDecimal(flowBo.getOrderProfitCount()), AccountConstant.BigDecimal_Scale, BigDecimal.ROUND_HALF_UP));
        }

        /*损失数据*/
        flow.setOrderLoss(flowBo.getOrderLoss());
        flow.setOrderLossCount(flowBo.getOrderLossCount());
        flow.setOrderLossLots(flowBo.getOrderLossLots());
        flow.setOrderLossMax(flowBo.getOrderLossMax());
        if(flowBo.getOrderLossCount()>0){
            flow.setOrderLossAvg(flowBo.getOrderLoss().divide(new BigDecimal(flowBo.getOrderLossCount()), AccountConstant.BigDecimal_Scale, BigDecimal.ROUND_HALF_UP));
        }

        flow.setOrderSwap(flowBo.getOrderSwap());
        flow.setOrderCommission(flowBo.getOrderCommission());
        flow.setOrderIncome(flowBo.getOrderIncome());

        //todo 这里计算历史数据时 有问题，获取不了当时的净值
        Wrapper<FuAccountMt> wrapper=new EntityWrapper<>();
        wrapper.eq(FuAccountMt.USER_ID,userId);
        wrapper.eq(FuAccountMt.MT_ACC_ID,mtAccId);
        FuAccountMt accountMt= fuAccountMtService.selectOne(wrapper);
        //当日收益率(当日收益/当日净值)
        if(accountMt.getEquity().compareTo(new BigDecimal(0))>0){
            flow.setOrderIncomeRate(flowBo.getOrderIncome().divide(accountMt.getEquity(),AccountConstant.BigDecimal_Scale, BigDecimal.ROUND_HALF_UP));
        }
        /*交易胜率(盈利笔数在总笔数中的占比，数据值越大，代表盈利订单占比越高)*/
        if(flowBo.getOrderCount()>0){
            flow.setOrderWinRate(new BigDecimal(flowBo.getOrderProfitCount()).divide(new BigDecimal(flowBo.getOrderCount()),AccountConstant.BigDecimal_Scale, BigDecimal.ROUND_HALF_UP));
            flow.setOrderHoldTimeAvg(flowBo.getOrderHoldTimeSum().divide(new BigDecimal(flowBo.getOrderCount()),AccountConstant.BigDecimal_Scale, BigDecimal.ROUND_HALF_UP));
            /*预期回报（每笔交易平均利润）*/
            flow.setOrderExpectedReturn(flowBo.getOrderIncome().divide(new BigDecimal(flowBo.getOrderCount()),AccountConstant.BigDecimal_Scale, BigDecimal.ROUND_HALF_UP));
        }
        /*净值盈亏比（盈利订单总额与亏损订单总额的比值，该值越大表明该账户当前的绩效结果越好。）*/
        if(flowBo.getOrderLoss().compareTo(new BigDecimal(0))>0){
            flow.setOrderPlRate(flowBo.getOrderProfit().divide(flowBo.getOrderLoss(),AccountConstant.BigDecimal_Scale, BigDecimal.ROUND_HALF_UP));
        }

        flow.setOrderLotsMax(flowBo.getOrderLotsMax());
        flow.setOrderMoneyMax(flowBo.getOrderMoneyMax());
        flow.setOrderHoldTimeMax(flowBo.getOrderHoldTimeMax());

        /*计算连续盈利/亏损*/
        if(lastOrderFlow!=null&&lastOrderFlow.getId()!=null&&lastOrderFlow.getId()>0){
            /*判断上次为连续盈利还是亏损*/
            if(lastOrderFlow.getOrderIncome().compareTo(BigDecimal.ZERO)>0){
                //上个交易日为盈利
                if(flow.getOrderIncome().compareTo(BigDecimal.ZERO)>0){
                    //本次为盈利 连续盈利
                    flow.setOrderProfitKeep(lastOrderFlow.getOrderProfitKeep().add(flow.getOrderIncome()));
                    flow.setOrderProfitKeepCount(lastOrderFlow.getOrderProfitKeepCount()+1);
                    flow.setOrderLossKeep(new BigDecimal(0));
                    flow.setOrderLossKeepCount(0);
                }else {
                    //本次为亏损 初次连续亏损
                    flow.setOrderLossKeep(flow.getOrderIncome());
                    flow.setOrderLossKeepCount(1);
                    flow.setOrderProfitKeep(new BigDecimal(0));
                    flow.setOrderProfitKeepCount(0);
                }
            }else {
                //上个交易日为亏损
                if(flow.getOrderIncome().compareTo(BigDecimal.ZERO)>0){
                    //本次为盈利 初次连续盈利
                    flow.setOrderProfitKeep(flow.getOrderIncome());
                    flow.setOrderProfitKeepCount(1);
                    flow.setOrderLossKeep(new BigDecimal(0));
                    flow.setOrderLossKeepCount(0);
                }else {
                    //本次为亏损 连续亏损
                    flow.setOrderLossKeep(lastOrderFlow.getOrderLossKeep().add(flow.getOrderIncome()));
                    flow.setOrderLossKeepCount(lastOrderFlow.getOrderLossKeepCount()+1);
                    flow.setOrderProfitKeep(new BigDecimal(0));
                    flow.setOrderProfitKeepCount(0);
                }
            }
        }

        fuReportOrderFlowMapper.insertSelective(flow);
        return flow;
    }

}
