package com.future.service.user;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.future.entity.com.FuComAgent;
import com.future.mapper.com.FuComAgentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Arrays;


@Service
public class UserCommonService extends ServiceImpl<FuComAgentMapper,FuComAgent> {

    Logger log= LoggerFactory.getLogger(UserCommonService.class);

    @Value("${SUPER_ADMINISTRATOR}")
    private String superAdministrator;

    /**
     * 判断用户是否是管理员
     * @param userId
     */
    public boolean isAdministrator(String userId){
        //获取配置文件中超级管理员，判断当前登录用户是否为超级管理员,true为超管
        String[] superAdministrators = superAdministrator.split(",");
        boolean contains = false;
        if (userId != null && superAdministrators != null) {
            contains = Arrays.asList(superAdministrators).contains(userId);
        }
        return contains;
    }

}
