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

import com.jfx.net.JFXServer;

import java.io.IOException;

public class Trader {
    private static final Trader instance = new Trader();
    //
    // Use -DMT4_TESTING=true to run Trader in testing mode
    //
    private static final boolean IS_MT4_TESTING = System.getProperty("MT4_TESTING", "false").equals("true");
    private static final String USER = "1001087404"; 
    private static final String PASSWORD = "ine8ydq";

    public static void main(String[] args) throws InterruptedException {
        Trader.getInstance();
        Thread.sleep(1000000);
    }

    private Trader() {
    }

    private static boolean isMt4Initialized;

    public static Trader getInstance() {
        synchronized (Trader.class) {
            if (!isMt4Initialized) {
                isMt4Initialized = true;
                //
                // Init MT4StrategyProxy
                //
                if (IS_MT4_TESTING) {
                    //
                    // Will wait for MT4 Terminal Tester to connect 127.0.0.1:7777
                    //
                    // JFXServer.getInstance() uses jfx_server_host/port system properties
                    // System.getProperty("jfx_server_host", "127.0.0.1")
                    // System.getProperty("jfx_server_port", "7777")
                    JFXServer.getInstance(); // same as JFXServer jfxServer = new JFXServer("localhost", 7777); but no need to keep extra variable 
                } else {
                    try {
                        new MT4StrategyProxy(USER, PASSWORD);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return instance;
    }

    private final Object mt4Synch = new Object();
    private MT4StrategyProxy mt4;

    public synchronized void setMt4(MT4StrategyProxy mt4StrategyProxy) {
        synchronized (mt4Synch) {
            mt4 = mt4StrategyProxy;
            mt4Synch.notifyAll();
        }
    }

    public synchronized MT4StrategyProxy getMt4() throws InterruptedException {
        synchronized (mt4Synch) {
            while (mt4 == null) {
                System.out.println("Waiting for Mt4 to be initialized ...");
                mt4Synch.wait(5000); // will be notified by setMt4()
            }
            return mt4;
        }
    }


    public void doSomething() {
        //
        // This way MT4StrategyProxy is not connected yet
        //
        //MT4StrategyProxy mt4 = new MT4StrategyProxy();

        int ticket = 0;
        try {
            double v = getMt4().accountBalance();
            System.out.println("Testing=" + getMt4().isTesting() + ", accountBalance=" + v + ", time=" + getMt4().marketInfo_MODE_TIME("GBPUSD"));
            /*ticket = getMt4().orderSend(
                    "GBPUSD",
                    TradeOperation.OP_SELL,
                    1,
                    1.7272,
                    2,
                    0,//price + 200 * point,
                    0,//price - 200 * point,
                    "" + System.currentTimeMillis(),
                    0,
                    new Date(System.currentTimeMillis() + 60 * 60 * 1000),
                    -1
            );*/
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}