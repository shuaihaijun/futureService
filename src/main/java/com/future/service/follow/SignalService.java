package com.future.service.follow;

import com.alibaba.fastjson.JSONObject;
import com.future.common.constants.FollowConstant;
import com.future.common.exception.BusinessException;
import com.jfx.strategy.Strategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

import java.util.HashMap;
import java.util.Map;

/**
 * 信号源处理逻辑
 */
@Service
public class SignalService extends Strategy {

    Logger log= LoggerFactory.getLogger(SignalService.class);
    @Autowired
    FollowService followService;


    /**
     * 初始化信号源(单线程)
     * @param siganlHost siganlHost="127.0.0.1:32888"
     * @param signalServer
     * @param signalAccount
     */
    public synchronized Boolean signalInit(String siganlHost,String signalServer,String signalAccount){
        // 1 初始化信号源  accountInfo|SIGNALINIT|signalHost:signalPort
        // 2 初始化跟随者  accountInfo|FOLLOWINIT|serverHost:serverPort#followHost:followPort#signalArray#followOrderList|ruleList(signalAccount:ruleType:ruleAmount:limitUpper)
        // 3 跟随信息  signalAccountInfo|SIGNALFOLLOWORDER|action|tradeOrder
        // 4 跟随者订单  accountInfo|signalAccount|action|tradeOrder

        String accountInfo=signalServer+"&"+signalAccount;

        /*信号源初始化数据*/
        String signalInit = accountInfo+"|"+ FollowConstant.SIGNAL_INIT+"|"+siganlHost;

        try (ZContext context = new ZContext()) {
            //广播模式（该步骤无法精确定位信号源）
            ZMQ.Socket pubSocket = context.createSocket(SocketType.PUB);
            ZMQ.Socket pullSocket=context.createSocket(SocketType.PULL);
            //默认 固定 服务器地址！
            pubSocket.bind("tcp://127.0.0.1:32777");
            int i=0;
            String reciveMsg="";
            // 请求100*5次
            while (i<20) {
                System.out.println("send init String :"+signalInit);
                pubSocket.send(signalInit,0);
                //接收信号源绑定回执信息
                pullSocket.connect("tcp://"+siganlHost+"");
                pullSocket.setReceiveTimeOut(100);
                byte[] reply = pullSocket.recv(0);
                if(reply!=null && reply.length>0){
                    reciveMsg=new String(reply);
                    System.out.println(reciveMsg);
                    if(reciveMsg.equalsIgnoreCase(signalServer+"&"+signalAccount)){
                        //绑定成功
                        return true;
                    }
                }
                i++;
                Thread.sleep(400);
            }
        }catch (Exception e){
            log.error(e.getMessage(),e);
            throw new BusinessException(e);
        }

        return false;
    }


    /**
     * 监听信号源订单  --------------定时任务
     * @param siganlHost
     * @return
     */
    public String monitorSignal(String siganlHost){

        try (ZContext context = new ZContext()) {
            //监听该信号源
            ZMQ.Socket pullSocket=context.createSocket(SocketType.PULL);

            String reciveMsg="";

            //接收信号源订单信息
            pullSocket.connect("tcp://"+siganlHost+"");
            pullSocket.setReceiveTimeOut(10);
            int i=0;
            while (true){
                byte[] reciveByte = pullSocket.recv(0);
                if(reciveByte!=null&&reciveByte.length>0){
                    //获取数据成功
                    reciveMsg=new String(reciveByte, ZMQ.CHARSET);
                    return reciveMsg;
                }
                Thread.sleep(1000);
                System.out.println("listened times:"+i++);
            }
        }catch (Exception e){
            log.error(e.getMessage(),e);
            throw new BusinessException(e);
        }
    }


    /**
     * 处理信号源订单信息
     * @param signalMsg
     */
    public void dealSignalReciveMsg(String signalMsg){
        // 1 初始化信号源  accountInfo|SIGNALINIT|signalHost:signalPort
        // 2 初始化跟随者  accountInfo|FOLLOWINIT|serverHost:serverPort#followHost:followPort#signalArray#followOrderList|ruleList(signalAccount:ruleType:ruleAmount:limitUpper)
        // 3 信号源订单  signalAccountInfo|SIGNALFOLLOWORDER|action|tradeOrder
        // 4 跟随者订单  accountInfo|signalAccount|action|tradeOrder

        /*signalMsg = "MultiBankFXInt-Demo F&2102266149|SIGNALFOLLOWORDER|CLOSE|{'orderType':'0','orderTicket':'131145568','orderSymbol':'EURUSD'," +
                "'orderLots':'0.01000000','orderCloseTime':'1568882015','orderClosePrice':'1.10591000','orderStopLoss':'0.00000000'," +
                "'orderTakeProfit':'0.00000000','orderMagicNumber':'0.00000000','orderComment':'comment','orderCommission':'0.00000000','orderExpiration':'0'";*/

        System.out.println("dealSignalReciveMsg:"+signalMsg);
        String msgInfo[]=signalMsg.split("\\|");
        for(String signalMsgInfo:msgInfo){
            System.out.println(signalMsgInfo);
        }

        // 解析數據
        /*reciveTradeOrder = "{'orderType':'0','orderTicket':'131145568','orderSymbol':'EURUSD'," +
                "'orderLots':'0.01000000','orderCloseTime':'1568882015','orderClosePrice':'1.10591000','orderStopLoss':'0.00000000'," +
                "'orderTakeProfit':'0.00000000','orderMagicNumber':'0.00000000','orderComment':'comment','orderCommission':'0.00000000','orderExpiration':'0'}";*/

        Map signalOrderMsg = new HashMap();
        String signalAccount[]=  msgInfo[0].split("&");
        String orderAction=msgInfo[2];
        String orderDetail=msgInfo[3].replace("'","\"");
        String signalServer=signalAccount[0];
        String signalAccountId=signalAccount[1];
        JSONObject orderJson=JSONObject.parseObject(orderDetail);

        /*保存至 信号源订单表*/


    }


}
