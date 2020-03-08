package com.future.service.order;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.future.common.constants.UserConstant;
import com.future.common.exception.BusinessException;
import com.future.common.exception.ParameterInvalidException;
import com.future.common.util.ConvertUtil;
import com.future.common.util.RedisManager;
import com.future.entity.order.FuOrderCustomer;
import com.future.entity.order.FuOrderFollowInfo;
import com.future.mapper.order.FuOrderFollowInfoMapper;
import com.future.pojo.bo.order.UserMTAccountBO;
import com.future.service.account.FuAccountMtService;
import com.future.service.trade.FuTradeOrderService;
import com.future.service.user.AdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

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
    FuOrderFollowInfoMapper fuOrderFollowInfoMapper;
    @Autowired
    RedisManager redisManager;

    /**
     * 根据时间段 查询用户历史订单
     * @param userId,
     * @param username
     * @param accountId
     * @param dataFrom
     * @param dateTo
     * @return
     */
    public List<FuOrderFollowInfo> geMTtHistoryOrders(String userId,String username, String accountId,Date dataFrom,Date dateTo){
        return getMTOrders(true,userId,username,accountId,dataFrom,dateTo);
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
     * @param dataFrom
     * @param dateTo
     * @return
     */
    public List<FuOrderFollowInfo> getMTOrders(Boolean isHistoryOrder, String userId,String username, String mtAccId,Date dataFrom,Date dateTo){
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
        // todo 考虑多账户问题
        Integer userType=userMtAcc.getUserType();
        String serverName=String.valueOf(userMtAcc.getServerName());
        String mtPassword=String.valueOf(userMtAcc.getMtPasswordTrade());
        if(StringUtils.isEmpty(serverName)){
            log.error("根据时间段 查询用户历史订单，用户MT4账户信息有误！");
            return null;
        }
        JSONArray orderJson=new JSONArray();
        if(isHistoryOrder){
            orderJson= fuTradeOrderService.getUserCloseOrders(serverName,Integer.parseInt(mtAccId),mtPassword, dataFrom.getTime(),dateTo.getTime());
        }else {
            orderJson= fuTradeOrderService.getUserOpenOrders(serverName,Integer.parseInt(mtAccId),mtPassword, dataFrom.getTime(),dateTo.getTime());
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
     * 插入数据
     * @param record
     * @return
     */
    public int insertSelective(FuOrderFollowInfo record){
       return fuOrderFollowInfoMapper.insertSelective(record);
    }

    /**
     * 查询 用户社区内最后的自如交易订单
     * @param username
     * @return
     */
    public FuOrderCustomer getLastCustomerOrder(String username){
        FuOrderCustomer lastOrder=new FuOrderCustomer();
        /*从数据库表 fuOrderCustomer中，查出最新自主交易订单*/
        return lastOrder;
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
}
