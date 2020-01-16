package com.future.service.user;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.future.common.constants.AccountConstant;
import com.future.common.constants.CommonConstant;
import com.future.common.constants.GlobalConstant;
import com.future.common.constants.RedisConstant;
import com.future.common.util.RedisManager;
import com.future.entity.com.FuComAgent;
import com.future.entity.permission.FuPermissionAdmin;
import com.future.mapper.com.FuComAgentMapper;
import com.future.mapper.permission.FuPermissionAdminMapper;
import com.future.mapper.permission.FuPermissionUserProjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.xml.ws.soap.Addressing;
import java.util.Arrays;
import java.util.List;


@Service
public class UserCommonService extends ServiceImpl<FuComAgentMapper,FuComAgent> {

    Logger log= LoggerFactory.getLogger(UserCommonService.class);
    @Autowired
    FuPermissionAdminMapper fuPermissionAdminMapper;
    @Autowired
    FuPermissionUserProjectMapper fuPermissionUserProjectMapper;
    @Autowired
    RedisManager redisManager;

    @Value("${SUPER_ADMINISTRATOR}")
    private String superAdministrator;
    @Value("${PROJECT_ADMINISTRATOR}")
    private String projectAdministrator;

    /**
     * 判断用户是否是管理员
     * @param userId
     */
    public boolean isAdministrator(Integer userId){
        if(userId==null||userId==0){
            return false;
        }
        //获取配置文件中超级管理员，判断当前登录用户是否为超级管理员,true为超管
        String addminId=userId.toString();
        String[] superAdministrators = superAdministrator.split(",");
        boolean contains = false;
        if (superAdministrators != null) {
            contains = Arrays.asList(superAdministrators).contains(addminId);
        }
        if(!contains){
            //获取配置文件中超级管理员，判断当前登录用户是否为超级管理员,true为超管
            superAdministrators = projectAdministrator.split(",");
            if (superAdministrators != null) {
                contains = Arrays.asList(superAdministrators).contains(addminId);
            }
        }
        return contains;
    }

    /**
     * 判断用户是否是管理员
     * @param userId
     */
    public boolean isSuperAdministrator(Integer userId){
        if(userId==null||userId==0){
            return false;
        }
        //获取配置文件中超级管理员，判断当前登录用户是否为超级管理员,true为超管
        String addminId=userId.toString();
        String[] superAdministrators = superAdministrator.split(",");
        boolean contains = false;
        if (superAdministrators != null) {
            contains = Arrays.asList(superAdministrators).contains(addminId);
        }
        return contains;
    }

    /**
     * 判断用户是否是项目组管理员
     * @param userId
     */
    public boolean isAdministrator(Integer userId,Integer projKey){
        if(userId==null || userId==0){
            return false;
        }
        if(projKey==null){
            projKey=0;
        }
        //获取配置文件中超级管理员，判断当前登录用户是否为超级管理员,true为超管
        FuPermissionAdmin admin=new FuPermissionAdmin();
        admin.setUserId(userId);
        admin.setProjKey(projKey);
        FuPermissionAdmin projAdmin=fuPermissionAdminMapper.selectOne(admin);
        if(projAdmin!=null&&projAdmin.getAdminState()== AccountConstant.ACCOUNT_STATE_NORMAL){
            return true;
        }
        return false;
    }


    /**
     * 获取用户projKey
     * @param userId
     * @return
     */
    public Integer getUserProjKey(Integer userId){
        if(userId==null || userId==0){
            return 0;
        }
        Integer projKey=0;
        Object oProjKey= redisManager.hget(RedisConstant.H_USER_PROJ,userId.toString());
        if(ObjectUtils.isEmpty(oProjKey)){
            List<Integer> keys= fuPermissionUserProjectMapper.selectPorjKeysByUserId(userId);
            if(keys==null||keys.size()==0){
                log.error("根据用户ID，获取用户所属project失败！");
                return 0;
            }
            projKey=keys.get(0);
            redisManager.hset(RedisConstant.H_USER_PROJ,userId.toString(),keys.get(0));
        }else {
            projKey=(Integer)oProjKey;
        }
        return projKey;
    }
}
