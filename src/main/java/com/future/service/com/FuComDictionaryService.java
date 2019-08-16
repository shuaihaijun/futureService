package com.future.service.com;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.future.common.constants.CommonConstant;
import com.future.common.exception.BusinessException;
import com.future.common.exception.DataConflictException;
import com.future.common.util.ConvertUtil;
import com.future.entity.com.FuComDictionary;
import com.future.entity.com.FuComServer;
import com.future.mapper.com.FuComDictionaryMapper;
import com.future.mapper.com.FuComServerMapper;
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

    Logger log= LoggerFactory.getLogger(FuComDictionaryService.class);

    @Autowired
    FuComDictionaryMapper fuComDictionaryMapper;

    /**
     * 根据服务器名称 查询服务器信息
     * @param conditionMap
     * @return
     */
    public List<FuComDictionary> findByCondition(Map conditionMap){
        if(ObjectUtils.isEmpty(conditionMap)){
            log.error("传入参数为空！");
            throw new DataConflictException("传入参数为空！");
        }

        if(ObjectUtils.isEmpty(conditionMap.get("id"))
                &&ObjectUtils.isEmpty(conditionMap.get("sign"))
                &&ObjectUtils.isEmpty(conditionMap.get("key"))
                &&ObjectUtils.isEmpty(conditionMap.get("value"))){
            log.error("请传入必要参数！");
            throw new DataConflictException("请传入必要参数！");
        }

        try {
            return fuComDictionaryMapper.selectByMap(conditionMap);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            throw new BusinessException(e);
        }
    }


    /**
     * 保存服务器信息
     * @param dataMap
     * @return
     */
    public int saveComDictionnary(Map dataMap){
        /*校验保存数据*/
        if(ObjectUtils.isEmpty(dataMap)){
            log.error("传入参数为空！");
            throw new DataConflictException("传入参数为空！");
        }

        if(ObjectUtils.isEmpty(ObjectUtils.isEmpty(dataMap.get("sign"))
                ||ObjectUtils.isEmpty(dataMap.get("key"))
                ||ObjectUtils.isEmpty(dataMap.get("value")))){
            log.error("请传入必要参数！");
            throw new DataConflictException("请传入必要参数！");
        }

        try {
            if(fuComDictionaryMapper.selectByMap(dataMap).size()>0){
                log.error("该条数据已存在！");
                throw new DataConflictException("该条数据已存在！");
            }
            /*map 转 entity*/
            FuComDictionary dictionary=(FuComDictionary) ConvertUtil.MapToJavaBean((HashMap) dataMap,FuComDictionary.class);

            dictionary.setCreateDate(new Date());
            dictionary.setModifyDate(new Date());
            /*修改数据*/
            return fuComDictionaryMapper.insert(dictionary);
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
    public int updateComDictionary(Map dataMap){
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
            FuComDictionary fuComServer=(FuComDictionary) ConvertUtil.MapToJavaBean((HashMap) dataMap,FuComDictionary.class);

            /*修改数据*/
            return fuComDictionaryMapper.updateByPrimaryKey(fuComServer);
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
