package com.future.service.order;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.future.common.constants.OrderConstant;
import com.future.common.enums.GlobalResultCode;
import com.future.common.exception.BusinessException;
import com.future.common.exception.DataConflictException;
import com.future.common.exception.ParameterInvalidException;
import com.future.common.helper.PageInfoHelper;
import com.future.common.util.ConvertUtil;
import com.future.common.util.DateUtil;
import com.future.common.util.StringUtils;
import com.future.entity.order.FuOrderCustomer;
import com.future.entity.order.FuOrderFollowInfo;
import com.future.entity.user.FuUser;
import com.future.mapper.order.FuOrderCustomerMapper;
import com.future.pojo.bo.account.UserMTAccountBO;
import com.future.service.account.FuAccountMtService;
import com.future.service.commission.FuCommissionCustomerService;
import com.future.service.trade.FuTradeOrderService;
import com.future.service.user.AdminService;
import com.future.service.user.FuUserFollowsService;
import com.future.service.user.UserCommonService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jfx.Broker;
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
    FuTradeOrderService fuTradeOrderService;
    @Autowired
    FuCommissionCustomerService fuCommissionCustomerService;
    @Autowired
    FuUserFollowsService fuUserFollowsService;
    @Autowired
    UserCommonService userCommonService;
    @Autowired
    AdminService adminService;
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

        if(userId==null||userId==0){
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
        }else {
            Wrapper<FuUser> wrapper=new EntityWrapper<>();
            wrapper.eq(FuUser.USER_ID,userId);
            FuUser user= adminService.selectOne(wrapper);
            lastCLostTime=user.getModifyDate();
        }

        List<FuOrderCustomer> selectOrdercustomers=new ArrayList<FuOrderCustomer>();
        List<FuOrderCustomer> newOrderCustomers=new ArrayList<FuOrderCustomer>();
        FuOrderFollowInfo info;
        Broker broker;
        String server;
        String password;
        String mtAccId;
        Map selectMap=new HashMap();
        selectMap.put(FuOrderCustomer.USER_ID,userId);
        try {
            for(UserMTAccountBO userMTAccountBO:accounts){
                selectOrdercustomers.clear();
                newOrderCustomers.clear();
                server=String.valueOf(userMTAccountBO.getServerName());
                password=String.valueOf(userMTAccountBO.getMtPasswordWatch());
                userId=Integer.valueOf(userMTAccountBO.getUserId());
                mtAccId=String.valueOf(userMTAccountBO.getMtAccId());
                conditionMap.clear();
                conditionMap.put("userId",userId);

                if(StringUtils.isEmpty(server)){
                    log.error("根据时间段 查询用户历史订单，用户MT4账户信息有误！");
                    continue;
                }
                JSONArray orders= fuTradeOrderService.getUserCloseOrders(server,Integer.parseInt(mtAccId),password,lastCLostTime.getTime(),0);
                if(orders==null||orders.size()==0){
                    return;
                }
                /*只同步已完成的订单*/
                selectOrdercustomers=ConvertUtil.convertOrderCustomers(orders);

                /*跟新 userId 的 mtAccId 账户信息*/ // todo
                /*fuAccountMtService.updateAccFromMt(userId,mtAccountId,accountInfo);*/

                /*处理订单*/
                if(ObjectUtils.isEmpty(selectOrdercustomers)) {
                    return;
                }
                for(FuOrderCustomer customer:selectOrdercustomers){
                    /*判断是否是初始化数据*/
                    if(StringUtils.isEmpty(customer.getOrderSymbol())){
                        continue;
                    }
                    /*判断时间范围*/
                    if(lastCLostTime.compareTo(customer.getOrderCloseDate())>0){
                        continue;
                    }
                    //限定交易类型 挂单的订单不做佣金处理
                    if(customer.getOrderType()!=OrderConstant.ORDER_TYPE_BUY
                            &&customer.getOrderType()!=OrderConstant.ORDER_TYPE_SELL){
                        continue;
                    }
                    /*与社区订单查重，已有订单无需操作*/
                    selectMap.put(FuOrderCustomer.ORDER_ID,customer.getOrderId());
                    List<FuOrderCustomer> infos=fuOrderCustomerMapper.selectByMap(selectMap);
                    if(infos!=null&&infos.size()>0){
                        /*数据已存在=*/
                        continue;
                    }

                    /*转换订单*/
                    customer.setUserId(userId);
                    customer.setMtAccId(mtAccId);
                    customer.setMtServerId(userMTAccountBO.getServerId());
                    customer.setMtServerName(server);
                    customer.setOrderState(OrderConstant.ORDER_STATE_CLOSE);

                    /*保存用户自交易订单*/
                    fuOrderCustomerMapper.insertSelective(customer);
                    //计算出入金
                    if(customer.getOrderType()==OrderConstant.ORDER_TYPE_DEPOSIT){
                    }
                    /*需要计算佣金的数据  暂存*/
                    newOrderCustomers.add(customer);
                }
                /*用户自交易订单 计算代理佣金*/
                if(newOrderCustomers!=null&&newOrderCustomers.size()>0){
                    fuCommissionCustomerService.dealUserOrderCommission(userId,newOrderCustomers);
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

    public PageInfo<FuOrderFollowInfo> getMTAliveOrders(Map conditionMap, PageInfoHelper helper){
        // 获取请求参数
        String accountId="";
        String username="";
        String userId="";
        String orderSymbol="";
        String orderOpenDate="";
        String dateFrom="";
        String dataTo="";

        if(conditionMap == null||ObjectUtils.isEmpty(conditionMap.get("operUserId"))){
            log.error("查询用户列表,获取参数为空！");
            throw new ParameterInvalidException("查询用户列表,获取参数为空！");
        }
        if(!ObjectUtils.isEmpty(conditionMap.get("accountId"))){
            accountId=String.valueOf(conditionMap.get("accountId"));
        }
        if(!ObjectUtils.isEmpty(conditionMap.get("username"))){
            username=String.valueOf(conditionMap.get("username"));
        }
        if(!ObjectUtils.isEmpty(conditionMap.get("userId"))){
            userId=String.valueOf(conditionMap.get("userId"));
        }
        if(!ObjectUtils.isEmpty(conditionMap.get("orderSymbol"))){
            orderSymbol=String.valueOf(conditionMap.get("orderSymbol"));
        }
        if(!ObjectUtils.isEmpty(conditionMap.get("orderOpenDate"))){
            orderOpenDate=String.valueOf(conditionMap.get("orderOpenDate"));
        }

        /*判断权限*/
        String operUserId=String.valueOf(conditionMap.get("operUserId"));
        if(com.alibaba.druid.util.StringUtils.isEmpty(operUserId)){
            log.error("查询用户列表,用户未登录！");
            throw new ParameterInvalidException("查询用户列表,获取参数为空！");
        }
        Integer operUserProj=userCommonService.getUserProjKey(Integer.parseInt(operUserId));
        Boolean isProjAdmin=userCommonService.isAdministrator(Integer.parseInt(operUserId),operUserProj);
        if(operUserProj==null){
            log.error("查询用户列表,用户权限有误！");
            throw new ParameterInvalidException("查询用户列表,用户权限有误！");
        }
        if(isProjAdmin&&operUserProj==0){
            /*超管查询*/
        }else if(isProjAdmin){
            /*资源组管理员查找*/
            Integer userProj=userCommonService.getUserProjKey(Integer.parseInt(userId));
            if(!operUserProj.equals(userProj)){
                log.error("无权限查看该用户数据，请检查用户ID！");
                throw new ParameterInvalidException("无权限查看该用户数据，请检查用户ID！");
            }
            conditionMap.put("projKey",operUserProj);
        }else {
            /*普通用户查找*/
            userId=operUserId;
        }

        if(StringUtils.isEmpty(userId)&&StringUtils.isEmpty(username)){
            log.error("查询时 用户数据不能为空！");
            new DataConflictException(GlobalResultCode.PARAM_NULL_POINTER,"查询时 用户数据不能为空！");
        }
        if(!ObjectUtils.isEmpty(orderOpenDate)&&orderOpenDate.indexOf(",")>0){
            //时间段
            List dateList=(List) conditionMap.get("orderOpenDate");
            if(dateList.size()!=2){
                log.error("建仓时间段数据传入错误！"+conditionMap.get("orderOpenDate"));
                throw new DataConflictException(GlobalResultCode.PARAM_VERIFY_ERROR,"建仓时间段数据传入错误！"+conditionMap.get("orderOpenDate"));
            }
            dateFrom=String.valueOf(dateList.get(0));
            dataTo=String.valueOf(dateList.get(1));
        }
        /*条件查询日期范围不能超过1周*/

        List<FuOrderFollowInfo> orders= fuOrderFollowInfoService.getMTAliveOrders(userId,username,accountId, DateUtil.toDate(dateFrom),DateUtil.toDate(dataTo));
        if(orders==null){
            return null;
        }
        return new PageInfo<FuOrderFollowInfo>(orders);
    }

    /**
     * 根据条件查询用户历史交易订单
     * @param conditionMap
     * @param helper
     * @return
     */
    public Page<FuOrderCustomer> queryOrderCustomer(Map conditionMap, PageInfoHelper helper){
        /*判断查询条件*/
        if(conditionMap == null||conditionMap.get("operUserId")==null){
            log.error("查询用户列表,获取参数为空！");
            throw new ParameterInvalidException("查询用户列表,获取参数为空！");
        }
        /*判断权限*/
        String operUserId=String.valueOf(conditionMap.get("operUserId"));
        if(com.alibaba.druid.util.StringUtils.isEmpty(operUserId)){
            log.error("查询用户列表,用户未登录！");
            throw new ParameterInvalidException("查询用户列表,获取参数为空！");
        }
        Integer operUserProj=userCommonService.getUserProjKey(Integer.parseInt(operUserId));
        Boolean isProjAdmin=userCommonService.isAdministrator(Integer.parseInt(operUserId),operUserProj);
        if(operUserProj==null){
            log.error("查询用户列表,用户权限有误！");
            throw new ParameterInvalidException("查询用户列表,用户权限有误！");
        }

        if(isProjAdmin&&operUserProj==0){
            /*超管查询*/
            return findOrderCustomerByCondition(conditionMap,helper);
        }else if(isProjAdmin){
            /*资源组管理员查找*/
            conditionMap.put("projKey",operUserProj);
            return findOrderCustomerByProject(conditionMap,helper);
        }else {
            /*普通用户查找*/
            conditionMap.put("userId",operUserId);
            return findOrderCustomerByCondition(conditionMap,helper);
        }
    }

    /**
     * 根据project查询用户历史订单
     * @param conditionMap
     * @param helper
     * @return
     */
    public Page<FuOrderCustomer> findOrderCustomerByProject(Map conditionMap, PageInfoHelper helper){
        if(conditionMap.get("projKey")==null){
            log.error("根据project查询用户历史订单失败，数据为空！");
        }
        if(helper==null){
            helper=new PageInfoHelper();
        }
        Page<FuOrderCustomer> orders=PageHelper.startPage(helper.getPageNo(),helper.getPageSize());
        fuOrderCustomerMapper.queryCustomerOrderByProject(conditionMap);
        return orders;
    }

    /**
     * 根据条件查询用户历史订单（单标查询）
     * @param conditionMap
     * @param helper
     * @return
     */
    public Page<FuOrderCustomer> findOrderCustomerByCondition(Map conditionMap, PageInfoHelper helper){

        EntityWrapper<FuOrderCustomer> wrapper=new EntityWrapper<FuOrderCustomer>();

        if(!ObjectUtils.isEmpty(conditionMap.get("userId"))){
            wrapper.eq(FuOrderCustomer.USER_ID,conditionMap.get("userId"));
        }
        if(!ObjectUtils.isEmpty(conditionMap.get("orderId"))){
            wrapper.eq(FuOrderCustomer.ORDER_ID,conditionMap.get("orderId"));
        }
        if(!ObjectUtils.isEmpty(conditionMap.get("orderSymbol"))){
            wrapper.eq(FuOrderCustomer.ORDER_SYMBOL,conditionMap.get("orderSymbol"));
        }
        if(!ObjectUtils.isEmpty(conditionMap.get("orderType"))){
            wrapper.eq(FuOrderCustomer.ORDER_TYPE,conditionMap.get("orderType"));
        }
        if(!ObjectUtils.isEmpty(conditionMap.get("orderOpenDate"))){
            if(String.valueOf(conditionMap.get("orderOpenDate")).indexOf(",")<0){
                wrapper.eq(FuOrderCustomer.ORDER_OPEN_DATE,conditionMap.get("orderOpenDate"));
            }else {
                //时间段
                List dateList=(List) conditionMap.get("orderOpenDate");
                if(dateList.size()!=2){
                    log.error("建仓时间段数据传入错误！"+conditionMap.get("orderOpenDate"));
                    throw new DataConflictException(GlobalResultCode.PARAM_VERIFY_ERROR,"建仓时间段数据传入错误！"+conditionMap.get("orderOpenDate"));
                }
                wrapper.gt(FuOrderCustomer.ORDER_OPEN_DATE,dateList.get(0));
                wrapper.lt(FuOrderCustomer.ORDER_OPEN_DATE,dateList.get(1));
            }
        }
        if(!ObjectUtils.isEmpty(conditionMap.get("orderCloseDate"))){
            if(String.valueOf(conditionMap.get("orderCloseDate")).indexOf(",")<0){
                wrapper.eq(FuOrderCustomer.ORDER_OPEN_DATE,conditionMap.get("orderCloseDate"));
            }else {
                //时间段
                List dateList=(List) conditionMap.get("orderCloseDate");
                if(dateList.size()!=2){
                    log.error("平仓时间段数据传入错误！"+conditionMap.get("orderCloseDate"));
                    throw new DataConflictException(GlobalResultCode.PARAM_VERIFY_ERROR,"平仓时间段数据传入错误！"+conditionMap.get("orderCloseDate"));
                }
                wrapper.gt(FuOrderCustomer.ORDER_CLOSE_DATE,dateList.get(0));
                wrapper.lt(FuOrderCustomer.ORDER_CLOSE_DATE,dateList.get(1));
            }
        }
        wrapper.orderBy(FuOrderCustomer.ORDER_CLOSE_DATE,false);

        if(helper==null){
            helper=new PageInfoHelper();
        }
        Page<FuOrderCustomer> orders=PageHelper.startPage(helper.getPageNo(),helper.getPageSize());
        selectList(wrapper);
        return orders;
    }
}
