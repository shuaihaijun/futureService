package com.future.service.account;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.future.common.exception.DataConflictException;
import com.future.common.util.StringUtils;
import com.future.entity.account.FuAccountInfo;
import com.future.mapper.account.FuAccountInfoMapper;
import com.future.service.com.ServerService;
import com.future.service.mt.MTAccountService;
import com.jfx.Broker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 社区账户服务
 */
@Service
public class FuAccoutInfoService extends ServiceImpl<FuAccountInfoMapper, FuAccountInfo> {

    Logger log = LoggerFactory.getLogger(FuAccoutInfoService.class);

    @Autowired
    MTAccountService mtAccountService;
    @Autowired
    ServerService serverService;


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


    /**
     *  校验用户mt账户信息
     * @param serverName
     * @param username
     * @param password
     * @return
     */
    public Boolean verifyMTAccount(String serverName,String username,String password){

        if(StringUtils.isEmpty(serverName)
                ||StringUtils.isEmpty(username)
                ||StringUtils.isEmpty(password)){
            log.error("校验用户mt账户信息, 录入信息为空！");
            return false;
        }

        /*校验 服务器名称*/
        if(serverService.findByServerName(serverName)==null){
            log.error("校验用户mt账户信息, 服务器名称错误或不支持！");
            return false;
        }

        Broker broker=new Broker(serverName);
        return mtAccountService.getConnect(null,broker,username,password);
    }


}
