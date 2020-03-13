package com.future.follow;


import com.future.common.constants.AccountConstant;
import com.future.common.util.DateUtil;
import com.future.entity.user.FuUserFollows;
import com.future.service.account.FuAccountMtService;
import com.future.service.user.FuUserFollowsService;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
@Ignore
public class followTest {

    @Autowired
    FuAccountMtService fuAccountMtService;
    @Autowired
    FuUserFollowsService fuUserFollowsService;

    @Test
    public void testFollow(){

        int signalId=2;

        int userId=58;
        String userMtAccId="1100551517";
        String username="test22";
        String userMtServerName="USGFX-Demo";

        boolean signalInit= fuAccountMtService.connectSignalMTAccount(signalId);
        if(signalInit){
            System.out.println("signalInit sucess!");
        }
        boolean followInit= fuAccountMtService.connectUserMTAccount(userId,userMtAccId,username,userMtServerName);
        if(followInit){
            System.out.println("followInit sucess!");
        }

        Map follows=new HashMap();
        follows.put("userId",userId);
        follows.put("mtAccId",userMtAccId);
        follows.put("signalId",signalId);
        follows.put("followDirect",1);
        follows.put("followType",0);
        follows.put("followAmount",0.5);
        fuUserFollowsService.signalFollowsSaveOrUpdate(follows);
        System.out.println("follow relation set sucess!");
    }

    @Test
    public void testFollowMonitor(){
        long time=1583899522;
        Date timeDate=DateUtil.toDataFormTimeStamp(time*1000);
        System.out.println(timeDate);
        System.out.println(DateUtil.toDatetimeString(timeDate));
    }

    @Test
    public void testFollowRule(){
        Map conditionMap=new HashMap();
        conditionMap.put("userId",58);
        conditionMap.put("signalMtAccId",1100551517);
        conditionMap.put("signalId",2);
        conditionMap.put("followState", AccountConstant.ACCOUNT_STATE_NORMAL);
        List<FuUserFollows> fuUserFollows= fuUserFollowsService.signalFollowsQuery(conditionMap);
        System.out.println(fuUserFollows.size());
    }

}
