package com.future.trade;

import com.jfx.*;
import com.jfx.strategy.Strategy;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MultipleConnectionsExample {
    String eurjpy = "EURJPY";
    TickInfo lastTick;
    Strategy s;
    Strategy.Terminal terminal;
    Strategy.Terminal t2;
    Strategy.Terminal t3;

    private void run() throws IOException, InterruptedException, MT4Exception {
        s = new Strategy();
        s.connect("127.0.0.1", 7788, Account.MT_4_SERVER, Account.MT_4_USER, Account.MT_4_PASSWORD, true);
        System.out.println("Account connected: " + s.accountName());
        //
        terminal = s.addTerminal(Strategy.TerminalType.TICK_WORKER);
        for (final String sy : s.getSymbols()) {
            terminal.addTickListener(sy, new Strategy.TickListener() {
                @Override
                public void onTick(TickInfo tick, MT4 connection) {
                    System.out.println("TICK (" + sy + "): " + tick);
                }
            });
        }
        terminal.addTickListener(eurjpy, new Strategy.TickListener() {
            @Override
            public void onTick(TickInfo tick, MT4 connection) {
                lastTick = tick;
                System.out.println("TICK: " + tick);
            }
        });
        terminal.addTickListener("EURUSD", new Strategy.TickListener() {
            @Override
            public void onTick(TickInfo tick, MT4 connection) {
                System.out.println("EURUSD TICK: " + tick);
            }
        });
        terminal.addTickListener("GBPUSD", new Strategy.TickListener() {
            @Override
            public void onTick(TickInfo tick, MT4 connection) {
                System.out.println("GBPUSD TICK: " + tick);
            }
        });
        t2 = s.addTerminal(Strategy.TerminalType.FREE_WORKER);
        t3 = s.addTerminal(Strategy.TerminalType.FREE_WORKER);
        terminal.connect();
        System.out.println("Connected tick listener.");
        t2.connect();
        System.out.println("Connected terminal #2.");
        t3.connect();
        System.out.println("Connected terminal #3.");
        //
        t2.getMt4Connection().iHigh(eurjpy, Timeframe.PERIOD_M1, 0);
        t3.getMt4Connection().iHigh(eurjpy, Timeframe.PERIOD_M1, 0);
        // ...

        Runnable task1 = new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("Time: " + System.currentTimeMillis() + "> Sending order");
                    int ticket = s.orderSend(eurjpy, TradeOperation.OP_BUY, 0.01, lastTick.ask, 10, 0, 0, "multiple connections example", 0, null, 0);
                    System.out.println("Time: " + System.currentTimeMillis() + "> Order sent: ticket=" + ticket);
                } catch (MT4Exception e) {
                    System.out.println("Time: " + System.currentTimeMillis() + "> Order sending error: " + e);
                }
            }
        };
        Runnable task2 = new Runnable() {
            @Override
            public void run() {
                try {
                    for (int i = 0; i < 10; i++) {
                        System.out.println("Time: " + System.currentTimeMillis() + "> BID = " + s.marketInfo(eurjpy, MarketInfo.MODE_BID));
                        try {
                            Thread.sleep(200);
                        } catch (InterruptedException ignore) {
                        }
                    }
                } catch (MT4Exception e) {
                    e.printStackTrace();
                }
            }
        };
        ExecutorService executorService = Executors.newCachedThreadPool();
        // asynchOrderOperations == true: we can do orderSend/Close/Delete/Modify in parallel to other MT4 methods
        System.out.println("--------------------------- asynchOrderOperations: submitting jobs -----------------------------");
        executorService.submit(task1);
        executorService.submit(task2);
        System.out.println("--------------------------- asynchOrderOperations: jobs submitted ------------------------------");
    }

    private void run2() throws IOException, InterruptedException, MT4Exception {
        final Strategy strategy = new Strategy();
        strategy.connect("127.0.0.1", 7788, Account.MT_4_SERVER, Account.MT_4_USER, Account.MT_4_PASSWORD);
        //
        terminal = strategy.addTerminal(Strategy.TerminalType.TICK_WORKER);
        for (final String symbol : strategy.getSymbols()) {
            terminal.addTickListener(symbol, new Strategy.TickListener() {
                @Override
                public void onTick(final TickInfo tick, MT4 connection) {
                    HashMap<Integer,Double> activeOrdersProfitLoss = tick.orderPlMap;
                    Date time = tick.time;
                    double ask = tick.ask;
                    double bid = tick.bid;
                    System.out.println("TICK (" + symbol + "): " + tick);
                }
            });
        }
        terminal.connect();
    }

    public static void main(String[] args) throws Exception {
        System.setProperty("jfx_activation_key", "235961853");
//        System.setProperty("jfx_server_port", "17342");
        MultipleConnectionsExample example = new MultipleConnectionsExample();
        System.out.println("Press <Enter> to exit.");
        example.run();
        System.in.read();
        example.s.disconnect();
        System.out.println("Account disconnected");
        System.exit(0);
    }

    private static class Account {
        public static final Broker MT_4_SERVER = new Broker("5*91.109.206.235:443");
        public static final String MT_4_USER = "9007162";
        public static final String MT_4_PASSWORD = "ibiq4mnf";
//        public static final Broker MT_4_SERVER = new Broker("5*91.109.206.235:443");
//        public static final String MT_4_USER = "9007162";
//        public static final String MT_4_PASSWORD = "ibiq4mnf";
    }
}
