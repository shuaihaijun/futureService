package com.future.mapper.product;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.future.entity.product.FuProductSignalApply;
import org.springframework.stereotype.Repository;

@Repository
public interface FuProductSignalApplyMapper extends BaseMapper<FuProductSignalApply> {
    int deleteByPrimaryKey(Integer id);

    Integer insert(FuProductSignalApply record);

    int insertSelective(FuProductSignalApply record);

    FuProductSignalApply selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FuProductSignalApply record);

    int updateByPrimaryKey(FuProductSignalApply record);
}