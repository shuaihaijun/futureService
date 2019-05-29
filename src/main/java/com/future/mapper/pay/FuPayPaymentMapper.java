package com.future.mapper.pay;

import com.future.entity.pay.FuPayPayment;

public interface FuPayPaymentMapper {
    int deleteByPrimaryKey(Byte id);

    int insert(FuPayPayment record);

    int insertSelective(FuPayPayment record);

    FuPayPayment selectByPrimaryKey(Byte id);

    int updateByPrimaryKeySelective(FuPayPayment record);

    int updateByPrimaryKey(FuPayPayment record);
}