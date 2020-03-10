package com.future.commission;

import com.future.common.helper.PageInfoHelper;
import com.future.entity.commission.FuCommissionLevel;
import com.future.service.commission.FuCommissionLevelSevice;
import com.future.timing.CommissionOrderMonitor;
import lombok.extern.slf4j.Slf4j;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
@Ignore
public class CommissionTests {

    @Autowired
    CommissionOrderMonitor commissionOrderMonitor;

    @Autowired
    private FuCommissionLevelSevice fuCommissionLevelSevice;

    @Test
    public void testGetHistoryOrder(){

        FuCommissionLevel level=new FuCommissionLevel();
        PageInfoHelper helper=new PageInfoHelper();

        fuCommissionLevelSevice.getPageCommissonLevel(level,helper);
    }

    @Test
    public void testCommissionOrdeMonitor(){
        commissionOrderMonitor.monitor();
    }
}