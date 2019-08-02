package com.future.mapper.account;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.future.entity.account.FuAccountInfo;
import org.springframework.stereotype.Repository;

@Repository
public interface FuAccountInfoMapper extends BaseMapper<FuAccountInfo> {
    int deleteByPrimaryKey(Integer id);

    Integer insert(FuAccountInfo record);

    int insertSelective(FuAccountInfo record);

    FuAccountInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FuAccountInfo record);

    int updateByPrimaryKey(FuAccountInfo record);
}