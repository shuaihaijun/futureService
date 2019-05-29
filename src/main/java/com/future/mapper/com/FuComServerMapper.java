package com.future.mapper.com;

import com.future.entity.com.FuComServer;

public interface FuComServerMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(FuComServer record);

    int insertSelective(FuComServer record);

    FuComServer selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FuComServer record);

    int updateByPrimaryKey(FuComServer record);
}