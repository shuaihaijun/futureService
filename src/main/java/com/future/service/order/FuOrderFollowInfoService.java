package com.future.service.order;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.future.common.constants.AccountConstant;
import com.future.common.constants.OrderConstant;
import com.future.common.constants.UserConstant;
import com.future.common.enums.GlobalResultCode;
import com.future.common.exception.BusinessException;
import com.future.common.exception.DataConflictException;
import com.future.common.exception.ParameterInvalidException;
import com.future.common.helper.PageInfoHelper;
import com.future.common.util.ConvertUtil;
import com.future.common.util.DateUtil;
import com.future.common.util.RedisManager;
import com.future.entity.account.FuAccountMt;
import com.future.entity.order.FuOrderFollowInfo;
import com.future.entity.product.FuProductSignal;
import com.future.entity.user.FuUserFollows;
import com.future.mapper.order.FuOrderFollowInfoMapper;
import com.future.pojo.bo.account.UserMTAccountBO;
import com.future.service.account.FuAccountMtService;
import com.future.service.product.FuProductSignalService;
import com.future.service.trade.FuTradeOrderService;
import com.future.service.user.AdminService;
import com.future.service.user.FuUserFollowsService;
import com.future.service.user.UserCommonService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FuOrderFollowInfoService extends ServiceImpl<FuOrderFollowInfoMapper,FuOrderFollowInfo> {

    Logger log = LoggerFactory.getLogger(FuOrderFollowInfoService.class);

    @Autowired
    AdminService adminService;
    @Autowired
    FuAccountMtService fuAccountMtService;
    @Autowired
    FuOrderCustomerService fuOrderCustomerService;
    @Autowired
    FuTradeOrderService fuTradeOrderService;
    @Autowired
    FuUserFollowsService fuUserFollowsService;
    @Autowired
    FuProductSignalService fuProductSignalService;
    @Autowired
    UserCommonService userCommonService;
    @Autowired
    FuOrderFollowInfoMapper fuOrderFollowInfoMapper;
    @Autowired
    RedisManager redisManager;

    /**
     * 根据时间段 查询用户历史订单
     * @param userId,
     * @param username
     * @param mtAccId
     * @param dataFrom
     * @param dateTo
     * @return
     */
    public List<FuOrderFollowInfo> geMTtHistoryOrders(String userId,String username, String mtAccId,Date dataFrom,Date dateTo){
        return getMTOrders(true,userId,username,mtAccId,dataFrom,dateTo);
    }


    /**
     * 获取用户在仓订单信息
     * @param userId
     * @param username
     * @param mtAccId
     * @param dataFrom
     * @param dateTo
     * @return
     */
    public List<FuOrderFollowInfo> getMTAliveOrders(String userId,String username, String mtAccId,Date dataFrom,Date dateTo){
        return getMTOrders(false,userId,username,mtAccId,dataFrom,dateTo);
    }

    /**
     * 根据MT订单号查询用户历史订单详情
     * @param username
     * @param mtAccId
     * @param orderId
     * @return
     */
    public FuOrderFollowInfo getMTHistoryOrderByOrderId(String username, String mtAccId,int orderId){
        return getMTOrderByIndex(username,mtAccId,orderId,true);
    }

    /**
     * 根据MT订单号查询用户在仓订单详情
     * @param username
     * @param mtAccId
     * @param orderId
     * @return
     */
    public FuOrderFollowInfo getMTAliveOrderByOrderId(String username, String mtAccId,int orderId){
        return getMTOrderByIndex(username,mtAccId,orderId,false);
    }

    /**
     * 根据条件获取MT的订单信息
     * @param isHistoryOrder
     * @param userId
     * @param username
     * @param mtAccId
     * @param dateFrom
     * @param dateTo
     * @return
     */
    public List<FuOrderFollowInfo> getMTOrders(Boolean isHistoryOrder, String userId,String username, String mtAccId,Date dateFrom,Date dateTo){
        if(StringUtils.isEmpty(username)&&StringUtils.isEmpty(userId)){
            log.error("根据时间段 查询用户历史订单，用户信息为空！");
            return null;
        }

        /*查询用户历史订单*/
        Map acountMap=new HashMap();
        if(!StringUtils.isEmpty(mtAccId)){
            acountMap.put("mtAccId",mtAccId);
        }
        if(!StringUtils.isEmpty(userId)){
            /*默认查询主账户号*/
            acountMap.put("userId",userId);
        }
        if(!StringUtils.isEmpty(username)){
            /*默认查询主账户号*/
            acountMap.put("username",username);
        }
        List<UserMTAccountBO> mts= fuAccountMtService.getUserMTAccByCondition(acountMap);
        if(ObjectUtils.isEmpty(mts)||mts.isEmpty()){
            log.error("根据时间段查询用户历史订单|用户MT4账户未绑定！");
            throw new BusinessException("根据时间段查询用户历史订单|用户MT4账户未绑定！");
        }
        UserMTAccountBO userMtAcc=mts.get(0);
        mtAccId =userMtAcc.getMtAccId();
        Integer userType=userMtAcc.getUserType();
        String serverName=String.valueOf(userMtAcc.getServerName());
        String mtPassword=String.valueOf(userMtAcc.getMtPasswordTrade());
        if(StringUtils.isEmpty(serverName)){
            log.error("根据时间段 查询用户历史订单，用户MT4账户信息有误！");
            return null;
        }
        JSONArray orderJson=new JSONArray();
        long dateTimeFrom=dateFrom==null?0:dateFrom.getTime();
        long dateTimeTo=dateTo==null?0:dateTo.getTime();
        if(isHistoryOrder){
            orderJson= fuTradeOrderService.getUserCloseOrders(serverName,Integer.parseInt(mtAccId),mtPassword, dateTimeFrom,dateTimeTo);
        }else {
            orderJson= fuTradeOrderService.getUserOpenOrders(serverName,Integer.parseInt(mtAccId),mtPassword, dateTimeFrom,dateTimeTo);
        }

        if(ObjectUtils.isEmpty(orderJson)){
            return null;
        }
        List<FuOrderFollowInfo> infos=ConvertUtil.convertOrderInfos(orderJson);

        /*填充数据*/
        for (FuOrderFollowInfo info:infos){
            // 是不是普通用户 都填充用户信息，方便前端展示
            info.setUserMtAccId(mtAccId);
            info.setUserId(Integer.parseInt(userId));
            info.setUserServerName(serverName);
            if(userType==UserConstant.USER_TYPE_SIGNAL){
                // 信号源 填充信号源信息
                info.setSignalOrderId(info.getOrderId());
                info.setSignalMtAccId(mtAccId);
                info.setSignalServerName(serverName);
            }
        }
        return infos;
    }


    /**
     * 根据Mt订单号 查询用户订单信息
     * @param username
     * @param mtAccId
     * @param orderId
     * @param isHistoryOrder
     * @return
     */
    public FuOrderFollowInfo getMTOrderByIndex(String username, String mtAccId,int orderId,Boolean isHistoryOrder){

        if(StringUtils.isEmpty(username)||orderId==0){
            log.error("根据时间段 查询用户历史订单，数据为空！");
            return null;
        }
        /*查询用户历史订单*/
        Map acountMap=new HashMap();
        if(StringUtils.isEmpty(username)){
            acountMap.put("username",username);
        }
        if(StringUtils.isEmpty(mtAccId)){
            /*默认查询主账户号*/
            acountMap.put("mtAccId",mtAccId);
        }
        /*查询用户账户信息*/
        List<UserMTAccountBO> mts= fuAccountMtService.getUserMTAccByCondition(acountMap);
        if(ObjectUtils.isEmpty(mts)||mts.isEmpty()){
            log.error("根据时间段查询用户历史订单|用户MT4账户未绑定！");
            throw new BusinessException("根据时间段查询用户历史订单|用户MT4账户未绑定！");
        }
        UserMTAccountBO accountBO=mts.get(0);
        String serverName=String.valueOf(accountBO.getServerName());
        String mtPassword=String.valueOf(accountBO.getMtPasswordTrade());
        if(StringUtils.isEmpty(serverName)){
            log.error("根据时间段 查询用户历史订单，用户MT4账户信息有误！");
            return null;
        }
        JSONObject order=new JSONObject();
        if(isHistoryOrder){
            /*历史订单*/
            order= fuTradeOrderService.getUserCloseOrder(serverName,Integer.parseInt(mtAccId),mtPassword,orderId);
        }else {
            /*在仓订单*/
            order= fuTradeOrderService.getUserOpenOrder(serverName,Integer.parseInt(mtAccId),mtPassword,orderId);
        }
        return ConvertUtil.convertOrderInfo(order);
    }

    /**
     * 根据orderId+userId查询订单信息
     * @param orderId
     * @return
     */
    public FuOrderFollowInfo getOrderByCondition(Integer userId,String orderId){
        if(userId==0
            ||StringUtils.isEmpty(orderId)){
            log.error("根据orderId+userId查询订单信息,参数为空！");
            throw new ParameterInvalidException("根据orderId+userId查询订单信息,参数为空！");
        }
        /*redis 或 数据库*/
        Map conditionMap =new HashMap();
        conditionMap.put(FuOrderFollowInfo.USER_ID,userId);
        conditionMap.put(FuOrderFollowInfo.ORDER_ID,orderId);
        List<FuOrderFollowInfo> infos=fuOrderFollowInfoMapper.selectByMap(conditionMap);
        if (infos!=null&& infos.size()>0){
            return infos.get(0);
        }
        return null;
    }


    /**
     * 跟单回调数据保存
     * @param followMtAccId
     * @param signalMtAccId
     * @param signalOrderId
     * @param orderAction
     * @param followOrder
     * @return
     */
    public boolean saveFollowOrderData(int followMtAccId,int signalMtAccId,int signalOrderId,int orderAction,JSONObject followOrder){

        if(followMtAccId==0||signalMtAccId==0||signalOrderId==0||ObjectUtils.isEmpty(followOrder)){
            log.error("跟单回调数据保存 数据为空！");
            return false;
        }

        //根据mtaccId 查询user
        Wrapper<FuAccountMt> wrapper = new EntityWrapper<FuAccountMt>();
        wrapper.eq(FuAccountMt.MT_ACC_ID,followMtAccId);
        FuAccountMt accountMt= fuAccountMtService.selectOne(wrapper);
        /*判断数据*/
        if(accountMt==null){
            log.error("查询信号源账户信息错误！");
            return false;
        }

        /*根据信号源mtAccId 查询信号源*/
        Map conditionMap=new HashMap();
        conditionMap.put(FuProductSignal.MT_ACC_ID,signalMtAccId);
        List<FuProductSignal> signals= fuProductSignalService.selectByMap(conditionMap);
        if(signals==null||signals.size()<=0){
            log.error("根据服务器和账号，查询信号源信息错误！");
            return false;
        }
        /*跟单数据*/
        conditionMap.clear();
        conditionMap.put(FuUserFollows.USER_ID,accountMt.getUserId());
        conditionMap.put(FuUserFollows.USER_MT_ACC_ID,accountMt.getMtAccId());
        conditionMap.put(FuUserFollows.SIGNAL_ID,signals.get(0).getId());
        conditionMap.put(FuUserFollows.SIGNAL_MT_ACC_ID,signals.get(0).getMtAccId());
        conditionMap.put(FuUserFollows.FOLLOW_STATE, AccountConstant.ACCOUNT_STATE_NORMAL);
        List<FuUserFollows> fuUserFollows= fuUserFollowsService.selectByMap(conditionMap);
        if(fuUserFollows==null||fuUserFollows.size()<=0){
            log.error("根据订单数据，查询跟单信息错误！");
        }

        /*保存至 信号源订单表*/
        FuOrderFollowInfo followInfo=new FuOrderFollowInfo();
        followInfo.setSignalId(signals.get(0).getId());

        followInfo.setUserId(accountMt.getUserId());
        followInfo.setUserServerName(accountMt.getServerName());
        followInfo.setUserMtAccId(accountMt.getMtAccId());
        followInfo.setUserServerId(accountMt.getServerId());

        followInfo.setSignalMtAccId(signals.get(0).getMtAccId());
        followInfo.setSignalOrderId(String.valueOf(signalOrderId));
        followInfo.setSignalServerName(signals.get(0).getServerName());

        followInfo.setOrderId(followOrder.getString("order"));
        followInfo.setOrderLots(followOrder.getBigDecimal("volume").multiply(new BigDecimal(0.01)));
        followInfo.setOrderStoploss(followOrder.getBigDecimal("stoploss"));
        followInfo.setOrderTakeprofit(followOrder.getBigDecimal("takeprofit"));
        followInfo.setOrderProfit(followOrder.getBigDecimal("profit"));
        followInfo.setOrderMagic(followOrder.getBigDecimal("magic"));
        followInfo.setOrderSymbol(followOrder.getString("symbol"));

        followInfo.setOrderType(followOrder.getInteger("cmd"));
        followInfo.setOrderOpenDate(DateUtil.toDataFormTimeStamp(followOrder.getLong("open_time")*1000));
        followInfo.setOrderOpenPrice(followOrder.getBigDecimal("open_price"));
        followInfo.setOrderExpiration(DateUtil.toDataFormTimeStamp(followOrder.getInteger("expiration")*1000));

        followInfo.setComment(followOrder.getString("comment"));

        /*跟单数据*/
        if(fuUserFollows!=null||fuUserFollows.size()>0){
            followInfo.setFollowDirect(fuUserFollows.get(0).getFollowDirect());
            followInfo.setFollowMode(fuUserFollows.get(0).getFollowMode());
            followInfo.setFollowType(fuUserFollows.get(0).getFollowType());
            followInfo.setFollowAmount(fuUserFollows.get(0).getFollowAmount());
        }

        if(orderAction==OrderConstant.ORDER_OPERATION_OPEN){
            //开仓
            followInfo.setOrderTradeOperation(OrderConstant.ORDER_OPERATION_OPEN);
        }else if(orderAction==OrderConstant.ORDER_OPERATION_CLOSE){
            followInfo.setOrderTradeOperation(OrderConstant.ORDER_OPERATION_CLOSE);
            followInfo.setOrderSwap(followOrder.getBigDecimal("storage"));
            followInfo.setOrderCommission(followOrder.getBigDecimal("commission"));
            followInfo.setOrderCloseDate(DateUtil.toDataFormTimeStamp(followOrder.getLong("close_time")*1000));
            followInfo.setOrderClosePrice(followOrder.getBigDecimal("close_price"));
        }else if(orderAction==OrderConstant.ORDER_OPERATION_MODIFY){
            followInfo.setOrderTradeOperation(OrderConstant.ORDER_OPERATION_MODIFY);
            log.error("跟单回调数据保存 订单类型错误！orderAction："+orderAction);
        }else {
            log.error("跟单回调数据保存 订单类型错误！orderAction："+orderAction);
            return false;
        }
        fuOrderFollowInfoMapper.insertSelective(followInfo);
        return true;
    }

    /**
     * 查询社区跟随订单
     * @param requestMap
     * @param helper
     * @return
     */
    public Page<FuOrderFollowInfo> queryOrderFollowInfo(Map requestMap, PageInfoHelper helper){

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
            return queryUserFollowOrder(requestMap,helper);
        }else if(isProjAdmin){
            /*资源组管理员查找*/
            requestMap.put("projKey",operUserProj);
            return queryProjectFollowOrder(requestMap,helper);
        }else {
            /*普通用户查找*/
            requestMap.put("userId",operUserId);
            return queryUserFollowOrder(requestMap,helper);
        }
    }

    /**
     * 查询社区跟随订单
     * @param requestMap
     * @param helper
     * @return
     */
    public Page<FuOrderFollowInfo> queryUserFollowOrder(Map requestMap, PageInfoHelper helper){
        EntityWrapper<FuOrderFollowInfo> wrapper=new EntityWrapper<FuOrderFollowInfo>();
        if(!ObjectUtils.isEmpty(requestMap.get("userId"))){
            wrapper.eq(FuOrderFollowInfo.USER_ID,requestMap.get("userId"));
        }
        if(!ObjectUtils.isEmpty(requestMap.get("signalId"))){
            wrapper.eq(FuOrderFollowInfo.SIGNAL_ID,requestMap.get("signalId"));
        }
        if(!ObjectUtils.isEmpty(requestMap.get("orderId"))){
            wrapper.eq(FuOrderFollowInfo.ORDER_ID,requestMap.get("orderId"));
        }
        if(!ObjectUtils.isEmpty(requestMap.get("signalOrderId"))){
            wrapper.eq(FuOrderFollowInfo.SIGNAL_ORDER_ID,requestMap.get("signalOrderId"));
        }
        if(!ObjectUtils.isEmpty(requestMap.get("orderSymbol"))){
            wrapper.eq(FuOrderFollowInfo.ORDER_SYMBOL,requestMap.get("orderSymbol"));
        }
        if(!ObjectUtils.isEmpty(requestMap.get("orderType"))){
            wrapper.eq(FuOrderFollowInfo.ORDER_TYPE,requestMap.get("orderType"));
        }
        if(!ObjectUtils.isEmpty(requestMap.get("orderOpenDate"))){
            if(String.valueOf(requestMap.get("orderOpenDate")).indexOf(",")<0){
                wrapper.eq(FuOrderFollowInfo.ORDER_OPEN_DATE,requestMap.get("orderOpenDate"));
            }else {
                List dateList=(List) requestMap.get("orderOpenDate");
                if(dateList.size()!=2){
                    log.error("建仓时间段数据传入错误！"+requestMap.get("orderOpenDate"));
                    throw new DataConflictException(GlobalResultCode.PARAM_VERIFY_ERROR,"建仓时间段数据传入错误！"+requestMap.get("orderOpenDate"));
                }
                wrapper.gt(FuOrderFollowInfo.ORDER_OPEN_DATE,dateList.get(0));
                wrapper.lt(FuOrderFollowInfo.ORDER_OPEN_DATE,dateList.get(1));
            }
        }
        if(!ObjectUtils.isEmpty(requestMap.get("orderCloseDate"))){
            if(String.valueOf(requestMap.get("orderCloseDate")).indexOf(",")<0){
                wrapper.eq(FuOrderFollowInfo.ORDER_CLOSE_DATE,requestMap.get("orderCloseDate"));
            }else {
                //时间段
                List dateList=(List) requestMap.get("orderCloseDate");
                if(dateList.size()!=2){
                    log.error("平仓时间段数据传入错误！"+requestMap.get("orderCloseDate"));
                    throw new DataConflictException(GlobalResultCode.PARAM_VERIFY_ERROR,"平仓时间段数据传入错误！"+requestMap.get("orderCloseDate"));
                }
                wrapper.gt(FuOrderFollowInfo.ORDER_CLOSE_DATE,dateList.get(0));
                wrapper.lt(FuOrderFollowInfo.ORDER_CLOSE_DATE,dateList.get(1));
            }
        }

        if(helper==null){
            helper=new PageInfoHelper();
        }
        Page<FuOrderFollowInfo> followOrders=  PageHelper.startPage(helper.getPageNo(),helper.getPageSize());
        selectList(wrapper);
        return followOrders;
    }


    /**
     * 查找团队用户跟随订单
     * @param condtionMap
     * @param helper
     * @return
     */
    public Page<FuOrderFollowInfo> queryProjectFollowOrder(Map condtionMap,PageInfoHelper helper){
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
        Page<FuOrderFollowInfo> followOrders=  PageHelper.startPage(helper.getPageNo(),helper.getPageSize());
        fuOrderFollowInfoMapper.queryProjectFollowOrder(condtionMap);
        return followOrders;
    }
}
