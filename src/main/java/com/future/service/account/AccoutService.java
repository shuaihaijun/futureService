package com.future.service.account;

import com.future.service.mt.MTAccountService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 社区账户服务
 */
@Service
public class AccoutService {

    MTAccountService mtAccountService;

    /**
     * 根据用户名查询用户mt账户信息
     * @param username
     * @param chief
     * @return
     */
    public Map getUserMTAccountByUsername(String username ,int chief){
        return null;
    }

    /**
     * 根据账户ID获取账户信息
     * @param accountId
     * @return
     */
    public Map getUserMtAccountById(String accountId){
        return null;
    }

    /**
     * 根据用户名获取用户所有MT账户信息
     * @param username
     * @return
     */
    public List<Map> getUserMTAccount(String username){
        return null;
    }




}
