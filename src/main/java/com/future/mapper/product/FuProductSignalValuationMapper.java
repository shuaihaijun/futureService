package com.future.mapper.product;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.future.entity.product.FuProductSignalValuation;

public interface FuProductSignalValuationMapper extends BaseMapper<FuProductSignalValuation> {
    int deleteByPrimaryKey(Integer id);

    Integer insert(FuProductSignalValuation record);

    int insertSelective(FuProductSignalValuation record);

    FuProductSignalValuation selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FuProductSignalValuation record);

    int updateByPrimaryKey(FuProductSignalValuation record);
}