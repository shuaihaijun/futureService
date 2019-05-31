package com.future.mapper.product;

import com.future.entity.product.FuProductFollow;

public interface FuProductFollowMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(FuProductFollow record);

    int insertSelective(FuProductFollow record);

    FuProductFollow selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FuProductFollow record);

    int updateByPrimaryKey(FuProductFollow record);
}