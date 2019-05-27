package com.future.trade;import com.jfx.AppliedPrice;
import com.jfx.ErrUnknownSymbol;
import com.jfx.MarketInfo;
import com.jfx.MovingAverageMethod;
import com.jfx.strategy.Strategy;
import com.jfx.strategy.StrategyRunner;

import java.io.IOException;

/*
An output produced for me -

19 10:12:54 | pool-1-thread-1 | INFO  | Client EURUSD.240.2088518006/PrimeXM Prime Exchange Markets@AE1DB206C2F3B4D3A8B2638A607BA3ED1 connected.
accountServer=PrimeXM-DemoUK
**** CALLED FROM COORDINATE *****
----------- MA (13, SMA, CLOSE) --------------
1.29056808 1.2904905 1.29042842 1.29037083 1.29032529 1.2902885 1.29026663 1.29025596 1.29023733 1.29021821
1.29020167 1.29017954 1.29015063 1.29013321 1.29010625 1.29009125 1.29009304 1.29008825 1.29009992 1.29011925
1.29016304 1.29020646 1.29023938 1.29027492 1.29032258 1.29037667 1.290456 1.29053246 1.29059983 1.29066438
1.29073946 1.29079821 1.29085621 1.29092004 1.2909595 1.29097467 1.29099179 1.29100288 1.29101142 1.29102454
1.29101633 1.29099246 1.29098029 1.29096683 1.29092158 1.2908785 1.29084575 1.29080925 1.29074621 1.29068925
1.29062629 1.29054988 1.29047379 1.29037663 1.29027958 1.29018492 1.29010933 1.2900325 1.28996004 1.289879
1.28979508 1.28970621 1.28963996 1.28957404 1.28953713 1.28950775 1.28950792 1.28949833 1.28948367 1.28947092
1.28944642 1.28943733 1.28944513 1.28946288 1.28948521 1.28950279 1.28954192 1.28956504 1.28958783 1.28962213
1.28966504 1.28971808 1.28975067 1.28975763 1.28976154 1.28975833 1.28975479 1.28973463 1.28969975 1.28966696
1.289635 1.28960979 1.28958754 1.28956763 1.28955833 1.2895435 1.28951175 1.28947975 1.28944567 1.28941638
--------- End Of MA (13, SMA, CLOSE) -----------
**** CALLED FROM MAIN *****
----------- MA (13, SMA, CLOSE) --------------
1.29056808 1.2904905 1.29042842 1.29037083 1.29032529 1.2902885 1.29026663 1.29025596 1.29023733 1.29021821
1.29020167 1.29017954 1.29015063 1.29013321 1.29010625 1.29009125 1.29009304 1.29008825 1.29009992 1.29011925
1.29016304 1.29020646 1.29023938 1.29027492 1.29032258 1.29037667 1.290456 1.29053246 1.29059983 1.29066438
1.29073946 1.29079821 1.29085621 1.29092004 1.2909595 1.29097467 1.29099179 1.29100288 1.29101142 1.29102454
1.29101633 1.29099246 1.29098029 1.29096683 1.29092158 1.2908785 1.29084575 1.29080925 1.29074621 1.29068925
1.29062629 1.29054988 1.29047379 1.29037663 1.29027958 1.29018492 1.29010933 1.2900325 1.28996004 1.289879
1.28979508 1.28970621 1.28963996 1.28957404 1.28953713 1.28950775 1.28950792 1.28949833 1.28948367 1.28947092
1.28944642 1.28943733 1.28944513 1.28946288 1.28948521 1.28950279 1.28954192 1.28956504 1.28958783 1.28962213
1.28966504 1.28971808 1.28975067 1.28975763 1.28976154 1.28975833 1.28975479 1.28973463 1.28969975 1.28966696
1.289635 1.28960979 1.28958754 1.28956763 1.28955833 1.2895435 1.28951175 1.28947975 1.28944567 1.28941638
--------- End Of MA (13, SMA, CLOSE) -----------
Exit.
MT4 Terminal client disconnect...
19 10:12:55 |   Thread-0 | INFO  | Close method called: AE1DB206C2F3B4D3A8B2638A607BA3ED
Disconnected from the target VM, address: '127.0.0.1:55404', transport: 'socket'

Process finished with exit code 0

 */
public class IndicatorsExample extends Strategy {
    public IndicatorsExample() {
    }

    @Override
    public synchronized void init(String symbol, int period, StrategyRunner strategyRunner) throws ErrUnknownSymbol, IOException {
        super.init(symbol, period, strategyRunner);
        System.out.println("Connected, " + getVersion());
        setAutoRefreshRates(false);
    }

    boolean areIndicatorsDone;
    public void coordinate() {
        if (!areIndicatorsDone) {
            calcIndicators("**** CALLED FROM COORDINATE *****");
            areIndicatorsDone = true;
        }
    }

    private void calcIndicators(String msg) {
        try {
//            iCustom(getSymbol(), Timeframe.PERIOD_H1, "ZigZag", 0, 0, 12, 5, 3);
            //
            String s = "#CLF3";
            boolean contains = getSymbols().contains(new Instrument(s, s, s));
            if (contains) {
                double ask = marketInfo(s, MarketInfo.MODE_ASK);
            }
            System.out.println(msg);
            double[] movingAverages = new double[100];
            for (int i = 0; i < movingAverages.length; i++) {
                movingAverages[i] = iMA(getSymbol(), getTimeframe(), getPeriod(),
                        13, MovingAverageMethod.MODE_SMA, AppliedPrice.PRICE_CLOSE,
                        i);
            }
            //
            System.out.println("----------- MA (13, SMA, CLOSE) --------------");
            for (int i = 0; i < movingAverages.length; i++) {
                System.out.print("" + movingAverages[i] + " ");
                if (i > 0 && (i+1) % 10 == 0) {
                    System.out.println();
                }
            }
            System.out.println("--------- End Of MA (13, SMA, CLOSE) -----------");
        } catch (ErrUnknownSymbol errUnknownSymbol) {
            errUnknownSymbol.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        System.setProperty("jfx_activation_key", "235961853");
//        System.setProperty("jfx_server_port", "17342");
        connectToMT4Server();
        //
        System.out.println("Exit.");
        System.exit(0);
    }

    private static void connectToMT4Server() throws IOException, InterruptedException {
        final IndicatorsExample mt4 = new IndicatorsExample();
        //
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                try {
                    System.out.println("MT4 Terminal client disconnect...");
                    mt4.disconnect();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        mt4.setReconnect(true);
        String terminalServerIpAddress = "127.0.0.1";
        //
        mt4.connect(terminalServerIpAddress, 7788, DemoAccount.MT_4_SERVER, DemoAccount.MT_4_USER, DemoAccount.MT_4_PASSWORD);
        //
        while (!mt4.areIndicatorsDone) {
            Thread.sleep(100);
        }
        mt4.calcIndicators("**** CALLED FROM MAIN *****");
    }
}
