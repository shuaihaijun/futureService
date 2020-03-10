package com.future.service.order;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.future.common.constants.OrderConstant;
import com.future.common.enums.TradeErrorEnum;
import com.future.entity.order.FuOrderFollowError;
import com.future.entity.user.FuUserFollows;
import com.future.mapper.order.FuOrderFollowErrorMapper;
import com.future.pojo.bo.order.UserMTAccountBO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FuOrderFollowErrorService extends ServiceImpl<FuOrderFollowErrorMapper, FuOrderFollowError> {

    Logger log = LoggerFactory.getLogger(FuOrderFollowErrorService.class);

    @Autowired
    FuOrderFollowErrorMapper fuOrderFollowErrorMapper;


    /**
     * 处理跟单失败逻辑
     * @param updateAction
     * @param signalOrder
     * @param followRule
     * @param errorCode
     * @return
     */
    public boolean dealFollowErrorData(int updateAction,JSONObject signalOrder,JSONObject followRule,int errorCode){

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
        error.setSignalOrderId(signalOrder.getString("tradeRecord"));
        error.setOrderSymbol(new String(orderInfo.getBytes("symbol")).trim());
        error.setOrderType(orderInfo.getInteger("cmd"));
        error.setOrderOpenDate(orderInfo.getTimestamp("open_time"));
        error.setOrderOpenPrice(orderInfo.getBigDecimal("open_price"));
        if(updateAction==OrderConstant.ORDER_OPERATION_OPEN){
            //开仓
            error.setOrderTradeOperation(OrderConstant.ORDER_OPERATION_OPEN);
        }else if(updateAction==OrderConstant.ORDER_OPERATION_CLOSE){
            error.setOrderTradeOperation(OrderConstant.ORDER_OPERATION_CLOSE);
            error.setOrderCloseDate(orderInfo.getTimestamp("close_time"));
            error.setOrderClosePrice(orderInfo.getBigDecimal("close_price"));
        }

        fuOrderFollowErrorMapper.insert(error);

        return true;
    }
}
