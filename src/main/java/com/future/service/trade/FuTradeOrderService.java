package com.future.service.trade;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.future.common.util.RedisManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *  跟随者逻辑处理
 */
@Service
public class FuTradeOrderService {

    Logger log= LoggerFactory.getLogger(FuTradeOrderService.class);

    @Autowired
    RedisManager redisManager;

    /**
     * 根据时间段获取用户关闭订单（时间段不能超过1周）
     * @param brokerName
     * @param username
     * @param password
     * @param nHisTimeFrom
     * @param nHisTimeTo
     */
    public JSONArray getUserCloseOrders(String brokerName, int username, String password, int nHisTimeFrom, int nHisTimeTo){
        return null;
    }

    /**
     * 根据orderId获取用户关闭订单
     * @param brokerName
     * @param username
     * @param password
     * @param orderId
     */
    public JSONObject getUserCloseOrder(String brokerName, int username, String password, int orderId){
        return null;
    }

    /**
     * 获取用户open订单
     * @param brokerName
     * @param username
     * @param password
     */
    public JSONArray getUserOpenOrders(String brokerName, int username, String password){
        return null;
    }

    /**
     * 根据orderId获取用户open订单
     * @param brokerName
     * @param username
     * @param password
     * @param orderId
     */
    public JSONObject getUserOpenOrder(String brokerName, int username, String password, int orderId){
        return null;
    }

}
