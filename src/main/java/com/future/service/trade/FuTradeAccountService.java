package com.future.service.trade;

import com.alibaba.fastjson.JSONObject;
import com.future.common.constants.GlobalConstant;
import com.future.common.exception.DataConflictException;
import com.future.common.util.HttpUtils;
import com.future.common.util.RedisManager;
import com.future.common.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 *  跟随者逻辑处理
 */
@Service
public class FuTradeAccountService {

    Logger log= LoggerFactory.getLogger(FuTradeAccountService.class);

    @Autowired
    RedisManager redisManager;


    @Value("${tradeServerHost}")
    public String tradeServerHost;
    @Value("${tradeServerPort}")
    public int tradeServerPort;

    /**
     * 连接MT账号
     * @param brokerName
     * @param mtAccId
     * @param password
     * @return
     */
    public int setMtAccountConnect(String brokerName,int mtAccId,String password){
        /*校验数据*/
        if(StringUtils.isEmpty(brokerName)||mtAccId==0||StringUtils.isEmpty(password)){
            log.error("getMtAccountConnect null params!");
            throw new DataConflictException("setAccountConnnect null params!");
        }
        Map requestMap=new HashMap();
        requestMap.put("brokerName",brokerName);
        requestMap.put("username",mtAccId);
        requestMap.put("password",password);
        String url=tradeServerHost+":"+tradeServerPort+ GlobalConstant.TRADE_ACC_SET_CONNECT;
        String result= HttpUtils.doPost(url,requestMap);
        if(StringUtils.isEmpty(result)){
            log.error("登录MT账号失败：mtAccId:"+mtAccId);
        }
        JSONObject resultJson=JSONObject.parseObject(result);
        JSONObject content=resultJson.getJSONObject("content");
        int clientId=0;
        if(content.get("data")!=null){
            clientId=content.getInteger("data");
        }
        return clientId;
    }

    /**
     * 断开MT账号连接
     * @param brokerName
     * @param mtAccId
     * @param password
     * @return
     */
    public boolean setMtAccountDisConnnect(String brokerName,int mtAccId,String password){
        /*校验数据*/
        if(StringUtils.isEmpty(brokerName)||mtAccId==0||StringUtils.isEmpty(password)){
            log.error("getMtAccountConnect null params!");
            throw new DataConflictException("setAccountConnnect null params!");
        }
        Map requestMap=new HashMap();
        requestMap.put("brokerName",brokerName);
        requestMap.put("username",mtAccId);
        requestMap.put("password",password);
        String url=tradeServerHost+":"+tradeServerPort+ GlobalConstant.TRADE_ACC_DIS_CONNECT;
        String result= HttpUtils.doPost(url,requestMap);
        if(StringUtils.isEmpty(result)){
            log.error("登录MT账号失败：mtAccId:"+mtAccId);
        }
        JSONObject resultJson=JSONObject.parseObject(result);
        boolean content=resultJson.getBoolean("content");
        return content;
    }

    /**
     * 设置信号源监听
     * @param brokerName
     * @param mtAccId
     * @param password
     * @return
     */
    public int setMtSignalMonitor(String brokerName,int mtAccId,String password){
        /*校验数据*/
        if(StringUtils.isEmpty(brokerName)||mtAccId==0||StringUtils.isEmpty(password)){
            log.error("getMtAccountConnect null params!");
            throw new DataConflictException("setAccountConnnect null params!");
        }
        Map requestMap=new HashMap();
        requestMap.put("brokerName",brokerName);
        requestMap.put("username",mtAccId);
        requestMap.put("password",password);
        String url=tradeServerHost+":"+tradeServerPort+ GlobalConstant.TRADE_ACC_SIGNAL_MONITOR;
        String result= HttpUtils.doPost(url,requestMap);
        if(StringUtils.isEmpty(result)){
            log.error("登录MT账号失败：mtAccId:"+mtAccId);
        }
        JSONObject resultJson=JSONObject.parseObject(result);
        JSONObject content=resultJson.getJSONObject("content");
        int clientId=0;
        if(content.get("data")!=null){
            clientId=content.getInteger("data");
        }
        return clientId;
    }
}
