package com.future.controller.report;

import com.future.common.exception.DataConflictException;
import com.future.common.helper.PageInfoHelper;
import com.future.common.result.RequestParams;
import com.future.entity.report.FuReportOrderFlow;
import com.future.entity.report.FuReportOrderSum;
import com.future.service.report.FuReportOrderFlowService;
import com.future.service.report.FuReportOrderSumService;
import com.github.pagehelper.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/report/order")
public class ReportOrderController {
    Logger log = LoggerFactory.getLogger(ReportOrderController.class);

    @Autowired
    FuReportOrderSumService fuReportOrderSumService;
    @Autowired
    FuReportOrderFlowService fuReportOrderFlowService;

    //查找订单结算汇总信息
    @RequestMapping(value = "/getOrderSum", method = RequestMethod.POST)
    public @ResponseBody
    FuReportOrderSum getOrderSum(@RequestBody RequestParams<Map> requestParams) {
        // 获取请求参数
        Map conditionMap = requestParams.getParams();
        if (conditionMap == null || conditionMap.get("userId") == null || conditionMap.get("mtAccId") == null) {
            log.error("查找订单结算汇总信息失败，传入参数为空！");
            throw new DataConflictException("查找订单结算汇总信息失败，传入参数为空！");
        }
        // 获取请求参数
        String userId = String.valueOf(conditionMap.get("userId"));
        String mtAccId = String.valueOf(conditionMap.get("mtAccId"));

        /*条件查询日期不能超过1周*/
        return fuReportOrderSumService.getOrderSum(Integer.parseInt(userId), mtAccId);
    }

    //查找订单结算流水信息
    @RequestMapping(value = "/queryOrderFlow", method = RequestMethod.POST)
    public @ResponseBody
    Page<FuReportOrderFlow> queryOrderFlow(@RequestBody RequestParams<Map> requestParams) {
        // 获取请求参数
        Map conditionMap = requestParams.getParams();
        PageInfoHelper helper = requestParams.getPageInfoHelper();
        if (conditionMap == null || conditionMap.get("userId") == null || conditionMap.get("mtAccId") == null) {
            log.error("查找订单结算流水信息，传入参数为空！");
            throw new DataConflictException("查找订单结算流水信息，传入参数为空！");
        }
        // 获取请求参数
        String userId = String.valueOf(conditionMap.get("userId"));
        String mtAccId = String.valueOf(conditionMap.get("mtAccId"));
        return fuReportOrderFlowService.queryOrderFlow(Integer.valueOf(userId),mtAccId,conditionMap,helper);
    }

}