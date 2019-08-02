package com.future.controller.signal;

import com.alibaba.fastjson.JSONObject;
import com.future.common.util.ThreadCache;
import com.future.entity.product.FuProductSignal;
import com.future.service.product.FuProductSignalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/signal")
public class ProductSignalController {
    Logger log= LoggerFactory.getLogger(ProductSignalController.class);

    @Autowired
    FuProductSignalService fuProductSignalService;

    //查找申请信息
    @RequestMapping(value= "/findApplyById",method=RequestMethod.POST)
    public @ResponseBody
    FuProductSignal findApplyById(){
        // 获取请求参数
        String requestJSONStr = ThreadCache.getPostRequestParams();
        JSONObject requestMap = JSONObject.parseObject(requestJSONStr);
        String signalId=requestMap.getString("signalId");
        String username=requestMap.getString("username");

        /*条件查询日期不能超过1周*/
        return fuProductSignalService.findSignalById(Integer.parseInt(signalId));
    }

    //查找申请信息
    @RequestMapping(value= "/findApply",method=RequestMethod.POST)
    public @ResponseBody
    List<FuProductSignal> findApply(){
        // 获取请求参数
        String requestJSONStr = ThreadCache.getPostRequestParams();
        JSONObject requestMap = JSONObject.parseObject(requestJSONStr);
        String signalId=requestMap.getString("signalId");
        String username=requestMap.getString("username");

        /*条件查询日期不能超过1周*/
        return fuProductSignalService.findSignalByCondition(requestMap);
    }

    //保存申请信息
    @RequestMapping(value= "/saveApply",method=RequestMethod.POST)
    public @ResponseBody Boolean saveApply(){
        // 获取请求参数
        String requestJSONStr = ThreadCache.getPostRequestParams();
        JSONObject requestMap = JSONObject.parseObject(requestJSONStr);
        String userId=requestMap.getString("userId");
        String username=requestMap.getString("username");

        /*条件查询日期不能超过1周*/
        return fuProductSignalService.saveProductSignal(requestMap);
    }
    //修改申请信息
    @RequestMapping(value= "/updateApply",method=RequestMethod.POST)
    public @ResponseBody Boolean updateApply(){
        // 获取请求参数
        String requestJSONStr = ThreadCache.getPostRequestParams();
        JSONObject requestMap = JSONObject.parseObject(requestJSONStr);
        String signalId=requestMap.getString("signalId");
        String username=requestMap.getString("username");
        /*校验参数*/
        return fuProductSignalService.updateProductSignal(Integer.parseInt(signalId),requestMap);
    }

    //删除申请信息
    @RequestMapping(value= "/deleteApply",method=RequestMethod.POST)
    public @ResponseBody Boolean deleteApply(){
        // 获取请求参数
        String requestJSONStr = ThreadCache.getPostRequestParams();
        JSONObject requestMap = JSONObject.parseObject(requestJSONStr);
        String signalId=requestMap.getString("signalId");
        String username=requestMap.getString("username");
        /*校验参数*/
        return fuProductSignalService.deleteProductSignal(Integer.parseInt(signalId));
    }

}