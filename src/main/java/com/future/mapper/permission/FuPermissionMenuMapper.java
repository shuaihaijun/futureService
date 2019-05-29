package com.future.mapper.permission;

import com.future.entity.permission.FuPermissionMenu;

public interface FuPermissionMenuMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(FuPermissionMenu record);

    int insertSelective(FuPermissionMenu record);

    FuPermissionMenu selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FuPermissionMenu record);

    int updateByPrimaryKey(FuPermissionMenu record);
}