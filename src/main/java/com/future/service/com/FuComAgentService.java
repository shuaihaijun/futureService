package com.future.service.com;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.future.common.constants.AgentConstant;
import com.future.common.enums.GlobalResultCode;
import com.future.common.exception.BusinessException;
import com.future.common.exception.DataConflictException;
import com.future.common.util.ConvertUtil;
import com.future.common.util.StringUtils;
import com.future.entity.com.FuComAgent;
import com.future.mapper.com.FuComAgentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Service
public class FuComAgentService extends ServiceImpl<FuComAgentMapper,FuComAgent> {

    Logger log= LoggerFactory.getLogger(FuComAgentService.class);

    @Autowired
    FuComAgentMapper fuComAgentMapper;

    /**
     * 根据条件查找代理信息
     * @param conditionMap
     * @return
     */
    public Page<FuComAgent> findAgentByCondition(Map conditionMap){
        /*校验信息*/
        Page<FuComAgent> page=new Page<FuComAgent>();
        Wrapper<FuComAgent> wrapper=new EntityWrapper<FuComAgent>();

        int pageSize=20;
        int pageNum=1;

        if(StringUtils.isEmpty(pageSize)||StringUtils.isEmpty(pageNum)){
            log.error("分页数据为空！");
            new DataConflictException(GlobalResultCode.PARAM_NULL_POINTER,"分页数据为空！");
        }
        if(!StringUtils.isEmpty(String.valueOf(conditionMap.get("pageSize")))){
            pageSize=Integer.parseInt(String.valueOf(conditionMap.get("pageSize")));
        }
        if(!StringUtils.isEmpty(String.valueOf(conditionMap.get("pageNum")))){
            pageNum=Integer.parseInt(String.valueOf(conditionMap.get("pageNum")));
        }
        page.setSize(pageSize);
        page.setCurrent(pageNum);

        if(!ObjectUtils.isEmpty(conditionMap.get("id"))){
            wrapper.eq(FuComAgent.ID,String.valueOf(conditionMap.get("id")));
        }
        if(!ObjectUtils.isEmpty(conditionMap.get("userId"))){
            wrapper.eq(FuComAgent.USER_ID,String.valueOf(conditionMap.get("userId")));
        }
        if(!ObjectUtils.isEmpty(conditionMap.get("agentName"))){
            wrapper.eq(FuComAgent.AGENT_NAME,String.valueOf(conditionMap.get("agentName")));
        }
        if(!ObjectUtils.isEmpty(conditionMap.get("agentType"))){
            wrapper.eq(FuComAgent.AGENT_TYPE,String.valueOf(conditionMap.get("agentType")));
        }
        if(!ObjectUtils.isEmpty(conditionMap.get("agentState"))){
            wrapper.eq(FuComAgent.AGENT_STATE,String.valueOf(conditionMap.get("agentState")));
        }
        if(!ObjectUtils.isEmpty(conditionMap.get("agentLevel"))){
            wrapper.eq(FuComAgent.AGENT_LEVEL,String.valueOf(conditionMap.get("agentLevel")));
        }

        try {
            return selectPage(page,wrapper);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            throw new BusinessException(e);
        }
    }

    /**
     * 根据ID获取代理信息
     * @param agentId
     * @return
     */
    public FuComAgent findAgentApplyById(int agentId){
        /*校验信息*/
        if(agentId<1){
            log.error("传入数据为空！");
            throw new DataConflictException(GlobalResultCode.PARAM_NULL_POINTER,"传入数据为空!");
        }

        try {
            return selectById(agentId);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            throw new BusinessException(e);
        }
    }


    /**
     * 保存代理申请信息(管理员用)
     * @param agentMap
     */
    public int saveAgent(Map agentMap){
        /*校验信息*/
        if(ObjectUtils.isEmpty(agentMap)){
            log.error("申请信息不能为空！");
            throw new DataConflictException(GlobalResultCode.PARAM_NULL_POINTER,"申请信息不能为空！");
        }
        if(ObjectUtils.isEmpty(agentMap.get("userId"))){
            log.error("申请人信息不能为空！");
            throw new DataConflictException(GlobalResultCode.PARAM_NULL_POINTER,"申请人信息不能为空！");
        }
        if(ObjectUtils.isEmpty(agentMap.get("agentName"))){
            log.error("代理名称不能为空！");
            throw new DataConflictException(GlobalResultCode.PARAM_NULL_POINTER,"代理名称不能为空！");
        }
        if(ObjectUtils.isEmpty(agentMap.get("applyDesc"))){
            log.error("代理描述不能为空！");
            throw new DataConflictException(GlobalResultCode.PARAM_NULL_POINTER,"代理描述不能为空！");
        }

        try {
            /*组装信息*/
            FuComAgent agent=(FuComAgent)ConvertUtil.MapToJavaBean((HashMap) agentMap,FuComAgent.class);
            agent.setCreateDate(new Date());
            agent.setModifyDate(new Date());
            agent.setApplyDate(new Date());
            /*默认数据填充*/
            return fuComAgentMapper.insertSelective(agent);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            throw new BusinessException(e);
        }
    }

    /**
     * 修改信息
     * @param agentId
     * @param agentMap
     * @return
     */
    public int updateAgent(int agentId, Map agentMap){
        /*校验信息*/
        if(agentId<1){
            log.error("传入ID数据为空！");
            throw new DataConflictException(GlobalResultCode.PARAM_NULL_POINTER,"传入ID数据为空!");
        }
        try {
            /*组装信息*/
            FuComAgent agent=(FuComAgent)ConvertUtil.MapToJavaBean((HashMap) agentMap,FuComAgent.class);

            agent.setId(agentId);
            /*修改信息*/
            return fuComAgentMapper.updateByPrimaryKeySelective(agent);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            throw new BusinessException(e);
        }
    }

    /**
     * 删除代理信息
     * @param agentId
     */
    public int deleteAgent(int agentId){
        /**校验信息*/
        /*校验信息*/
        if(agentId<1){
            log.error("传入数据为空！");
            throw new DataConflictException(GlobalResultCode.PARAM_NULL_POINTER,"传入数据为空!");
        }
        try {
            /*组装信息*/
            FuComAgent agent=new FuComAgent();
            agent.setId(agentId);
            /*逻辑删除*/
            agent.setAgentState(AgentConstant.AGENT_STATE_REMOVE);
            /*修改信息*/
            return fuComAgentMapper.updateByPrimaryKeySelective(agent);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            throw new BusinessException(e);
        }
    }

}
