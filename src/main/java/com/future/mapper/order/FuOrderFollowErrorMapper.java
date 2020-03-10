package com.future.mapper.order;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.future.entity.order.FuOrderFollowError;
import org.springframework.stereotype.Component;

@Component
public interface FuOrderFollowErrorMapper extends BaseMapper<FuOrderFollowError> {
    int deleteByPrimaryKey(Integer id);

    Integer insert(FuOrderFollowError record);

    int insertSelective(FuOrderFollowError record);

    FuOrderFollowError selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FuOrderFollowError record);

    int updateByPrimaryKey(FuOrderFollowError record);
}