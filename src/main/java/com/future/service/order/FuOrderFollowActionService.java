package com.future.service.order;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.future.entity.order.FuOrderFollowAction;
import com.future.mapper.order.FuOrderFollowActionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FuOrderFollowActionService extends ServiceImpl<FuOrderFollowActionMapper, FuOrderFollowAction> {

    Logger log = LoggerFactory.getLogger(FuOrderFollowActionService.class);

    /**
     * 批量新增跟单动作表
     * @param actions
     */
    public void batchInsert(List<FuOrderFollowAction> actions){
        if(actions!=null || actions.size()<=0){
            log.error("批量新增跟单动作表失败！数据为空！");
            return;
        }
        batchInsert(actions);
    }
}
