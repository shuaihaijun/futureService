package com.future.mapper.order;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.future.entity.order.FuOrderDaysum;
import org.springframework.stereotype.Component;

@Component
public interface FuOrderDaysumMapper extends BaseMapper<FuOrderDaysum> {
    int deleteByPrimaryKey(Integer id);

    Integer insert(FuOrderDaysum record);

    int insertSelective(FuOrderDaysum record);

    FuOrderDaysum selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FuOrderDaysum record);

    int updateByPrimaryKey(FuOrderDaysum record);
}