package com.future.mapper.permission;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.future.entity.permission.FuPermissionAdmin;
import org.springframework.stereotype.Component;

@Component
public interface FuPermissionAdminMapper extends BaseMapper<FuPermissionAdmin> {
    int deleteByPrimaryKey(Integer id);

    Integer insert(FuPermissionAdmin record);

    int insertSelective(FuPermissionAdmin record);

    FuPermissionAdmin selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FuPermissionAdmin record);

    int updateByPrimaryKey(FuPermissionAdmin record);
}