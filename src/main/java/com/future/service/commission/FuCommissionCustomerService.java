package com.future.service.commission;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.future.common.constants.CommissionConstant;
import com.future.common.exception.BusinessException;
import com.future.entity.account.FuAccountInfo;
import com.future.entity.commission.FuCommissionCustomer;
import com.future.entity.commission.FuCommissionLevel;
import com.future.entity.order.FuOrderCustomer;
import com.future.entity.user.FuUser;
import com.future.mapper.commission.FuCommissionCustomerMapper;
import com.future.service.account.FuAccountInfoSevice;
import com.future.service.user.AdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;
import java.math.BigDecimal;
import java.util.*;

/**
 * 用户订单佣金业务逻辑
 */
@Service
public class FuCommissionCustomerService extends ServiceImpl<FuCommissionCustomerMapper,FuCommissionCustomer> {

    Logger log=LoggerFactory.getLogger(FuCommissionCustomerService.class);
    @Autowired
    FuAccountInfoSevice fuAccountInfoSevice;
    @Autowired
    FuCommissionLevelSevice fuCommissionLevelSevice;
    @Autowired
    AdminService adminService;
    @Autowired
    FuCommissionCustomerMapper fuCommissionCustomerMapper;

    /**
     * 处理用户自交易订单佣金
     * @param userId
     * @param orderCustomers
     */
    public void dealUserOrderCommission(Integer userId, List<FuOrderCustomer> orderCustomers){
        if(orderCustomers==null || orderCustomers.size()==0){
            log.error("处理用户自交易订单佣金,参数为空！");
            return;
        }

        Map conditionMap =new HashMap();
        conditionMap.put(FuAccountInfo.USER_ID,userId);
        List<FuAccountInfo> accountInfos= fuAccountInfoSevice.selectByMap(conditionMap);
        if(accountInfos==null || accountInfos.size()==0){
            log.error("处理用户自交易订单佣金,查询用户账户为空！");
        }

        List<FuCommissionCustomer> commissions=new ArrayList<>();
        for(FuOrderCustomer orderCustomer:orderCustomers){

            FuCommissionCustomer commission=new FuCommissionCustomer();
            /*订单ID*/
//            commission.setSourceOrderId(orderCustomer.getOrderId());
            /*用户ID*/
            commission.setSourceUserId(orderCustomer.getUserId());
            /*账户ID*/
            commission.setSourceAccountId(accountInfos.get(0).getId());
            /*订单操作类型*/
            commission.setSourceOrderOper(orderCustomer.getOrderType());
            /*订单状态*/
            commission.setSourceOrderState(orderCustomer.getOrderState());
            /*订单手数*/
            commission.setSourceOrderLots(orderCustomer.getOrderLots());
            /*订单购买金额*/
            commission.setSourceOrderMoney(orderCustomer.getOrderOpenPrice().multiply(orderCustomer.getOrderLots()));
            /*订单开仓时间*/
            commission.setOperDate(orderCustomer.getOrderOpenDate());

            commissions.add(commission);
        }

        /*计算下一级佣金*/
        dealCommissionLevel(userId,CommissionConstant.COMMISSION_TYPE_AGENT,CommissionConstant.COMMISSION_ORDER_TYPE_CUSTOMER,
                CommissionConstant.COMMISSION_RATE_TYPE_LOTS,CommissionConstant.COMMISSION_USER_LEVEL_FIRST,commissions);

    }

    /**
     * 计算佣金
     * @param levelUserId
     * @param commissionType
     * @param orderType
     * @param rateType
     * @param level
     * @param commissions
     */
    public void dealCommissionLevel(Integer levelUserId,Integer commissionType,Integer orderType,Integer rateType, Integer level, List<FuCommissionCustomer> commissions){

        /*校验数据*/
        if(levelUserId==0 || commissions==null||commissions.size()==0||level==0){
            log.error("计算佣金时，获取参数为空！");
        }

        /*查询第n级客户*/
        FuUser fuUser= adminService.findUserIntroducer(levelUserId);
        if(fuUser==null||fuUser.getId()==0){
            /*该用户无推荐用户（自助注册有用/升级过猛用户）*/
            log.warn("用户："+levelUserId+"无推荐人！计算上级佣金失败！");
            return;
        }
        Map conditionMap =new HashMap();
        conditionMap.put(FuAccountInfo.USER_ID,levelUserId);
        List<FuAccountInfo> accountInfos= fuAccountInfoSevice.selectByMap(conditionMap);
        if(accountInfos==null || accountInfos.size()==0){
            log.error("处理用户自交易订单佣金,查询用户账户为空！");
        }


        /*根据客户登记查询返佣等级*/
        Map condtionMap =new HashMap();
        condtionMap.put(FuCommissionLevel.COMMISSION_TYPE,commissionType);
        condtionMap.put(FuCommissionLevel.ORDER_TYPE,orderType);
        condtionMap.put(FuCommissionLevel.COMMISSION_USER_TYPE,fuUser.getUserType());
        condtionMap.put(FuCommissionLevel.COMMISSION_USER_LEVEL,level);
        condtionMap.put(FuCommissionLevel.RATE_TYPE,rateType);
        List<FuCommissionLevel> commissionLevels= fuCommissionLevelSevice.selectByMap(condtionMap);
        if(commissionLevels==null || commissionLevels.size()==0){
            log.error("根据条件查询返佣规则失败，Data:"+ JSON.toJSON(condtionMap));
            throw new BusinessException("根据条件查询返佣规则失败!");
        }
        if(commissionLevels.size()>1){
            log.warn("根据条件查询返佣规则多条，Data:"+ JSON.toJSON(condtionMap));
        }

        /*根据返佣等级计算佣金*/
        for(FuCommissionCustomer commission:commissions ){
            commission.setCommissionType(commissionType);
            commission.setSourceUserId(fuUser.getId());
            commission.setCommissionUserType(fuUser.getUserType());
            commission.setCommissionUserLevel(level);
            commission.setSourceAccountId(accountInfos.get(0).getId());
//            commission.setCommissionRateType();
            commission.setCommissionRate(commissionLevels.get(0).getRate());
            commission.setCommissionDate(new Date());
            /*比率计算类型（0 交易手数，1 按原金额，2 按返佣金额, 3 指定金额）*/
            if(rateType.equals(CommissionConstant.COMMISSION_RATE_TYPE_LOTS)){
                commission.setCommissionMoney(commission.getSourceOrderLots().multiply(commissionLevels.get(0).getRate()));
            }else if(rateType.equals(CommissionConstant.COMMISSION_RATE_TYPE_MONEY_SOURCE)){
                commission.setCommissionMoney(commission.getSourceOrderMoney().multiply(commissionLevels.get(0).getRate()));
            }else if(rateType.equals(CommissionConstant.COMMISSION_RATE_TYPE_MONEY)){
                commission.setCommissionMoney(commissionLevels.get(0).getRate());
            }else {
                /*此处无法按 按返佣金额*/
                log.error("此处无法按 按返佣金额!");
                throw new BusinessException("返佣计算，此处无法按 按返佣金额!");
            }
        }

        /*保存数据*/
        insertBatch(commissions);

        /*继续下一级别*/
        if(level.equals(CommissionConstant.COMMISSION_USER_LEVEL_THIRD)){
            return;
        }else {
            level++;
        }

        /*计算上一级佣金*/
        dealCommissionLevel(fuUser.getId(),CommissionConstant.COMMISSION_TYPE_AGENT,CommissionConstant.COMMISSION_ORDER_TYPE_CUSTOMER,
                CommissionConstant.COMMISSION_RATE_TYPE_LOTS,CommissionConstant.COMMISSION_USER_LEVEL_FIRST,commissions);
    }

    /**
     * 处理用户跟单订单佣金
     */
    public void dealUserFollowOrderCommission(){

    }
}
