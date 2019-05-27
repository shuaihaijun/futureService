package com.future.trade.jboss.ejbModule;

import com.future.trade.jboss.ejbModule.t.ExampleTimer;

import javax.naming.InitialContext;


public class Main {
	public static void main(String[] args) throws Exception {
		InitialContext ctx = new InitialContext();
		ExampleTimer timer = (ExampleTimer) ctx
				.lookup("ExampleTimerBean/remote");
		timer.scheduleTimer(5000);
		System.out.println("Timer scheduled to trigger after 5 seconds");
	}
}