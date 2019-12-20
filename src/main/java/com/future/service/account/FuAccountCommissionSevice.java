package com.future.service.account;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.future.common.constants.CommonConstant;
import com.future.entity.account.FuAccountCommission;
import com.future.entity.account.FuAccountInfo;
import com.future.mapper.account.FuAccountCommissionMapper;
import com.future.mapper.account.FuAccountInfoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FuAccountCommissionSevice extends ServiceImpl<FuAccountCommissionMapper, FuAccountCommission> {

    Logger log= LoggerFactory.getLogger(FuAccountCommissionSevice.class);
    @Autowired
    FuAccountCommissionMapper fuAccountCommissionMapper;

    /**
     * 初始化用户佣金账户
     * @param userId
     */
    public void initAccountCommission(Integer userId,Integer accountId){
        if(userId==null||userId==0||accountId==null||accountId==0){
            log.error("初始化用户佣金账户,用户ID为空！");
            return;
        }

        FuAccountCommission acc=new FuAccountCommission();
        Map conditionMap=new HashMap();
        conditionMap.put(FuAccountCommission.USER_ID,userId);
        List<FuAccountCommission> accountInfos=fuAccountCommissionMapper.selectByMap(conditionMap);
        if(accountInfos!=null && accountInfos.size()>0){
            /*以后账户，设置为正常*/
            acc=accountInfos.get(0);
            acc.setAccountState(CommonConstant.COMMON_STATE_INVALID);
            fuAccountCommissionMapper.updateByPrimaryKey(acc);
            return;
        }
        /*新建账户*/
        acc.setUserId(userId);
        acc.setAccountId(accountId);
        /*加密后的密码，初始密码默认 跟 用户密码一致*/
        fuAccountCommissionMapper.insertSelective(acc);
    }

}
