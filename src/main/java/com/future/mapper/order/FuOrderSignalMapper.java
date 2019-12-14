package com.future.mapper.order;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.future.entity.order.FuOrderSignal;
import org.springframework.stereotype.Component;

@Component
public interface FuOrderSignalMapper extends BaseMapper<FuOrderSignal> {
    int deleteByPrimaryKey(Integer id);

    Integer insert(FuOrderSignal record);

    int insertSelective(FuOrderSignal record);

    FuOrderSignal selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FuOrderSignal record);

    int updateByPrimaryKey(FuOrderSignal record);
}