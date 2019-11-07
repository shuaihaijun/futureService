package com.future.common.util;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

/**
 * @author ：haijun
 * @date ：Created in 2019-10-15 15:36
 * @description：zero公共类
 * @modified By：
 * @version: 1.0$
 */
public class ZeroMqUtil {

    public static  ZContext context = new ZContext();
    public static ZMQ.Socket pubSocket = context.createSocket(SocketType.PUB);

    ZeroMqUtil(){
        pubSocket.bind("tcp://127.0.0.1:32777");
    }
}
