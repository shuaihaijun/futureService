package com.future.service.order;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.future.common.constants.AccountConstant;
import com.future.common.constants.OrderConstant;
import com.future.common.enums.GlobalResultCode;
import com.future.common.exception.DataConflictException;
import com.future.common.exception.ParameterInvalidException;
import com.future.common.helper.PageInfoHelper;
import com.future.common.util.DateUtil;
import com.future.common.util.StringUtils;
import com.future.entity.account.FuAccountMt;
import com.future.entity.com.FuComDictionary;
import com.future.entity.order.FuOrderCustomer;
import com.future.entity.order.FuOrderFollowInfo;
import com.future.entity.order.FuOrderSignal;
import com.future.entity.product.FuProductSignal;
import com.future.entity.user.FuUserFollows;
import com.future.mapper.order.FuOrderSignalMapper;
import com.future.pojo.bo.order.UserMTAccountBO;
import com.future.service.account.FuAccountMtService;
import com.future.service.product.FuProductSignalService;
import com.future.service.user.UserCommonService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class FuOrderSignalService extends ServiceImpl<FuOrderSignalMapper, FuOrderSignal> {

    Logger log=LoggerFactory.getLogger(FuOrderSignalService.class);

    @Autowired
    FuProductSignalService fuProductSignalService;
    @Autowired
    FuAccountMtService fuAccountMtService;
    @Autowired
    UserCommonService userCommonService;
    @Autowired
    FuOrderSignalMapper fuOrderSignalMapper;


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
        if(com.alibaba.druid.util.StringUtils.isEmpty(operUserId)){
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
        if(!ObjectUtils.isEmpty(condtionMap.get("orderOpenDate"))){
            if(String.valueOf(condtionMap.get("orderOpenDate")).indexOf(",")>0){
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
        orderSignal.setOrderLots(followOrder.getBigDecimal("volume").multiply(new BigDecimal(0.01)));
        orderSignal.setOrderStoploss(followOrder.getBigDecimal("stoploss"));
        orderSignal.setOrderProfit(followOrder.getBigDecimal("profit"));
        orderSignal.setOrderMagic(followOrder.getBigDecimal("magic"));
        orderSignal.setOrderSymbol(followOrder.getString("symbol"));

        orderSignal.setOrderType(followOrder.getInteger("cmd"));
        orderSignal.setOrderOpenDate(DateUtil.toDataFormTimeStamp(followOrder.getLong("open_time")*1000));
        orderSignal.setOrderOpenPrice(followOrder.getBigDecimal("open_price"));
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
}
