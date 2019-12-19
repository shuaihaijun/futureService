package com.future.mapper.user;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.future.entity.user.FuUserOrderDay;

public interface FuUserOrderDayMapper extends BaseMapper<FuUserOrderDay> {
    int deleteByPrimaryKey(Integer id);

    Integer insert(FuUserOrderDay record);

    int insertSelective(FuUserOrderDay record);

    FuUserOrderDay selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FuUserOrderDay record);

    int updateByPrimaryKey(FuUserOrderDay record);
}