package com.future.service.account;

import com.alibaba.druid.util.StringUtils;
import com.future.entity.order.FuOrderCustomer;
import com.future.entity.order.FuOrderInfo;
import com.future.entity.user.FuUser;
import com.future.service.mt.MTOrderService;
import com.future.service.user.AdminService;
import com.jfx.Broker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;

import java.util.*;

public class OrderService {

    Logger log = LoggerFactory.getLogger(OrderService.class);

    @Autowired
    AdminService adminService;
    @Autowired
    MTOrderService mtOrderService;
    @Autowired
    AccoutService accoutService;

    /**
     * 根据时间段 查询用户历史订单
     * @param username
     * @param accountId
     * @param dataFrom
     * @param dateTo
     * @return
     */
    public List<FuOrderInfo> getHistoryOrders(String username, String accountId,Date dataFrom,Date dateTo){
        if(StringUtils.isEmpty(username)){
            log.error("根据时间段 查询用户历史订单，用户名为空！");
            return null;
        }
        /*根据用户名 查询用户信息*/
        FuUser fuUser=adminService.findByUsername(username);
        if(ObjectUtils.isEmpty(fuUser)){
            log.error("根据时间段 查询用户历史订单，用户信息不存在！");
            return null;
        }

        /*验证用户登录状态*/


        /*查询用户历史订单*/
        Map acountMap=new HashMap();
        if(!StringUtils.isEmpty(accountId)){
            acountMap=accoutService.getUserMtAccountById(accountId);
        }else {
            /*默认查询主账户号*/
            acountMap=accoutService.getUserMTAccountByUsername(username,1);
        }
        if(ObjectUtils.isEmpty(acountMap)){
            log.error("根据时间段 查询用户历史订单，用户MT4账户未绑定！");
            return null;
        }
        String server=String.valueOf(acountMap.get("serverName"));
        String password=String.valueOf(acountMap.get("password"));
        if(StringUtils.isEmpty(server)){
            log.error("根据时间段 查询用户历史订单，用户MT4账户信息有误！");
            return null;
        }
        Broker broker=new Broker(server);

        return mtOrderService.getHistoryOrders(broker,username,password,dataFrom,dateTo);
    }


    /**
     * 获取用户在仓订单信息
     * @param username
     * @param accountId
     * @param dataFrom
     * @param dateTo
     * @return
     */
    public List<FuOrderInfo> getAliveOrders(String username, String accountId,Date dataFrom,Date dateTo){

        return null;
    }

    /**
     * 同步用户历史订单至社区
     * @param username
     */
    public void synUserMTOrder(String username){

        List<Map> accounts=accoutService.getUserMTAccount(username);
        FuOrderCustomer lastOrder=getLastCustomerOrder(username);
        /*根据闭单时间处理*/
        Date lastCLostTime=lastOrder.getCreateDate();

        List<FuOrderInfo> fuOrderInfos=new ArrayList<>();
        Broker broker;
        String server;
        String password;
        for(Map account:accounts){
            fuOrderInfos.clear();
            server=String.valueOf(account.get("serverName"));
            password=String.valueOf(account.get("password"));
            if(StringUtils.isEmpty(server)){
                log.error("根据时间段 查询用户历史订单，用户MT4账户信息有误！");
                continue;
            }
            broker=new Broker(server);

            /*只同步已完成的订单*/
            fuOrderInfos=mtOrderService.getHistoryOrders(broker,username,password,lastCLostTime,new Date());

            if(!ObjectUtils.isEmpty(fuOrderInfos)){
                saveMTCustomerOrder(fuOrderInfos);
            }
        }
    }


    /**
     * 保存用户自主交易订单
     * @param fuOrderInfos
     */
    public void saveMTCustomerOrder(List<FuOrderInfo> fuOrderInfos){

    }

    /**
     * 查询 用户社区内最后的自如交易订单
     * @param username
     * @return
     */
    public FuOrderCustomer getLastCustomerOrder(String username){
        FuOrderCustomer lastOrder=new FuOrderCustomer();
        return lastOrder;
    }


}
