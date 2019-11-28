package com.future.controller.admin;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.plugins.Page;
import com.future.common.util.ThreadCache;
import com.future.entity.user.FuUser;
import com.future.service.user.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/admin")
public class AdminController{

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

    //注册操作
    @RequestMapping(value= "/registered",method=RequestMethod.POST)
    public @ResponseBody Map registered(){
        // 获取请求参数
        String requestJSONStr = ThreadCache.getPostRequestParams();
        JSONObject requestMap = JSONObject.parseObject(requestJSONStr);
        Map resMap=adminService.registered(requestMap);
        return resMap;
    }

    //查询用户列表
    @RequestMapping(value= "/queryUserList",method=RequestMethod.POST)
    public @ResponseBody
    Page<FuUser> queryUserList(){
        // 获取请求参数
        String requestJSONStr = ThreadCache.getPostRequestParams();
        JSONObject requestMap = JSONObject.parseObject(requestJSONStr);
        return adminService.queryUserList(requestMap);
    }

    //查询用户 by id or name
    @RequestMapping(value= "/getUserByIdOrderName",method=RequestMethod.POST)
    public @ResponseBody FuUser getUserByIdOrderName(){
        // 获取请求参数
        String requestJSONStr = ThreadCache.getPostRequestParams();
        JSONObject requestMap = JSONObject.parseObject(requestJSONStr);
        return adminService.getUserByIdOrderName(requestMap);
    }
}