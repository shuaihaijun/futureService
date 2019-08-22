package com.future.controller.com;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.plugins.Page;
import com.future.common.exception.DataConflictException;
import com.future.common.util.StringUtils;
import com.future.common.util.ThreadCache;
import com.future.entity.com.FuComServer;
import com.future.service.com.FuComServerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/comServer")
public class FuComServerController {

    Logger log= LoggerFactory.getLogger(FuComServerController.class);

    @Autowired
    FuComServerService fuComServerService;


    @RequestMapping("/saveComServer")
    public @ResponseBody int saveComServer(){
        // 获取请求参数
        String requestJSONStr = ThreadCache.getPostRequestParams();
        JSONObject requestMap = JSONObject.parseObject(requestJSONStr);
        if(ObjectUtils.isEmpty(requestJSONStr)){
            log.error("数据为空！");
            throw new DataConflictException("数据为空！");
        }

        String serverId=requestMap.getString("id");
        if(StringUtils.isEmpty(serverId)){
            return fuComServerService.saveComServer(requestMap);
        }else {
            return fuComServerService.updateComServer(requestMap);
        }
    }

    @RequestMapping("/queryComServer")
    public @ResponseBody
    Page<FuComServer> queryComServer(){
        // 获取请求参数
        String requestJSONStr = ThreadCache.getPostRequestParams();
        JSONObject requestMap = JSONObject.parseObject(requestJSONStr);
        if(ObjectUtils.isEmpty(requestJSONStr)){
            log.error("数据为空！");
            throw new DataConflictException("数据为空！");
        }
        return fuComServerService.queryComServer(requestMap);
    }

    @RequestMapping("/findComServerById")
    public @ResponseBody FuComServer findComServerById(){
        // 获取请求参数
        String requestJSONStr = ThreadCache.getPostRequestParams();
        JSONObject requestMap = JSONObject.parseObject(requestJSONStr);
        if(ObjectUtils.isEmpty(requestJSONStr)){
            log.error("数据为空！");
            throw new DataConflictException("数据为空！");
        }

        String serverId=requestMap.getString("id");
        if(StringUtils.isEmpty(requestJSONStr)){
            log.error("ID数据为空！");
            throw new DataConflictException("ID数据为空！");
        }

        return fuComServerService.selectById(serverId);
    }


    @RequestMapping("/deleteServer")
    public @ResponseBody int deleteServer(){
        // 获取请求参数
        String requestJSONStr = ThreadCache.getPostRequestParams();
        JSONObject requestMap = JSONObject.parseObject(requestJSONStr);
        if(ObjectUtils.isEmpty(requestJSONStr)){
            log.error("数据为空！");
            throw new DataConflictException("数据为空！");
        }

        String serverId=requestMap.getString("id");
        if(StringUtils.isEmpty(requestJSONStr)){
            log.error("ID数据为空！");
            throw new DataConflictException("ID数据为空！");
        }

        return fuComServerService.deleteServer(Integer.parseInt(serverId));
    }


    @RequestMapping("/updateComServer")
    public @ResponseBody int updateComServer(){
        // 获取请求参数
        String requestJSONStr = ThreadCache.getPostRequestParams();
        JSONObject requestMap = JSONObject.parseObject(requestJSONStr);
        if(ObjectUtils.isEmpty(requestJSONStr)){
            log.error("数据为空！");
            throw new DataConflictException("数据为空！");
        }

        return fuComServerService.updateComServer(requestMap);
    }

}
