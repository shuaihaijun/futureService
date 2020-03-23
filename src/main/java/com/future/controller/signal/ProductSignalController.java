package com.future.controller.signal;

import com.alibaba.fastjson.JSONObject;
import com.future.common.enums.GlobalResultCode;
import com.future.common.exception.ParameterInvalidException;
import com.future.common.helper.PageInfoHelper;
import com.future.common.result.RequestParams;
import com.future.common.util.ThreadCache;
import com.future.entity.product.FuProductSignal;
import com.future.entity.product.FuProductSignalValuation;
import com.future.entity.user.FuUserFollows;
import com.future.pojo.vo.signal.FuFollowStateVO;
import com.future.pojo.vo.signal.FuFollowUserVO;
import com.future.pojo.vo.signal.FuUserSignalVO;
import com.future.service.product.FuProductSignalService;
import com.future.service.product.FuProductSignalValuationService;
import com.future.service.user.FuUserFollowsService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/signal")
public class ProductSignalController {
    Logger log= LoggerFactory.getLogger(ProductSignalController.class);

    @Autowired
    FuProductSignalService fuProductSignalService;
    @Autowired
    FuUserFollowsService fuUserFollowsService;
    @Autowired
    FuProductSignalValuationService fuProductSignalValuationService;

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

    //查找信息
    @RequestMapping(value= "/querySignalInfos",method=RequestMethod.POST)
    public @ResponseBody
    com.github.pagehelper.Page<FuProductSignal> querySignalInfos(@RequestBody RequestParams<Map> requestParams) {
        Map orderCondition = requestParams.getParams();
        PageInfoHelper helper = requestParams.getPageInfoHelper();
        return fuProductSignalService.querySignalInfos(orderCondition,helper);
    }

    //查找允许跟随信号源信息
    @RequestMapping(value= "/querySignalAllowed",method=RequestMethod.POST)
    public @ResponseBody
    com.github.pagehelper.Page<FuProductSignal> querySignalAllowed(@RequestBody RequestParams<Map> requestParams) {
        Map orderCondition = requestParams.getParams();
        PageInfoHelper helper = requestParams.getPageInfoHelper();
        return fuProductSignalService.querySignalAllowed(orderCondition,helper);
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
    public @ResponseBody
    PageInfo<FuUserSignalVO> querySignalUsers(){
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
    public @ResponseBody Page<FuUserFollows> signalFollowsQuery(@RequestBody RequestParams<Map> requestParams) {
        // 获取请求参数
        Map conditionMap = requestParams.getParams();
        PageInfoHelper helper = requestParams.getPageInfoHelper();
        /*校验参数*/
        return fuUserFollowsService.querySignalFollows(conditionMap,helper);
    }

    //查询跟单信息
    @RequestMapping(value= "/querySignalFollowState",method=RequestMethod.POST)
    public @ResponseBody
    PageInfo<FuFollowStateVO> querySignalFollowState(@RequestBody RequestParams<Map> requestParams) {
        Map conditionMap = requestParams.getParams();
        PageInfoHelper helper = requestParams.getPageInfoHelper();
        /*校验参数*/
        return fuUserFollowsService.querySignalFollowState(conditionMap,helper);
    }

    //删除申请信息
    @RequestMapping(value= "/signalFollowsRemove",method=RequestMethod.POST)
    public @ResponseBody boolean signalFollowsRemove(){
        // 获取请求参数
        String requestJSONStr = ThreadCache.getPostRequestParams();
        JSONObject requestMap = JSONObject.parseObject(requestJSONStr);
        Map conditionMap = requestMap.getInnerMap();
        /*校验参数*/
        fuUserFollowsService.signalFollowsRemove(conditionMap);
        return true;
    }

    //信号源订阅信息保存/修改
    @RequestMapping(value= "/signalFollowsSaveOrUpdate",method=RequestMethod.POST)
    public @ResponseBody boolean signalFollowsSaveOrUpdate(){
        // 获取请求参数
        String requestJSONStr = ThreadCache.getPostRequestParams();
        JSONObject requestMap = JSONObject.parseObject(requestJSONStr);
        Map conditionMap = requestMap.getInnerMap();
        /*校验参数*/
        fuUserFollowsService.signalFollowsSaveOrUpdate(conditionMap);
        return true;
    }

    //查询跟单信息
    @RequestMapping(value= "/getSignalFollowByCondition",method=RequestMethod.POST)
    public @ResponseBody FuFollowStateVO getSignalFollowByCondition(@RequestBody RequestParams<Map> requestParams) {
        Map conditionMap = requestParams.getParams();
        /*校验参数*/
        return fuUserFollowsService.getSignalFollowByCondition(conditionMap);
    }

    //查询跟单信息
    @RequestMapping(value= "/queryFollowUsers",method=RequestMethod.POST)
    public @ResponseBody PageInfo<FuFollowUserVO> queryFollowUsers(){
        // 获取请求参数
        String requestJSONStr = ThreadCache.getPostRequestParams();
        JSONObject requestMap = JSONObject.parseObject(requestJSONStr);
        /*校验参数*/
        return fuUserFollowsService.queryFollowUsers(requestMap);
    }

    //查询跟单信息
    @RequestMapping(value= "/getSignalValuation",method=RequestMethod.POST)
    public @ResponseBody
    FuProductSignalValuation getSignalValuation(){
        // 获取请求参数
        String requestJSONStr = ThreadCache.getPostRequestParams();
        JSONObject requestMap = JSONObject.parseObject(requestJSONStr);
        if(requestMap.get("signalId")==null){
            throw new ParameterInvalidException(GlobalResultCode.PARAM_NULL_POINTER);
        }
        Integer signalId=requestMap.getInteger("signalId");
        /*校验参数*/
        return fuProductSignalValuationService.getSignalValuation(signalId);
    }
}