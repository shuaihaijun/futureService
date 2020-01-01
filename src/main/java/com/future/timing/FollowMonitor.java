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
 * @description：跟随者监听
 * @modified By：
 * @version: 1/0$
 */
@Configuration
@EnableScheduling
public class FollowMonitor {

    Logger log= LoggerFactory.getLogger(FollowMonitor.class);
    @Autowired
    SignalService signalService;

    /**
     * 监听信号源
     */
//    @Scheduled(cron = "* * * 1 * ?")
    public void monitor(){
        //从缓存中查询信号源列表
        try(ZContext zContext=new ZContext()) {
            ZMQ.Socket pullSocket=zContext.createSocket(SocketType.PULL);
            String followHostUrl="";
            String followHostPort="";
            List<Map> follows=new ArrayList<>();
            String followAccountInfo="";
            String reciveMsg="";
            byte[] reply;

            /*循环监听信号源*/
            for(Map map:follows){
                pullSocket.connect("tcp://"+followHostUrl+":"+followHostPort);
                followAccountInfo=String.valueOf(map.get("accountInfo"));
                pullSocket.setReceiveTimeOut(10);
                reply=pullSocket.recv(0);
                if(reply!=null && reply.length>0){
                    reciveMsg=new String(reply);
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
