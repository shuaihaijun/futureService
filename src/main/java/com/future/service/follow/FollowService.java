package com.future.service.follow;

import com.alibaba.fastjson.JSONObject;
import com.future.common.constants.FollowConstant;
import com.future.common.constants.RedisConstant;
import com.future.common.exception.BusinessException;
import com.future.common.util.RedisManager;
import com.future.entity.order.FuOrderFollowAction;
import com.future.entity.user.FuUserFollowRule;
import com.future.entity.user.FuUserFollows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *  跟随者逻辑处理
 */
@Service
public class FollowService{

    Logger log= LoggerFactory.getLogger(FollowService.class);

    @Autowired
    RedisManager redisManager;


    /**
     * 跟随者初始化
     * @param serverHost
     * @param customerServer
     * @param customerAccount
     */
    public Boolean followInit(String serverHost,String followHost,String customerServer,String customerAccount){
        // 1 初始化信号源  accountInfo|SIGNALINIT|signalHost:signalPort
        // 2 初始化跟随者  accountInfo|FOLLOWINIT|serverHost:serverPort#followHost:followPort#signalArray#followOrderList|ruleList(signalAccount:ruleType:ruleAmount:limitUpper)
        // 3 跟随信息  signalAccountInfo|SIGNALFOLLOWORDER|action|tradeOrder
        // 4 跟随者订单  accountInfo|signalAccount|action|tradeOrder


        String accountInfo=customerServer+"&"+customerAccount;
        // 根据当前账号 查询跟随列表
        // 解析数据 格式为 signalHost1:signalPort1:signalServer1&signalAccountId1;signalHost2:signalPort2:signalServer2&signalAccountId2
        String signalArray="127.0.0.1:30222:MultiBankFXInt-Demo F&2102272054";
        String ruleList="MultiBankFXInt-Demo F&2102272054:0:0.2";
        List<Map> follows=new ArrayList<>();
        for(Map follow:follows){
            signalArray=signalArray+follow.get("signalHost")+":"+follow.get("signalPort")+":"+follow.get("signalServerId")+"&"+follow.get("signalMtAccId")+";";
            ruleList=ruleList+follow.get("signalServerId")+"&"+follow.get("signalMtAccId")+":"+follow.get("ruleType")+":"+follow.get("amount")+";";
        }
        if(signalArray.length()>0 && follows.size()>0){
            signalArray=signalArray.substring(0,signalArray.length()-2);
        }
        if(ruleList.length()>0 && follows.size()>0){
            ruleList=ruleList.substring(0,ruleList.length()-2);
        }

        /*查询客户跟随列表*/
        List<FuOrderFollowAction> actions=new ArrayList<>();
        String followOrderList="131565349:131565350";
        for(FuOrderFollowAction action:actions){
            followOrderList=action.getUserOrderId()+":"+action.getSignalMtAccId()+";";
        }
        if(followOrderList.length()>0 && actions.size()>0){
            followOrderList=followOrderList.substring(0,followOrderList.length()-2);
        }

        String followInitStr = accountInfo+"|"+ FollowConstant.FOLLOW_INIT+"|"+serverHost+"#"+followHost+"#"+signalArray+"#"+followOrderList+"|"+ruleList;

        System.out.println(followInitStr);

        //初始化暂不能准确定位跟随者
        try (ZContext context = new ZContext()) {
            //广播模式（该步骤无法精确定位信号源）
            ZMQ.Socket pubSocket = context.createSocket(SocketType.PUB);
            ZMQ.Socket pullSocket=context.createSocket(SocketType.PULL);

            pubSocket.bind("tcp://"+serverHost);

            int i=0;
            String reciveMsg="";
            // 请求100*5次
            while (i<10) {

                System.out.println("send times:"+i);
                pubSocket.send(followInitStr,0);

                //接收信号源绑定回执信息
                pullSocket.connect("tcp://"+followHost);
                pullSocket.setReceiveTimeOut(100);
                byte[] reply = pullSocket.recv(0);
                if(reply!=null && reply.length>0){
                    reciveMsg=new String(reply);
                    System.out.println(reciveMsg);
                    return true;
                }
                if(reciveMsg.equalsIgnoreCase(customerServer+"&"+customerAccount)){
                    //绑定成功
                    return true;
                }
                i++;
                Thread.sleep(1000);
            }
        }catch (Exception e){
            log.error(e.getMessage(),e);
            throw new BusinessException(e);
        }
        return false;

    }

    /**
     * 跟随订单 处理逻辑
     * @param signalMsg
     */
    public void followOrders(Map signalMsg){

        String signalAccount = String.valueOf(signalMsg.get("signalAccount"));
        String action = String.valueOf(signalMsg.get("action"));
        String signalOrder = String.valueOf(signalMsg.get("signalOrder"));

        String orderJson= StringUtils.replace("'",signalOrder,null);

        // 获得信号源跟随者列表（缓存中获取）
        String follows= JSONObject.toJSONString( redisManager.hget(RedisConstant.FOLLOW_LIST_KEY,signalAccount));

        List<Map> followList = new ArrayList<>();
        for(Map map:followList){
            // followAccout = mtServer&mtAccout
            String followAccout=String.valueOf(map.get("followAccout"));
            FollowDealThread dealThread=new FollowDealThread();
            dealThread.setFollowData(followAccout,signalAccount,action,orderJson);
            /*多线程跑起来*/
            dealThread.run();
        }
    }

    /**
     * 监听跟随者订单（跟随订单+自主交易订单）---------定时任务
     */
    public void monitorFollowsOrder(){
        //** 获取 跟随者列表（有跟随记录）
        List<Map> follows = new ArrayList<Map>();

        try (ZContext context = new ZContext()) {
            ZMQ.Socket pullSocket=context.createSocket(SocketType.PULL);
            for(Map map :follows){
                String followAdress="";
                String followPort="";

                pullSocket.connect("tcp://"+followAdress+":"+followPort);
                byte[] reply = pullSocket.recv(0);
                if(reply!=null&&reply.length>0){
                    /*监听到订单 处理订单*/
                    dealFollowOrder(new String(reply));
                }
            }
        }
    }

    /**
     * 处理监听到的 跟随者订单信息
     */
    public void dealFollowOrder(String followOrder){
        log.info(followOrder);
    }


}
