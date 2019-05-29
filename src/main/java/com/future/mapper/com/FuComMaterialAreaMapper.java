package com.future.mapper.com;

import com.future.entity.com.FuComMaterialArea;

public interface FuComMaterialAreaMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(FuComMaterialArea record);

    int insertSelective(FuComMaterialArea record);

    FuComMaterialArea selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FuComMaterialArea record);

    int updateByPrimaryKey(FuComMaterialArea record);
}