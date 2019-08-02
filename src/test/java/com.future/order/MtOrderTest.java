package com.future.order;


import com.future.TestDemoAccount;
import com.future.common.util.DateUtil;
import com.future.entity.order.FuOrderInfo;
import com.future.service.mt.MTOrderService;
import com.future.service.order.FuOrderInfoService;
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
    MTOrderService mtOrderService;
    @Autowired
    FuOrderInfoService fuOrderInfoService;

    @Test
    public void testGetHistoryOrder(){
        Date dateFrom= DateUtil.toDateTimeFromString("2019-06-10 10:00:00");
        Date dateTo= DateUtil.toDateTimeFromString("2019-06-11 05:00:00");
        mtOrderService.getOrders(TestDemoAccount.MT_4_SERVER,TestDemoAccount.MT_4_USER,TestDemoAccount.MT_4_PASSWORD, SelectionPool.MODE_HISTORY,dateFrom,dateTo,null);
    }

    @Test
    public void getAliveOrders(){
        String username="admin";
        String accountId="";
        Date dateFrom= DateUtil.toDateTimeFromString("2019-06-10 10:00:00");
        Date dateTo= DateUtil.toDateTimeFromString("2019-07-15 05:00:00");
        System.out.println("begin========"+new Date().getTime());
        List<FuOrderInfo> data= fuOrderInfoService.geMTtHistoryOrders(null,username,accountId,dateFrom,dateTo,null);
        System.out.println("end  ========"+new Date().getTime());
    }
}
