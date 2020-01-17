package com.future.service.com;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.future.common.constants.AgentConstant;
import com.future.common.constants.SignalConstant;
import com.future.common.enums.GlobalResultCode;
import com.future.common.exception.BusinessException;
import com.future.common.exception.DataConflictException;
import com.future.common.exception.ParameterInvalidException;
import com.future.common.helper.PageInfoHelper;
import com.future.common.util.BeanUtil;
import com.future.common.util.ConvertUtil;
import com.future.common.util.StringUtils;
import com.future.entity.account.FuAccountInfo;
import com.future.entity.com.FuComAgent;
import com.future.entity.com.FuComAgentApply;
import com.future.entity.permission.FuPermissionRole;
import com.future.entity.permission.FuPermissionUserProject;
import com.future.entity.permission.FuPermissionUserRole;
import com.future.entity.product.FuProductSignalApply;
import com.future.entity.user.FuUser;
import com.future.mapper.com.FuComAgentApplyMapper;
import com.future.service.account.FuAccountCommissionService;
import com.future.service.account.FuAccountInfoService;
import com.future.service.permission.PermissionRoleService;
import com.future.service.permission.PermissionUserProjectService;
import com.future.service.permission.PermissionUserRoleService;
import com.future.service.user.AdminService;
import com.future.service.user.UserCommonService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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
    PermissionUserProjectService permissionUserProjectService;
    @Autowired
    PermissionRoleService permissionRoleService;
    @Autowired
    FuComAgentApplyMapper agentApplyMapper;
    @Autowired
    UserCommonService userCommonService;


    /**
     * 根据条件查询代理信息
     * @param agentConditon
     * @param helper
     * @return
     */
    public Page<FuComAgentApply> queryAgentPage(Map agentConditon, PageInfoHelper helper){
        /*判断查询条件*/
        if(agentConditon == null||agentConditon.get("operUserId")==null){
            log.error("查询用户列表,获取参数为空！");
            throw new ParameterInvalidException("查询用户列表,获取参数为空！");
        }
        /*判断权限*/
        String operUserId=String.valueOf(agentConditon.get("operUserId"));
        if(com.alibaba.druid.util.StringUtils.isEmpty(operUserId)){
            log.error("查询用户列表,用户未登录！");
            throw new ParameterInvalidException("查询用户列表,获取参数为空！");
        }
        Integer operUserProj=userCommonService.getUserProjKey(Integer.parseInt(operUserId));
        Boolean isProjAdmin=userCommonService.isAdministrator(Integer.parseInt(operUserId),operUserProj);
        if(operUserProj==null){
            log.error("查询用户列表,用户权限有误！");
            throw new ParameterInvalidException("查询用户列表,用户权限有误！");
        }

        if(isProjAdmin&&operUserProj==0){
            /*超管查询*/
            return findAgentApplyByCondition(agentConditon,helper);
        }else if(isProjAdmin){
            /*资源组管理员查找*/
            agentConditon.put("projKey",operUserProj);
            return findAgentByProjCondtion(agentConditon,helper);
        }else {
            /*普通用户查找*/
            agentConditon.put("userId",operUserId);
            return findAgentApplyByCondition(agentConditon,helper);
        }
    }

    /**
     * 根据project查询代理申请信息
     * @param conditionMap
     * @param helper
     * @return
     */
    public Page<FuComAgentApply> findAgentByProjCondtion(Map conditionMap, PageInfoHelper helper){
        if(helper==null){
            helper=new PageInfoHelper();
        }
        Page<FuComAgentApply> applies= PageHelper.startPage(helper.getPageNo(),helper.getPageSize());
        agentApplyMapper.queryAgentApplyByProjCondition(conditionMap);
        return applies;
    }

    /**
     * 根据条件查找代理信息
     * @param conditionMap
     * @return
     */
    public Page<FuComAgentApply> findAgentApplyByCondition(Map conditionMap, PageInfoHelper helper){

        Wrapper<FuComAgentApply> wrapper=new EntityWrapper<FuComAgentApply>();
        /*封装验信息*/
        if(!ObjectUtils.isEmpty(conditionMap.get("userId"))){
            wrapper.eq(FuComAgentApply.USER_ID,String.valueOf(conditionMap.get("userId")));
        }
        if(!ObjectUtils.isEmpty(conditionMap.get("id"))){
            wrapper.eq(FuComAgentApply.AGENT_ID,String.valueOf(conditionMap.get("id")));
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

        if(helper==null){
            helper=new PageInfoHelper();
        }
        Page<FuComAgentApply> applies= PageHelper.startPage(helper.getPageNo(),helper.getPageSize());
        selectList(wrapper);
        return applies;
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

        /*判断权限*/
        String operUserId=String.valueOf(agentMap.get("operUserId"));
        String userId=String.valueOf(agentMap.get("userId"));
        if(com.alibaba.druid.util.StringUtils.isEmpty(operUserId)){
            log.error("查询用户列表,用户未登录！");
            throw new ParameterInvalidException("查询用户列表,获取参数为空！");
        }
        if(!operUserId.equals(userId)){
            //非用户本人提交申请
            Integer operUserProj=userCommonService.getUserProjKey(Integer.parseInt(operUserId));
            Boolean isProjAdmin=userCommonService.isAdministrator(Integer.parseInt(operUserId),operUserProj);
            if(operUserProj==null){
                log.error("查询用户列表,用户权限有误！");
                throw new ParameterInvalidException("查询用户列表,用户权限有误！");
            }
            if(isProjAdmin&&operUserProj==0){
                /*超管*/
            }else if(isProjAdmin){
                /*资源组管理员申请*/
                Integer userProj=userCommonService.getUserProjKey(Integer.parseInt(userId));
                if(!operUserProj.equals(userProj)){
                    log.error("无权限操作该用户数据，请检查用户ID！");
                    throw new ParameterInvalidException("无权限操作该用户数据，请检查用户ID！");
                }
            }else {
                /*普通用户非自己申请*/
                log.error("无权限操作该用户数据，请检查用户ID！");
                throw new ParameterInvalidException("无权限操作该用户数据，请检查用户ID！");
            }
        }

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
        /*校验数据是否已存在！*/
        Wrapper<FuComAgentApply> wrapper=new EntityWrapper<>();
        wrapper.eq(FuComAgentApply.USER_ID,agentApply.getUserId());
        wrapper.ne(FuComAgentApply.APPLY_STATE,AgentConstant.AGENT_APPLY_STATE_DONE);
        List<FuComAgentApply> applies= selectList(wrapper);
        if(applies!=null&&applies.size()>0){
            log.warn("该用户已在申请队列中，请检查您的数据！");
            throw new DataConflictException("该用户已在申请队列中，请检查您的数据！");
        }

        /*校验申请类型是否正确*/
        FuComAgent fuComAgent= fuComAgentService.findAgentByUserId(agentApply.getUserId());
        if(agentApply.getApplyType().equals(AgentConstant.AGENT_APPLY_TYPE_NEW)){
            if(fuComAgent!=null){
                log.warn("该用户已是代理身份，请做其他操作！");
                throw new DataConflictException("该用户已是代理身份，请做其他操作！");
            }
        }
        if(agentApply.getApplyType().equals(AgentConstant.AGENT_APPLY_TYPE_UP)){
            if(fuComAgent==null){
                log.warn("该用户不是代理身份，请做其他操作！");
                throw new DataConflictException("该用户不是代理身份，请做其他操作！");
            }
            if(fuComAgent.getAgentType().equals(AgentConstant.AGENT_TYPE_PIB)){
                log.warn("该用户已是高级代理身份，不能再次升级！");
                throw new DataConflictException("该用户已是高级代理身份，不能再次升级！");
            }
        }
        if(agentApply.getApplyType().equals(AgentConstant.AGENT_APPLY_TYPE_DOWN)){
            if(fuComAgent==null){
                log.warn("该用户不是代理身份，请做其他操作！");
                throw new DataConflictException("该用户不是代理身份，请做其他操作！");
            }
            if(fuComAgent.getAgentType().equals(AgentConstant.AGENT_TYPE_IB)){
                log.warn("该用户已是初级代理身份，不能再次升级！");
                throw new DataConflictException("该用户已是初级代理身份，不能再次升级！");
            }
        }

        return agentApplyMapper.insertSelective(agentApply);

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
     * 审核代理信息
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
                Integer agentType=AgentConstant.AGENT_TYPE_IB;
                FuPermissionUserProject userProject= permissionUserProjectService.selectOne(new EntityWrapper<FuPermissionUserProject>()
                        .eq(FuPermissionUserProject.USER_ID,user.getId()));
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
                    agentType=AgentConstant.AGENT_TYPE_IB;
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
                        agentType=AgentConstant.AGENT_TYPE_MIB;
                    }else if(user.getUserType()==AgentConstant.AGENT_TYPE_MIB){
                        user.setUserType(AgentConstant.AGENT_TYPE_PIB);
                        agent.setAgentType(AgentConstant.AGENT_TYPE_PIB);
                        agentType=AgentConstant.AGENT_TYPE_PIB;
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
                        agentType=AgentConstant.AGENT_TYPE_MIB;
                    }else if(user.getUserType()==AgentConstant.AGENT_TYPE_MIB){
                        user.setUserType(AgentConstant.AGENT_TYPE_IB);
                        agent.setAgentType(AgentConstant.AGENT_TYPE_IB);
                        agentType=AgentConstant.AGENT_TYPE_IB;
                    }
                    fuComAgentService.updateById(agent);
                }else {
                    log.error("审核状态错误！");
                    throw new DataConflictException(GlobalResultCode.PARAM_IS_INVALID,"核状态错误！");
                }
                /*设置角色信息*/
                FuPermissionRole defaultRole= permissionRoleService.getRoleByProject(userProject.getProjKey(),agentType);
                if(defaultRole!=null){
                    userRole.setRoleId(defaultRole.getId());
                    /*分配相关角色*/
                    permissionUserRoleService.updateById(userRole);
                }else {
                    log.warn("未匹配到相关数据：根据projkey userType未找到相关角色信息！");
                }

                /*变更用户类型*/
                adminService.updateById(user);
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
