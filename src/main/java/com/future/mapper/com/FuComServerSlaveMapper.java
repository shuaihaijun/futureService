package com.future.mapper.com;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.future.entity.com.FuComServerSlave;
import org.springframework.stereotype.Repository;

@Repository
public interface FuComServerSlaveMapper extends BaseMapper<FuComServerSlave> {
    int deleteByPrimaryKey(Integer id);

    Integer insert(FuComServerSlave record);

    int insertSelective(FuComServerSlave record);

    FuComServerSlave selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FuComServerSlave record);

    int updateByPrimaryKey(FuComServerSlave record);
}