package com.future.timing;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.future.common.constants.CommonConstant;
import com.future.common.helper.PageInfoHelper;
import com.future.common.util.DateUtil;
import com.future.entity.account.FuAccountMt;
import com.future.entity.report.FuReportOrderFlow;
import com.future.service.account.FuAccountMtService;
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
import org.springframework.util.ObjectUtils;

import java.util.Date;


/**
 * @author ：haijun
 * @date ：Created in 2019-10-17 15:17
 * @description：用户订单统计报表
 * @modified By：
 * @version: 1/0$
 */
@Configuration
@EnableScheduling
public class ReportOrderMonitor {

    Logger log= LoggerFactory.getLogger(ReportOrderMonitor.class);

    @Autowired
    FuAccountMtService fuAccountMtService;
    @Autowired
    FuReportOrderFlowService fuReportOrderFlowService;
    @Autowired
    FuReportOrderSumService reportOrderSumService;

    /**
     * 订单报表分析 每天同步一次
     */
    @Scheduled(cron = "0 0 5 * * ?")
    public void reportOrderAnalysis(){

        Date tradeDate= DateUtil.getFutureDate(new Date(),-1);
        PageInfoHelper helper=new PageInfoHelper();
        Wrapper<FuAccountMt> wrapper=new EntityWrapper<>();

        FuReportOrderFlow orderFlow=new FuReportOrderFlow();

        wrapper.eq(FuAccountMt.PASSWORD_WATCH_CHECKED, CommonConstant.CHECK_YES);
        Page<FuAccountMt> accountMts=PageHelper.startPage(helper.getPageNo(),helper.getPageSize());
        fuAccountMtService.selectList(wrapper);

        while (accountMts!=null&&accountMts.size()>0) {
            for (FuAccountMt accountMt : accountMts) {
                try {
                    /*计算需要分析的天数差 (以防 之前有漏掉的日期未分析)*/
                    int days= fuReportOrderFlowService.orderFlowDateCaculate(accountMt.getUserId(), tradeDate, Integer.parseInt(accountMt.getMtAccId()));
                    if(days<0){
                        log.info("用户订单分析,时间段内没有交易记录，startTime:"+DateUtil.toDateString(tradeDate));
                        continue;
                    }
                    /*循环 需要分析的日期*/
                    for(int i=days;i>=0;i--){
                        Date needTradeDate=  DateUtil.getFutureDate(tradeDate,-days);

                        /*日交易订单流水*/
                        orderFlow = fuReportOrderFlowService.orderFlowAnalysis(accountMt.getUserId(), needTradeDate, accountMt.getServerName(), Integer.parseInt(accountMt.getMtAccId()));
                        if (ObjectUtils.isEmpty(orderFlow)) {
                            continue;
                        }
                        /*日交易订单汇总*/
                        reportOrderSumService.orderSumAnalysis(accountMt.getUserId(), needTradeDate, accountMt.getServerName(), Integer.parseInt(accountMt.getMtAccId()), orderFlow);
                    }

                } catch (Exception e) {
                    log.error("订单报表分析 失败，userId:" + accountMt.getUserId() + ",mtAccId:" + accountMt.getMtAccId());
                    log.error(e.getMessage(), e);
                }
            }
            /*翻页*/
            helper.setPageNo(helper.getPageNo()+1);
            accountMts=PageHelper.startPage(helper.getPageNo(),helper.getPageSize());
            fuAccountMtService.selectList(wrapper);
        }
    }
}
