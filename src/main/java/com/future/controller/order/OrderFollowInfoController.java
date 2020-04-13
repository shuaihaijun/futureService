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

    //获得Mt历史订单操作(直接从MT平台查询)
    @RequestMapping(value= "/getMTtHistoryOrders",method=RequestMethod.POST)
    @Deprecated
    public @ResponseBody
    List<FuOrderFollowInfo> getMTtHistoryOrders(@RequestBody RequestParams<Map> requestParams) {
        // 获取请求参数
        Map requestMap = requestParams.getParams();
        if(requestMap==null){
            throw new DataConflictException(GlobalResultCode.PARAM_NULL_POINTER);
        }
        String userId="";
        String username="";
        String mtAccId="";
        String dateFrom="";
        String dataTo="";
        if(requestMap.get("userId")!=null){
            userId=String.valueOf(requestMap.get("userId"));
        }
        if(requestMap.get("username")!=null){
            username=String.valueOf(requestMap.get("username"));
        }
        if(requestMap.get("mtAccId")!=null){
            mtAccId=String.valueOf(requestMap.get("mtAccId"));
        }
        if(requestMap.get("dateFrom")!=null){
            dateFrom=String.valueOf(requestMap.get("dateFrom"));
        }else {
            new DataConflictException(GlobalResultCode.PARAM_NULL_POINTER,"日期数据为空！");
        }
        if(requestMap.get("dataTo")!=null){
            dataTo=String.valueOf(requestMap.get("dataTo"));
        }else {
            new DataConflictException(GlobalResultCode.PARAM_NULL_POINTER,"日期数据为空！");
        }
        /*条件查询日期不能超过1周*/
        return orderService.geMTtHistoryOrders(userId,username,mtAccId, DateUtil.toDate(dateFrom),DateUtil.toDate(dataTo));
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