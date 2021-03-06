package com.future.service.com;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.future.common.constants.AccountConstant;
import com.future.common.constants.AgentConstant;
import com.future.common.constants.CommissionConstant;
import com.future.common.constants.UserConstant;
import com.future.common.enums.GlobalResultCode;
import com.future.common.exception.BusinessException;
import com.future.common.exception.DataConflictException;
import com.future.common.exception.ParameterInvalidException;
import com.future.common.helper.PageInfoHelper;
import com.future.common.util.ConvertUtil;
import com.future.entity.account.FuAccountCommission;
import com.future.entity.account.FuAccountCommissionFlow;
import com.future.entity.account.FuAccountInfo;
import com.future.entity.com.FuComAgent;
import com.future.entity.permission.FuPermissionRole;
import com.future.entity.permission.FuPermissionUserProject;
import com.future.entity.permission.FuPermissionUserRole;
import com.future.entity.user.FuUser;
import com.future.entity.user.FuUserIdentity;
import com.future.mapper.com.FuComAgentMapper;
import com.future.pojo.bo.account.UserMTAccountBO;
import com.future.service.account.FuAccountCommissionFlowService;
import com.future.service.account.FuAccountCommissionService;
import com.future.service.account.FuAccountInfoService;
import com.future.service.account.FuAccountMtService;
import com.future.service.permission.PermissionRoleService;
import com.future.service.permission.PermissionUserProjectService;
import com.future.service.permission.PermissionUserRoleService;
import com.future.service.user.AdminService;
import com.future.service.user.FuUserIdentityService;
import com.future.service.user.UserCommonService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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
    @Autowired
    UserCommonService userCommonService;
    @Autowired
    FuUserIdentityService fuUserIdentityService;

    /**
     * 根据条件查找代理信息
     * @param conditionMap
     * @return
     */
    public Page<FuComAgent> findAgentByCondition(Map conditionMap, PageInfoHelper helper){
        /*校验信息*/
        Wrapper<FuComAgent> wrapper=new EntityWrapper<FuComAgent>();

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

        if(helper==null){
            helper=new PageInfoHelper();
        }
        Page<FuComAgent> agenets= PageHelper.startPage(helper.getPageNo(),helper.getPageSize());
        selectList(wrapper);
        return agenets;
    }

    /**
     * 根据条件查询代理信息
     * @param agentConditon
     * @param helper
     * @return
     */
    public Page<FuComAgent> queryAgentPage(Map agentConditon,PageInfoHelper helper){
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
            return findAgentByCondition(agentConditon,helper);
        }else if(isProjAdmin){
            /*资源组管理员查找*/
            agentConditon.put("projKey",operUserProj);
            return findAgentByProjCondtion(agentConditon,helper);
        }else {
            /*普通用户查找*/
            agentConditon.put("userId",operUserId);
            return findAgentByCondition(agentConditon,helper);
        }
    }

    /**
     * 根据proj获取代理信息
     * @param agentConditon
     * @param helper
     */
    public Page<FuComAgent> findAgentByProjCondtion(Map agentConditon,PageInfoHelper helper){
        /*判断查询条件*/
        if(agentConditon == null||agentConditon.get("operUserId")==null){
            log.error("查询代理信息列表,获取参数为空！");
            throw new ParameterInvalidException("查询代理信息列表,获取参数为空！");
        }
        if(helper==null){
            helper=new PageInfoHelper();
        }
        Page<FuComAgent> userPage= PageHelper.startPage(helper.getPageNo(),helper.getPageSize());
        fuComAgentMapper.queryAgentByProjCondition(agentConditon);
        return userPage;
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

        /*组装信息*/
        FuComAgent agent=(FuComAgent)ConvertUtil.MapToJavaBean((HashMap) agentMap,FuComAgent.class);
        agent.setCreateDate(new Date());
        agent.setModifyDate(new Date());
        agent.setApplyDate(new Date());
        /*默认数据填充*/

        /*校验数据是否已存在*/
        FuComAgent oldAgent=findAgentByUserId(agent.getUserId());
        if(oldAgent!=null&&oldAgent.getUserId()>0){
            log.error("改用户代理信息已存在，请检查数据！");
            throw new BusinessException("改用户代理信息已存在，请检查数据！");
        }

        return fuComAgentMapper.insertSelective(agent);

    }

    /**
     * 根据用户ID获取代理信息
     * @return
     */
    public  FuComAgent findAgentByUserId(Integer userId){
        if(userId==null || userId==0){
            return null;
        }
        Wrapper<FuComAgent> wrapper=new EntityWrapper<>();
        wrapper.eq(FuComAgent.USER_ID,userId);
        return selectOne(wrapper);
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
        if(list==null|| list.size()==0){
            log.error("查询用户账户失败！");
            throw new DataConflictException(GlobalResultCode.RESULE_DATA_NONE,"查询用户账户失败！");
        }
        /*查询余额最大的账户*/
        UserMTAccountBO mtAccountBO=list.get(0);
        for(UserMTAccountBO accountBO:list){
            if(accountBO.getBalance().compareTo(mtAccountBO.getBalance())>0){
                mtAccountBO=accountBO;
            }
        }
        /*计算 评估*/
        agentUpgrade(mtAccountBO);
    }

    /**
     * 根据用户账户信息 判断代理升级
     * @param accountBO
     */
    @Transactional(rollbackFor = Exception.class)
    public void agentUpgrade(UserMTAccountBO accountBO){

        /*判断代理是否可升级*/
        if(accountBO.getUserType()>= UserConstant.USER_TYPE_PIB){
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
            }else if(user.getUserType()<UserConstant.USER_TYPE_IB && user.getUserType() > UserConstant.USER_TYPE_PIB){
                numUser++;
            }
        }

        boolean checkPass=false;
        /*判断代理当前级别*/
        int agentType = accountBO.getUserType();
        if(agentType < UserConstant.USER_TYPE_IB && agentType > UserConstant.USER_TYPE_PIB){
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
            checkPass=true;
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
            checkPass=true;
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
            checkPass=true;
        }

        /*升级*/
        if(checkPass){
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

            /*客户身份变更*/
            FuUserIdentity identity=fuUserIdentityService.selectByCondition(accountBO.getUserId(), UserConstant.USER_IDENTITY_AGENT);

            Map conditionMap=new HashMap();
            conditionMap.put(FuComAgent.USER_ID,accountBO.getUserId());
            List<FuComAgent> agents= fuComAgentMapper.selectByMap(conditionMap);
            if(agents==null||agents.size()==0){
                if(agentType==AgentConstant.AGENT_TYPE_IB){

                    /*用户身份变更*/
                    if(identity==null){
                        /*新增代理身份*/
                        FuUserIdentity fuUserIdentity=new FuUserIdentity();
                        fuUserIdentity.setUserId(accountBO.getUserId());
                        fuUserIdentity.setCreateDate(new Date());
                        fuUserIdentity.setIdentity(UserConstant.USER_IDENTITY_AGENT);
                        fuUserIdentity.setIdentityLevel(agentType);
                        fuUserIdentityService.insertSelective(fuUserIdentity);
                    }

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
            }else {
                //代理升级
                if(identity==null){
                    log.error("客户代理身份查询失败！");
                    throw new BusinessException("客户代理身份查询失败！");
                }
                identity.setIdentityLevel(agentType);
                fuUserIdentityService.updateByPrimaryKeySelective(identity);

                FuComAgent agent=agents.get(0);
                agent.setAgentType(agentType);
                /*修改代理类型 agent*/
                fuComAgentMapper.updateByPrimaryKeySelective(agent);
            }
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
        for(UserMTAccountBO beIntroducedUser:beIntroduced ){
            if(beIntroducedUser.getUserType()<agent.getAgentType()){
                /*用户 低于 代理级别 ，不返佣*/
                continue;
            }else if(beIntroducedUser.getUserType()>agent.getAgentType()){
                /*用户 超越 代理级别。  用户的介绍人值为0 默认社区*/
                /*修改用户类型 userType*/
                FuUser fuUser=new FuUser();
                fuUser.setId(agent.getUserId());
                fuUser.setIntroducer(0);
                adminService.updateAdmin(fuUser);
            }else if(beIntroducedUser.getUserType()==agent.getAgentType()){
                /*用户等于 代理级别，社区返佣10%*/
                conditionMap.put("userId",beIntroducedUser.getUserId());
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
                agenFlow.setCommissionLevel(CommissionConstant.COMMISSION_USER_LEVEL_ZERO);
                agenFlow.setCommissionRateType(CommissionConstant.COMMISSION_RATE_TYPE_MONEY_COMMISSION);
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
        /*校验数据是否已存在*/
        FuComAgent oldAgent=findAgentByUserId(agent.getUserId());
        if(oldAgent!=null&&oldAgent.getUserId()>0){
            log.error("改用户代理信息已存在，请检查数据！");
            throw new BusinessException("改用户代理信息已存在，请检查数据！");
        }
        fuComAgentMapper.insertSelective(agent);
    }
}
