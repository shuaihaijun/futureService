package com.future.service.account;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.future.entity.account.FuAccountCommissionFlow;
import com.future.mapper.account.FuAccountCommissionFlowMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class FuAccountCommissionFlowService extends ServiceImpl<FuAccountCommissionFlowMapper, FuAccountCommissionFlow> {

    Logger log= LoggerFactory.getLogger(FuAccountCommissionFlowService.class);


}
