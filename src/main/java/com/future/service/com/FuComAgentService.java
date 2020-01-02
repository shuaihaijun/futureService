package com.future.service.com;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.future.common.constants.*;
import com.future.common.enums.GlobalResultCode;
import com.future.common.exception.BusinessException;
import com.future.common.exception.DataConflictException;
import com.future.common.helper.PageInfoHelper;
import com.future.common.util.ConvertUtil;
import com.future.common.util.StringUtils;
import com.future.entity.account.FuAccountCommission;
import com.future.entity.account.FuAccountCommissionFlow;
import com.future.entity.account.FuAccountInfo;
import com.future.entity.com.FuComAgent;
import com.future.entity.permission.FuPermissionRole;
import com.future.entity.permission.FuPermissionUserProject;
import com.future.entity.permission.FuPermissionUserRole;
import com.future.entity.user.FuUser;
import com.future.mapper.com.FuComAgentMapper;
import com.future.pojo.bo.order.UserMTAccountBO;
import com.future.service.account.FuAccountCommissionFlowService;
import com.future.service.account.FuAccountCommissionService;
import com.future.service.account.FuAccountInfoService;
import com.future.service.account.FuAccountMtService;
import com.future.service.permission.PermissionRoleService;
import com.future.service.permission.PermissionUserProjectService;
import com.future.service.permission.PermissionUserRoleService;
import com.future.service.user.AdminService;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.util.*;


@Service
public class FuComAgentService extends ServiceImpl<FuComAgentMapper,FuComAgent> {

    Logger log= LoggerFactory.getLogger(FuComAgentService.class);

    @Autowired
    FuComAgentMapper fuComAgentMapper;
    @Autowired
    FuAccountMtService fuAccountMtService;
    @Autowired
    AdminService adminService;
    @Autowired
    FuAccountCommissionFlowService fuAccountCommissionFlowService;
    @Autowired
    FuAccountInfoService fuAccountInfoService;
    @Autowired
    FuAccountCommissionService fuAccountCommissionService;
    @Autowired
    PermissionRoleService permissionRoleService;
    @Autowired
    PermissionUserProjectService permissionUserProjectService;
    @Autowired
    PermissionUserRoleService permissionUserRoleService;

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
            wrapper.eq(FuComAgent.AGENT_ID,String.valueOf(conditionMap.get("id")));
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

    /**
     * 代理升级处理
     * @param userId
     */
    public void agentUpgrade(Integer userId){
        /*校验信息*/
        if(userId==null || userId==0){
            log.error("传入数据为空！");
            throw new DataConflictException(GlobalResultCode.PARAM_NULL_POINTER,"传入数据为空!");
        }

        /*1、同步用户自定义订单 计算佣金流水*/
        Map conditionMap=new HashMap();
        /*已验证的 合作平台的 MT账户*/
        conditionMap.put("userId", userId);
        List<UserMTAccountBO> list = fuAccountMtService.getUserMTAccByCondition(conditionMap);

        agentUpgrade(list.get(0));
    }

    /**
     * 根据用户账户信息 判断代理升级
     * @param accountBO
     */
    @Transactional(rollbackFor = Exception.class)
    public void agentUpgrade(UserMTAccountBO accountBO){

        /*判断代理是否可升级*/
        if(accountBO.getUserType()> UserConstant.USER_TYPE_PIB){
            /*类型不符合  只有MIB IB可以升级*/
            return;
        }

        /*获取 被介绍的客户*/
        List<UserMTAccountBO> beIntroducer = getAgentIntroducedUser(accountBO.getUserId());
        if(beIntroducer==null || beIntroducer.size()<5){// 至少大于5
            /*没介绍*/
            return;
        }
        int amout2000=0;
        int amout5000=0;
        int amout10000=0;
        int numUser=0;
        int numIB=0;
        int numMIB=0;
        for(UserMTAccountBO user:beIntroducer){
            if(user.getBalance().compareTo(new BigDecimal(2000))>=0){
                amout2000++;
            }
            if(user.getBalance().compareTo(new BigDecimal(5000))>=0){
                amout5000++;
            }
            if(user.getBalance().compareTo(new BigDecimal(10000))>=0){
                amout10000++;
            }
            if(user.getUserType()==UserConstant.USER_TYPE_IB){
                numIB++;
            }else if(user.getUserType()==UserConstant.USER_TYPE_MIB){
                numMIB++;
            }else if(user.getUserType()<UserConstant.USER_TYPE_IB){
                numUser++;
            }
        }

        boolean checkPass=false;
        /*判断代理当前级别*/
        int agentType = accountBO.getUserType();
        if(agentType < UserConstant.USER_TYPE_IB){
            // 非IB 升级 IB
            /*1、本人入金3000美金*/
            if(accountBO.getBalance().compareTo(new BigDecimal(3000))<0){
                return;
            }
            /*2、直推5名有效会员*/
            if(numUser<5){
                return;
            }
            /*3、每个会员入金在2000美金以上*/
            if (amout2000<5){
                return;
            }
            agentType =UserConstant.USER_TYPE_IB;
        }else if(agentType == UserConstant.USER_TYPE_IB){
            // IB 升级 MIB
            /*1、本人入金5000美金*/
            if(accountBO.getBalance().compareTo(new BigDecimal(5000))<0){
                return;
            }
            /*2、直推3名初级会员*/
            if(numIB<3){
                return;
            }
            /*3、直推7名有效会员*/
            if (numUser<7){
                return;
            }
            agentType =UserConstant.USER_TYPE_MIB;
        }else if(agentType == UserConstant.USER_TYPE_MIB){
            // MIB 升级 PIB
            /*1、本人入金10000美金*/
            if(accountBO.getBalance().compareTo(new BigDecimal(10000))<0){
                return;
            }
            /*2、直推3名有效中级会员*/
            if(numMIB<3){
                return;
            }
            /*3、直推12名有效会员*/
            if(numUser<12){
                return;
            }
            agentType =UserConstant.USER_TYPE_PIB;
        }

        if(checkPass){
            /*升级*/
            Map conditionMap=new HashMap();
            conditionMap.put("userId",accountBO.getUserId());
            List<FuComAgent> agents= fuComAgentMapper.selectByMap(conditionMap);
            if(agents==null||agents.size()==0){
                if(agentType<AgentConstant.AGENT_TYPE_IB){
                    /*普通用户 升级IB*/
                    Map agentMap=new HashMap();
                    agentMap.put("userId",accountBO.getUserId());
                    agentMap.put("agentName",accountBO.getUsername());
                    agentMap.put("applyDesc","系统自动升级！");
                    saveAgent(agentMap);
                }else {
                    log.error("根据用户查询代理信息失败！");
                    throw new BusinessException("根据用户查询代理信息失败！");
                }
            }
            FuComAgent agent=agents.get(0);
            agent.setAgentType(agentType);

            /*修改用户类型 userType*/
            FuUser user=adminService.selectById(accountBO.getUserId());
            user.setUserType(agentType);
            adminService.updateAdmin(user);

            /*修改代理角色*/
            FuPermissionUserProject userProject= permissionUserProjectService.selectOne(new EntityWrapper<FuPermissionUserProject>()
                    .eq(FuPermissionUserProject.USER_ID,user.getId()));
            FuPermissionRole defaultRole= permissionRoleService.getRoleByProject(userProject.getProjKey(),agentType);
            if(defaultRole!=null){
                FuPermissionUserRole userRole= permissionUserRoleService.selectOne(new EntityWrapper<FuPermissionUserRole>().eq(FuPermissionUserRole.USER_ID,user.getId()));
                if(userRole!=null){
                    userRole.setRoleId(defaultRole.getId());
                    permissionUserRoleService.updateById(userRole);
                }else {
                    log.error("查询用户角色失败！");
                    throw new BusinessException("查询用户角色失败！");
                }
            }

            /*修改代理类型 agent*/
            fuComAgentMapper.updateByPrimaryKeySelective(agent);
        }

    }


    /**
     * 根据介绍人获取 被介绍的客户信息
     * @param introducer
     * @return
     */
    public List<UserMTAccountBO> getAgentIntroducedUser(Integer introducer){
        /*校验信息*/
        if(introducer==null || introducer==0){
            log.error("传入数据为空！");
            throw new DataConflictException(GlobalResultCode.PARAM_NULL_POINTER,"传入数据为空!");
        }

        Map conditionMap=new HashMap();
        conditionMap.put("introducer", introducer);
        conditionMap.put("userState", UserConstant.USER_STATE_NORMAL);
        List<UserMTAccountBO> beIntroducer = fuAccountMtService.getUserMTAccByCondition(conditionMap);
        return beIntroducer;
    }

    /**
     * 计算社区给代理返佣
     * @param agent
     * @param dealBeginDate
     */
    public void dealAgentCommissionFromCommunity(FuComAgent agent,Date dealBeginDate,Date endDate){
        /*校验信息*/
        if(ObjectUtils.isEmpty(agent)){
            log.error("传入数据为空！");
            return;
        }

        /*查找 介绍的客户*/
        List<UserMTAccountBO> beIntroduced=getAgentIntroducedUser(agent.getUserId());
        if(beIntroduced==null ||beIntroduced.size()==0){
            return;
        }
        FuAccountInfo accountInfo = new FuAccountInfo();
        BigDecimal commissionMoney=new BigDecimal(0);
        BigDecimal commissionLots=new BigDecimal(0);
        List<FuAccountCommission> commissions= fuAccountCommissionService.selectList(new EntityWrapper<FuAccountCommission>().eq(FuAccountCommission.USER_ID,agent.getUserId()));
        if(commissions==null ||commissions.size()==0){
            log.error("查询代理人佣金账户失败！ userId:"+agent.getUserId());
            return;
        }
        FuAccountCommission commission=commissions.get(0);

        Map conditionMap =new HashMap();
        for(UserMTAccountBO user:beIntroduced ){
            if(user.getUserType()<agent.getAgentType()){
                /*用户 低于 代理级别 ，不返佣*/
                continue;
            }else if(user.getUserType()>agent.getAgentType()){
                /*用户 超越 代理级别。  用户的介绍人值为0 默认社区*/
                /*修改用户类型 userType*/
                FuUser fuUser=new FuUser();
                fuUser.setId(agent.getUserId());
                fuUser.setIntroducer(0);
                adminService.updateAdmin(fuUser);
            }else if(user.getUserType()==agent.getAgentType()){
                /*用户等于 代理级别，社区返佣10%*/
                conditionMap.put("userId",user.getUserId());
                conditionMap.put("beginDate",dealBeginDate);
                if(endDate!=null){
                    conditionMap.put("endDate",endDate);
                }
                List<FuAccountCommissionFlow> userflows= fuAccountCommissionFlowService.findFlowByCondition(conditionMap);
                if (userflows==null || userflows.size()==0){
                    continue;
                }
                accountInfo=fuAccountInfoService.selectOne(new EntityWrapper<FuAccountInfo>().eq(FuAccountInfo.USER_ID,agent.getUserId()));
                if(ObjectUtils.isEmpty(accountInfo)){
                    log.error("查询代理人 社区账户失败！userId:"+agent.getUserId());
                    return;
                }
                /*社区 返该用户 佣金的10%给代理*/
                FuAccountCommissionFlow agenFlow=new FuAccountCommissionFlow();
                agenFlow.setCreateDate(new Date());
                agenFlow.setModifyDate(new Date());
                agenFlow.setUserId(agent.getUserId());
                agenFlow.setAccountId(accountInfo.getId());
                agenFlow.setCommissionDate(new Date());
                agenFlow.setCommissionType(CommissionConstant.COMMISSION_TYPE_COMMUNITY);
                agenFlow.setCommissionUserType(agent.getAgentType());
                agenFlow.setCommissionRate(CommissionConstant.COMMISSION_SAME_LEVEL_RATE_FROM_COMMUNITY);
                agenFlow.setCommissionLevel(0);
                agenFlow.setCommissionRateType(2);
                agenFlow.setCommissionState(CommissionConstant.COMMISSION_STATE_SUCCESS);
                agenFlow.setCoinType(AccountConstant.ACCOUNT_CION_BALANCE);
                for (FuAccountCommissionFlow userFlow:userflows){
                    commissionLots=commissionLots.add(userFlow.getSourceLots());
                    commissionMoney=commissionMoney.add(userFlow.getCommissionMoney());
                }
                agenFlow.setSourceLots(commissionLots);
                agenFlow.setSourceMoney(commissionMoney);
                agenFlow.setCommissionMoney(commissionMoney.multiply(agenFlow.getCommissionRate()));

                commission.setCommissionSourceMoney(commission.getCommissionSourceMoney().add(commissionMoney));
                commission.setCommissionMoney(commission.getCommissionMoney().add(agenFlow.getCommissionMoney()));
                commission.setCommissionTotal(commission.getCommissionTotal().add(agenFlow.getCommissionMoney()));
                fuAccountCommissionFlowService.insert(agenFlow);
            }
        }
        fuAccountCommissionService.updateById(commission);
    }

    /**
     * 保存
     * @param agent
     */
    public void insertSelective(FuComAgent agent){
        fuComAgentMapper.insertSelective(agent);
    }
}
