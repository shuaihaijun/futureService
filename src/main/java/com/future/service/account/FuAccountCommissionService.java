package com.future.service.account;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.future.common.constants.AccountConstant;
import com.future.common.constants.CommissionConstant;
import com.future.common.constants.CommonConstant;
import com.future.common.enums.GlobalResultCode;
import com.future.common.exception.BusinessException;
import com.future.common.exception.ParameterInvalidException;
import com.future.common.helper.PageInfoHelper;
import com.future.common.util.DateUtil;
import com.future.entity.account.FuAccountCommission;
import com.future.entity.account.FuAccountCommissionFlow;
import com.future.entity.account.FuAccountInfo;
import com.future.entity.account.FuAccountWithdraw;
import com.future.entity.commission.FuCommissionCustomer;
import com.future.entity.commission.FuCommissionLevel;
import com.future.entity.user.FuUser;
import com.future.mapper.account.FuAccountCommissionMapper;
import com.future.service.commission.FuCommissionCustomerService;
import com.future.service.user.AdminService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.omg.PortableInterceptor.INACTIVE;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

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
    AdminService adminService;
    @Value("${SUPER_ADMINISTRATOR}")
    private String superAdministrator;

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
    public PageInfo<FuAccountCommission> getPageAccountCommisson(FuAccountCommission commission, PageInfoHelper helper){

        Wrapper<FuAccountCommission> wrapper=new EntityWrapper<>();

        if(commission.getUserId()!=null){
            wrapper.eq(FuAccountCommission.USER_ID,commission.getUserId());
        }
        if(commission.getAccountId()!=null){
            wrapper.eq(FuAccountCommission.ACCOUNT_ID,commission.getAccountId());
        }
        if(commission.getAccountState()!=null){
            wrapper.eq(FuAccountCommission.ACCOUNT_STATE,commission.getAccountState());
        }

        if(helper==null){
            helper=new PageInfoHelper();
        }
        PageHelper.startPage(helper.getPageNo(),helper.getPageSize());
        List<FuAccountCommission> commissions= selectList(wrapper);
        return new PageInfo<>(commissions);
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
     * 佣金提取
     * @param userId
     * @param accountId
     * @param operId
     * @param commissionMoney
     * @return
     */
    public Boolean accountCommissonWithdraw(Integer userId, Integer accountId,Integer operId,BigDecimal commissionMoney){
        if(userId==null||accountId==null||operId==null
            ||userId==0||accountId==0||operId==0){
            log.error("佣金提取,传入数据为空！");
            throw new BusinessException("佣金提取,传入数据为空！");
        }

        if(commissionMoney==null||commissionMoney.compareTo(new BigDecimal(0))==0){
            log.error("佣金提取,传入金额为空！");
            throw new BusinessException("佣金提取,传入金额为空！");
        }

        FuAccountCommission commission=selectOne(new EntityWrapper<FuAccountCommission>().eq(FuAccountCommission.USER_ID,userId)
                    .and().eq(FuAccountCommission.ACCOUNT_ID,accountId));
        if(commission==null||commission.getId()==null){
            log.error("佣金提取,根據用户查询账户为空！");
            throw new BusinessException("佣金提取,根據用户查询账户为空！");
        }
        /*判断余额是否够用*/
        if(commissionMoney.compareTo(commission.getCommissionMoney())>0){
            log.error("佣金提取,提取额度超过剩余余额！");
            throw new BusinessException("佣金提取,提取额度超过剩余余额！");
        }

        FuUser operUser=adminService.selectById(operId);
        if(operUser==null||operUser.getId()==null){
            log.error("佣金提取,根據用户查询操作员为空！");
            throw new BusinessException("佣金提取,根據用户查询操作员为空！");
        }
        //获取配置文件中超级管理员，判断当前登录用户是否为超级管理员,true为超管
        String[] superAdministrators = superAdministrator.split(",");
        boolean contains = false;
        if (operUser != null && superAdministrators != null) {
            contains = Arrays.asList(superAdministrators).contains(operUser.getId().toString());
        }
        if(!contains){
            //该用户无提取佣金权限
            log.error("佣金提取,该用户无提取佣金权限！");
            throw new BusinessException("佣金提取,该用户无提取佣金权限！");
        }

        FuAccountWithdraw withdraw=new FuAccountWithdraw();
        withdraw.setUserId(userId);
        withdraw.setAccountId(commission.getAccountId());
        withdraw.setWithdrawType(CommissionConstant.COMMISSION_WITHDRAW_TYPE_COMMISSION);
        withdraw.setWithdrawAmount(commissionMoney);

        withdraw.setAccountBefore(commission.getCommissionMoney());
        withdraw.setAccountAfter(commission.getCommissionMoney().subtract(commissionMoney));
        withdraw.setWithdrawState(CommissionConstant.COMMISSION_STATE_SUCCESS);
        withdraw.setComment("管理员提手动返佣！");

        commission.setModifyDate(new Date());
        commission.setCommissionPaid(commission.getCommissionPaid().add(commissionMoney));
        commission.setCommissionMoney(commission.getCommissionMoney().subtract(commissionMoney));

        fuAccountWithdrawService.saveSelective(withdraw);
        fuAccountCommissionMapper.updateByPrimaryKeySelective(commission);

        return true;
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

        BigDecimal commissionMoney=new BigDecimal(0);//'佣金余额'
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

        /*更新佣金表数据*/
        fuAccountCommissionMapper.updateByPrimaryKeySelective(accountCommission);

        /*插入佣金流水表*/
        fuAccountCommissionFlowService.insertBatch(flows);
    }
}
