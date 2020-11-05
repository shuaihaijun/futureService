package com.future.service.report;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.future.common.constants.AccountConstant;
import com.future.common.constants.CommonConstant;
import com.future.common.enums.GlobalResultCode;
import com.future.common.exception.BusinessException;
import com.future.common.exception.DataConflictException;
import com.future.common.helper.PageInfoHelper;
import com.future.common.util.DateUtil;
import com.future.common.util.StringUtils;
import com.future.entity.order.FuOrderCustomer;
import com.future.entity.product.FuProductSignal;
import com.future.entity.report.FuReportOrderFlow;
import com.future.entity.report.FuReportOrderSum;
import com.future.mapper.report.FuReportOrderSumMapper;
import com.future.pojo.vo.report.FuReportFollowVO;
import com.future.pojo.vo.report.FuReportSignalVO;
import com.future.service.order.FuOrderCustomerService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import io.swagger.models.auth.In;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class FuReportOrderSumService extends ServiceImpl<FuReportOrderSumMapper,FuReportOrderSum> {

    Logger log= LoggerFactory.getLogger(FuReportOrderSumService.class);

    @Autowired
    FuOrderCustomerService fuOrderCustomerService;
    @Autowired
    FuReportOrderFlowService fuReportOrderFlowService;
    @Autowired
    FuReportOrderSumMapper fuReportOrderSumMapper;

    /**
     * 初始化订单交易分析数据
     * @param userId
     * @param serverName
     * @param mtAccId
     */
    public void orderSumStatisticsInit(Integer userId,String serverName,Integer mtAccId){

        /*获取用户交易列表 最好开始数据*/
        Wrapper<FuOrderCustomer> flowWrapper=new EntityWrapper<>();
        flowWrapper.eq(FuOrderCustomer.USER_ID,userId);
        flowWrapper.eq(FuOrderCustomer.MT_ACC_ID,mtAccId);
        flowWrapper.orderBy(FuOrderCustomer.ORDER_CLOSE_DATE,true);
        flowWrapper.last("limit 1");
        List<FuOrderCustomer> flows= fuOrderCustomerService.selectList(flowWrapper);
        if(flows==null||flows.size()==0){
            log.warn("初始化订单交易分析数据失败，用户账户无历史交易数据，userId:"+userId+",mtAccId:"+mtAccId);
        }

        Date fistCloseDate=flows.get(0).getOrderCloseDate();

        log.info("-----------------fistCloseDate:"+fistCloseDate);

        /*计算初次交易 至今的天数*/
        Integer days=DateUtil.getDateDiff(DateUtil.toDateString(fistCloseDate),DateUtil.toDateString(new Date()));
        Date tradeDate=fistCloseDate;
        FuReportOrderFlow orderFlow=new FuReportOrderFlow();
        for(int i=0;i<days;i++){
            try {
                log.info("分析----------------i: date"+tradeDate);
                /*日交易订单流水*/
                orderFlow=fuReportOrderFlowService.orderFlowAnalysis(userId,tradeDate,serverName, mtAccId);
                if(ObjectUtils.isEmpty(orderFlow)){
                    /*日期+1*/
                    tradeDate=DateUtil.getFutureDate(tradeDate,1);
                    continue;
                }
                /*日交易订单汇总*/
                orderSumAnalysis(userId,tradeDate,serverName, mtAccId,orderFlow);
                /*日期+1*/
                tradeDate=DateUtil.getFutureDate(tradeDate,1);
            }catch (Exception e){
                log.error("初始化订单交易分析数据异常=============");
                log.error(e.getMessage(),e);
            }
        }

    }

    /**
     * 用户订单分析
     * @param userId
     * @param tradeDate
     * @param serverName
     * @param mtAccId
     * @param orderFlow
     * @return
     */
    @Transactional
    public FuReportOrderSum orderSumAnalysis(Integer userId, Date tradeDate, String serverName, Integer mtAccId, FuReportOrderFlow orderFlow){
        if(userId==null||userId==0
                ||tradeDate==null|| StringUtils.isEmpty(serverName)
                ||mtAccId==null||mtAccId==0){
            log.error("用户订单分析 用户数据为空！");
            throw new DataConflictException("用户订单分析 用户数据为空！");
        }
        if(ObjectUtils.isEmpty(orderFlow)){
            Wrapper<FuReportOrderFlow> orderFlowWrapper=new EntityWrapper<>();
            orderFlowWrapper.eq(FuReportOrderFlow.USER_ID,userId);
            orderFlowWrapper.eq(FuReportOrderFlow.MT_ACC_ID,mtAccId);
            orderFlowWrapper.eq(FuReportOrderFlow.TRADE_DATE, DateUtil.toDateString(tradeDate));
            orderFlow=fuReportOrderFlowService.selectOne(orderFlowWrapper);
            if(orderFlow==null||orderFlow.getId()==null||orderFlow.getId()==0){
                log.error("根据条件查询订单结算流水错误！");
                throw new BusinessException("用户订单分析 用户数据为空！");
            }
        }

        boolean isFist=false;
        FuReportOrderSum orderSum=new FuReportOrderSum();
        orderSum.setUserId(userId);
        orderSum.setMtAccId(String.valueOf(mtAccId));
        /*总持仓时间*/
        BigDecimal orderHoldSum=new BigDecimal(0);

        orderSum=fuReportOrderSumMapper.selectOne(orderSum);
        if(ObjectUtils.isEmpty(orderSum)){
            //初始化
            isFist=true;
            orderSum=new FuReportOrderSum();
            orderSum.setUserId(userId);
            orderSum.setMtAccId(String.valueOf(mtAccId));
            orderSum.setServerName(serverName);
            orderSum.setBeginDate(tradeDate);

            orderSum.setTradeDaySum(1);
            orderSum.setOrderLots(orderFlow.getOrderLots());
            orderSum.setOrderCount(orderFlow.getOrderCount());

            /*盈利金额（历史盈利金额）*/
            orderSum.setOrderProfit(orderFlow.getOrderProfit());
            /*盈利手数（历史盈利手数）*/
            orderSum.setOrderProfitLots(orderFlow.getOrderProfitLots());
            /*盈利单数（历史盈利单数）*/
            orderSum.setOrderProfitCount(orderFlow.getOrderProfitCount());
            /*单笔最大获利*/
            orderSum.setOrderProfitMax(orderFlow.getOrderProfitMax());
            /*日连续最大获利天*/
            orderSum.setOrderProfitKeepCount(orderFlow.getOrderProfitKeepCount());
            /*日连续最大获利天-金额*/
            orderSum.setOrderProfitKeep(orderFlow.getOrderProfitKeep());
            /*日连续最大获利-最后时间*/
            orderSum.setOrderProfitKeepDay(orderFlow.getTradeDate());

            /*亏损金额（历史亏损金额*/
            orderSum.setOrderLoss(orderFlow.getOrderLoss());
            /*亏损手数（历史亏损手数）*/
            orderSum.setOrderLossLots(orderFlow.getOrderLossLots());
            /*亏损单数（历史亏损单数）*/
            orderSum.setOrderLossCount(orderFlow.getOrderLossCount());
            /*单笔最大亏损*/
            orderSum.setOrderLossMax(orderFlow.getOrderLossMax());

            /*日连续最大亏损利天*/
            orderSum.setOrderLossKeepCount(orderFlow.getOrderLossKeepCount());
            /*日连续最大亏损天-金额*/
            orderSum.setOrderLossKeep(orderFlow.getOrderLossKeep());
            /*日连续最大亏损-最后时间*/
            orderSum.setOrderLossKeepDay(orderFlow.getTradeDate());

            /*最大交易手数*/
            orderSum.setOrderLotsMax(orderFlow.getOrderLotsMax());
            /*最大交易金额*/
            orderSum.setOrderMoneyMax(orderFlow.getOrderMoneyMax());
            /*最大持仓时间*/
            orderSum.setOrderHoldTimeMax(orderFlow.getOrderHoldTimeMax());

            /*累计手续费*/
            orderSum.setOrderSwap(orderFlow.getOrderSwap());
            /*累计隔夜利息*/
            orderSum.setOrderCommission(orderFlow.getOrderCommission());
            /*累计收益*/
            orderSum.setOrderIncome(orderFlow.getOrderIncome());
            /*累计收益率(日交易的累计收益率)*/
            orderSum.setOrderIncomeRate(orderFlow.getOrderIncomeRate());

        } else {

            orderSum.setOrderLots(orderSum.getOrderLots().add(orderFlow.getOrderLots()));
            orderSum.setOrderCount(orderSum.getOrderCount()+orderFlow.getOrderCount());

            orderHoldSum= orderSum.getOrderHoldTimeAvg().multiply(new BigDecimal(orderSum.getOrderCount())).add(orderFlow.getOrderHoldTimeAvg().multiply(new BigDecimal(orderFlow.getOrderCount())));
            orderSum.setTradeDaySum(orderSum.getTradeDaySum()+1);
            /*盈利金额（历史盈利金额）*/
            orderSum.setOrderProfit(orderSum.getOrderProfit().add(orderFlow.getOrderProfit()));
            /*盈利手数（历史盈利手数）*/
            orderSum.setOrderProfitLots(orderSum.getOrderProfitLots().add(orderFlow.getOrderProfitLots()));
            /*盈利单数（历史盈利单数）*/
            orderSum.setOrderProfitCount(orderSum.getOrderProfitCount()+orderFlow.getOrderProfitCount());
            /*单笔最大获利*/
            if(orderFlow.getOrderProfitMax().compareTo(orderSum.getOrderProfitMax())>0){
                orderSum.setOrderProfitMax(orderFlow.getOrderProfitMax());
            }
            /*日连续最大获利天*/
            if(orderFlow.getOrderProfitKeepCount()>orderSum.getOrderProfitKeepCount()){
                orderSum.setOrderProfitKeepCount(orderFlow.getOrderProfitKeepCount());
                /*日连续最大获利天-金额*/
                orderSum.setOrderProfitKeep(orderFlow.getOrderProfitKeep());
                /*日连续最大获利-最后时间*/
                orderSum.setOrderProfitKeepDay(orderFlow.getTradeDate());
            }

            /*亏损金额（历史亏损金额*/
            orderSum.setOrderLoss(orderSum.getOrderLoss().add(orderFlow.getOrderLoss()));
            /*亏损手数（历史亏损手数）*/
            orderSum.setOrderLossLots(orderSum.getOrderLossLots().add(orderFlow.getOrderLossLots()));
            /*亏损单数（历史亏损单数）*/
            orderSum.setOrderLossCount(orderSum.getOrderLossCount()+orderFlow.getOrderLossCount());
            /*单笔最大亏损*/
            if(orderFlow.getOrderLossMax().compareTo(orderSum.getOrderLossMax())<0){
                orderSum.setOrderLossMax(orderFlow.getOrderLossMax());
            }
            /*日连续最大亏损利天*/
            if(orderFlow.getOrderLossKeepCount()>orderSum.getOrderLossKeepCount()){
                orderSum.setOrderLossKeepCount(orderFlow.getOrderLossKeepCount());
                /*日连续最大亏损天-金额*/
                orderSum.setOrderLossKeep(orderFlow.getOrderLossKeep());
                /*日连续最大亏损-最后时间*/
                orderSum.setOrderLossKeepDay(orderFlow.getTradeDate());
            }

            /*最大交易手数*/
            if(orderFlow.getOrderLotsMax().compareTo(orderSum.getOrderLotsMax())>0){
                orderSum.setOrderLotsMax(orderFlow.getOrderLotsMax());
            }
            /*最大交易金额*/
            if(orderFlow.getOrderMoneyMax().compareTo(orderSum.getOrderMoneyMax())>0){
                orderSum.setOrderMoneyMax(orderFlow.getOrderMoneyMax());
            }
            /*最大持仓时间*/
            if(orderFlow.getOrderHoldTimeMax().compareTo(orderSum.getOrderHoldTimeMax())>0){
                orderSum.setOrderHoldTimeMax(orderFlow.getOrderHoldTimeMax());
            }
            /*累计手续费*/
            orderSum.setOrderSwap(orderSum.getOrderSwap().add(orderFlow.getOrderSwap()));
            /*累计隔夜利息*/
            orderSum.setOrderCommission(orderSum.getOrderCommission().add(orderFlow.getOrderCommission()));
            /*累计收益*/
            orderSum.setOrderIncome(orderSum.getOrderIncome().add(orderFlow.getOrderIncome()));
            /*累计收益率(日交易的累计收益率)*/
            orderSum.setOrderIncomeRate(orderSum.getOrderIncomeRate().add(orderFlow.getOrderIncomeRate()));
        }

        /*日均交易手数（该账户交易至今每天的交易标准手数数，扣除节假日*/
        orderSum.setOrderLotsDaily(orderSum.getOrderLots().divide(new BigDecimal(orderSum.getTradeDaySum()),AccountConstant.BigDecimal_Scale, BigDecimal.ROUND_HALF_UP));
        /*日均交易单数（该账户交易至今每天的交易笔数，扣除节假日）*/
        orderSum.setOrderCountDaily(new BigDecimal(orderSum.getOrderCount()).divide(new BigDecimal(orderSum.getTradeDaySum()),AccountConstant.BigDecimal_Scale, BigDecimal.ROUND_HALF_UP));


        if(orderSum.getOrderProfitCount()>0){
            /*每笔平均盈利(盈利金额/盈利交易笔数)*/
            orderSum.setOrderProfitAvg(orderSum.getOrderProfit().divide(new BigDecimal(orderSum.getOrderProfitCount()),AccountConstant.BigDecimal_Scale, BigDecimal.ROUND_HALF_UP));
        }
        if(orderSum.getOrderLossCount()>0){
            /*每笔平均亏损(亏损金额/亏损交易笔数)*/
            orderSum.setOrderLossAvg(orderSum.getOrderLoss().divide(new BigDecimal(orderSum.getOrderLossCount()),AccountConstant.BigDecimal_Scale, BigDecimal.ROUND_HALF_UP));
        }
        /*盈利手数占比（盈利手数/总手数）*/
        orderSum.setOrderProfitRate(orderSum.getOrderProfitLots().divide(orderSum.getOrderLots(),AccountConstant.BigDecimal_Scale, BigDecimal.ROUND_HALF_UP));

        /*亏损手数占比（亏损手数/总手数）*/
        orderSum.setOrderLossRate(orderSum.getOrderLossLots().divide(orderSum.getOrderLots(),AccountConstant.BigDecimal_Scale, BigDecimal.ROUND_HALF_UP));

        /*平均持仓时间*/
        orderSum.setOrderHoldTimeAvg(orderHoldSum.divide(new BigDecimal(orderSum.getOrderCount()),AccountConstant.BigDecimal_Scale, BigDecimal.ROUND_HALF_UP));
        /*交易胜率(盈利笔数在总笔数中的占比，数据值越大，代表盈利订单占比越高)*/
        orderSum.setOrderWinRate(new BigDecimal(orderSum.getOrderProfitCount()).divide(new BigDecimal(orderSum.getOrderCount()),AccountConstant.BigDecimal_Scale, BigDecimal.ROUND_HALF_UP));
        if(orderSum.getOrderLoss().compareTo(new BigDecimal(0))!=0){
            /*净值盈亏比（盈利订单总额与亏损订单总额的比值，该值越大表明该账户当前的绩效结果越好）*/
            orderSum.setOrderPlRate(orderSum.getOrderProfit().divide(orderSum.getOrderLoss().abs(),AccountConstant.BigDecimal_Scale, BigDecimal.ROUND_HALF_UP));
        }

        /*预期回报（每笔交易平均利润）*/
        orderSum.setOrderExpectedReturn(orderSum.getOrderIncome().divide(new BigDecimal(orderSum.getOrderCount()),AccountConstant.BigDecimal_Scale, BigDecimal.ROUND_HALF_UP));;

        if(isFist){
            fuReportOrderSumMapper.insertSelective(orderSum);
        }else {
            fuReportOrderSumMapper.updateByPrimaryKeySelective(orderSum);
        }
        return orderSum;
    }

    /**
     * 根据条件查询用户交易订单汇总信息
     * @param userId
     * @param mtAccId
     * @return
     */
    public FuReportOrderSum getOrderSum(Integer userId,String mtAccId){
        if(userId==null||userId==0||StringUtils.isEmpty(mtAccId)){
            log.error("根据条件查询用户交易订单汇总信息失败，传入参数为空！");
            throw new DataConflictException("根据条件查询用户交易订单汇总信息失败，传入参数为空！");
        }
        Wrapper<FuReportOrderSum> orderSumWrapper=new EntityWrapper<>();
        orderSumWrapper.eq(FuReportOrderSum.USER_ID,userId);
        orderSumWrapper.eq(FuReportOrderSum.MT_ACC_ID,mtAccId);

        FuReportOrderSum orderSum=selectOne(orderSumWrapper);
        return orderSum;
    }


    /**
     * 查找信号源订单结算汇总信息
     * @param condition
     * @param helper
     * @return
     */
    public Page<FuReportSignalVO> querySignalOrderSumPermit(Map condition, PageInfoHelper helper){

        if(condition==null){
            log.error("查找信号源订单结算汇总信息 参数为空！");
            throw new DataConflictException("查找信号源订单结算汇总信息 参数为空！");
        }

        if(helper==null){
            helper=new PageInfoHelper();
        }
        Page<FuReportSignalVO> flows= PageHelper.startPage(helper.getPageNo(),helper.getPageSize());
        fuReportOrderSumMapper.querySignalOrderSumPermit(condition);
        return flows;
    }

    /**
     * 查找跟随者订单结算汇总信息
     * @param conditionMap
     * @param helper
     * @return
     */
    public Page<FuReportFollowVO> queryFollowOrderSumPermit(Map conditionMap, PageInfoHelper helper){

        if(conditionMap==null){
            log.error("查找跟随者订单结算汇总信息 参数为空！");
            throw new DataConflictException("查找跟随者订单结算汇总信息 参数为空！");
        }
        if(!ObjectUtils.isEmpty(conditionMap.get("beginDate"))){
            if(String.valueOf(conditionMap.get("beginDate")).indexOf(",")<0){
                conditionMap.put(FuReportOrderSum.BEGIN_DATE,conditionMap.get("beginDate"));
            }else {
                //时间段
                String[] dateClose=String.valueOf(conditionMap.get("beginDate")).split(",");
                if(dateClose.length!=2){
                    log.error("创建时间段数据传入错误！"+conditionMap.get("beginDate"));
                    throw new DataConflictException(GlobalResultCode.PARAM_VERIFY_ERROR,"创建时间段数据传入错误！"+conditionMap.get("beginDate"));
                }
                conditionMap.put("beginDateBegin",dateClose[0]);
                conditionMap.put("beginDateEnd",dateClose[1]);
            }
        }
        if(helper==null){
            helper=new PageInfoHelper();
        }
        Page<FuReportFollowVO> flows= PageHelper.startPage(helper.getPageNo(),helper.getPageSize());
        fuReportOrderSumMapper.queryFollowOrderSumPermit(conditionMap);
        return flows;
    }
}
