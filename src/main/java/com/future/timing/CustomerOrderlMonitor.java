package com.future.timing;

import com.future.common.constants.AgentConstant;
import com.future.common.constants.CommonConstant;
import com.future.common.constants.UserConstant;
import com.future.common.helper.PageInfoHelper;
import com.future.entity.account.FuAccountCommissionFlow;
import com.future.entity.com.FuComAgent;
import com.future.pojo.bo.order.UserMTAccountBO;
import com.future.service.account.FuAccountCommissionFlowService;
import com.future.service.account.FuAccountCommissionService;
import com.future.service.account.FuAccountMtService;
import com.future.service.com.FuComAgentService;
import com.future.service.order.FuOrderCustomerService;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.*;

/**
 * @author ：haijun
 * @date ：Created in 2019-10-17 15:17
 * @description：用户自交易订单定时同步
 * @modified By：
 * @version: 1/0$
 */
public class CustomerOrderlMonitor {

    Logger log= LoggerFactory.getLogger(CustomerOrderlMonitor.class);
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

    /**
     * 监听信号源 （每天晚上1点开始触发同步）
     */
    @Scheduled(cron = "0 0 1 * * ?")
    public void monitor(){

        Map conditionMap=new HashMap();
        /*已验证的 合作平台的 MT账户*/
        conditionMap.put("isAccount", CommonConstant.COMMON_YES);
        conditionMap.put("isChief",CommonConstant.COMMON_YES);
        Date beginTime=new Date();
        List<FuAccountCommissionFlow> flows=new ArrayList<>();
        PageInfoHelper helper = new PageInfoHelper();

        /*1、同步用户自定义订单 计算佣金流水*/
        PageHelper.startPage(helper.getPageNo(), helper.getPageSize());
        List<UserMTAccountBO> list = fuAccountMtService.getUserMTAccByCondition(conditionMap);
        while (list.size()>0){
            for(UserMTAccountBO userMTAccountBO: list){
                fuOrderCustomerService.synUserMTOrder(userMTAccountBO.getUserId(),userMTAccountBO.getUsername());
            }
            /*翻页*/
            helper.setPageNo(helper.getPageNo()+1);
            PageHelper.startPage(helper.getPageNo(), helper.getPageSize());
            list = fuAccountMtService.getUserMTAccByCondition(conditionMap);
        }


        /*2、计算佣金*/
        helper.setPageNo(1);
        PageHelper.startPage(helper.getPageNo(), helper.getPageSize());
        list = fuAccountMtService.getUserMTAccByCondition(conditionMap);
        while (list.size()>0){
            for(UserMTAccountBO userMTAccountBO: list){
                /*根据佣金日志 查出上次结算时间*/
                flows= fuAccountCommissionFlowService.findLastSumFlowByUserId(userMTAccountBO.getUserId(),userMTAccountBO.getAccountId(),1);
                if(flows!=null&& flows.size()>0){
                    beginTime=flows.get(0).getCommissionDate();
                }

                /*计算佣金*/
                fuAccountCommissionService.dealAccountCommissionDaySum(userMTAccountBO.getUserId(),userMTAccountBO.getAccountId(),beginTime,null);

                /*判断代理是否可升级(介绍人可升级到初级代理)*/
                if(userMTAccountBO.getUserType()> UserConstant.USER_TYPE_PIB){
                    /*升级逻辑*/
                    fuComAgentService.agentUpgrade(userMTAccountBO);
                }

            }
            /*翻页*/
            helper.setPageNo(helper.getPageNo()+1);
            PageHelper.startPage(helper.getPageNo(), helper.getPageSize());
            list = fuAccountMtService.getUserMTAccByCondition(conditionMap);
        }

        /*计算社区同级返佣*/
        helper.setPageNo(1);
        PageHelper.startPage(helper.getPageNo(), helper.getPageSize());
        conditionMap.clear();
        conditionMap.put(FuComAgent.AGENT_STATE, AgentConstant.AGENT_STATE_NORMAL);
        List<FuComAgent> agents = fuComAgentService.selectByMap(conditionMap);
        while (agents.size()>0){
            for(FuComAgent agent: agents){
                // 同级反佣0.1 越级置空
                fuComAgentService.dealAgentCommissionFromCommunity(agent,beginTime,null);
            }
            /*翻页*/
            helper.setPageNo(helper.getPageNo()+1);
            PageHelper.startPage(helper.getPageNo(), helper.getPageSize());
            agents = fuComAgentService.selectByMap(conditionMap);
        }

    }
}
