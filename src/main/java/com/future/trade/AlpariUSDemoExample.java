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
import com.jfx.strategy.Strategy;

import java.io.IOException;
import java.util.Date;

public class AlpariUSDemoExample extends Strategy {
    private long ticket;

    public AlpariUSDemoExample() {
    }

    private long placeOrder(String symbol, TradeOperation cmd,
                           double volume, double price, int slippage, double stoploss,
                           double takeprofit, String comment, int magic,
                           Date expiration) {

        long idOrder = -1;

        refreshRates();
        try {
            idOrder = orderSend(symbol, cmd, volume, price, slippage, stoploss,
                    takeprofit, comment, magic, expiration);
        } catch (MT4Exception mt4Ex) {
        }

        return idOrder;
    }


    public void coordinate() {
        int ordersCount = ordersTotal();
        //
        if (ticket == 0) {
            ticket = placeOrder("EURUSD", TradeOperation.OP_BUYSTOP, 1, 1.42900, 3, 1.42560, 0, "buy with stop", 12345, null);
            //
            System.out.println("BUYSTOP created: " + ticket);
        } else  {
            try {
                orderDelete(ticket, 0);
                //
                System.out.println("BUYSTOP deleted: " + ticket);
            } catch (MT4Exception ex) {
                ex.printStackTrace();
            } finally {
                ticket = 0;
            }
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        final AlpariUSDemoExample usDemoExample = new AlpariUSDemoExample();
        //
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                try {
                    System.out.println("MT4 Terminal client disconnect...");
                    usDemoExample.disconnect();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        //
        System.out.println("You may kill terminal.exe to test reconnection...");
        usDemoExample.setReconnect(true);
        //
        String terminalServerIpAddress = "127.0.0.1";
        //
        usDemoExample.connect(terminalServerIpAddress, 7788, new Broker("AlpariUS-Demo"), "1146239", "lher0vc", "EURUSD");
        //
        System.out.println("Working...");
        Thread.sleep(1200000);
        //
        System.out.println("Stopping JFXServer...");
        JFXServer.stop();
        //
        System.out.println("Exit.");
    }
}
