//package com.future.user;
//
//
//import com.future.entity.user.FuUser;
//import com.future.service.user.AdminService;
//import org.junit.Ignore;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.util.DigestUtils;
//
//import java.util.Map;
//
//@Ignore
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class AdminTest {
//
//    @Autowired
//    AdminService adminService;
//
//    @Test
//    public void testAdminLogin(){
//        String username="";
//        String password="";
//        /*Map map=adminService.login(username,password);
//        System.out.println(map.get("0"));
//        System.out.println(map.get("-1"));*/
//    }
//
//    @Test
//    public void AdminSave(){
//        FuUser fuUser=new FuUser();
//        fuUser.setUsername("admin");
//        fuUser.setPassword(DigestUtils.md5DigestAsHex("123".getBytes()));
//        fuUser.setEmail("123@qwe.com");
//        fuUser.setRefName("shuai8");
//        fuUser.setUserState(1);
//
//        /*Map map=adminService.save(fuUser);
//        System.out.println(map.get("0"));
//        System.out.println(map.get("-1"));*/
//    }
//
//    @Test
//    public void findUser(){
//        FuUser fuUser=adminService.findByUsername("admin");
//    }
//}
