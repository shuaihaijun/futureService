package com.future.controller.order;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.future.common.enums.GlobalResultCode;
import com.future.common.exception.DataConflictException;
import com.future.common.util.DateUtil;
import com.future.common.util.StringUtils;
import com.future.common.util.ThreadCache;
import com.future.entity.order.FuOrderCustomer;
import com.future.entity.order.FuOrderInfo;
import com.future.service.order.FuOrderInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {
    Logger log= LoggerFactory.getLogger(OrderController.class);

    @Autowired
    FuOrderInfoService orderService;

    //获得Mt历史订单操作
    @RequestMapping(value= "/getMTtHistoryOrders",method=RequestMethod.POST)
    public @ResponseBody
    List<FuOrderInfo> getMTtHistoryOrders(){
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
        return orderService.geMTtHistoryOrders(userId,username,accountId, DateUtil.toDate(dateFrom),DateUtil.toDate(dataTo),"");
    }
    //获得Mt在仓订单操作
    @RequestMapping(value= "/getMTAliveOrders",method=RequestMethod.POST)
    public @ResponseBody
    List<FuOrderInfo> getMTAliveOrders(){
        // 获取请求参数
        String requestJSONStr = ThreadCache.getPostRequestParams();
        JSONObject requestMap = JSONObject.parseObject(requestJSONStr);
        String accountId=requestMap.getString("accountId");
        String username=requestMap.getString("username");
        String userId=requestMap.getString("userId");
        String orderSymbol=requestMap.getString("orderSymbol");
        String orderOpenDate=requestMap.getString("orderOpenDate");
        String dateFrom="";
        String dataTo="";

        if(StringUtils.isEmpty(userId)&&StringUtils.isEmpty(username)){
            log.error("查询时 用户数据不能为空！");
            new DataConflictException(GlobalResultCode.PARAM_NULL_POINTER,"查询时 用户数据不能为空！");
        }
        if(StringUtils.isEmpty(orderOpenDate)){
            log.error("查询时间段不能为空！");
            new DataConflictException(GlobalResultCode.PARAM_NULL_POINTER,"查询时间段不能为空！");
        }
        if(requestMap.getString("orderOpenDate").indexOf(",")<0){
            log.error("查询时间段必须包含起、始时间！");
            new DataConflictException(GlobalResultCode.PARAM_NULL_POINTER,"查询时间段必须包含起、始时间！");
        }else {
            //时间段
            JSONArray dateOpen=requestMap.getJSONArray("orderOpenDate");
            if(dateOpen.size()!=2){
                log.error("建仓时间段数据传入错误！"+requestMap.getString("orderOpenDate"));
                throw new DataConflictException("建仓时间段数据传入错误！"+requestMap.getString("orderOpenDate"));
            }
            dateFrom=dateOpen.getString(0);
            dataTo=dateOpen.getString(1);
        }
        /*条件查询日期范围不能超过1周*/

        return orderService.getMTAliveOrders(userId,username,accountId, DateUtil.toDate(dateFrom),DateUtil.toDate(dataTo),orderSymbol);
    }

}