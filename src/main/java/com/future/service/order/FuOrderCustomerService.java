package com.future.service.order;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.future.common.constants.OrderConstant;
import com.future.common.exception.BusinessException;
import com.future.common.exception.DataConflictException;
import com.future.common.util.ConvertUtil;
import com.future.common.util.StringUtils;
import com.future.entity.order.FuOrderCustomer;
import com.future.entity.order.FuOrderFollowInfo;
import com.future.mapper.order.FuOrderCustomerMapper;
import com.future.pojo.bo.order.UserMTAccountBO;
import com.future.service.account.FuAccountMtSevice;
import com.future.service.account.FuAccoutInfoService;
import com.future.service.mt.MTOrderService;
import com.jfx.Broker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.*;


@Service
public class FuOrderCustomerService extends ServiceImpl<FuOrderCustomerMapper, FuOrderCustomer> {

    Logger log=LoggerFactory.getLogger(FuOrderCustomerService.class);

    @Autowired
    FuAccoutInfoService fuAccoutInfoService;
    @Autowired
    FuAccountMtSevice fuAccountMtSevice;
    @Autowired
    FuOrderFollowInfoService fuOrderInfoService;
    @Autowired
    MTOrderService mtOrderService;
    @Autowired
    FuOrderCustomerMapper fuOrderCustomerMapper;

    /**
     * 查询 用户已同步最后的自如交易订单
     * @param username
     * @return
     */
    public FuOrderCustomer getLastCustomerOrder(String username){
        /*校验username*/
        if(StringUtils.isEmpty(username)){
            log.error("查询 用户已同步最后的自如交易订单，传入用户信息为空！");
            throw new DataConflictException("查询 用户已同步最后的自如交易订单，传入用户信息为空！");
        }
        /*从数据库表 fuOrderCustomer中，查出最新自主交易订单*/
        return fuOrderCustomerMapper.getLastCustomerOrder(username);
    }

    /**
     * 同步用户历史订单至社区
     * @param userId
     * @param username
     */
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
        List<UserMTAccountBO> accounts=fuAccountMtSevice.getUserMTAccByCondition(conditionMap);
        if(accounts==null||accounts.size()<1){
            log.error("用户"+username+"信息未绑定");
        }
        /*获取用户最后自主交易订单（社区内）*/
        FuOrderCustomer lastOrder=getLastCustomerOrder(username);
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

        try {
            for(UserMTAccountBO userMTAccountBO:accounts){
                if(!userMTAccountBO.getMtAccId().equalsIgnoreCase("2102261700")){
                    continue;
                }
                customers.clear();
                fuOrderInfos.clear();
                server=String.valueOf(userMTAccountBO.getServerName());
                password=String.valueOf(userMTAccountBO.getMtPasswordTrade());
                userId=Integer.valueOf(userMTAccountBO.getUserId());
                mtAccountId=String.valueOf(userMTAccountBO.getMtAccId());

                if(com.alibaba.druid.util.StringUtils.isEmpty(server)){
                    log.error("根据时间段 查询用户历史订单，用户MT4账户信息有误！");
                    continue;
                }
                broker=new Broker(server);

                /*只同步已完成的订单*/
                fuOrderInfos=mtOrderService.getHistoryOrders(broker,mtAccountId,password,lastCLostTime,new Date(),null);

                if(!ObjectUtils.isEmpty(fuOrderInfos)){
                    for(FuOrderFollowInfo fuOrderInfo:fuOrderInfos){
                        /*与社区订单查重，已有订单无需操作*/
                        selectMap.put("order_id",fuOrderInfo.getOrderId());
                        List<FuOrderCustomer> infos=fuOrderCustomerMapper.selectByMap(selectMap);
                        selectMap.clear();
                        if(infos!=null&&infos.size()>0){
                            /*数据已存在*/
                            continue;
                        }
                        /*转换订单*/
                        FuOrderCustomer customerInfo= ConvertUtil.convertOrderCustomer(fuOrderInfo);
                        customerInfo.setUserId(userId);
                        customerInfo.setMtAccId(mtAccountId);
                        customerInfo.setOrderState(OrderConstant.ORDER_STATE_CLOSE);
                        fuOrderCustomerMapper.insertSelective(customerInfo);
                        customers.add(customerInfo);
                    }
                    /*批量保存用户自交易订单*/
//                    insertBatch(customers);
                }
            }
        }catch (Exception e){
            log.error("同步用户历史订单至社区："+e.getMessage());
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
