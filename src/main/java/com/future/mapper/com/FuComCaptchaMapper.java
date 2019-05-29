package com.future.mapper.com;

import com.future.entity.com.FuComCaptcha;

public interface FuComCaptchaMapper {
    int deleteByPrimaryKey(Long id);

    int insert(FuComCaptcha record);

    int insertSelective(FuComCaptcha record);

    FuComCaptcha selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(FuComCaptcha record);

    int updateByPrimaryKey(FuComCaptcha record);
}