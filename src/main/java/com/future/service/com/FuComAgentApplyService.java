package com.future.service.com;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.future.common.constants.AgentConstant;
import com.future.common.constants.SignalConstant;
import com.future.common.enums.GlobalResultCode;
import com.future.common.enums.UserResultCode;
import com.future.common.enums.UserRoleCode;
import com.future.common.exception.BusinessException;
import com.future.common.exception.DataConflictException;
import com.future.common.util.BeanUtil;
import com.future.common.util.ConvertUtil;
import com.future.common.util.StringUtils;
import com.future.entity.account.FuAccountInfo;
import com.future.entity.com.FuComAgent;
import com.future.entity.com.FuComAgentApply;
import com.future.entity.permission.FuPermissionUserRole;
import com.future.entity.product.FuProductSignalApply;
import com.future.entity.user.FuUser;
import com.future.mapper.com.FuComAgentApplyMapper;
import com.future.mapper.com.FuComAgentMapper;
import com.future.service.account.FuAccountCommissionService;
import com.future.service.account.FuAccountInfoService;
import com.future.service.permission.PermissionUserRoleService;
import com.future.service.user.AdminService;
import com.future.service.user.UserCommonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.*;


@Service
public class FuComAgentApplyService extends ServiceImpl<FuComAgentApplyMapper,FuComAgentApply> {

    Logger log= LoggerFactory.getLogger(FuComAgentApplyService.class);

    @Autowired
    FuAccountInfoService fuAccountInfoService;
    @Autowired
    FuAccountCommissionService fuAccountCommissionService;
    @Autowired
    AdminService adminService;
    @Autowired
    FuComAgentService fuComAgentService;
    @Autowired
    PermissionUserRoleService permissionUserRoleService;
    @Autowired
    FuComAgentApplyMapper agentApplyMapper;
    @Autowired
    UserCommonService userCommonService;
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

        if(ObjectUtils.isEmpty(conditionMap.get("operUserId"))){
            log.error("操作人数据为空！");
            new DataConflictException(GlobalResultCode.PARAM_NULL_POINTER,"操作人数据为空！");
        }
        String operUserId=String.valueOf(conditionMap.get("operUserId"));
        //获取配置文件中超级管理员，判断当前登录用户是否为超级管理员,true为超管
        boolean contains =  userCommonService.isAdministrator(operUserId);
        if(!contains){
            //该用户只能查询自己的数据
            wrapper.eq(FuComAgentApply.USER_ID,operUserId);
        }else if(!ObjectUtils.isEmpty(conditionMap.get("userId"))){
            wrapper.eq(FuComAgentApply.USER_ID,String.valueOf(conditionMap.get("userId")));
        }

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
            //将对象转换成为字符串
            String str = JSON.toJSONString(agentMap);
            //字符串转换成为对象
            HashMap infoMap = JSON.parseObject(str, HashMap.class);
            FuComAgentApply agentApply=(FuComAgentApply)ConvertUtil.MapToJavaBean(infoMap,FuComAgentApply.class);
            if(ObjectUtils.isEmpty(agentMap.get("applyType"))){
                //申请类型（0 IB注册申请，1 IB升级申请 ，2 IB降级申请）
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
            //将对象转换成为字符串
            String str = JSON.toJSONString(agentMap);
            //字符串转换成为对象
            HashMap infoMap = JSON.parseObject(str, HashMap.class);
            FuComAgentApply agentApply=(FuComAgentApply)ConvertUtil.MapToJavaBean(infoMap,FuComAgentApply.class);

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
    public int submitAgentApply(int signalId,String mesage){
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
     * @param message
     */
    @Transactional(rollbackFor = Exception.class)
    public void reviewAgentApply(int applyId,int state, String message){
        /**校验信息*/
        if(applyId<1||state<0){
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
            agentApply.setCheckDesc(message);

            if(state== AgentConstant.AGENT_APPLY_STATE_DONE){
                /*审核通过*/
                agentApply.setApplyState(AgentConstant.AGENT_APPLY_STATE_DONE);

                FuComAgent agent= new FuComAgent();
                BeanUtil.copyProperties(agentApply,agent);
                /*初始化代理数据*/
                agent.setId(null);
                /*变更用户类型*/
                FuUser user=adminService.selectById(agent.getUserId());
                if(user==null){
                    log.error("查询用户信息错误！");
                    throw new BusinessException("查询用户信息错误！");
                }
                FuPermissionUserRole userRole=permissionUserRoleService.selectOne((new EntityWrapper<FuPermissionUserRole>().eq(FuPermissionUserRole.USER_ID,agentApply.getUserId())));
                if(userRole==null){
                    log.error("查询用户信息错误！");
                    throw new BusinessException("查询用户信息错误！");
                }
                if(agentApply.getApplyType().equals(AgentConstant.AGENT_APPLY_TYPE_NEW)){
                    //新增
                    /*建立社区佣金账户*/
                    Map conditionMap =new HashMap();
                    conditionMap.put(FuProductSignalApply.USER_ID,agent.getUserId());
                    List<FuAccountInfo> accountInfos= fuAccountInfoService.selectByMap(conditionMap);
                    if(accountInfos==null || accountInfos.size()==0){
                        log.error("查询社区账户信息错误！");
                        throw new BusinessException("查询社区账户信息错误！");
                    }
                    fuAccountCommissionService.initAccountCommission(agent.getUserId(),accountInfos.get(0).getId());

                    agent.setAgentType(AgentConstant.AGENT_TYPE_IB);
                    /**/
                    user.setUserType(AgentConstant.AGENT_TYPE_IB);

                    /*设置角色信息*/
                    userRole.setRoleId(UserRoleCode.USER_ROLE_IB.code());
                    fuComAgentService.insertSelective(agent);

                }else if(agentApply.getApplyType().equals(AgentConstant.AGENT_APPLY_TYPE_UP)){
                    //升级
                    if(user.getUserType()!=AgentConstant.AGENT_TYPE_IB&&user.getUserType()!=AgentConstant.AGENT_TYPE_MIB){
                        log.error("审核状态错误！用户类型不符合！");
                        throw new DataConflictException(GlobalResultCode.PARAM_IS_INVALID,"核状态错误！用户类型不符合！");
                    }
                    agent=  fuComAgentService.selectOne(new EntityWrapper<FuComAgent>().eq(FuComAgent.USER_ID,agentApply.getUserId()));
                    if(user.getUserType()==AgentConstant.AGENT_TYPE_IB){
                        user.setUserType(AgentConstant.AGENT_TYPE_MIB);
                        agent.setAgentType(AgentConstant.AGENT_TYPE_MIB);
                        userRole.setRoleId(UserRoleCode.USER_ROLE_MIB.code());
                    }
                    if(user.getUserType()==AgentConstant.AGENT_TYPE_MIB){
                        user.setUserType(AgentConstant.AGENT_TYPE_PIB);
                        agent.setAgentType(AgentConstant.AGENT_TYPE_PIB);
                        userRole.setRoleId(UserRoleCode.USER_ROLE_PIB.code());
                    }
                    fuComAgentService.updateById(agent);
                }else if(agentApply.getApplyType().equals(AgentConstant.AGENT_APPLY_TYPE_DOWN)){
                    //降级
                    if(user.getUserType()!=AgentConstant.AGENT_TYPE_PIB&&user.getUserType()!=AgentConstant.AGENT_TYPE_MIB){
                        log.error("审核状态错误！用户类型不符合！");
                        throw new DataConflictException(GlobalResultCode.PARAM_IS_INVALID,"核状态错误！用户类型不符合！");
                    }
                    agent=  fuComAgentService.selectOne(new EntityWrapper<FuComAgent>().eq(FuComAgent.USER_ID,agentApply.getUserId()));
                    if(user.getUserType()==AgentConstant.AGENT_TYPE_PIB){
                        user.setUserType(AgentConstant.AGENT_TYPE_MIB);
                        agent.setAgentType(AgentConstant.AGENT_TYPE_MIB);
                        userRole.setRoleId(UserRoleCode.USER_ROLE_MIB.code());
                    }
                    if(user.getUserType()==AgentConstant.AGENT_TYPE_MIB){
                        user.setUserType(AgentConstant.AGENT_TYPE_IB);
                        agent.setAgentType(AgentConstant.AGENT_TYPE_IB);
                        userRole.setRoleId(UserRoleCode.USER_ROLE_IB.code());
                    }
                    fuComAgentService.updateById(agent);
                }else {
                    log.error("审核状态错误！");
                    throw new DataConflictException(GlobalResultCode.PARAM_IS_INVALID,"核状态错误！");
                }

                /*变更用户类型*/
                adminService.updateById(user);
                /*分配相关角色*/
                permissionUserRoleService.updateById(userRole);
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
