package com.future.mapper.permission;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.future.entity.permission.FuPermissionControl;
import org.springframework.stereotype.Component;

@Component
public interface FuPermissionControlMapper extends BaseMapper<FuPermissionControl> {
    int deleteByPrimaryKey(Integer id);

    Integer insert(FuPermissionControl record);

    int insertSelective(FuPermissionControl record);

    FuPermissionControl selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FuPermissionControl record);

    int updateByPrimaryKey(FuPermissionControl record);
}