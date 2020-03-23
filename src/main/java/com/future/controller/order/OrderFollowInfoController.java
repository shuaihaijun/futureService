package com.future.controller.order;

import com.alibaba.fastjson.JSONObject;
import com.future.common.enums.GlobalResultCode;
import com.future.common.exception.DataConflictException;
import com.future.common.helper.PageInfoHelper;
import com.future.common.result.RequestParams;
import com.future.common.util.DateUtil;
import com.future.common.util.StringUtils;
import com.future.common.util.ThreadCache;
import com.future.entity.order.FuOrderFollowError;
import com.future.entity.order.FuOrderFollowInfo;
import com.future.service.order.FuOrderFollowErrorService;
import com.future.service.order.FuOrderFollowInfoService;
import com.github.pagehelper.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/orderFollowInfo")
public class OrderFollowInfoController {
    Logger log= LoggerFactory.getLogger(OrderFollowInfoController.class);

    @Autowired
    FuOrderFollowInfoService orderService;
    @Autowired
    FuOrderFollowErrorService fuOrderFollowErrorService;

    //获得Mt历史订单操作
    @RequestMapping(value= "/getMTtHistoryOrders",method=RequestMethod.POST)
    public @ResponseBody
    List<FuOrderFollowInfo> getMTtHistoryOrders(){
        // 获取请求参数
        String requestJSONStr = ThreadCache.getPostRequestParams();
        JSONObject requestMap = JSONObject.parseObject(requestJSONStr);
        String userId=requestMap.getString("userId");
        String username=requestMap.getString("username");
        String accountId=requestMap.getString("accountId");
        String dateFrom=requestMap.getString("dateFrom");
        String dataTo=requestMap.getString("dataTo");
        if(StringUtils.isEmpty(dateFrom)){
            new DataConflictException(GlobalResultCode.PARAM_NULL_POINTER,"日期数据为空！");
        }
        if(StringUtils.isEmpty(dataTo)){
            new DataConflictException(GlobalResultCode.PARAM_NULL_POINTER,"日期数据为空！");
        }
        /*条件查询日期不能超过1周*/
        return orderService.geMTtHistoryOrders(userId,username,accountId, DateUtil.toDate(dateFrom),DateUtil.toDate(dataTo));
    }

    //获得社区跟随订单
    @RequestMapping(value= "/getOrderFollowInfo",method=RequestMethod.POST)
    public @ResponseBody
    Page<FuOrderFollowInfo> getOrderFollowInfo(@RequestBody RequestParams<Map> requestParams) {
        Map requestMap = requestParams.getParams();
        PageInfoHelper helper = requestParams.getPageInfoHelper();
        return orderService.queryOrderFollowInfo(requestMap, helper);
    }

    //获得社区跟随异常
    @RequestMapping(value= "/queryOrderFollowError",method=RequestMethod.POST)
    public @ResponseBody
    Page<FuOrderFollowError> queryOrderFollowError(@RequestBody RequestParams<Map> requestParams) {
        Map orderCondition = requestParams.getParams();
        PageInfoHelper helper = requestParams.getPageInfoHelper();
        return fuOrderFollowErrorService.queryOrderFollowError(orderCondition,helper);
    }
}