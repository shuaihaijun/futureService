package com.future.timing;

import com.alibaba.fastjson.JSONObject;
import com.future.common.constants.RedisConstant;
import com.future.common.util.RedisManager;
import com.future.service.order.FuOrderFollowErrorService;
import com.future.service.order.FuOrderFollowInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

/**
 * @author ：haijun
 * @date ：Created in 2019-10-17 15:17
 * @description：用户跟单订单定时同步
 * @modified By：
 * @version: 1/0$
 */
@Configuration
@EnableScheduling
public class FollowOrderMonitor {

    Logger log= LoggerFactory.getLogger(FollowOrderMonitor.class);
    @Autowired
    RedisManager redisManager;
    @Autowired
    FuOrderFollowInfoService fuOrderFollowInfoService;
    @Autowired
    FuOrderFollowErrorService fuOrderFollowErrorService;

    /**
     * 跟单监听 每分钟同步一次
     */
    @Scheduled(cron = "0/30 * * * * ?")
    public void monitor(){

        /*1、同步跟单成功订单*/
        long orderSize = redisManager.lGetListSize(RedisConstant.L_ORDER_FOLLOW_ORDERS);
        if(orderSize>0){
            JSONObject followOrder=new JSONObject();
            int followName=0;
            int signalName=0;
            int signalOrderId=0;
            int orderAction=0;
            Object orderInfo=redisManager.lPop(RedisConstant.L_ORDER_FOLLOW_ORDERS);
            while (orderInfo!=null){
                try {
                    log.info("同步跟单订单数据------");
                    JSONObject orderJson= (JSONObject)orderInfo;
                    log.info(orderJson.toJSONString());
                    followOrder=orderJson.getJSONObject("followOrder");
                    followName=orderJson.getInteger("followName");
                    signalName=orderJson.getInteger("signalName");
                    signalOrderId=orderJson.getInteger("signalOrderId");
                    orderAction=orderJson.getInteger("orderAction");
                    boolean isSuccess= fuOrderFollowInfoService.saveFollowOrderData(followName,signalName,signalOrderId,orderAction,followOrder);
                    if(!isSuccess){
                        log.error("同步跟单订单失败，订单："+JSONObject.toJSONString(orderInfo));
                        //redis 暂存处理失败数据(暂时未做处理 需要实例化)
                        redisManager.lSet(RedisConstant.L_ORDER_FOLLOW_ORDERS_BAK,orderInfo);
                    }
                }catch (Exception e){
                    log.error("同步跟单订单失败，订单："+JSONObject.toJSONString(orderInfo));
                    //redis 暂存处理失败数据(暂时未做处理 需要实例化)
                    redisManager.lSet(RedisConstant.L_ORDER_FOLLOW_ORDERS_BAK,orderInfo);
                    log.error(e.getMessage(),e);
                }
                orderInfo=redisManager.lPop(RedisConstant.L_ORDER_FOLLOW_ORDERS);
            }
        }

        /*2、同步跟单异常订单*/
        long errorSize = redisManager.lGetListSize(RedisConstant.L_ORDER_FOLLOW_ERROR_DATA);
        if(errorSize>0){
            JSONObject order=new JSONObject();
            JSONObject followRule=new JSONObject();
            int errorCode=1;
            int updateAction=0;
            Object errorInfo=redisManager.lPop(RedisConstant.L_ORDER_FOLLOW_ERROR_DATA);
            while (errorInfo!=null){
                try {
                    log.info("同步跟单错误数据------");
                    JSONObject errorData=(JSONObject)errorInfo;
                    log.info(errorData.toJSONString());
                    if(errorData.get("updateAction")!=null){
                        updateAction=errorData.getInteger("updateAction");
                    }
                    if(errorData.get("errorCode")!=null){
                        errorCode=errorData.getInteger("errorCode");
                    }
                    order=errorData.getJSONObject("order");
                    followRule=errorData.getJSONObject("followRule");
                    boolean isSuccess=fuOrderFollowErrorService.dealFollowErrorData(updateAction,order,followRule,errorCode);
                    if(!isSuccess){
                        log.error("同步跟单订单失败，订单："+JSONObject.toJSONString(errorInfo));
                        //redis 暂存处理失败数据(暂时未做处理 需要实例化)
                        redisManager.lSet(RedisConstant.L_ORDER_FOLLOW_ORDERS_BAK,errorInfo);
                    }
                }catch (Exception e){
                    log.error("同步跟单错误数据失败，订单："+JSONObject.toJSONString(errorInfo));
                    log.error(e.getMessage(),e);
                    //redis 暂存处理失败数据(暂时未做处理 需要实例化)
                    redisManager.lSet(RedisConstant.L_ORDER_FOLLOW_ERROR_DATA_BAK,errorInfo);
                }
                errorInfo=redisManager.lPop(RedisConstant.L_ORDER_FOLLOW_ERROR_DATA);
            }
        }
    }
}
