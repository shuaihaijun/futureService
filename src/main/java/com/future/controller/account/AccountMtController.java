package com.future.controller.account;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.future.common.enums.GlobalResultCode;
import com.future.common.exception.ParameterInvalidException;
import com.future.common.helper.PageInfoHelper;
import com.future.common.result.RequestParams;
import com.future.common.util.ThreadCache;
import com.future.entity.product.FuProductSignal;
import com.future.pojo.bo.order.UserMTAccountBO;
import com.future.service.account.FuAccountMtService;
import com.future.service.product.FuProductSignalService;
import com.future.service.user.UserCommonService;
import com.github.pagehelper.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/accountMt")
public class AccountMtController {

    Logger log= LoggerFactory.getLogger(AccountMtController.class);

    @Autowired
    FuAccountMtService fuAccountMtService;
    @Autowired
    FuProductSignalService fuProductSignalService;
    @Autowired
    UserCommonService userCommonService;



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
            conditonMap.put("id",accountId);
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
            conditonMap.put("id",accountId);
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
        List<UserMTAccountBO> accounts=  fuAccountMtService.getUserMTAccByCondition(conditonMap);

        return  accounts;
    }

    //获取MT账户信息
    @RequestMapping(value= "/queryUsersMtAccount",method=RequestMethod.POST)
    public @ResponseBody
    Page<UserMTAccountBO> queryUsersMtAccount(@RequestBody RequestParams<Map> requestParams) {
        Map accountMap = requestParams.getParams();
        PageInfoHelper helper = requestParams.getPageInfoHelper();
        return fuAccountMtService.queryUsersMtAccount(accountMap,helper);
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
        Integer operUserId=requestData.getInteger("operUserId");
        /*校验数据*/
        if(signalId==null||signalId==0||operUserId==null||operUserId==0){
            log.warn("连接MT账户信息,数据为空!");
            throw new ParameterInvalidException(GlobalResultCode.PARAM_NULL_POINTER);
        }
        /*判断权限*/
        FuProductSignal signal= fuProductSignalService.findSignalById(signalId);
        Integer operProjectKey= userCommonService.getUserProjKey(operUserId);
        if(signal==null||operProjectKey==null){
            log.warn("查询信号源和团队信息失败!");
            throw new ParameterInvalidException("查询信号源和团队信息失败!");
        }
        if(signal.getProjKey()!=operProjectKey){
            log.warn("管理员只能操作自己团队的信号源!");
            throw new ParameterInvalidException("管理员只能操作自己团队的信号源!");
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
        Integer operUserId=requestData.getInteger("operUserId");
        /*校验数据*/
        if(signalId==null||signalId==0||operUserId==null||operUserId==0){
            log.warn("连接MT账户信息,数据为空!");
            throw new ParameterInvalidException(GlobalResultCode.PARAM_NULL_POINTER);
        }
        /*判断权限*/
        FuProductSignal signal= fuProductSignalService.findSignalById(signalId);
        Integer operProjectKey= userCommonService.getUserProjKey(operUserId);
        if(signal==null||operProjectKey==null){
            log.warn("查询信号源和团队信息失败!");
            throw new ParameterInvalidException("查询信号源和团队信息失败!");
        }
        if(signal.getProjKey()!=operProjectKey){
            log.warn("管理员只能操作自己团队的信号源!");
            throw new ParameterInvalidException("管理员只能操作自己团队的信号源!");
        }
        return fuAccountMtService.disConnectSignalMTAccount(signalId);
    }

}