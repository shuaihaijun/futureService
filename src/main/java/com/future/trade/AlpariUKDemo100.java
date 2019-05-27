package com.future.trade;

import com.jfx.Broker;
import com.jfx.MarketInfo;
import com.jfx.strategy.Strategy;
import com.jfx.xml.DOMUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;

public class AlpariUKDemo100 extends Strategy {
    long start = 0;

    @Override
    public void coordinate() {
        try {
            long d = System.currentTimeMillis() - start;
            if (start != 0 && d > 4500) {
                System.out.println("Unexpected coordination delay of " + (d / 1000) + " seconds, account=" + getMt4User());
            }
            start = System.currentTimeMillis();
            //
            @SuppressWarnings("UnusedDeclaration")
            String data = getMt4User() + "> "
                    + marketInfo_MODE_TIME("AUDUSD") + "> "
                    + marketInfo("EURUSD", MarketInfo.MODE_BID);
//            System.out.println(data);
        } catch (Exception err) {
            err.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Document doc = DOMUtil.getDocument("alpari-uk-demo-100.xml");
        Element master_accounts = DOMUtil.findElement(doc, "master_accounts");
        //
        ExecutorService threads = java.util.concurrent.Executors.newFixedThreadPool(4);
        final ArrayList mt4Connections = new ArrayList();
        int submitted = 0;
        Iterator accounts = DOMUtil.getTopElements(master_accounts, "account");
        //noinspection LoopStatementThatDoesntLoop
        while (accounts.hasNext()) {
            final Element acc = (Element) accounts.next();
            threads.submit(new Runnable() {
                @Override
                public void run() {
                    AlpariUKDemo100 s = new AlpariUKDemo100();
                    IOException err = null;
                    for (int i = 0; i < 3; i++) {
                        try {
                            s.connect("localhost", 7788,
                                    new Broker(acc.getAttribute("server")),
                                    acc.getAttribute("account"),
                                    acc.getAttribute("password")
                            );
                            synchronized (mt4Connections) {
                                //noinspection unchecked
                                mt4Connections.add(s);
                                if (mt4Connections.size() % 5 == 0) {
                                    System.out.println("" + new Date()
                                            + "> connections made: " + mt4Connections.size());
                                }
                            }
                            err = null;
                            break;
                        } catch (IOException e) {
                            err = e;
                        }
                    }
                    if (err != null) {
                        new RuntimeException(
                                acc.getAttribute("account")
                                        + " connection error", err).printStackTrace();
                    }
                }
            });
            submitted++;
            if (submitted == 1) {
                break;
            }
        }
        //
        long start = System.currentTimeMillis();
        System.out.println("" + new Date() + "> " + submitted + " connection jobs have been started.");
        //
        while (mt4Connections.size() < submitted) {
            Thread.sleep(5000);
        }
        //
        System.out.println("" + new Date() + "> " + mt4Connections.size() + " connections have been established in " + (System.currentTimeMillis() - start) / 1000L + " seconds.");
        Thread.sleep(Long.MAX_VALUE);
    }
}
