package com.future.controller.account;

import com.alibaba.fastjson.JSONObject;
import com.future.common.enums.GlobalResultCode;
import com.future.common.exception.ParameterInvalidException;
import com.future.common.helper.PageInfoHelper;
import com.future.common.result.RequestParams;
import com.future.common.util.ThreadCache;
import com.future.entity.account.FuAccountCommission;
import com.future.entity.commission.FuCommissionLevel;
import com.future.service.account.FuAccountCommissionService;
import com.future.service.commission.FuCommissionLevelSevice;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;

@RestController
@RequestMapping("/account/CommissionInfo")
public class AccountCommissionController {

    Logger log= LoggerFactory.getLogger(AccountCommissionController.class);

    @Autowired
    FuAccountCommissionService fuAccountCommissionService;


    //获取佣金账户
    @RequestMapping(value= "/getPageAccountCommisson",method=RequestMethod.POST)
    public @ResponseBody
    PageInfo<FuAccountCommission> getPageAccountCommisson(@RequestBody RequestParams<FuAccountCommission> requestParams){
        // 获取请求参数
        FuAccountCommission commission = requestParams.getParams();
        PageInfoHelper helper = requestParams.getPageInfoHelper();
        return fuAccountCommissionService.getPageAccountCommisson(commission,helper);
    }

    //根据ID佣金账户
    @RequestMapping(value= "/getAccountCommissonById",method=RequestMethod.POST)
    public @ResponseBody FuAccountCommission getAccountCommissonById(@RequestBody RequestParams<FuAccountCommission> requestParams){
        // 获取请求参数
        FuAccountCommission commission = requestParams.getParams();
        if(commission==null||commission.getId()==null||commission.getId()==0){
            log.error("根据ID佣金账户,ID为空");
            throw new ParameterInvalidException(GlobalResultCode.PARAM_NULL_POINTER);
        }
        return fuAccountCommissionService.getAccountCommissonById(commission.getId());
    }
    //根据userID佣金账户
    @RequestMapping(value= "/getAccountCommissonByUserId",method=RequestMethod.POST)
    public @ResponseBody FuAccountCommission getAccountCommissonByUserId(@RequestBody RequestParams<FuAccountCommission> requestParams){
        // 获取请求参数
        FuAccountCommission commission = requestParams.getParams();
        if(commission==null||commission.getUserId()==null||commission.getUserId()==0){
            log.error("根据userID佣金账户,userID为空");
            throw new ParameterInvalidException(GlobalResultCode.PARAM_NULL_POINTER);
        }
        return fuAccountCommissionService.getAccountCommissonByUserId(commission.getUserId());
    }

    //根据userID佣金账户
    @RequestMapping(value= "/accountCommissonWithdraw",method=RequestMethod.POST)
    public @ResponseBody boolean accountCommissonWithdraw() {
        // 获取请求参数
        String requestJSONStr = ThreadCache.getPostRequestParams();
        JSONObject requestMap = JSONObject.parseObject(requestJSONStr);
        Integer userId=requestMap.getInteger("userId");
        Integer accountId=requestMap.getInteger("accountId");
        Integer operId=requestMap.getInteger("operId");
        BigDecimal commissionMoney=requestMap.getBigDecimal("commissionMoney");
        fuAccountCommissionService.accountCommissonWithdraw(userId,accountId,operId,commissionMoney);
        return true;
    }
}