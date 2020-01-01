package com.future.service.order;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.future.common.constants.OrderConstant;
import com.future.common.constants.UserConstant;
import com.future.common.exception.BusinessException;
import com.future.common.exception.DataConflictException;
import com.future.common.util.ConvertUtil;
import com.future.common.util.StringUtils;
import com.future.entity.order.FuOrderCustomer;
import com.future.entity.order.FuOrderFollowInfo;
import com.future.mapper.order.FuOrderCustomerMapper;
import com.future.pojo.bo.order.UserMTAccountBO;
import com.future.service.account.FuAccountMtService;
import com.future.service.commission.FuCommissionCustomerService;
import com.future.service.mt.MTOrderService;
import com.future.service.mt.MTStrategy;
import com.future.service.product.FuUserFollowsService;
import com.jfx.AccountInfo;
import com.jfx.Broker;
import com.jfx.SelectionPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.*;


@Service
public class FuOrderCustomerService extends ServiceImpl<FuOrderCustomerMapper, FuOrderCustomer> {

    Logger log=LoggerFactory.getLogger(FuOrderCustomerService.class);

    @Autowired
    FuAccountMtService fuAccountMtService;
    @Autowired
    FuOrderFollowInfoService fuOrderFollowInfoService;
    @Autowired
    MTOrderService mtOrderService;
    @Autowired
    FuCommissionCustomerService fuCommissionCustomerService;
    @Autowired
    FuUserFollowsService fuUserFollowsService;
    @Autowired
    FuOrderCustomerMapper fuOrderCustomerMapper;


    /**
     * 查询 用户已同步最后的自如交易订单
     * @param userId
     * @param username
     * @return
     */
    public FuOrderCustomer getLastCustomerOrder(Integer userId,String username){
        /*校验username*/
        if((userId==null ||userId==0)&& StringUtils.isEmpty(username)){
            log.error("查询 用户已同步最后的自如交易订单，传入用户信息为空！");
            throw new DataConflictException("查询 用户已同步最后的自如交易订单，传入用户信息为空！");
        }
        Map condition=new HashMap();
        if(userId!=null && userId>0){
            condition.put("userId",userId);
        }
        if(StringUtils.isEmpty(username)){
            condition.put("username",username);
        }
        /*从数据库表 fuOrderCustomer中，查出最新自主交易订单*/
        return fuOrderCustomerMapper.getLastCustomerOrder(condition);
    }

    /**
     * 同步用户历史订单至社区
     * @param userId
     * @param username
     */
    @Transactional
    public void synUserMTOrder(Integer userId,String username){

        if(StringUtils.isEmpty(username)&&userId==0){
            log.error("同步用户历史订单时，传入用户数据为空！");
            throw new DataConflictException("同步用户历史订单时，传入用户数据为空！");
        }
        Map conditionMap=new HashMap();
        if(userId>0){
            conditionMap.put("userId",userId);
        }
        if(!StringUtils.isEmpty(username)){
            conditionMap.put("username",username);
        }
        /*查询用户账户信息*/
        List<UserMTAccountBO> accounts= fuAccountMtService.getUserMTAccByCondition(conditionMap);
        if(accounts==null||accounts.size()<1){
            log.error("用户"+username+"信息未绑定");
            return;
        }
        /*获取用户最后自主交易订单（社区内）*/
        FuOrderCustomer lastOrder=getLastCustomerOrder(userId,username);
        /*根据闭单时间处理*/
        Date lastCLostTime=null;
        if(!ObjectUtils.isEmpty(lastOrder)){
            lastCLostTime=lastOrder.getCreateDate();
        }

        List<FuOrderFollowInfo> fuOrderInfos=new ArrayList<FuOrderFollowInfo>();
        List<FuOrderCustomer> customers=new ArrayList<FuOrderCustomer>();
        FuOrderFollowInfo info;
        Broker broker;
        String server;
        String password;
        String mtAccountId;
        Map selectMap=new HashMap();
        selectMap.put(FuOrderCustomer.USER_ID,userId);
        try {
            for(UserMTAccountBO userMTAccountBO:accounts){
                customers.clear();
                fuOrderInfos.clear();
                server=String.valueOf(userMTAccountBO.getServerName());
                password=String.valueOf(userMTAccountBO.getMtPasswordWatch());
                userId=Integer.valueOf(userMTAccountBO.getUserId());
                mtAccountId=String.valueOf(userMTAccountBO.getMtAccId());
                conditionMap.clear();
                conditionMap.put("userId",userId);

                if(StringUtils.isEmpty(server)){
                    log.error("根据时间段 查询用户历史订单，用户MT4账户信息有误！");
                    continue;
                }
                broker=new Broker(server);
                MTStrategy strategy=new MTStrategy();
                strategy.setMtData(broker,mtAccountId,password);
                strategy.setServerData(userMTAccountBO.getAccountUrl(),userMTAccountBO.getAccountPort());

                Map orderAndAccount=mtOrderService.getOrderAndAccountInfo(strategy, SelectionPool.MODE_HISTORY,lastCLostTime,new Date(),null);
                /*只同步已完成的订单*/
                fuOrderInfos=(List<FuOrderFollowInfo>)orderAndAccount.get("orders");
                /*跟新 userId 的 mtAccId 账户信息*/
                AccountInfo accountInfo=(AccountInfo)orderAndAccount.get("accountInfo");

                fuAccountMtService.updateAccFromMt(userId,mtAccountId,accountInfo);

                /*处理订单*/
                if(!ObjectUtils.isEmpty(fuOrderInfos)){
                    for(FuOrderFollowInfo fuOrderInfo:fuOrderInfos){
                        /*与社区订单查重，已有订单无需操作*/
                        selectMap.put(FuOrderCustomer.ORDER_ID,fuOrderInfo.getOrderId());
                        List<FuOrderCustomer> infos=fuOrderCustomerMapper.selectByMap(selectMap);
                        if(infos!=null&&infos.size()>0){
                            /*数据已存在=*/
                            continue;
                        }
                        FuOrderFollowInfo followInfo= fuOrderFollowInfoService.getOrderByCondition(userId,fuOrderInfo.getOrderId());
                        if(followInfo!=null){
                            /*跟单时 已处理过的数据=*/
                            continue;
                        }
                        /*转换订单*/
                        FuOrderCustomer customerInfo= ConvertUtil.convertOrderCustomer(fuOrderInfo);
                        customerInfo.setUserId(userId);
                        customerInfo.setMtAccId(mtAccountId);
                        customerInfo.setMtServerId(userMTAccountBO.getServerId());
                        customerInfo.setMtServerName(accountInfo.getServer());
                        customerInfo.setOrderState(OrderConstant.ORDER_STATE_CLOSE);

                        /*保存用户自交易订单*/
                        fuOrderCustomerMapper.insertSelective(customerInfo);
                        //计算出入金
                        if(customerInfo.getOrderType()==OrderConstant.ORDER_TYPE_DEPOSIT){
                            //TODO  计算出入金
                        }
                        if(customerInfo.getOrderType()==OrderConstant.ORDER_TYPE_BUY
                            ||customerInfo.getOrderType()==OrderConstant.ORDER_TYPE_SELL){
                            customers.add(customerInfo);
                        }
                    }
                    /*用户自交易订单 计算代理佣金*/
                    fuCommissionCustomerService.dealUserOrderCommission(userId,customers);
                }
                /*判断是否关闭该链接*/
                if(fuUserFollowsService.signalFollowsQuery(conditionMap)==null && userMTAccountBO.getUserType()!= UserConstant.USER_TYPE_SIGNAL){
                   //如果无跟单任务+非信号源，就关闭该链接
                    strategy.close(true);
                }
            }
        }catch (Exception e){
            log.error("同步用户历史订单至社区："+e.getMessage());
            log.error(e.getMessage(),e);
            throw new BusinessException(e);
        }

    }

    /**
     * 根据条件查询用户自主交易订单
     * @param requestMap
     * @return
     */
    public Page<FuOrderCustomer> getByConditon(int pageNum, int pageSize, Map requestMap){

        /*拼接查询条件*/
        Map conditionMap=new HashMap();
        conditionMap.put("userId",requestMap.get("userId"));
        conditionMap.put("orderId",requestMap.get("orderId"));
        conditionMap.put("orderSymbol",requestMap.get("orderSymbol"));
        if(!ObjectUtils.isEmpty(requestMap.get("orderOpenDate"))){
        }
        conditionMap.put("",requestMap.get("orderOpenDate"));
        conditionMap.put("",requestMap.get("orderCloseDate"));

        return null;
    }
}
