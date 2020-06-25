package com.future.service.order;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.future.common.constants.OrderConstant;
import com.future.common.constants.RedisConstant;
import com.future.common.enums.GlobalResultCode;
import com.future.common.exception.BusinessException;
import com.future.common.exception.DataConflictException;
import com.future.common.exception.ParameterInvalidException;
import com.future.common.helper.PageInfoHelper;
import com.future.common.util.ConvertUtil;
import com.future.common.util.DateUtil;
import com.future.common.util.RedisManager;
import com.future.common.util.StringUtils;
import com.future.entity.account.FuAccountMt;
import com.future.entity.order.FuOrderCustomer;
import com.future.entity.order.FuOrderSignal;
import com.future.entity.product.FuProductSignal;
import com.future.mapper.order.FuOrderSignalMapper;
import com.future.service.account.FuAccountMtService;
import com.future.service.product.FuProductSignalService;
import com.future.service.trade.FuTradeOrderService;
import com.future.service.user.UserCommonService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.util.*;


@Service
public class FuOrderSignalService extends ServiceImpl<FuOrderSignalMapper, FuOrderSignal> {

    Logger log=LoggerFactory.getLogger(FuOrderSignalService.class);

    @Autowired
    FuOrderCustomerService fuOrderCustomerService;
    @Autowired
    FuProductSignalService fuProductSignalService;
    @Autowired
    FuAccountMtService fuAccountMtService;
    @Autowired
    UserCommonService userCommonService;
    @Autowired
    FuTradeOrderService fuTradeOrderService;
    @Autowired
    FuOrderSignalMapper fuOrderSignalMapper;
    @Autowired
    RedisManager redisManager;


    /**
     * 查询信号源订单信息
     * @param requestMap
     * @param helper
     * @return
     */
    public Page<FuOrderSignal> querySignalOrder(Map requestMap, PageInfoHelper helper){
        /*校验信息*/
        if(ObjectUtils.isEmpty(requestMap.get("operUserId"))){
            throw new DataConflictException(GlobalResultCode.PARAM_NULL_POINTER.message());
        }
        /*判断权限*/
        String operUserId=String.valueOf(requestMap.get("operUserId"));
        if(StringUtils.isEmpty(operUserId)){
            log.error("查询用户列表,用户未登录！");
            throw new ParameterInvalidException("查询用户列表,获取参数为空！");
        }
        /*校验信息*/
        Integer operUserProj=userCommonService.getUserProjKey(Integer.parseInt(operUserId));
        Boolean isProjAdmin=userCommonService.isAdministrator(Integer.parseInt(operUserId),operUserProj);
        if(operUserProj==null){
            log.error("查询用户列表,用户权限有误！");
            throw new ParameterInvalidException("查询用户列表,用户权限有误！");
        }
        if(isProjAdmin&&operUserProj==0){
            /*超管查询*/
            return queryUserSignalOrder(requestMap,helper);
        }else if(isProjAdmin){
            /*资源组管理员查找*/
            requestMap.put("projKey",operUserProj);
            return queryProjectSignalOrder(requestMap,helper);
        }else {
            /*普通用户查找*/
            requestMap.put("userId",operUserId);
            return queryUserSignalOrder(requestMap,helper);
        }
    }

    /**
     * 查找团队用户跟随订单
     * @param condtionMap
     * @param helper
     * @return
     */
    public Page<FuOrderSignal> queryProjectSignalOrder(Map condtionMap,PageInfoHelper helper){
        if(condtionMap==null||condtionMap.get("projKey")==null){
            throw new DataConflictException(GlobalResultCode.PARAM_NULL_POINTER.message());
        }
        if(!ObjectUtils.isEmpty(condtionMap.get("orderOpenDate"))){
            if(String.valueOf(condtionMap.get("orderOpenDate")).indexOf(",")>0){
                List dateList=(List) condtionMap.get("orderOpenDate");
                if(dateList.size()!=2){
                    log.error("建仓时间段数据传入错误！"+condtionMap.get("orderOpenDate"));
                    throw new DataConflictException(GlobalResultCode.PARAM_VERIFY_ERROR,"建仓时间段数据传入错误！"+condtionMap.get("orderOpenDate"));
                }
                condtionMap.put("orderOpenDateBegin",dateList.get(0));
                condtionMap.put("orderOpenDateEnd",dateList.get(1));
                condtionMap.remove("orderOpenDate");
            }
        }
        if(!ObjectUtils.isEmpty(condtionMap.get("orderCloseDate"))){
            if(String.valueOf(condtionMap.get("orderCloseDate")).indexOf(",")>0){
                //时间段
                List dateList=(List) condtionMap.get("orderCloseDate");
                if(dateList.size()!=2){
                    log.error("平仓时间段数据传入错误！"+condtionMap.get("orderCloseDate"));
                    throw new DataConflictException(GlobalResultCode.PARAM_VERIFY_ERROR,"平仓时间段数据传入错误！"+condtionMap.get("orderCloseDate"));
                }
                condtionMap.put("orderCloseDateBegin",dateList.get(0));
                condtionMap.put("orderCloseDateEnd",dateList.get(1));
                condtionMap.remove("orderCloseDate");
            }
        }
        if(helper==null){
            helper=new PageInfoHelper();
        }
        Page<FuOrderSignal> followOrders=  PageHelper.startPage(helper.getPageNo(),helper.getPageSize());
        fuOrderSignalMapper.queryProjectSignalOrder(condtionMap);
        return followOrders;
    }

    /**
     * 用户查找信号源信息
     * @param requestMap
     * @param helper
     * @return
     */
    public Page<FuOrderSignal> queryUserSignalOrder(Map requestMap, PageInfoHelper helper){

        EntityWrapper<FuOrderSignal> wrapper=new EntityWrapper<FuOrderSignal>();
        if(!StringUtils.isEmpty(requestMap.get("userId"))){
            wrapper.eq(FuOrderSignal.USER_ID,requestMap.get("userId"));
        }
        if(!StringUtils.isEmpty(requestMap.get("signalId"))){
            wrapper.eq(FuOrderSignal.SIGNAL_ID,requestMap.get("signalId"));
        }
        if(!StringUtils.isEmpty(requestMap.get("orderId"))){
            wrapper.eq(FuOrderSignal.ORDER_ID,requestMap.get("orderId"));
        }
        if(!StringUtils.isEmpty(requestMap.get("orderSymbol"))){
            wrapper.eq(FuOrderSignal.ORDER_SYMBOL,requestMap.get("orderSymbol"));
        }
        if(!StringUtils.isEmpty(requestMap.get("orderType"))){
            wrapper.eq(FuOrderSignal.ORDER_TYPE,requestMap.get("orderType"));
        }
        if(!StringUtils.isEmpty(requestMap.get("orderTradeOperation"))){
            wrapper.eq(FuOrderSignal.ORDER_TRADE_OPERATION,requestMap.get("orderTradeOperation"));
        }
        if(!StringUtils.isEmpty(requestMap.get("orderOpenDate"))){
            if(String.valueOf(requestMap.get("orderOpenDate")).indexOf(",")<0){
                wrapper.eq(FuOrderSignal.ORDER_OPEN_DATE,requestMap.get("orderOpenDate"));
            }else {
                List dateList=(List) requestMap.get("orderOpenDate");
                if(dateList.size()!=2){
                    log.error("建仓时间段数据传入错误！"+requestMap.get("orderOpenDate"));
                    throw new DataConflictException(GlobalResultCode.PARAM_VERIFY_ERROR,"建仓时间段数据传入错误！"+requestMap.get("orderOpenDate"));
                }
                wrapper.gt(FuOrderSignal.ORDER_OPEN_DATE,dateList.get(0));
                wrapper.lt(FuOrderSignal.ORDER_OPEN_DATE,dateList.get(1));
            }
        }
        if(!StringUtils.isEmpty(requestMap.get("orderCloseDate"))){
            if(String.valueOf(requestMap.get("orderCloseDate")).indexOf(",")<0){
                wrapper.eq(FuOrderSignal.ORDER_OPEN_DATE,requestMap.get("orderCloseDate"));
            }else {
                //时间段
                //时间段
                List dateList=(List) requestMap.get("orderCloseDate");
                if(dateList.size()!=2){
                    log.error("平仓时间段数据传入错误！"+requestMap.get("orderCloseDate"));
                    throw new DataConflictException(GlobalResultCode.PARAM_VERIFY_ERROR,"平仓时间段数据传入错误！"+requestMap.get("orderCloseDate"));
                }
                wrapper.gt(FuOrderSignal.ORDER_CLOSE_DATE,dateList.get(0));
                wrapper.lt(FuOrderSignal.ORDER_CLOSE_DATE,dateList.get(1));
            }
        }
        wrapper.orderBy(FuOrderSignal.CREATE_DATE,false);
        if(helper==null){
            helper=new PageInfoHelper();
        }
        Page<FuOrderSignal> followOrders=  PageHelper.startPage(helper.getPageNo(),helper.getPageSize());
        selectList(wrapper);
        return followOrders;
    }


    /**
     * 保存监听到的信号源订单
     * @param signalMtAccId
     * @param orderAction
     * @param followOrder
     * @return
     */
    public boolean saveMonitorOrderData(int signalMtAccId,int orderAction,JSONObject followOrder){
        if(signalMtAccId==0||ObjectUtils.isEmpty(followOrder)){
            log.error("信号源监听数据保存 数据为空！");
            return false;
        }

        Map conditionMap=new HashMap();
        /*根据信号源mtAccId 查询信号源*/
        conditionMap.put(FuProductSignal.MT_ACC_ID,signalMtAccId);
        List<FuProductSignal> signals= fuProductSignalService.selectByMap(conditionMap);
        if(signals==null||signals.size()<=0){
            log.error("根据服务器和账号，查询信号源信息错误！");
            return false;
        }
        //根据mtaccId 查询user
        Wrapper<FuAccountMt> wrapper = new EntityWrapper<FuAccountMt>();
        wrapper.eq(FuAccountMt.MT_ACC_ID,signalMtAccId);
        FuAccountMt accountMt= fuAccountMtService.selectOne(wrapper);
        /*判断数据*/
        if(accountMt==null){
            log.error("查询信号源账户信息错误！");
            return false;
        }

        FuProductSignal productSignal=signals.get(0);

        /*保存至 信号源订单表*/
        FuOrderSignal orderSignal=new FuOrderSignal();
        orderSignal.setSignalId(productSignal.getId());
        orderSignal.setUserId(productSignal.getUserId());
        orderSignal.setMtAccId(productSignal.getMtAccId());
        orderSignal.setMtServerName(accountMt.getServerName());

        orderSignal.setOrderId(followOrder.getString("order"));
        orderSignal.setOrderLots(followOrder.getBigDecimal("volume").multiply(new BigDecimal(0.01)).setScale(6,BigDecimal.ROUND_HALF_UP));
        orderSignal.setOrderStoploss(followOrder.getBigDecimal("stoploss").setScale(6,BigDecimal.ROUND_HALF_UP));
        orderSignal.setOrderProfit(followOrder.getBigDecimal("profit").setScale(6,BigDecimal.ROUND_HALF_UP));
        orderSignal.setOrderMagic(followOrder.getBigDecimal("magic").setScale(6,BigDecimal.ROUND_HALF_UP));
        orderSignal.setOrderSymbol(followOrder.getString("symbol"));

        orderSignal.setOrderType(followOrder.getInteger("cmd"));
        orderSignal.setOrderOpenDate(DateUtil.toDataFormTimeStamp(followOrder.getLong("open_time")*1000));
        orderSignal.setOrderOpenPrice(followOrder.getBigDecimal("open_price").setScale(6,BigDecimal.ROUND_HALF_UP));
        orderSignal.setOrderExpiration(DateUtil.toDataFormTimeStamp(followOrder.getInteger("expiration")*1000));

        orderSignal.setComment(followOrder.getString("comment"));

        if(orderAction== OrderConstant.ORDER_OPERATION_OPEN){
            //开仓
            orderSignal.setOrderTradeOperation(OrderConstant.ORDER_OPERATION_OPEN);
        }else if(orderAction==OrderConstant.ORDER_OPERATION_CLOSE){
            orderSignal.setOrderTradeOperation(OrderConstant.ORDER_OPERATION_CLOSE);
            orderSignal.setOrderSwap(followOrder.getBigDecimal("storage"));
            orderSignal.setOrderCommission(followOrder.getBigDecimal("commission"));
            orderSignal.setOrderCloseDate(DateUtil.toDataFormTimeStamp(followOrder.getLong("close_time")*1000));
            orderSignal.setOrderClosePrice(followOrder.getBigDecimal("close_price"));
        }else if(orderAction==OrderConstant.ORDER_OPERATION_MODIFY){
            orderSignal.setOrderTradeOperation(OrderConstant.ORDER_OPERATION_MODIFY);
            log.error("跟单回调数据保存 订单类型错误！orderAction："+orderAction);
        }else {
            log.error("跟单回调数据保存 订单类型错误！orderAction："+orderAction);
            return false;
        }
        fuOrderSignalMapper.insertSelective(orderSignal);

        return true;
    }


    /**
     * 根据条件 同步信号源历史订单
     * @param conditionMap
     * @return
     */
    public boolean synSignalHistoryOrder(Map conditionMap){
        if(conditionMap==null||conditionMap.get("signalId")==null
                ||conditionMap.get("orderCloseDate")==null){
            log.error("根据条件 同步信号源历史订单,参数为空！");
            throw new DataConflictException("根据条件 同步信号源历史订单,参数为空！");
        }
        String signalId = String.valueOf(conditionMap.get("signalId"));
        String startDate ="";
        String endDate ="";

        if(!ObjectUtils.isEmpty(conditionMap.get("orderCloseDate"))){
            if(String.valueOf(conditionMap.get("orderCloseDate")).indexOf(",")<0){
                log.error("根据条件 同步信号源历史订单,日期格式错误！");
                throw new DataConflictException("根据条件 同步信号源历史订单,日期格式错误！");
            }else {
                //时间段
                List dateList=(List) conditionMap.get("orderCloseDate");
                if(dateList.size()!=2){
                    log.error("时间段数据传入错误！"+conditionMap.get("orderCloseDate"));
                    throw new DataConflictException(GlobalResultCode.PARAM_VERIFY_ERROR,"时间段数据传入错误！"+conditionMap.get("orderCloseDate"));
                }
                startDate=String.valueOf(dateList.get(0));
                endDate=String.valueOf(dateList.get(1));
            }
        }

        FuProductSignal signal= fuProductSignalService.findSignalById(Integer.valueOf(signalId));
        if(signal==null||signal.getId()==null){
            log.error("初始化信号源历史数据,信号源查询错误！");
            throw new DataConflictException("初始化信号源历史数据,信号源查询错误！");
        }

        initSignalHistoryOrder(signal.getUserId(),signal.getServerName(),signal.getMtAccId(),DateUtil.toDate(startDate),DateUtil.toDate(endDate));

        return true;
    }


    /**
     * 初始化信号源历史数据（已结束时间 倒叙按月同步）
     * @param userId
     * @param serverName
     * @param mtAccId
     * @param dataBegin
     * @param dateEnd
     * @return
     */
    public boolean initSignalHistoryOrder(Integer userId, String serverName,String mtAccId, Date dataBegin,Date dateEnd){
        if(serverName==null||mtAccId==null||userId==null||userId==0){
            log.error("初始化信号源历史数据,参数为空！");
            throw new DataConflictException("初始化信号源历史数据,参数为空！");
        }
        /*判断结束月份*/
        if(dateEnd==null||dateEnd.compareTo(new Date())>0){
            dateEnd=new Date();
        }
        if(dataBegin==null){
            /*默认同步3年内的数据*/
            dataBegin=DateUtil.getFutureDate(new Date(),-1000);
        }
        if (dateEnd.compareTo(dataBegin)<=0){
            log.error("初始化信号源历史数据,日期输入错误！");
            throw new DataConflictException("初始化信号源历史数据,日期输入错误！");
        }

        Wrapper<FuProductSignal> wrapper=new EntityWrapper<>();
        wrapper.eq(FuProductSignal.USER_ID,userId);
        wrapper.eq(FuProductSignal.MT_ACC_ID,mtAccId);
        FuProductSignal signal= fuProductSignalService.selectOne(wrapper);
        if(signal==null||signal.getId()==null){
            log.error("初始化信号源历史数据,信号源查询错误！");
            throw new DataConflictException("初始化信号源历史数据,信号源查询错误！");
        }

        /*信号源必须在监听状态下 不用再重复链接了*/
        boolean isSignalConnected=true;
        if(redisManager.hget(RedisConstant.H_ACCOUNT_CONNECT_INFO,mtAccId)==null){
            isSignalConnected=false;
            log.warn("初始化信号源历史数据,信号源必须在监听状态下才可以同步数据！暂时启动链接");
            boolean isConnect= fuAccountMtService.connectUserMTAccount(userId,mtAccId,null,serverName);
            if(!isConnect){
                log.error("初始化信号源历史数据,信号源链接失败！");
                throw new BusinessException("初始化信号源历史数据,信号源链接失败！");
            }
        }

        /*时间段按月截取*/
        List<FuOrderSignal> orderSignals=new ArrayList<>();
        List<FuOrderCustomer> orderCustomers=new ArrayList<>();
        Map selectMap = new HashMap();
        Date dateFrom=new Date();
        Date dateTo=new Date();
        String startDate =DateUtil.toDateString(dataBegin);
        String endDate =DateUtil.toDateString(dateEnd);

        List<String> months= DateUtil.getMonthBetween(startDate.substring(0,7),endDate.substring(0,7));
        /*倒叙查找*/
        for(int i=months.size()-1;i<months.size();i--){

            dateFrom=DateUtil.toDate(months.get(i-1)+"-01");
            dateTo=DateUtil.toDate(months.get(i)+"-01");

            if(i==0){
                dateFrom=DateUtil.toDate(startDate);
            }
            if(i==months.size()-1){
                dateTo=DateUtil.toDate(endDate);
            }

            JSONArray orders= fuTradeOrderService.getUserCloseOrders(serverName,Integer.parseInt(mtAccId),signal.getMtPasswordWatch(),dateFrom.getTime(),dateTo.getTime());
            if(orders==null||orders.size()==0){
                /*查询数据为空*/
                if(i==months.size()-1){
                    /*倒叙 第一个月为空继续查*/
                    continue;
                }else {
                    /*倒叙 非第一个月为空 默认为无数据*/
                    break;
                }
            }

            // 只同步已完成的订单
            orderSignals= ConvertUtil.convertOrderSignals(orders);

            // 处理订单
            if(ObjectUtils.isEmpty(orderSignals)) {
                continue;
            }
            for(FuOrderSignal orderSignal:orderSignals){

                // 判断时间范围
                if(dateFrom.compareTo(orderSignal.getOrderCloseDate())>0){
                    continue;
                }

                // 与社区订单查重，已有订单无需操作
                selectMap.clear();
                selectMap.put(FuOrderSignal.USER_ID,userId);
                selectMap.put(FuOrderSignal.SIGNAL_ID,signal.getId());
                selectMap.put(FuOrderSignal.ORDER_ID,orderSignal.getOrderId());
                List<FuOrderCustomer> infos=fuOrderSignalMapper.selectByMap(selectMap);
                if(infos!=null&&infos.size()>0){
                    // 数据已存在=
                    continue;
                }

                // 转换订单
                orderSignal.setUserId(signal.getUserId());
                orderSignal.setSignalId(signal.getId());
                orderSignal.setMtAccId(signal.getMtAccId());
                orderSignal.setMtServerName(signal.getServerName());
                orderSignal.setOrderState(OrderConstant.ORDER_STATE_CLOSE);
                fuOrderSignalMapper.insertSelective(orderSignal);

                // 保存用户自交易订单
                FuOrderCustomer orderCustomer= ConvertUtil.convertOrderCustomer(orderSignal);
                orderCustomers.add(orderCustomer);
                fuOrderCustomerService.insertSelective(orderCustomer);

                //历史订单不错佣金处理

                //计算出入金
                if(orderSignal.getOrderType()==OrderConstant.ORDER_TYPE_BALANCE){
                    log.info("----------------------出入金计算："+JSONObject.toJSONString(orderSignal));
                    fuAccountMtService.mtAccDepositUpate(userId,mtAccId,orderSignal.getOrderCloseDate(),orderSignal.getOrderProfit());
                }
            }
            /*// 保存信号源交易订单
            insertBatch(orderSignals);
            // 保持客户交易订单（数据分析用）
            fuOrderCustomerService.insertBatch(orderCustomers);*/
        }

        /*本地逻辑打开的链接需要关闭*/
        if(!isSignalConnected){
            fuAccountMtService.disConnectUserMTAccount(signal.getUserId(),signal.getMtAccId(),null,signal.getServerName());
        }

        return true;
    }
}
