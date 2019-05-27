package com.future.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private Registration registration;       // 服务注册
    @Autowired
    private DiscoveryClient client;
    @Value("${baseUrl}")
    public String dataUrl;

    @RequestMapping(value = "/test" ,method = RequestMethod.GET)
    public String add(@RequestParam Integer a, HttpServletRequest request) {
        List<ServiceInstance> instance = client.getInstances(registration.getServiceId());
        logger.info("/add, host:" + instance.get(0).getHost() + ", service_id:" + instance.get(0).getServiceId() + ", result:" );
        return "From servicePay, Result is ";
    }

    //B服务调用A服务
    @RequestMapping(value= "/testConfig",method=RequestMethod.GET)
    public String testConfig(){
        logger.info("http://localhost:7074/add?a="+dataUrl);
    	return dataUrl;
    }
}