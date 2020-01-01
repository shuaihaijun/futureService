package com.future.timing;

import com.future.common.exception.BusinessException;
import com.future.service.follow.SignalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author ：haijun
 * @date ：Created in 2019-10-17 15:17
 * @description：信号源监听
 * @modified By：
 * @version: 1/0$
 */
@Configuration
@EnableScheduling
public class SignalMonitor {

    Logger log= LoggerFactory.getLogger(SignalMonitor.class);
    @Autowired
    SignalService signalService;

    /**
     * 监听信号源 （监听周期 0.2秒）
     */
//    @Scheduled(cron = "* * * 1 * ?")
    public void monitor(){
        //从缓存中查询信号源列表
        try(ZContext zContext=new ZContext()) {
            ZMQ.Socket subSocket=zContext.createSocket(SocketType.SUB);
            ZMQ.Socket pubSocket=zContext.createSocket(SocketType.PUB);
            String serverHostUrl="";
            String serverHostPort="";
            pubSocket.connect("tcp://"+serverHostUrl+":"+serverHostPort);

            List<Map> signals=new ArrayList<>();
            String signalHost="";
            String signalAccountInfo="";
            String reciveMsg="";
            byte[] reply;

            /*循环监听信号源*/
            for(Map map:signals){

                signalHost=String.valueOf(map.get("signalHostUrl"))+":"+ String.valueOf(map.get("signalPort"));
                signalAccountInfo=String.valueOf(map.get("signalAccountInfo"));
                subSocket.connect("tcp://"+signalHost);
                subSocket.setReceiveTimeOut(10);
                subSocket.subscribe(signalAccountInfo);

                reply=subSocket.recv(0);
                if(reply!=null && reply.length>0){
                    reciveMsg=new String(reply);
                    //监听到订单后，转发给跟随者（跟随者会根据信号源账号配匹配）
                    pubSocket.send(reciveMsg,0);
                    //处理监听到的订单
                    signalService.dealSignalReciveMsg(reciveMsg);
                }
            }

        }catch (Exception e){
            log.error(e.getMessage(),e);
            throw new BusinessException(e);
        }
    }
}
