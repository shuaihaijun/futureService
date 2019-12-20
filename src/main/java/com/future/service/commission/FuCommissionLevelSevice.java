package com.future.service.commission;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.future.entity.commission.FuCommissionLevel;
import com.future.mapper.commission.FuCommissionLevelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class FuCommissionLevelSevice extends ServiceImpl<FuCommissionLevelMapper, FuCommissionLevel> {

    Logger log= LoggerFactory.getLogger(FuCommissionLevelSevice.class);


}
