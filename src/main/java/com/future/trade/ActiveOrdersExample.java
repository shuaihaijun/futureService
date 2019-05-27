package com.future.trade;
import com.jfx.*;
import com.jfx.strategy.Strategy;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ActiveOrdersExample extends Strategy {

    private boolean isContinue;

    public void x() throws MT4Exception {
        try {
            connect("127.0.0.1", 7788, DemoAccount.MT_4_SERVER, DemoAccount.MT_4_USER, DemoAccount.MT_4_PASSWORD);
            setReconnect(true);
            System.out.println("Connected to " + accountCompany() + " As " + accountName());
            //
            for (String s : getSymbols()) {
                if (s.equals("EURUSD")) {
                    double usdeur = iOpen("EURUSD", Timeframe.PERIOD_M1, 10);
                }
            }
            //
            dumpActiveOrders();
            //
            // disconnect();
        } catch (IOException e) {
            throw new MT4Exception(2, "No connection to TS", e);
        }
    }

    private void dumpActiveOrders() throws ErrNoOrderSelected {
        int ordersCount = ordersTotal();
        for (int i = 0; i < ordersCount; i++) {
            if (orderSelect(i, SelectionType.SELECT_BY_POS, SelectionPool.MODE_TRADES)) {
                int type = orderType(); // see TradeOperation
                System.out.println(String.format("Active order #%d, P/L=%f, comments=%s",
                        orderTicket(), orderProfit(), orderComment())
                );
            }
        }
    }

    @Override
    public long coordinationIntervalMillis() {
        return 1000;
    }

    @Override
    public void coordinate() {
        try {
            dumpActiveOrders();
        } catch (ErrNoOrderSelected errNoOrderSelected) {
            errNoOrderSelected.printStackTrace();
        }
    }

    private void m() throws IOException, InterruptedException {
        Strategy s = new Strategy(){
            @Override
            public long coordinationIntervalMillis() {
                return 1000;
            }
        };
        s.connect("127.0.0.1", 7788, DemoAccount.MT_4_SERVER, DemoAccount.MT_4_USER, DemoAccount.MT_4_PASSWORD);
        s.setReconnect(true);
        //
        Terminal t2 = s.addTerminal("EURJPY", new TickListener() {
            @Override
            public void onTick(TickInfo tick, MT4 connection) {
                System.out.println("T2 tick: " + tick);
            }
        });
        Terminal t3 = s.addTerminal(TerminalType.FREE_WORKER);
        Terminal t4 = s.addTerminal(TerminalType.ORDERS_WORKER);
        t2.connect();
        t3.connect();
        t4.connect();
        //
        isContinue = true;
        while (isContinue) {
            try {
                s.orderSelect(0, SelectionType.SELECT_BY_POS, SelectionPool.MODE_HISTORY);
                t3.getMt4Connection().orderSelect(1, SelectionType.SELECT_BY_POS, SelectionPool.MODE_HISTORY);
                t4.getMt4Connection().orderSelect(2, SelectionType.SELECT_BY_POS, SelectionPool.MODE_HISTORY);
            } catch (Exception e) {
                e.printStackTrace();
            }
            Thread.sleep(1000);
        }
        s.close(true);
        System.out.println("Done");
    }

    private void multipleConnectionsExample() throws IOException, InterruptedException, MT4Exception {
        final Strategy s = new Strategy();
        s.connect("127.0.0.1", 7788, DemoAccount.MT_4_SERVER, DemoAccount.MT_4_USER, DemoAccount.MT_4_PASSWORD, true);
        //
        final TickInfo lastTick[] = new TickInfo[1];
        final String eurjpy = "EURJPY";
        final Terminal t1 = s.addTerminal(eurjpy, new TickListener() {
            @Override
            public void onTick(TickInfo tick, MT4 connection) {
                lastTick[0] = tick;
            }
        });
        final Terminal t2 = s.addTerminal(TerminalType.FREE_WORKER);
        final Terminal t3 = s.addTerminal(TerminalType.FREE_WORKER);
        t1.connect();
        t2.connect();
        t3.connect();
        //
        t2.getMt4Connection().iHigh(eurjpy, Timeframe.PERIOD_M1, 0);
        t3.getMt4Connection().iHigh(eurjpy, Timeframe.PERIOD_M1, 0);
        // ...

        // asynchOrderOperations == true: we can do orderSend/Close/Delete/Modify in parallel to other MT4 methods
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    s.orderSend(eurjpy, TradeOperation.OP_BUY, 0.01, lastTick[0].ask, 10, 0, 0, "", 0, null, 0);
                } catch (MT4Exception e) {
                    e.printStackTrace();
                }
            }
        });
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    s.iHigh(eurjpy, Timeframe.PERIOD_M1, 0);
                } catch (MT4Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void main(String[] args) throws Exception  {
        System.setProperty("jfx_activation_key", "235961853");
        System.setProperty("jfx_activation_key", "2034239897");
        new ActiveOrdersExample().m();
        Thread.sleep(10000000);
    }

}
