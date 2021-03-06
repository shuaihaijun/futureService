package com.future.config.zeromq;

import com.alibaba.fastjson.JSONObject;
import com.future.common.constants.FollowConstant;
import org.springframework.util.StringUtils;
import org.zeromq.SocketType;
import org.zeromq.ZMQ;
import org.zeromq.ZContext;

import java.math.BigDecimal;
import java.util.Date;

public class hwserver
{
    public static void main(String[] args) throws Exception
    {

        try (ZContext context = new ZContext()) {

            //发布订阅模式服务端
            ZMQ.Socket pullSocket=context.createSocket(SocketType.PULL);
            ZMQ.Socket pubSocket1 = context.createSocket(SocketType.PUB);
            ZMQ.Socket pubSocket2=context.createSocket(SocketType.PUB);
            ZMQ.Socket pubSocket3=context.createSocket(SocketType.PUB);

            /*信号源初始化数据*/
          /*  String signalInit = accountInfo+"|"+ FollowConstant.SIGNAL_INIT+"|"+siganlHost;
            String followInit = subMsg+"|"+followinit+"|"+serverHost+":"+serverPort+":"+followArray+"|"+customerServer+"&"+customerAccount;
            String followOrder= subMsg+"|"+trade+"|"+signalServer+"&"+signalAccount+"|"+followTradeOrder;*/

            Boolean recived=false;
            String hostUrl="127.0.0.1:31111";
            String hostUr2="127.0.0.1:30222";

            pubSocket1.bind("tcp://"+hostUrl);
            pubSocket2.bind("tcp://"+hostUr2);
            pubSocket3.bind("tcp://127.0.0.1:32777");
            // 2 初始化跟随者  accountInfo|FOLLOWINIT|serverHost:serverPort#followHost:followPort#signalArray#followOrderList|ruleList(signalAccount:ruleType:ruleAmount:limitUpper)
            String reciveMsg1="MultiBankFXInt-Demo F&2102272054|FOLLOWINIT|127.0.0.1:32777#127.0.0.1:33888#127.0.0.1:30222:MultiBankFXInt-Demo1 F&2102272054#131623255:131623190;131565351:131565349|MultiBankFXInt-Demo1 F&2102272054:0:0.2";
            String reciveMsg2="MultiBankFXInt-Demo1 F&2102272054|SIGNALFOLLOWORDER|CLOSE_PARTIAL|1,EURUSD,1.28508000,0.00000000,0.00000000,to #131623225,0.01000000,0.00000000,131623190,1573632151,0.00000000,0,131623225";
            //String reciveMsg3="MultiBankFXInt-Demo1 F&2102272054|SIGNALFOLLOWORDER|CLOSE|0,EURUSD,1.28331000,0.00000000,0.00000000,to #131623225,0.01000000,0.00000000,131623225,1573551136,0.00000000,0";
            //MultiBankFXInt-Demo F&2102272054|FOLLOWINIT|127.0.0.1:32777#127.0.0.1:33888#127.0.0.1:30222:MultiBankFXInt-Demo1 F&2102272054#131612110:131621438;131565351:131565349|MultiBankFXInt-Demo1 F&2102272054:0:0.2
            //MultiBankFXInt-Demo1 F&2102272054|SIGNALFOLLOWORDER|OPEN|0,USDJPY,108.62100000,0.00000000,0.00000000,,0.03000000,0.00000000,131565400,1572512480,0.00000000,0
//          signalOrder:MultiBankFXInt-Demo F&2102272054|SIGNALFOLLOWORDER|CLOSE|0,GBPUSD,1.28331000,0.00000000,0.00000000,to #131617841,0.01000000,0.00000000,131622866,1573551136,0.00000000,0
//          signalOrder:MultiBankFXInt-Demo F&2102272054|SIGNALFOLLOWORDER|CLOSE_PARTIAL|1,GBPUSD,1.28508000,0.00000000,0.00000000,to #131622866,0.01000000,0.00000000,131621438,1573632151,0.00000000,0,131622866
//            TRADE|OPEN|0|USDJPY|108.62100000|0.00000000|0.00000000||0.006|0.00000000|131565400
//            MultiBankFXInt-Demo F&2102272054|MultiBankFXInt-Demo1 F&2102272054|OPEN|{'_action': 'EXECUTION', '_response': '131', 'response_value': 'invalid trade volume'}
//            MultiBankFXInt-Demo F&2102272054|MultiBankFXInt-Demo1 F&2102272054|OPEN|{'_action': 'EXECUTION', '_magic': 0, '_ticket': 131612110, '_open_time': '2019.11.11 10:39:35', '_open_price': 108.98800000}


            int i=0;
            while (i<3){
                if(!recived){
                    // 第一次可能不成功
                    System.out.println("`````````sending FOLLOWINIT--:"+reciveMsg1);
                    pubSocket3.send(reciveMsg1,0);
                    Thread.sleep(10000);
                    System.out.println("`````````sending FOLLOWINIT--:"+reciveMsg1);
                    pubSocket3.send(reciveMsg1,0);
                    recived=true;
                }else {
                    System.out.println("`````````sending SIGNALFOLLOWORDER--:"+reciveMsg2);
                    pubSocket3.send(reciveMsg2,0);
                    Thread.sleep(10000);
                    //System.out.println("`````````sending SIGNALFOLLOWORDER--:"+reciveMsg2);
                    //pubSocket3.send(reciveMsg3,0);
                }
                Thread.sleep(10000);
            }
        }
    }
}