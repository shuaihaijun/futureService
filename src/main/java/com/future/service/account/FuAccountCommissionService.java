package com.future.service.account;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.future.common.constants.CommonConstant;
import com.future.common.exception.BusinessException;
import com.future.common.util.DateUtil;
import com.future.entity.account.FuAccountCommission;
import com.future.entity.account.FuAccountCommissionFlow;
import com.future.entity.commission.FuCommissionCustomer;
import com.future.mapper.account.FuAccountCommissionMapper;
import com.future.service.commission.FuCommissionCustomerService;
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
            acc.setAccountState(CommonConstant.COMMON_STATE_INVALID);
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
        }

        /*查询佣金账户*/
        FuAccountCommission acc=new FuAccountCommission();
        Map conditionMap=new HashMap();
        conditionMap.put(FuAccountCommission.USER_ID,userId);
        List<FuAccountCommission> accountInfos=fuAccountCommissionMapper.selectByMap(conditionMap);
        if(accountInfos==null || accountInfos.size()==0){
            log.error("计算佣金日结,佣金账户查询为空！");
            throw new BusinessException("计算佣金日结,佣金账户查询为空！");
        }

        BigDecimal commissionMoney=new BigDecimal(0);//'佣金余额'
        BigDecimal commissionSourceMoney=new BigDecimal(0);//''源发生金额''
        BigDecimal commissionSourceLots=new BigDecimal(0);//''源发生手数''

        /*佣金日结日志*/
        for(FuAccountCommissionFlow flow:flows){
            flow.setUserId(userId);
            flow.setAccountId(accountId);
            flow.setCommissionDate(new Date());
            commissionMoney=commissionMoney.add(flow.getCommissionMoney());
            commissionSourceMoney=commissionSourceMoney.add(flow.getSourceMoney());
            commissionSourceLots=commissionSourceLots.add(flow.getSourceLots());
        }

        FuAccountCommission accountCommission=accountInfos.get(0);
        accountCommission.setCommissionMoney(accountCommission.getCommissionMoney().add(commissionMoney));//'佣金余额'
        accountCommission.setCommissionTotal(accountCommission.getCommissionTotal().add(commissionMoney));//''总佣金额''
        accountCommission.setCommissionSourceMoney(accountCommission.getCommissionSourceMoney());//''源发生金额''
        accountCommission.setCommissionSourceLots(accountCommission.getCommissionSourceLots().add(commissionSourceLots));//''源发生手数''

        /*更新佣金表数据*/
        fuAccountCommissionMapper.updateByPrimaryKeySelective(accountCommission);

        /*插入佣金流水表*/
        fuAccountCommissionFlowService.insertBatch(flows);
    }
}
