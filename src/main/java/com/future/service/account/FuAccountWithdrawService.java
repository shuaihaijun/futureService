package com.future.service.account;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.future.entity.account.FuAccountWithdraw;
import com.future.mapper.account.FuAccountWithdrawMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FuAccountWithdrawService extends ServiceImpl<FuAccountWithdrawMapper, FuAccountWithdraw> {

    Logger log= LoggerFactory.getLogger(FuAccountWithdrawService.class);
    @Autowired
    FuAccountWithdrawMapper fuAccountWithdrawMapper;

    /**
     * 保存数据
     * @param withdraw
     * @return
     */
    public int saveSelective(FuAccountWithdraw withdraw){
        return fuAccountWithdrawMapper.insertSelective(withdraw);
    }
}
