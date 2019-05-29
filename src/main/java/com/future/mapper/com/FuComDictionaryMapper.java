package com.future.mapper.com;

import com.future.entity.com.FuComDictionary;

public interface FuComDictionaryMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(FuComDictionary record);

    int insertSelective(FuComDictionary record);

    FuComDictionary selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FuComDictionary record);

    int updateByPrimaryKey(FuComDictionary record);
}