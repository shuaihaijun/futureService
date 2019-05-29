package com.future.mapper.com;

import com.future.entity.com.FuComLogs;

public interface FuComLogsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(FuComLogs record);

    int insertSelective(FuComLogs record);

    FuComLogs selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FuComLogs record);

    int updateByPrimaryKeyWithBLOBs(FuComLogs record);

    int updateByPrimaryKey(FuComLogs record);
}