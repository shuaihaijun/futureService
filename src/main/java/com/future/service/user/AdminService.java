package com.future.service.user;

import com.alibaba.druid.util.StringUtils;
import com.future.entity.user.FuUser;
import com.future.mapper.user.FuUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.HashMap;
import java.util.Map;

@Service
public class AdminService {

    @Autowired
    FuUserMapper fuUserMapper;

    /**
     * 通过用户名密码登录
     * @param username
     * @param password
     * @return
     */
    public Map login(String username,String password){
        Map result=new HashMap();

        if(StringUtils.isEmpty(username)||StringUtils.isEmpty(password)){
           result.put("-1","用户名或密码为空！");
           return result;
        }

        FuUser fuUser=fuUserMapper.selectByUsername(username);
        if(ObjectUtils.isEmpty(fuUser)){
           result.put("-1","用户不存在！");
           return result;
        }

        if(!fuUser.getPassword().equalsIgnoreCase(password)){
            result.put("-1","用户名或密码错误！");
            return result;
        }

        result.put("0","用户名、密码验证通过！");

        return result;
    }
}
