package com.future.service.permission;


import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.future.common.enums.GlobalResultCode;
import com.future.common.exception.BusinessException;
import com.future.common.exception.DataConflictException;
import com.future.common.helper.PageInfoHelper;
import com.future.common.util.StringUtils;
import com.future.entity.permission.FuPermissionAdmin;
import com.future.entity.permission.FuPermissionProject;
import com.future.mapper.permission.FuPermissionAdminMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 工程项目信息模块业务实现类
 *
 * @author Admin
 * @version: 1.0
 */
@Service
@Slf4j
public class PermissionAdminService extends ServiceImpl<FuPermissionAdminMapper, FuPermissionAdmin> {

    @Autowired
    FuPermissionAdminMapper fuPermissionAdminMapper;
    @Autowired
    PermissionProjectService permissionProjectService;

    /**
     * 根据条件查询
     * @param permissionAdmin
     * @param helper
     * @return
     */
    public Page<FuPermissionAdmin> queryPermissionAdmin(Map permissionAdmin, PageInfoHelper helper){
        if(permissionAdmin==null){
            throw new DataConflictException(GlobalResultCode.PARAM_NULL_POINTER);
        }
        if(helper==null){
            helper=new PageInfoHelper();
        }
        Map conditionMap =new HashMap();
        if(permissionAdmin.get("projKey")!=null){
            conditionMap.put(FuPermissionAdmin.PROJ_KEY,permissionAdmin.get("projKey"));
        }
        if(permissionAdmin.get("id")!=null){
            conditionMap.put(FuPermissionAdmin.ADMIN_ID,permissionAdmin.get("id"));
        }
        if(permissionAdmin.get("userId")!=null){
            conditionMap.put(FuPermissionAdmin.USER_ID,permissionAdmin.get("userId"));
        }
        Page<FuPermissionAdmin> permissionAdmins= PageHelper.startPage(helper.getPageNo(),helper.getPageSize());
        fuPermissionAdminMapper.selectByMap(conditionMap);
        return permissionAdmins;
    }

    /**
     * 添加FuPermissionAdmin
     */
    @Transactional
    public void addPermissionAdmin(FuPermissionAdmin permissionAdmin){
        if(permissionAdmin==null||permissionAdmin.getProjKey()==null||permissionAdmin.getUserId()==null){
            throw new DataConflictException(GlobalResultCode.PARAM_NULL_POINTER,"信息不能为空！");
        }
        /*校验用户是否已存在*/
        Map conditionMap=new HashMap();
        conditionMap.put(FuPermissionAdmin.PROJ_KEY,permissionAdmin.getProjKey());
        conditionMap.put(FuPermissionAdmin.USER_ID,permissionAdmin.getUserId());
        List<FuPermissionAdmin> admins=  fuPermissionAdminMapper.selectByMap(conditionMap);
        if(admins!=null&&admins.size()>0){
            throw new DataConflictException(GlobalResultCode.DATA_ALREADY_EXISTED);
        }

        /*校验项目*/
        conditionMap.clear();
        conditionMap.put(FuPermissionProject.PROJ_KEY,permissionAdmin.getProjKey());
        List<FuPermissionProject> projects= permissionProjectService.selectByMap(conditionMap);
        if(projects==null||projects.size()!=1){
            log.error("根据项目key 获取项目失败！");
            throw new BusinessException("根据项目key 获取项目失败！");
        }
        if(StringUtils.isEmpty(projects.get(0).getProjAdmin())){
            //第一次需要设置管理员
            projects.get(0).setProjAdmin(permissionAdmin.getUserId().toString());
            permissionProjectService.updateById(projects.get(0));
        }

        fuPermissionAdminMapper.insertSelective(permissionAdmin);
    }

    /**
     * 删除
     * @param permissionAdmin
     */
    public void removePermissionAdmin(FuPermissionAdmin permissionAdmin){
        if(permissionAdmin==null||permissionAdmin.getId()==null){
            throw new DataConflictException(GlobalResultCode.PARAM_NULL_POINTER,"信息不能为空！");
        }
        fuPermissionAdminMapper.deleteByPrimaryKey(permissionAdmin.getId());
    }
}