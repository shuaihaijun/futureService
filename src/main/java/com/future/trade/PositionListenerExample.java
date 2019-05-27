package com.future.trade;
import com.jfx.*;
import com.jfx.net.JFXServer;
import com.jfx.strategy.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ExecutorService;

public class PositionListenerExample {

    Logger log= LoggerFactory.getLogger(PositionListenerExample.class);
    String eurjpy = "USDCAD";
    Strategy s;
    boolean ordersComplete;
    ExecutorService executorService;

    private void getOrderRun() throws IOException, InterruptedException, MT4Exception {
        s = new Strategy();
        s.setPositionListener(new PositionListener() {
            @Override
            public void onInit(PositionInfo initialPositionInfo) {
                System.out.println("Time: " + System.currentTimeMillis() + "> PLSNR: init: " + initialPositionInfo.liveOrders());
                System.out.println("Time: " + System.currentTimeMillis() + "> PLSNR: init: " + initialPositionInfo.historicalOrders());
                ThreadCache.getResult().put("historyOrder",initialPositionInfo.historicalOrders());
            }

            @Override
            public void onChange(PositionInfo currentPositionInfo, PositionChangeInfo changes) {
                for (OrderInfo o : changes.getNewOrders()) {
                    System.out.println("Time: " + System.currentTimeMillis() + "> NEW:      " + o);
                }
                for (OrderInfo o : changes.getModifiedOrders()) {
                    System.out.println("Time: " + System.currentTimeMillis() + "> MODIFIED: " + o);
                }
                for (OrderInfo o : changes.getDeletedOrders()) {
                    System.out.println("Time: " + System.currentTimeMillis() + "> DELETED:  " + o);
                }
                for (OrderInfo o : changes.getClosedOrders()) {
                    System.out.println("Time: " + System.currentTimeMillis() + "> CLOSED:   " + o);
                }
            }
        }, 10, 1000).addTickListener(eurjpy, new Strategy.TickListener() {
            @Override
            public void onTick(TickInfo tick, MT4 connection) {
                System.out.println("(1)" + tick);
            }
        });
        s.connect("127.0.0.1", 7788, DemoAccount.MT_4_SERVER, DemoAccount.MT_4_USER, DemoAccount.MT_4_PASSWORD);
        System.out.println("Connected.");

        if (s.orderSelect(0, SelectionType.SELECT_BY_POS, SelectionPool.MODE_TRADES)) {
            s.orderTicket();
        }
        //
        OrderInfo orderInfo = s.orderGet(0, SelectionType.SELECT_BY_POS, SelectionPool.MODE_TRADES);
        if (orderInfo != null) {
            int ticket = orderInfo.getTicket();
            Date openTime = orderInfo.getOpenTime();
            double profit = orderInfo.getProfit();
            // ...
        }
        //
    }

    public void getOrder(){
        Map<Long, OrderInfo> hisOrder=(Map<Long, OrderInfo>)ThreadCache.getResult().get("historyOrder");
        for(OrderInfo a:hisOrder.values()){
            log.info("||||||||||||"+a);
            log.info("||||"+a.getComment());
            log.info("||||"+a.getSymbol());
            log.info("||||"+a.getOpenPrice());
            log.info("||||"+a.getOpenTime());
            log.info("||||"+a.getClosePrice());
            log.info("||||"+a.getClosePrice());
        }
    }


    public static void main(String[] args) throws Exception {
        System.setProperty("jfx_activation_key", System.getProperty("jfx_activation_key", "235961853"));
//        System.setProperty("jfx_server_port", "17342");
        //
        PositionListenerExample example = new PositionListenerExample();
        System.out.println("Press <Enter> to exit.");
        example.getOrderRun();
        example.getOrder();

        //noinspection ResultOfMethodCallIgnored
        System.in.read();
        example.s.disconnect();
        System.out.println("Account disconnected");
        example.executorService.shutdown();
        JFXServer.stop();
    }

}
