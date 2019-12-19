package com.future.controller.signal;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.plugins.Page;
import com.future.common.util.ThreadCache;
import com.future.entity.product.FuProductSignal;
import com.future.entity.product.FuProductSignalApply;
import com.future.entity.user.FuUserFollows;
import com.future.pojo.vo.signal.FuUserSignalVO;
import com.future.service.product.FollowsService;
import com.future.service.product.FuProductSignalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/signal")
public class ProductSignalController {
    Logger log= LoggerFactory.getLogger(ProductSignalController.class);

    @Autowired
    FuProductSignalService fuProductSignalService;
    @Autowired
    FollowsService followsService;

    //查找申请信息
    @RequestMapping(value= "/findSignalById",method=RequestMethod.POST)
    public @ResponseBody
    FuProductSignal findSignalById(){
        // 获取请求参数
        String requestJSONStr = ThreadCache.getPostRequestParams();
        JSONObject requestMap = JSONObject.parseObject(requestJSONStr);
        String signalId=requestMap.getString("signalId");
        String username=requestMap.getString("username");

        /*条件查询日期不能超过1周*/
        return fuProductSignalService.findSignalById(Integer.parseInt(signalId));
    }

    //查找申请信息
    @RequestMapping(value= "/getSignalInfos",method=RequestMethod.POST)
    public @ResponseBody
    Page<FuProductSignal>getSignalInfos(){
        // 获取请求参数
        String requestJSONStr = ThreadCache.getPostRequestParams();
        JSONObject requestMap = JSONObject.parseObject(requestJSONStr);

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
    //查询信号源用户信息
    @RequestMapping(value= "/querySignalUsers",method=RequestMethod.POST)
    public @ResponseBody Page<FuUserSignalVO> querySignalUsers(){
        // 获取请求参数
        String requestJSONStr = ThreadCache.getPostRequestParams();
        JSONObject requestMap = JSONObject.parseObject(requestJSONStr);
        return fuProductSignalService.querySignalUsers(requestMap);
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

    //查询跟单信息
    @RequestMapping(value= "/signalFollowsQuery",method=RequestMethod.POST)
    public @ResponseBody List<FuUserFollows> signalFollowsQuery(){
        // 获取请求参数
        String requestJSONStr = ThreadCache.getPostRequestParams();
        JSONObject requestMap = JSONObject.parseObject(requestJSONStr);
        Map conditionMap = requestMap.getInnerMap();
        /*校验参数*/
        return followsService.signalFollowsQuery(conditionMap);
    }

    //删除申请信息
    @RequestMapping(value= "/signalFollowsRemove",method=RequestMethod.POST)
    public @ResponseBody void signalFollowsRemove(){
        // 获取请求参数
        String requestJSONStr = ThreadCache.getPostRequestParams();
        JSONObject requestMap = JSONObject.parseObject(requestJSONStr);
        Map conditionMap = requestMap.getInnerMap();
        /*校验参数*/
        followsService.signalFollowsRemove(conditionMap);
    }

    //信号源订阅信息保存/修改
    @RequestMapping(value= "/signalFollowsSaveOrUpdate",method=RequestMethod.POST)
    public @ResponseBody void signalFollowsSaveOrUpdate(){
        // 获取请求参数
        String requestJSONStr = ThreadCache.getPostRequestParams();
        JSONObject requestMap = JSONObject.parseObject(requestJSONStr);
        Map conditionMap = requestMap.getInnerMap();
        /*校验参数*/
        followsService.signalFollowsSaveOrUpdate(conditionMap);
    }
}