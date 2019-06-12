package com.future.service.mt;


import com.alibaba.druid.util.StringUtils;
import com.future.entity.order.FuOrderInfo;
import com.future.util.ConvertUtil;
import com.jfx.Broker;
import com.jfx.MarketInfo;
import com.jfx.SelectionPool;
import com.jfx.TradeOperation;
import com.jfx.strategy.OrderInfo;
import com.jfx.strategy.Strategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class MTOrderService {

    Logger log = LoggerFactory.getLogger(MTAccountService.class);
    Strategy strategy;
    @Autowired
    MTAccountService mtAccountService;


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
     * @param username
     * @param password
     * @param dateFrom
     * @param dateTo
     * @return
     */
    public List<FuOrderInfo> getAliveOrders(Broker broker, String username, String password, Date dateFrom,Date dateTo){
        return getOrder(broker,username,password,SelectionPool.MODE_TRADES,dateFrom,dateTo);
    }

    /**
     * 获取历史订单
     * @param broker
     * @param username
     * @param password
     * @param dateFrom
     * @param dateTo
     * @return
     */
    public List<FuOrderInfo> getHistoryOrders(Broker broker, String username, String password, Date dateFrom,Date dateTo){
        return getOrder(broker,username,password,SelectionPool.MODE_HISTORY,dateFrom,dateTo);
    }

    /**
     * 获取订单信息(在仓/历史)
     * @param broker
     * @param username
     * @param password
     * @param selectionPool
     * @param dateFrom
     * @param dateTo
     * @return
     */
    public List<FuOrderInfo> getOrder(Broker broker, String username, String password, SelectionPool selectionPool, Date dateFrom,Date dateTo){

        if(StringUtils.isEmpty(username)||StringUtils.isEmpty(password)
                ||ObjectUtils.isEmpty(broker)
                ||ObjectUtils.isEmpty(selectionPool)){
            log.error("user login error, username:"+username);
        }

        strategy=new Strategy();

        try {
            /*连接数据*/
            if(!mtAccountService.getConnect(strategy,broker,username,password)){
                log.error("user connect failed, username:"+username);
                throw new RuntimeException("user connect failed!");
            }

            Map<Long, OrderInfo> userOrders=strategy.orderGetAll(selectionPool, dateFrom,dateTo);

            return ConvertUtil.convertOrderInfo(userOrders);

        }catch (Exception e){
            log.error(e.getMessage(),e);
            return null;
        }

    }


}
