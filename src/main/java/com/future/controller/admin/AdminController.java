package com.future.controller.admin;

import com.alibaba.fastjson.JSONObject;
import com.future.common.enums.GlobalResultCode;
import com.future.common.exception.DataConflictException;
import com.future.common.exception.ParameterInvalidException;
import com.future.common.helper.PageInfoHelper;
import com.future.common.result.RequestParams;
import com.future.common.util.StringUtils;
import com.future.common.util.ThreadCache;
import com.future.entity.user.FuUser;
import com.future.service.user.AdminService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/admin")
public class AdminController{

    Logger log= LoggerFactory.getLogger(AdminController.class);

    @Autowired
    AdminService adminService;

    //登录操作
    @RequestMapping(value= "/login",method=RequestMethod.POST)
    public @ResponseBody Map login(){
        // 获取请求参数
        String requestJSONStr = ThreadCache.getPostRequestParams();
        JSONObject requestMap = JSONObject.parseObject(requestJSONStr);
        String username=requestMap.getString("username");
        String password=requestMap.getString("password");
        Map resMap=adminService.login(username,password);
        return resMap;
    }

    //登录操作
    @RequestMapping(value= "/tokenLogin",method=RequestMethod.POST)
    public @ResponseBody Map tokenLogin(@RequestBody RequestParams<Map> requestParams) {
        // 获取请求参数
        if(requestParams==null||requestParams.getParams()==null||requestParams.getParams().get("token")==null){
            log.error("tokenLogin 传入参数为空！");
            throw new DataConflictException(GlobalResultCode.PARAM_NULL_POINTER);
        }
        String token=String.valueOf(requestParams.getParams().get("token"));
        Map resMap=adminService.tokenLogin(token);
        return resMap;
    }

    //登出操作
    @RequestMapping(value= "/logout",method=RequestMethod.POST)
    public @ResponseBody void logout(){
        // 获取请求参数
        String requestJSONStr = ThreadCache.getPostRequestParams();
        JSONObject requestMap = JSONObject.parseObject(requestJSONStr);
        String username=requestMap.getString("username");
        String userId=requestMap.getString("userId");
        String token=requestMap.getString("token");
        if(StringUtils.isEmpty(userId)){
            throw new DataConflictException(GlobalResultCode.PARAM_NULL_POINTER);
        }
        adminService.logout(Integer.parseInt(userId),username,token);
    }

    //注册操作
    @RequestMapping(value= "/registered",method=RequestMethod.POST)
    public @ResponseBody Map registered(){
        // 获取请求参数
        String requestJSONStr = ThreadCache.getPostRequestParams();
        JSONObject requestMap = JSONObject.parseObject(requestJSONStr);
        Map resMap=adminService.registered(requestMap);
        return resMap;
    }

    //修改操作
    @RequestMapping(value= "/saveOrUpdateUser",method=RequestMethod.POST)
    public @ResponseBody void saveOrUpdateUser(){
        // 获取请求参数
        String requestJSONStr = ThreadCache.getPostRequestParams();
        JSONObject requestData = JSONObject.parseObject(requestJSONStr);
        if(requestData.isEmpty()){
            throw new ParameterInvalidException(GlobalResultCode.PARAM_NULL_POINTER);
        }
        FuUser fuUser=JSONObject.toJavaObject(requestData,FuUser.class);
        adminService.updateAdmin(fuUser);
    }

    //查询用户列表
    @RequestMapping(value= "/queryUserList",method=RequestMethod.POST)
    public @ResponseBody Page<FuUser> queryUserList(@RequestBody RequestParams<Map> requestParams) {
        Map userMap = requestParams.getParams();
        PageInfoHelper helper = requestParams.getPageInfoHelper();
        // 获取请求参数
        return adminService.queryUserList(userMap,helper);
    }

    //查询用户 by id or name
    @RequestMapping(value= "/getUserByIdOrName",method=RequestMethod.POST)
    public @ResponseBody FuUser getUserByIdOrName(){
        // 获取请求参数
        String requestJSONStr = ThreadCache.getPostRequestParams();
        JSONObject requestMap = JSONObject.parseObject(requestJSONStr);
        return adminService.getUserByIdOrName(requestMap);
    }

    //提交审核申请 by id or name
    @RequestMapping(value= "/submitUserBinding",method=RequestMethod.POST)
    public @ResponseBody FuUser submitUserBinding(){
        // 获取请求参数
        String requestJSONStr = ThreadCache.getPostRequestParams();
        JSONObject requestMap = JSONObject.parseObject(requestJSONStr);
        return adminService.submitUserBinding(requestMap);
    }

    //审核 by id or name
    @RequestMapping(value= "/checkUserBinding",method=RequestMethod.POST)
    public @ResponseBody FuUser checkUserBinding(){
        // 获取请求参数
        String requestJSONStr = ThreadCache.getPostRequestParams();
        JSONObject requestMap = JSONObject.parseObject(requestJSONStr);
        return adminService.checkUserBinding(requestMap);
    }

    //登录操作
    @RequestMapping(value= "/updatePassword",method=RequestMethod.POST)
    public @ResponseBody boolean updatePassword(){
        // 获取请求参数
        String requestJSONStr = ThreadCache.getPostRequestParams();
        JSONObject requestMap = JSONObject.parseObject(requestJSONStr);
        Integer userId=requestMap.getInteger("userId");
        String username=requestMap.getString("username");
        String password=requestMap.getString("password");
        String passwordNew=requestMap.getString("passwordNew");
        adminService.updatePassword(userId,username,password,passwordNew);
        return true;
    }

    //查询用户列表
    @RequestMapping(value= "/queryAgentUserList",method=RequestMethod.POST)
    public @ResponseBody
    PageInfo<FuUser> queryAgentUserList(@RequestBody RequestParams<FuUser> requestParams) {
        FuUser fuUser = requestParams.getParams();
        PageInfoHelper helper = requestParams.getPageInfoHelper();
        return adminService.queryAgentUserList(fuUser,helper);
    }

    //修改用户介绍人信息
    @RequestMapping(value= "/updateUserIntroducer",method=RequestMethod.POST)
    public @ResponseBody boolean updateUserIntroducer(@RequestBody RequestParams<FuUser> requestParams) {
        FuUser fuUser = requestParams.getParams();
        if(fuUser==null){
            log.error("修改用户介绍人信息,传入参数为空！");
            throw new DataConflictException(GlobalResultCode.PARAM_NULL_POINTER);
        }
        adminService.updateUserIntroducer(fuUser.getId(),fuUser.getIntroducer());
        return true;
    }
}