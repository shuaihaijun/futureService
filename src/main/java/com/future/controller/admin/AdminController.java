package com.future.controller.admin;

import com.alibaba.fastjson.JSONObject;
import com.future.common.util.ThreadCache;
import com.future.service.user.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
}