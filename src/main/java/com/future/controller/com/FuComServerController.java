package com.future.controller.com;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.plugins.Page;
import com.future.common.exception.DataConflictException;
import com.future.common.result.RequestParams;
import com.future.common.util.StringUtils;
import com.future.common.util.ThreadCache;
import com.future.entity.com.FuComBroker;
import com.future.entity.com.FuComServer;
import com.future.entity.com.FuComServerSlave;
import com.future.service.com.FuComBrokerService;
import com.future.service.com.FuComServerService;
import com.future.service.com.FuComServerSlaveService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/comServer")
public class FuComServerController {

    Logger log= LoggerFactory.getLogger(FuComServerController.class);

    @Autowired
    FuComServerService fuComServerService;
    @Autowired
    FuComBrokerService fuComBrokerService;
    @Autowired
    FuComServerSlaveService fuComServerSlaveService;


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

    @RequestMapping("/queryBroker")
    public @ResponseBody
    List<FuComBroker> queryBroker(){
        // 获取请求参数
        String requestJSONStr = ThreadCache.getPostRequestParams();
        JSONObject requestMap = JSONObject.parseObject(requestJSONStr);
        if(ObjectUtils.isEmpty(requestJSONStr)){
            log.error("数据为空！");
            throw new DataConflictException("数据为空！");
        }
        return fuComBrokerService.selectByMap(null);

    }

    /**
     * 保存从服务器信息
     * @param requestParams
     * @return
     */
    @RequestMapping("/slaveSaveOrUpdate")
    public @ResponseBody boolean slaveSaveOrUpdate(@RequestBody RequestParams<FuComServerSlave> requestParams) {
        FuComServerSlave serverSlave = requestParams.getParams();
        return fuComServerSlaveService.saveOrUpdate(serverSlave);
    }

    /**
     * 根据主服务器名称 查找从服务器名称
     * @param requestParams
     * @return
     */
    @RequestMapping("/getSlaveByServerName")
    public @ResponseBody FuComServerSlave getSlaveByServerName(@RequestBody RequestParams<Map> requestParams) {
        Map condition = requestParams.getParams();
        if(condition==null||condition.get("serverName")==null){
            log.error("根据主服务器名称 查找从服务器名称,参数为空！");
            throw new DataConflictException("根据主服务器名称 查找从服务器名称,参数为空！！");
        }
        return fuComServerSlaveService.getSlaveByServerName(String.valueOf(condition.get("serverName")));
    }
}
