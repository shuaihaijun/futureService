package com.future.mapper.permission;

import com.future.entity.permission.FuPermissionRole;

public interface FuPermissionRoleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(FuPermissionRole record);

    int insertSelective(FuPermissionRole record);

    FuPermissionRole selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FuPermissionRole record);

    int updateByPrimaryKey(FuPermissionRole record);
}