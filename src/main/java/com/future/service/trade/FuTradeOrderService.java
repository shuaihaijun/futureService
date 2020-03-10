package com.future.service.trade;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.future.common.constants.GlobalConstant;
import com.future.common.exception.DataConflictException;
import com.future.common.util.ConvertUtil;
import com.future.common.util.DateUtil;
import com.future.common.util.HttpUtils;
import com.future.common.util.RedisManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;

/**
 *  跟随者逻辑处理
 */
@Service
public class FuTradeOrderService {

    Logger log= LoggerFactory.getLogger(FuTradeOrderService.class);

    @Autowired
    RedisManager redisManager;

    @Value("${tradeServerHost}")
    public String tradeServerHost;
    @Value("${tradeServerPort}")
    public int tradeServerPort;

    /**
     * 根据时间段获取用户关闭订单（时间段不能超过1周）
     * @param serverName
     * @param mtAccId
     * @param password
     * @param nHisTimeFrom
     * @param nHisTimeTo
     */
    public JSONArray getUserCloseOrders(String serverName, int mtAccId, String password, long nHisTimeFrom, long nHisTimeTo){
        if(StringUtils.isEmpty(serverName)||mtAccId==0||StringUtils.isEmpty(password)){
            log.error("根据时间段获取用户关闭订单,传入参数为空！");
            throw new DataConflictException("根据时间段获取用户关闭订单,传入参数为空！");
        }
        long time = LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().getEpochSecond();
        if(nHisTimeFrom==0){
            // 由于时区原因，扩展为8天
            nHisTimeFrom= (int)DateUtil.getFutureDate(time,-7);
        }
        if(nHisTimeTo==0){
            // 由于时区原因，扩展为8天
            nHisTimeTo= (int)DateUtil.getFutureDate(time,1);
        }

        String url=tradeServerHost+":"+tradeServerPort+ GlobalConstant.TRADE_ORDER_CLOSE_ORDERS;
        Map dataMap=new HashMap();
        dataMap.put("serverName",serverName);
        dataMap.put("username",mtAccId);
        dataMap.put("password",password);
        dataMap.put("nHisTimeFrom",nHisTimeFrom);
        dataMap.put("nHisTimeTo",nHisTimeTo);
        Map requestMap= new HashMap<>();
        requestMap.put("params",dataMap);
        // 请求
        String result= HttpUtils.httpPost(url,requestMap);
        JSONObject resultJson=JSONObject.parseObject(result);
        JSONObject content=resultJson.getJSONObject("content");
        if(content==null||content.getJSONObject("data")==null){
            log.error(resultJson.getString("msg"));
            return null;
        }
        JSONArray data=content.getJSONArray("data");
        return data;
    }

    /**
     * 根据orderId获取用户关闭订单
     * @param serverName
     * @param mtAccId
     * @param password
     * @param orderId
     */
    public JSONObject getUserCloseOrder(String serverName, int mtAccId, String password, int orderId){
        if(StringUtils.isEmpty(serverName)||mtAccId==0||StringUtils.isEmpty(password)){
            log.error("获取用户关闭订单,传入参数为空！");
            throw new DataConflictException("获取用户关闭订单,传入参数为空！");
        }
        String url=tradeServerHost+":"+tradeServerPort+ GlobalConstant.TRADE_ORDER_CLOSE_ORDER;
        Map dataMap=new HashMap();
        dataMap.put("serverName",serverName);
        dataMap.put("username",mtAccId);
        dataMap.put("password",password);
        Map requestMap= new HashMap<>();
        requestMap.put("params",dataMap);
        // 请求
        String result= HttpUtils.httpPost(url,requestMap);
        JSONObject resultJson=JSONObject.parseObject(result);
        JSONObject content=resultJson.getJSONObject("content");
        if(content==null||content.getJSONObject("data")==null){
            log.error(resultJson.getString("msg"));
            return null;
        }
        JSONObject data=content.getJSONObject("data");
        return data;
    }

    /**
     * 获取用户open订单
     * @param serverName
     * @param mtAccId
     * @param password
     * @param nHisTimeFrom
     * @param nHisTimeTo
     * @return
     */
    public JSONArray getUserOpenOrders(String serverName, int mtAccId, String password, long nHisTimeFrom, long nHisTimeTo){
        if(StringUtils.isEmpty(serverName)||mtAccId==0||StringUtils.isEmpty(password)){
            log.error("获取用户open订单,传入参数为空！");
            throw new DataConflictException("获取用户open订单,传入参数为空！");
        }
        String url=tradeServerHost+":"+tradeServerPort+ GlobalConstant.TRADE_ORDER_OPEN_ORDERS;
        Map dataMap=new HashMap();
        dataMap.put("serverName",serverName);
        dataMap.put("username",mtAccId);
        dataMap.put("password",password);
        Map requestMap= new HashMap<>();
        requestMap.put("params",dataMap);
        // 请求
        String result= HttpUtils.httpPost(url,requestMap);
        JSONObject resultJson=JSONObject.parseObject(result);
        JSONObject content=resultJson.getJSONObject("content");
        if(content==null||content.getJSONObject("data")==null){
            log.error(resultJson.getString("msg"));
            return null;
        }
        JSONArray data=content.getJSONArray("data");
        return data;
    }

    /**
     * 根据orderId获取用户open订单
     * @param serverName
     * @param mtAccId
     * @param password
     * @param orderId
     */
    public JSONObject getUserOpenOrder(String serverName, int mtAccId, String password, int orderId){
        if(StringUtils.isEmpty(serverName)||mtAccId==0||StringUtils.isEmpty(password)){
            log.error("获取用户关闭订单,传入参数为空！");
            throw new DataConflictException("获取用户关闭订单,传入参数为空！");
        }
        String url=tradeServerHost+":"+tradeServerPort+ GlobalConstant.TRADE_ORDER_OPEN_ORDER;
        Map dataMap=new HashMap();
        dataMap.put("serverName",serverName);
        dataMap.put("username",mtAccId);
        dataMap.put("password",password);
        Map requestMap= new HashMap<>();
        requestMap.put("params",dataMap);
        // 请求
        String result= HttpUtils.httpPost(url,requestMap);
        JSONObject resultJson=JSONObject.parseObject(result);
        JSONObject content=resultJson.getJSONObject("content");
        if(content==null||content.getJSONObject("data")==null){
            log.error(resultJson.getString("msg"));
            return null;
        }
        JSONObject data=content.getJSONObject("data");
        return data;
    }

}
