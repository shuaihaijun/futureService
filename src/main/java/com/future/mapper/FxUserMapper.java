package com.future.mapper;

import com.future.entity.FxUser;

public interface FxUserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(FxUser record);

    int insertSelective(FxUser record);

    FxUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FxUser record);

    int updateByPrimaryKeyWithBLOBs(FxUser record);

    int updateByPrimaryKey(FxUser record);
}