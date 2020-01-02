package com.future.controller.order;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.future.common.enums.GlobalResultCode;
import com.future.common.exception.DataConflictException;
import com.future.common.util.StringUtils;
import com.future.common.util.ThreadCache;
import com.future.entity.order.FuOrderCustomer;
import com.future.service.order.FuOrderCustomerService;
import com.future.service.user.UserCommonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orderCustomer")
public class OrderCustomerController {

    Logger log= LoggerFactory.getLogger(OrderCustomerController.class);

    @Autowired
    FuOrderCustomerService fuOrderCustomerService;
    @Autowired
    UserCommonService userCommonService;

    //获得Mt历史订单操作
    @RequestMapping(value= "/getOrderCustomer",method=RequestMethod.POST)
    public @ResponseBody
    Page<FuOrderCustomer> getOrderCustomer(){
        // 获取请求参数
        String requestJSONStr = ThreadCache.getPostRequestParams();
        JSONObject requestMap = JSONObject.parseObject(requestJSONStr);

        int pageSize=20;
        int pageNum=1;

        if(StringUtils.isEmpty(pageSize)||StringUtils.isEmpty(pageNum)){
            log.error("分页数据为空！");
            new DataConflictException("分页数据为空！");
        }
        if(!StringUtils.isEmpty(requestMap.getString("pageSize"))){
            pageSize=Integer.parseInt(requestMap.getString("pageSize"));
        }
        if(!StringUtils.isEmpty(requestMap.getString("pageNum"))){
            pageNum=Integer.parseInt(requestMap.getString("pageNum"));
        }
        Page page=new Page();
        page.setSize(pageSize);
        page.setCurrent(pageNum);
        EntityWrapper<FuOrderCustomer> wrapper=new EntityWrapper<FuOrderCustomer>();

        String userId="";
        if(!StringUtils.isEmpty(requestMap.getString("operUserId"))){
            if(!userCommonService.isAdministrator(String.valueOf(requestMap.getString("operUserId")))){
                // 不是管理员管理员
                wrapper.eq(FuOrderCustomer.USER_ID,requestMap.getString("operUserId"));
            }
        }else if(!StringUtils.isEmpty(requestMap.getString("userId"))){
            wrapper.eq(FuOrderCustomer.USER_ID,requestMap.getString("userId"));
        }
        if(!StringUtils.isEmpty(requestMap.getString("orderId"))){
            wrapper.eq(FuOrderCustomer.ORDER_ID,requestMap.getString("orderId"));
        }
        if(!StringUtils.isEmpty(requestMap.getString("orderSymbol"))){
            wrapper.eq(FuOrderCustomer.ORDER_SYMBOL,requestMap.getString("orderSymbol"));
        }
        if(!StringUtils.isEmpty(requestMap.getString("orderType"))){
            wrapper.eq(FuOrderCustomer.ORDER_TYPE,requestMap.getString("orderType"));
        }
        if(!StringUtils.isEmpty(requestMap.getString("orderOpenDate"))){
            if(requestMap.getString("orderOpenDate").indexOf(",")<0){
                wrapper.eq(FuOrderCustomer.ORDER_OPEN_DATE,requestMap.getString("orderOpenDate"));
            }else {
                //时间段
                JSONArray dateOpen=requestMap.getJSONArray("orderOpenDate");
                if(dateOpen.size()!=2){
                    log.error("建仓时间段数据传入错误！"+requestMap.getString("orderOpenDate"));
                    throw new DataConflictException(GlobalResultCode.PARAM_VERIFY_ERROR,"建仓时间段数据传入错误！"+requestMap.getString("orderOpenDate"));
                }
                wrapper.gt(FuOrderCustomer.ORDER_OPEN_DATE,dateOpen.getString(0));
                wrapper.lt(FuOrderCustomer.ORDER_OPEN_DATE,dateOpen.getString(1));
            }
        }
        if(!StringUtils.isEmpty(requestMap.getString("orderCloseDate"))){
            if(requestMap.getString("orderCloseDate").indexOf(",")<0){
                wrapper.eq(FuOrderCustomer.ORDER_OPEN_DATE,requestMap.getString("orderCloseDate"));
            }else {
                //时间段
                JSONArray dateClose=requestMap.getJSONArray("orderCloseDate");
                if(dateClose.size()!=2){
                    log.error("平仓时间段数据传入错误！"+requestMap.getString("orderCloseDate"));
                    throw new DataConflictException(GlobalResultCode.PARAM_VERIFY_ERROR,"平仓时间段数据传入错误！"+requestMap.getString("orderCloseDate"));
                }
                wrapper.gt(FuOrderCustomer.ORDER_CLOSE_DATE,dateClose.getString(0));
                wrapper.lt(FuOrderCustomer.ORDER_CLOSE_DATE,dateClose.getString(1));
            }
        }
        return fuOrderCustomerService.selectPage(page, wrapper);
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
}