package com.future;

import com.jfx.Broker;

public class TestDemoAccount {
    public final static Broker MT_4_SERVER = new Broker("5*91.109.206.235:443");
    public final static String MT_4_USER = "9007162";
    public final static String MT_4_PASSWORD = "ibiq4mnf";

    public static final String JFX_ACTIVATION_KEY = System.getProperty("nj4x_activation_key", "209083576");  //box=825604286 -> 209083576
    public static final String NJ4X_MT5_ACTIVATION_KEY = System.getProperty("nj4x_mt5_activation_key", "3542556695");// box=825604286 -> 3542556695

    static {
//        System.setProperty("nj4x_server_host", "109.197.217.248");
        System.setProperty("nj4x_server_port", "7744");
        System.setProperty("nj4x_activation_key", JFX_ACTIVATION_KEY);
        System.setProperty("nj4x_mt5_activation_key", NJ4X_MT5_ACTIVATION_KEY);
    }
}
