package com.future.controller.signal;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.plugins.Page;
import com.future.common.enums.GlobalResultCode;
import com.future.common.exception.DataConflictException;
import com.future.common.util.StringUtils;
import com.future.common.util.ThreadCache;
import com.future.entity.product.FuProductSignalApply;
import com.future.service.product.FuProductSignalApplyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/signalApply")
public class ProductSignalApplyController {
    Logger log= LoggerFactory.getLogger(ProductSignalApplyController.class);

    @Autowired
    FuProductSignalApplyService fuProductSignalApplyService;

    //查找申请信息
    @RequestMapping(value= "/findApplyById",method=RequestMethod.POST)
    public @ResponseBody
    FuProductSignalApply findApplyById(){
        // 获取请求参数
        String requestJSONStr = ThreadCache.getPostRequestParams();
        JSONObject requestMap = JSONObject.parseObject(requestJSONStr);
        String signalId=requestMap.getString("signalId");
        if(StringUtils.isEmpty(signalId)){
            log.error("参数为空！");
            throw new DataConflictException(GlobalResultCode.PARAM_NULL_POINTER);
        }

        /*条件查询日期不能超过1周*/
        return fuProductSignalApplyService.findSignalApplyById(Integer.parseInt(signalId));
    }

    //查找申请信息
    @RequestMapping(value= "/findApply",method=RequestMethod.POST)
    public @ResponseBody
    Page<FuProductSignalApply> findApply(){
        // 获取请求参数
        String requestJSONStr = ThreadCache.getPostRequestParams();
        JSONObject requestMap = JSONObject.parseObject(requestJSONStr);
        /*条件查询日期不能超过1周*/
        return fuProductSignalApplyService.findSignalApplyByCondition(requestMap);
    }

    //保存申请信息
    @RequestMapping(value= "/saveApply",method=RequestMethod.POST)
    public @ResponseBody int saveApply(){
        // 获取请求参数
        String requestJSONStr = ThreadCache.getPostRequestParams();
        JSONObject requestMap = JSONObject.parseObject(requestJSONStr);
        /*校验必要的参数*/
        if(ObjectUtils.isEmpty(requestMap.getString("userId"))){
            log.error("申请人信息不能为空！");
            throw new DataConflictException(GlobalResultCode.PARAM_NULL_POINTER,"申请人信息不能为空！");
        }
        if(ObjectUtils.isEmpty(requestMap.getString("signalName"))){
            log.error("信号源名称不能为空！");
            throw new DataConflictException(GlobalResultCode.PARAM_NULL_POINTER,"信号源名称不能为空！！");
        }
        if(ObjectUtils.isEmpty(requestMap.getString("serverName"))){
            log.error("服务器名称不能为空！");
            throw new DataConflictException(GlobalResultCode.PARAM_NULL_POINTER,"服务器名称不能为空！！");
        }
        if(ObjectUtils.isEmpty(requestMap.getString("mtAccId"))){
            log.error("MT账户不能为空！");
            throw new DataConflictException(GlobalResultCode.PARAM_NULL_POINTER,"MT账户不能为空！！");
        }
        if(ObjectUtils.isEmpty(requestMap.getString("mtPasswordWatch"))){
            log.error("MT账户密码不能为空！");
            throw new DataConflictException(GlobalResultCode.PARAM_NULL_POINTER,"MT账户密码不能为空！！");
        }
        if(ObjectUtils.isEmpty(requestMap.getString("email"))){
            log.error("email不能为空！");
            throw new DataConflictException(GlobalResultCode.PARAM_NULL_POINTER,"email不能为空！！");
        }
        if(ObjectUtils.isEmpty(requestMap.getString("phone"))){
            log.error("联系人电话不能为空！");
            throw new DataConflictException(GlobalResultCode.PARAM_NULL_POINTER,"联系人电话不能为空！！");
        }
        if(ObjectUtils.isEmpty(requestMap.getString("qqNumber"))){
            log.error("联系人QQ不能为空！");
            throw new DataConflictException(GlobalResultCode.PARAM_NULL_POINTER,"联系人QQ不能为空！！");
        }

        try {
            /*这里的异常 到底跑步抛给用户*/
            return fuProductSignalApplyService.saveProductSignal(requestMap);
        }catch (Exception e){
            log.error(e.getMessage());
            return 0;
        }
        /*条件查询日期不能超过1周*/
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
        return fuProductSignalApplyService.updateProductSignalApply(Integer.parseInt(signalId),requestMap);
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
        return fuProductSignalApplyService.deleteProductSignalApply(Integer.parseInt(signalId));
    }

    //提交申请信息
    @RequestMapping(value= "/submitApply",method=RequestMethod.POST)
    public @ResponseBody int submitApply(){
        // 获取请求参数
        String requestJSONStr = ThreadCache.getPostRequestParams();
        JSONObject requestMap = JSONObject.parseObject(requestJSONStr);
        String signalId=requestMap.getString("signalId");
        String username=requestMap.getString("username");
        /*校验参数*/
        return fuProductSignalApplyService.submitProductSignal(Integer.parseInt(signalId));
    }

}