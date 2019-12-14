package com.future.service.order;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.future.entity.order.FuOrderSignal;
import com.future.mapper.order.FuOrderSignalMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@Service
public class FuOrderSignalService extends ServiceImpl<FuOrderSignalMapper, FuOrderSignal> {

    Logger log=LoggerFactory.getLogger(FuOrderSignalService.class);

}
