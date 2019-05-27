package com.future.trade;/*
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

import com.jfx.ErrUnknownSymbol;
import com.jfx.MT4;
import com.jfx.MarketInfo;
import com.jfx.TickInfo;
import com.jfx.strategy.Strategy;
import com.jfx.strategy.StrategyRunner;

import java.io.IOException;

public class TickListenerExample extends Strategy {
    public TickListenerExample() {
    }

    @Override
    public synchronized void init(String symbol, int period, StrategyRunner strategyRunner) throws ErrUnknownSymbol, IOException {
        super.init(symbol, period, strategyRunner);
        System.out.println("Connected, " + getVersion());
        System.out.println("accountServer=" + accountServer());
        setAutoRefreshRates(false);
    }

    public void coordinate() {
        try {
            System.out.println("BID: " + marketInfo("EURJPY", MarketInfo.MODE_BID));
        } catch (ErrUnknownSymbol errUnknownSymbol) {
            errUnknownSymbol.printStackTrace();
        }
    }

    @Override
    public long coordinationIntervalMillis() {
        return 5000;
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        connectToMT4Server();
        //
        System.out.println("30-seconds demo...");
        Thread.sleep(30000);
        //
        System.out.println("Exit.");
        System.exit(0);
    }

    private static void connectToMT4Server() throws IOException {
        final TickListenerExample jfxExample = new TickListenerExample();
        //
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                try {
                    System.out.println("MT4 Terminal client disconnect...");
                    jfxExample.disconnect();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        jfxExample.setReconnect(true);
        String terminalServerIpAddress = "127.0.0.1";
        jfxExample.addTickListener("EURJPY", new TickListener() {
            @Override
            public void onTick(TickInfo tick, MT4 doNotUse) {
                System.out.println("" + System.currentTimeMillis() + "> " + tick + " " + jfxExample.accountBalance());
            }
        });
        jfxExample.connect(terminalServerIpAddress, 7788, DemoAccount.MT_4_SERVER, DemoAccount.MT_4_USER, DemoAccount.MT_4_PASSWORD);
    }
}
