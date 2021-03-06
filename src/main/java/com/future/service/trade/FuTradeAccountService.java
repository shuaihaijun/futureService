package com.future.service.trade;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.future.common.constants.GlobalConstant;
import com.future.common.exception.DataConflictException;
import com.future.common.util.HttpUtils;
import com.future.common.util.RedisManager;
import com.future.common.util.StringUtils;
import com.future.entity.user.FuUserFollows;
import com.future.pojo.bo.account.MtAccountInfoBo;
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
     * @param serverName
     * @param mtAccId
     * @param password
     * @return
     */
    public int setMtAccountConnect(String serverName,int mtAccId,String password){
        /*校验数据*/
        if(StringUtils.isEmpty(serverName)||mtAccId==0||StringUtils.isEmpty(password)){
            log.error("getMtAccountConnect null params!");
            throw new DataConflictException("setAccountConnnect null params!");
        }
        Map dataMap=new HashMap();
        dataMap.put("serverName",serverName);
        dataMap.put("username",mtAccId);
        dataMap.put("password",password);
        Map requestMap= new HashMap<>();
        requestMap.put("params",dataMap);
        String url=tradeServerHost+":"+tradeServerPort+ GlobalConstant.TRADE_ACC_SET_CONNECT;
        String result= HttpUtils.httpPost(url,requestMap);
        if(StringUtils.isEmpty(result)){
            log.error("登录MT账号失败：mtAccId:"+mtAccId);
        }
        JSONObject resultJson=JSONObject.parseObject(result);
        JSONObject content=resultJson.getJSONObject("content");
        int clientId=0;
        if(content!=null&&content.get("data")!=null){
            clientId=content.getInteger("data");
        }
        return clientId;
    }


    /**
     * 连接MT账号 并且允许交易
     * @param serverName
     * @param mtAccId
     * @param password
     * @return
     */
    public int setAccountConnectTradeAllowed(String serverName,int mtAccId,String password){
        /*校验数据*/
        if(StringUtils.isEmpty(serverName)||mtAccId==0||StringUtils.isEmpty(password)){
            log.error("getMtAccountConnect null params!");
            throw new DataConflictException("setAccountConnnect null params!");
        }
        Map dataMap=new HashMap();
        dataMap.put("serverName",serverName);
        dataMap.put("username",mtAccId);
        dataMap.put("password",password);
        Map requestMap= new HashMap<>();
        requestMap.put("params",dataMap);
        String url=tradeServerHost+":"+tradeServerPort+ GlobalConstant.TRADE_ACC_SET_CONNECT_TRADE;
        String result= HttpUtils.httpPost(url,requestMap);
        if(StringUtils.isEmpty(result)){
            log.error("登录MT账号失败：mtAccId:"+mtAccId);
        }
        JSONObject resultJson=JSONObject.parseObject(result);
        JSONObject content=resultJson.getJSONObject("content");
        int clientId=0;
        if(content!=null&&content.get("data")!=null){
            clientId=content.getInteger("data");
        }
        return clientId;
    }

    /**
     * 断开MT账号连接
     * @param serverName
     * @param mtAccId
     * @param password
     * @return
     */
    public boolean setMtAccountDisConnnect(String serverName,int mtAccId,String password){
        /*校验数据*/
        if(StringUtils.isEmpty(serverName)||mtAccId==0||StringUtils.isEmpty(password)){
            log.error("getMtAccountConnect null params!");
            throw new DataConflictException("setAccountConnnect null params!");
        }
        Map dataMap=new HashMap();
        dataMap.put("serverName",serverName);
        dataMap.put("username",mtAccId);
        dataMap.put("password",password);
        Map requestMap= new HashMap<>();
        requestMap.put("params",dataMap);
        String url=tradeServerHost+":"+tradeServerPort+ GlobalConstant.TRADE_ACC_DIS_CONNECT;
        String result= HttpUtils.httpPost(url,requestMap);
        if(StringUtils.isEmpty(result)){
            log.error("断开MT账号连接失败：mtAccId:"+mtAccId);
        }
        JSONObject resultJson=JSONObject.parseObject(result);
        boolean content=resultJson.getBoolean("content");
        return content;
    }

    /**
     * 设置信号源监听
     * @param serverName
     * @param mtAccId
     * @param password
     * @return
     */
    public int setMtSignalMonitor(String serverName,int mtAccId,String password){
        /*校验数据*/
        if(StringUtils.isEmpty(serverName)||mtAccId==0||StringUtils.isEmpty(password)){
            log.error("getMtAccountConnect null params!");
            throw new DataConflictException("setAccountConnnect null params!");
        }
        Map dataMap= new HashMap<>();
        dataMap.put("serverName",serverName);
        dataMap.put("username",mtAccId);
        dataMap.put("password",password);
        Map requestMap= new HashMap<>();
        requestMap.put("params",dataMap);
        String url=tradeServerHost+":"+tradeServerPort+ GlobalConstant.TRADE_ACC_SIGNAL_MONITOR;
        String result= HttpUtils.httpPost(url,requestMap);
        if(StringUtils.isEmpty(result)){
            log.error("设置信号源监听失败：mtAccId:"+mtAccId);
        }
        JSONObject resultJson=JSONObject.parseObject(result);
        JSONObject content=resultJson.getJSONObject("content");
        int clientId=0;
        if(content!=null&&content.get("data")!=null){
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
        Map requestMap= new HashMap<>();
        requestMap.put("params",requestJson);
        String url=tradeServerHost+":"+tradeServerPort+ GlobalConstant.TRADE_ACC_SET_FOLLOW_RALATION;
        String result= HttpUtils.httpPost(url,requestMap);
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
        Map requestMap= new HashMap<>();
        requestMap.put("params",requestJson);
        String url=tradeServerHost+":"+tradeServerPort+ GlobalConstant.TRADE_ACC_REMOVE_FOLLOW_RALATION;
        String result= HttpUtils.httpPost(url,requestMap);
        if(StringUtils.isEmpty(result)){
            log.error("设置跟单关系失败：signalMtAccId:"+signalMtAccId+",userMtAccId"+userMtAccId);
        }
        JSONObject resultJson=JSONObject.parseObject(result);
        if(resultJson.get("content")==null){
            log.error("设置跟单关系失败：signalMtAccId:"+signalMtAccId+",userMtAccId"+userMtAccId);
            return false;
        }
        boolean content=resultJson.getBoolean("content");
        if(!content){
            log.error("设置跟单关系失败：signalMtAccId:"+signalMtAccId+",userMtAccId"+userMtAccId);
            return false;
        }
        return true;
    }


    /**
     * 获取用户MT账户信息
     * @param serverName
     * @param mtAccId
     * @param password
     */
    public MtAccountInfoBo getMtAccountInfo(String serverName, int mtAccId, String password){
        /*校验数据*/
        if(StringUtils.isEmpty(serverName)||mtAccId==0||StringUtils.isEmpty(password)){
            log.error("getMtAccountConnect null params!");
            throw new DataConflictException("setAccountConnnect null params!");
        }
        Map dataMap= new HashMap<>();
        dataMap.put("serverName",serverName);
        dataMap.put("username",mtAccId);
        dataMap.put("password",password);
        Map requestMap= new HashMap<>();
        requestMap.put("params",dataMap);
        String url=tradeServerHost+":"+tradeServerPort+ GlobalConstant.TRADE_ACC_GET_INFO;
        String result= HttpUtils.httpPost(url,requestMap);
        if(StringUtils.isEmpty(result)){
            log.error("设置信号源监听失败：mtAccId:"+mtAccId);
        }
        JSONObject resultJson=JSONObject.parseObject(result);
        JSONObject content=resultJson.getJSONObject("content");
        MtAccountInfoBo info=new MtAccountInfoBo();
        if(content!=null){
            info=JSONObject.toJavaObject(content,MtAccountInfoBo.class);
        }
        return info;
    }
}
