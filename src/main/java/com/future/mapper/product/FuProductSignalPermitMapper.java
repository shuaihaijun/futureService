package com.future.mapper.product;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.future.entity.product.FuProductSignalPermit;
import org.springframework.stereotype.Component;

@Component
public interface FuProductSignalPermitMapper extends BaseMapper<FuProductSignalPermit> {
    int deleteByPrimaryKey(Integer id);

    Integer insert(FuProductSignalPermit record);

    int insertSelective(FuProductSignalPermit record);

    FuProductSignalPermit selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FuProductSignalPermit record);

    int updateByPrimaryKey(FuProductSignalPermit record);
}