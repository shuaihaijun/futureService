package com.future.user;


import com.future.entity.user.FuUser;
import com.future.service.account.FuAccountMtService;
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

import java.util.*;

@Ignore
@RunWith(SpringRunner.class)
@SpringBootTest
public class AdminTest {

    @Autowired
    AdminService adminService;
    @Autowired
    FuAccountMtService fuAccountMtService;
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
//        List<UserMTAccountBO> accts=fuAccountMtSevice.getUserMTAccByCondition(para);
        List<Map> maps=new ArrayList<Map>();

        Map map1 = new HashMap();	map1.put("mtAccId","2102261700");	map1.put("mtPassword","jxg3vvd");	maps.add(map1);
        Map map2 = new HashMap();	map2.put("mtAccId","2102261701");	map2.put("mtPassword","hvg4ozp");	maps.add(map2);
        Map map3 = new HashMap();	map3.put("mtAccId","2102261702");	map3.put("mtPassword","5wapgit");	maps.add(map3);
        Map map4 = new HashMap();	map4.put("mtAccId","2102261703");	map4.put("mtPassword","8oufysq");	maps.add(map4);
        Map map5 = new HashMap();	map5.put("mtAccId","2102261704");	map5.put("mtPassword","kmnv1tp");	maps.add(map5);
        Map map6 = new HashMap();	map6.put("mtAccId","2102261705");	map6.put("mtPassword","bkp4nug");	maps.add(map6);
        Map map7 = new HashMap();	map7.put("mtAccId","2102261706");	map7.put("mtPassword","og8yknt");	maps.add(map7);
        Map map8 = new HashMap();	map8.put("mtAccId","2102261707");	map8.put("mtPassword","od7synh");	maps.add(map8);
        Map map9 = new HashMap();	map9.put("mtAccId","2102261708");	map9.put("mtPassword","ffzz1bj");	maps.add(map9);
        Map map10 = new HashMap();	map10.put("mtAccId","2102261709");	map10.put("mtPassword","zftp3kf");	maps.add(map10);
        Map map11 = new HashMap();	map11.put("mtAccId","2102261710");	map11.put("mtPassword","w4xyzcp");	maps.add(map11);
        Map map12 = new HashMap();	map12.put("mtAccId","2102261711");	map12.put("mtPassword","7qzoydg");	maps.add(map12);
        Map map13 = new HashMap();	map13.put("mtAccId","2102261712");	map13.put("mtPassword","t4fjagj");	maps.add(map13);
        Map map14 = new HashMap();	map14.put("mtAccId","2102261713");	map14.put("mtPassword","mqr6spf");	maps.add(map14);
        Map map15 = new HashMap();	map15.put("mtAccId","2102261714");	map15.put("mtPassword","j6vieoo");	maps.add(map15);
        Map map16 = new HashMap();	map16.put("mtAccId","2102261715");	map16.put("mtPassword","xp1nbib");	maps.add(map16);
        Map map17 = new HashMap();	map17.put("mtAccId","2102261716");	map17.put("mtPassword","h7cafti");	maps.add(map17);
        Map map18 = new HashMap();	map18.put("mtAccId","2102261717");	map18.put("mtPassword","un2ycmn");	maps.add(map18);
        Map map19 = new HashMap();	map19.put("mtAccId","2102261718");	map19.put("mtPassword","nffo3vr");	maps.add(map19);
        Map map20 = new HashMap();	map20.put("mtAccId","2102261719");	map20.put("mtPassword","4oqzsdz");	maps.add(map20);
        Map map21 = new HashMap();	map21.put("mtAccId","2102261720");	map21.put("mtPassword","m3nsfco");	maps.add(map21);
        Map map22 = new HashMap();	map22.put("mtAccId","2102261721");	map22.put("mtPassword","t0tnhfq");	maps.add(map22);
        Map map23 = new HashMap();	map23.put("mtAccId","2102261723");	map23.put("mtPassword","5dcwkke");	maps.add(map23);
        Map map24 = new HashMap();	map24.put("mtAccId","2102261724");	map24.put("mtPassword","e2sjfve");	maps.add(map24);
        Map map25 = new HashMap();	map25.put("mtAccId","2102261725");	map25.put("mtPassword","5duhexv");	maps.add(map25);
        Map map26 = new HashMap();	map26.put("mtAccId","2102261726");	map26.put("mtPassword","pdg5qsz");	maps.add(map26);
        Map map27 = new HashMap();	map27.put("mtAccId","2102261727");	map27.put("mtPassword","vdlv3vt");	maps.add(map27);
        Map map28 = new HashMap();	map28.put("mtAccId","2102261728");	map28.put("mtPassword","z2zftqj");	maps.add(map28);
        Map map29 = new HashMap();	map29.put("mtAccId","2102261729");	map29.put("mtPassword","fq8zute");	maps.add(map29);
        Map map30 = new HashMap();	map30.put("mtAccId","2102261730");	map30.put("mtPassword","n4zyeim");	maps.add(map30);
        Map map31 = new HashMap();	map31.put("mtAccId","2102261731");	map31.put("mtPassword","0pxlatt");	maps.add(map31);
        Map map32 = new HashMap();	map32.put("mtAccId","2102261732");	map32.put("mtPassword","aul8inj");	maps.add(map32);
        Map map33 = new HashMap();	map33.put("mtAccId","2102261733");	map33.put("mtPassword","qs2lypb");	maps.add(map33);
        Map map34 = new HashMap();	map34.put("mtAccId","2102261734");	map34.put("mtPassword","h5ojxys");	maps.add(map34);
        Map map35 = new HashMap();	map35.put("mtAccId","2102261735");	map35.put("mtPassword","oq2dzbv");	maps.add(map35);
        Map map36 = new HashMap();	map36.put("mtAccId","2102261736");	map36.put("mtPassword","ysk7vmu");	maps.add(map36);
        Map map37 = new HashMap();	map37.put("mtAccId","2102261737");	map37.put("mtPassword","oq1gtnm");	maps.add(map37);
        Map map38 = new HashMap();	map38.put("mtAccId","2102261738");	map38.put("mtPassword","2cgysax");	maps.add(map38);
        String server="";
        String mtAccId="";
        String mtPassword="";
        int i=0;
        for(Map map:maps){
            server="AETOS-Demo F";
            mtAccId=String.valueOf(map.get("mtAccId"));
            mtPassword=String.valueOf(map.get("mtPassword"));
            Broker broker=new Broker(server);
            if(server.equalsIgnoreCase("AETOS-Demo F")){
                System.out.println("----------------start:"+i+", time"+new Date().getTime());
                System.out.println("-----------serverName:"+server+", mtAccId:"+mtAccId+", mtPassword:"+mtPassword);
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
        }
    }
}
