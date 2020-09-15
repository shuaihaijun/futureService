package com.future.timing;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.future.common.constants.CommonConstant;
import com.future.common.constants.RedisConstant;
import com.future.common.helper.PageInfoHelper;
import com.future.common.util.RedisManager;
import com.future.entity.user.FuUser;
import com.future.service.account.FuAccountMtService;
import com.future.service.trade.FuTradeAccountService;
import com.future.service.user.AdminService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;


/**
 * @author ：haijun
 * @date ：Created in 2019-10-17 15:17
 * @description：账户监听
 * @modified By：
 * @version: 1/0$
 */
@Configuration
@EnableScheduling
public class AccountMonitor {

    Logger log= LoggerFactory.getLogger(AccountMonitor.class);

    @Autowired
    FuAccountMtService fuAccountMtService;
    @Autowired
    AdminService adminService;
    @Autowired
    FuTradeAccountService fuTradeAccountService;
    @Autowired
    RedisManager redisManager;

    /**
     * 账户信息 每天同步一次
     */
    @Scheduled(cron = "0 0 0 * * ?")
    public void mtAccMonitor(){

        PageInfoHelper helper=new PageInfoHelper();
        Wrapper<FuUser> wrapper=new EntityWrapper<>();
        wrapper.eq(FuUser.IS_VERIFIED, CommonConstant.COMMON_YES);
        wrapper.eq(FuUser.IS_ACCOUNT, CommonConstant.COMMON_YES);
        Page<FuUser> fuUsers=PageHelper.startPage(helper.getPageNo(),helper.getPageSize());
        adminService.selectList(wrapper);

        while (fuUsers!=null&&fuUsers.size()>0){
            for(FuUser user:fuUsers){
                try {
                    log.info("账户信息 每天同步一次------------------------userId:"+user.getId());
                    /*同步用户账户信息*/
                    fuAccountMtService.updateAccountInfoFromMt(user.getId());
                }catch (Exception e){
                    log.error("账户信每天同步 失败，userId:"+user.getId());
                    log.error(e.getMessage(),e);
                }
            }
            /*翻页*/
            helper.setPageNo(helper.getPageNo()+1);
            fuUsers=PageHelper.startPage(helper.getPageNo(), helper.getPageSize());
            adminService.selectList(wrapper);
        }
    }

    /**
     * 账户链接失败重试 每分钟重试一次
     */
    @Scheduled(cron = "0 * * * * ?")
    public void mtAccReConnectMonitor(){

        long orderSize = redisManager.lGetListSize(RedisConstant.L_ACCOUNT_INFO_DISCONNECT);
        log.info("账户链接失败重试------size:"+orderSize);
        if(orderSize>0){
            String account[];
            String updateAccInfo="";
            Object accountInfo=redisManager.lPopLeft(RedisConstant.L_ACCOUNT_INFO_DISCONNECT);
            while (accountInfo!=null&&orderSize>0){
                try {
                    log.info("账户链接失败重试------account:"+accountInfo);
                    int times=1;
                    updateAccInfo=String.valueOf(accountInfo);
                    long lastTime=new Date().getTime()/1000;
                    account=updateAccInfo.split(",");
                    /*校验*/
                    if(account.length>3){
                        /*非第一次*/
                        times= Integer.parseInt(account[3]);
                        if(times>7){
                            // 重试上线 7次 （1分钟 10分钟 30分钟 60分钟 120分钟 360分钟 720分钟）
                            orderSize--;
                            if(orderSize==0){
                                break;
                            }
                            accountInfo=redisManager.lPopLeft(RedisConstant.L_ACCOUNT_INFO_DISCONNECT);
                            continue;
                        }
                        /*判断是否满足重连条件*/
                        if(!checkAccReConnect(times,Long.parseLong(account[4]))){
                            /*不满足条件 再放回去*/
                            redisManager.lSet(RedisConstant.L_ACCOUNT_INFO_DISCONNECT,accountInfo);
                            orderSize--;
                            if(orderSize==0){
                                break;
                            }
                            accountInfo=redisManager.lPopLeft(RedisConstant.L_ACCOUNT_INFO_DISCONNECT);
                            continue;
                        }
                    }
                    updateAccInfo = account[0]+","+account[1]+","+account[2]+","+ ++times +","+lastTime;
                    log.info("账户链接失败重试------account:"+accountInfo+",times:"+times);

                    /*1/登录MT账号*/
                    int clientId= fuTradeAccountService.setMtAccountConnect(account[0], Integer.parseInt(account[1]),account[2]);
                    if(clientId==0){
                        log.error("账户链接失败重试------user MTAccount connect failed!");
                        /*失败 再放回去*/
                        redisManager.lSet(RedisConstant.L_ACCOUNT_INFO_DISCONNECT,updateAccInfo);
                    }else {
                        log.error("账户链接失败重试------user MTAccount connect success!");
                    }
                }catch (Exception e){
                    log.error("账户链接失败重试，异常："+JSONObject.toJSONString(accountInfo));
                    /*失败 再放回去*/
                    redisManager.lSet(RedisConstant.L_ACCOUNT_INFO_DISCONNECT,updateAccInfo);
                    log.error(e.getMessage(),e);
                }
                orderSize--;
                if(orderSize==0){
                    break;
                }
                accountInfo=redisManager.lPopLeft(RedisConstant.L_ACCOUNT_INFO_DISCONNECT);
            }
        }
    }

    /**
     * 判断是否满足重连条件,重试上线 7次 （1分钟 10分钟 30分钟 60分钟 120分钟 360分钟 720分钟）
     * @param times
     * @param lastTime
     * @return
     */
    boolean checkAccReConnect(int times,long lastTime){
        System.out.println("-------------lastTime:"+lastTime);
        long currentTime=new Date().getTime()/1000;
        System.out.println("-------------currentTime:"+currentTime);
        long betweenSecond= currentTime - lastTime;
        System.out.println("-------------betweenSecond:"+betweenSecond);
        if(times==1){
            return true;
        }
        if(times==2 && betweenSecond>10*60){
            return true;
        }
        if(times==3 && betweenSecond>30*60){
            return true;
        }
        if(times==4 && betweenSecond>60*60){
            return true;
        }
        if(times==5 && betweenSecond>120*60){
            return true;
        }
        if(times==6 && betweenSecond>360*60){
            return true;
        }
        if(times==7 && betweenSecond>720*60){
            return true;
        }
        return false;
    }
}
