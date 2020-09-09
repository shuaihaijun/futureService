package com.future.timing;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.future.common.constants.CommonConstant;
import com.future.common.helper.PageInfoHelper;
import com.future.entity.user.FuUser;
import com.future.service.account.FuAccountMtService;
import com.future.service.user.AdminService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;


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
}
