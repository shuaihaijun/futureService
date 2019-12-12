package com.future.controller.com;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.plugins.Page;
import com.future.common.exception.DataConflictException;
import com.future.common.util.StringUtils;
import com.future.common.util.ThreadCache;
import com.future.entity.com.FuComDictionary;
import com.future.service.com.FuComDictionaryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/dictionary")
public class FuComDictionaryController {

    Logger log= LoggerFactory.getLogger(FuComDictionaryController.class);

    @Autowired
    FuComDictionaryService dictionaryService;


    @RequestMapping("/saveDictionary")
    public @ResponseBody int saveDictionnary(){
        // 获取请求参数
        String requestJSONStr = ThreadCache.getPostRequestParams();
        JSONObject requestMap = JSONObject.parseObject(requestJSONStr);
        if(ObjectUtils.isEmpty(requestJSONStr)){
            log.error("数据为空！");
            throw new DataConflictException("数据为空！");
        }
        String dicId=requestMap.getString("id");
        if(StringUtils.isEmpty(dicId)){
            return dictionaryService.saveDictionnary(requestMap);
        }else {
            return dictionaryService.updateDictionary(requestMap);
        }
    }

    @RequestMapping("/queryDictionary")
    public @ResponseBody
    Page<FuComDictionary> queryDictionary(){
        // 获取请求参数
        String requestJSONStr = ThreadCache.getPostRequestParams();
        JSONObject requestMap = JSONObject.parseObject(requestJSONStr);
        if(ObjectUtils.isEmpty(requestJSONStr)){
            log.error("数据为空！");
            throw new DataConflictException("数据为空！");
        }
        return dictionaryService.findByCondition(requestMap);
    }

    @RequestMapping("/queryDictionaryAll")
    public @ResponseBody Map queryDictionaryAll(){
        // 获取请求参数
        String requestJSONStr = ThreadCache.getPostRequestParams();
        JSONObject requestMap = JSONObject.parseObject(requestJSONStr);
        Map dataMap = new HashMap();
        dataMap.put("data",dictionaryService.queryDictionaryAll());
        return dataMap;
    }

    @RequestMapping("/getDictionaryById")
    public @ResponseBody FuComDictionary getDictionaryById(){
        // 获取请求参数
        String requestJSONStr = ThreadCache.getPostRequestParams();
        JSONObject requestMap = JSONObject.parseObject(requestJSONStr);
        if(ObjectUtils.isEmpty(requestJSONStr)){
            log.error("数据为空！");
            throw new DataConflictException("数据为空！");
        }

        String dicId=requestMap.getString("id");
        if(StringUtils.isEmpty(dicId)){
            log.error("ID数据为空！");
            throw new DataConflictException("ID数据为空！");
        }

        return dictionaryService.selectById(dicId);
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

        String dicId=requestMap.getString("id");
        if(StringUtils.isEmpty(dicId)){
            log.error("ID数据为空！");
            throw new DataConflictException("ID数据为空！");
        }

        return dictionaryService.deleteDictionary(Integer.parseInt(dicId));
    }


    @RequestMapping("/updateDictionary")
    public @ResponseBody int updateDictionary(){
        // 获取请求参数
        String requestJSONStr = ThreadCache.getPostRequestParams();
        JSONObject requestMap = JSONObject.parseObject(requestJSONStr);
        if(ObjectUtils.isEmpty(requestJSONStr)){
            log.error("数据为空！");
            throw new DataConflictException("数据为空！");
        }

        return dictionaryService.updateDictionary(requestMap);
    }

}
