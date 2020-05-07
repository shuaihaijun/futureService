package com.future.order;


import com.alibaba.fastjson.JSONArray;
import com.future.TestDemoAccount;
import com.future.common.util.DateUtil;
import com.future.entity.order.FuOrderFollowInfo;
import com.future.service.bak.BakMTOrderService;
import com.future.service.order.FuOrderFollowInfoService;
import com.future.service.trade.FuTradeOrderService;
import com.jfx.SelectionPool;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

@Ignore
@RunWith(SpringRunner.class)
@SpringBootTest
public class MtOrderTest {

    @Autowired
    BakMTOrderService bakMtOrderService;
    @Autowired
    FuOrderFollowInfoService fuOrderInfoService;
    @Autowired
    FuTradeOrderService fuTradeOrderService;

    @Test
    public void testGetHistoryOrder(){
        Date dateFrom= DateUtil.toDateTimeFromString("2019-06-10 10:00:00");
        Date dateTo= DateUtil.toDateTimeFromString("2022-06-11 05:00:00");
        bakMtOrderService.getOrders(TestDemoAccount.MT_4_SERVER,TestDemoAccount.MT_4_USER,TestDemoAccount.MT_4_PASSWORD, SelectionPool.MODE_HISTORY,dateFrom,dateTo,null);
    }

    @Test
    public void getAliveOrders(){
        String username="admin";
        String accountId="";
        Date dateFrom= DateUtil.toDateTimeFromString("2019-06-10 10:00:00");
        Date dateTo= DateUtil.toDateTimeFromString("2019-07-15 05:00:00");
        System.out.println("begin========"+new Date().getTime());
        List<FuOrderFollowInfo> data= fuOrderInfoService.geMTtHistoryOrders(null,username,accountId,dateFrom,dateTo);
        System.out.println("end  ========"+new Date().getTime());
    }

    @Test
    public void testTime(){
        int nThreadHisTimeFrom =(int)((new Date().getTime()-((long)(30 * 24) * 3600000))/1000);
        int nThreadHisTimeTo =(int)(new Date().getTime()/1000);
        System.out.println(nThreadHisTimeFrom);
        System.out.println(nThreadHisTimeTo);
    }

    @Test
    public void getUserCloseOrders(){
        JSONArray orders= fuTradeOrderService.getUserCloseOrders("server",123,"password",1583574140,0);
        System.out.println(orders.toString());
    }
}
