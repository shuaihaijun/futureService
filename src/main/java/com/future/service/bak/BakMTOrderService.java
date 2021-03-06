package com.future.service.bak;


import com.alibaba.druid.util.StringUtils;
import com.future.common.exception.BusinessException;
import com.future.common.exception.DataConflictException;
import com.future.common.util.ConvertUtil;
import com.future.entity.order.FuOrderFollowInfo;
import com.jfx.*;
import com.jfx.strategy.OrderInfo;
import com.jfx.strategy.Strategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Deprecated
@Service
public class BakMTOrderService {

    Logger log = LoggerFactory.getLogger(BakMTAccountService.class);
    @Autowired
    BakMTAccountService bakMtAccountService;
    @Value("${userTermServerHost}")
    public String userTermServerHost;
    @Value("${userTermServerPort}")
    public int userTermServerPort;

    Strategy strategy;


    /**
     * 生成订单
     * @param symbol 币种
     * @param operate 交易方式
     * @param lots 数量
     * @return
     */
    public long sendOrder(String symbol,TradeOperation operate, double lots){
        strategy=new Strategy();
        long ticketNo =0;
        try {
            ticketNo = strategy.orderSend(
                    symbol,
                    operate,
                    lots,
                    strategy.marketInfo(symbol, MarketInfo.MODE_ASK),
                    10,
                    0, 0, "comment", 0, null);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            return 0;
        }

        return ticketNo;
    }

    /**
     * 获取在仓订单
     * @param broker
     * @param mtAccId
     * @param password
     * @param dateFrom
     * @param dateTo
     * @param symbol
     * @return
     */
    public List<FuOrderFollowInfo> getAliveOrders(Broker broker, String mtAccId, String password, Date dateFrom, Date dateTo,String symbol){
        return getOrders(broker,mtAccId,password,SelectionPool.MODE_TRADES,dateFrom,dateTo,symbol);
    }

    /**
     * 获取历史订单
     * @param broker
     * @param mtAccId
     * @param password
     * @param dateFrom
     * @param dateTo
     * @param symbol
     * @return
     */
    public List<FuOrderFollowInfo> getHistoryOrders(Broker broker, String mtAccId, String password, Date dateFrom,Date dateTo,String symbol){
        return getOrders(broker,mtAccId,password,SelectionPool.MODE_HISTORY,dateFrom,dateTo,symbol);
    }

    /**
     * 获取订单信息(在仓/历史)
     * @param broker
     * @param mtAccId
     * @param password
     * @param selectionPool
     * @param dateFrom
     * @param dateTo
     * @param symbol
     * @return
     */
    public List<FuOrderFollowInfo> getOrders(Broker broker, String mtAccId, String password, SelectionPool selectionPool, Date dateFrom,Date dateTo,String symbol){

        if(StringUtils.isEmpty(mtAccId)||StringUtils.isEmpty(password)
                ||ObjectUtils.isEmpty(broker)
                ||ObjectUtils.isEmpty(selectionPool)){
            log.error("user login error, mtAccId:"+mtAccId);
        }

        try {
            System.out.println("========="+mtAccId+","+password);
            BakMTStrategy bakMtStrategy =new BakMTStrategy();
            bakMtStrategy.setMtData(broker,mtAccId,password);
            bakMtStrategy.setServerData(userTermServerHost,userTermServerPort);
            /*连接服务器*/
            bakMtStrategy.init();
            /*连接数据*//*
            if(!mtStrategy.init(userTermServerHost,userTermServerPort,account)){
                log.error("user connect failed, username:"+username);
                throw new RuntimeException("user connect failed!");
            }*/
            Map<Long, OrderInfo> userOrders= bakMtStrategy.orderGetAll(selectionPool, dateFrom,dateTo,symbol);
            return ConvertUtil.convertOrderInfo(userOrders);

        }catch (Exception e){
            log.error(e.getMessage(),e);
            throw new BusinessException(e);
        }

    }

    /**
     * 获取订单信息(在仓/历史)
     * @param selectionPool
     * @param dateFrom
     * @param dateTo
     * @param symbol
     * @return
     */
    public List<FuOrderFollowInfo> getOrders(BakMTStrategy bakMtStrategy, SelectionPool selectionPool, Date dateFrom, Date dateTo, String symbol){

        if(ObjectUtils.isEmpty(selectionPool)){
            selectionPool= SelectionPool.MODE_HISTORY;
        }

        try {
            if(!bakMtStrategy.checkData()){
                log.error("获取MT账户信息 参数信息不全！");
                throw new BusinessException("获取MT账户信息 参数信息不全！");
            }
            /*连接服务器*/
            bakMtStrategy.init();
            Map<Long, OrderInfo> userOrders= bakMtStrategy.orderGetAll(selectionPool, dateFrom,dateTo,symbol);
            return ConvertUtil.convertOrderInfo(userOrders);

        }catch (Exception e){
            log.error(e.getMessage(),e);
            throw new BusinessException(e);
        }
    }

    /**
     * 获取MT账户信息
     * @return
     */
    public AccountInfo getAccountInfo(BakMTStrategy bakMtStrategy){

        try {
            if(!bakMtStrategy.checkData()){
                log.error("获取MT账户信息 参数信息不全！");
                throw new BusinessException("获取MT账户信息 参数信息不全！");
            }
            /*连接服务器*/
            bakMtStrategy.init();
            /*连接数据*//*
            if(!mtStrategy.init(userTermServerHost,userTermServerPort,account)){
                log.error("user connect failed, username:"+username);
                throw new RuntimeException("user connect failed!");
            }*/
            return bakMtStrategy.accountInfo();

        }catch (Exception e){
            log.error(e.getMessage(),e);
            throw new BusinessException(e);
        }
    }

    /**
     * 获取订单信息(在仓/历史)
     * @param selectionPool
     * @param dateFrom
     * @param dateTo
     * @param symbol
     * @return
     */
    public Map getOrderAndAccountInfo(BakMTStrategy bakMtStrategy, SelectionPool selectionPool, Date dateFrom, Date dateTo, String symbol){

        if(ObjectUtils.isEmpty(selectionPool)){
            selectionPool= SelectionPool.MODE_HISTORY;
        }

        try {
            if(!bakMtStrategy.checkData()){
                log.error("获取MT账户信息 参数信息不全！");
                throw new BusinessException("获取MT账户信息 参数信息不全！");
            }
            /*连接服务器*/
            bakMtStrategy.init();
            Map<Long, OrderInfo> userOrders= bakMtStrategy.orderGetAll(selectionPool, dateFrom,dateTo,symbol);
            AccountInfo accountInfo= bakMtStrategy.accountInfo();
            List<FuOrderFollowInfo> orderFollowInfos= ConvertUtil.convertOrderInfo(userOrders);
            Map orderAndAccount = new HashMap();
            orderAndAccount.put("orders",orderFollowInfos);
            orderAndAccount.put("accountInfo",accountInfo);
            return orderAndAccount;

        }catch (Exception e){
            log.error(e.getMessage(),e);
            throw new BusinessException(e);
        }
    }

    /**
     * 根据订单序号查询历史订单信息
     * @param broker
     * @param username
     * @param password
     * @param index
     * @return
     */
    public FuOrderFollowInfo getHistoryOrderByIndex(Broker broker, String username, String password,long index){
        return getOrderByIndex(broker,username,password,index,SelectionType.SELECT_BY_TICKET,SelectionPool.MODE_HISTORY);
    }


    /**
     * 根据订单序号查询在仓订单信息
     * @param broker
     * @param username
     * @param password
     * @param index
     * @return
     */
    public FuOrderFollowInfo getAliveOrderByIndex(Broker broker, String username, String password,long index){
        return getOrderByIndex(broker,username,password,index,SelectionType.SELECT_BY_TICKET,SelectionPool.MODE_TRADES);
    }

    /**
     * 根据订单序号查询订单信息
     * @param broker
     * @param username
     * @param password
     * @param index
     * @param type
     * @param pool
     * @return
     */
    public FuOrderFollowInfo getOrderByIndex(Broker broker, String username, String password, long index,SelectionType type,SelectionPool pool){
        if(index==0||ObjectUtils.isEmpty(type)||ObjectUtils.isEmpty(pool)){
            throw new DataConflictException("根据订单序号查询订单信息,传入数据为空！");
        }
        strategy=new Strategy();

        try {
            /*连接数据*/
            if(!bakMtAccountService.getConnect(strategy,broker,username,password)){
                log.error("user connect failed, username:"+username);
                throw new RuntimeException("user connect failed!");
            }
            OrderInfo orderInfo=strategy.orderGet(index, type,pool);
            return ConvertUtil.convertOrderInfo(orderInfo);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            throw new BusinessException(e);
        }
    }



}
