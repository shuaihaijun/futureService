package com.future.controller.account;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.future.common.enums.GlobalResultCode;
import com.future.common.exception.ParameterInvalidException;
import com.future.common.util.ThreadCache;
import com.future.pojo.bo.order.UserMTAccountBO;
import com.future.service.account.FuAccountMtSevice;
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
    FuAccountMtSevice fuAccountMtSevice;

    //获取MT账户信息
    @RequestMapping(value= "/getUserMtAccountByCondition",method=RequestMethod.POST)
    public @ResponseBody UserMTAccountBO getUserMtAccountByCondition(){
        // 获取请求参数
        String requestJSONStr = ThreadCache.getPostRequestParams();
        JSONObject requestMap = JSONObject.parseObject(requestJSONStr);
        String username=requestMap.getString("username");
        String serverName=requestMap.getString("serverName");
        String mtAccId=requestMap.getString("mtAccId");
        String accountId=requestMap.getString("accountId");
        Map conditonMap=new HashMap();
        if(!StringUtils.isEmpty(accountId)){
            conditonMap.put("accountId",accountId);
        }else if(!StringUtils.isEmpty(username)){
            /*默认查询主账户号*/
            conditonMap.put("username",username);
            conditonMap.put("isChief",1);
        }else if(!StringUtils.isEmpty(serverName)&& !StringUtils.isEmpty(mtAccId)){
            conditonMap.put("serverName",serverName);
            conditonMap.put("mtAccId",mtAccId);
        }else {
            throw new ParameterInvalidException(GlobalResultCode.PARAM_NOT_COMPLETE);
        }
        List<UserMTAccountBO> accouts=  fuAccountMtSevice.getUserMTAccByCondition(conditonMap);

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
        String mtAccId=requestMap.getString("mtAccId");
        String accountId=requestMap.getString("accountId");
        Map conditonMap=new HashMap();
        if(!StringUtils.isEmpty(accountId)){
            conditonMap.put("accountId",accountId);
        }else if(!StringUtils.isEmpty(username)){
            /*默认查询主账户号*/
            conditonMap.put("username",username);
            conditonMap.put("isChief",1);
        }else if(!StringUtils.isEmpty(serverName)&& !StringUtils.isEmpty(mtAccId)){
            conditonMap.put("serverName",serverName);
            conditonMap.put("mtAccId",mtAccId);
        }else {
            throw new ParameterInvalidException(GlobalResultCode.PARAM_NOT_COMPLETE);
        }
        List<UserMTAccountBO> accouts=  fuAccountMtSevice.getUserMTAccByCondition(conditonMap);

        return  accouts;
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
        fuAccountMtSevice.saveUserMTAccount(requestData.getJSONObject("mtAccInfo"));
    }

}