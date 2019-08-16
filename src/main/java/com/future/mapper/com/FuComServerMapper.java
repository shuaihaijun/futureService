package com.future.mapper.com;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.future.entity.com.FuComServer;
import org.springframework.stereotype.Repository;

@Repository
public interface FuComServerMapper extends BaseMapper<FuComServer> {
    int deleteByPrimaryKey(Integer id);

    Integer insert(FuComServer record);

    int insertSelective(FuComServer record);

    FuComServer selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FuComServer record);

    int updateByPrimaryKey(FuComServer record);
}