package com.future.service.follow;

import com.future.common.util.RedisManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *  跟随者逻辑处理
 */
@Service
public class FuTraderService {

    Logger log= LoggerFactory.getLogger(FuTraderService.class);

    @Autowired
    RedisManager redisManager;



}
