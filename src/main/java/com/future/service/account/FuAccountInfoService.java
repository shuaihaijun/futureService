package com.future.service.account;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.future.common.constants.AccountConstant;
import com.future.entity.account.FuAccountInfo;
import com.future.mapper.account.FuAccountInfoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FuAccountInfoService extends ServiceImpl<FuAccountInfoMapper, FuAccountInfo> {

    Logger log= LoggerFactory.getLogger(FuAccountInfoService.class);
    @Autowired
    FuAccountInfoMapper fuAccountInfoMapper;

    /**
     * 初始化用户社区账户
     * @param userId
     */
    public void initAccountInfo(Integer userId,String password){
        if(userId==null||userId==0){
            log.error("初始化用户社区账户,用户ID为空！");
            return;
        }

        FuAccountInfo acc=new FuAccountInfo();
        Map conditionMap=new HashMap();
        conditionMap.put(FuAccountInfo.USER_ID,userId);
        List<FuAccountInfo> accountInfos=fuAccountInfoMapper.selectByMap(conditionMap);
        if(accountInfos!=null && accountInfos.size()>0){
            /*以后账户，设置为正常*/
            acc=accountInfos.get(0);
            acc.setAccountState(AccountConstant.ACCOUNT_STATE_NORMAL);
            fuAccountInfoMapper.updateByPrimaryKey(acc);
            return;
        }
        /*新建账户*/
        acc.setUserId(userId);
        /*加密后的密码，初始密码默认 跟 用户密码一致*/
        acc.setAccountSecurities(password);
        fuAccountInfoMapper.insertSelective(acc);
    }

}