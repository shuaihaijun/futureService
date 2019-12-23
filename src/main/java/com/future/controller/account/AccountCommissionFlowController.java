package com.future.controller.account;

import com.future.common.helper.PageInfoHelper;
import com.future.common.result.RequestParams;
import com.future.entity.account.FuAccountCommissionFlow;
import com.future.service.account.FuAccountCommissionFlowService;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/account/CommissionFlow")
public class AccountCommissionFlowController {

    Logger log= LoggerFactory.getLogger(AccountCommissionFlowController.class);

    @Autowired
    FuAccountCommissionFlowService fuAccountCommissionFlowService;


    //获取返佣规则
    @RequestMapping(value= "/getPageCommissonFlow",method=RequestMethod.POST)
    public @ResponseBody
    PageInfo<FuAccountCommissionFlow> getPageCommissonLevel(@RequestBody RequestParams<Map> requestParams){
        // 获取请求参数
        Map condition = requestParams.getParams();
        PageInfoHelper helper = requestParams.getPageInfoHelper();
        return fuAccountCommissionFlowService.findPageFlowByCondition(condition,helper);
    }
}