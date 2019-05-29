package com.future.mapper.com;

import com.future.entity.com.FuComRegion;

public interface FuComRegionMapper {
    int deleteByPrimaryKey(Short regionId);

    int insert(FuComRegion record);

    int insertSelective(FuComRegion record);

    FuComRegion selectByPrimaryKey(Short regionId);

    int updateByPrimaryKeySelective(FuComRegion record);

    int updateByPrimaryKey(FuComRegion record);
}