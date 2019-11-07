package com.future.service.follow;

import com.alibaba.fastjson.JSONObject;
import com.future.common.constants.FollowConstant;
import com.future.common.exception.BusinessException;
import com.future.common.util.ZeroMqUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

/**
 * @author ：haijun
 * @date ：Created in 2019-10-17 19:11
 * @description：跟单处理线程
 * @modified By：
 * @version: 1.0$
 */
public class FollowDealThread extends Thread{

    Logger log = LoggerFactory.getLogger(FollowDealThread.class);

    public String followAccount;
    public String action;
    public String orderJson;
    public String signalAccount;

    public void setFollowData(String followAccount,String signalAccount,String action,String orderJson){
        this.followAccount=followAccount;
        this.signalAccount=signalAccount;
        this.action=action;
        this.orderJson=orderJson;
    }

    public void runBak() {
        JSONObject reciveOrderJson=JSONObject.parseObject(orderJson);
        Integer orderType=Integer.parseInt(reciveOrderJson.getString("orderType"));
        Integer orderTicket=Integer.parseInt(reciveOrderJson.getString("orderTicket"));
        String orderSymbol=reciveOrderJson.getString("orderSymbol");
        BigDecimal orderLots=new BigDecimal(reciveOrderJson.getString("orderLots"));
        BigDecimal orderClosePrice=new BigDecimal(reciveOrderJson.getString("orderClosePrice"));
        Long orderCloseTime=Long.parseLong(reciveOrderJson.getString("orderCloseTime"));
        BigDecimal orderStopLoss=new BigDecimal(reciveOrderJson.getString("orderStopLoss"));
        BigDecimal orderTakeProfit=new BigDecimal(reciveOrderJson.getString("orderTakeProfit"));
        BigDecimal orderMagicNumber=new BigDecimal(reciveOrderJson.getString("orderMagicNumber"));
        String orderComment=reciveOrderJson.getString("orderComment");
        Long orderExpiration=Long.parseLong(reciveOrderJson.getString("orderExpiration"));
        int superiorTicket=0;
        if(action.equalsIgnoreCase(FollowConstant.ACTION_CLOSEPART)&&reciveOrderJson.getString("superiorTicket")!=null){
            // 部分平仓！
            superiorTicket=Integer.parseInt(reciveOrderJson.getString("superiorTicket"));
        }
        int follownTradeTicket=0;


        //查找每个跟随者的跟单规则（多线程）

        //重新计算 并得到跟随者的订单

        //跟随订单拼接
        String followTradeOrder= FollowConstant.ACTION_TRADE+";"+action+";"+orderType+";"+orderSymbol+";"+orderClosePrice
                +";"+orderStopLoss+";"+orderTakeProfit+";"+orderComment+";"+orderLots+";"+orderMagicNumber;

        //如果是关闭订单，需要查出源跟随订单
        if(action.equalsIgnoreCase(FollowConstant.ACTION_CLOSEPART)||action.equalsIgnoreCase(FollowConstant.ACTION_CLOSE)){
            if(action.equalsIgnoreCase(FollowConstant.ACTION_CLOSEPART)){
                orderTicket=superiorTicket;
            }
            // 根据 信号源+跟随者+源信号源订单号  查出应该关闭的 followTickt
            followTradeOrder=followTradeOrder+";"+follownTradeTicket;
        }

        String followOrderMsg= FollowConstant.SUB_MSG+"|"+followAccount+"|"+signalAccount+"|"+followTradeOrder;

        //发布订单
        try{
            //广播模式（该步骤无法精确定位信号源）
            ZeroMqUtil.pubSocket.send(followOrderMsg);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            throw new BusinessException(e);
        }
    }
}
