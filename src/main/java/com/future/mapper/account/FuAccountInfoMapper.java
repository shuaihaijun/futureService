package com.future.mapper.account;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.future.entity.account.FuAccountInfo;
import com.future.pojo.bo.account.UserAccountInfoBO;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface FuAccountInfoMapper extends BaseMapper<FuAccountInfo> {
    int deleteByPrimaryKey(Integer id);

    Integer insert(FuAccountInfo record);

    int insertSelective(FuAccountInfo record);

    FuAccountInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FuAccountInfo record);

    int updateByPrimaryKey(FuAccountInfo record);

    List<UserAccountInfoBO> selectUserAccountByCondition(Map condition);
}