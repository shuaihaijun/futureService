//+--------------------------------------------------------------+
//|     DWX_ZeroMQ_Server_v2.0.1_RC8.mq4
//|     Required: MQL-ZMQ from https://github.com/dingmaotu/mql-zmq
//+--------------------------------------------------------------+
#property copyright "Copyright 2017-2019, Darwinex Shuai."
#property link      "https://www.darwinex.com/"
#property version   "2.0.1"
#property strict
 

#include <Zmq/Zmq.mqh>

extern string PROJECT_NAME = "DWX_ZeroMQ_MT4_Server";
extern string ZEROMQ_PROTOCOL = "tcp";
extern string HOSTNAME = "127.0.0.1";
extern int PUSH_PORT = 32768;
extern int PULL_PORT = 32769;
extern int PUB_PORT = 32770;
extern int SUB_PORT = 32771;
extern int MILLISECOND_TIMER = 1;

extern string t0 = "--- Trading Parameters ---";
extern int MagicNumber = 123456;
extern int MaximumOrders = 1;
extern double MaximumLotSize = 0.01;
extern int MaximumSlippage = 3;
extern bool DMA_MODE = true;

extern string t1 = "--- ZeroMQ Configuration ---";
// Publish_MarketData 和 signalMode 不能同时开启
extern bool Publish_MarketData = false;

//全局变量
bool signalMode = false; 
//用来发送订单广播给跟随者
string signalHost = "127.0.0.1";
int signalPort = 33777;
//信号源用来监听订单
datetime LastOpenTime;
datetime LastCloseTime;
//信号源在仓订单列表
string signalAliveOrders=""; 

//监听服务器地址（用来初始化）
string serverHost = "127.0.0.1"; 
int serverPort = 32777;


//用来发送订单给服务器
string followHost = "127.0.0.1"; 
int followPort = 32666; 
//跟随上限为10个
string followSignalArray[10];
//已跟随订单列表
string followOrderList[];
//跟随规则列表（每个信号源一个）
string followRuleList[5]; 


int testCount=0;

string Publish_Symbols[7] = {
   "EURUSD","GBPUSD","USDJPY","USDCAD","AUDUSD","NZDUSD","USDCHF"
};

/*
string Publish_Symbols[28] = {
   "EURUSD","EURGBP","EURAUD","EURNZD","EURJPY","EURCHF","EURCAD",
   "GBPUSD","AUDUSD","NZDUSD","USDJPY","USDCHF","USDCAD","GBPAUD",
   "GBPNZD","GBPJPY","GBPCHF","GBPCAD","AUDJPY","CHFJPY","CADJPY",
   "AUDNZD","AUDCHF","AUDCAD","NZDJPY","NZDCHF","NZDCAD","CADCHF"
};
*/

// CREATE ZeroMQ Context
Context context(PROJECT_NAME);
// CREATE ZMQ_PUSH SOCKET
Socket pushSocket(context, ZMQ_PUSH);
// CREATE ZMQ_PULL SOCKET
Socket pullSocket(context, ZMQ_PULL);
// CREATE ZMQ_PUB SOCKET
Socket pubSocket(context, ZMQ_PUB);

// CREATE ZMQ_SUB SOCKET
Socket followSubSocket(context, ZMQ_SUB);
// CREATE ZMQ_SUB SOCKET
Socket subSocket(context, ZMQ_SUB);

// VARIABLES FOR LATER
uchar _data[];
ZmqMsg request;

//+------------------------------------------------------------------+
//| Expert initialization function                                   |
//+------------------------------------------------------------------+
int OnInit()
  {
//---

   EventSetMillisecondTimer(MILLISECOND_TIMER);     // Set Millisecond Timer to get client socket input
   
   context.setBlocky(false);
   
   //Send responses to PULL_PORT that client is listening on.
   // Print("[PUSH] Binding MT4 Server to Socket on Port " + IntegerToString(signalPort) + "..");
   // pushSocket.bind(StringFormat("%s://%s:%d", ZEROMQ_PROTOCOL, signalHost, signalPort));
   // pushSocket.setSendHighWaterMark(1);
   // pushSocket.setLinger(0);
   
   // Receive commands from PUSH_PORT that client is sending to.
   // Print("[PULL] Binding MT4 Server to Socket on Port " + IntegerToString(PUSH_PORT) + "..");   
   // pullSocket.bind(StringFormat("%s://%s:%d", ZEROMQ_PROTOCOL, HOSTNAME, PUSH_PORT));
   // pullSocket.setReceiveHighWaterMark(1);
   // pullSocket.setLinger(0);
   
   // 用于接受订阅消息 用于初始化
   Print("[PUB] connect MT4 Server to Socket on Port, "+serverHost+":" + IntegerToString(serverPort) + "..");
   subSocket.connect(StringFormat("%s://%s:%d", ZEROMQ_PROTOCOL, serverHost, serverPort));
      
   // 用于监听价格 发送给application
   if (Publish_MarketData == true)
   {
      // Send new market data to PUB_PORT that client is subscribed to.
      Print("[PUB] Binding MT4 Server to Socket on Port " + IntegerToString(PUB_PORT) + "..");
      pubSocket.bind(StringFormat("%s://%s:%d", ZEROMQ_PROTOCOL, HOSTNAME, PUB_PORT));
   }
      
//---
   return(INIT_SUCCEEDED);
  }
//+------------------------------------------------------------------+
//| Expert deinitialization function                                 |
//+------------------------------------------------------------------+
void OnDeinit(const int reason)
{
//---
    
   // Print("[PUSH] Unbinding MT4 Server from Socket on Port " + IntegerToString(PULL_PORT) + "..");
   // pushSocket.unbind(StringFormat("%s://%s:%d", ZEROMQ_PROTOCOL, HOSTNAME, PULL_PORT));
   
   // Print("[PULL] Unbinding MT4 Server from Socket on Port " + IntegerToString(PUSH_PORT) + "..");
   // pullSocket.unbind(StringFormat("%s://%s:%d", ZEROMQ_PROTOCOL, HOSTNAME, PUSH_PORT));
   
   if (Publish_MarketData == TRUE)
   {
      Print("[PUB] Unbinding MT4 Server from Socket on Port " + IntegerToString(PUB_PORT) + "..");
      pubSocket.unbind(StringFormat("%s://%s:%d", ZEROMQ_PROTOCOL, HOSTNAME, PUB_PORT));
   }
    if (signalMode == TRUE)
   {
      Print("[PUB] Unbinding MT4 Server from Socket on Port " + signalHost+ ":"+IntegerToString(signalPort));
      pushSocket.unbind(StringFormat("%s://%s:%d", ZEROMQ_PROTOCOL, signalHost, signalPort)); 
   
      //清除全局变量
      GlobalVariablesDeleteAll();
   }
   else
   {
      Print("[PUB] Unbinding MT4 Server from Socket on Port " + followHost+ ":"+IntegerToString(followPort));
      pushSocket.unbind(StringFormat("%s://%s:%d", ZEROMQ_PROTOCOL, followHost, followPort));
   }
   
   
   // Shutdown ZeroMQ Context
   context.shutdown();
   context.destroy(0);
   
   EventKillTimer();
}

//+------------------------------------------------------------------+
//| Expert tick function                                            |
//+------------------------------------------------------------------+
void OnTick()
{
   /*
      Use this OnTick() function to send market data to subscribed client.
   */
    
   if(!IsStopped() && Publish_MarketData == true)
   {
      for(int s = 0; s < ArraySize(Publish_Symbols); s++)
      {
         // Python clients can subscribe to a price feed by setting
         // socket options to the symbol name. For example:
         
         string _tick = GetBidAsk(Publish_Symbols[s]);
         // Print("Sending " + Publish_Symbols[s] + " " + _tick + " to PUB Socket " + IntegerToString(PUB_PORT) + "..");
        
         ZmqMsg reply(StringFormat("%s %s", Publish_Symbols[s], _tick));
         // ZmqMsg reply("abc pub server send!");
         // 监听订单，并发布广播
         pubSocket.send(reply, true); 
      }
   }
}

//+------------------------------------------------------------------+
//| Expert timer function                                            |
//+------------------------------------------------------------------+
void OnTimer()
{
   
   // 信号源发布 跟随者订阅
   if(signalMode){
      //处理信号源监听逻辑
      monitorSignal();   
         
   }else{
      // 初始化 /参数修改
      
      string accountInfo= AccountServer()+"&"+IntegerToString(AccountNumber()); 
      subSocket.setSubscribe("MultiBankFXInt-Demo1");
      subSocket.recv(request, true);
      if (request.size() > 0)
      {
         Print("recived init subMsg! 1");
         ZmqMsg reply = MessageHandlerInit(request);
      }
       
      subSocket.setSubscribe("MultiBankFXInt-Demo2");
      subSocket.recv(request, true);
      if (request.size() > 0)
      {
         Print("recived init subMsg! 2");
         ZmqMsg reply = MessageHandlerInit(request);
      }
      
      subSocket.setSubscribe("MultiBankFXInt-Demo3");
      subSocket.recv(request, true);
      if (request.size() > 0)
      {
         Print("recived init subMsg! 3");
         ZmqMsg reply = MessageHandlerInit(request);
      }
      // 匹配跟随者（这块儿有隐患 如变更信号源需要重新初始化）
      //string followSignalInfo[2];
      //for(int i = 0; i < ArraySize(followSignalArray); i++) {
      //   if(followSignalArray[i]==NULL || followSignalArray[i] ==""){
      //      break;
      //   }
         // 解析数据 格式为 signalHost1:signalPort1:signalServer1&signalAccountId1;signalHost2:signalPort2:signalServer2&signalAccountId2
      //   ParseStringMessage(":",followSignalArray[i], followSignalInfo);  
      //   followSubSocket.setSubscribe(followSignalInfo[2]);
      //   followSubSocket.recv(request, true);
      //   if (request.size() > 0)
      //   { 
      //      Print("listened signalOrder!"); 
      //     ZmqMsg reply = MessageHandlerFollow(request);
      //   }
      //}
   }
  
}


//处理信号源订单监听逻辑
void monitorSignal(){
 
   //用最后一个订单的时间来判断是否有新的开仓订单生成 
   if (OrderSelect(OrdersTotal()-1,SELECT_BY_POS)==true)
   {
      if(OrderOpenTime()>LastOpenTime){
         // 有新开仓订单 
         // 拼接订单
         string accountInfo=AccountServer()+"&"+IntegerToString(AccountNumber());
         string orderDeatail=accountInfo+"|SIGNALFOLLOWORDER|";
         orderDeatail=orderDeatail+"OPEN|";
         orderDeatail=orderDeatail+IntegerToString(OrderType());
         orderDeatail=orderDeatail+","+OrderSymbol(); 
         orderDeatail=orderDeatail+","+DoubleToString(OrderOpenPrice());
         orderDeatail=orderDeatail+","+DoubleToString(OrderStopLoss());
         orderDeatail=orderDeatail+","+DoubleToString(OrderTakeProfit()); 
         orderDeatail=orderDeatail+","+OrderComment();
         orderDeatail=orderDeatail+","+DoubleToString(OrderLots());
         orderDeatail=orderDeatail+","+DoubleToString(OrderMagicNumber());
         orderDeatail=orderDeatail+","+IntegerToString(OrderTicket()); 
         
         orderDeatail=orderDeatail+","+IntegerToString(OrderOpenTime());
         orderDeatail=orderDeatail+","+DoubleToString(OrderCommission());
         orderDeatail=orderDeatail+","+IntegerToString(OrderExpiration()); 
          
         Print("a new open order find!"+orderDeatail); 
         // 发送mq  signalMsg|signalAccount|action|orderInfo
         InformPullClient(pushSocket,orderDeatail); 
         // 设置时间
         LastOpenTime=OrderOpenTime();
         // 重置在仓订单信息
         resetSignalOrders();
      }
   }
   //用最后一个订单的时间来判断是否有新的关闭订单生成 
   if (OrderSelect(OrdersHistoryTotal()-1,SELECT_BY_POS,MODE_HISTORY)==true)
   {
      if(OrderCloseTime()>LastCloseTime){
         // 有新平仓订单  
         // 拼接订单
         string accountInfo=AccountServer()+"&"+IntegerToString(AccountNumber());
         string orderDeatail=accountInfo+"|SIGNALFOLLOWORDER|"; 
         // 判断是否是部分平仓
         int superiorTicket=isClosePartial();
         if(superiorTicket>0){
            //单个订单部分平仓
            orderDeatail=orderDeatail+"CLOSE_PARTIAL|"; 
         }else{
            //单个订单全部平仓
            orderDeatail=orderDeatail+"CLOSE|";
         }
         orderDeatail=orderDeatail+IntegerToString(OrderType());
         orderDeatail=orderDeatail+","+OrderSymbol(); 
         orderDeatail=orderDeatail+","+DoubleToString(OrderClosePrice());
         orderDeatail=orderDeatail+","+DoubleToString(OrderStopLoss());
         orderDeatail=orderDeatail+","+DoubleToString(OrderTakeProfit()); 
         orderDeatail=orderDeatail+","+OrderComment();
         orderDeatail=orderDeatail+","+DoubleToString(OrderLots());
         orderDeatail=orderDeatail+","+DoubleToString(OrderMagicNumber());  
         orderDeatail=orderDeatail+","+IntegerToString(OrderTicket()); 
         
         orderDeatail=orderDeatail+","+IntegerToString(OrderCloseTime());  
         orderDeatail=orderDeatail+","+DoubleToString(OrderCommission());
         orderDeatail=orderDeatail+","+IntegerToString(OrderExpiration()); 
         if(superiorTicket>0){
            orderDeatail=orderDeatail+","+IntegerToString(superiorTicket);  
         }
          
         Print("a new close order find!"+orderDeatail); 
         // 发送mq  signalMsg|signalAccount|action|orderInfo
         InformPullClient(pushSocket,orderDeatail); 
         // pubSocket.send(reply, true);
         // 设置时间
         LastCloseTime=OrderCloseTime();
         // 重置在仓订单信息
         resetSignalOrders();
      }
   }
}

//+------------------------------------------------------------------+
//初始化 修改（信号源不能修改）
ZmqMsg MessageHandlerInit(ZmqMsg &_request) {
   
   // Output object
   ZmqMsg reply;
   
   // Message components for later.
   string dataArray[11];
   string components[11];
   bool thisFollow=false;
   
   if(_request.size() > 0) {
   
      // Get data from request   
      ArrayResize(_data, _request.size());
      _request.getData(_data);
      string dataStr = CharArrayToString(_data);
      
      Print("recived message is :"+dataStr);
      
      // 数据格式 以“|”分割 subMsg(订阅标识)|signalInit/followInit/trade(操作)|param1(signalPort/signalArray/signal)|param2(tradeInfo/accountInfo)(参数以“;”分割)
      // 1 初始化信号源  accountInfo|SIGNALINIT|signalHost:signalPort
      // 2 初始化跟随者  accountInfo|FOLLOWINIT|serverHost:serverPort#followHost:followPort#signalArray#followOrderList|ruleList(signalAccount:ruleType:ruleAmount:limitUpper)
      // 3 跟随信息  accountInfo|SIGNALFOLLOWORDER|action|tradeOrder
      // 4 跟随者订单  accountInfo|signalAccount|action|tradeOrder
      // 解析数据 账户格式为 server&accountnumber
      ParseStringMessage("|",dataStr, dataArray);
       
      if(dataArray[1] == "SIGNALINIT"){
         Print("SIGNALINIT");
         // 初始化信号源信息
         initSignal(dataArray[0],dataArray[2]); 
         
      }else if(dataArray[1] == "FOLLOWINIT"){
         Print("FOLLOWINIT");
         // 初始化跟随信息
         initFollower(dataArray[0],dataArray[2],dataArray[3]); 
         
      }
      
   } 
   
   return(reply);
}

//处理跟随订单
ZmqMsg MessageHandlerFollow(ZmqMsg &_request) {
   
   // Output object
   ZmqMsg reply;
   
   // Message components for later.
   string dataArray[3];
   string components[11]; 
   string resultOrder="";
   
   if(_request.size() > 0) {
   
      // Get data from request   
      ArrayResize(_data, _request.size());
      _request.getData(_data);
      string dataStr = CharArrayToString(_data);
      
      Print("recived signalOrder is :"+dataStr);
      
      // 数据格式 以“|”分割 subMsg(订阅标识)|signalInit/followInit/trade(操作)|param1(signalPort/signalArray/signal)|param2(tradeInfo/accountInfo)(参数以“;”分割)
      // 1 初始化信号源  accountInfo|SIGNALINIT|signalHost:signalPort
      // 2 初始化跟随者  accountInfo|FOLLOWINIT|serverHost:serverPort#followHost:followPort#signalArray#followOrderList|ruleList(signalAccount:ruleType:ruleAmount:limitUpper)
      // 3 跟随信息  signalAccountInfo|SIGNALFOLLOWORDER|action|tradeOrder
      // 4 跟随者订单  accountInfo|signalAccount|action|tradeOrder
      // 解析数据 账户格式为 server&accountnumber
      ParseStringMessage("|",dataStr, dataArray);
       
      if(dataArray[1] == "SIGNALFOLLOWORDER"){
         // 跟随订单
         Print("TRADE mactch，signal:" + dataArray[0] + ", follow:"+AccountServer()+"&"+IntegerToString(AccountNumber()));
         
         // 拿到signalOrder数据后，根据规则进行
         dealFollowRule(dataArray[0],dataArray[2],dataArray[3],resultOrder);
         
         if(resultOrder == NULL || resultOrder == ""){
            //没有找到相应的订单
             Print("  mactch signalRule failed ! ");
            return reply;
         }
         
         ParseStringMessage("|",resultOrder,components);
                  
         // Interpret data 交易完成后，需要将订单信息回传保存
         InterpretZmqMessage(&pushSocket, components, dataArray[0]);
         
      }
      
   } 
   
   return(reply);
}


// 初始化信号源
void initSignal(string paramAccount,string signalInfo){
  
   string thisAccount=AccountServer()+"&"+IntegerToString(AccountNumber());
     
   if(paramAccount != thisAccount){
      // 账户不匹配
      Print("thisAccount:"+thisAccount+",paramAccount:"+paramAccount);
      return;
   }
   string signalArray[2]; 
   //解析数据 signalHost;signalPort
   ParseStringMessage(":",signalInfo, signalArray); 
     
   //设置信号源绑定地址
   signalHost=signalArray[0];
   //设置信号源端口
   signalPort=StringToInteger(signalArray[1]);
   //信号源标识
   signalMode=true;
   // 初始化时间戳
   LastOpenTime=TimeCurrent();
   LastCloseTime=TimeCurrent(); 
    
   //初始化MQ
   //Print("[PUB] Binding MT4 Server to Socket on Port " + signalHost+ ":"+IntegerToString(signalPort));
   //pubSocket.bind(StringFormat("%s://%s:%d", ZEROMQ_PROTOCOL, signalHost, signalPort));
   
   //Send responses to PULL_PORT that client is listening on.
   Print("[PUSH] Binding MT4 Server to Socket on Port "+ signalHost+ ":"+IntegerToString(signalPort));
   pushSocket.bind(StringFormat("%s://%s:%d", ZEROMQ_PROTOCOL, signalHost, signalPort));
   pushSocket.setSendHighWaterMark(1);
   pushSocket.setLinger(0);
   
   //初始化信号源在仓订单
   resetSignalOrders();
   
   //给回个确认消息 
   InformPullClient(pushSocket,thisAccount);
}


// 初始化跟随者
void initFollower(string paramAccount,string followInfo,string rule){
   string thisAccount=AccountServer()+"&"+IntegerToString(AccountNumber());
   if(paramAccount != thisAccount){
      // 账户不匹配
      return;
   } 
   string followData[3];
   string followDataServer[2];
   string followDataFollow[2];
   string followDataSignal[2];
   string followSignals[11];
   
   //解析数据 serverHost:serverPort#followHost:followPort#signalArray(signalHost1:signalPort1:signalServer1&signalAccountId1)#followOrderId:signalOrderId(331344:34324324;321344:4324324)
   ParseStringMessage("#", followInfo, followData);
   
   //解析数据 serverHost:serverPort
   ParseStringMessage(":", followData[0], followDataServer);
   //初始化服务器地址
   serverHost=followDataServer[0];
   serverPort=StringToInteger(followDataServer[1]);
   
   //解析数据 followHost:followPort
   ParseStringMessage(":", followData[1], followDataFollow);
   //初始化跟随者地址
   followHost=followDataFollow[0];
   followPort=StringToInteger(followDataFollow[1]);
   
   // 设置跟随这的跟随信号源
   ParseStringMessage(";", followData[2], followSignals); 
   //初始化信号源
   for(int i=0;i<ArraySize(followSignals);i++){
      // signalHost:signalPort:signalServer&signalAccountId  
      followSignalArray[i]=followSignals[i];
   }
   
   //初始化跟随订单
   ParseStringMessage(";", followData[3], followOrderList);
   //初始化跟随规则
   ParseStringMessage(";", rule, followRuleList);
   
   //初始化跟随者MQ
   // 发送端，跟随完成后发送订单
   Print("[PUSH] Binding MT4 Server to Socket on Port, "+ followHost+ ":"+IntegerToString(followPort));
   pushSocket.bind(StringFormat("%s://%s:%d", ZEROMQ_PROTOCOL, followHost, followPort));
   pushSocket.setSendHighWaterMark(1);
   pushSocket.setLinger(0);
   
   //给回个确认消息 
   InformPullClient(pushSocket,paramAccount);
}


// 重置信号源在仓订单
void resetSignalOrders(){
   // 初始化在仓订单字符串
   signalAliveOrders="";
   // 删除所有全局变量
   GlobalVariablesDeleteAll(NULL);
   // 重置参数
   for(int i=0;i<OrdersTotal();i++){
      if(OrderSelect(i,SELECT_BY_POS)==true){
         if(signalAliveOrders == ""){
            signalAliveOrders=IntegerToString(OrderTicket()); 
         }else{
            signalAliveOrders=signalAliveOrders+","+IntegerToString(OrderTicket()); 
         }
         // 将订单对应的手数保存在全局变量中
         GlobalVariableSet(IntegerToString(OrderTicket()),OrderLots());
      }
   }
}

// 判斷是否是部分关闭
int isClosePartial(){
   //检测到关闭订单后 先判断在仓订单是否有减少
   string result[];
   ParseStringMessage(",",signalAliveOrders,result);
   if(ArraySize(result) == OrdersTotal()){
      //有关闭订单 但是订单总数没变，所以是部分关闭
      //找到具体的订单
      for(int i=0;i<OrdersTotal();i++){
         if(OrderLots()!=GlobalVariableGet(IntegerToString(OrderTicket()))){
            //找到变动的订单
            return OrderTicket();
         }
      }
   }
   return 0;
}

// 处理跟单规则
void dealFollowRule(string signalAccount,string aciton,string signalOrder,string followOrder){
   // action OPEN CLOSE CLOSE_PARTIAL
   // signalOrder=type,symbol,closePrice/openPrice,stopLoss,takeProfit,comment,lots,magic,ticket,closeTime/openTime,commission,expiration,superiorTicket
   // followOrder=TRADE|ACTION|TYPE|Symbol|Open/Close|SL|TP|Comment|Lots|Magic|Ticket  
   
   // 根据信号源查找跟单规则 
   string followRule[4];
   bool isMatched = false;
   for(int i=0;i<ArraySize(followRuleList);i++){
      ParseStringMessage(":",followRuleList[i],followRule);
      if(followRule[0] == signalAccount){
         //匹配到改信号源规则
          isMatched=true; 
      }
   }
   if(!isMatched){
      //根据信号源匹配规则失败，返回null
      followOrder=NULL;
      return;
   }
   
   // 解析数据
   string signalOrderDetail[15];
   ParseStringMessage(",",signalOrder,signalOrderDetail);
   //followRule[0] 0 按手数比例；1 按固定金额；2 按固定手数  ruleType,ruleAmount,limitLower,limitUpper
   string lots = signalOrderDetail[6];
   string ticket= signalOrderDetail[8];
   followOrder="TRADE|"+aciton+"|"+signalOrderDetail[0]+"|"+signalOrderDetail[1]+"|"+signalOrderDetail[2]+"|"+signalOrderDetail[3]+"|"+signalOrderDetail[4]+"|"+signalOrderDetail[5]+"|";
   // open/close/closePart
   if(aciton == "OPEN"){
      //根据规则确定手数  followRule[0] (0 按手数比例；1 按固定金额；2 按固定手数)  ruleType:ruleAmount:limitUpper（最低净值/最低净值百分比）
      if(StrToInteger(followRule[0]) == 0){
         //0 按手数比例
         lots = DoubleToStr(StrToDouble(signalOrderDetail[6]) * StrToDouble(followRule[1]));
      }else if(StrToInteger(followRule[0]) == 1){
         //1 按固定金额
         lots = DoubleToStr(StrToDouble(followRule[1]) / StrToDouble(signalOrderDetail[1]));
      }else{
         //2 按固定手数
         lots = followRule[1];
      }
      // 判断是否达到上线金额
      // 判断是否超过 最低净值/最低净值百分比
   }else if(aciton == "CLOSE" || aciton == "CLOSE_PARTIAL"){
      // 根据信号源的订单 找到对应的订单，找不到不做跟单操作（无订单可关闭） 
      // 331344:34324324;321344:4324324（followOrderId:signalOrderId）
      string resultTicket; 
      string ticketCP[];
      for(int i=0;i< ArraySize(followOrderList);i++){
         ParseStringMessage(":",followOrderList[i],ticketCP);
         if(ticketCP[0] == signalOrderDetail[8]){
            resultTicket=ticketCP[1];
            break;
         }
      }
      if(resultTicket == NULL || resultTicket == ""){
         //没有找到相应的订单，无法跟单操作
         Print(" no follow Order find! signalOrder:"+signalOrderDetail[8]);
         followOrder=NULL;
         return;
      }
      ticket=resultTicket;
      
      if( aciton == "CLOSE_PARTIAL"){
         // 需要根据规则 算出需要跟随的手数
         //根据规则确定手数  followRule[0] (0 按手数比例；1 按固定金额；2 按固定手数)  ruleType:ruleAmount:limitUpper（最低净值/最低净值百分比）
         if(StrToInteger(followRule[0]) == 0){
            //0 按手数比例
            lots = DoubleToStr(StrToDouble(signalOrderDetail[6]) * StrToDouble(followRule[1]));
         }else if(StrToInteger(followRule[0]) == 1){
            //1 按固定金额
            lots = DoubleToStr(StrToDouble(followRule[1]) / StrToDouble(signalOrderDetail[1]));
         }else{
            //2 按固定手数
            lots = followRule[1];
         }
      }
   }
   followOrder = followOrder + lots+"|"+signalOrderDetail[7]+"|"+ticket;
   
}

// Interpret Zmq Message and perform actions
void InterpretZmqMessage(Socket &pSocket, string &compArray[], string signalAccount) {

   // Print("ZMQ: Interpreting Message..");
   
   // Message Structures:
   
   // 1) Trading
   // TRADE|ACTION|TYPE|SYMBOL|PRICE|SL|TP|COMMENT|TICKET
   // e.g. TRADE|OPEN|1|EURUSD|0|50|50|R-to-MetaTrader4|12345678
   
   // The 12345678 at the end is the ticket ID, for MODIFY and CLOSE.
   
   // 2) Data Requests
   
   // 2.1) RATES|SYMBOL   -> Returns Current Bid/Ask
   
   // 2.2) DATA|SYMBOL|TIMEFRAME|START_DATETIME|END_DATETIME
   
   // NOTE: datetime has format: D'2015.01.01 00:00'
   
   /*
      compArray[0] = TRADE or RATES
      If RATES -> compArray[1] = Symbol
      
      If TRADE ->
         compArray[0] = TRADE
         compArray[1] = ACTION (e.g. OPEN, MODIFY, CLOSE)
         compArray[2] = TYPE (e.g. OP_BUY, OP_SELL, etc - only used when ACTION=OPEN)
         
         // ORDER TYPES: 
         // https://docs.mql4.com/constants/tradingconstants/orderproperties
         
         // OP_BUY = 0
         // OP_SELL = 1
         // OP_BUYLIMIT = 2
         // OP_SELLLIMIT = 3
         // OP_BUYSTOP = 4
         // OP_SELLSTOP = 5
         
         compArray[3] = Symbol (e.g. EURUSD, etc.)
         compArray[4] = Open/Close Price (ignored if ACTION = MODIFY)
         compArray[5] = SL
         compArray[6] = TP
         compArray[7] = Trade Comment
         compArray[8] = Lots
         compArray[9] = Magic Number
         compArray[10] = Ticket Number (MODIFY/CLOSE)
   */
   
   int switch_action = 0;
   
   if(compArray[0] == "TRADE" && compArray[1] == "OPEN")
      switch_action = 1;
   if(compArray[0] == "TRADE" && compArray[1] == "MODIFY")
      switch_action = 2;
   if(compArray[0] == "TRADE" && compArray[1] == "CLOSE")
      switch_action = 3;
   if(compArray[0] == "TRADE" && compArray[1] == "CLOSE_PARTIAL")
      switch_action = 4;
   if(compArray[0] == "TRADE" && compArray[1] == "CLOSE_MAGIC")
      switch_action = 5;
   if(compArray[0] == "TRADE" && compArray[1] == "CLOSE_ALL")
      switch_action = 6;
   if(compArray[0] == "TRADE" && compArray[1] == "GET_OPEN_TRADES")
      switch_action = 7;
   if(compArray[0] == "DATA")
      switch_action = 8;
   
   string zmq_ret = "";
   string ret = "";
   int ticket = -1;
   bool ans = FALSE;
   string accountInfo=AccountServer()+"&"+IntegerToString(AccountNumber());
   zmq_ret = accountInfo+"|"+signalAccount+"|"+compArray[1]+"|";
   
   switch(switch_action) 
   {
      case 1: // OPEN TRADE
           
         zmq_ret =zmq_ret+ "{";
         // Function definition:
         ticket = DWX_OpenOrder(compArray[3], StrToInteger(compArray[2]), StrToDouble(compArray[8]), 
                                 StrToDouble(compArray[4]), StrToInteger(compArray[5]), StrToInteger(compArray[6]), 
                                 compArray[7], StrToInteger(compArray[9]), zmq_ret);
                                 
         // Send TICKET back as JSON
         InformPullClient(pSocket, zmq_ret + "}");
         
         break;
         
      case 2: // MODIFY SL/TP
         
         zmq_ret =zmq_ret+  "{'_action': 'MODIFY'";
         
         // Function definition:
         ans = DWX_SetSLTP(StrToInteger(compArray[10]), StrToDouble(compArray[5]), StrToDouble(compArray[6]), 
                           StrToInteger(compArray[9]), StrToInteger(compArray[2]), StrToDouble(compArray[4]), 
                           compArray[3], 3, zmq_ret);
         
         InformPullClient(pSocket, zmq_ret + "}");
         
         break;
         
      case 3: // CLOSE TRADE 
         
         zmq_ret =zmq_ret+ "{";
         // IMPLEMENT CLOSE TRADE LOGIC HERE
         DWX_CloseOrder_Ticket(StrToInteger(compArray[10]), zmq_ret);
         
         InformPullClient(pSocket, zmq_ret + "}");
         
         break;
      
      case 4: // CLOSE PARTIAL 
         
         zmq_ret =zmq_ret+ "{";
         ans = DWX_ClosePartial(StrToDouble(compArray[8]), zmq_ret, StrToInteger(compArray[10]));
            
         InformPullClient(pSocket, zmq_ret + "}");
         
         break;
         
      case 5: // CLOSE MAGIC 
         
         zmq_ret =zmq_ret+ "{";
         DWX_CloseOrder_Magic(StrToInteger(compArray[9]), zmq_ret);
            
         InformPullClient(pSocket, zmq_ret + "}");
         
         break;
         
      case 6: // CLOSE ALL ORDERS 
         
         zmq_ret =zmq_ret+ "{";
         DWX_CloseAllOrders(zmq_ret);
            
         InformPullClient(pSocket, zmq_ret + "}");
         
         break;
      
      case 7: // GET OPEN ORDERS 
         
         zmq_ret =zmq_ret+ "{";
         DWX_GetOpenOrders(zmq_ret);
            
         InformPullClient(pSocket, zmq_ret + "}");
         
         break;
            
      case 8: // DATA REQUEST 
         
         zmq_ret =zmq_ret+ "{";
         DWX_GetData(compArray, zmq_ret);
         
         InformPullClient(pSocket, zmq_ret + "}");
         
         break;
         
      default: 
         break;
   }
}

// Parse Zmq Message
void ParseStringMessage(string sep, string& message, string& retArray[]) {
    
   ushort u_sep = StringGetCharacter(sep,0);  
   int splits = StringSplit(message, u_sep, retArray); 
   
}


//+------------------------------------------------------------------+
// Generate string for Bid/Ask by symbol
string GetBidAsk(string symbol) {
   
   MqlTick last_tick;
    
   if(SymbolInfoTick(symbol,last_tick))
   {
       return(StringFormat("%f;%f", last_tick.bid, last_tick.ask));
   }
   
   // Default
   return "";
}

// Get data for request datetime range
void DWX_GetData(string& compArray[], string& zmq_ret) {
         
   // Format: DATA|SYMBOL|TIMEFRAME|START_DATETIME|END_DATETIME
   
   double price_array[];
   datetime time_array[];
   
   // Get prices
   int price_count = CopyClose(compArray[1], 
                  StrToInteger(compArray[2]), StrToTime(compArray[3]),
                  StrToTime(compArray[4]), price_array);
   
   // Get timestamps
   int time_count = CopyTime(compArray[1], 
                  StrToInteger(compArray[2]), StrToTime(compArray[3]),
                  StrToTime(compArray[4]), time_array);
      
   zmq_ret = zmq_ret + "'_action': 'DATA'";
               
   if (price_count > 0) {
      
      zmq_ret = zmq_ret + ", '_data': {";
      
      // Construct string of price|price|price|.. etc and send to PULL client.
      for(int i = 0; i < price_count; i++ ) {
         
         if(i == 0)
            zmq_ret = zmq_ret + "'" + TimeToString(time_array[i]) + "': " + DoubleToString(price_array[i]);
         else
            zmq_ret = zmq_ret + ", '" + TimeToString(time_array[i]) + "': " + DoubleToString(price_array[i]);
       
      }
      
      zmq_ret = zmq_ret + "}";
      
   }
   else {
      zmq_ret = zmq_ret + ", " + "'_response': 'NOT_AVAILABLE'";
   }
         
}

// Inform Client
void InformPullClient(Socket& pSocket, string message) {
   
   //发送处理结果
   ZmqMsg pushReply(StringFormat("%s", message));
   
   pSocket.send(pushReply,true); // NON-BLOCKING
   
   Print("MQ sended message :"+message);
   
}

/*
 ############################################################################
 ############################################################################
 ############################################################################
*/

// OPEN NEW ORDER
int DWX_OpenOrder(string _symbol, int _type, double _lots, double _price, double _SL, double _TP, string _comment, int _magic, string &zmq_ret) {
   
   int ticket, error;
   
   zmq_ret = zmq_ret + "'_action': 'EXECUTION'";
   
   if(_lots > MaximumLotSize) {
      zmq_ret = zmq_ret + ", " + "'_response': 'LOT_SIZE_ERROR', 'response_value': 'MAX_LOT_SIZE_EXCEEDED'";
      return(-1);
   }
   
   double sl = _SL;
   double tp = _TP;
  
   // Else
   if(DMA_MODE) {
      sl = 0.0;
      tp = 0.0;
   } 
   
   if(_symbol == "NULL") {
      ticket = OrderSend(Symbol(), _type, _lots, _price, MaximumSlippage, sl, tp, _comment, _magic);
   } else {
      ticket = OrderSend(_symbol, _type, _lots, _price, MaximumSlippage, sl, tp, _comment, _magic);
   }
   if(ticket < 0) {
      // Failure
      error = GetLastError();
      zmq_ret = zmq_ret + ", " + "'_response': '" + IntegerToString(error) + "', 'response_value': '" + ErrorDescription(error) + "'";
      return(-1*error);
   }

   int tmpRet = OrderSelect(ticket, SELECT_BY_TICKET, MODE_TRADES);
   
   zmq_ret = zmq_ret + ", " + "'_magic': " + IntegerToString(_magic) + ", '_ticket': " + IntegerToString(OrderTicket()) + ", '_open_time': '" + TimeToStr(OrderOpenTime(),TIME_DATE|TIME_SECONDS) + "', '_open_price': " + DoubleToString(OrderOpenPrice());

   if(DMA_MODE) {
   
      int retries = 3;
      while(true) {
         retries--;
         if(retries < 0) return(0);
         
         if((_SL == 0 && _TP == 0) || (OrderStopLoss() == _SL && OrderTakeProfit() == _TP)) {
            return(ticket);
         }

         if(DWX_IsTradeAllowed(30, zmq_ret) == 1) {
            if(DWX_SetSLTP(ticket, _SL, _TP, _magic, _type, _price, _symbol, retries, zmq_ret)) {
               return(ticket);
            }
            if(retries == 0) {
               zmq_ret = zmq_ret + ", '_response': 'ERROR_SETTING_SL_TP'";
               return(-11111);
            }
         }

         Sleep(MILLISECOND_TIMER);
      }

      zmq_ret = zmq_ret + ", '_response': 'ERROR_SETTING_SL_TP'";
      zmq_ret = zmq_ret + "}";
      return(-1);
   }

    // Send zmq_ret to Python Client
    zmq_ret = zmq_ret + "}";
    
   return(ticket);
}

// SET SL/TP
bool DWX_SetSLTP(int ticket, double _SL, double _TP, int _magic, int _type, double _price, string _symbol, int retries, string &zmq_ret) {
   
   if (OrderSelect(ticket, SELECT_BY_TICKET) == true)
   {
      int dir_flag = -1;
      
      if (OrderType() == 0 || OrderType() == 2 || OrderType() == 4)
         dir_flag = 1;
    
      double vpoint  = MarketInfo(OrderSymbol(), MODE_POINT);
      int    vdigits = (int)MarketInfo(OrderSymbol(), MODE_DIGITS);
      
      if(OrderModify(ticket, OrderOpenPrice(), NormalizeDouble(OrderOpenPrice()-_SL*dir_flag*vpoint,vdigits), NormalizeDouble(OrderOpenPrice()+_TP*dir_flag*vpoint,vdigits), 0, 0)) {
         zmq_ret = zmq_ret + ", '_sl': " + DoubleToString(_SL) + ", '_tp': " + DoubleToString(_TP);
         return(true);
      } else {
         int error = GetLastError();
         zmq_ret = zmq_ret + ", '_response': '" + IntegerToString(error) + "', '_response_value': '" + ErrorDescription(error) + "', '_sl_attempted': " + DoubleToString(NormalizeDouble(OrderOpenPrice()-_SL*dir_flag*vpoint,vdigits)) + ", '_tp_attempted': " + DoubleToString(NormalizeDouble(OrderOpenPrice()+_TP*dir_flag*vpoint,vdigits));
   
         if(retries == 0) {
            RefreshRates();
            DWX_CloseAtMarket(-1, zmq_ret);
            // int lastOrderErrorCloseTime = TimeCurrent();
         }
         
         return(false);
      }
   }    
   
   // return(true);
   return(false);
}

// CLOSE AT MARKET
bool DWX_CloseAtMarket(double size, string &zmq_ret) {

   int error;

   int retries = 3;
   while(true) {
      retries--;
      if(retries < 0) return(false);

      if(DWX_IsTradeAllowed(30, zmq_ret) == 1) {
         if(DWX_ClosePartial(size, zmq_ret)) {
            // trade successfuly closed
            return(true);
         } else {
            error = GetLastError();
            zmq_ret = zmq_ret + ", '_response': '" + IntegerToString(error) + "', '_response_value': '" + ErrorDescription(error) + "'";
         }
      }

   }

   return(false);
}

// CLOSE PARTIAL SIZE
bool DWX_ClosePartial(double size, string &zmq_ret, int ticket = 0) {

   RefreshRates();
   double priceCP;
   
   bool close_ret = False;
   
   if(OrderType() != OP_BUY && OrderType() != OP_SELL) {
     return(true);
   }

   if(OrderType() == OP_BUY) {
      priceCP = DWX_GetBid(OrderSymbol());
   } else {
      priceCP = DWX_GetAsk(OrderSymbol());
   }

   // If the function is called directly, setup init() JSON here.
   if(ticket != 0) {
      zmq_ret = zmq_ret + "'_action': 'CLOSE', '_ticket': " + IntegerToString(ticket);
      zmq_ret = zmq_ret + ", '_response': 'CLOSE_PARTIAL'";
   }
   
   int local_ticket = 0;
   
   if (ticket != 0)
      local_ticket = ticket;
   else
      local_ticket = OrderTicket();
   
   if(size < 0.01 || size > OrderLots()) {
      close_ret = OrderClose(local_ticket, OrderLots(), priceCP, MaximumSlippage);
      zmq_ret = zmq_ret + ", '_close_price': " + DoubleToString(priceCP) + ", '_close_lots': " + DoubleToString(OrderLots());
      return(close_ret);
   } else {
      close_ret = OrderClose(local_ticket, size, priceCP, MaximumSlippage);
      zmq_ret = zmq_ret + ", '_close_price': " + DoubleToString(priceCP) + ", '_close_lots': " + DoubleToString(size);
      return(close_ret);
   }   
}

// CLOSE ORDER (by Magic Number)
void DWX_CloseOrder_Magic(int _magic, string &zmq_ret) {

   bool found = false;

   zmq_ret = zmq_ret + "'_action': 'CLOSE_ALL_MAGIC'";
   zmq_ret = zmq_ret + ", '_magic': " + IntegerToString(_magic);
   
   zmq_ret = zmq_ret + ", '_responses': {";
   
   for(int i=OrdersTotal()-1; i >= 0; i--) {
      if (OrderSelect(i,SELECT_BY_POS)==true && OrderMagicNumber() == _magic) {
         found = true;
         
         zmq_ret = zmq_ret + IntegerToString(OrderTicket()) + ": {'_symbol':'" + OrderSymbol() + "'";
         
         if(OrderType() == OP_BUY || OrderType() == OP_SELL) {
            DWX_CloseAtMarket(-1, zmq_ret);
            zmq_ret = zmq_ret + ", '_response': 'CLOSE_MARKET'";
            
            if (i != 0)
               zmq_ret = zmq_ret + "}, ";
            else
               zmq_ret = zmq_ret + "}";
               
         } else {
            zmq_ret = zmq_ret + ", '_response': 'CLOSE_PENDING'";
            
            if (i != 0)
               zmq_ret = zmq_ret + "}, ";
            else
               zmq_ret = zmq_ret + "}";
               
            int tmpRet = OrderDelete(OrderTicket());
         }
      }
   }

   zmq_ret = zmq_ret + "}";
   
   if(found == false) {
      zmq_ret = zmq_ret + ", '_response': 'NOT_FOUND'";
   }
   else {
      zmq_ret = zmq_ret + ", '_response_value': 'SUCCESS'";
   }

}

// CLOSE ORDER (by Ticket)
void DWX_CloseOrder_Ticket(int _ticket, string &zmq_ret) {

   bool found = false;

   zmq_ret = zmq_ret + "'_action': 'CLOSE', '_ticket': " + IntegerToString(_ticket);

   for(int i=0; i<OrdersTotal(); i++) {
      if (OrderSelect(i,SELECT_BY_POS)==true && OrderTicket() == _ticket) {
         found = true;

         if(OrderType() == OP_BUY || OrderType() == OP_SELL) {
            DWX_CloseAtMarket(-1, zmq_ret);
            zmq_ret = zmq_ret + ", '_response': 'CLOSE_MARKET'";
         } else {
            zmq_ret = zmq_ret + ", '_response': 'CLOSE_PENDING'";
            int tmpRet = OrderDelete(OrderTicket());
         }
      }
   }

   if(found == false) {
      zmq_ret = zmq_ret + ", '_response': 'NOT_FOUND'";
   }
   else {
      zmq_ret = zmq_ret + ", '_response_value': 'SUCCESS'";
   }

}

// CLOSE ALL ORDERS
void DWX_CloseAllOrders(string &zmq_ret) {

   bool found = false;

   zmq_ret = zmq_ret + "'_action': 'CLOSE_ALL'";
   
   zmq_ret = zmq_ret + ", '_responses': {";
   
   for(int i=OrdersTotal()-1; i >= 0; i--) {
      if (OrderSelect(i,SELECT_BY_POS)==true) {
      
         found = true;
         
         zmq_ret = zmq_ret + IntegerToString(OrderTicket()) + ": {'_symbol':'" + OrderSymbol() + "', '_magic': " + IntegerToString(OrderMagicNumber());
         
         if(OrderType() == OP_BUY || OrderType() == OP_SELL) {
            DWX_CloseAtMarket(-1, zmq_ret);
            zmq_ret = zmq_ret + ", '_response': 'CLOSE_MARKET'";
            
            if (i != 0)
               zmq_ret = zmq_ret + "}, ";
            else
               zmq_ret = zmq_ret + "}";
               
         } else {
            zmq_ret = zmq_ret + ", '_response': 'CLOSE_PENDING'";
            
            if (i != 0)
               zmq_ret = zmq_ret + "}, ";
            else
               zmq_ret = zmq_ret + "}";
               
            int tmpRet = OrderDelete(OrderTicket());
         }
      }
   }

   zmq_ret = zmq_ret + "}";
   
   if(found == false) {
      zmq_ret = zmq_ret + ", '_response': 'NOT_FOUND'";
   }
   else {
      zmq_ret = zmq_ret + ", '_response_value': 'SUCCESS'";
   }

}

// GET OPEN ORDERS
void DWX_GetOpenOrders(string &zmq_ret) {

   bool found = false;

   zmq_ret = zmq_ret + "'_action': 'OPEN_TRADES'";
   zmq_ret = zmq_ret + ", '_trades': {";
   
   for(int i=OrdersTotal()-1; i>=0; i--) {
      found = true;
      
      if (OrderSelect(i,SELECT_BY_POS)==true) {
      
         zmq_ret = zmq_ret + IntegerToString(OrderTicket()) + ": {";
         
         zmq_ret = zmq_ret + "'_magic': " + IntegerToString(OrderMagicNumber()) + ", '_symbol': '" + OrderSymbol() + "', '_lots': " + DoubleToString(OrderLots()) + ", '_type': " + IntegerToString(OrderType()) + ", '_open_price': " + DoubleToString(OrderOpenPrice()) + ", '_open_time': '" + TimeToStr(OrderOpenTime(),TIME_DATE|TIME_SECONDS) + "', '_SL': " + DoubleToString(OrderStopLoss()) + ", '_TP': " + DoubleToString(OrderTakeProfit()) + ", '_pnl': " + DoubleToString(OrderProfit()) + ", '_comment': '" + OrderComment() + "'";
         
         if (i != 0)
            zmq_ret = zmq_ret + "}, ";
         else
            zmq_ret = zmq_ret + "}";
      }
   }
   zmq_ret = zmq_ret + "}";

}

// CHECK IF TRADE IS ALLOWED
int DWX_IsTradeAllowed(int MaxWaiting_sec, string &zmq_ret) {
    
    if(!IsTradeAllowed()) {
    
        int StartWaitingTime = (int)GetTickCount();
        zmq_ret = zmq_ret + ", " + "'_response': 'TRADE_CONTEXT_BUSY'";
        
        while(true) {
            
            if(IsStopped()) {
                zmq_ret = zmq_ret + ", " + "'_response_value': 'EA_STOPPED_BY_USER'";
                return(-1);
            }
            
            int diff = (int)(GetTickCount() - StartWaitingTime);
            if(diff > MaxWaiting_sec * 1000) {
                zmq_ret = zmq_ret + ", '_response': 'WAIT_LIMIT_EXCEEDED', '_response_value': " + IntegerToString(MaxWaiting_sec);
                return(-2);
            }
            // if the trade context has become free,
            if(IsTradeAllowed()) {
                zmq_ret = zmq_ret + ", '_response': 'TRADE_CONTEXT_NOW_FREE'";
                RefreshRates();
                return(1);
            }
            
          }
    } else {
        return(1);
    }
    
    return(1);
}

string ErrorDescription(int error_code)
  {
   string error_string;
//----
   switch(error_code)
     {
      //---- codes returned from trade server
      case 0:
      case 1:   error_string="no error";                                                  break;
      case 2:   error_string="common error";                                              break;
      case 3:   error_string="invalid trade parameters";                                  break;
      case 4:   error_string="trade server is busy";                                      break;
      case 5:   error_string="old version of the client terminal";                        break;
      case 6:   error_string="no connection with trade server";                           break;
      case 7:   error_string="not enough rights";                                         break;
      case 8:   error_string="too frequent requests";                                     break;
      case 9:   error_string="malfunctional trade operation (never returned error)";      break;
      case 64:  error_string="account disabled";                                          break;
      case 65:  error_string="invalid account";                                           break;
      case 128: error_string="trade timeout";                                             break;
      case 129: error_string="invalid price";                                             break;
      case 130: error_string="invalid stops";                                             break;
      case 131: error_string="invalid trade volume";                                      break;
      case 132: error_string="market is closed";                                          break;
      case 133: error_string="trade is disabled";                                         break;
      case 134: error_string="not enough money";                                          break;
      case 135: error_string="price changed";                                             break;
      case 136: error_string="off quotes";                                                break;
      case 137: error_string="broker is busy (never returned error)";                     break;
      case 138: error_string="requote";                                                   break;
      case 139: error_string="order is locked";                                           break;
      case 140: error_string="long positions only allowed";                               break;
      case 141: error_string="too many requests";                                         break;
      case 145: error_string="modification denied because order too close to market";     break;
      case 146: error_string="trade context is busy";                                     break;
      case 147: error_string="expirations are denied by broker";                          break;
      case 148: error_string="amount of open and pending orders has reached the limit";   break;
      case 149: error_string="hedging is prohibited";                                     break;
      case 150: error_string="prohibited by FIFO rules";                                  break;
      //---- mql4 errors
      case 4000: error_string="no error (never generated code)";                          break;
      case 4001: error_string="wrong function pointer";                                   break;
      case 4002: error_string="array index is out of range";                              break;
      case 4003: error_string="no memory for function call stack";                        break;
      case 4004: error_string="recursive stack overflow";                                 break;
      case 4005: error_string="not enough stack for parameter";                           break;
      case 4006: error_string="no memory for parameter string";                           break;
      case 4007: error_string="no memory for temp string";                                break;
      case 4008: error_string="not initialized string";                                   break;
      case 4009: error_string="not initialized string in array";                          break;
      case 4010: error_string="no memory for array\' string";                             break;
      case 4011: error_string="too long string";                                          break;
      case 4012: error_string="remainder from zero divide";                               break;
      case 4013: error_string="zero divide";                                              break;
      case 4014: error_string="unknown command";                                          break;
      case 4015: error_string="wrong jump (never generated error)";                       break;
      case 4016: error_string="not initialized array";                                    break;
      case 4017: error_string="dll calls are not allowed";                                break;
      case 4018: error_string="cannot load library";                                      break;
      case 4019: error_string="cannot call function";                                     break;
      case 4020: error_string="expert function calls are not allowed";                    break;
      case 4021: error_string="not enough memory for temp string returned from function"; break;
      case 4022: error_string="system is busy (never generated error)";                   break;
      case 4050: error_string="invalid function parameters count";                        break;
      case 4051: error_string="invalid function parameter value";                         break;
      case 4052: error_string="string function internal error";                           break;
      case 4053: error_string="some array error";                                         break;
      case 4054: error_string="incorrect series array using";                             break;
      case 4055: error_string="custom indicator error";                                   break;
      case 4056: error_string="arrays are incompatible";                                  break;
      case 4057: error_string="global variables processing error";                        break;
      case 4058: error_string="global variable not found";                                break;
      case 4059: error_string="function is not allowed in testing mode";                  break;
      case 4060: error_string="function is not confirmed";                                break;
      case 4061: error_string="send mail error";                                          break;
      case 4062: error_string="string parameter expected";                                break;
      case 4063: error_string="integer parameter expected";                               break;
      case 4064: error_string="double parameter expected";                                break;
      case 4065: error_string="array as parameter expected";                              break;
      case 4066: error_string="requested history data in update state";                   break;
      case 4099: error_string="end of file";                                              break;
      case 4100: error_string="some file error";                                          break;
      case 4101: error_string="wrong file name";                                          break;
      case 4102: error_string="too many opened files";                                    break;
      case 4103: error_string="cannot open file";                                         break;
      case 4104: error_string="incompatible access to a file";                            break;
      case 4105: error_string="no order selected";                                        break;
      case 4106: error_string="unknown symbol";                                           break;
      case 4107: error_string="invalid price parameter for trade function";               break;
      case 4108: error_string="invalid ticket";                                           break;
      case 4109: error_string="trade is not allowed in the expert properties";            break;
      case 4110: error_string="longs are not allowed in the expert properties";           break;
      case 4111: error_string="shorts are not allowed in the expert properties";          break;
      case 4200: error_string="object is already exist";                                  break;
      case 4201: error_string="unknown object property";                                  break;
      case 4202: error_string="object is not exist";                                      break;
      case 4203: error_string="unknown object type";                                      break;
      case 4204: error_string="no object name";                                           break;
      case 4205: error_string="object coordinates error";                                 break;
      case 4206: error_string="no specified subwindow";                                   break;
      default:   error_string="unknown error";
     }
//----
   return(error_string);
  }
  
//+------------------------------------------------------------------+

double DWX_GetAsk(string symbol) {
   if(symbol == "NULL") {
      return(Ask);
   } else {
      return(MarketInfo(symbol,MODE_ASK));
   }
}

//+------------------------------------------------------------------+

double DWX_GetBid(string symbol) {
   if(symbol == "NULL") {
      return(Bid);
   } else {
      return(MarketInfo(symbol,MODE_BID));
   }
}
//+------------------------------------------------------------------+



//+------------------------------------------------------------------+
