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

import com.jfx.*;
import com.jfx.net.JFXServer;
import com.jfx.strategy.Strategy;
import com.jfx.strategy.StrategyRunner;
import com.jfx.xml.DOMUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class JFXExample extends Strategy {
    private static boolean multi;

    static {
//        String publicIP = getPublicIP();
//        System.out.println("Using public IP: " + publicIP);
//        System.setProperty("jfx_server_host", "10.10.1.188");

//        System.setProperty("jfx_activation_key", "187905");
//        System.setProperty("jfx_server_port", "17342"); // for extended debug info
    }

    private static String getPublicIP() {
        try {
            //
            String bindAddress = null;
            for (Enumeration ifaces = NetworkInterface.getNetworkInterfaces(); ifaces.hasMoreElements(); ) {
                NetworkInterface iface = (NetworkInterface) ifaces.nextElement();
                boolean ifPrinted = false;
                for (Enumeration ips = iface.getInetAddresses(); ips.hasMoreElements(); ) {
                    InetAddress ia = (InetAddress) ips.nextElement();
                    String address = ia.getHostAddress();
                    boolean skip = address.startsWith("192.168.") || address.startsWith("127.0.0.1") || address.indexOf(':') > 0;
                    if (!skip) {
                        if (!ifPrinted) {
                            System.out.println("Interface: " + iface.getDisplayName() + '/' + iface.getName());
                            ifPrinted = true;
                        }
                        System.out.println("    " + ia.getCanonicalHostName() + ": " + address);
//                        System.out.println("    SiteLocal=" + ia.isSiteLocalAddress());
                        if (!ia.isSiteLocalAddress()) {
                            return address;
                        } else {
                            bindAddress = address;
                        }
                    }
                }
            }
            return bindAddress;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //
    private int shift;
    public double sellPrice;
    private static boolean laTestDone = false;
//    private static final String EURUSD = "EURUSD";

    public JFXExample() {
        System.out.println("********** JFXExample() constructor called ***********");
    }

    public JFXExample(double p1, double p2) {
        System.out.println("********** JFXExample(p1, p2) constructor called ***********");
    }

    public JFXExample(double p1, double p2, double p3) {
        System.out.println("********** JFXExample(p1, p2, p3) constructor called ***********");
    }

    @Override
    public void deinit() {
        System.out.printf("********** JFXExample.deinit(%s, %d) constructor called ***********\n", getSymbol(), getPeriod());
        super.deinit();
    }

    public synchronized void init(String symbol, int period, StrategyRunner strategyRunner) throws ErrUnknownSymbol, IOException {
        System.out.printf("********** %s %s JFXExample.init(%s, %d) init called ***********\n", accountCompany(), accountNumber(), symbol, period);
        super.init(symbol, period, strategyRunner);
        //
        ArrayList<Instrument> instruments = getInstruments();
        if (instruments != null) {
            System.out.println("Available instruments:");
            for (int i = 0; i < instruments.size(); i++) {
                Instrument s = instruments.get(i);
                System.out.println("" + s);
            }
            System.out.println("");
        }
        //
        int gmax = globalVariablesTotal();
        for (int i = 0; i < gmax; i++) {
            String n = globalVariableName(i);
            System.out.println(n + '=' + globalVariableGet(n));
        }
    }

    boolean infoPrinted;

    //final static long[] CNT_THRESHOLDS = new long[]{50, 100, 250, 500, 10000};
    final static long[] CNT_THRESHOLDS = new long[]{5, 10, 25, 50, 100};

    private void getCounted(long createdRespDuration, long[] createCnt) {
        for (int i = 0; i < CNT_THRESHOLDS.length; i++) {
            long cntThreshold = CNT_THRESHOLDS[i];
            if (cntThreshold > createdRespDuration) {
                createCnt[i]++;
                break;
            }
        }
    }

    public static String lpad(String s, int len, char pad) {
        int padLen = len - s.length();
        if (padLen > 0) {
            char[] p = new char[padLen];
            for (int i = 0; i < padLen; ++i) {
                p[i] = pad;
            }
            return (new String(p)).concat(s);
        } else {
            return s;
        }
    }

    private String format(String ts, String s, String s1, String s2, String s3, String s4, long[] timeCounts) {
        int width = 20;
        StringBuffer msg = new StringBuffer(ts + s + lpad(" ", width - s.length(), ' ') +
                s1 + lpad(" ", width - s1.length(), ' ') +
                s2 + lpad(" ", width - s2.length(), ' ') +
                s3 + lpad(" ", width - s3.length(), ' ') +
                s4 + lpad(" ", width - s4.length(), ' ') + "cnt: ");
        for (int i = 0; i < timeCounts.length; i++) {
            long timeCount = timeCounts[i];
            String str = String.valueOf(timeCount);
            msg.append(str).append(lpad(" ", 5 - str.length(), ' '));
        }
        return msg.toString();
    }

    private long placeOrder(java.lang.String symbol, TradeOperation cmd,
                           double volume, double price, int slippage, double stoploss,
                           double takeprofit, java.lang.String comment, int magic,
                           java.util.Date expiration) {

        long idOrder = -1;
        try {
            double point = marketInfo(symbol, MarketInfo.MODE_POINT);
            idOrder = orderSend(symbol, cmd, volume, normalize(price, point), slippage, normalize(stoploss, point),
                    normalize(takeprofit, point), comment, magic, expiration);
        } catch (MT4Exception mt4Ex) {
        }

        return idOrder;
    }


    public class TrailingStop {
        int ticket, keepDistance, changeInterval;

        public TrailingStop(int ticket, int keepDistance, int changeInterval) {
            this.ticket = ticket;
            this.keepDistance = keepDistance;
            this.changeInterval = changeInterval;
        }

        public void check() {
            try {
                if (orderSelect(ticket, SelectionType.SELECT_BY_TICKET, SelectionPool.MODE_TRADES)) {
                    if (orderType() == TradeOperation._OP_BUY) {
                        //
                    } else {
                    }
                }
            } catch (MT4Exception errNoOrderSelected) {
//                errNoOrderSelected.printStackTrace();
            }
        }
    }

    boolean notEnoughMoney;

    @Override
    public long coordinationIntervalMillis() {
        return 2000; // check market each 2 seconds
    }

    long lastSymbolTickTime;
    double lastSymbolBid;
    ExecutorService threads = java.util.concurrent.Executors.newFixedThreadPool(10);

    @Override
    public void coordinate() {
        try {
            final MarketInformation mi = marketInfo(getSymbol());
            long symbolTickTime = (long) marketInfo(getSymbol(), MarketInfo.MODE_TIME); // The last incoming tick time (last known server time).
            if (lastSymbolTickTime / 60 == symbolTickTime / 60) {
                return;
            }
            lastSymbolTickTime = symbolTickTime;
            for (int i = 0; i < 2; ++i) { // creating two orders in parallel
                threads.submit(new Runnable() {
                    @Override
                    public void run() {
                        int t = 0;
                        try {
                            t = orderSend(getSymbol(),
                                    TradeOperation.OP_BUY,
                                    1,
                                    mi.ASK,
                                    1,
                                    mi.ASK - mi.POINT * 200,
                                    mi.ASK + mi.POINT * 200,
                                    "" + new Date(),
                                    0, null, Color.AliceBlue.val
                            );
                            System.out.println("New order created: ticket=" + t + ", time=" + new Date());
                        } catch (MT4Exception e) {
                            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                        }
                    }
                });
            }
        } catch (MT4Exception errUnknownSymbol) {
            errUnknownSymbol.printStackTrace();
        }

        try {
            String symbol = getSymbol();//"SYMBOL_OF_INTEREST";
//
            iBarShift(symbol, Timeframe.PERIOD_H1, java.sql.Timestamp.valueOf("2011-01-01 00:00:00.0"), true);
            //
            double symbolTickTime = marketInfo(symbol, MarketInfo.MODE_TIME); // The last incoming tick time (last known server time).
            double symbolBid = marketInfo(symbol, MarketInfo.MODE_BID); // Last incoming bid price.
            int ordersCount = ordersTotal();
            for (int i = 0; i < ordersCount; i++) {
                if (orderSelect(i, SelectionType.SELECT_BY_POS, SelectionPool.MODE_TRADES)) {
                    int t = orderTicket();
                    int type = orderType();
                }
            }
        } catch (MT4Exception mt4ex) {
            mt4ex.printStackTrace();
        }
        try {
            //refreshRates();
            //double _point = marketInfo(getSymbol(), MarketInfo.MODE_POINT);
            //double _bid = marketInfo(getSymbol(), MarketInfo.MODE_BID);
            //double _ask = marketInfo(getSymbol(), MarketInfo.MODE_ASK);
            //System.out.println("point=[" + _point + "] sym=" + getSymbol());
            //System.out.println("bid=[" + _bid + "] [" + (Math.round(_bid/_point) * _point) + ']');
            //System.out.println("bid=[" + _bid + "] [" + (Math.round(_bid * 1000000) / 1000000.0) + ']');
            //System.out.println("ask=[" + _ask + "] [" + (Math.round(_ask/_point) * _point) + ']');
            //
            if (!laTestDone && isTesting()) {
                laTestDone = true;
            }
            if (!laTestDone) {
                laTestDone = true;
                long[] createCnt = new long[CNT_THRESHOLDS.length];
                long createMax = Long.MIN_VALUE,
                        createMin = Long.MAX_VALUE,
                        createTotal = 0,
                        createCount = 0,
                        createErrCount = 0;
                long start0 = System.currentTimeMillis();
                String ts = "" + new java.sql.Timestamp(System.currentTimeMillis()) + "> ";
                System.out.println(ts + "Latency test...");
                int i = 0;
                for (; i < 50000; i++) {
                    try {
                        long start = System.currentTimeMillis();
                        //
                        if ((i == 5) && ((start - start0) > 1000)) {
                            System.out.println("Slowing to " + (start - start0) / 5000 + " sec/req");
                            break;
                        }
                        //
//                        print("some message to print by MT4 Terminal");
//                        MarketInformation marketInformation = mi;
                        marketInfo(getSymbol(), MarketInfo.MODE_BID);
                        //
                        long time = System.currentTimeMillis() - start;
                        //
                        getCounted(time, createCnt);
                        //
                        createMax = Math.max(createMax, time);
                        createMin = Math.min(createMin, time);
                        createCount++;
                    } catch (Exception e) {
                        createErrCount++;
                    } finally {
                        createTotal++;
                    }
                }
                if (i > 5) {
                    long time0 = System.currentTimeMillis() - start0;
                    ts = "" + new java.sql.Timestamp(System.currentTimeMillis()) + "> ";
                    System.out.println(ts + "total latency test duration: " + time0 + " millis for " + createTotal + " requests");
                    System.out.println(format(ts,
                            "Total Req",
                            "Error Req",
                            "Min_Req Time",
                            "Max_Req Time",
                            "Avg_Req Time",
                            CNT_THRESHOLDS));
                    System.out.println(format(ts,
                            "Cnt " + createCount,
                            "ErrCnt " + createErrCount,
                            "Min " + (createMin == Long.MAX_VALUE ? 0 : createMin),
                            "Max " + createMax,
                            "Avg " + ((int) ((double) createTotal / createCount)),
                            createCnt));
                }
            }
            //
            int ordersCount = ordersTotal();
            //
            if (ordersCount == 0 && !notEnoughMoney) {
                System.out.println("ordersTotal: " + ordersCount + ", accBal=" + accountBalance());
                //
                placeOrder(getSymbol(), TradeOperation.OP_BUYSTOP, marketInfo(getSymbol(), MarketInfo.MODE_MINLOT)
                        , 1.42900, 3, 1.42560, 0, "buy with stop", 12345, null);
                //
                double point = marketInfo(getSymbol(), MarketInfo.MODE_POINT);
                double price = marketInfo(getSymbol(), MarketInfo.MODE_BID);
                sellPrice = price;
                //
                double bb1 = iBands(getSymbol(), Timeframe.PERIOD_H1, 10, 3, 5, AppliedPrice.PRICE_CLOSE, BandsIndicatorLines.MODE_LOWER, 0);
                double bb2 = iBands(getSymbol(), Timeframe.PERIOD_H4, 13, 1, 9, AppliedPrice.PRICE_MEDIAN, BandsIndicatorLines.MODE_UPPER, 0);
                //
                int ticket = orderSend(
                        getSymbol(),
                        TradeOperation.OP_SELL,
                        marketInfo(getSymbol(), MarketInfo.MODE_MINLOT),
                        sellPrice,
                        2,
                        0,//price + 200 * point,
                        0,//price - 200 * point,
                        "" + System.currentTimeMillis(),
                        0,
                        new Date(System.currentTimeMillis() + 60 * 60 * 1000),
                        -1
                );
                System.out.println("---------------------------------------");
                System.out.println("Sell order ticket: " + ticket);
            } else {
                if (orderSelect(0, SelectionType.SELECT_BY_POS, SelectionPool.MODE_TRADES)) {
                    int ticket = orderTicket();
                    String orderSymbol = orderSymbol();
                    double bid = marketInfo(orderSymbol, MarketInfo.MODE_BID);
                    double ask = marketInfo(orderSymbol, MarketInfo.MODE_ASK);
                    //
                    if (!infoPrinted) {
                        long serverTime = ((long) marketInfo(orderSymbol(), MarketInfo.MODE_TIME)) * 1000;
                        long l = (serverTime - System.currentTimeMillis()) / 1000 / 60;
                        System.out.println("Found order, ticket=" + ticket + ", profit=" + orderProfit() + ", symbol=" + orderSymbol + ", volume=" + orderLots() + ", orderOpenTime=" + orderOpenTime());
                        if (false && getTermServerHost() != null && getTermServerHost().equals("127.0.0.1")) {
//                            alert("Found order, ticket=" + ticket + ", profit=" + orderProfit() + ", symbol=" + orderSymbol + ", volume=" + orderLots() + ", orderOpenTime=" + orderOpenTime());
                            //
                            String text = "Found order, ticket=" + ticket + ", profit=" + orderProfit() + ", symbol=" + orderSymbol + ", volume=" + orderLots() + ", orderOpenTime=" + orderOpenTime()
                                    + " minWindowPrice=" + windowPriceMin(0)
                                    + " maxWindowPrice=" + windowPriceMax(0);
                            String caption = terminalCompany();
                            int res = messageBox(
                                    text,
                                    caption,
                                    MessageBoxFlag._MB_OK | MessageBoxFlag._MB_ICONINFORMATION
                            );
                            if (MessageBoxID._IDOK == res) {
                                System.out.println("MessageBox result: IDOK");
                            }
                        }
                        //
                        infoPrinted = true;
                    }
                    //
                    String startTime = orderComment();
                    long _startTime = 0;
                    try {
                        _startTime = Long.parseLong(startTime);
                    } catch (NumberFormatException e) {
                    }
                    if (_startTime == 0) {
                        System.out.println("Closing order, ticket=" + ticket + ", profit=" + orderProfit() + ", symbol=" + orderSymbol + ", volume=" + orderLots() + ", orderOpenTime=" + orderOpenTime());
                        if (orderType() == TradeOperation._OP_BUY) {
                            orderClose(ticket, orderLots(), bid, 2, 0);
                        } else if (orderType() == TradeOperation._OP_SELL) {
                            orderClose(ticket, orderLots(), marketInfo(orderSymbol, MarketInfo.MODE_ASK), 2, 0);
                        }
                    } else {
                        long seconds = (System.currentTimeMillis() - _startTime) / 1000;
                        if (seconds % 100 == 0) {
                            if (orderProfit() > 1000) {
                                System.out.println("Closing order, ticket=" + ticket + ", profit=" + orderProfit() + ", orderOpenTime=" + orderOpenTime());
                                orderClose(ticket, 1, ask, 2, 0);
                            } else if (seconds > 6000) {
                                System.out.println("Closing order, ticket=" + ticket + ", loss=" + orderProfit() + ", orderOpenTime=" + orderOpenTime());
                                orderClose(ticket, 1, ask, 2, 0);
                            }
                        } else {
                            if (shift++ < 10000) {
                                //
                                // Load history
                                //
                                String mySymbol = getSymbol();
                                try {
                                    Date dateInPast = new Date();
                                    Timeframe timeframe = Timeframe.PERIOD_M1;
                                    int maxShift = iBarShift(mySymbol, timeframe, dateInPast, true);
                                    for (int shift = maxShift; shift >=0; shift--) {
                                        iOpen(mySymbol, timeframe, shift);
                                        iClose(mySymbol, timeframe, shift);
                                        iHigh(mySymbol, timeframe, shift);
                                        iLow(mySymbol, timeframe, shift);
                                    }
                                    if (shift % 10 == 0) {
                                        System.out.println("MT4 user " + getMt4User() + ": History H1 shift: " + shift);
                                    }
                                } catch (MT4Exception ignore) {
                                }
                            }
                        }
                    }
                }
            }
        } catch (MT4Exception err) {
            throw new RuntimeException(err);
        }
    }

    private double normalize(double bid, double point) {
        return new BigDecimal(bid).setScale((int) (1 / point)).doubleValue();
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        if (args.length == 1) {
            if (args[0].equals("test")) {
                JFXServer.getInstance();// jfx_server_host:jfx_server_port [localhost:7777]
                //
                System.out.println("Waiting for incoming MT4 connection at "
                        + System.getProperty("jfx_server_host", "127.0.0.1") + ":"
                        + System.getProperty("jfx_server_port", "7777")
                        + "..."
                );
                Thread.sleep(2000000);
                //
                System.out.println("Stopping JFXServer...");
                JFXServer.stop();
                //
                System.out.println("Exit.");
            } else {
                multi = args[0].equals("multi");
                if (multi) {
                    _main2(args);
                } else {
                    if (args[0].equals("max")) {
                        load_max(args);
                    } else {
                        System.err.println("Unrecognized argument: " + args[0]);
                    }
                }
            }
        } else {
            _main(args);
        }
    }

    public static void test(String arg) {
        System.out.println("test: " + arg);
    }

    public static void maintest(String[] args) throws IOException, InterruptedException {
        System.out.println("maintest: 1");
        System.setOut(new PrintStream(new FileOutputStream("logs/java.out"), true));
        System.setErr(new PrintStream(new FileOutputStream("logs/java.err"), true));
        System.out.println("maintest: 2");
        JFXServer.getInstance();
        System.out.println("maintest: 3");
    }

    public static void shutdown() {
        System.out.println("shutdown: 1");
        JFXServer.stop();
        //System.exit(0);
        System.out.println("shutdown: 2");
    }

    public static void _main(String[] args) throws IOException, InterruptedException {
        final JFXExample jfxExample1 = new JFXExample();
        //
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                try {
                    System.out.println("MT4 Terminal client disconnect...");
                    jfxExample1.disconnect();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        //
        System.out.println("You may kill terminal.exe to test reconnection...");
        jfxExample1.setReconnect(true);
        //
        String terminalServerIpAddress = "127.0.0.1";
        //
        jfxExample1.connect(terminalServerIpAddress, 7788, DemoAccount.MT_4_SERVER, DemoAccount.MT_4_USER, DemoAccount.MT_4_PASSWORD);
        jfxExample1.addTerminal(jfxExample1.getSymbol(), new TickListener() {
            @Override
            public void onTick(TickInfo tick, MT4 connection) {
                System.out.println(jfxExample1.getSymbol() + ": " + tick);
            }
        }).connect();
        jfxExample1.addTerminal("EURJPY", new TickListener() {
            @Override
            public void onTick(TickInfo tick, MT4 connection) {
                System.out.println("EURJPY: " + tick);
            }
        }).connect();
        jfxExample1.addTerminal(new TimerListener() {
            @Override
            public void onTimer(MT4 connection) {
                System.out.println("Time to eat: " + connection.accountProfit());
            }
        }, 5000).connect();
        jfxExample1.addTerminal(TerminalType.ORDERS_WORKER).connect();
        //
        System.out.println("1-minute example ...");
        Thread.sleep(60000);
        //
        System.out.println("Exit.");
        System.exit(0);
    }

    public static void _main2(String[] args) throws IOException, InterruptedException {
        laTestDone = true;
        //
        Document doc = DOMUtil.getDocument("alpari-uk-demo-100.xml");
        Element master_accounts = DOMUtil.findElement(doc, "master_accounts");
        Iterator accs = DOMUtil.getTopElements(master_accounts, "account");
        ExecutorService threads = java.util.concurrent.Executors.newFixedThreadPool(10);
        final ArrayList conns = new ArrayList();
        while (accs.hasNext()) {
            final Element acc = (Element) accs.next();
            threads.submit(new Runnable() {
                @Override
                public void run() {
                    JFXExample s = new JFXExample();
                    try {
                        s.connect("localhost", 7788, new Broker(acc.getAttribute("server")), acc.getAttribute("account"), acc.getAttribute("password"));
/*
                        Terminal mt = s.addTerminal(TerminalType.FREE_WORKER);
                        s.addTerminal(TerminalType.FREE_WORKER, new TimerListener() {public void onTimer(Terminal t){}}, 100);
                        s.addTerminal(TerminalType.ORDERS_WORKER);
                        Terminal mt2 = s.addTerminal("EURUSD", new TickListener() {public void onTick(Terminal t, TickInfo ti){}});
                        mt2.getTerminalType() == TerminalType.TICK_WORKER;
*/
                        //
                        conns.add(s);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        //
        int c = 0;
        while (c++ < 100) {
            Thread.sleep(60000);
            for (int i = 0; i < conns.size(); i++) {
                JFXExample s = (JFXExample) conns.get(i);
                System.out.println("Strategy #" + i + ", acc=" + s.accountName() + ", bal=" + s.accountBalance());
            }
            System.out.println("Connected " + conns.size() + " accounts.");
        }
        //
        System.out.println("Exit.");
        System.exit(0);
    }

    private static int tCount;
    private static boolean tSilent;
    public static void load_max(String[] args) throws IOException, InterruptedException {
        laTestDone = true;
        //
        String terminalServerIpAddress = "127.0.0.1";
        //                  
        String[][] accPass = {{DemoAccount.MT_4_USER, DemoAccount.MT_4_PASSWORD}};
        final Strategy[] s = {new Strategy()};
        for (int i = 0; i < s.length; i++) {
            s[i].connect(terminalServerIpAddress, 7788, DemoAccount.MT_4_SERVER, accPass[i][0], accPass[i][1]);
        }
        ExecutorService threads = java.util.concurrent.Executors.newFixedThreadPool(1);
        final ArrayList<Terminal> created = new ArrayList<Terminal>();
        final int[] err = new int[1];
        final ArrayList<Future> futures = new ArrayList<Future>();
        while (err[0] == 0 && futures.size() < 64) {
            futures.add(threads.submit(new Runnable() {
                int no = futures.size();
                @Override
                public void run() {
                    Terminal t = null;
                    int retry = 0;
                    try {
                        Thread.sleep(5000);
                        if (t == null) {
                            t = s[no % s.length].addTerminal(new TimerListener() {
                                @Override
                                public void onTimer(MT4 connection) {
                                    String id = ((TerminalStrategy) connection).getTerminal().getId();
                                    double v = connection.accountProfit();
                                    if (tSilent) return;
                                    System.out.print(" [" + id + "]");
                                    if ((++tCount) % 10 == 0) {
                                        System.out.println();
                                    } else {
                                        System.out.flush();
                                    }
                                }
                            }, 30000);
                        }
                        t.connect();
                    } catch (Exception e) {
                        if (++retry > 2) {
                            e.printStackTrace();
                            err[0]++;
                            return;
                        }
                    }
                    synchronized (created) {
                        created.add(t);
                    }
                }
            }));
        }
        //
        while (true) {
            int cnt = 0;
            for (Future f : futures) {
                cnt += (f.isDone() || f.isCancelled() ? 1 : 0);
            }
            if (futures.size() == cnt) {
                tSilent = true;
                Thread.sleep(1000);
                break;
            } else if (err[0] > 0) {
                System.out.println("Created: " + created.size() + ", errors=" + err[0]);
                Thread.sleep(30000);
            } else {
                Thread.sleep(1000);
            }
        }
        //
        System.out.println("Created: " + created.size());
        //
        for (int i = 0; i < s.length; i++) {
            //s[i].close();
        }
        //
        System.out.println("Exit.");
        System.exit(0);
    }
}
