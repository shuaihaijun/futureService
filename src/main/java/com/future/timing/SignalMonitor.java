package com.future.timing;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.future.common.helper.PageInfoHelper;
import com.future.common.util.DateUtil;
import com.future.entity.product.FuProductSignal;
import com.future.entity.report.FuReportOrderFlow;
import com.future.service.order.FuOrderSignalService;
import com.future.service.product.FuProductSignalService;
import com.future.service.report.FuReportOrderFlowService;
import com.future.service.report.FuReportOrderSumService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;


/**
 * @author ：haijun
 * @date ：Created in 2019-10-17 15:17
 * @description：信号源定时任务
 * @modified By：
 * @version: 1/0$
 */
@Configuration
@EnableScheduling
public class SignalMonitor {

    Logger log= LoggerFactory.getLogger(SignalMonitor.class);

    @Autowired
    FuProductSignalService fuProductSignalService;
    @Autowired
    FuReportOrderFlowService fuReportOrderFlowService;
    @Autowired
    FuOrderSignalService fuOrderSignalService;
    @Autowired
    FuReportOrderSumService fuReportOrderSumService;

    /**
     * 信号源初始化监测 每小时同步一次
     */
    @Scheduled(cron = "0 0 * * * ?")
    public void signalInitMonitor(){


        String toDayString=DateUtil.toDateString(new Date());
        Date today=DateUtil.toDate(toDayString);
        /*考虑到零点 零界点创建的信号源会错过*/
        Date oneDay=DateUtil.getFutureDate(new Date(),-1);
        /*参考时间，如果有前期的 日结流水数据 就认为已经初始化过了*/
        Date referDate=DateUtil.getFutureDate(today,-2);

        /*查询1天内新申请的信号源*/
        PageInfoHelper helper=new PageInfoHelper();
        Wrapper<FuProductSignal> wrapper=new EntityWrapper<>();
        wrapper.ge(FuProductSignal.CREATE_DATE, oneDay);
        Page<FuProductSignal> signals=PageHelper.startPage(helper.getPageNo(),helper.getPageSize());
        fuProductSignalService.selectList(wrapper);

        log.error("定时任务------初始化信号源订单----------size:"+signals.getResult().size());

        Wrapper<FuReportOrderFlow> flowWrapper=new EntityWrapper<>();
        FuReportOrderFlow orderFlow=new FuReportOrderFlow();
        for(FuProductSignal signal:signals){
            /*根据两天以前的结算数据  来判断是否已初始化*/
            flowWrapper.eq(FuReportOrderFlow.USER_ID,signal.getUserId());
            flowWrapper.eq(FuReportOrderFlow.MT_ACC_ID,signal.getMtAccId());
            flowWrapper.lt(FuReportOrderFlow.TRADE_DATE,referDate);
            flowWrapper.last("limit 1");
            orderFlow=fuReportOrderFlowService.selectOne(flowWrapper);
            if(orderFlow!=null){
                continue;
            }
            try {
                log.info("定时任务------初始化信号源订单----------同步信号源历史交易数据:");
                /*1 同步信号源历史交易数据*/
                fuOrderSignalService.initSignalHistoryOrder(signal.getUserId(),signal.getServerName(),signal.getMtAccId(),null,new Date());
                log.info("定时任务------初始化信号源订单----------根据历史交易数据 分析:");
                /*2 根据历史交易数据 分析*/
                fuReportOrderSumService.orderSumStatisticsInit(signal.getUserId(),signal.getServerName(), Integer.valueOf(signal.getMtAccId()));
            }catch (Exception e){
                log.error("定时任务------初始化信号源订单失败----------");
                log.error(e.getMessage(),e);
            }
        }
    }
}
