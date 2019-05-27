package com.future.trade.jboss.ejbModule.t;

import java.io.IOException;
import java.util.Date;

import javax.annotation.Resource;
import javax.ejb.Remote;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.Timeout;
import javax.ejb.Timer;

import com.jfx.Broker;
import com.jfx.MT4Exception;
import com.jfx.MarketInfo;
import com.jfx.SelectionPool;
import com.jfx.SelectionType;
import com.jfx.TradeOperation;
import com.jfx.jboss.JFXService;

@Stateless
@Remote(ExampleTimer.class)
public class ExampleTimerBean implements ExampleTimer {
	private @Resource
	SessionContext ctx;

	public void scheduleTimer(long milliseconds) {
		ctx.getTimerService().createTimer( new Date(new Date().getTime() + milliseconds), "Hello JFX");
		System.out.println("---------------------");
		System.out.println("Created a timer event to be triggered after "
				+ milliseconds / 1000L + " seconds");
		System.out.println("---------------------");
	}

	@Timeout
	public void timeoutHandler(Timer timer) {
		System.out.println("---------------------");
		System.out.println("* Received Timer event: " + timer.getInfo());
		//
		JFXService mt4 = (JFXService) ctx.lookup("MT4Service/local");
		Integer session = getSession(mt4);
		//
		System.out.println("MT4Service: " + mt4 + ", accBalance="
				+ mt4.accountBalance(session));
		System.out.println("---------------------");

		System.out.println("MT4Service.getAttribute, accBal="
				+ mt4.accountBalance(session));
		try {
			int orders = mt4.ordersTotal(session);
			// MarketInfo mode = MarketInfo.MODE_BID;
			// TradeOperation op = TradeOperation.OP_SELL;
			MarketInfo mode = MarketInfo.MODE_ASK;
			TradeOperation op = TradeOperation.OP_BUY;
			if (orders == 0) {
				int order = mt4.orderSend(session, "GBPUSD", op, 1,
						mt4.marketInfo(session, "GBPUSD", mode), 1, 0, 0,
						"test", 0, null, 0);
				System.out.println("New Order: #" + order + ", op=" + op);
				scheduleTimer(60000);
			} else {
				if (mt4.orderSelect(session, 0, SelectionType.SELECT_BY_POS,
						SelectionPool.MODE_TRADES)) {
					int orderTicket = mt4.orderTicket(session);
					if (mt4.orderProfit(session) > 0
							|| mt4.orderProfit(session) < -100) {
						System.out.println("orderTicket=" + orderTicket);
						double ask = mt4
								.marketInfo(
										session,
										"GBPUSD",
										mt4.orderType(session) == TradeOperation._OP_SELL ? MarketInfo.MODE_ASK
												: MarketInfo.MODE_BID);
						System.out.println("Closing order, ask=" + ask);
						mt4.orderClose(session, orderTicket, 1, ask, 1, 0);
						System.out.println("Order closed: #" + orderTicket);
						System.out.println("accBal="
								+ mt4.accountBalance(session));
					} else {
						System.out.println("Order #" + orderTicket + " profit="
								+ mt4.orderProfit(session));
					}
					scheduleTimer(5000);
				}
			}
		} catch (MT4Exception e) {
			e.printStackTrace();
		}

		timer.cancel();
	}

	private Integer getSession(JFXService jfx) {
		Broker broker = new Broker("AlpariUS-Demo");
		String user = "1234376";
		Integer session = jfx.getSessionID(broker, user);
		if (session == null) {
			try {
				session = jfx.connect("127.0.0.1", 7788, broker, user,
						"ihep4ei");
			} catch (IOException e1) {
				throw new RuntimeException(e1);
			}
		}
		return session;
	}
}
