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

import com.jfx.strategy.Strategy;
import com.jfx.strategy.StrategyRunner;
import com.jfx.Broker;
import com.jfx.ErrUnknownSymbol;

import java.io.IOException;

public class MT4StrategyProxy extends Strategy {
    /**
     * This constructor is used in testing mode, or when connection is initiated by MT4 terminal (jfx EA has been dropped on a chart) 
     */
    public MT4StrategyProxy() throws IOException {
    }

    /**
     * This constructor is used when Java initiates connection
     * JFX Terminal Server is used to start MT4 terminal in background
     * See Trader.getInstance() { .... new MT4StrategyProxy(USER, PASSWORD); .... }
     */
    public MT4StrategyProxy(String user, String password) throws IOException {
        //setReconnect(true);
        connect(
                "127.0.0.1", 7788, // <-- these are JFX Terminal Server host/port 
                new Broker("SIG-Demo"), // <-- put your broker here
                user, password
        );
    }

    public synchronized void init(String symbol, int period, StrategyRunner strategyRunner) throws ErrUnknownSymbol, IOException {
        super.init(symbol, period, strategyRunner);
        //
        // Set mt4 for Trader to use
        //
        Trader.getInstance().setMt4(this);
    }

    public void deinit() {
        super.deinit();
        //
        // Trader can not use mt4 anymore, clearing it
        //
        Trader.getInstance().setMt4(null);
    }

    public void coordinate() {
// I removed the code from this part, but basically there is an external class doing mt4 calls
// code below is just to simulate the scenario
      Trader.getInstance().doSomething();
    }
}