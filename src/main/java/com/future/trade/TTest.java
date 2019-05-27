package examples;

import com.jfx.*;
import com.jfx.strategy.Strategy;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;


class CreateConnectAccount extends Strategy {
    @Override
    public void coordinate() {
    }

    public void createConnectAccount(String s, String s1, String qam6pqe, String eurusd) {
        try {
            connect("localhost", 7788, new Broker(s), s1, qam6pqe, eurusd);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

public class TTest {
    public static void main1(String[] args) {
        ExecutorService threads = java.util.concurrent.Executors.newCachedThreadPool();

        final CreateConnectAccount[] up = new CreateConnectAccount[]{
                new CreateConnectAccount() {
                    {createConnectAccount("MMCIS-Demo", "1816269", "qam6pqe", "EURUSD");}
                },
                new CreateConnectAccount() {
                    {createConnectAccount("Activtrades-Demo", "432060", "n6niita", "EURUSD");}
                },
        };

        threads.submit(new Runnable() {
            public void run() {
                up[0].accountBalance();
            }
        });
        threads.submit(new Runnable() {
            public void run() {
                up[1].accountBalance();
            }
        });
    }
    public static void main2(String[] args) {
        ExecutorService threads = java.util.concurrent.Executors.newCachedThreadPool();

        final CreateConnectAccount[] up = new CreateConnectAccount[]{
                new CreateConnectAccount() {
                    @Override
                    public void coordinate() {
                        // eto zapustitsa posle connekta
                        if (accountEquity() == 1000000) {
                            System.exit(0);
                        }
                        ///...
                    }
                },
                new CreateConnectAccount() {
                    @Override
                    public void coordinate() {
                        // eto zapustitsa posle connekta
                        try {
                            if (accountEquity() < 1000000 && ordersTotal() < 3) {
                                orderSend(getSymbol(),
                                        TradeOperation.OP_BUY,
                                        marketInfo(getSymbol(), MarketInfo.MODE_ASK),
                                        2, 0, 0, marketInfo(getSymbol(), MarketInfo.MODE_ASK)
                                        +100*marketInfo(getSymbol(), MarketInfo.MODE_POINT), null, 0, null, 0
                                        ) ;
                            }
                            ///...
                        } catch (MT4Exception err) {
                            //oops
                            err.printStackTrace();
                        }
                    }
                },
        };

        threads.submit(new Runnable() {
            public void run() {
                up[0].createConnectAccount("MMCIS-Demo", "1816269", "qam6pqe", "EURUSD");
            }
        });
        threads.submit(new Runnable() {
            public void run() {
                up[1].createConnectAccount("Activtrades-Demo", "432060", "n6niita", "EURUSD");
            }
        });
    }
    public static void main(String[] args) {
        ExecutorService threads = java.util.concurrent.Executors.newCachedThreadPool();
        //
        final CreateConnectAccount[] up = new CreateConnectAccount[]{
                new CreateConnectAccount(),
                new CreateConnectAccount()
        };
        //
        threads.submit(new Runnable() {
            public void run() {
                up[0].createConnectAccount("MMCIS-Demo", "1816269", "qam6pqe", "EURUSD");
            }
        });
        threads.submit(new Runnable() {
            public void run() {
                up[1].createConnectAccount("Activtrades-Demo", "432060", "n6niita", "EURUSD");
            }
        });
        //
        if (up[0].isAlive()) {
            up[0].accountBalance();
        }
        threads.submit(new Runnable() {
            public void run() {
                if (up[0].isAlive()) {
                    up[0].accountBalance();
                }
            }
        });
        Future future = threads.submit(new Runnable() {
            @Override
            public void run() {
                if (up[0].isAlive()) {
                    System.out.println("--------- 1");

                } else {
                    try {

                        System.out.println("--------- 2" + up[0].accountCompany()); // почему эта строчка не работает(просто ничего не выводит из if() )? без up[0].accountCompany() работает.
                        Thread.sleep(10000);
                        run();
                    } catch (RuntimeException err) {
                        err.printStackTrace();
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
        future.isDone();
    }
}
