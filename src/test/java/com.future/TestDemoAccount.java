package com.future;

import com.jfx.Broker;

public class TestDemoAccount {
    public final static Broker MT_4_SERVER = new Broker("USGFX-Demo");
    public final static String MT_4_USER = "1100531439";
    public final static String MT_4_PASSWORD = "4xgrrfh";

    public static final String JFX_ACTIVATION_KEY = System.getProperty("nj4x_activation_key", "209083576");  //box=825604286 -> 209083576
    public static final String NJ4X_MT5_ACTIVATION_KEY = System.getProperty("nj4x_mt5_activation_key", "3542556695");// box=825604286 -> 3542556695

    static {
//        System.setProperty("nj4x_server_host", "109.197.217.248");
        System.setProperty("nj4x_server_port", "7744");
        System.setProperty("nj4x_activation_key", JFX_ACTIVATION_KEY);
        System.setProperty("nj4x_mt5_activation_key", NJ4X_MT5_ACTIVATION_KEY);
    }
}
