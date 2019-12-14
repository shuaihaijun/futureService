package com.future.service.follow;

import com.alibaba.fastjson.JSONObject;
import com.future.common.constants.CommonConstant;
import com.future.common.constants.FollowConstant;
import com.future.common.constants.OrderConstant;
import com.future.common.constants.RedisConstant;
import com.future.common.enums.GlobalResultCode;
import com.future.common.exception.BusinessException;
import com.future.common.util.RedisManager;
import com.future.entity.account.FuAccountMt;
import com.future.entity.order.FuOrderFollowAction;
import com.future.entity.order.FuOrderFollowError;
import com.future.entity.order.FuOrderFollowInfo;
import com.future.entity.user.FuUserFollows;
import com.future.mapper.order.FuOrderFollowErrorMapper;
import com.future.mapper.order.FuOrderFollowInfoMapper;
import com.future.mapper.user.FuUserFollowsMapper;
import com.future.pojo.bo.order.UserMTAccountBO;
import com.future.service.account.FuAccountMtSevice;
import com.future.service.order.FuOrderFollowActionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

import java.util.ArrayList;
import java.util.HashMap;
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
    @Autowired
    FuAccountMtSevice fuAccountMtSevice;
    @Autowired
    FuOrderFollowInfoMapper fuOrderFollowInfoMapper;
    @Autowired
    FuOrderFollowErrorMapper fuOrderFollowErrorMapper;
    @Autowired
    FuUserFollowsMapper fuUserFollowsMapper;
    @Autowired
    FuOrderFollowActionService fuOrderFollowActionService;

    /**
     * 跟随者初始化
     * @param userId
     * @param userServer
     * @param userAccount
     * @param serverHost
     * @param followHost
     * @return
     */
    public Boolean followInit(String userId,String userServer,String userAccount,String serverHost,String followHost){
        // 1 初始化信号源  accountInfo|SIGNALINIT|signalHost:signalPort
        // 2 初始化跟随者  accountInfo|FOLLOWINIT|serverHost:serverPort#followHost:followPort#signalArray#followOrderList|ruleList(signalAccount:ruleType:ruleAmount:limitUpper)
        // 3 跟随信息  signalAccountInfo|SIGNALFOLLOWORDER|action|tradeOrder
        // 4 跟随者订单  accountInfo|signalAccount|action|tradeOrder


        String accountInfo=userServer+"&"+userAccount;
        // 根据当前账号 查询跟随列表
        // 解析数据 格式为 signalHost1:signalPort1:signalServer1&signalAccountId1;signalHost2:signalPort2:signalServer2&signalAccountId2
        // String signalArray="127.0.0.1:30222:MultiBankFXInt-Demo F&2102272054";
        // String ruleList="MultiBankFXInt-Demo F&2102272054:0:0.2";

        /*查询用户订阅信息*/
        String signalArray="";
        String ruleList="";
        Map followInfo=new HashMap();
        followInfo.put(FuUserFollows.USER_ID,userId);
        followInfo.put(FuUserFollows.USER_MT_ACC_ID,userAccount);
        List<FuUserFollows> userFollows= fuUserFollowsMapper.selectByMap(followInfo);
        /*判斷跟随者列表*/
        if(userFollows==null ||userFollows.size()==0){
            log.error("连接MT账户信息, 用户无订阅信息!");
            throw new BusinessException("连接MT账户信息, 用户无订阅信息!");
        }

        /*拼接用户账户信息*/
        Map userMtAccMap=new HashMap();
        List<UserMTAccountBO> fuAccountMts;
        String signalAccountInfo="";
        for(FuUserFollows follow:userFollows){
            /*查询信号源账户*/
            userMtAccMap.put(FuAccountMt.MT_ACC_ID,follow.getSignalMtAccId());
            userMtAccMap.put(FuAccountMt.IS_SIGNAL, CommonConstant.COMMON_YES);
            fuAccountMts=fuAccountMtSevice.getUserMTAccByCondition(userMtAccMap);
            if(fuAccountMts==null ||fuAccountMts.isEmpty()){
                log.error("跟随信号源账户信息查询失败! signalId:"+follow.getSignalId());
                throw new BusinessException(GlobalResultCode.RESULE_DATA_NONE,"跟随信号源账户信息查询失败! signalId:"+follow.getSignalId());
            }
            signalAccountInfo=fuAccountMts.get(0).getServerName()+"&"+fuAccountMts.get(0).getMtAccId();
            /*拼接信号源信息*/
            signalArray=signalArray+fuAccountMts.get(0).getAccountUrl()+":"+fuAccountMts.get(0).getAccountPort()+":"+signalAccountInfo+";";
            /*拼接跟随规则信息*/
            ruleList=ruleList+signalAccountInfo+":"+follow.getRuleType()+":"+follow.getAmount()+";";
            userMtAccMap.clear();
        }
        if(signalArray.length()>0 && userFollows.size()>0){
            signalArray=signalArray.substring(0,signalArray.length()-2);
        }
        if(ruleList.length()>0 && userFollows.size()>0){
            ruleList=ruleList.substring(0,ruleList.length()-2);
        }

        /*查询客户订单跟随列表*/
        Map followOrderMap =new HashMap();
        followOrderMap.put(FuOrderFollowAction.USER_ID,userId);
        followOrderMap.put(FuOrderFollowAction.ORDER_STATE,OrderConstant.ORDER_FOLLOW_ORDERS_STATE_ING);
        List<FuOrderFollowAction> actions=fuOrderFollowActionService.selectByMap(followOrderMap);
        String followOrderList="";
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
            pubSocket.setSendTimeOut(100);
            /*需要先发一次 建立连接，mql4中 zeroMq的限制*/
            pubSocket.send(followInitStr,0);
            int i=0;
            String reciveMsg="";
            // 请求100*5次
            while (i<10){
                pubSocket.send(followInitStr,0);

                //接收信号源绑定回执信息
                pullSocket.connect("tcp://"+followHost);
                pullSocket.setReceiveTimeOut(10);
                byte[] reply = pullSocket.recv(0);
                if(reply!=null && reply.length>0){
                    reciveMsg=new String(reply);
                    return true;
                }
                if(reciveMsg.equalsIgnoreCase(accountInfo)){
                    //绑定成功
                    return true;
                }
                i++;
                Thread.sleep(100);
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
    public void dealFollowOrder(String followMsg){
        // 1 初始化信号源  accountInfo|SIGNALINIT|signalHost:signalPort
        // 2 初始化跟随者  accountInfo|FOLLOWINIT|serverHost:serverPort#followHost:followPort#signalArray#followOrderList|ruleList(signalAccount:ruleType:ruleAmount:limitUpper)
        // 3 信号源订单  signalAccountInfo|SIGNALFOLLOWORDER|action|tradeOrder
        // 4 跟随者订单  accountInfo|signalAccount|action|tradeOrder
        // MultiBankFXInt-Demo F&2102272054|MultiBankFXInt-Demo1 F&2102272054|OPEN|{'_action': 'EXECUTION', '_response': '131', 'response_value': 'invalid trade volume','signalTicket':131623205,'signalAccount':MultiBankFXInt-Demo1 F&2102272054}
        // MultiBankFXInt-Demo F&2102272054|MultiBankFXInt-Demo1 F&2102272054|OPEN|{'_action': 'EXECUTION', '_magic': 0, '_ticket': 131612110, '_open_time': '2019.11.11 10:39:35', '_open_price': 108.98800000,'_lots':'1','signalTicket':131623205,'signalAccount':MultiBankFXInt-Demo1 F&2102272054}
        // MultiBankFXInt-Demo F&2102272054|MultiBankFXInt-Demo1 F&2102272054|CLOSE_PARTIAL|{'_action': 'CLOSE', '_ticket': 131623205, '_response': 'CLOSE_PARTIAL', '_close_price': 1.10146000, '_close_lots': 0.01000000,'signalTicket':131623205,'signalAccount':MultiBankFXInt-Demo1 F&2102272054}
        // MultiBankFXInt-Demo F&2102272054|MultiBankFXInt-Demo1 F&2102272054|CLOSE|{'_action': 'CLOSE', '_ticket': 131623205, '_close_price': 1.10171000, '_close_lots': 1.00000000, '_response': 'CLOSE_MARKET', '_response_value': 'SUCCESS','signalTicket':131623205,'signalAccount':MultiBankFXInt-Demo1 F&2102272054}

        log.info("deal follow ReciveMsg:"+followMsg);
        String msgInfo[]=followMsg.split("\\|");

        // 解析數據
        String followAccount[]=  msgInfo[0].split("&");
        String signalAccount[]=  msgInfo[1].split("&");
        String orderAction=msgInfo[2];
        String orderDetail=msgInfo[3].replace("'","\\");
        JSONObject orderJson=JSONObject.parseObject(orderDetail);
        String response=orderJson.getString("_response");

        //根据serverName + mtaccId 查询user
        Map conditionMap=new HashMap();
        conditionMap.put("serverName",followAccount[0]);
        conditionMap.put("mtAccId",followAccount[1]);
        List<UserMTAccountBO> followAccountInfo=fuAccountMtSevice.getUserMTAccByCondition(conditionMap);
        conditionMap.put("serverName",signalAccount[0]);
        conditionMap.put("mtAccId",signalAccount[1]);
        List<UserMTAccountBO> signalAccountInfo=fuAccountMtSevice.getUserMTAccByCondition(conditionMap);
        if(followAccountInfo.size()<=0||signalAccountInfo.size()<=0){
            log.error("根据服务器和账号，查询用户错误！");
            log.error("signalMsg:"+followMsg);
        }

        if(!StringUtils.isEmpty(response)&& com.alibaba.druid.util.StringUtils.isNumber(response)){
            //跟单出错！
            log.error("follow order error,msg is :"+orderJson.toJSONString());

            FuOrderFollowError error=new FuOrderFollowError();
            error.setUserId(followAccountInfo.get(0).getUserId());
            error.setUserServerName(followAccountInfo.get(0).getServerName());
            error.setUserMtAccId(followAccountInfo.get(0).getMtAccId());
            error.setUserServerId(followAccountInfo.get(0).getServerId());

            error.setErrorCode(response);
            error.setErrorMsg(orderJson.toJSONString());

            error.setSignalServerId(signalAccountInfo.get(0).getServerId());
            error.setSignalServerName(signalAccountInfo.get(0).getServerName());
            error.setSignalMtAccId(signalAccountInfo.get(0).getMtAccId());
            error.setSignalOrderId(orderJson.getString("signalTicket"));
            error.setUserOrderId(orderJson.getInteger("_ticket")==null?0:orderJson.getInteger("_ticket"));

            fuOrderFollowErrorMapper.insert(error);
            return;
        }

        /*保存至 信号源订单表*/
        FuOrderFollowInfo followInfo=new FuOrderFollowInfo();

        followInfo.setUserId(followAccountInfo.get(0).getUserId());
        followInfo.setUserServerName(followAccountInfo.get(0).getServerName());
        followInfo.setUserMtAccId(followAccountInfo.get(0).getMtAccId());
        followInfo.setUserServerId(followAccountInfo.get(0).getServerId());

        followInfo.setSignalServerId(signalAccountInfo.get(0).getServerId());
        followInfo.setSignalServerName(signalAccountInfo.get(0).getServerName());
        followInfo.setSignalMtAccId(signalAccountInfo.get(0).getMtAccId());
        followInfo.setSignalOrderId(orderJson.getString("signalTicket"));

        followInfo.setOrderId(orderJson.getString("_ticket"));
        followInfo.setOrderLots(orderJson.getBigDecimal("_lots"));
        followInfo.setOrderStoploss(orderJson.getBigDecimal("_SL"));
        followInfo.setOrderTakeprofit(orderJson.getBigDecimal("_TP"));
        if(orderAction.equalsIgnoreCase(FollowConstant.ACTION_OPEN)){
            followInfo.setOrderTradeOperation(OrderConstant.ORDER_OPERATION_OPEN);
            followInfo.setOrderType(orderJson.getInteger("_orderType"));
            followInfo.setOrderOpenDate(orderJson.getTimestamp("_open_time"));
            followInfo.setOrderOpenPrice(orderJson.getBigDecimal("_open_price"));
        }else if(orderAction.equalsIgnoreCase(FollowConstant.ACTION_CLOSE)){
            followInfo.setOrderTradeOperation(OrderConstant.ORDER_OPERATION_CLOSE);
        }else if(orderAction.equalsIgnoreCase(FollowConstant.ACTION_CLOSE_PARTIAL)){
            followInfo.setOrderTradeOperation(OrderConstant.ORDER_OPERATION_CLOSE_PARTIAL);
            //            followInfo.setOrderSuperior();
        }
        if((orderAction.equalsIgnoreCase(FollowConstant.ACTION_CLOSE)
                ||orderAction.equalsIgnoreCase(FollowConstant.ACTION_CLOSE_PARTIAL))){
            followInfo.setOrderSwap(orderJson.getBigDecimal("_orderSwap"));
            followInfo.setOrderCommission(orderJson.getBigDecimal("_orderCommission"));
            followInfo.setOrderCloseDate(orderJson.getTimestamp("_orderCloseTime"));
            followInfo.setOrderClosePrice(orderJson.getBigDecimal("_orderClosePrice"));
        }
        followInfo.setOrderMagic(orderJson.getBigDecimal("_magic"));

        /*计算佣金*/

        /*保存至数据库*/
        fuOrderFollowInfoMapper.insertSelective(followInfo);

    }

}
