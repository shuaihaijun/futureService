package com.future.trade;

import com.jfx.ErrUnknownSymbol;
import com.jfx.MarketInfo;
import com.jfx.strategy.StrategyRunner;

import java.io.IOException;

public class MyStrategy extends com.jfx.strategy.Strategy{
    public void init(String symbol, int period, StrategyRunner strategyRunner) {
        try {
            System.out.println("init method");
            super.init(symbol, period, strategyRunner);
        } catch (ErrUnknownSymbol e) {

            e.printStackTrace();
        } catch (IOException e) {

            e.printStackTrace();
        }
        //
        // load existing orders, recover itself from the previous shutdown
        //
    }
    public void deinit() {
        // release resources on EA exit
        System.out.println("deinit method");
    }
    public void coordinate() {
        // trading logic goes here
		/* make use of all API methods: accountBalance, accountCompany, accountCredit, accountCurrency, accountEquity,
		accountFreeMargin, accountMargin, accountName, accountNumber, accountProfit, comment, day, dayOfWeek, dayOfYear,
		getLastError, getTickCount, hour, iAC, iAD, iADX, iAlligator, iAO, iATR, iBands, iBars, iBarShift, iBearsPower, iBullsPower,
		iBWMFI, iCCI, iClose, iCustom, iDeMarker, iEnvelopes, iForce, iFractals, iGator, iHigh, iHighest, iLow, iLowest, iMA, iMACD,
		iMFI, iMomentum, iOBV, iOpen, iOsMA, iRSI, iRVI, iSAR, isConnected, isDemo, iStdDev, isTesting, iStochastic,
		isTradeContextBusy, isVisualMode, iTime, iVolume, iWPR, marketInfo, minute, month, objectCreate, objectCreate, objectCreate,
		objectDelete, objectGet, objectGetFiboDescription, objectSet, objectSetFiboDescription, objectSetText, objectsTotal, objectType,
		orderClose, orderCloseBy, orderClosePrice, orderCloseTime, orderComment, orderCommission, orderDelete, orderExpiration,
		orderLots, orderMagicNumber, orderModify, orderOpenPrice, orderOpenTime, orderPrint, orderProfit, orderSelect, orderSend,ordersHistoryTotal, orderStopLoss, ordersTotal, orderSwap, orderSymbol, orderTakeProfit, orderTicket, orderType, print,
		refreshRates, seconds, timeCurrent, year
		*/try{
            System.out.println("coordinate method");
            //double point = marketInfo("EURUSD", MarketInfo.MODE_POINT);
            double price = marketInfo(getSymbol(), MarketInfo.MODE_ASK);
            /*double buyPrice = price;
            int ticket = orderSend(
                    "EURUSD",
                    TradeOperation.OP_BUY,
                    1,
                    buyPrice,
                    2,
                    price - 100 * point,
                    price + 100 * point,
                    "" + System.currentTimeMillis(),
                    0,
                    new Date(System.currentTimeMillis() + 60 * 60 * 1000),
                    -1
            );*/
            System.out.println("---------------------------------------");
            System.out.println("Buy order ticket: " + price);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}