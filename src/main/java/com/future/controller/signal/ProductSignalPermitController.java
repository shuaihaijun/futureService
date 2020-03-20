package com.future.controller.signal;

import com.future.common.helper.PageInfoHelper;
import com.future.common.result.RequestParams;
import com.future.entity.product.FuProductSignalPermit;
import com.future.service.product.FuProductSignalPermitService;
import com.github.pagehelper.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/signal/signalPermit")
public class ProductSignalPermitController {
    Logger log= LoggerFactory.getLogger(ProductSignalPermitController.class);

    @Autowired
    FuProductSignalPermitService fuProductSignalPermitService;

    //查询信息
    @RequestMapping(value= "/querySignalPermit",method= RequestMethod.POST)
    public @ResponseBody
    Page<FuProductSignalPermit> querySignalPermit(@RequestBody RequestParams<Map> requestParams) {
        // 获取请求参数
        Map conditionMap = requestParams.getParams();
        PageInfoHelper helper = requestParams.getPageInfoHelper();
        /*校验参数*/
        return fuProductSignalPermitService.querySignalPermit(conditionMap,helper);
    }

    //保存信息
    @RequestMapping(value= "/saveSignalPermit",method= RequestMethod.POST)
    public @ResponseBody boolean savePermit(@RequestBody RequestParams<Map> requestParams) {
        // 获取请求参数
        Map dataMap = requestParams.getParams();
        /*校验参数*/
        fuProductSignalPermitService.savePermit(dataMap);
        return true;
    }
}