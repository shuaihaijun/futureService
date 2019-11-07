package com.future.config.zeromq;

import org.zeromq.SocketType;
import org.zeromq.ZMQ;
import org.zeromq.ZContext;

import java.util.Date;


public class hwclient
{
    public static void main(String[] args)
    {
        try (ZContext context = new ZContext()) {
            //  请求回应模式
            /*ZMQ.Socket socket = context.createSocket(SocketType.REQ);
            socket.connect("tcp://localhost:7777");
            int i=0;
            while (!Thread.currentThread().isInterrupted()) {
                String request = "Hello "+i++;
                System.out.println(request+"----"+new Date().getTime());
                socket.send(request.getBytes(ZMQ.CHARSET), 0);

                byte[] reply = socket.recv(0);
                System.out.println("Received: " + new String(reply, ZMQ.CHARSET)+"---"+new Date().getTime());

            }*/
            String ticks="";

            // pubMsg|{'orderAction':'CLOSE','orderType':'0','orderTicket':'131155058','orderSymbol':'EURUSD','orderLots':'0.01000000','orderCloseTime':'1568860584','orderClosePrice':'1.10336000','orderStopLoss':'0.00000000','orderTakeProfit':'0.00000000','orderMagicNumber':'0.00000000','orderComment':'comment','orderCommission':'0.00000000','orderExpiration':'0'
            //发布订阅客户端
            /*ZMQ.Socket subSocket=context.createSocket(SocketType.SUB);
            while (!Thread.currentThread().isInterrupted()){
                //订阅
                *//*subSocket.connect("tcp://127.0.0.1:32888");
//                subSocket.subscribe("abc");
                subSocket.subscribe("pubMsg");
                ticks=subSocket.recvStr();
//                byte[] reply = pull.recv(0);
//                System.out.println("Received " + new String(reply, ZMQ.CHARSET));
                System.out.println("`````````"+ticks);*//*

                //信号源
//                subSocket.connect("tcp://127.0.0.1:32772");
//                subSocket.subscribe("test");
//                ticks=subSocket.recvStr();
//                System.out.println("`````````"+ticks);
            }*/


            // 推拉模式
            ZMQ.Socket socket1=context.createSocket(SocketType.PUB);
            ZMQ.Socket socket2=context.createSocket(SocketType.PULL);
            ZMQ.Socket socket3=context.createSocket(SocketType.SUB);

            String reciveMsg="MultiBankFXInt-Demo F&2102272054|SIGNALFOLLOWORDER|OPEN|0,USDJPY,108.62100000,0.00000000,0.00000000,,0.03000000,0.00000000,131565400,1572512480,0.00000000,0";

            String hostUrl="127.0.0.1:32777";
            String followUrl="127.0.0.1:33888";

            byte[] reply;
            System.out.println("`````````1");

            while (!Thread.currentThread().isInterrupted()){
                socket2.connect("tcp://"+followUrl);
                socket2.setReceiveTimeOut(10);
                reply=socket2.recv(0);
                if(reply!=null && reply.length>0){
                    reciveMsg=new String(reply);
                    System.out.println(reciveMsg);
                }
            }
            System.out.println("`````````5");
        }catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}