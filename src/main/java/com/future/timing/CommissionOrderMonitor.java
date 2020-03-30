package com.future.timing;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.future.common.constants.AccountConstant;
import com.future.common.constants.AgentConstant;
import com.future.common.constants.CommonConstant;
import com.future.common.constants.UserConstant;
import com.future.common.helper.PageInfoHelper;
import com.future.entity.account.FuAccountCommission;
import com.future.entity.account.FuAccountCommissionFlow;
import com.future.entity.com.FuComAgent;
import com.future.entity.user.FuUser;
import com.future.pojo.bo.account.UserAccountInfoBO;
import com.future.service.account.FuAccountCommissionFlowService;
import com.future.service.account.FuAccountCommissionService;
import com.future.service.account.FuAccountInfoService;
import com.future.service.account.FuAccountMtService;
import com.future.service.com.FuComAgentService;
import com.future.service.order.FuOrderCustomerService;
import com.future.service.user.AdminService;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.*;

/**
 * @author ：haijun
 * @date ：Created in 2019-10-17 15:17
 * @description：用户自交易订单定时同步
 * @modified By：
 * @version: 1/0$
 */
@Configuration
@EnableScheduling
public class CommissionOrderMonitor {

    Logger log= LoggerFactory.getLogger(CommissionOrderMonitor.class);
    @Autowired
    FuOrderCustomerService fuOrderCustomerService;
    @Autowired
    FuAccountMtService fuAccountMtService;
    @Autowired
    FuAccountCommissionService fuAccountCommissionService;
    @Autowired
    FuAccountCommissionFlowService fuAccountCommissionFlowService;
    @Autowired
    FuComAgentService fuComAgentService;
    @Autowired
    FuAccountInfoService fuAccountInfoService;
    @Autowired
    AdminService adminService;

    /**
     * 监听信号源 （每天晚上1点开始触发同步）
     */
    @Scheduled(cron = "0 0 2 * * ?")
    public void monitor(){

        log.info("同步客户自交易订单开始---------------------"+new Date().getTime());

        Date daySumBegin=new Date();
        List<FuAccountCommissionFlow> flows=new ArrayList<>();
        FuAccountCommission accountCommission=new FuAccountCommission();
        PageInfoHelper helper = new PageInfoHelper();

        /*1、同步用户自定义订单 计算佣金流水（以用户为单位）*/
        Wrapper<FuUser> userWapper=new EntityWrapper<>();
        userWapper.eq(FuUser.IS_ACCOUNT,CommonConstant.COMMON_YES);
        userWapper.eq(FuUser.IS_VERIFIED,CommonConstant.COMMON_YES);
        PageHelper.startPage(helper.getPageNo(), helper.getPageSize());
        List<FuUser> userList = adminService.selectList(userWapper);
        while (userList.size()>0){
            for(FuUser user: userList){
                try {
                    /*同步用户历史订单*/
                    fuOrderCustomerService.synUserMTOrder(user.getId(),user.getUsername());
                }catch (Exception e){
                    log.error(e.getMessage(),e);
                }
                try {
                    /*同步用户账户信息*/
                    fuAccountMtService.updateAccountInfoFromMt(user.getId());
                }catch (Exception e){
                    log.error(e.getMessage(),e);
                }
            }
            /*翻页*/
            helper.setPageNo(helper.getPageNo()+1);
            PageHelper.startPage(helper.getPageNo(), helper.getPageSize());
            userList = adminService.selectList(userWapper);
        }
        log.info("同步客户自交易订单结束---------------------"+new Date().getTime());


        /*2、计算佣金 （以用户为单位）*/
        log.info("计算佣金开始---------------------"+new Date().getTime());
        Map conditionMap=new HashMap();
        conditionMap.put("isAccount", CommonConstant.COMMON_YES);
        conditionMap.put("isChief",CommonConstant.COMMON_YES);
        conditionMap.put("accountState",AccountConstant.ACCOUNT_STATE_NORMAL);
        Date commissionBegin=new Date();
        helper.setPageNo(1);
        PageHelper.startPage(helper.getPageNo(), helper.getPageSize());
        List<UserAccountInfoBO> accountInfos= fuAccountInfoService.selectUserAccountByCondition(conditionMap);
        while (accountInfos.size()>0){
            for(UserAccountInfoBO accountInfo: accountInfos){
                try {
                    if(accountInfo.getUserType()!=UserConstant.USER_TYPE_SIGNAL
                        &&accountInfo.getUserType()!=UserConstant.USER_TYPE_IB
                            &&accountInfo.getUserType()!=UserConstant.USER_TYPE_MIB
                                &&accountInfo.getUserType()!=UserConstant.USER_TYPE_PIB){
                        continue;
                    }
                    /*根据佣金日志 查出上次结算时间*/
                    flows= fuAccountCommissionFlowService.findLastSumFlowByUserId(accountInfo.getUserId(),accountInfo.getAccountId(),1);
                    if(flows!=null&& flows.size()>0){
                        commissionBegin=flows.get(0).getCommissionDate();
                    }else {
                        /*初次返佣  以建立佣金账户时间为参考*/
                        accountCommission=fuAccountCommissionService.getAccountCommissonByUserId(accountInfo.getUserId());
                        commissionBegin=accountCommission.getCreateDate();
                    }

                    /*计算佣金*/
                    fuAccountCommissionService.dealAccountCommissionDaySum(accountInfo.getUserId(),accountInfo.getAccountId(),commissionBegin,null);

                    /*判断代理是否可升级(介绍人可升级到初级代理)*/
                    if(accountInfo.getUserType()< UserConstant.USER_TYPE_PIB){
                        /*升级逻辑*/
                        fuComAgentService.agentUpgrade(accountInfo.getUserId());
                    }
                }catch (Exception e){
                    log.error(e.getMessage(),e);
                    //TODO error log
                }
            }
            /*翻页*/
            helper.setPageNo(helper.getPageNo()+1);
            PageHelper.startPage(helper.getPageNo(), helper.getPageSize());
            accountInfos= fuAccountInfoService.selectUserAccountByCondition(conditionMap);
        }
        log.info("计算佣金结束---------------------"+new Date().getTime());

        /*计算社区同级返佣 （以代理为单位）*/
        log.info("计算社区同级返佣开始---------------------"+new Date().getTime());
        helper.setPageNo(1);
        PageHelper.startPage(helper.getPageNo(), helper.getPageSize());
        conditionMap.clear();
        conditionMap.put(FuComAgent.AGENT_STATE, AgentConstant.AGENT_STATE_NORMAL);
        List<FuComAgent> agents = fuComAgentService.selectByMap(conditionMap);
        while (agents.size()>0){
            for(FuComAgent agent: agents){
                try {
                    // 同级反佣0.1 越级置空
                    fuComAgentService.dealAgentCommissionFromCommunity(agent,daySumBegin,null);
                }catch (Exception e){
                    log.error(e.getMessage(),e);
                    //TODO error log
                }
            }
            /*翻页*/
            helper.setPageNo(helper.getPageNo()+1);
            PageHelper.startPage(helper.getPageNo(), helper.getPageSize());
            agents = fuComAgentService.selectByMap(conditionMap);
        }
        log.info("计算社区同级返佣结束---------------------"+new Date().getTime());
    }
}
