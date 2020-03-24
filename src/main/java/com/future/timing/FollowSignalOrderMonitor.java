package com.future.timing;

import com.alibaba.fastjson.JSONObject;
import com.future.common.constants.RedisConstant;
import com.future.common.util.RedisManager;
import com.future.service.order.FuOrderSignalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * @author ：haijun
 * @date ：Created in 2020-03-17 15:17
 * @description：用户跟单订单定时同步
 * @modified By：
 * @version: 1/0$
 */
@Configuration
@EnableScheduling
public class FollowSignalOrderMonitor {

    Logger log= LoggerFactory.getLogger(FollowSignalOrderMonitor.class);
    @Autowired
    RedisManager redisManager;
    @Autowired
    FuOrderSignalService fuOrderSignalService;

    /**
     * 信号源订单监听 每分钟同步一次
     */
    @Scheduled(cron = "0/30 * * * * ?")
    public void monitor(){

        /*保存监听到的信号源订单*/
        long signalOrderSize = redisManager.lGetListSize(RedisConstant.L_ORDER_FOLLOW_SIGNAL_ORDER);
        if(signalOrderSize>0){
            JSONObject followOrder=new JSONObject();
            int signalName=0;
            int orderAction=0;
            Object signalOrderInfo=redisManager.lPop(RedisConstant.L_ORDER_FOLLOW_SIGNAL_ORDER);
            while (signalOrderInfo!=null){
                try {
                    log.info("同步信号源订单数据------");
                    JSONObject orderJson= (JSONObject)signalOrderInfo;
                    log.info(orderJson.toJSONString());
                    followOrder=orderJson.getJSONObject("followOrder");
                    signalName=orderJson.getInteger("signalName");
                    orderAction=orderJson.getInteger("orderAction");
                    boolean isSuccess= fuOrderSignalService.saveMonitorOrderData(signalName,orderAction,followOrder);
                    if(!isSuccess){
                        log.error("同步跟单订单失败，订单："+JSONObject.toJSONString(signalOrderInfo));
                        //redis 暂存处理失败数据(暂时未做处理 需要实例化)
                        redisManager.lSet(RedisConstant.L_ORDER_FOLLOW_ORDERS_BAK,signalOrderInfo);
                    }
                }catch (Exception e){
                    log.error("同步跟单订单失败，订单："+JSONObject.toJSONString(signalOrderInfo));
                    //redis 暂存处理失败数据(暂时未做处理 需要实例化)
                    redisManager.lSet(RedisConstant.L_ORDER_FOLLOW_SIGNAL_ORDER_BAK,signalOrderInfo);
                    log.error(e.getMessage(),e);
                }
                signalOrderInfo=redisManager.lPop(RedisConstant.L_ORDER_FOLLOW_SIGNAL_ORDER);
            }
        }
    }
}
