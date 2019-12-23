package com.future.controller.com;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.plugins.Page;
import com.future.common.exception.DataConflictException;
import com.future.common.util.StringUtils;
import com.future.common.util.ThreadCache;
import com.future.entity.com.FuComAgent;
import com.future.entity.com.FuComAgentApply;
import com.future.service.com.FuComAgentApplyService;
import com.future.service.com.FuComAgentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/agent")
public class FuComAgentController {

    Logger log= LoggerFactory.getLogger(FuComAgentController.class);

    @Autowired
    FuComAgentApplyService fuComAgentApplyService;
    @Autowired
    FuComAgentService fuComAgentService;


    @RequestMapping("/saveAgentApply")
    public @ResponseBody int saveAgentApply(){
        // 获取请求参数
        String requestJSONStr = ThreadCache.getPostRequestParams();
        JSONObject requestMap = JSONObject.parseObject(requestJSONStr);
        if(ObjectUtils.isEmpty(requestJSONStr)){
            log.error("数据为空！");
            throw new DataConflictException("数据为空！");
        }
        String serverId=requestMap.getString("id");
        if(StringUtils.isEmpty(serverId)){
            return fuComAgentApplyService.saveAgentApply(requestMap);
        }else {
            return fuComAgentApplyService.updateAgentApply(Integer.parseInt(serverId),requestMap);
        }
    }

    @RequestMapping("/queryAgentApply")
    public @ResponseBody
    Page<FuComAgentApply> queryAgentApply(){
        // 获取请求参数
        String requestJSONStr = ThreadCache.getPostRequestParams();
        JSONObject requestMap = JSONObject.parseObject(requestJSONStr);
        if(ObjectUtils.isEmpty(requestJSONStr)){
            log.error("数据为空！");
            throw new DataConflictException("数据为空！");
        }
        return fuComAgentApplyService.findAgentApplyByCondition(requestMap);
    }

    @RequestMapping("/getAgentApplyById")
    public @ResponseBody FuComAgentApply getAgentApplyById(){
        // 获取请求参数
        String requestJSONStr = ThreadCache.getPostRequestParams();
        JSONObject requestMap = JSONObject.parseObject(requestJSONStr);
        if(ObjectUtils.isEmpty(requestJSONStr)){
            log.error("数据为空！");
            throw new DataConflictException("数据为空！");
        }

        String applyId=requestMap.getString("id");
        if(StringUtils.isEmpty(applyId)){
            log.error("ID数据为空！");
            throw new DataConflictException("ID数据为空！");
        }

        return fuComAgentApplyService.selectById(applyId);
    }


    @RequestMapping("/deleteAgentApply")
    public @ResponseBody Boolean deleteAgentApply(){
        // 获取请求参数
        String requestJSONStr = ThreadCache.getPostRequestParams();
        JSONObject requestMap = JSONObject.parseObject(requestJSONStr);
        if(ObjectUtils.isEmpty(requestJSONStr)){
            log.error("数据为空！");
            throw new DataConflictException("数据为空！");
        }

        String applyId=requestMap.getString("id");
        if(StringUtils.isEmpty(applyId)){
            log.error("ID数据为空！");
            throw new DataConflictException("ID数据为空！");
        }

        return fuComAgentApplyService.deleteAgentApply(Integer.parseInt(applyId));
    }


    @RequestMapping("/updateAgentApply")
    public @ResponseBody int updateAgentApply(){
        // 获取请求参数
        String requestJSONStr = ThreadCache.getPostRequestParams();
        JSONObject requestMap = JSONObject.parseObject(requestJSONStr);
        if(ObjectUtils.isEmpty(requestJSONStr)){
            log.error("数据为空！");
            throw new DataConflictException("数据为空！");
        }
        String applyId=requestMap.getString("id");
        if(StringUtils.isEmpty(applyId)){
            log.error("ID数据为空！");
            throw new DataConflictException("ID数据为空！");
        }
        return fuComAgentApplyService.updateAgentApply(Integer.parseInt(applyId),requestMap);
    }

    @RequestMapping("/submitAgentApply")
    public @ResponseBody int submitAgentApply(){
        // 获取请求参数
        String requestJSONStr = ThreadCache.getPostRequestParams();
        JSONObject requestMap = JSONObject.parseObject(requestJSONStr);
        if(ObjectUtils.isEmpty(requestJSONStr)){
            log.error("数据为空！");
            throw new DataConflictException("数据为空！");
        }
        String applyId=requestMap.getString("id");
        if(StringUtils.isEmpty(applyId)){
            log.error("ID数据为空！");
            throw new DataConflictException("ID数据为空！");
        }
        String mesage=requestMap.getString("mesage");
        return fuComAgentApplyService.submitAgentApply(Integer.parseInt(applyId),mesage);
    }

    @RequestMapping("/reviewAgentApply")
    public @ResponseBody boolean reviewAgentApply(){
        // 获取请求参数
        String requestJSONStr = ThreadCache.getPostRequestParams();
        JSONObject requestMap = JSONObject.parseObject(requestJSONStr);
        if(ObjectUtils.isEmpty(requestJSONStr)){
            log.error("数据为空！");
            throw new DataConflictException("数据为空！");
        }
        String id=requestMap.getString("id");
        String applyState=requestMap.getString("applyState");
        if(StringUtils.isEmpty(id)||StringUtils.isEmpty(applyState)){
            log.error("数据为空！");
            throw new DataConflictException("数据为空！");
        }
        String message=requestMap.getString("message");
        fuComAgentApplyService.reviewAgentApply(Integer.parseInt(id),Integer.parseInt(applyState),message);
        return true;
    }

    @RequestMapping("/queryAgent")
    public @ResponseBody
    Page<FuComAgent> queryAgent(){
        // 获取请求参数
        String requestJSONStr = ThreadCache.getPostRequestParams();
        JSONObject requestMap = JSONObject.parseObject(requestJSONStr);
        if(ObjectUtils.isEmpty(requestJSONStr)){
            log.error("数据为空！");
            throw new DataConflictException("数据为空！");
        }
        return fuComAgentService.findAgentByCondition(requestMap);
    }
}
