package com.future.mapper.order;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.future.entity.order.FuOrderInfo;

public interface FuOrderInfoMapper extends BaseMapper<FuOrderInfo> {
    int deleteByPrimaryKey(Integer id);

    Integer insert(FuOrderInfo record);

    int insertSelective(FuOrderInfo record);

    FuOrderInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FuOrderInfo record);

    int updateByPrimaryKey(FuOrderInfo record);
}