package com.future.web.admin;

import com.future.common.Response;
import com.future.common.ResultMsg;
import com.future.common.ResultObject;
import com.future.service.user.AdminService;
import com.future.web.BaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin")
public class AdminController extends BaseController {

    @Autowired
    AdminService adminService;

    //登录操作
    @RequestMapping(value= "/login",method=RequestMethod.POST)
    public @ResponseBody Object login(HttpServletRequest request, HttpServletResponse response){
        String username=request.getParameter("username");
        String password=request.getParameter("password");
        Response resService=adminService.login(username,password);

        if(resService.getCode().equalsIgnoreCase(ResultMsg.FAILED.getCode())){
            /*处理错误！*/
            return responseData(ResultMsg.FAILED,resService);
        }
    	return responseData(ResultMsg.SUCCESS,resService);
    }
}