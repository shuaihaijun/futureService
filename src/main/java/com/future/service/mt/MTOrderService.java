package com.future.service.mt;


import com.alibaba.druid.util.StringUtils;
import com.future.common.exception.BusinessException;
import com.future.common.exception.DataConflictException;
import com.future.common.util.ConvertUtil;
import com.future.entity.order.FuOrderInfo;
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
import java.util.List;
import java.util.Map;

@Service
public class MTOrderService {

    Logger log = LoggerFactory.getLogger(MTAccountService.class);
    @Autowired
    MTAccountService mtAccountService;
    @Value("${termServerHost}")
    public String termServerHost;
    @Value("${termServerPort}")
    public int termServerPort;

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
    public List<FuOrderInfo> getAliveOrders(Broker broker, String mtAccId, String password, Date dateFrom, Date dateTo,String symbol){
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
    public List<FuOrderInfo> getHistoryOrders(Broker broker, String mtAccId, String password, Date dateFrom,Date dateTo,String symbol){
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
    public List<FuOrderInfo> getOrders(Broker broker, String mtAccId, String password, SelectionPool selectionPool, Date dateFrom,Date dateTo,String symbol){

        if(StringUtils.isEmpty(mtAccId)||StringUtils.isEmpty(password)
                ||ObjectUtils.isEmpty(broker)
                ||ObjectUtils.isEmpty(selectionPool)){
            log.error("user login error, mtAccId:"+mtAccId);
        }

        try {
            System.out.println("========="+mtAccId+","+password);
            MTStrategy mtStrategy=new MTStrategy();
            mtStrategy.setMtData(broker,mtAccId,password);
            mtStrategy.setServerData(termServerHost,termServerPort);
            /*连接服务器*/
            mtStrategy.init();
            /*连接数据*//*
            if(!mtStrategy.init(termServerHost,termServerPort,account)){
                log.error("user connect failed, username:"+username);
                throw new RuntimeException("user connect failed!");
            }*/
            Map<Long, OrderInfo> userOrders=mtStrategy.orderGetAll(selectionPool, dateFrom,dateTo,symbol);
            /*释放连接*/
            mtStrategy.disconnect();
            return ConvertUtil.convertOrderInfo(userOrders);

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
    public FuOrderInfo getHistoryOrderByIndex(Broker broker, String username, String password,long index){
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
    public FuOrderInfo getAliveOrderByIndex(Broker broker, String username, String password,long index){
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
    public FuOrderInfo getOrderByIndex(Broker broker, String username, String password, long index,SelectionType type,SelectionPool pool){
        if(index==0||ObjectUtils.isEmpty(type)||ObjectUtils.isEmpty(pool)){
            throw new DataConflictException("根据订单序号查询订单信息,传入数据为空！");
        }
        strategy=new Strategy();

        try {
            /*连接数据*/
            if(!mtAccountService.getConnect(strategy,broker,username,password)){
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
