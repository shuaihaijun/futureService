package com.future.controller.order;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.future.common.enums.GlobalResultCode;
import com.future.common.exception.DataConflictException;
import com.future.common.util.StringUtils;
import com.future.common.util.ThreadCache;
import com.future.entity.order.FuOrderSignal;
import com.future.service.order.FuOrderSignalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orderSignal")
public class OrderSignalController {

    Logger log= LoggerFactory.getLogger(OrderSignalController.class);

    @Autowired
    FuOrderSignalService fuOrderSignalService;

    //获得信号源订单操作
    @RequestMapping(value= "/getOrderSignal",method=RequestMethod.POST)
    public @ResponseBody
    Page<FuOrderSignal> getOrderSignal(){
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
        EntityWrapper<FuOrderSignal> wrapper=new EntityWrapper<FuOrderSignal>();
        if(!StringUtils.isEmpty(requestMap.getString("userId"))){
            wrapper.eq(FuOrderSignal.USER_ID,requestMap.getString("userId"));
        }
        if(!StringUtils.isEmpty(requestMap.getString("orderId"))){
            wrapper.eq(FuOrderSignal.ORDER_ID,requestMap.getString("orderId"));
        }
        if(!StringUtils.isEmpty(requestMap.getString("orderSymbol"))){
            wrapper.eq(FuOrderSignal.ORDER_SYMBOL,requestMap.getString("orderSymbol"));
        }
        if(!StringUtils.isEmpty(requestMap.getString("orderType"))){
            wrapper.eq(FuOrderSignal.ORDER_TYPE,requestMap.getString("orderType"));
        }
        if(!StringUtils.isEmpty(requestMap.getString("orderOpenDate"))){
            if(requestMap.getString("orderOpenDate").indexOf(",")<0){
                wrapper.eq(FuOrderSignal.ORDER_OPEN_DATE,requestMap.getString("orderOpenDate"));
            }else {
                //时间段
                JSONArray dateOpen=requestMap.getJSONArray("orderOpenDate");
                if(dateOpen.size()!=2){
                    log.error("建仓时间段数据传入错误！"+requestMap.getString("orderOpenDate"));
                    throw new DataConflictException(GlobalResultCode.PARAM_VERIFY_ERROR,"建仓时间段数据传入错误！"+requestMap.getString("orderOpenDate"));
                }
                wrapper.gt(FuOrderSignal.ORDER_OPEN_DATE,dateOpen.getString(0));
                wrapper.lt(FuOrderSignal.ORDER_OPEN_DATE,dateOpen.getString(1));
            }
        }
        if(!StringUtils.isEmpty(requestMap.getString("orderCloseDate"))){
            if(requestMap.getString("orderCloseDate").indexOf(",")<0){
                wrapper.eq(FuOrderSignal.ORDER_OPEN_DATE,requestMap.getString("orderCloseDate"));
            }else {
                //时间段
                JSONArray dateClose=requestMap.getJSONArray("orderCloseDate");
                if(dateClose.size()!=2){
                    log.error("平仓时间段数据传入错误！"+requestMap.getString("orderCloseDate"));
                    throw new DataConflictException(GlobalResultCode.PARAM_VERIFY_ERROR,"平仓时间段数据传入错误！"+requestMap.getString("orderCloseDate"));
                }
                wrapper.gt(FuOrderSignal.ORDER_CLOSE_DATE,dateClose.getString(0));
                wrapper.lt(FuOrderSignal.ORDER_CLOSE_DATE,dateClose.getString(1));
            }
        }
        return fuOrderSignalService.selectPage(page, wrapper);
    }

}