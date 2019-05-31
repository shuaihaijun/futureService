package com.future.mapper.product;

import com.future.entity.product.FuProductSignal;

public interface FuProductSignalMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(FuProductSignal record);

    int insertSelective(FuProductSignal record);

    FuProductSignal selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FuProductSignal record);

    int updateByPrimaryKey(FuProductSignal record);
}