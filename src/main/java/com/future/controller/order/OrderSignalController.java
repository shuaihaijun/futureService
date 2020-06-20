package com.future.controller.order;

import com.future.common.helper.PageInfoHelper;
import com.future.common.result.RequestParams;
import com.future.entity.order.FuOrderSignal;
import com.future.service.order.FuOrderSignalService;
import com.github.pagehelper.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/orderSignal")
public class OrderSignalController {

    Logger log= LoggerFactory.getLogger(OrderSignalController.class);

    @Autowired
    FuOrderSignalService fuOrderSignalService;

    //获得信号源订单操作
    @RequestMapping(value= "/querySignalOrder",method=RequestMethod.POST)
    public @ResponseBody
    Page<FuOrderSignal> querySignalOrder(@RequestBody RequestParams<Map> requestParams) {
        // 获取请求参数
        Map requestMap = requestParams.getParams();
        PageInfoHelper helper = requestParams.getPageInfoHelper();
        return fuOrderSignalService.querySignalOrder(requestMap,helper);
    }

    //用户查询信号源订单
    @RequestMapping(value= "/queryUserSignalOrder",method=RequestMethod.POST)
    public @ResponseBody
    Page<FuOrderSignal> queryUserSignalOrder(@RequestBody RequestParams<Map> requestParams) {
        // 获取请求参数
        Map requestMap = requestParams.getParams();
        PageInfoHelper helper = requestParams.getPageInfoHelper();
        return fuOrderSignalService.queryUserSignalOrder(requestMap,helper);
    }

    //根据时间段同步信号源订单
    @RequestMapping(value= "/synSignalHistoryOrder",method=RequestMethod.POST)
    public @ResponseBody boolean synSignalHistoryOrder(@RequestBody RequestParams<Map> requestParams) {
        // 获取请求参数
        Map requestMap = requestParams.getParams();
        return fuOrderSignalService.synSignalHistoryOrder(requestMap);
    }

}