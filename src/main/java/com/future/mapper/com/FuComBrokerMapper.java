package com.future.mapper.com;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.future.entity.com.FuComBroker;
import org.springframework.stereotype.Component;

@Component
public interface FuComBrokerMapper extends BaseMapper<FuComBroker> {
    int deleteByPrimaryKey(Integer id);

    Integer insert(FuComBroker record);

    int insertSelective(FuComBroker record);

    FuComBroker selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FuComBroker record);

    int updateByPrimaryKey(FuComBroker record);
}