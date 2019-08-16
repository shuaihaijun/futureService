package com.future.mapper.com;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.future.entity.com.FuComDictionary;
import org.springframework.stereotype.Repository;

@Repository
public interface FuComDictionaryMapper extends BaseMapper<FuComDictionary> {
    int deleteByPrimaryKey(Integer id);

    Integer insert(FuComDictionary record);

    int insertSelective(FuComDictionary record);

    FuComDictionary selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FuComDictionary record);

    int updateByPrimaryKey(FuComDictionary record);
}