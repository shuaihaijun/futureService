package com.future.service.user;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.future.common.util.RedisManager;
import com.future.entity.com.FuComAgent;
import com.future.mapper.com.FuComAgentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.xml.ws.soap.Addressing;
import java.util.Arrays;


@Service
public class UserCommonService extends ServiceImpl<FuComAgentMapper,FuComAgent> {

    Logger log= LoggerFactory.getLogger(UserCommonService.class);

    @Value("${SUPER_ADMINISTRATOR}")
    private String superAdministrator;
    @Value("${PROJECT_ADMINISTRATOR}")
    private String projectAdministrator;
    @Autowired
    RedisManager redisManager;

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

    /**
     * 判断是否是管理员
     * @param userId
     * @param projKey
     * @return
     */
    public boolean isProjectAdministrator(Integer userId,Integer projKey){
        //获取配置文件中超级管理员，判断当前登录用户是否为超级管理员,true为超管
        String[] superAdministrators = projectAdministrator.split(",");
        boolean contains = false;
        if (userId != null && superAdministrators != null) {
            contains = Arrays.asList(superAdministrators).contains(userId.toString());
        }
        return contains;
    }

}
