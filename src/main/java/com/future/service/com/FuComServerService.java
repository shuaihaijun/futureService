package com.future.service.com;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.future.common.constants.CommonConstant;
import com.future.common.exception.BusinessException;
import com.future.common.exception.DataConflictException;
import com.future.common.util.ConvertUtil;
import com.future.common.util.PageBean;
import com.future.entity.com.FuComServer;
import com.future.mapper.com.FuComServerMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.HashMap;
import java.util.Map;

@Service
public class FuComServerService extends ServiceImpl<FuComServerMapper,FuComServer> {

    Logger log= LoggerFactory.getLogger(FuComServerService.class);

    @Autowired
    FuComServerMapper fuComServerMapper;

    /**
     * 根据* 查询服务器信息
     * @param conditionMap
     * @return
     */
    public Page<FuComServer> queryComServer(Map conditionMap){
       /* if(ObjectUtils.isEmpty(conditionMap)){
            log.error("传入参数为空！");
            throw new DataConflictException("传入参数为空！");
        }
*/
        /*if(ObjectUtils.isEmpty(conditionMap.get("id"))
                &&ObjectUtils.isEmpty(conditionMap.get("serverName"))
                &&ObjectUtils.isEmpty(conditionMap.get("serverState"))
                &&ObjectUtils.isEmpty(conditionMap.get("brokerName"))
                &&ObjectUtils.isEmpty(conditionMap.get("serverIp"))){
            log.error("请传入必要参数！");
            throw new DataConflictException("请传入必要参数！");
        }*/

        try {
            /*校验信息*/
            Wrapper<FuComServer> wrapper = new EntityWrapper<FuComServer>();
            PageBean<FuComServer> pageBean = new PageBean<FuComServer>();
            Page<FuComServer> page = pageBean.getPage(conditionMap);

            if (!ObjectUtils.isEmpty(conditionMap.get("id"))) {
                wrapper.eq(FuComServer.SERVER_ID, conditionMap.get("id"));
            }
            if (!ObjectUtils.isEmpty(conditionMap.get("serverName"))) {
                wrapper.eq(FuComServer.SERVER_NAME, conditionMap.get("serverName"));
            }
            if (!ObjectUtils.isEmpty(conditionMap.get("serverState"))) {
                wrapper.eq(FuComServer.SERVER_STATE, conditionMap.get("serverState"));
            }
            if (!ObjectUtils.isEmpty(conditionMap.get("brokerName"))) {
                wrapper.eq(FuComServer.BROKER_NAME, conditionMap.get("brokerName"));
            }
            if (!ObjectUtils.isEmpty(conditionMap.get("serverIp"))) {
                wrapper.eq(FuComServer.SERVER_IP, conditionMap.get("serverIp"));
            }
            if (!ObjectUtils.isEmpty(conditionMap.get("localPort"))) {
                wrapper.eq(FuComServer.LOCAL_PORT, conditionMap.get("localPort"));
            }
            wrapper.orderBy(FuComServer.SERVER_ID,false);
            return this.selectPage(page,wrapper);
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
                ||ObjectUtils.isEmpty(dataMap.get("brokerName"))){
            log.error("请传入必要参数！");
            throw new DataConflictException("请传入必要参数！");
        }

        try {
            Wrapper<FuComServer> wrapper = new EntityWrapper<FuComServer>();

            if (!ObjectUtils.isEmpty(dataMap.get("id"))) {
                throw new DataConflictException("数据已存在！");
            }
            if (!ObjectUtils.isEmpty(dataMap.get("serverName"))) {
                wrapper.eq(FuComServer.SERVER_NAME, dataMap.get("serverName"));
            }
            if (!ObjectUtils.isEmpty(dataMap.get("brokerName"))) {
                wrapper.eq(FuComServer.BROKER_NAME, dataMap.get("brokerName"));
            }
            if (!ObjectUtils.isEmpty(dataMap.get("serverIp"))) {
                wrapper.eq(FuComServer.SERVER_IP, dataMap.get("serverIp"));
            }

            if(fuComServerMapper.selectList(wrapper).size()>0){
                log.error("该条数据已存在！");
                throw new DataConflictException("该条数据已存在！");
            }
            /*map 转 entity*/
            //将对象转换成为字符串
            String str = JSON.toJSONString(dataMap);
            //字符串转换成为对象
            HashMap infoMap = JSON.parseObject(str, HashMap.class);
            FuComServer fuComServer=(FuComServer) ConvertUtil.MapToJavaBean(infoMap,FuComServer.class);

            fuComServer.setServerState(CommonConstant.COMMON_SERVER_STATE_NORMAL);
            /*修改数据*/
            return fuComServerMapper.insertSelective(fuComServer);
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
            //将对象转换成为字符串
            String str = JSON.toJSONString(dataMap);
            //字符串转换成为对象
            HashMap infoMap = JSON.parseObject(str, HashMap.class);
            FuComServer fuComServer=(FuComServer) ConvertUtil.MapToJavaBean(infoMap,FuComServer.class);

            /*修改数据*/
            return fuComServerMapper.updateByPrimaryKeySelective(fuComServer);
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
            return fuComServerMapper.deleteByPrimaryKey(serverId);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            throw new BusinessException(e);
        }
    }

}
