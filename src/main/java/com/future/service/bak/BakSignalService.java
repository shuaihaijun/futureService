package com.future.service.bak;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.future.common.constants.FollowConstant;
import com.future.common.exception.BusinessException;
import com.future.common.util.DateUtil;
import com.future.entity.order.FuOrderFollowAction;
import com.future.entity.order.FuOrderSignal;
import com.future.entity.product.FuProductSignal;
import com.future.mapper.order.FuOrderSignalMapper;
import com.future.pojo.bo.account.UserMTAccountBO;
import com.future.service.account.FuAccountMtService;
import com.future.service.order.FuOrderFollowActionService;
import com.future.service.product.FuProductSignalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

import java.math.BigDecimal;
import java.util.*;

/**
 * 信号源处理逻辑
 */
@Deprecated
@Service
public class BakSignalService extends ServiceImpl<FuOrderSignalMapper,FuOrderSignal> {

    Logger log= LoggerFactory.getLogger(BakSignalService.class);
    @Autowired
    BakFollowService bakFollowService;
    @Autowired
    FuOrderSignalMapper fuOrderSignalMapper;
    @Autowired
    FuAccountMtService fuAccountMtService;
    @Autowired
    FuOrderFollowActionService fuOrderFollowActionService;
    @Autowired
    FuProductSignalService fuProductSignalService;

    @Value("pubServerUrl")
    String pubServerUrl;
    @Value("pubServerPort")
    String pubServerPort;

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
            pubSocket.bind("tcp://"+pubServerUrl+":"+pubServerPort);
            int i=0;
            String reciveMsg="";
            // 请求100*5次
            while (i<2) {
                System.out.println("send init String :"+signalInit);
                pubSocket.send(signalInit,0);
                //接收信号源绑定回执信息
                pullSocket.connect("tcp://"+siganlHost+"");
                pullSocket.setReceiveTimeOut(100);
                byte[] reply = pullSocket.recv(0);
                if(reply!=null && reply.length>0){
                    reciveMsg=new String(reply);
                    System.out.println("recivde signal Confirm msg:"+reciveMsg);
                    if(reciveMsg.equalsIgnoreCase(signalServer+"&"+signalAccount)){
                        //绑定成功
                        return true;
                    }
                }
                i++;
                Thread.sleep(700);
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
        // MultiBankFXInt-Demo F&2102272054|SIGNALFOLLOWORDER|OPEN|1,EURAUD,1.61436000,0.00000000,0.00000000,,0.03000000,0.00000000,131565401,1572512497,0.00000000,0
        // tradeOrder= type,symbol,closePrice/openPrice,stopLoss,takeProfit,comment,lots,magic,ticket,closeTime/openTime,commission,expiration,superiorTicket
        System.out.println("dealSignalReciveMsg:"+signalMsg);
        String msgInfo[]=signalMsg.split("\\|");

        // 解析數據
        Map signalOrderMsg = new HashMap();
        String signalAccount[]=  msgInfo[0].split("&");
        String orderAction=msgInfo[2];
        String orderDetail[]=msgInfo[3].split(",");
        for(String signalMsgInfo:msgInfo){
            log.info("recived signalTradeOrder from："+msgInfo[0]);
            log.info("orderDetail is ："+signalMsg);
        }

        //根据serverName + mtaccId 查询user
        Map conditionMap=new HashMap();
        conditionMap.put("serverName",signalAccount[0]);
        conditionMap.put("mtAccId",signalAccount[1]);
        List<UserMTAccountBO> accountBOS= fuAccountMtService.getUserMTAccByCondition(conditionMap);
        if(accountBOS==null||accountBOS.size()<=0){
            log.error("根据服务器和账号，查询用户错误！");
            log.error("signalMsg:"+signalMsg);
            // TODO 保存錯誤數據
        }
        conditionMap.clear();
        conditionMap.put(FuProductSignal.USER_ID,accountBOS.get(0).getUserId());
        conditionMap.put(FuProductSignal.SERVER_NAME,accountBOS.get(0).getServerName());
        conditionMap.put(FuProductSignal.MT_ACC_ID,accountBOS.get(0).getMtAccId());
        List<FuProductSignal> signals=fuProductSignalService.selectByMap(conditionMap);
        if(accountBOS.size()<=0){
            log.error("根据服务器和账号，查询信号源错误！");
            log.error("signalMsg:"+signalMsg);
            // TODO 保存錯誤數據
        }
        /*保存至 信号源订单表*/
        FuOrderSignal signalOrder=new FuOrderSignal();
        signalOrder.setUserId(accountBOS.get(0).getUserId());
        signalOrder.setMtServerName(accountBOS.get(0).getServerName());
        signalOrder.setSignalId(signals.get(0).getId());
//        signalOrder.setMtServerId();
        signalOrder.setMtAccId(accountBOS.get(0).getMtAccId());
        signalOrder.setOrderType(Integer.parseInt(orderDetail[0]));
        signalOrder.setOrderSymbol(orderDetail[1]);
        //订单状态（0 PositionOpen ，1 PositionClose，2 PositionModify，3 PendingOpen，4 PendingDelete,5 PendingModify,6 PendingFill
        if(orderAction.equalsIgnoreCase(FollowConstant.ACTION_OPEN)){
            signalOrder.setOrderTradeOperation(0);
            signalOrder.setOrderOpenDate(DateUtil.toDataFormTimeStamp(Long.parseLong(orderDetail[9])));
            signalOrder.setOrderOpenPrice(new BigDecimal(orderDetail[2]));
        }else if(orderAction.equalsIgnoreCase(FollowConstant.ACTION_MODIFY)){
            signalOrder.setOrderTradeOperation(1);
        }else if(orderAction.equalsIgnoreCase(FollowConstant.ACTION_CLOSE)){
            signalOrder.setOrderTradeOperation(2);
            signalOrder.setOrderCloseDate(DateUtil.toDataFormTimeStamp(Long.parseLong(orderDetail[9])));
            signalOrder.setOrderClosePrice(new BigDecimal(orderDetail[2]));
        }else if(orderAction.equalsIgnoreCase(FollowConstant.ACTION_CLOSE_PARTIAL)){
            signalOrder.setOrderTradeOperation(3);
            signalOrder.setOrderCloseDate(DateUtil.toDataFormTimeStamp(Long.parseLong(orderDetail[9])));
            signalOrder.setOrderClosePrice(new BigDecimal(orderDetail[2]));
            signalOrder.setOrderSuperior(orderDetail[12]);
        }else if(orderAction.equalsIgnoreCase(FollowConstant.ACTION_CLOSE_MAGIC)){
            signalOrder.setOrderTradeOperation(4);
        }
        signalOrder.setOrderStoploss(new BigDecimal(orderDetail[3]));
        signalOrder.setOrderProfit(new BigDecimal(orderDetail[4]));
        signalOrder.setComment(orderDetail[5]);
        signalOrder.setOrderLots(new BigDecimal(orderDetail[6]));
        signalOrder.setOrderMagic(new BigDecimal(orderDetail[7]));
        signalOrder.setOrderId(orderDetail[8]);
        signalOrder.setOrderCommission(new BigDecimal(orderDetail[10]));
        signalOrder.setOrderExpiration(DateUtil.toDataFormTimeStamp(Long.parseLong(orderDetail[11])));

        if(orderAction.equalsIgnoreCase(FollowConstant.ACTION_CLOSE)
            ||orderAction.equalsIgnoreCase(FollowConstant.ACTION_CLOSE_PARTIAL)
                ||orderAction.equalsIgnoreCase(FollowConstant.ACTION_CLOSE_MAGIC)){
            // 信号源订单 关闭 变更跟单关系表
            Map selectMap=new HashMap();
            selectMap.put(FuOrderFollowAction.SIGNAL_ORDER_ID,signalOrder.getOrderId());
            selectMap.put(FuOrderFollowAction.ORDER_STATE,"1");
            List<FuOrderFollowAction> currents= fuOrderFollowActionService.selectByMap(selectMap);
            List<FuOrderFollowAction> newActions=new ArrayList<>();
            for(FuOrderFollowAction action:currents){
                if(orderAction.equalsIgnoreCase(FollowConstant.ACTION_CLOSE_PARTIAL)){
                    //如果是部分关闭，生成一个新的订单跟随任务
                    FuOrderFollowAction newAction=new FuOrderFollowAction();
                    BeanUtils.copyProperties(action,newAction);
                    newAction.setCreateDate(new Date());
                    newAction.setModifyDate(new Date());
                    newAction.setSignalOrderId(signalOrder.getOrderSuperior());
                    newActions.add(newAction);
                }
                //关闭当前订单监听任务
                action.setOrderAction(signalOrder.getOrderTradeOperation());
                action.setOrderState(0);
            }
            if(currents.size()>0){
                //批量修改
                fuOrderFollowActionService.updateBatchById(currents);
            }
            if(newActions.size()>0){
                //批量新增
                fuOrderFollowActionService.batchInsert(newActions);
            }
        }

        /*保存至数据库*/
        fuOrderSignalMapper.insertSelective(signalOrder);

    }


}
