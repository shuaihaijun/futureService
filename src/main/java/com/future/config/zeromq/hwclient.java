package com.future.config.zeromq;

import com.alibaba.druid.util.StringUtils;
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

            String test="a";

            String signalUrl="127.0.0.1:33777";
            String followUrl="127.0.0.1:33888";

            byte[] reply;
            System.out.println("`````````1");

            while (!Thread.currentThread().isInterrupted()){
//                socket2.connect("tcp://"+followUrl);
//                socket2.setReceiveTimeOut(10);
//                reply=socket2.recv(0);
//                if(reply!=null && reply.length>0){
//                    reciveMsg=new String(reply);
//                    System.out.println("followOrder:"+reciveMsg);
//                }
//                TRADE|CLOSE_PARTIAL|1|EURUSD|1.28508000|0.00000000|0.00000000|followEA.to #131623225|0.01|0.00000000|131623205

                socket2.connect("tcp://"+followUrl);
                socket2.setReceiveTimeOut(20);
                reply=socket2.recv(0);
                if(reply!=null && reply.length>0){
                    reciveMsg=new String(reply);
                    System.out.println("signalOrder:"+reciveMsg);
                }
                Thread.sleep(300);
            }
            System.out.println("`````````5");
        }catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

/*
        signalOrder:MultiBankFXInt-Demo F&2102272054|SIGNALFOLLOWORDER|OPEN|1,USDJPY,109.25000000,0.00000000,0.00000000,,0.10000000,0.00000000,131617041,1573543843,0.00000000,0
        signalOrder:MultiBankFXInt-Demo F&2102272054|SIGNALFOLLOWORDER|CLOSE_PARTIAL|0,GBPUSD,1.28468000,0.00000000,0.00000000,to #131617057,0.04000000,0.00000000,131617035,1573544021,0.00000000,0,131617035
        // from
        signalOrder:MultiBankFXInt-Demo F&2102272054|SIGNALFOLLOWORDER|CLOSE_PARTIAL|0,GBPUSD,1.28331000,0.00000000,0.00000000,from #131617517,0.02000000,0.00000000,131617841,0,0.00000000,0,131617841
        signalOrder:MultiBankFXInt-Demo F&2102272054|SIGNALFOLLOWORDER|CLOSE|0,GBPUSD,1.28331000,0.00000000,0.00000000,to #131617841,0.01000000,0.00000000,131617517,1573551136,0.00000000,0

        signalOrder:MultiBankFXInt-Demo F&2102272054|SIGNALFOLLOWORDER|CLOSE_PARTIAL|1,GBPUSD,1.28508000,0.00000000,0.00000000,to #131622866,0.01000000,0.00000000,131565234,1573632151,0.00000000,0,131622866

        signalOrder:MultiBankFXInt-Demo F&2102272054|MultiBankFXInt-Demo1 F&2102272054|CLOSE_PARTIAL|{'_action': 'CLOSE', '_ticket': 131623205, '_response': 'CLOSE_PARTIAL', '_close_price': 1.10146000, '_close_lots': 0.01000000}
        signalOrder:MultiBankFXInt-Demo F&2102272054|MultiBankFXInt-Demo1 F&2102272054|CLOSE|{'_action': 'CLOSE', '_ticket': 131623205, '_close_price': 1.10171000, '_close_lots': 1.00000000, '_response': 'CLOSE_MARKET', '_response_value': 'SUCCESS'}
        signalOrder:MultiBankFXInt-Demo F&2102272054|SIGNALFOLLOWORDER|OPEN|1,USDJPY,109.25000000,0.00000000,0.00000000,,0.10000000,0.00000000,131617041,1573543843,0.00000000,0

 */
    }
}