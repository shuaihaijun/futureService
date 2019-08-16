package com.future.service.com;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.future.common.constants.AgentConstant;
import com.future.common.constants.SignalConstant;
import com.future.common.enums.GlobalResultCode;
import com.future.common.exception.BusinessException;
import com.future.common.exception.DataConflictException;
import com.future.common.util.BeanUtil;
import com.future.common.util.ConvertUtil;
import com.future.common.util.StringUtils;
import com.future.entity.com.FuComAgent;
import com.future.entity.com.FuComAgentApply;
import com.future.mapper.com.FuComAgentApplyMapper;
import com.future.mapper.com.FuComAgentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Service
public class FuComAgentApplyService extends ServiceImpl<FuComAgentApplyMapper,FuComAgentApply> {

    Logger log= LoggerFactory.getLogger(FuComAgentApplyService.class);

    @Autowired
    FuComAgentApplyMapper agentApplyMapper;
    @Autowired
    FuComAgentMapper agentMapper;
    /**
     * 根据条件查找代理信息
     * @param conditionMap
     * @return
     */
    public Page<FuComAgentApply> findAgentApplyByCondition(Map conditionMap){
        /*校验信息*/
        Page<FuComAgentApply> page=new Page<FuComAgentApply>();
        Wrapper<FuComAgentApply> wrapper=new EntityWrapper<FuComAgentApply>();

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
            wrapper.eq(FuComAgentApply.ID,String.valueOf(conditionMap.get("id")));
        }
        if(!ObjectUtils.isEmpty(conditionMap.get("agentName"))){
            wrapper.eq(FuComAgentApply.AGENT_NAME,String.valueOf(conditionMap.get("agentName")));
        }
        if(!ObjectUtils.isEmpty(conditionMap.get("applyType"))){
            wrapper.eq(FuComAgentApply.APPLY_TYPE,String.valueOf(conditionMap.get("applyType")));
        }
        if(!ObjectUtils.isEmpty(conditionMap.get("applyState"))){
            wrapper.eq(FuComAgentApply.APPLY_STATE,String.valueOf(conditionMap.get("applyState")));
        }
        if(!ObjectUtils.isEmpty(conditionMap.get("userId"))){
            wrapper.eq(FuComAgentApply.USER_ID,String.valueOf(conditionMap.get("userId")));
        }

        return selectPage(page,wrapper);
    }

    /**
     * 根据ID获取代理信息
     * @param agentId
     * @return
     */
    public FuComAgentApply findAgentApplyById(int agentId){
        /*校验信息*/
        if(agentId<1){
            log.error("传入数据为空！");
            throw new DataConflictException(GlobalResultCode.PARAM_NULL_POINTER,"传入数据为空!");
        }
        return selectById(agentId);
    }


    /**
     * 保存代理申请信息
     * @param agentMap
     */
    public int saveAgentApply(Map agentMap){
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
        if(ObjectUtils.isEmpty(agentMap.get("applyReason"))){
            log.error("申请原由不能为空！");
            throw new DataConflictException(GlobalResultCode.PARAM_NULL_POINTER,"请原由不能为空！");
        }
        if(ObjectUtils.isEmpty(agentMap.get("applyDesc"))){
            log.error("代理描述不能为空！");
            throw new DataConflictException(GlobalResultCode.PARAM_NULL_POINTER,"代理描述不能为空！");
        }

        try {
            /*组装信息*/
            FuComAgentApply agentApply=(FuComAgentApply)ConvertUtil.MapToJavaBean((HashMap) agentMap,FuComAgentApply.class);
            if(ObjectUtils.isEmpty(agentMap.get("applyType"))){
                //申请类型（0 IB升级申请，1 注册申请 ，2 IB降级申请）
                agentApply.setApplyType(AgentConstant.AGENT_APPLY_TYPE_NEW);
            }
            agentApply.setApplyState(AgentConstant.AGENT_APPLY_STATE_SAVE);
            agentApply.setCreateDate(new Date());
            /*默认数据填充*/

            return agentApplyMapper.insertSelective(agentApply);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            throw new BusinessException(e);
        }
    }

    /**
     * 修改信号源信息
     * @param applyId
     * @param agentMap
     * @return
     */
    public int updateAgentApply(int applyId, Map agentMap){
        /*校验信息*/
        /*校验信息*/
        if(applyId<1){
            log.error("传入ID数据为空！");
            throw new DataConflictException(GlobalResultCode.PARAM_NULL_POINTER,"传入ID数据为空!");
        }
        try {
            /*组装信息*/
            FuComAgentApply agentApply=(FuComAgentApply)ConvertUtil.MapToJavaBean((HashMap) agentMap,FuComAgentApply.class);

            agentApply.setId(applyId);
            /*修改信息*/
            return agentApplyMapper.updateByPrimaryKeySelective(agentApply);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            throw new BusinessException(e);
        }
    }

    /**
     * 提交申请信息
     * @param signalId
     * @param mesage
     * @return
     */
    public int submitAgentSignal(int signalId,String mesage){
        /**校验信息*/
        if(signalId<1){
            log.error("传入数据为空！");
            throw new DataConflictException(GlobalResultCode.PARAM_NULL_POINTER,"传入数据为空!");
        }

        try {
            /*组装信息*/
            FuComAgentApply agentApply=new FuComAgentApply();
            agentApply.setId(signalId);
            agentApply.setApplyState(AgentConstant.AGENT_APPLY_STATE_CHECK);
            agentApply.setApplyDate(new Date());
            return agentApplyMapper.updateByPrimaryKeySelective(agentApply);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            throw new BusinessException(e);
        }
    }

    /**
     * 审核信号源信息
     * @param applyId
     * @param state
     * @param mesage
     */
    @Transactional(rollbackFor = Exception.class)
    public void reviewProductSignal(int applyId,int state, String mesage){
        /**校验信息*/
        if(applyId<1||state<1){
            log.error("传入数据为空！");
            throw new DataConflictException(GlobalResultCode.PARAM_NULL_POINTER,"传入数据为空!");
        }
        /*组装信息*/
        try {
            FuComAgentApply agentApply=findAgentApplyById(applyId);
            if(ObjectUtils.isEmpty(agentApply)){
                log.error("查询申请信息错误！");
                throw new BusinessException("查询申请信息错误！");
            }
            agentApply.setCheckDate(new Date());
            agentApply.setCheckDesc(mesage);

            if(state== AgentConstant.AGENT_APPLY_STATE_DONE){
                /*审核通过*/
                agentApply.setApplyState(AgentConstant.AGENT_APPLY_STATE_DONE);

                FuComAgent agent= new FuComAgent();
                BeanUtil.copyProperties(agentApply,agent);

                /*初始化代理数据*/
                agent.setId(null);
                /*保存信号源*/
                agentMapper.insertSelective(agent);
            }else if(state==SignalConstant.SIGNAL_APPLY_STATE_UNPASS){
                /*审核未通过*/
                agentApply.setApplyState(AgentConstant.AGENT_APPLY_STATE_FAIL);
            }else {
                log.error("审核状态错误！");
                throw new DataConflictException(GlobalResultCode.PARAM_IS_INVALID,"核状态错误！");
            }
            /*修改数据*/
            updateById(agentApply);

        }catch (Exception e){
            log.error(e.getMessage(),e);
            throw new BusinessException(e);
        }

    }

    /**
     * 删除代理信息
     * @param agentId
     */
    public Boolean deleteAgentApply(int agentId){
        /**校验信息*/
        /*校验信息*/
        if(agentId<1){
            log.error("传入数据为空！");
            throw new DataConflictException(GlobalResultCode.PARAM_NULL_POINTER,"传入数据为空!");
        }
        try {
            /*删除*/
            return this.deleteById(agentId);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            throw new BusinessException(e);
        }
    }

}
