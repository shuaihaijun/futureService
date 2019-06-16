package com.future.service.order;

import com.alibaba.druid.util.StringUtils;
import com.future.constant.OrderConstant;
import com.future.entity.order.FuOrderCustomer;
import com.future.entity.order.FuOrderInfo;
import com.future.entity.user.FuUser;
import com.future.service.account.AccoutService;
import com.future.service.mt.MTOrderService;
import com.future.service.user.AdminService;
import com.future.util.ConvertUtil;
import com.jfx.Broker;
import com.jfx.TradeOperation;
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
    public List<FuOrderInfo> geMTtHistoryOrders(String username, String accountId,Date dataFrom,Date dateTo){
        return getMTOrders(true,username,accountId,dataFrom,dateTo);
    }


    /**
     * 获取用户在仓订单信息
     * @param username
     * @param accountId
     * @param dataFrom
     * @param dateTo
     * @return
     */
    public List<FuOrderInfo> getMTAliveOrders(String username, String accountId,Date dataFrom,Date dateTo){
        return getMTOrders(false,username,accountId,dataFrom,dateTo);
    }

    public List<FuOrderInfo> getMTOrders(Boolean isHistoryOrder, String username, String accountId,Date dataFrom,Date dateTo){
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

        if(isHistoryOrder){
            return mtOrderService.getHistoryOrders(broker,username,password,dataFrom,dateTo);
        }else {
            return mtOrderService.getAliveOrders(broker,username,password,dataFrom,dateTo);
        }
    }


    /**
     * 同步用户历史订单至社区
     * @param username
     */
    public void synUserMTOrder(String username){

        /*查询用户账户信息*/
        List<Map> accounts=accoutService.getUserMTAccount(username);
        /*获取用户最后自主交易订单（社区内）*/
        FuOrderCustomer lastOrder=getLastCustomerOrder(username);
        /*根据闭单时间处理*/
        Date lastCLostTime=lastOrder.getCreateDate();

        List<FuOrderInfo> fuOrderInfos=new ArrayList<>();
        FuOrderInfo info;
        FuOrderCustomer customerInfo;
        Broker broker;
        String server;
        String password;
        String mtAccountId;
        int userId;
        for(Map account:accounts){
            fuOrderInfos.clear();
            server=String.valueOf(account.get("serverName"));
            password=String.valueOf(account.get("password"));
            userId=Integer.valueOf(String.valueOf(account.get("userId")));
            mtAccountId=String.valueOf(account.get("mtAccountId"));

            if(StringUtils.isEmpty(server)){
                log.error("根据时间段 查询用户历史订单，用户MT4账户信息有误！");
                continue;
            }
            broker=new Broker(server);

            /*只同步已完成的订单*/
            fuOrderInfos=mtOrderService.getHistoryOrders(broker,username,password,lastCLostTime,new Date());

            if(!ObjectUtils.isEmpty(fuOrderInfos)){
                for(FuOrderInfo fuOrderInfo:fuOrderInfos){
                    /*与社区订单查重，已有订单无需操作*/
                    info=getOrderById(fuOrderInfo.getOrderId());
                    if(info!=null){
                        /*数据已存在*/
                        continue;
                    }
                    /*转换订单*/
                    customerInfo= ConvertUtil.convertOrderCustomer(fuOrderInfo);
                    customerInfo.setUserId(userId);
                    customerInfo.setMtId(mtAccountId);
                    customerInfo.setOrderState(OrderConstant.ORDER_STATE_CLOSE);

                    /*保存数据*/
                    saveMTCustomerOrder(customerInfo);
                }
            }
        }
    }


    /**
     * 批量保存用户自主交易订单
     * @param fuOrderInfos
     */
    public void saveMTCustomerOrders(List<FuOrderInfo> fuOrderInfos){

        if(ObjectUtils.isEmpty(fuOrderInfos)){
            log.error("保存用户自主交易订单,数据为空！ 无需保存！");
            return;
        }
        /*批量保存*/
    }

    /**
     * 保存用户自主交易订单
     * @param fuOrderCustomer
     */
    public void saveMTCustomerOrder(FuOrderCustomer fuOrderCustomer){

        if(ObjectUtils.isEmpty(fuOrderCustomer)){
            log.error("保存用户自主交易订单,数据为空！ 无需保存！");
            return;
        }

        /*保存*/

    }

    /**
     * 查询 用户社区内最后的自如交易订单
     * @param username
     * @return
     */
    public FuOrderCustomer getLastCustomerOrder(String username){
        FuOrderCustomer lastOrder=new FuOrderCustomer();
        /*从数据库表 fuOrderCustomer中，查出最新自主交易订单*/
        return lastOrder;
    }

    /**
     * 根据orderId查询订单信息
     * @param orderId
     * @return
     */
    public FuOrderInfo getOrderById(String orderId){

        FuOrderInfo fuOrderInfo=new FuOrderInfo();
        /*redis 或 数据库*/
        return fuOrderInfo;
    }



    /**
     * 生成订单
     * @param symbol 币种
     * @param operate 交易方式
     * @param lots 数量
     * @return
     */
    public long sendOrder(String symbol, TradeOperation operate, double lots){
        /*返回订单号orderId*/
        /*有没有必要 保存该订单至数据库？*/
        return mtOrderService.sendOrder(symbol,operate,lots);
    }
}
