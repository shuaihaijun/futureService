package com.future.mapper.product;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.future.entity.product.FuProductSignal;
import org.springframework.stereotype.Repository;

@Repository
public interface FuProductSignalMapper extends BaseMapper<FuProductSignal> {
    int deleteByPrimaryKey(Integer id);

    Integer insert(FuProductSignal record);

    int insertSelective(FuProductSignal record);

    FuProductSignal selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FuProductSignal record);

    int updateByPrimaryKey(FuProductSignal record);
}