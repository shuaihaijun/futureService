package com.future.user;


import com.future.entity.user.FuUser;
import com.future.service.mt.MTOrderService;
import com.future.service.user.AdminService;
import com.future.trade.DemoAccount;
import com.future.util.DateUtil;
import com.jfx.SelectionPool;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.DigestUtils;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MtOrderTest {

    @Autowired
    MTOrderService mtOrderService;

    @Test
    public void testGetHistoryOrder(){
        Date dateFrom= DateUtil.toDateTimeFromString("2019-06-10 10:00:00");
        Date dateTo= DateUtil.toDateTimeFromString("2019-06-11 05:00:00");
        mtOrderService.getOrder(DemoAccount.MT_4_SERVER,DemoAccount.MT_4_USER,DemoAccount.MT_4_PASSWORD, SelectionPool.MODE_HISTORY,dateFrom,dateTo);
    }
}
