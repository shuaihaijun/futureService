package com.future.service.order;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.future.common.constants.OrderConstant;
import com.future.common.enums.GlobalResultCode;
import com.future.common.enums.TradeErrorEnum;
import com.future.common.exception.DataConflictException;
import com.future.common.helper.PageInfoHelper;
import com.future.common.util.DateUtil;
import com.future.common.util.StringUtils;
import com.future.entity.order.FuOrderFollowError;
import com.future.entity.user.FuUserFollows;
import com.future.mapper.order.FuOrderFollowErrorMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;

@Service
public class FuOrderFollowErrorService extends ServiceImpl<FuOrderFollowErrorMapper, FuOrderFollowError> {

    Logger log = LoggerFactory.getLogger(FuOrderFollowErrorService.class);

    @Autowired
    FuOrderFollowErrorMapper fuOrderFollowErrorMapper;

    /**
     * 查询信息
     * @param condition
     * @param helper
     * @return
     */
    public Page<FuOrderFollowError> queryOrderFollowError(Map condition, PageInfoHelper helper){
        EntityWrapper<FuOrderFollowError> wrapper=new EntityWrapper<FuOrderFollowError>();
        if(condition.get("userId")!=null){
            wrapper.eq(FuOrderFollowError.USER_ID,condition.get("userId"));
        }
        if(condition.get("userMtAccId")!=null){
            wrapper.eq(FuOrderFollowError.USER_MT_ACC_ID,condition.get("userMtAccId"));
        }
        if(condition.get("userServerName")!=null){
            wrapper.eq(FuOrderFollowError.USER_SERVER_NAME,condition.get("userServerName"));
        }
        if(condition.get("signalId")!=null){
            wrapper.eq(FuOrderFollowError.SIGNAL_ID,condition.get("signalId"));
        }
        if(condition.get("signalMtAccId")!=null){
            wrapper.eq(FuOrderFollowError.SIGNAL_MT_ACC_ID,condition.get("signalMtAccId"));
        }
        if(condition.get("signalServerName")!=null){
            wrapper.eq(FuOrderFollowError.SIGNAL_SERVER_NAME,condition.get("signalServerName"));
        }
        if( condition.get("signalOrderId")!=null){
            wrapper.eq(FuOrderFollowError.SIGNAL_ORDER_ID,condition.get("signalOrderId"));
        }
        if( condition.get("orderSymbol")!=null){
            wrapper.eq(FuOrderFollowError.ORDER_SYMBOL,condition.get("orderSymbol"));
        }
        if(condition.get("orderType")!=null){
            wrapper.eq(FuOrderFollowError.ORDER_TYPE,condition.get("orderType"));
        }
        if(condition.get("followDirect")!=null){
            wrapper.eq(FuOrderFollowError.FOLLOW_DIRECT,condition.get("followDirect"));
        }
        if(!StringUtils.isEmpty(condition.get("orderOpenDate"))){
            String orderOpenDate=String.valueOf(condition.get("orderOpenDate"));
            if(orderOpenDate.indexOf(",")<0){
                wrapper.eq(FuOrderFollowError.ORDER_OPEN_DATE,orderOpenDate);
            }else {
                //时间段
                JSONArray dateOpen=JSONArray.parseArray(orderOpenDate);
                if(dateOpen.size()!=2){
                    log.error("建仓时间段数据传入错误！"+orderOpenDate);
                    throw new DataConflictException(GlobalResultCode.PARAM_VERIFY_ERROR,"建仓时间段数据传入错误！"+orderOpenDate);
                }
                wrapper.gt(FuOrderFollowError.ORDER_OPEN_DATE,dateOpen.getString(0));
                wrapper.lt(FuOrderFollowError.ORDER_OPEN_DATE,dateOpen.getString(1));
            }
        }

        if(helper==null){
            helper=new PageInfoHelper();
        }
        Page<FuOrderFollowError> errors= PageHelper.startPage(helper.getPageNo(),helper.getPageSize());
        fuOrderFollowErrorMapper.selectList(wrapper);
        return errors;
    }


    /**
     * 处理跟单失败逻辑
     * @param updateAction
     * @param signalOrder
     * @param followRule
     * @param errorCode
     * @return
     */
    public boolean dealFollowErrorData(int updateAction,JSONObject signalOrder,JSONObject followRule,int errorCode){

        if(signalOrder==null||followRule==null){
            log.error("处理跟单失败逻辑 失败，数据为空！");
            return false;
        }

        FuOrderFollowError error=new FuOrderFollowError();

        FuUserFollows follows=JSONObject.parseObject(followRule.toJSONString(),FuUserFollows.class);

        /*错误信息*/
        error.setErrorCode(errorCode);
        error.setErrorMsg(TradeErrorEnum.getMessage(errorCode));
        /*用户信息*/
        error.setUserId(follows.getUserId());
        error.setUserServerName(follows.getUserServerName());
        error.setUserMtAccId(follows.getUserMtAccId());
        /*信号源信息*/
        error.setSignalId(follows.getSignalId());
        error.setSignalServerName(follows.getSignalServerName());
        error.setSignalMtAccId(follows.getSignalMtAccId());
        /*跟随规则信息*/
        error.setFollowDirect(follows.getFollowDirect());
        error.setFollowMode(follows.getFollowMode());
        error.setFollowType(follows.getFollowType());
        error.setFollowAmount(follows.getFollowAmount());

        /*订单信息*/
        JSONObject orderInfo=signalOrder.getJSONObject("tradeRecord");
        error.setSignalOrderId(orderInfo.getString("order"));
        error.setOrderSymbol(new String(orderInfo.getBytes("symbol")).trim());
        error.setOrderType(orderInfo.getInteger("cmd"));
        error.setOrderOpenDate(DateUtil.toDataFormTimeStamp(orderInfo.getInteger("open_time")*1000));
        error.setOrderOpenPrice(orderInfo.getBigDecimal("open_price"));
        error.setOrderLots(orderInfo.getBigDecimal("volume").multiply(new BigDecimal(0.01)));//需要转换
        if(updateAction==OrderConstant.ORDER_OPERATION_OPEN){
            //开仓
            error.setOrderTradeOperation(OrderConstant.ORDER_OPERATION_OPEN);
        }else if(updateAction==OrderConstant.ORDER_OPERATION_CLOSE){
            error.setOrderTradeOperation(OrderConstant.ORDER_OPERATION_CLOSE);
            error.setOrderCloseDate(DateUtil.toDataFormTimeStamp(orderInfo.getInteger("close_time")*1000));
            error.setOrderClosePrice(orderInfo.getBigDecimal("close_price"));
        }else {
            log.error("跟单回调数据保存 订单动作错误！updateAction："+updateAction);
            return false;
        }

        fuOrderFollowErrorMapper.insertSelective(error);

        return true;
    }
}
