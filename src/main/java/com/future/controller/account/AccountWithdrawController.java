package com.future.controller.account;

import com.alibaba.fastjson.JSONObject;
import com.future.common.enums.GlobalResultCode;
import com.future.common.exception.ParameterInvalidException;
import com.future.common.helper.PageInfoHelper;
import com.future.common.result.RequestParams;
import com.future.common.util.ThreadCache;
import com.future.entity.account.FuAccountCommission;
import com.future.entity.account.FuAccountWithdrawApply;
import com.future.service.account.FuAccountCommissionService;
import com.future.service.account.FuAccountWithdrawService;
import com.github.pagehelper.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;

@RestController
@RequestMapping("/account/Withdraw")
public class AccountWithdrawController {

    Logger log= LoggerFactory.getLogger(AccountWithdrawController.class);

    @Autowired
    FuAccountWithdrawService fuAccountWithdrawService;


    //佣金账户 佣金提取
    @RequestMapping(value= "/commissonWithdraw",method=RequestMethod.POST)
    public @ResponseBody boolean accountCommissonWithdraw() {
        // 获取请求参数
        String requestJSONStr = ThreadCache.getPostRequestParams();
        JSONObject requestMap = JSONObject.parseObject(requestJSONStr);
        Integer userId=requestMap.getInteger("userId");
        Integer accountId=requestMap.getInteger("accountId");
        Integer operId=requestMap.getInteger("operId");
        BigDecimal commissionMoney=requestMap.getBigDecimal("commissionMoney");
        fuAccountWithdrawService.accountCommissonWithdraw(userId,accountId,operId,commissionMoney);
        return true;
    }

    //佣金账户 佣金提取申请保存
    @RequestMapping(value= "/commissonWithdrawApplySave",method=RequestMethod.POST)
    public @ResponseBody boolean commissonWithdrawApplySave(@RequestBody RequestParams<FuAccountWithdrawApply> requestParams){
        // 获取请求参数
        FuAccountWithdrawApply commission = requestParams.getParams();
        // 获取请求参数
        if(commission==null||commission.getUserId()==null||commission.getUserId()==0){
            log.error("佣金提取申请,userID为空");
            throw new ParameterInvalidException(GlobalResultCode.PARAM_NULL_POINTER);
        }
        fuAccountWithdrawService.commissonWithdrawApplySave(commission);
        return true;
    }

    //佣金账户 佣金提取申请提交
    @RequestMapping(value= "/commissonWithdrawApplySubmit",method=RequestMethod.POST)
    public @ResponseBody boolean commissonWithdrawApplySubmit(@RequestBody RequestParams<FuAccountWithdrawApply> requestParams){
        // 获取请求参数
        FuAccountWithdrawApply commission = requestParams.getParams();
        // 获取请求参数
        if(commission==null||commission.getUserId()==null||commission.getUserId()==0){
            log.error("佣金提取申请,userID为空");
            throw new ParameterInvalidException(GlobalResultCode.PARAM_NULL_POINTER);
        }
        fuAccountWithdrawService.commissonWithdrawApplySubmit(commission);
        return true;
    }

    //佣金账户 佣金提取申请审核
    @RequestMapping(value= "/commissonWithdrawApplyCheck",method=RequestMethod.POST)
    public @ResponseBody boolean commissonWithdrawApplyCheck(@RequestBody RequestParams<Map> requestParams){
        // 获取请求参数
        Map checkMap = requestParams.getParams();
        // 获取请求参数
        if(checkMap==null||checkMap.get("id")==null||checkMap.get("oper")==null){
            log.error("佣金提取申请,参数为空");
            throw new ParameterInvalidException(GlobalResultCode.PARAM_NULL_POINTER);
        }
        fuAccountWithdrawService.commissonWithdrawApplyCheck(checkMap);
        return true;
    }

    //佣金账户 佣金提取申请查询
    @RequestMapping(value= "/commissonWithdrawApplyQuery",method=RequestMethod.POST)
    public @ResponseBody Page<FuAccountWithdrawApply> commissonWithdrawApplyQuery(@RequestBody RequestParams<Map> requestParams){
        // 获取请求参数
        Map queryMap = requestParams.getParams();
        PageInfoHelper helper = requestParams.getPageInfoHelper();
        // 获取请求参数
        if(queryMap==null||queryMap.get("operUserId")==null){
            log.error("佣金提取申请,参数为空");
            throw new ParameterInvalidException(GlobalResultCode.PARAM_NULL_POINTER);
        }
        return fuAccountWithdrawService.commissonWithdrawApplyQuery(queryMap,helper);
    }
}