package com.future.mapper.permission;

import com.future.entity.permission.FuPermissionRoleMenu;

public interface FuPermissionRoleMenuMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(FuPermissionRoleMenu record);

    int insertSelective(FuPermissionRoleMenu record);

    FuPermissionRoleMenu selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FuPermissionRoleMenu record);

    int updateByPrimaryKey(FuPermissionRoleMenu record);
}