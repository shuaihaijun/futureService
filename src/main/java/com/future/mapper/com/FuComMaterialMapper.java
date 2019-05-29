package com.future.mapper.com;

import com.future.entity.com.FuComMaterial;

public interface FuComMaterialMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(FuComMaterial record);

    int insertSelective(FuComMaterial record);

    FuComMaterial selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FuComMaterial record);

    int updateByPrimaryKeyWithBLOBs(FuComMaterial record);

    int updateByPrimaryKey(FuComMaterial record);
}