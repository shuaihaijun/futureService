package com.future.mapper.com;

import com.future.entity.com.FuComBroker;

public interface FuComBrokerMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(FuComBroker record);

    int insertSelective(FuComBroker record);

    FuComBroker selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FuComBroker record);

    int updateByPrimaryKey(FuComBroker record);
}