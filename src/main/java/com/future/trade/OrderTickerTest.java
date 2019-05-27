/*
 * Copyright (c) 2008-2014 by Gerasimenko Roman.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistribution of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistribution in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in
 *     the documentation and/or other materials provided with the
 *     distribution.
 *
 * 3. The name "JFX" must not be used to endorse or promote
 *     products derived from this software without prior written
 *     permission.
 *     For written permission, please contact roman.gerasimenko@gmail.com
 *
 * 4. Products derived from this software may not be called "JFX",
 *     nor may "JFX" appear in their name, without prior written
 *     permission of Gerasimenko Roman.
 *
 *  THIS SOFTWARE IS PROVIDED "AS IS" AND ANY EXPRESSED OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 *  OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 *  DISCLAIMED.  IN NO EVENT SHALL THE JFX CONTRIBUTORS
 *  BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 *  SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 *  LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 *  USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 *  ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 *  OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 *  OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 *  SUCH DAMAGE.
 */
package com.future.trade;
import com.jfx.*;
import com.jfx.strategy.*;

import java.io.IOException;
import java.util.concurrent.*;

public class OrderTickerTest {
    public static final Broker MT_4_SERVER = new Broker("5*91.109.206.235:443");
    public static final String MT_4_USER = "9007162";
    public static final String MT_4_PASSWORD = "ibiq4mnf";

    public static void main(String[] args) throws IOException {
        System.setProperty("jfx_activation_key", System.getProperty("jfx_activation_key", "235961853"));
//        System.setProperty("jfx_use_udp", "true");
        new OrderTickerTest().mainTest();
    }

    Strategy c;
    final ExecutorService executor = Executors.newFixedThreadPool(4);

    //    @Test
    public void mainTest() throws IOException {
        c = new Strategy() {
            @Override
            public synchronized void init(String symbol, int period,
                                          StrategyRunner strategyRunner) throws ErrUnknownSymbol, IOException {
                super.init(symbol, period, strategyRunner);
                setAutoRefreshRates(false);
            }
        };
        c.setReconnect(true);
        c.addTickListener("EURUSD", new MyTickListener("EURUSD"))
//                .addTickListener("GBPUSD", new MyTickListener("GBPUSD"))
//                .addTickListener("USDCHF", new MyTickListener("USDCHF"))
//                .addTickListener("EURJPY", new MyTickListener("EURJPY"))
                .setPositionListener(new PositionListener() {
                    @Override
                    public void onInit(PositionInfo initialPositionInfo) {
                        System.out.println("Time: " + System.currentTimeMillis() + "> PLSNR: init: " + initialPositionInfo.liveOrders());
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
                }, 1000, 1000)
                .withDedicatedInstrumentOrdersWorker("EURUSD")
                .connect("127.0.0.1", 7788, MT_4_SERVER, MT_4_USER, MT_4_PASSWORD);
        //
/*
        c.connect("127.0.0.1", 7788, DemoAccount.MT_4_SERVER, DemoAccount.MT_4_USER, DemoAccount.MT_4_PASSWORD);
        c.addTerminal(Strategy.TerminalType.ORDERS_WORKER).connect();// as many as you need parallel order processors
        c.addTerminal(Strategy.TerminalType.TICK_WORKER)
                .addTickListener("EURUSD", new MyTickListener("EURUSD"))
                .addTickListener("GBPUSD", new MyTickListener("GBPUSD"))
                .addTickListener("USDCHF", new MyTickListener("USDCHF"))
                .addTickListener("EURJPY", new MyTickListener("EURJPY"))
                .connect();
*/
        //
        while (true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                break;
            }
        }
        //
        executor.shutdown();
        c.disconnect();
    }

    private class MyTickListener implements Strategy.TickListener {
        private String symbol;
        Future<Integer> orderJob;
        Future<Boolean> orderCloseJob;

        private MyTickListener(String symbol) {
            this.symbol = symbol;
            orderJob = null;
            orderCloseJob = null;
        }

        @Override
        public void onTick(final TickInfo tick, MT4 connection) {
            System.out.println("New tick: " + tick);
            try {
                if (orderJob == null) {
                    orderJob = executor.submit(new Callable<Integer>() {
                        @Override
                        public Integer call() throws Exception {
                            return c.orderSend(symbol, TradeOperation.OP_BUY, 1, tick.ask, 100, 0, 0, "order ticker test", 0, null, 0);
                        }
                    });
                } else if (orderJob.isDone()) {
                    if (orderCloseJob == null) {
                        try {
                            final int ticket = orderJob.get();
                            if (connection.orderSelect(ticket, SelectionType.SELECT_BY_TICKET, SelectionPool.MODE_TRADES)) {
                                System.out.println("Opened order: " + symbol + " t=" + connection.orderTicket() + ", p=" + connection.orderOpenPrice());
                            }
                            orderCloseJob = executor.submit(new Callable<Boolean>() {
                                @Override
                                public Boolean call() throws Exception {
                                    return c.orderClose(ticket, 1, tick.bid, 100, 0);
                                }
                            });
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }
                    } else if (orderCloseJob.isDone()) {
                        try {
                            final int ticket = orderJob.get();
                            if (connection.orderSelect(ticket, SelectionType.SELECT_BY_TICKET, SelectionPool.MODE_HISTORY)) {
                                System.out.println("Closed order: " + symbol + " t=" + connection.orderTicket() + ", p=" + connection.orderClosePrice());
                            }
                            Thread.sleep(10000);
                            orderJob = null;
                            orderCloseJob = null;
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            } catch (MT4Exception e) {
                e.printStackTrace();
            }
        }
    }
}
