package com.future.mapper.order;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.future.entity.order.FuOrderFollowInfo;
import org.springframework.stereotype.Repository;

@Repository
public interface FuOrderFollowInfoMapper extends BaseMapper<FuOrderFollowInfo> {
    int deleteByPrimaryKey(Integer id);

    Integer insert(FuOrderFollowInfo record);

    int insertSelective(FuOrderFollowInfo record);

    FuOrderFollowInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FuOrderFollowInfo record);

    int updateByPrimaryKey(FuOrderFollowInfo record);
}