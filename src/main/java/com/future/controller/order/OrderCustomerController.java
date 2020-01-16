package com.future.controller.order;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.future.common.enums.GlobalResultCode;
import com.future.common.exception.DataConflictException;
import com.future.common.helper.PageInfoHelper;
import com.future.common.result.RequestParams;
import com.future.common.util.DateUtil;
import com.future.common.util.StringUtils;
import com.future.common.util.ThreadCache;
import com.future.entity.order.FuOrderCustomer;
import com.future.entity.order.FuOrderFollowInfo;
import com.future.service.order.FuOrderCustomerService;
import com.future.service.order.FuOrderFollowInfoService;
import com.future.service.user.UserCommonService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/orderCustomer")
public class OrderCustomerController {

    Logger log= LoggerFactory.getLogger(OrderCustomerController.class);

    @Autowired
    FuOrderCustomerService fuOrderCustomerService;
    @Autowired
    UserCommonService userCommonService;
    @Autowired
    FuOrderFollowInfoService fuOrderFollowInfoService;

    //获得Mt历史订单操作
    @RequestMapping(value= "/getOrderCustomer",method=RequestMethod.POST)
    public @ResponseBody
    Page<FuOrderCustomer> getOrderCustomer(@RequestBody RequestParams<Map> requestParams) {
        Map orderCondition = requestParams.getParams();
        PageInfoHelper helper = requestParams.getPageInfoHelper();
        // 获取请求参数
        return fuOrderCustomerService.queryOrderCustomer(orderCondition,helper);
    }

    //同步用户MT已平仓订单
    @RequestMapping(value= "/synUserMTOrder",method=RequestMethod.POST)
    public @ResponseBody void synUserMTOrder(){
        // 获取请求参数
        String requestJSONStr = ThreadCache.getPostRequestParams();
        JSONObject requestMap = JSONObject.parseObject(requestJSONStr);
        String username=requestMap.getString("username");
        if(StringUtils.isEmpty(requestMap.getString("userId"))&&StringUtils.isEmpty(username)){
            log.error("用户数据为空！");
            new DataConflictException(GlobalResultCode.PARAM_NULL_POINTER,"用户数据为空！");
        }
        Integer userId=0;
        if(!ObjectUtils.isEmpty(requestMap.getString("userId"))){
            userId=Integer.valueOf(requestMap.getString("userId"));
        }

        //增量同步用户已平仓数据
        fuOrderCustomerService.synUserMTOrder(userId,username);
    }

    //获得Mt在仓订单操作
    @RequestMapping(value= "/getMTAliveOrders",method=RequestMethod.POST)
    public @ResponseBody
    PageInfo<FuOrderFollowInfo> getMTAliveOrders(@RequestBody RequestParams<Map> requestParams) {
        Map orderCondition = requestParams.getParams();
        PageInfoHelper helper = requestParams.getPageInfoHelper();
        return fuOrderCustomerService.getMTAliveOrders(orderCondition,helper);
    }
}