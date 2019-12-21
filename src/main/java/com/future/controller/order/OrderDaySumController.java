package com.future.controller.order;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.plugins.Page;
import com.future.common.util.ThreadCache;
import com.future.entity.order.FuOrderDaysum;
import com.future.entity.order.FuOrderSignal;
import com.future.service.order.FuOrderDaysumService;
import com.future.service.order.FuOrderSignalService;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orderDaysum")
public class OrderDaySumController {

    Logger log= LoggerFactory.getLogger(OrderDaySumController.class);

    @Autowired
    FuOrderDaysumService fuOrderDaysumService;

    //获得信号源订单操作
    @RequestMapping(value= "/getSignalOrderDaySum",method=RequestMethod.POST)
    public @ResponseBody
    PageInfo<FuOrderDaysum> getSignalOrderDaySum(){
        // 获取请求参数
        String requestJSONStr = ThreadCache.getPostRequestParams();
        JSONObject requestMap = JSONObject.parseObject(requestJSONStr);
        return fuOrderDaysumService.getSignalOrderDaySum(requestMap.getInnerMap());
    }

}