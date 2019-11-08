package com.future.service.mt;

import com.jfx.Broker;
import com.jfx.MT4Exception;
import com.jfx.MarketInfo;
import com.jfx.TradeOperation;
import com.jfx.strategy.Strategy;

import java.io.IOException;

public class MTStrategy extends Strategy {

    public Broker MT_4_SERVER = new Broker("AETOS-Demo F");
    public String MT_4_USER = "2102261700";
    public String MT_4_PASSWORD = "jxg3vvd";
    public String HOST_URL="127.0.0.1";//"127.0.0.1"
    public int HOST_PORT=7788;//"127.0.0.1"

    public void setMtData(Broker broker,String username,String password){
        MT_4_SERVER =broker;
        MT_4_USER =username;
        MT_4_PASSWORD = password;
    }

    public void setServerData(String hostUrl,int hostPort){
        HOST_URL=hostUrl;
        HOST_PORT=hostPort;
    }

    /**
     * 初始化连接
     * @return
     * @throws MT4Exception
     */
    public MTStrategy init() throws MT4Exception {
        try {
            connect(HOST_URL, HOST_PORT, MT_4_SERVER, MT_4_USER, MT_4_PASSWORD);
            return this;
        } catch (IOException e) {
            throw new MT4Exception(2, "No connection to TS", e);
        }
    }

    /**
     * 生成订单
     * @param symbol 币种
     * @param tradeOperation 交易方式
     * @param lots 数量
     * @return
     */
    public void newPosition(String symbol, TradeOperation tradeOperation, double lots) throws MT4Exception {
            //noinspection unused
            long ticketNo = orderSend(
                    symbol,
                    tradeOperation,
                    lots,
                    marketInfo(symbol, MarketInfo.MODE_ASK),
                    10,
                    0, 0, "comment", 0, null
            );
    }

}