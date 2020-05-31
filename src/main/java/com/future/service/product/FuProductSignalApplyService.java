package com.future.service.product;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.future.common.constants.SignalConstant;
import com.future.common.constants.UserConstant;
import com.future.common.enums.GlobalResultCode;
import com.future.common.exception.BusinessException;
import com.future.common.exception.DataConflictException;
import com.future.common.exception.ParameterInvalidException;
import com.future.common.helper.PageInfoHelper;
import com.future.common.util.CommonUtil;
import com.future.common.util.ConvertUtil;
import com.future.common.util.StringUtils;
import com.future.entity.account.FuAccountInfo;
import com.future.entity.permission.FuPermissionProject;
import com.future.entity.permission.FuPermissionRole;
import com.future.entity.permission.FuPermissionUserProject;
import com.future.entity.permission.FuPermissionUserRole;
import com.future.entity.product.FuProductSignal;
import com.future.entity.product.FuProductSignalApply;
import com.future.entity.product.FuProductSignalPermit;
import com.future.entity.product.FuProductSignalValuation;
import com.future.entity.user.FuUser;
import com.future.mapper.product.FuProductSignalApplyMapper;
import com.future.mapper.product.FuProductSignalMapper;
import com.future.service.account.FuAccountCommissionService;
import com.future.service.account.FuAccountInfoService;
import com.future.service.account.FuAccountMtService;
import com.future.service.permission.PermissionProjectService;
import com.future.service.permission.PermissionRoleService;
import com.future.service.permission.PermissionUserProjectService;
import com.future.service.permission.PermissionUserRoleService;
import com.future.service.user.AdminService;
import com.future.service.user.UserCommonService;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FuProductSignalApplyService extends ServiceImpl<FuProductSignalApplyMapper, FuProductSignalApply> {

    Logger log=LoggerFactory.getLogger(FuProductSignalApplyService.class);
    @Autowired
    FuProductSignalService fuProductSignalService;
    @Autowired
    FuProductSignalApplyMapper fuProductSignalApplyMapper;
    @Autowired
    FuProductSignalMapper fuProductSignalMapper;
    @Autowired
    FuAccountMtService fuAccountMtService;
    @Autowired
    FuAccountInfoService fuAccountInfoService;
    @Autowired
    FuAccountCommissionService fuAccountCommissionService;
    @Autowired
    PermissionUserProjectService permissionUserProjectService;
    @Autowired
    PermissionRoleService permissionRoleService;
    @Autowired
    AdminService adminService;
    @Autowired
    PermissionUserRoleService permissionUserRoleService;
    @Autowired
    UserCommonService userCommonService;
    @Autowired
    PermissionProjectService permissionProjectService;
    @Autowired
    FuProductSignalPermitService fuProductSignalPermitService;
    @Autowired
    FuProductSignalValuationService fuProductSignalValuationService;


    /**
     * 查询申请列表
     * @param conditionMap
     * @param helper
     * @return
     */
    public com.github.pagehelper.Page<FuProductSignalApply> querySignalApply(Map conditionMap, PageInfoHelper helper){

        /*判断查询条件*/
        if(conditionMap == null||conditionMap.get("operId")==null){
            log.error("查询列表,获取参数为空！");
            throw new ParameterInvalidException("查询列表,获取参数为空！");
        }
        /*判断权限*/
        String operId=String.valueOf(conditionMap.get("operId"));
        if(StringUtils.isEmpty(operId)){
            log.error("查询用户列表,用户未登录！");
            throw new ParameterInvalidException("查询用户列表,获取参数为空！");
        }
        /*校验信息*/
        Integer operUserProj=userCommonService.getUserProjKey(Integer.parseInt(operId));
        Boolean isProjAdmin=userCommonService.isAdministrator(Integer.parseInt(operId),operUserProj);
        if(operUserProj==null){
            log.error("查询用户列表,用户权限有误！");
            throw new ParameterInvalidException("查询用户列表,用户权限有误！");
        }

        if(isProjAdmin&&operUserProj==0){
            /*超管查询*/
            return findSignalApplyByCondition(conditionMap,helper);
        }else if(isProjAdmin){
            /*资源组管理员查找*/
            conditionMap.put("projKey",operUserProj);
            return findSignalApplyByCondition(conditionMap,helper);
        }else {
            /*普通用户查找*/
            conditionMap.put("userId",operId);
            return findSignalApplyByCondition(conditionMap,helper);
        }
    }

    /**
     * 根据条件查找信号源信息
     * @param conditionMap
     * @return
     */
    public com.github.pagehelper.Page<FuProductSignalApply> findSignalApplyByCondition(Map conditionMap, PageInfoHelper helper){

        Wrapper<FuProductSignalApply> wrapper=new EntityWrapper<FuProductSignalApply>();
        if(!ObjectUtils.isEmpty(conditionMap.get("applyId"))){
            wrapper.eq(FuProductSignalApply.APPLY_ID,String.valueOf(conditionMap.get("applyId")));
        }
        if(!ObjectUtils.isEmpty(conditionMap.get("userId"))){
            wrapper.eq(FuProductSignalApply.USER_ID,String.valueOf(conditionMap.get("userId")));
        }
        if(!ObjectUtils.isEmpty(conditionMap.get("signalName"))){
            wrapper.eq(FuProductSignalApply.SIGNAL_NAME,String.valueOf(conditionMap.get("signalName")));
        }
        if(!ObjectUtils.isEmpty(conditionMap.get("projKey"))){
            wrapper.eq(FuProductSignalApply.PROJ_KEY,String.valueOf(conditionMap.get("projKey")));
        }
        if(!ObjectUtils.isEmpty(conditionMap.get("applyState"))){
            if(String.valueOf(conditionMap.get("applyState")).indexOf(",")<0){
                wrapper.eq(FuProductSignalApply.APPLY_STATE,String.valueOf(conditionMap.get("applyState")));
            }else {
                //多状态
                String[] state=String.valueOf(conditionMap.get("applyState")).split(",");
                Wrapper<FuProductSignalApply> stateWrapper=new EntityWrapper<FuProductSignalApply>();
                wrapper.andNew().eq(FuProductSignalApply.APPLY_STATE,state[0]);
                for (int i=1;i<state.length;i++){
                    wrapper.or().eq(FuProductSignalApply.APPLY_STATE,state[i]);
                }
            }
        }
        if(!ObjectUtils.isEmpty(conditionMap.get("serverName"))){
            wrapper.eq(FuProductSignalApply.SERVER_NAME,String.valueOf(conditionMap.get("serverName")));
        }
        if(!ObjectUtils.isEmpty(conditionMap.get("mtAccId"))){
            wrapper.eq(FuProductSignalApply.MT_ACC_ID,String.valueOf(conditionMap.get("mtAccId")));
        }
        if(!ObjectUtils.isEmpty(conditionMap.get("createDate"))){
            if(String.valueOf(conditionMap.get("createDate")).indexOf(",")<0){
                wrapper.eq(FuProductSignalApply.CREATE_DATE,conditionMap.get("createDate"));
            }else {
                //时间段
                String[] dateClose=String.valueOf(conditionMap.get("createDate")).split(",");
                if(dateClose.length!=2){
                    log.error("创建时间段数据传入错误！"+conditionMap.get("orderCloseDate"));
                    throw new DataConflictException(GlobalResultCode.PARAM_VERIFY_ERROR,"创建时间段数据传入错误！"+conditionMap.get("orderCloseDate"));
                }
                wrapper.gt(FuProductSignalApply.CREATE_DATE,dateClose[0]);
                wrapper.lt(FuProductSignalApply.CREATE_DATE,dateClose[1]);
            }
        }
        if(!ObjectUtils.isEmpty(conditionMap.get("applyDate"))){
            if(String.valueOf(conditionMap.get("applyDate")).indexOf(",")<0){
                wrapper.eq(FuProductSignalApply.APPLY_DATE,conditionMap.get("applyDate"));
            }else {
                //时间段
                String[] dateClose=String.valueOf(conditionMap.get("applyDate")).split(",");
                if(dateClose.length!=2){
                    log.error("申请时间段数据传入错误！"+conditionMap.get("APPLY_DATE"));
                    throw new DataConflictException(GlobalResultCode.PARAM_VERIFY_ERROR,"申请时间段数据传入错误！"+conditionMap.get("APPLY_DATE"));
                }
                wrapper.gt(FuProductSignalApply.APPLY_DATE,dateClose[0]);
                wrapper.lt(FuProductSignalApply.APPLY_DATE,dateClose[1]);
            }
        }
        if(!ObjectUtils.isEmpty(conditionMap.get("checkDate"))){
            if(String.valueOf(conditionMap.get("checkDate")).indexOf(",")<0){
                wrapper.eq(FuProductSignalApply.CHECK_DATE,conditionMap.get("checkDate"));
            }else {
                //时间段
                String[] dateClose=String.valueOf(conditionMap.get("checkDate")).split(",");
                if(dateClose.length!=2){
                    log.error("创建时间段数据传入错误！"+conditionMap.get("checkDate"));
                    throw new DataConflictException(GlobalResultCode.PARAM_VERIFY_ERROR,"创建时间段数据传入错误！"+conditionMap.get("checkDate"));
                }
                wrapper.gt(FuProductSignalApply.CHECK_DATE,dateClose[0]);
                wrapper.lt(FuProductSignalApply.CHECK_DATE,dateClose[1]);
            }
        }

        if(helper==null){
            helper=new PageInfoHelper();
        }
        com.github.pagehelper.Page<FuProductSignalApply> commissions=PageHelper.startPage(helper.getPageNo(),helper.getPageSize());
        fuProductSignalApplyMapper.selectList(wrapper);
        return commissions;
    }

    /**
     * 根据ID获取信号源信息
     * @param signalId
     * @return
     */
    public FuProductSignalApply findSignalApplyById(int signalId){
        /*校验信息*/
        if(signalId<1){
            log.error("传入数据为空！");
            throw new DataConflictException(GlobalResultCode.PARAM_NULL_POINTER,"传入数据为空!");
        }
        return selectById(signalId);
    }


    /**
     * 保存信号源信息
     * @param signalMap
     */
    @Transactional
    public int saveProductSignal(Map signalMap){
        /*校验信息*/
        if(ObjectUtils.isEmpty(signalMap.get("userId"))){
            log.error("申请人信息不能为空！");
            throw new DataConflictException("申请人信息不能为空！");
        }
        if(ObjectUtils.isEmpty(signalMap.get("signalName"))){
            log.error("信号源名称不能为空！");
            throw new DataConflictException("信号源名称不能为空！！");
        }
        if(ObjectUtils.isEmpty(signalMap.get("serverName"))){
            log.error("服务器名称不能为空！");
            throw new DataConflictException("服务器名称不能为空！！");
        }
        if(ObjectUtils.isEmpty(signalMap.get("mtAccId"))){
            log.error("MT账户不能为空！");
            throw new DataConflictException("MT账户不能为空！！");
        }
        if(!CommonUtil.isNumeric(String.valueOf(signalMap.get("mtAccId")))){
            log.error("MT账户必须为数字类型！");
            throw new DataConflictException("MT账户必须为数字类型！");
        }
        if(ObjectUtils.isEmpty(signalMap.get("mtPasswordWatch"))){
            log.error("MT账户密码不能为空！");
            throw new DataConflictException("MT账户密码不能为空！！");
        }
        if(ObjectUtils.isEmpty(signalMap.get("email"))){
            log.error("email不能为空！");
            throw new DataConflictException("email不能为空！！");
        }
        if(ObjectUtils.isEmpty(signalMap.get("phone"))){
            log.error("联系人电话不能为空！");
            throw new DataConflictException("联系人电话不能为空！！");
        }
        if(ObjectUtils.isEmpty(signalMap.get("qqNumber"))){
            log.error("联系人QQ不能为空！");
            throw new DataConflictException("联系人QQ不能为空！！");
        }
        if(ObjectUtils.isEmpty(signalMap.get("qqNumber"))){
            log.error("联系人QQ不能为空！");
            throw new DataConflictException("联系人QQ不能为空！！");
        }
        /*校验申请人*/
        if(ObjectUtils.isEmpty(signalMap.get("operUserId"))){
            log.error("申请人信息为空！");
            throw new DataConflictException("申请人信息为空！");
        }

        /*组装信息*/
        FuProductSignalApply signal=setSignal(signalMap);

        /*查看 申请人和用户 是否属于同一团队工程*/
        Integer userProjKey= userCommonService.getUserProjKey(signal.getUserId());
        Integer operProjKey= userCommonService.getUserProjKey(signal.getOperUserId());
        if(userProjKey==null||operProjKey==null){
            log.error("查询用户和申请人团队信息失败！");
            throw new BusinessException("查询用户和申请人团队信息失败！");
        }
        if(userProjKey!=operProjKey){
            log.error("只能申请自己团队的用户为信号源！");
            throw new BusinessException("只能申请自己团队的用户为信号源！");
        }

        // 判断该信号源是否满足申请条件
        Map conditionMap = new HashMap();
        conditionMap.put(FuProductSignalApply.MT_ACC_ID,signalMap.get("mtAccId"));
        List<FuProductSignalApply> applies=fuProductSignalMapper.selectByMap(conditionMap);
        if(applies!=null&&applies.size()>0){
            log.error("该MT账户已申请！");
            throw new DataConflictException(GlobalResultCode.DATA_ALREADY_EXISTED,"该MT账户已申请！");
        }
        /*判断信号源是否已存在*/
        List<FuProductSignal> currentSignal= fuProductSignalMapper.selectByMap(conditionMap);
        if(currentSignal!=null&&currentSignal.size()>0){
            log.error("该账号已为信号源，不能再次申请！");
            throw new BusinessException("该账号已为信号源，不能再次申请！");
        }


        Map projectMap=new HashMap();
        projectMap.put(FuPermissionProject.PROJ_KEY,operProjKey);
        List<FuPermissionProject> projects= permissionProjectService.selectByMap(projectMap);
        if(projects==null||projects.size()==0){
            log.error("查询团队信息失败！");
            throw new BusinessException(GlobalResultCode.RESULE_DATA_NONE,"查询团队信息失败！");
        }

        /*默认数据填充*/
        signal.setCreateDate(new Date());
        signal.setApplyState(SignalConstant.SIGNAL_APPLY_STATE_SAVE);
        signal.setModifyDate(new Date());
        signal.setProjKey(operProjKey);
        signal.setProjName(projects.get(0).getProjName());

        return fuProductSignalApplyMapper.insertSelective(signal);
    }

    /**
     * 修改信号源信息
     * @param signalMap
     */
    public int updateProductSignalApply(int signalId, Map signalMap){
        /*校验信息*/
        /*校验信息*/
        if(signalId<1){
            log.error("传入ID数据为空！");
            throw new DataConflictException(GlobalResultCode.PARAM_NULL_POINTER,"传入ID数据为空!");
        }
        /*组装信息*/
        FuProductSignalApply signal=setSignal(signalMap);
        if(!CommonUtil.isNumeric(String.valueOf(signal.getMtAccId()))){
            log.error("MT账户必须为数字类型！");
            throw new DataConflictException("MT账户必须为数字类型！");
        }
        /*默认数据填充*/
        signal.setModifyDate(new Date());


        return fuProductSignalApplyMapper.updateByPrimaryKeySelective(signal);
    }

    /**
     * 提交申请信息
     * @param signalId
     * @param mesage
     * @return
     */
    public int submitProductSignal(int signalId,String mesage){
        /**校验信息*/
        if(signalId<1){
            log.error("传入数据为空！");
            throw new DataConflictException(GlobalResultCode.PARAM_NULL_POINTER,"传入数据为空!");
        }

        /*组装信息*/
        FuProductSignalApply signalApply=new FuProductSignalApply();
        signalApply.setId(signalId);
        /*状态：待审核*/
        signalApply.setApplyState(SignalConstant.SIGNAL_APPLY_STATE_SUBMIT);
        signalApply.setRemarks(mesage);

        return fuProductSignalApplyMapper.updateByPrimaryKeySelective(signalApply);
    }

    /**
     * 审核信号源信息
     * @param signalApplyId
     * @param state
     * @param mesage
     */
    @Transactional(propagation=Propagation.REQUIRED)
    public void reviewProductSignal(int signalApplyId,int state, String mesage){
        /**校验信息*/
        if(signalApplyId<1){
            log.error("传入数据为空！");
            throw new DataConflictException(GlobalResultCode.PARAM_NULL_POINTER,"传入数据为空!");
        }
        /*组装信息*/
        FuProductSignalApply signalApply=new FuProductSignalApply();
        signalApply.setId(signalApplyId);
        if(state== SignalConstant.SIGNAL_APPLY_STATE_NORMAL){
            /*审核通过*/
            FuProductSignalApply apply=findSignalApplyById(signalApplyId);
            if(ObjectUtils.isEmpty(apply)){
                log.error("查询申请信息错误！");
                throw new BusinessException("查询申请信息错误！");
            }
            FuProductSignal signal=ConvertUtil.convertSignal(apply);
            signal.setCheckDate(new Date());
            signal.setCreateDate(new Date());
            signal.setModifyDate(new Date());

            /*判断信号源是否已存在*/
            Map conditionMap =new HashMap();
            conditionMap.put(FuProductSignal.MT_ACC_ID,signal.getMtAccId());
            List<FuProductSignal> currentSignal= fuProductSignalMapper.selectByMap(conditionMap);
            if(currentSignal!=null&&currentSignal.size()>0){
                log.error("该账号已为信号源，不能再次申请！");
                throw new BusinessException("该账号已为信号源，不能再次申请！");
            }


            /*建立社区佣金账户*/
            conditionMap.clear();
            conditionMap.put(FuProductSignalApply.USER_ID,signal.getUserId());
            List<FuAccountInfo> accountInfos= fuAccountInfoService.selectByMap(conditionMap);
            if(accountInfos==null || accountInfos.size()==0){
                log.error("查询社区账户信息错误！");
                throw new BusinessException("查询社区账户信息错误！");
            }
            fuAccountCommissionService.initAccountCommission(signal.getUserId(),accountInfos.get(0).getId());

            /*变更用户类型*/
            FuUser user=adminService.selectById(signal.getUserId());
            if(user==null){
                log.error("查询用户信息错误！");
                throw new BusinessException("查询用户信息错误！");
            }
            FuPermissionUserRole userRole=permissionUserRoleService.selectOne((new EntityWrapper<FuPermissionUserRole>().eq(FuPermissionUserRole.USER_ID,signal.getUserId())));
            if(userRole==null){
                log.error("查询用户角色信息错误！");
                throw new BusinessException("查询用户角色信息错误！");
            }
            /*查询用户所属项目*/
            FuPermissionUserProject userProject= permissionUserProjectService.selectOne(new EntityWrapper<FuPermissionUserProject>()
                    .eq(FuPermissionUserProject.USER_ID,user.getId()));
            /*设置角色信息*/
            FuPermissionRole defaultRole= permissionRoleService.getRoleByProject(userProject.getProjKey(),UserConstant.USER_TYPE_SIGNAL);
            if(defaultRole!=null){
                userRole.setRoleId(defaultRole.getId());
                /*分配相关角色*/
                permissionUserRoleService.updateById(userRole);
            }

            /*变更用户类型*/
            user.setUserType(UserConstant.USER_TYPE_SIGNAL);
            adminService.updateById(user);

            /*修改绑定MT账号状态  isSignal=1、 设置端口等 */
            fuAccountMtService.checkSignalMtAccount(apply.getUserId(),apply.getServerName(),apply.getMtAccId());

            /*保存信号源*/
            int isInsert= fuProductSignalMapper.insertSelective(signal);
            if(isInsert==0){
                throw new BusinessException("信号源保存失败！");
            }

            /*设置信号源权限*/
            Map condition=new HashMap();
            condition.put(FuProductSignal.MT_ACC_ID,signal.getMtAccId());
            condition.put(FuProductSignal.USER_ID,signal.getUserId());
            List<FuProductSignal> newSignals= fuProductSignalService.selectByMap(condition);
            if(newSignals==null||newSignals.size()==0){
                throw new BusinessException("信号源保存失败！");
            }
            FuProductSignalPermit permit=new FuProductSignalPermit();
            permit.setSignalId(newSignals.get(0).getId());
            permit.setProjKey(signal.getProjKey());
            fuProductSignalPermitService.insertSelective(permit);

            /*设置信号源评级*/
            FuProductSignalValuation valuation=new FuProductSignalValuation();
            valuation.setSignalId(newSignals.get(0).getId());
            valuation.setLevel(SignalConstant.SIGNAL_LEVEL_DEFAULT);
            valuation.setScore(new BigDecimal(SignalConstant.SIGNAL_SCORE_DEFAULT));
            valuation.setFundSizeScore(new BigDecimal(SignalConstant.SIGNAL_SCORE_DEFAULT));
            valuation.setNonFlukeProfitScore(new BigDecimal(SignalConstant.SIGNAL_SCORE_DEFAULT));
            valuation.setProfitScore(new BigDecimal(SignalConstant.SIGNAL_SCORE_DEFAULT));
            valuation.setRiskControlScore(new BigDecimal(SignalConstant.SIGNAL_SCORE_DEFAULT));
            valuation.setSteadyScore(new BigDecimal(SignalConstant.SIGNAL_SCORE_DEFAULT));
            fuProductSignalValuationService.insertSelective(valuation);

            signalApply.setApplyState(SignalConstant.SIGNAL_APPLY_STATE_NORMAL);

        }else if(state==SignalConstant.SIGNAL_APPLY_STATE_UNPASS){
            /*审核未通过*/
            signalApply.setApplyState(SignalConstant.SIGNAL_APPLY_STATE_UNPASS);
        }else {
            log.error("审核状态错误！");
            throw new DataConflictException(GlobalResultCode.PARAM_IS_INVALID,"核状态错误！");
        }
        signalApply.setRemarks(mesage);
        /*修改数据*/
        updateById(signalApply);
    }

    /**
     * 删除信号源信息
     * @param signalId
     */
    public Boolean deleteProductSignalApply(int signalId){
        /**校验信息*/
        /*校验信息*/
        if(signalId<1){
            log.error("传入数据为空！");
            throw new DataConflictException(GlobalResultCode.PARAM_NULL_POINTER,"传入数据为空!");
        }
        return deleteById(signalId);
    }

    /**
     * 填充信号源申请数值
     * @param signalMap
     * @return
     */
    public FuProductSignalApply setSignal(Map signalMap){
        /*组装信息*/
        FuProductSignalApply signal=new FuProductSignalApply();
        try {
            if(!ObjectUtils.isEmpty(signalMap.get("id"))){
                signal.setId(Integer.valueOf(String.valueOf(signalMap.get("id"))));
            }
            if(!ObjectUtils.isEmpty(signalMap.get("userId"))){
                signal.setUserId(Integer.valueOf(String.valueOf(signalMap.get("userId"))));
            }
            if(!ObjectUtils.isEmpty(signalMap.get("operUserId"))){
                signal.setOperUserId(Integer.valueOf(String.valueOf(signalMap.get("operUserId"))));
            }
            if(!ObjectUtils.isEmpty(signalMap.get("signalName"))){
                signal.setSignalName(String.valueOf(signalMap.get("signalName")));
            }
            if(!ObjectUtils.isEmpty(signalMap.get("serverName"))){
                signal.setServerName(String.valueOf(signalMap.get("serverName")));
            }
            if(!ObjectUtils.isEmpty(signalMap.get("state"))){
                signal.setApplyState(Integer.parseInt(String.valueOf(signalMap.get("state"))));
            }
            if(!ObjectUtils.isEmpty(signalMap.get("applyState"))){
                signal.setApplyState(Integer.parseInt(String.valueOf(signalMap.get("applyState"))));
            }
            if(!ObjectUtils.isEmpty(signalMap.get("mtAccId"))){
                signal.setMtAccId(String.valueOf(signalMap.get("mtAccId")));
            }
            if(!ObjectUtils.isEmpty(signalMap.get("mtPasswordWatch"))){
                signal.setMtPasswordWatch(String.valueOf(signalMap.get("mtPasswordWatch")));
            }
            if(!ObjectUtils.isEmpty(signalMap.get("email"))){
                signal.setEmail(String.valueOf(signalMap.get("email")));
            }
            if(!ObjectUtils.isEmpty(signalMap.get("phone"))){
                signal.setPhone(String.valueOf(signalMap.get("phone")));
            }
            if(!ObjectUtils.isEmpty(signalMap.get("qqNumber"))){
                signal.setQqNumber(String.valueOf(signalMap.get("qqNumber")));
            }
            if(!ObjectUtils.isEmpty(signalMap.get("mtPasswordTrade"))){
                signal.setMtPasswordTrade(String.valueOf(signalMap.get("mtPasswordTrade")));
            }
            if(!ObjectUtils.isEmpty(signalMap.get("signalCurrency"))){
                signal.setSignalCurrency(String.valueOf(signalMap.get("signalCurrency")));
            }
            if(!ObjectUtils.isEmpty(signalMap.get("historyWithdraw"))){
                String withdraw=String.valueOf(signalMap.get("historyWithdraw"));
                /*如果是百分比 则转换为小数*/
                if(withdraw.indexOf("%")>=0){
                    signal.setHistoryWithdraw(new BigDecimal((Double)NumberFormat.getPercentInstance().parse(withdraw)));
                }else if(Double.parseDouble(withdraw)>1){
                    log.error("历史最大回撤 校验错误！ 数据不能大于1（百分比）");
                }else {
                    signal.setHistoryWithdraw(new BigDecimal(withdraw));
                }
            }
            if(!ObjectUtils.isEmpty(signalMap.get("monthlyAverageIncome"))){
                String averageIncome=String.valueOf(signalMap.get("monthlyAverageIncome"));
                /*如果是百分比 则转换为小数*/
                if(averageIncome.indexOf("%")>=0){
                    signal.setMonthlyAverageIncome(new BigDecimal((Double)NumberFormat.getPercentInstance().parse(averageIncome)));
                }else if(Double.parseDouble(averageIncome)>1){
                    log.error("每月收益 校验错误！ 数据不能大于1（百分比）");
                }else {
                    signal.setMonthlyAverageIncome(new BigDecimal(averageIncome));
                }
            }
            if(!ObjectUtils.isEmpty(signalMap.get("annualizedExpectedReturn"))){
                String expectedReturn=String.valueOf(signalMap.get("annualizedExpectedReturn"));
                /*如果是百分比 则转换为小数*/
                if(expectedReturn.indexOf("%")>=0){
                    signal.setAnnualizedExpectedReturn(new BigDecimal((Double)NumberFormat.getPercentInstance().parse(expectedReturn)));
                }else if(Double.parseDouble(expectedReturn)>1){
                    log.error("每月收益 校验错误！ 数据不能大于1（百分比）");
                }else {
                    signal.setAnnualizedExpectedReturn(new BigDecimal(expectedReturn));
                }
            }
            if(!ObjectUtils.isEmpty(signalMap.get("signalTem"))){
                signal.setSignalTem(String.valueOf(signalMap.get("signalTem")));
            }
            if(!ObjectUtils.isEmpty(signalMap.get("signalDesc"))){
                signal.setSignalDesc(String.valueOf(signalMap.get("signalDesc")));
            }

        }catch (Exception e){
            log.error(e.getMessage(),e);
            throw new DataConflictException(e);
        }
        return signal;
    }
}
