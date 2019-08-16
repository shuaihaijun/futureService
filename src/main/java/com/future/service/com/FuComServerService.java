package com.future.service.com;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.future.common.constants.CommonConstant;
import com.future.common.exception.BusinessException;
import com.future.common.exception.DataConflictException;
import com.future.common.util.ConvertUtil;
import com.future.entity.com.FuComServer;
import com.future.mapper.com.FuComServerMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FuComServerService extends ServiceImpl<FuComServerMapper,FuComServer> {

    Logger log= LoggerFactory.getLogger(FuComServerService.class);

    @Autowired
    FuComServerMapper fuComServerMapper;

    /**
     * 根据服务器名称 查询服务器信息
     * @param conditionMap
     * @return
     */
    public List<FuComServer> findByCondition(Map conditionMap){
        if(ObjectUtils.isEmpty(conditionMap)){
            log.error("传入参数为空！");
            throw new DataConflictException("传入参数为空！");
        }

        if(ObjectUtils.isEmpty(conditionMap.get("id"))
                &&ObjectUtils.isEmpty(conditionMap.get("serverName"))
                &&ObjectUtils.isEmpty(conditionMap.get("brokerId"))
                &&ObjectUtils.isEmpty(conditionMap.get("serverState"))
                &&ObjectUtils.isEmpty(conditionMap.get("brokerName"))
                &&ObjectUtils.isEmpty(conditionMap.get("serverIp"))){
            log.error("请传入必要参数！");
            throw new DataConflictException("请传入必要参数！");
        }

        try {
            return fuComServerMapper.selectByMap(conditionMap);
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
    public int saveComServer(Map dataMap){
        /*校验保存数据*/
        if(ObjectUtils.isEmpty(dataMap)){
            log.error("传入参数为空！");
            throw new DataConflictException("传入参数为空！");
        }

        if(ObjectUtils.isEmpty(dataMap.get("serverName"))
                ||ObjectUtils.isEmpty(dataMap.get("brokerId"))
                ||ObjectUtils.isEmpty(dataMap.get("brokerName"))
                ||ObjectUtils.isEmpty(dataMap.get("serverIp"))){
            log.error("请传入必要参数！");
            throw new DataConflictException("请传入必要参数！");
        }

        try {
            if(fuComServerMapper.selectByMap(dataMap).size()>0){
                log.error("数据已存在!");
                throw new DataConflictException("数据已存在!");
            }
            /*map 转 entity*/
            FuComServer fuComServer=(FuComServer) ConvertUtil.MapToJavaBean((HashMap) dataMap,FuComServer.class);

            fuComServer.setServerState(CommonConstant.COMMON_SERVER_STATE_NORMAL);
            /*修改数据*/
            return fuComServerMapper.insert(fuComServer);
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
    public int updateComServer(Map dataMap){
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
            FuComServer fuComServer=(FuComServer) ConvertUtil.MapToJavaBean((HashMap) dataMap,FuComServer.class);

            /*修改数据*/
            return fuComServerMapper.updateByPrimaryKey(fuComServer);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            throw new BusinessException(e);
        }
    }

    /**
     * 删除交易商信息
     * @param serverId
     * @return
     */
    public int deleteServer(int serverId){
        if(serverId<1){
            log.error("传入参数Id为空！");
            throw new DataConflictException("传入参数Id为空！");
        }
        try {
            /*作废数据*/
            FuComServer fuComServer=new FuComServer();
            fuComServer.setId(serverId);
            fuComServer.setServerState(CommonConstant.COMMON_SERVER_STATE_INVALID);
            return fuComServerMapper.updateByPrimaryKey(fuComServer);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            throw new BusinessException(e);
        }
    }

}
