package com.future.service.order;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.future.common.enums.GlobalResultCode;
import com.future.common.exception.ParameterInvalidException;
import com.future.common.helper.PageInfoHelper;
import com.future.entity.order.FuOrderDaysum;
import com.future.entity.product.FuProductSignal;
import com.future.mapper.order.FuOrderDaysumMapper;
import com.future.service.product.FuProductSignalService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class FuOrderDaysumService extends ServiceImpl<FuOrderDaysumMapper, FuOrderDaysum>
{
    Logger log=LoggerFactory.getLogger(FuOrderDaysumService.class);
    @Autowired
    FuProductSignalService fuProductSignalService;


    /**
     * 查询用户订单价交易日结
     * @param condition
     * @return
     */
    public PageInfo getOrderDaySum(Map condition){
        if(condition==null){
            log.error("参数为空！");
            throw  new ParameterInvalidException(GlobalResultCode.PARAM_NULL_POINTER);
        }

        Wrapper<FuOrderDaysum> wrapper=new EntityWrapper<FuOrderDaysum>();
        if(condition.get("userId")!=null){
            wrapper.andNew().eq(FuOrderDaysum.USER_ID,condition.get("userId"));
        }
        if(condition.get("userType")!=null){
            wrapper.andNew().eq(FuOrderDaysum.USER_TYPE,condition.get("userType"));
        }
        if(condition.get("otherId")!=null){
            wrapper.andNew().eq(FuOrderDaysum.OTHER_ID,condition.get("otherId"));
        }
        if(condition.get("dateBegin")!=null){
            wrapper.andNew().ge(FuOrderDaysum.TRADE_DATE,condition.get("dateBegin"));
        }
        if(condition.get("dateEnd")!=null){
            wrapper.andNew().le(FuOrderDaysum.TRADE_DATE,condition.get("dateEnd"));
        }

        PageInfoHelper infoHelper=new PageInfoHelper();
        if(condition.get("pageNo")!=null){
            infoHelper.setPageNo(Integer.parseInt(String.valueOf(condition.get("pageNo"))));
        }
        if(condition.get("pageSize")!=null){
            infoHelper.setPageSize(Integer.parseInt(String.valueOf(condition.get("pageSize"))));
        }

        PageHelper.startPage(infoHelper.getPageNo(),infoHelper.getPageSize());
        List<FuOrderDaysum> daysumList=selectList(wrapper);
        return new PageInfo(daysumList);

    }

    /**
     * 查询信号源交易订单日结
     * @param condition
     * @return
     */
    public PageInfo getSignalOrderDaySum(Map condition){
        if(condition==null){
            log.error("参数为空！");
            throw  new ParameterInvalidException(GlobalResultCode.PARAM_NULL_POINTER);
        }
        if(condition.get("signalId")!=null){
            String signalId=String.valueOf(condition.get("signalId"));
            FuProductSignal signal=  fuProductSignalService.selectById(signalId);
            condition.put("userId",signal.getUserId());
            condition.remove("signalId");
        }
        return getOrderDaySum(condition);
    }
}
