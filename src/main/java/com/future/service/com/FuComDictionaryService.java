package com.future.service.com;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.future.common.exception.BusinessException;
import com.future.common.exception.DataConflictException;
import com.future.common.util.CommonUtil;
import com.future.common.util.ConvertUtil;
import com.future.common.util.JsonUtils;
import com.future.common.util.PageBean;
import com.future.entity.com.FuComDictionary;
import com.future.entity.product.FuProductSignalApply;
import com.future.mapper.com.FuComDictionaryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FuComDictionaryService extends ServiceImpl<FuComDictionaryMapper, FuComDictionary> {

    Logger log = LoggerFactory.getLogger(FuComDictionaryService.class);

    @Autowired
    FuComDictionaryMapper fuComDictionaryMapper;

    /**
     * 查找所有数据
     * @return
     */
    public String queryDictionaryAll() {
        try {
            List<FuComDictionary> dictionaries= selectByMap(null);
            JSONObject json=new JSONObject();
            JSONArray midJson=new JSONArray();
            for(int i=0;i< dictionaries.size();i++){
                if(json.get(dictionaries.get(i).getDicSign())!=null){
                    // 多个
                    if(JSONArray.isValidArray(json.getJSONObject(dictionaries.get(i).getDicSign()).toJSONString())){
                        midJson = json.getJSONArray(dictionaries.get(i).getDicSign());
                    }else {
                        midJson.add(json.getJSONObject(dictionaries.get(i).getDicSign()));
                    }
                    midJson.add(JSONObject.toJSON(dictionaries.get(i)));
                    json.put(dictionaries.get(i).getDicSign(),midJson);
                }else {
                    json.put(dictionaries.get(i).getDicSign(),JSONObject.toJSON(dictionaries.get(i)));
                }
            }
            log.info(json.toJSONString());
            return json.toJSONString();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new BusinessException(e);
        }
    }


    /**
     * 根据服务器名称 查询服务器信息
     *
     * @param conditionMap
     * @return
     */
    public Page<FuComDictionary> findByCondition(Map conditionMap) {
        if (ObjectUtils.isEmpty(conditionMap)) {
            log.error("传入参数为空！");
            throw new DataConflictException("传入参数为空！");
        }

        /*if (ObjectUtils.isEmpty(conditionMap.get("id"))
                && ObjectUtils.isEmpty(conditionMap.get("dicSign"))
                && ObjectUtils.isEmpty(conditionMap.get("dicKey"))
                && ObjectUtils.isEmpty(conditionMap.get("dicValue"))) {
            log.error("请传入必要参数！");
            throw new DataConflictException("请传入必要参数！");
        }*/
        /*校验信息*/
        Wrapper<FuComDictionary> wrapper = new EntityWrapper<FuComDictionary>();
        PageBean<FuComDictionary> pageBean = new PageBean<FuComDictionary>();
        Page<FuComDictionary> page = pageBean.getPage(conditionMap);

        if (!ObjectUtils.isEmpty(conditionMap.get("id"))) {
            wrapper.eq(FuComDictionary.DIC_ID, conditionMap.get("id"));
        }
        if (!ObjectUtils.isEmpty(conditionMap.get("dicSign"))) {
            wrapper.eq(FuComDictionary.DIC_SIGN, conditionMap.get("dicSign"));
        }
        if (!ObjectUtils.isEmpty(conditionMap.get("dicKey"))) {
            wrapper.eq(FuComDictionary.DIC_KEY, conditionMap.get("dicKey"));
        }
        if (!ObjectUtils.isEmpty(conditionMap.get("dicName"))) {
            wrapper.eq(FuComDictionary.DIC_NAME, conditionMap.get("dicName"));
        }

        try {
            return selectPage(page, wrapper);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new BusinessException(e);
        }
    }

    /**
     * 根据ID 查找数据字典信息
     * @param id
     * @return
     */
    public FuComDictionary findById(Integer id) {
        if(id<=0){
            log.error("传入参数为空!");
            throw new DataConflictException("传入参数为空!");
        }
        return findById(id);
    }


    /**
     * 保存服务器信息
     * @param dataMap
     * @return
     */
    public int saveDictionnary(Map dataMap){
        /*校验保存数据*/
        if(ObjectUtils.isEmpty(dataMap)){
            log.error("传入参数为空！");
            throw new DataConflictException("传入参数为空！");
        }

        if(ObjectUtils.isEmpty(ObjectUtils.isEmpty(dataMap.get("dicSign"))
                ||ObjectUtils.isEmpty(dataMap.get("dicKey"))
                ||ObjectUtils.isEmpty(dataMap.get("dicValue")))){
            log.error("请传入必要参数！");
            throw new DataConflictException("请传入必要参数！");
        }

        try {
            Wrapper<FuComDictionary> wrapper = new EntityWrapper<FuComDictionary>();

            if (!ObjectUtils.isEmpty(dataMap.get("id"))) {
                throw new DataConflictException("数据已存在！");
            }
            if (!ObjectUtils.isEmpty(dataMap.get("dicSign"))) {
                wrapper.eq(FuComDictionary.DIC_SIGN, dataMap.get("dicSign"));
            }
            if (!ObjectUtils.isEmpty(dataMap.get("dicKey"))) {
                wrapper.eq(FuComDictionary.DIC_KEY, dataMap.get("dicKey"));
            }
            if (!ObjectUtils.isEmpty(dataMap.get("dicName"))) {
                wrapper.eq(FuComDictionary.DIC_NAME, dataMap.get("dicName"));
            }

            if(fuComDictionaryMapper.selectList(wrapper).size()>0){
                log.error("该条数据已存在！");
                throw new DataConflictException("该条数据已存在！");
            }
            /*map 转 entity*/
            //将对象转换成为字符串
            String str = JSON.toJSONString(dataMap);
            //字符串转换成为对象
            HashMap infoMap = JSON.parseObject(str, HashMap.class);
            FuComDictionary dictionary=(FuComDictionary) ConvertUtil.MapToJavaBean(infoMap,FuComDictionary.class);

            dictionary.setCreateDate(new Date());
            dictionary.setModifyDate(new Date());
            /*修改数据*/
            return fuComDictionaryMapper.insertSelective(dictionary);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            throw new BusinessException(e);
        }
    }

    /**
     * 修改信息
     * @param dataMap
     * @return
     */
    public int updateDictionary(Map dataMap){
        /*校验保存数据*/
        if(ObjectUtils.isEmpty(dataMap)){
            log.error("传入参数为空！");
            throw new DataConflictException("传入参数为空！");
        }

        if(ObjectUtils.isEmpty(dataMap.get("id"))){
            log.error("传入参数Id为空！");
            throw new DataConflictException("传入参数Id为空！");
        }

        try {
            /*map 转 entity*/
            //将对象转换成为字符串
            String str = JSON.toJSONString(dataMap);
            //字符串转换成为对象
            HashMap infoMap = JSON.parseObject(str, HashMap.class);
            FuComDictionary fuComServer=(FuComDictionary) ConvertUtil.MapToJavaBean(infoMap,FuComDictionary.class);

            /*修改数据*/
            return fuComDictionaryMapper.updateByPrimaryKeySelective(fuComServer);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            throw new BusinessException(e);
        }
    }

    /**
     * 删除信息
     * @param serverId
     * @return
     */
    public int deleteDictionary(int serverId){
        if(serverId<1){
            log.error("传入参数Id为空！");
            throw new DataConflictException("传入参数Id为空！");
        }
        try {
            /*作废数据*/
            return fuComDictionaryMapper.deleteById(serverId);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            throw new BusinessException(e);
        }
    }

}
