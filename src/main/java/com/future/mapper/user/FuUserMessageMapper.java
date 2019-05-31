package com.future.mapper.user;

import com.future.entity.user.FuUserMessage;

public interface FuUserMessageMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(FuUserMessage record);

    int insertSelective(FuUserMessage record);

    FuUserMessage selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FuUserMessage record);

    int updateByPrimaryKey(FuUserMessage record);
}