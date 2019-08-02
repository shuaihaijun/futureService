package com.future.user;


import com.future.entity.user.FuUser;
import com.future.pojo.bo.order.UserMTAccountBO;
import com.future.service.account.FuAccountMtSevice;
import com.future.service.mt.MTAccountService;
import com.future.service.user.AdminService;
import com.future.trade.OrderSendExample;
import com.jfx.Broker;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Ignore
@RunWith(SpringRunner.class)
@SpringBootTest
public class AdminTest {

    @Autowired
    AdminService adminService;
    @Autowired
    FuAccountMtSevice fuAccountMtSevice;
    @Autowired
    MTAccountService mtAccountService;

    @Test
    public void testAdminLogin(){
        String username="";
        String password="";
        /*Map map=adminService.login(username,password);
        System.out.println(map.get("0"));
        System.out.println(map.get("-1"));*/
    }

    @Test
    public void AdminSave(){
        FuUser fuUser=new FuUser();
        fuUser.setUsername("admin");
        fuUser.setPassword(DigestUtils.md5DigestAsHex("123".getBytes()));
        fuUser.setEmail("123@qwe.com");
        fuUser.setRefName("shuai8");
        fuUser.setUserState(1);

        /*Map map=adminService.save(fuUser);
        System.out.println(map.get("0"));
        System.out.println(map.get("-1"));*/
    }

    @Test
    public void findUser(){
        FuUser fuUser=adminService.findByUsername("admin");
    }


    @Test
    public void testMaxTerminal() throws Exception{
        Map para=new HashMap();
        para.put("isChief",1);
        List<UserMTAccountBO> accts=fuAccountMtSevice.getUserMTAccByCondition(para);

        String server="";
        String mtAccId="";
        String mtPassword="";
        String accountId="";
        int i=0;
        for(UserMTAccountBO acct:accts){
            accountId=String.valueOf(acct.getAccountId());
            server=String.valueOf(acct.getServerName());
            mtAccId=String.valueOf(acct.getMtAccId());
            mtPassword=String.valueOf(acct.getMtPasswordTrade());
            Broker broker=new Broker(server);
            if(server.equalsIgnoreCase("AETOS-Demo F")){
                System.out.println("----------------start:"+i+", time"+new Date().getTime());
                System.out.println("-----------accountId:"+accountId+",serverName:"+server+", mtAccId:"+mtAccId+", mtPassword:"+mtPassword);
                /*mtAccountService.getConnect(strategy,broker,mtAccId,mtPassword);
                System.out.println("----------------connected:"+i+", time"+new Date().getTime());*/
                try{
                    OrderSendExample orderSendExample = new OrderSendExample();
                    System.out.println("========connect begin");
                    orderSendExample.setData(broker,mtAccId,mtPassword);
                    orderSendExample.init();
                    System.out.println("========connected orderSend begin");
                    orderSendExample.newPosition("EURUSD", "B", 0.01);
                    System.out.println("========orderSended well done!");
                }catch (Exception e){
                    System.out.println("errorrrrrrr:"+e.getMessage());
                    e.printStackTrace();
                }
                i++;
            }

           /* Runnable task1 = new Runnable() {
                @Override
                public void run() {
                    try {
                        System.out.println("----------------start:"+i+", time"+new Date().getTime());
                        System.out.println("-----------accountId:"+accountId+",serverName:"+server+", mtAccId:"+mtAccId+", mtPassword:"+mtPassword);
                        mtAccountService.getConnect(strategy,broker,mtAccId,mtPassword);
                        System.out.println("----------------connected:"+i+", time"+new Date().getTime());
                    } catch (MT4Exception e) {
                        System.out.println("Time: " + System.currentTimeMillis() + "> Order sending error: " + e);
                    }
                }
            };*/
        }
    }
}
