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
import com.jfx.strategy.Strategy;
import com.jfx.strategy.StrategyRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

public class JFXStreamerExample extends Strategy {
    public JFXStreamerExample() {
    }

    @Override
    public synchronized void init(String symbol, int period, StrategyRunner strategyRunner) throws ErrUnknownSymbol, IOException {
        super.init(symbol, period, strategyRunner);
        System.out.println("Connected, " + getVersion());
        System.out.println("accountServer=" + accountServer());
        setAutoRefreshRates(false);
    }

    HashSet<Instrument> ignoreSymbols = new HashSet<Instrument>();
    public void coordinate() {
        ArrayList<Instrument> instruments = getInstruments();
        if (instruments != null) {
            System.out.println("----------- Balance and Margin --------");
            System.out.println("Balance=" + accountBalance() + ", leverage=1:" + accountLeverage()+ ", " + accountCompany());
            System.out.println("----------- Streaming M-1 Bars --------");
            //noinspection ForLoopReplaceableByForEach
            for (int i = 0; i < instruments.size(); i++) {
                Instrument s = instruments.get(i);
                if (ignoreSymbols.contains(s)) {
                    continue;
                }
                //
                try {
//                    double bid = marketInfo(s.getName(), MarketInfo.MODE_BID);
                    //
                    String symbol = s.getName();
                    System.out.println(
                            symbol
                            + " -M1-"
                            + " " + iTime(symbol, Timeframe.PERIOD_M1, 0)
                            + ", " + iOpen(symbol, Timeframe.PERIOD_M1, 0)
                            + ", " + iHigh(symbol, Timeframe.PERIOD_M1, 0)
                            + ", " + iLow(symbol, Timeframe.PERIOD_M1, 0)
                            + ", " + iClose(symbol, Timeframe.PERIOD_M1, 0)
                    );
                } catch (MT4Exception err) {
                    err.printStackTrace();
                } catch (MT4RuntimeException err) {
                    ignoreSymbols.add(s);
                }
            }
            System.out.println();
        } else {
            try {
                String symbol = getSymbol();
                System.out.println(
                        symbol
                                + " -M1-"
                                + " " + iTime(symbol, Timeframe.PERIOD_M1, 0)
                                + ", " + iOpen(symbol, Timeframe.PERIOD_M1, 0)
                                + ", " + iHigh(symbol, Timeframe.PERIOD_M1, 0)
                                + ", " + iLow(symbol, Timeframe.PERIOD_M1, 0)
                                + ", " + iClose(symbol, Timeframe.PERIOD_M1, 0)
                );
            } catch (MT4Exception err) {
                err.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        connectToMT4Server();
        //
        System.out.println("30-seconds demo...");
        Thread.sleep(90000);
        //
        System.out.println("Exit.");
        System.exit(0);
    }

    private static void connectToMT4Server() throws IOException {
        final JFXStreamerExample jfxExample = new JFXStreamerExample();
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
//        String terminalServerIpAddress = "10.64.31.126";
        jfxExample.connect(terminalServerIpAddress, 7788, DemoAccount.MT_4_SERVER, DemoAccount.MT_4_USER, DemoAccount.MT_4_PASSWORD, "EURUSD");
        jfxExample.addTerminal("EURJPY", new TickListener() {
            @Override
            public void onTick(TickInfo tick, MT4 connection) {
                System.out.println("EURJPY -> " + tick);
            }
        });
    }
}
