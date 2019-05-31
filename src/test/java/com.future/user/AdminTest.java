package com.future.user;


import com.future.service.user.AdminService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AdminTest {

    @Autowired
    AdminService adminService;

    @Test
    public void testAdminLogin(){
        String username="";
        String password="";
        Map map=adminService.login(username,password);
        System.out.println(map.get("0"));
        System.out.println(map.get("-1"));
    }
}
