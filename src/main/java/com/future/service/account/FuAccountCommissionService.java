package com.future.service.account;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.future.common.constants.AccountConstant;
import com.future.common.constants.CommissionConstant;
import com.future.common.enums.GlobalResultCode;
import com.future.common.exception.BusinessException;
import com.future.common.exception.ParameterInvalidException;
import com.future.common.helper.PageInfoHelper;
import com.future.common.util.DateUtil;
import com.future.entity.account.FuAccountCommission;
import com.future.entity.account.FuAccountCommissionFlow;
import com.future.mapper.account.FuAccountCommissionMapper;
import com.future.service.commission.FuCommissionCustomerService;
import com.future.service.user.AdminService;
import com.future.service.user.UserCommonService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FuAccountCommissionService extends ServiceImpl<FuAccountCommissionMapper, FuAccountCommission> {

    Logger log= LoggerFactory.getLogger(FuAccountCommissionService.class);
    @Autowired
    FuAccountCommissionMapper fuAccountCommissionMapper;
    @Autowired
    FuCommissionCustomerService fuCommissionCustomerService;
    @Autowired
    FuAccountCommissionFlowService fuAccountCommissionFlowService;
    @Autowired
    FuAccountWithdrawService fuAccountWithdrawService;
    @Autowired
    UserCommonService userCommonService;
    @Autowired
    AdminService adminService;

    /**
     * 初始化用户佣金账户
     * @param userId
     */
    public void initAccountCommission(Integer userId,Integer accountId){
        if(userId==null||userId==0||accountId==null||accountId==0){
            log.error("初始化用户佣金账户,用户ID为空！");
            return;
        }

        FuAccountCommission acc=new FuAccountCommission();
        Map conditionMap=new HashMap();
        conditionMap.put(FuAccountCommission.USER_ID,userId);
        List<FuAccountCommission> accountInfos=fuAccountCommissionMapper.selectByMap(conditionMap);
        if(accountInfos!=null && accountInfos.size()>0){
            /*以后账户，设置为正常*/
            acc=accountInfos.get(0);
            acc.setAccountState(AccountConstant.ACCOUNT_STATE_NORMAL);
            fuAccountCommissionMapper.updateByPrimaryKey(acc);
            return;
        }
        /*新建账户*/
        acc.setUserId(userId);
        acc.setAccountId(accountId);
        /*加密后的密码，初始密码默认 跟 用户密码一致*/
        fuAccountCommissionMapper.insertSelective(acc);
    }

    /**
     * 获取佣金账户
     * @return
     */
    public Page<FuAccountCommission> getPageAccountCommisson(Map commissionMap, PageInfoHelper helper){

        /*判断查询条件*/
        if(commissionMap == null||commissionMap.get("operUserId")==null){
            log.error("查询用户列表,获取参数为空！");
            throw new ParameterInvalidException("查询用户列表,获取参数为空！");
        }
        /*判断权限*/
        String operUserId=String.valueOf(commissionMap.get("operUserId"));
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
            return queryAccountCommission(commissionMap,helper);
        }else if(isProjAdmin){
            /*资源组管理员查找*/
            commissionMap.put("projKey",operUserProj);
            return queryAccountCommissionByProject(commissionMap,helper);
        }else {
            /*普通用户查找*/
            commissionMap.put("userId",operUserId);
            return queryAccountCommission(commissionMap,helper);
        }
    }

    /**
     * 查询用户佣金账户列表
     * @param commissionMap
     * @param helper
     * @return
     */
    public Page<FuAccountCommission> queryAccountCommission(Map commissionMap, PageInfoHelper helper){

        Wrapper<FuAccountCommission> wrapper=new EntityWrapper<>();
        if(commissionMap.get("userId")!=null){
            wrapper.eq(FuAccountCommission.USER_ID,commissionMap.get("userId"));
        }
        if(commissionMap.get("accountId")!=null){
            wrapper.eq(FuAccountCommission.ACCOUNT_ID,commissionMap.get("accountId"));
        }
        if(commissionMap.get("accountState")!=null){
            wrapper.eq(FuAccountCommission.ACCOUNT_STATE,commissionMap.get("accountState"));
        }else {
            wrapper.eq(FuAccountCommission.ACCOUNT_STATE,AccountConstant.ACCOUNT_STATE_NORMAL);
        }

        if(helper==null){
            helper=new PageInfoHelper();
        }
        Page<FuAccountCommission> commissions=PageHelper.startPage(helper.getPageNo(),helper.getPageSize());
        selectList(wrapper);
        return commissions;
    }


    /**
     * 以资源组为单位查询用户佣金账户列表
     * @param commissionMap
     * @param helper
     * @return
     */
    public Page<FuAccountCommission> queryAccountCommissionByProject(Map commissionMap, PageInfoHelper helper){

        if(helper==null){
            helper=new PageInfoHelper();
        }
        Page<FuAccountCommission> commissions=PageHelper.startPage(helper.getPageNo(),helper.getPageSize());
        fuAccountCommissionMapper.queryAccountCommissionByProject(commissionMap);
        return commissions;
    }

    /**
     * 根据ID佣金账户
     * @param id
     * @return
     */
    public FuAccountCommission getAccountCommissonById(Integer id){
        if(id==null||id==0){
            log.error("根据ID佣金账户,ID为空");
            throw new ParameterInvalidException(GlobalResultCode.PARAM_NULL_POINTER);
        }
        return fuAccountCommissionMapper.selectByPrimaryKey(id);
    }


    /**
     * 根据userID佣金账户
     * @param userId
     * @return
     */
    public FuAccountCommission getAccountCommissonByUserId(Integer userId){
        if(userId==null||userId==0){
            log.error("根据ID佣金账户,ID为空");
            throw new ParameterInvalidException(GlobalResultCode.PARAM_NULL_POINTER);
        }
        Wrapper<FuAccountCommission> wrapper=new EntityWrapper<>();
        wrapper.eq(FuAccountCommission.USER_ID,userId);
        return selectOne(wrapper);
    }


    /**
     * 计算佣金日结
     * @param userId
     * @param beginTime
     * @param endTime
     */
    public void dealAccountCommissionDaySum(Integer userId,Integer accountId, Date beginTime,Date endTime){

        if(userId==null||userId==0||beginTime==null){
            log.error("计算佣金日结,传入数据为空！");
            throw new BusinessException("计算佣金日结,传入数据为空！");
        }

        /*查询时间段内需要计算的佣金流*/
        List<FuAccountCommissionFlow> flows= fuCommissionCustomerService.selectOrderCustomerSumFlow(userId,accountId, beginTime,endTime);

        if(flows==null||flows.size()==0){
            log.info("用户："+userId+"无佣金记录，date:"+ DateUtil.getCurrDateTime());
            return;
        }

        /*查询佣金账户*/
        FuAccountCommission accountCommission=getAccountCommissonByUserId(userId);
        if(accountCommission==null || accountCommission.getId()==0){
            log.error("计算佣金日结,佣金账户查询为空！");
            throw new BusinessException("计算佣金日结,佣金账户查询为空！");
        }

        BigDecimal commissionMoney=new BigDecimal(0);//'佣金余额'updateByPrimaryKeySelective
        BigDecimal commissionSourceMoney=new BigDecimal(0);//''源发生金额''
        BigDecimal commissionSourceLots=new BigDecimal(0);//''源发生手数''

        /*佣金日结日志*/
        for(FuAccountCommissionFlow flow:flows){
            flow.setCreateDate(new Date());
            flow.setModifyDate(new Date());
            flow.setUserId(userId);
            flow.setAccountId(accountId);
            flow.setCommissionDate(new Date());
            flow.setCommissionState(CommissionConstant.COMMISSION_STATE_SUCCESS);
            flow.setCoinType(AccountConstant.ACCOUNT_CION_BALANCE);
            commissionMoney=commissionMoney.add(flow.getCommissionMoney());
            commissionSourceMoney=commissionSourceMoney.add(flow.getSourceMoney());
            commissionSourceLots=commissionSourceLots.add(flow.getSourceLots());
        }

        accountCommission.setCommissionMoney(accountCommission.getCommissionMoney().add(commissionMoney));//'佣金余额'
        accountCommission.setCommissionTotal(accountCommission.getCommissionTotal().add(commissionMoney));//''总佣金额''
        accountCommission.setCommissionSourceMoney(accountCommission.getCommissionSourceMoney().add(commissionSourceMoney));//''源发生金额''
        accountCommission.setCommissionSourceLots(accountCommission.getCommissionSourceLots().add(commissionSourceLots));//''源发生手数''

        //TODO  团队介绍人返佣判断

        /*更新佣金表数据*/
        fuAccountCommissionMapper.updateByPrimaryKeySelective(accountCommission);

        /*插入佣金流水表*/
        fuAccountCommissionFlowService.insertBatch(flows);
    }

}
