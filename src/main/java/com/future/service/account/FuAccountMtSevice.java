package com.future.service.account;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.future.entity.account.FuAccountMt;
import com.future.mapper.account.FuAccountMtMapper;
import com.future.pojo.bo.order.UserMTAccountBO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class FuAccountMtSevice extends ServiceImpl<FuAccountMtMapper, FuAccountMt> {

    Logger log= LoggerFactory.getLogger(FuAccountMtSevice.class);

    @Autowired
    FuAccountMtMapper fuAccountMtMapper;

    /**
     * 根据条件查询用户MT账户信息
     * @param condition
     * @return
     */
    public List<UserMTAccountBO> getUserMTAccByCondition(Map condition){

        //  TODO     条件暂时没设置
        /*if(){
        }*/
        return fuAccountMtMapper.selectUserMTAccByCondition(condition);
    }

}
