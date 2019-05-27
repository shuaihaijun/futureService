package com.future.trade;
import com.jfx.*;
import com.jfx.strategy.Strategy;

import java.io.IOException;

public class OrderSendExample extends Strategy {

    public OrderSendExample init() throws MT4Exception {
        try {
            connect("127.0.0.1", 7788, DemoAccount.MT_4_SERVER, DemoAccount.MT_4_USER, DemoAccount.MT_4_PASSWORD);
            setReconnect(true);
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

       /* long tickCount= getTickCount();
        ArrayList<Tick> ticks= getTicks();
        for(int i=0;i<ticks.size();i++){
            logger.info("--------ticks----"+i);
            logger.info("---"+ticks.get(i).symbol);
            logger.info("---"+ticks.get(i).time.toString());
            logger.info("---"+ticks.get(i).flags);
            logger.info("---"+ticks.get(i).ask);
            logger.info("---"+ticks.get(i).bid);
            logger.info("---"+ticks.get(i).last);
            logger.info("---"+ticks.get(i).volume);
        }*/
    }



    public static void main(String[] args) throws Exception  {
//        System.setProperty("jfx_activation_key", "235961853");
//        System.setProperty("jfx_activation_key", "2034239897");
        OrderSendExample orderSendExample = new OrderSendExample();
        System.out.println("========connect begin");
        orderSendExample.init();
        System.out.println("========connected orderSend begin");
        orderSendExample.newPosition("EURUSD", "B", 0.01);
        System.out.println("========orderSended well done!");
    }

}
