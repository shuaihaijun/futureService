package com.future.service.trade;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.future.common.constants.GlobalConstant;
import com.future.common.exception.DataConflictException;
import com.future.common.util.HttpUtils;
import com.future.common.util.RedisManager;
import com.future.common.util.StringUtils;
import com.future.entity.user.FuUserFollows;
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
            log.error("断开MT账号连接失败：mtAccId:"+mtAccId);
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
            log.error("设置信号源监听失败：mtAccId:"+mtAccId);
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
     * 设置跟单关系
     * @param signalMtAccId
     * @param userMtAccId
     * @param userFollows
     * @return
     */
    public boolean addAccountFollowRelation(String signalMtAccId, String userMtAccId,FuUserFollows userFollows){
        if(userFollows==null){
            return false;
        }
        JSONObject followRule= JSON.parseObject(JSON.toJSONString(userFollows));
        JSONObject requestJson=new JSONObject();
        requestJson.put("signalName",signalMtAccId);
        requestJson.put("followName",userMtAccId);
        requestJson.put("followRule",followRule);
        String url=tradeServerHost+":"+tradeServerPort+ GlobalConstant.TRADE_ACC_SET_FOLLOW_RALATION;
        String result= HttpUtils.doPost(url,requestJson);
        if(StringUtils.isEmpty(result)){
            log.error("设置跟单关系失败：signalMtAccId:"+signalMtAccId+",userMtAccId"+userMtAccId);
        }
        JSONObject resultJson=JSONObject.parseObject(result);
        boolean content=resultJson.getBoolean("content");
        if(!content){
            log.error("设置跟单关系失败：signalMtAccId:"+signalMtAccId+",userMtAccId"+userMtAccId);
            return false;
        }
        return true;
    }

    /**
     * 移除跟单关系
     * @param signalMtAccId
     * @param userMtAccId
     * @return
     */
    public boolean removeAccountFollowRelation(String signalMtAccId, String userMtAccId){
        if(StringUtils.isEmpty(signalMtAccId)||StringUtils.isEmpty(userMtAccId)){
            log.error("移除跟单关系 数据为空！");
            return false;
        }
        JSONObject requestJson=new JSONObject();
        requestJson.put("signalName",signalMtAccId);
        requestJson.put("followName",userMtAccId);
        String url=tradeServerHost+":"+tradeServerPort+ GlobalConstant.TRADE_ACC_REMOVE_FOLLOW_RALATION;
        String result= HttpUtils.doPost(url,requestJson);
        if(StringUtils.isEmpty(result)){
            log.error("设置跟单关系失败：signalMtAccId:"+signalMtAccId+",userMtAccId"+userMtAccId);
        }
        JSONObject resultJson=JSONObject.parseObject(result);
        boolean content=resultJson.getBoolean("content");
        if(!content){
            log.error("设置跟单关系失败：signalMtAccId:"+signalMtAccId+",userMtAccId"+userMtAccId);
            return false;
        }
        return true;
    }
}
