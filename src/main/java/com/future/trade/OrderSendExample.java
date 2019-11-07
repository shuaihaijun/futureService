package com.future.trade;
import com.jfx.*;
import com.jfx.strategy.Strategy;

import java.io.IOException;

public class OrderSendExample extends Strategy {

    public Broker MT_4_SERVER = new Broker("MultiBankFXInt-Demo F");
    public String MT_4_USER = "2102272054";
    public String MT_4_PASSWORD = "dcb6ose";
    public String HOST_URL="127.0.0.1";//"127.0.0.1"
//    public String HOST_URL="192.168.199.101";//"127.0.0.1"

    public void setData(Broker broker,String username,String password){
        MT_4_SERVER =broker;
        MT_4_USER =username;
        MT_4_PASSWORD = password;
    }

    public OrderSendExample init() throws MT4Exception {
        try {
            connect(HOST_URL, 7788, MT_4_SERVER, MT_4_USER, MT_4_PASSWORD);
            System.out.println("Connected to " + accountCompany() + " As " + accountName());
            //
            return this;
        } catch (IOException e) {
            throw new MT4Exception(2, "No connection to TS", e);
        }
    }

    public void newPosition(String symbol, String command, double lots) throws MT4Exception {
        if (command.equals("B"))
        {
            int ticketNo = orderSend(
                    symbol,
                    TradeOperation.OP_BUY,
                    lots,
                    marketInfo(symbol, MarketInfo.MODE_ASK),
                    10,
                    0, 0, "comment", 0, null, 0
            );
        }
    }

    public void disconnetct() throws MT4Exception {
        try {
            disconnect();
        }catch (IOException e){
            throw new MT4Exception(2, "No connection to TS", e);
        }
    }



    public static void main(String[] args) throws Exception  {
        OrderSendExample orderSendExample = new OrderSendExample();
        System.out.println("========connect begin");
        orderSendExample.init();
        System.out.println("========connected orderSend begin");
        orderSendExample.newPosition("EURUSD", "B", 0.01);
        System.out.println("========orderSended well done!");
//        orderSendExample.disconnect();
    }

}
