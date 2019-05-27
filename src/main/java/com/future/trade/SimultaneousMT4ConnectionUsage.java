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
import com.jfx.net.JFXServer;
import com.jfx.strategy.MT4DisconnectException;
import com.jfx.strategy.Strategy;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * This class shows how to use MT4 strategy instances from other classes, not extending com.jfx.strategy.BBStrategy
 */
public class SimultaneousMT4ConnectionUsage {

    public static class MyMT4Proxy extends Strategy {
        public MyMT4Proxy() throws IOException {
            setReconnect(true);
            connect("127.0.0.1", 7788, Broker.AlpariUS_Demo, "1058553", "r7etsoo");
        }

        int c;

        public void coordinate() {
            refreshRates();
            System.out.println("Coordination: " + c + ", doing some parallel job ...");
            c++;
        }
    }

    private static int taskNo;

    //
    public static void main(String[] args) throws IOException, InterruptedException {
        final MyMT4Proxy mt4 = new MyMT4Proxy();
        //
        ExecutorService executorService = Executors.newCachedThreadPool();
        ArrayList<Future> tasks = new ArrayList<Future>();
        for (int t = 0; t < 5; t++) {
            tasks.add(executorService.submit(new Runnable() {
                int task;

                {
                    synchronized (Class.class) {
                        task = taskNo++;
                    }
                }

                public void run() {
                    try {
                        System.out.println("Started. " + " Task #" + task);
                        long t1 = System.currentTimeMillis();
                        int cnt = 5000;
                        for (int i = 0; i < cnt; i++) {
                            try {
                                // no synchronization for non-order-select-depending methods is required - it is done automatically
                                mt4.getSlippage();
                                mt4.getTickCount();
                                mt4.iOpen(mt4.getSymbols().get(0), Timeframe.PERIOD_H1, 0);
                                //
                                synchronized (mt4) { // synchronize for order-selection processing
                                    try {
                                        mt4.orderSelect(0, SelectionType.SELECT_BY_POS, SelectionPool.MODE_TRADES);
                                        mt4.orderProfit();
                                    } catch (ErrNoOrderSelected errNoOrderSelected) {
                                        errNoOrderSelected.printStackTrace();
                                    }
                                }
                                if (i % 1000 == 0) {
                                    System.out.println("Sub-task: iter=" + i + " Task #" + task);
                                }
                            } catch (MT4DisconnectException e) {
                                //noinspection EmptyCatchBlock
                                try {
                                    Thread.sleep(5000); // wait for reconnect
                                } catch (InterruptedException e1) {
                                }
                            } catch (MT4Exception other) {
//                                other.printStackTrace();
                            } catch (RuntimeException unknown) {
//                                unknown.printStackTrace();
                            }
                        }
                        long t2 = System.currentTimeMillis();
                        System.out.println("Ended, TPS=" + Math.round(cnt / (((double) (t2 - t1)) / 1000.0)) + ", time=" + (t2 - t1) / 1000 + " sec." + " Task #" + task);
                    } catch (Throwable t) {
                        t.printStackTrace();
                    }
                }
            }));
        }
        //
        boolean isSmthToDo = true;
        while (isSmthToDo) {
            isSmthToDo = false;
            //noinspection ForLoopReplaceableByForEach
            for (int i = 0; i < tasks.size(); i++) {
                Future future = tasks.get(i);
                if (!future.isDone()) {
                    isSmthToDo = true;
                    Thread.sleep(1000);
                    break;
                }
            }
        }
        //
        mt4.disconnect();
        //
        executorService.shutdown();
        //
        Thread.sleep(1000);
        JFXServer.stop();
    }
}
