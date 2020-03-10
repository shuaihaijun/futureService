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
    @Scheduled(cron = "0 * * * * ?")
    public void monitor(){

        /*1、同步跟单成功订单*/
        long orderSize = redisManager.lGetListSize(RedisConstant.L_ORDER_FOLLOW_ORDERS);
        if(orderSize>0){
            JSONObject followOrder=new JSONObject();
            int followName=0;
            int signalName=0;
            int signalOrderId=0;
            int orderAction=0;
            List<Object> orderInfos=redisManager.lGet(RedisConstant.L_ORDER_FOLLOW_ORDERS,0,50);
            while (orderInfos.size()>0){
                for(Object orderInfo:orderInfos){
                    try {
                        JSONObject orderJson=(JSONObject)orderInfo;
                        log.info("同步跟单订单数据------");
                        log.info(orderJson.toJSONString());
                        followOrder=orderJson.getJSONObject("followOrder");
                        followName=orderJson.getInteger("followName");
                        signalName=orderJson.getInteger("signalName");
                        signalOrderId=orderJson.getInteger("signalOrderId");
                        orderAction=orderJson.getInteger("signalOrderId");
                        //TODO redis 暂存数据
                        fuOrderFollowInfoService.saveFollowOrderData(followName,signalName,signalOrderId,orderAction,followOrder);
                        //TODO redis 删除暂存数据
                    }catch (Exception e){
                        log.error("同步跟单订单失败，订单："+JSONObject.toJSONString(orderInfo));
                        log.error(e.getMessage(),e);
                    }
                }
                orderInfos.clear();
                orderInfos=redisManager.lGet(RedisConstant.L_ORDER_FOLLOW_ORDERS,0,50);
            }
        }

        /*2、同步跟单异常订单*/
        long errorSize = redisManager.lGetListSize(RedisConstant.L_ORDER_FOLLOW_ERROR_DATA);
        if(errorSize>0){
            JSONObject order=new JSONObject();
            JSONObject followRule=new JSONObject();
            int errorCode=0;
            int updateAction=0;
            List<Object> errorInfos=redisManager.lGet(RedisConstant.L_ORDER_FOLLOW_ORDERS,0,50);
            while (errorInfos.size()>0){
                for(Object errorInfo:errorInfos){
                    try {
                        JSONObject errorData=(JSONObject)errorInfo;
                        log.info("同步跟单错误数据------");
                        log.info(errorData.toJSONString());
                        updateAction=errorData.getInteger("updateAction");
                        order=errorData.getJSONObject("order");
                        followRule=errorData.getJSONObject("followRule");
                        errorCode=errorData.getInteger("errorCode");
                        //TODO redis 暂存数据
                        fuOrderFollowErrorService.dealFollowErrorData(updateAction,order,followRule,errorCode);
                        //TODO redis 删除暂存数据
                    }catch (Exception e){
                        log.error("同步跟单错误数据失败，订单："+JSONObject.toJSONString(errorInfo));
                        log.error(e.getMessage(),e);
                    }
                }
                errorInfos.clear();
                errorInfos=redisManager.lGet(RedisConstant.L_ORDER_FOLLOW_ERROR_DATA,0,50);
            }
        }
    }
}
