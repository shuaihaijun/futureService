package com.future.trade;

import com.jfx.*;
import com.jfx.strategy.OrderInfo;
import com.jfx.strategy.Strategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;

public class OrdersExample {

    Logger log = LoggerFactory.getLogger(OrdersExample.class);
    String eurjpy = "EURJAP";
    Strategy s;
    boolean ordersComplete;

    private void run() throws IOException, InterruptedException, MT4Exception {
        s = new Strategy();
        System.out.println("Connecte begin");
//        s.connect("127.0.0.1", 7788, Account.MT_4_SERVER, Account.MT_4_USER, Account.MT_4_PASSWORD, true);
        s.connect("127.0.0.1", 7788, Account.MT_4_SERVER, Account.MT_4_USER, Account.MT_4_PASSWORD);


        System.out.println("Connected.");
        //
        try {
            int tickets[] = new int[3];
            for (int i = 0; i < 3; i++) {
                try {
                    System.out.println("Time: " + System.currentTimeMillis() + "> Sending order");
                    tickets[i] = s.orderSend(eurjpy, TradeOperation.OP_SELL, 0.01, s.marketInfo(eurjpy, MarketInfo.MODE_ASK), 100, 0, 0, "asynch orders example", 0, null, 0);
                    System.out.println("Time: " + System.currentTimeMillis() + "> Order sent: ticket=" + tickets[i]);
                    Thread.sleep(500);
                } catch (InterruptedException ignore) {
                    log.error("InterruptedException :"+i);
                    break;
                } catch (Exception e) {
                    System.out.println("errr:---Time: " + System.currentTimeMillis() + "> Order sending error: " + e);
                }
            }
            for (int i = 0; i < 3; i++) {
                if (tickets[i] > 0) {
                    try {
                        System.out.println("Time: " + System.currentTimeMillis() + "> Closing order");
                        boolean b = s.orderClose(tickets[i], 1, s.marketInfo(eurjpy, MarketInfo.MODE_BID), 100, 0);
                        System.out.println("Time: " + System.currentTimeMillis() + "> Order " + (b ? "closed" : "not closed") + ": ticket=" + tickets[i]);
                        Thread.sleep(200);
                    } catch (InterruptedException ignore) {
                        break;
                    } catch (Exception e) {
                        System.out.println("Time: " + System.currentTimeMillis() + "> Order closing error: " + e);
                    }
                }
            }
        } finally {
            ordersComplete = true;
        }

        try {
            while (!ordersComplete) {
                System.out.println("Time: " + System.currentTimeMillis() + "> BID = " + s.marketInfo(eurjpy, MarketInfo.MODE_BID));
                System.out.println("Time: " + System.currentTimeMillis() + "> OrdersTotal = " + s.ordersTotal());
                Map<Long, OrderInfo> hisOrder= s.orderGetAll(SelectionPool.MODE_HISTORY);
                System.out.println("Time: " + System.currentTimeMillis() + "> HISTORY = " + hisOrder.size());
                Map<Long, OrderInfo> trdes= s.orderGetAll(SelectionPool.MODE_TRADES);
                System.out.println("Time: " + System.currentTimeMillis() + "> TRADES = " + trdes.size());
                try {
                    Thread.sleep(200);
                } catch (InterruptedException ignore) {
                }
            }
            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        System.setProperty("jfx_activation_key", "235961853");
        System.setProperty("jfx_server_port", "17342");
        OrdersExample example = new OrdersExample();
        System.out.println("Press <Enter> to exit.");
        example.run();
        //noinspection ResultOfMethodCallIgnored
        System.in.read();
        example.s.disconnect();
        System.out.println("Account disconnected");
        System.exit(0);
    }

    private static class Account {
        public static final Broker MT_4_SERVER = new Broker("AETOS-Demo F");
        public static final String MT_4_USER = "2102249938";
        public static final String MT_4_PASSWORD = "xl8lbai";
    }
}
