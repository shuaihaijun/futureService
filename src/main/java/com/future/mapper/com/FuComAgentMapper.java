package com.future.mapper.com;

import com.future.entity.com.FuComAgent;

public interface FuComAgentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(FuComAgent record);

    int insertSelective(FuComAgent record);

    FuComAgent selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FuComAgent record);

    int updateByPrimaryKey(FuComAgent record);
}