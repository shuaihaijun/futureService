package com.future.controller.signal;

import com.alibaba.fastjson.JSONObject;
import com.future.common.enums.GlobalResultCode;
import com.future.common.exception.DataConflictException;
import com.future.common.helper.PageInfoHelper;
import com.future.common.result.RequestParams;
import com.future.common.util.StringUtils;
import com.future.common.util.ThreadCache;
import com.future.entity.product.FuProductSignalApply;
import com.future.service.product.FuProductSignalApplyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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
        String applyId=requestMap.getString("applyId");
        if(StringUtils.isEmpty(applyId)){
            log.error("参数为空！");
            throw new DataConflictException(GlobalResultCode.PARAM_NULL_POINTER);
        }

        /*条件查询日期不能超过1周*/
        return fuProductSignalApplyService.findSignalApplyById(Integer.parseInt(applyId));
    }

    //查找申请信息
    @RequestMapping(value= "/findApply",method=RequestMethod.POST)
    public @ResponseBody
    com.github.pagehelper.Page<FuProductSignalApply> findApply(@RequestBody RequestParams<Map> requestParams) throws Exception {
        Map requestMap = requestParams.getParams();
        PageInfoHelper helper = requestParams.getPageInfoHelper();
        /*条件查询日期不能超过1周*/
        return fuProductSignalApplyService.querySignalApply(requestMap,helper);
    }

    //保存申请信息
    @RequestMapping(value= "/saveApply",method=RequestMethod.POST)
    public @ResponseBody int saveApply(){
        // 获取请求参数
        String requestJSONStr = ThreadCache.getPostRequestParams();
        JSONObject requestMap = JSONObject.parseObject(requestJSONStr);
        /*校验必要的参数*/
        if(ObjectUtils.isEmpty(requestMap)){
            log.error("申请信息不能为空！");
            throw new DataConflictException(GlobalResultCode.PARAM_NULL_POINTER,"申请信息不能为空！");
        }

        try {
            /*这里的异常 到底跑步抛给用户*/
            return fuProductSignalApplyService.saveProductSignal(requestMap);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            return 0;
        }
        /*条件查询日期不能超过1周*/
    }
    //修改申请信息
    @RequestMapping(value= "/saveOrUpdate",method=RequestMethod.POST)
    public @ResponseBody int saveOrUpdate(){
        // 获取请求参数
        String requestJSONStr = ThreadCache.getPostRequestParams();
        JSONObject requestMap = JSONObject.parseObject(requestJSONStr);

        if(ObjectUtils.isEmpty(requestMap)){
            log.error("信息不能为空！");
            throw new DataConflictException(GlobalResultCode.PARAM_NULL_POINTER,"信息不能为空！");
        }
        /*校验参数*/
        if(ObjectUtils.isEmpty(requestMap.getString("id"))){
           // 保存
            return fuProductSignalApplyService.saveProductSignal(requestMap);
        }else {
            //修改
            return fuProductSignalApplyService.updateProductSignalApply(Integer.parseInt(requestMap.getString("id")),requestMap);
        }

    }

    //删除申请信息
    @RequestMapping(value= "/deleteApply",method=RequestMethod.POST)
    public @ResponseBody Boolean deleteApply(){
        // 获取请求参数
        String requestJSONStr = ThreadCache.getPostRequestParams();
        JSONObject requestMap = JSONObject.parseObject(requestJSONStr);

        if(ObjectUtils.isEmpty(requestMap)){
            log.error("信息不能为空！");
            throw new DataConflictException(GlobalResultCode.PARAM_NULL_POINTER,"信息不能为空！");
        }
        String id=requestMap.getString("id");
        /*校验参数*/
        if(ObjectUtils.isEmpty(id)){
            log.error("申请ID不能为空！");
            throw new DataConflictException(GlobalResultCode.PARAM_NULL_POINTER,"申请ID不能为空！");
        }

        /*校验参数*/
        return fuProductSignalApplyService.deleteProductSignalApply(Integer.parseInt(id));
    }

    //提交申请信息
    @RequestMapping(value= "/submitApply",method=RequestMethod.POST)
    public @ResponseBody int submitApply(){
        // 获取请求参数
        String requestJSONStr = ThreadCache.getPostRequestParams();
        JSONObject requestMap = JSONObject.parseObject(requestJSONStr);
        String signalId=requestMap.getString("id");
        String message=requestMap.getString("message");
        /*校验参数*/
        return fuProductSignalApplyService.submitProductSignal(Integer.parseInt(signalId),message);
    }
    //提交审核信息
    @RequestMapping(value= "/reviewProductSignal",method=RequestMethod.POST)
    public @ResponseBody boolean submitCheck(){
        // 获取请求参数
        String requestJSONStr = ThreadCache.getPostRequestParams();
        JSONObject requestMap = JSONObject.parseObject(requestJSONStr);
        String signalId=requestMap.getString("id");
        String state=requestMap.getString("state");
        String message=requestMap.getString("message");
        /*校验参数*/
        fuProductSignalApplyService.reviewProductSignal(Integer.parseInt(signalId),Integer.parseInt(state),message);
        return true;
    }
}