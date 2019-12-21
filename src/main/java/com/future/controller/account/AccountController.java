package com.future.controller.account;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.future.common.enums.GlobalResultCode;
import com.future.common.exception.ParameterInvalidException;
import com.future.common.util.ThreadCache;
import com.future.pojo.bo.order.UserMTAccountBO;
import com.future.service.account.FuAccountMtService;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/account")
public class AccountController {

    Logger log= LoggerFactory.getLogger(AccountController.class);

    @Autowired
    FuAccountMtService fuAccountMtService;

    //获取MT账户信息
    @RequestMapping(value= "/getUserMtAccountByCondition",method=RequestMethod.POST)
    public @ResponseBody UserMTAccountBO getUserMtAccountByCondition(){
        // 获取请求参数
        String requestJSONStr = ThreadCache.getPostRequestParams();
        JSONObject requestMap = JSONObject.parseObject(requestJSONStr);
        String username=requestMap.getString("username");
        String serverName=requestMap.getString("serverName");
        String userId=requestMap.getString("userId");
        String mtAccId=requestMap.getString("mtAccId");
        String accountId=requestMap.getString("accountId");
        Map conditonMap=new HashMap();
        if(!StringUtils.isEmpty(accountId)){
            conditonMap.put("accountId",accountId);
        }else if(!StringUtils.isEmpty(username) || !StringUtils.isEmpty(userId)){
            /*默认查询主账户号*/
            conditonMap.put("userId",userId);
            conditonMap.put("username",username);
        }else if(!StringUtils.isEmpty(mtAccId)){
            conditonMap.put("userId",userId);
            conditonMap.put("username",username);
            conditonMap.put("serverName",serverName);
            conditonMap.put("mtAccId",mtAccId);
        }else {
            throw new ParameterInvalidException(GlobalResultCode.PARAM_NOT_COMPLETE);
        }
        List<UserMTAccountBO> accouts=  fuAccountMtService.getUserMTAccByCondition(conditonMap);

        log.info(accouts.size()+"");

        if(accouts.size()>0){
            return accouts.get(0);
        }else {
            return null;
        }
    }

    //获取MT账户信息
    @RequestMapping(value= "/getUsersMtAccountByCondition",method=RequestMethod.POST)
    public @ResponseBody List getUsersMtAccountByCondition(){
        // 获取请求参数
        String requestJSONStr = ThreadCache.getPostRequestParams();
        JSONObject requestMap = JSONObject.parseObject(requestJSONStr);
        String username=requestMap.getString("username");
        String serverName=requestMap.getString("serverName");
        String userId=requestMap.getString("userId");
        String mtAccId=requestMap.getString("mtAccId");
        String accountId=requestMap.getString("accountId");
        Map conditonMap=new HashMap();
        if(!StringUtils.isEmpty(accountId)){
            conditonMap.put("accountId",accountId);
        }else if(!StringUtils.isEmpty(username) || !StringUtils.isEmpty(userId)){
            /*默认查询主账户号*/
            conditonMap.put("userId",userId);
            conditonMap.put("username",username);
        }else if(!StringUtils.isEmpty(mtAccId)){
            conditonMap.put("userId",userId);
            conditonMap.put("username",username);
            conditonMap.put("serverName",serverName);
            conditonMap.put("mtAccId",mtAccId);
        }else {
            throw new ParameterInvalidException(GlobalResultCode.PARAM_NOT_COMPLETE);
        }
        List<UserMTAccountBO> accouts=  fuAccountMtService.getUserMTAccByCondition(conditonMap);

        return  accouts;
    }

    //获取MT账户信息
    @RequestMapping(value= "/queryUsersMtAccount",method=RequestMethod.POST)
    public @ResponseBody
    PageInfo<UserMTAccountBO> queryUsersMtAccount(){
        // 获取请求参数
        String requestJSONStr = ThreadCache.getPostRequestParams();
        JSONObject requestMap = JSONObject.parseObject(requestJSONStr);
        String userId=requestMap.getString("userId");
        String username=requestMap.getString("username");
        String brokerName=requestMap.getString("brokerName");
        String serverName=requestMap.getString("serverName");
        String mtAccId=requestMap.getString("mtAccId");
        String accountId=requestMap.getString("accountId");
        Map conditonMap=new HashMap();
        if(!StringUtils.isEmpty(userId)){
            conditonMap.put("userId",userId);
        }
        if(!StringUtils.isEmpty(username)){
            conditonMap.put("username",username);
        }
        if(!StringUtils.isEmpty(brokerName)){
            conditonMap.put("brokerName",brokerName);
        }
        if(!StringUtils.isEmpty(serverName)){
            conditonMap.put("serverName",serverName);
        }
        if(!StringUtils.isEmpty(mtAccId)){
            conditonMap.put("mtAccId",mtAccId);
        }
        if(!StringUtils.isEmpty(accountId)){
            conditonMap.put("accountId",accountId);
        }
        List<UserMTAccountBO> accouts=  fuAccountMtService.queryUsersMtAccount(conditonMap);

        return  new PageInfo<UserMTAccountBO>(accouts);
    }

    //保存MT账户信息
    @RequestMapping(value= "/saveUserMTAccount",method=RequestMethod.POST)
    public @ResponseBody void saveUserMTAccount(){
        // 获取请求参数
        String requestJSONStr = ThreadCache.getPostRequestParams();
        JSONObject requestData = JSONObject.parseObject(requestJSONStr);
        if(requestData.isEmpty()||requestData.getJSONObject("mtAccInfo").isEmpty()){
            throw new ParameterInvalidException(GlobalResultCode.PARAM_NULL_POINTER);
        }
        fuAccountMtService.saveUserMTAccount(requestData.getJSONObject("mtAccInfo"));
    }

    //连接MT账户信息
    @RequestMapping(value= "/connectUserMTAccount",method=RequestMethod.POST)
    public @ResponseBody boolean connectUserMTAccount(){
        // 获取请求参数
        String requestJSONStr = ThreadCache.getPostRequestParams();
        JSONObject requestData = JSONObject.parseObject(requestJSONStr);
        Integer userId=requestData.getInteger("userId");
        String username=requestData.getString("username");
        String serverName=requestData.getString("serverName");
        String mtAccId=requestData.getString("mtAccId");
        /*校验数据*/
        if(((userId==null||userId==0)&&StringUtils.isEmpty(username))
            ||StringUtils.isEmpty(mtAccId)){
            //用户信息 和 账户信息 不能同时为空
            log.warn("连接MT账户信息,用户信息 和 账户信息 同时为空!");
            throw new ParameterInvalidException(GlobalResultCode.PARAM_NULL_POINTER);
        }
        return fuAccountMtService.connectUserMTAccount(userId,mtAccId,username,serverName);
    }
    //断开连接MT账户信息
    @RequestMapping(value= "/disConnectUserMTAccount",method=RequestMethod.POST)
    public @ResponseBody boolean disConnectUserMTAccount(){
        // 获取请求参数
        String requestJSONStr = ThreadCache.getPostRequestParams();
        JSONObject requestData = JSONObject.parseObject(requestJSONStr);
        Integer userId=requestData.getInteger("userId");
        String username=requestData.getString("username");
        String serverName=requestData.getString("serverName");
        String mtAccId=requestData.getString("mtAccId");
        /*校验数据*/
        if(((userId==null||userId==0)&&StringUtils.isEmpty(username))
                ||StringUtils.isEmpty(mtAccId)){
            //用户信息 和 账户信息 不能同时为空
            log.warn("连接MT账户信息,用户信息 和 账户信息 同时为空!");
            throw new ParameterInvalidException(GlobalResultCode.PARAM_NULL_POINTER);
        }
        return fuAccountMtService.disConnectUserMTAccount(userId,mtAccId,username,serverName);
    }


    //连接信号源MT账户信息
    @RequestMapping(value= "/connectSignalMTAccount",method=RequestMethod.POST)
    public @ResponseBody boolean connectSignalMTAccount(){
        // 获取请求参数
        String requestJSONStr = ThreadCache.getPostRequestParams();
        JSONObject requestData = JSONObject.parseObject(requestJSONStr);
        Integer signalId=requestData.getInteger("signalId");
        /*校验数据*/
        if(signalId==null||signalId==0){
            log.warn("连接MT账户信息,数据为空!");
            throw new ParameterInvalidException(GlobalResultCode.PARAM_NULL_POINTER);
        }
        return fuAccountMtService.connectSignalMTAccount(signalId);
    }
    //断开信号源连接MT账户信息
    @RequestMapping(value= "/disConnectSignalMTAccount",method=RequestMethod.POST)
    public @ResponseBody boolean disConnectSignalMTAccount(){
        // 获取请求参数
        String requestJSONStr = ThreadCache.getPostRequestParams();
        JSONObject requestData = JSONObject.parseObject(requestJSONStr);
        Integer signalId=requestData.getInteger("signalId");
        /*校验数据*/
        if(signalId==null||signalId==0){
            log.warn("连接MT账户信息,数据为空!");
            throw new ParameterInvalidException(GlobalResultCode.PARAM_NULL_POINTER);
        }
        return fuAccountMtService.disConnectSignalMTAccount(signalId);
    }

}