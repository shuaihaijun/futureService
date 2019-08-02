package com.future.config.zeromq;

import org.zeromq.SocketType;
import org.zeromq.ZMQ;
import org.zeromq.ZContext;

import java.util.Date;

public class hwserver
{
    public static void main(String[] args) throws Exception
    {

        try (ZContext context = new ZContext()) {
            // Socket to talk to clients
            /*ZMQ.Socket socket = context.createSocket(SocketType.REP);
            socket.bind("tcp://*:7777");
            while (!Thread.currentThread().isInterrupted()) {
                byte[] reply = push.recv(0);
                System.out.println(
                        "Received " + ": [" + new String(reply, ZMQ.CHARSET) + "]"
                );

                String response = "world";
                push.send(response.getBytes(ZMQ.CHARSET), 0);

//                Thread.sleep(1000); //  Do some 'work'
            }*/

            //推拉模式服务端
            ZMQ.Socket push = context.createSocket(SocketType.PUSH);
            push.connect("tcp://localhost:6666");


            System.out.println("[PULL] Unbinding MT4 Server from Socket on Port:"+"6666");

            while (!Thread.currentThread().isInterrupted()) {

                String serverSend = "pull server send!";
                System.out.println(serverSend+"  :"+new Date().getTime());
                push.send(serverSend,0);

                Thread.sleep(1000);
            }
        }
    }
}