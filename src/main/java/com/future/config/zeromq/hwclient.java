package com.future.config.zeromq;

import org.zeromq.SocketType;
import org.zeromq.ZMQ;
import org.zeromq.ZContext;


public class hwclient
{
    public static void main(String[] args)
    {
        try (ZContext context = new ZContext()) {
            //  Socket to talk to server
            System.out.println("Connecting to hello world client");

            /*ZMQ.Socket socket = context.createSocket(SocketType.REQ);
            socket.connect("tcp://localhost:7777");

            for (int requestNbr = 0; requestNbr != 10; requestNbr++) {
                String request = "Hello";
                System.out.println(new Date().getTime());
                System.out.println("Sending Hello " + requestNbr);
                socket.send(request.getBytes(ZMQ.CHARSET), 0);

                byte[] reply = socket.recv(0);
                System.out.println(new Date().getTime());
                System.out.println(
                        "Received " + new String(reply, ZMQ.CHARSET) + " " +
                                requestNbr
                );
            }*/

            ZMQ.Socket pull=context.createSocket(SocketType.PULL);
            pull.connect("tcp://localhost:5555");
            while (!Thread.currentThread().isInterrupted()) {

                String ticks=pull.recvStr();
//                byte[] reply = pull.recv(0);
//                System.out.println("Received " + new String(reply, ZMQ.CHARSET));
                System.out.println("`````````"+ticks);
            }
        }
    }
}