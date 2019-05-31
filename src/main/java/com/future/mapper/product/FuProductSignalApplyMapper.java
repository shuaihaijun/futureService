package com.future.mapper.product;

import com.future.entity.product.FuProductSignalApply;

public interface FuProductSignalApplyMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(FuProductSignalApply record);

    int insertSelective(FuProductSignalApply record);

    FuProductSignalApply selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FuProductSignalApply record);

    int updateByPrimaryKey(FuProductSignalApply record);
}