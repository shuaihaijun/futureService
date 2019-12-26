package com.future.controller.commission;

import com.future.common.helper.PageInfoHelper;
import com.future.common.result.RequestParams;
import com.future.entity.commission.FuCommissionCustomer;
import com.future.entity.commission.FuCommissionLevel;
import com.future.service.commission.FuCommissionCustomerService;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.soap.Addressing;

/**
 * 用户订单佣金业务逻辑
 */
@RestController
@RequestMapping("/commission/commissionCustomer")
public class CommissionCustomerController{

    Logger log=LoggerFactory.getLogger(CommissionCustomerController.class);
    @Autowired
    FuCommissionCustomerService fuCommissionCustomerService;


    @RequestMapping(value= "/findCommissionByCondition",method= RequestMethod.POST)
    public @ResponseBody
    PageInfo<FuCommissionCustomer> findCommissionByCondition(@RequestBody RequestParams<FuCommissionCustomer> requestParams){
        // 获取请求参数
        FuCommissionCustomer commissionCustomer = requestParams.getParams();
        PageInfoHelper helper = requestParams.getPageInfoHelper();
        return fuCommissionCustomerService.findCommissionByCondition(commissionCustomer,helper);
    }
}
