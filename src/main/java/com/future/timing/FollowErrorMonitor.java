package com.future.timing;

import com.alibaba.fastjson.JSONObject;
import com.future.common.constants.RedisConstant;
import com.future.common.util.RedisManager;
import com.future.service.order.FuOrderFollowErrorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.ObjectUtils;

/**
 * @author ：haijun
 * @date ：Created in 2019-10-17 15:17
 * @description：用户跟单订单定时同步
 * @modified By：
 * @version: 1/0$
 */
@Configuration
@EnableScheduling
public class FollowErrorMonitor {

    Logger log= LoggerFactory.getLogger(FollowErrorMonitor.class);
    @Autowired
    RedisManager redisManager;
    @Autowired
    FuOrderFollowErrorService fuOrderFollowErrorService;

    /**
     * 跟单错误监听 每分钟同步一次
     */
    @Scheduled(cron = "0/30 * * * * ?")
    public void monitor(){

        /*同步跟单异常订单*/
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
                    if(!ObjectUtils.isEmpty(errorData.get("updateAction"))){
                        updateAction=errorData.getInteger("updateAction");
                    }
                    if(!ObjectUtils.isEmpty(errorData.get("errorCode"))){
                        errorCode=errorData.getInteger("errorCode");
                    }
                    order=errorData.getJSONObject("order");
                    followRule=errorData.getJSONObject("followRule");
                    boolean isSuccess=fuOrderFollowErrorService.dealFollowErrorData(updateAction,order,followRule,errorCode);
                    if(!isSuccess){
                        log.error("同步跟单订单失败，订单："+JSONObject.toJSONString(errorInfo));
                        //redis 暂存处理失败数据(暂时未做处理 需要实例化)
                        redisManager.lSet(RedisConstant.L_ORDER_FOLLOW_ERROR_DATA_BAK,errorInfo);
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

    /**
     * 跟单错误监听 每小时同步一次
     */
//    @Scheduled(cron = "0 0 * * * ?")
    public void bakMonitor(){
        /*同步跟单异常订单*/
        long errorSize = redisManager.lGetListSize(RedisConstant.L_ORDER_FOLLOW_ERROR_DATA_BAK);
        log.info("跟单错误监听 处理失败暂存------size:"+errorSize);
        if(errorSize>0){
            JSONObject order=new JSONObject();
            JSONObject followRule=new JSONObject();
            int errorCode=1;
            int updateAction=0;
            Object errorInfo=redisManager.lPop(RedisConstant.L_ORDER_FOLLOW_ERROR_DATA_BAK);
            while (errorInfo!=null&&errorSize>0){
                try {
                    log.info("同步跟单错误数据------");
                    JSONObject errorData=(JSONObject)errorInfo;
                    log.info(errorData.toJSONString());
                    if(!ObjectUtils.isEmpty(errorData.get("updateAction"))){
                        updateAction=errorData.getInteger("updateAction");
                    }
                    if(!ObjectUtils.isEmpty(errorData.get("errorCode"))){
                        errorCode=errorData.getInteger("errorCode");
                    }
                    order=errorData.getJSONObject("order");
                    followRule=errorData.getJSONObject("followRule");
                    boolean isSuccess=fuOrderFollowErrorService.dealFollowErrorData(updateAction,order,followRule,errorCode);
                    if(!isSuccess){
                        log.error("同步跟单订单失败，订单："+JSONObject.toJSONString(errorInfo));
                        //redis 暂存处理失败数据(暂时未做处理 需要实例化)
                        redisManager.lSet(RedisConstant.L_ORDER_FOLLOW_ERROR_DATA_BAK,errorInfo);
                    }
                }catch (Exception e){
                    log.error("同步跟单错误数据失败，订单："+JSONObject.toJSONString(errorInfo));
                    log.error(e.getMessage(),e);
                    //redis 暂存处理失败数据(暂时未做处理 需要实例化)
                    redisManager.lSet(RedisConstant.L_ORDER_FOLLOW_ERROR_DATA_BAK,errorInfo);
                }
                errorSize--;
                errorInfo=redisManager.lPop(RedisConstant.L_ORDER_FOLLOW_ERROR_DATA_BAK);
            }
        }
    }
}
