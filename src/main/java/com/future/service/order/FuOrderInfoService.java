package com.future.service.order;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.future.common.exception.BusinessException;
import com.future.entity.order.FuOrderCustomer;
import com.future.entity.order.FuOrderFollowInfo;
import com.future.mapper.order.FuOrderFollowInfoMapper;
import com.future.pojo.bo.order.UserMTAccountBO;
import com.future.service.account.FuAccountMtSevice;
import com.future.service.account.FuAccoutInfoService;
import com.future.service.mt.MTOrderService;
import com.future.service.user.AdminService;
import com.jfx.Broker;
import com.jfx.TradeOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.*;

@Service
public class FuOrderInfoService extends ServiceImpl<FuOrderFollowInfoMapper,FuOrderFollowInfo> {

    Logger log = LoggerFactory.getLogger(FuOrderInfoService.class);

    @Autowired
    AdminService adminService;
    @Autowired
    MTOrderService mtOrderService;
    @Autowired
    FuAccoutInfoService fuAccoutInfoService;
    @Autowired
    FuAccountMtSevice fuAccountMtSevice;
    @Autowired
    FuOrderCustomerService fuOrderCustomerService;

    /**
     * 根据时间段 查询用户历史订单
     * @param userId,
     * @param username
     * @param accountId
     * @param dataFrom
     * @param dateTo
     * @param symbol
     * @return
     */
    public List<FuOrderFollowInfo> geMTtHistoryOrders(String userId,String username, String accountId,Date dataFrom,Date dateTo,String symbol){
        return getMTOrders(true,userId,username,accountId,dataFrom,dateTo,symbol);
    }


    /**
     * 获取用户在仓订单信息
     * @param userId
     * @param username
     * @param accountId
     * @param dataFrom
     * @param dateTo
     * @param symbol
     * @return
     */
    public List<FuOrderFollowInfo> getMTAliveOrders(String userId,String username, String accountId,Date dataFrom,Date dateTo,String symbol){
        return getMTOrders(false,userId,username,accountId,dataFrom,dateTo,symbol);
    }

    /**
     * 根据MT订单号查询用户历史订单详情
     * @param username
     * @param accountId
     * @param index
     * @return
     */
    public FuOrderFollowInfo getMTHistoryOrderByIndex(String username, String accountId,long index){
        return getMTOrderByIndex(username,accountId,index,true);
    }

    /**
     * 根据MT订单号查询用户在仓订单详情
     * @param username
     * @param accountId
     * @param index
     * @return
     */
    public FuOrderFollowInfo getMTAliveOrderByIndex(String username, String accountId,long index){
        return getMTOrderByIndex(username,accountId,index,false);
    }

    /**
     * 根据条件获取MT的订单信息
     * @param isHistoryOrder
     * @param userId
     * @param username
     * @param accountId
     * @param dataFrom
     * @param dateTo
     * @param symbol
     * @return
     */
    public List<FuOrderFollowInfo> getMTOrders(Boolean isHistoryOrder, String userId,String username, String accountId,Date dataFrom,Date dateTo,String symbol){
        if(StringUtils.isEmpty(username)&&StringUtils.isEmpty(userId)){
            log.error("根据时间段 查询用户历史订单，用户信息为空！");
            return null;
        }
        /*根据用户名 验证用户信息*/
        /*FuUser fuUser=adminService.findByUsername(username);
        if(ObjectUtils.isEmpty(fuUser)){
            log.error("根据时间段 查询用户历史订单，用户信息不存在！");
            return null;
        }*/

        /*验证用户登录状态*/


        /*查询用户历史订单*/
        Map acountMap=new HashMap();
        if(!StringUtils.isEmpty(accountId)){
            acountMap.put("accountId",accountId);
        }else {
            /*默认查询主账户号*/
            acountMap.put("username",username);
            acountMap.put("isChief",1);
        }
        List<UserMTAccountBO> mts=fuAccountMtSevice.getUserMTAccByCondition(acountMap);
        if(ObjectUtils.isEmpty(mts)||mts.isEmpty()){
            log.error("根据时间段查询用户历史订单|用户MT4账户未绑定！");
            throw new BusinessException("根据时间段查询用户历史订单|用户MT4账户未绑定！");
        }
        String server=String.valueOf(mts.get(0).getServerName());
        String mtAccId=String.valueOf(mts.get(0).getMtAccId());
        String mtPassword=String.valueOf(mts.get(0).getMtPasswordTrade());

        if(StringUtils.isEmpty(server)){
            log.error("根据时间段 查询用户历史订单，用户MT4账户信息有误！");
            return null;
        }
        Broker broker=new Broker(server);
        List<FuOrderFollowInfo> infos=new ArrayList<FuOrderFollowInfo>();
        if(isHistoryOrder){
            infos= mtOrderService.getHistoryOrders(broker,mtAccId,mtPassword,dataFrom,dateTo,symbol);
        }else {
            infos= mtOrderService.getAliveOrders(broker,mtAccId,mtPassword,dataFrom,dateTo,symbol);
        }

        /*填充数据*/
        for (FuOrderFollowInfo info:infos){
//            info.setUserMtAccId(mtAccId);
            info.setUserId(Integer.parseInt(userId));
        }
        return infos;
    }


    /**
     * 根据Mt订单号 查询用户订单信息
     * @param username
     * @param accountId
     * @param index
     * @param isHistoryOrder
     * @return
     */
    public FuOrderFollowInfo getMTOrderByIndex(String username, String accountId,long index,Boolean isHistoryOrder){

        if(StringUtils.isEmpty(username)||index==0){
            log.error("根据时间段 查询用户历史订单，数据为空！");
            return null;
        }
        /*根据用户名 验证用户信息*/
        /*FuUser fuUser=adminService.findByUsername(username);
        if(ObjectUtils.isEmpty(fuUser)){
            log.error("根据时间段 查询用户历史订单，用户信息不存在！");
            return null;
        }*/

        /*验证用户登录状态*/

        /*查询用户历史订单*/
        Map acountMap=new HashMap();
        if(StringUtils.isEmpty(accountId)){
            /*默认查询主账户号*/
            acountMap.put("username",username);
            acountMap.put("isChief",1);
        }else {
            acountMap.put("accountId",accountId);
        }
        /*查询用户账户信息*/
        List<UserMTAccountBO> mts=fuAccountMtSevice.getUserMTAccByCondition(acountMap);
        if(ObjectUtils.isEmpty(mts)||mts.isEmpty()){
            log.error("根据时间段查询用户历史订单|用户MT4账户未绑定！");
            throw new BusinessException("根据时间段查询用户历史订单|用户MT4账户未绑定！");
        }
        String server=String.valueOf(mts.get(0).getServerName());
        String mtAccId=String.valueOf(mts.get(0).getMtAccId());
        String mtPassword=String.valueOf(mts.get(0).getMtPasswordTrade());
        if(StringUtils.isEmpty(server)){
            log.error("根据时间段 查询用户历史订单，用户MT4账户信息有误！");
            return null;
        }
        Broker broker=new Broker(server);

        if(isHistoryOrder){
            /*历史订单*/
            return mtOrderService.getHistoryOrderByIndex(broker,mtAccId,mtPassword,index);
        }else {
            /*在仓订单*/
            return mtOrderService.getAliveOrderByIndex(broker,mtAccId,mtPassword,index);
        }
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
     * 根据orderId查询订单信息
     * @param orderId
     * @return
     */
    public FuOrderFollowInfo getOrderById(String orderId){

        FuOrderFollowInfo fuOrderInfo=new FuOrderFollowInfo();
        /*redis 或 数据库*/
        return null;
    }



    /**
     * 生成订单
     * @param symbol 币种
     * @param operate 交易方式
     * @param lots 数量
     * @return
     */
    public long sendOrder(String symbol, TradeOperation operate, double lots){
        /*返回订单号orderId*/
        /*有没有必要 保存该订单至数据库？*/
        return mtOrderService.sendOrder(symbol,operate,lots);
    }
}
