package com.future.controller.com;


import com.alibaba.fastjson.JSONObject;
import com.future.common.exception.DataConflictException;
import com.future.common.util.StringUtils;
import com.future.common.util.ThreadCache;
import com.future.entity.com.FuComDictionary;
import com.future.entity.com.FuComServer;
import com.future.service.com.FuComDictionaryService;
import com.future.service.com.FuComServerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/comDictionary")
public class FuComDictionaryontroller {

    Logger log= LoggerFactory.getLogger(FuComDictionaryontroller.class);

    @Autowired
    FuComDictionaryService dictionaryService;


    @RequestMapping("/saveComDictionnary")
    public @ResponseBody int saveComDictionnary(){
        // 获取请求参数
        String requestJSONStr = ThreadCache.getPostRequestParams();
        JSONObject requestMap = JSONObject.parseObject(requestJSONStr);
        if(ObjectUtils.isEmpty(requestJSONStr)){
            log.error("数据为空！");
            throw new DataConflictException("数据为空！");
        }

        return dictionaryService.saveComDictionnary(requestMap);
    }

    @RequestMapping("/findComDictionary")
    public @ResponseBody List<FuComDictionary> findComDictionary(){
        // 获取请求参数
        String requestJSONStr = ThreadCache.getPostRequestParams();
        JSONObject requestMap = JSONObject.parseObject(requestJSONStr);
        if(ObjectUtils.isEmpty(requestJSONStr)){
            log.error("数据为空！");
            throw new DataConflictException("数据为空！");
        }
        return dictionaryService.findByCondition(requestMap);
    }

    @RequestMapping("/findComDictionaryById")
    public @ResponseBody FuComDictionary findComDictionaryById(){
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

        return dictionaryService.selectById(serverId);
    }


    @RequestMapping("/deleteDictionary")
    public @ResponseBody int deleteDictionary(){
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

        return dictionaryService.deleteDictionary(Integer.parseInt(serverId));
    }


    @RequestMapping("/updateComDictionary")
    public @ResponseBody int updateComDictionary(){
        // 获取请求参数
        String requestJSONStr = ThreadCache.getPostRequestParams();
        JSONObject requestMap = JSONObject.parseObject(requestJSONStr);
        if(ObjectUtils.isEmpty(requestJSONStr)){
            log.error("数据为空！");
            throw new DataConflictException("数据为空！");
        }

        return dictionaryService.updateComDictionary(requestMap);
    }

}
