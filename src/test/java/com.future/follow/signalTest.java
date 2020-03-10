package com.future.follow;


import com.future.service.bak.BakFollowService;
import com.future.service.bak.BakSignalService;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

@RunWith(SpringRunner.class)
@SpringBootTest
@Ignore
public class signalTest {

    @Autowired
    BakSignalService bakSignalService;
    @Autowired
    BakFollowService bakFollowService;

    @Test
    public void testSignalInit(){
        String siganlHost="127.0.0.1:33777";
        String serverId="MultiBankFXInt-Demo F";
        String accountId="2102272054";

        //初始化信号源
        /*boolean isInit= signalService.signalInit(siganlHost,serverId,accountId);

        if(!isInit){
            System.out.println("signalInit failed!");
        }else {
            System.out.println("signalInit success!");
        }*/


        /*Map signalOrderMsg = signalService.monitorSignal(hostUrl);

        String signalAccount= String.valueOf(signalOrderMsg.get("signalAccount"));
        String action=String.valueOf(signalOrderMsg.get("action"));
        String signalOrder=String.valueOf(signalOrderMsg.get("signalOrder"));

        System.out.println(signalAccount);
        System.out.println(action);
        System.out.println(signalOrder);*/
    }

    @Test
    public void testFollowInit(){
        String hostUrl="127.0.0.1:32777";
        String followUrl="127.0.0.1:33888";
        String serverId="MultiBankFXInt-Demo F";
        String accountId="2102272054";
        String userId="";
        /*boolean isSuccess=followService.followInit(userId,hostUrl,followUrl,serverId,accountId);
        if(!isSuccess){
            System.out.println("Init failed!");
        }else {
            System.out.println("Init success!");
        }*/

        String signalUrl="127.0.0.1:30222";
        //跟随者
        try (ZContext context = new ZContext()) {
            ZMQ.Socket pubSocket = context.createSocket(SocketType.PUB);
            pubSocket.bind("tcp://"+signalUrl);
            int i=0;
            while (i<5){
                String reciveMsg="MultiBankFXInt-Demo F&2102272054|SIGNALFOLLOWORDER|OPEN|0,USDJPY,108.62100000,0.00000000,0.00000000,,0.03000000,0.00000000,131565400,1572512480,0.00000000,0";
                // 请求100*5次
                System.out.println("send order msg:"+reciveMsg);
                pubSocket.send(reciveMsg,0);
                i++;
                Thread.sleep(5000);
            }

        }catch (Exception e){
            System.out.println("wrrrrrrrrrrrrrrrrroy");
        }

        //MultiBankFXInt-Demo F&2102272054|SIGNALFOLLOWORDER|OPEN|0,USDJPY,108.62100000,0.00000000,0.00000000,,0.03000000,0.00000000,131565400,1572512480,0.00000000,0
        //MultiBankFXInt-Demo F&2102272054|SIGNALFOLLOWORDER|OPEN|1,EURAUD,1.61436000,0.00000000,0.00000000,,0.03000000,0.00000000,131565401,1572512497,0.00000000,0

    }

}
