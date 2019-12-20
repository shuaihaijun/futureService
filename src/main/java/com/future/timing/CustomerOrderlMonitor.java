package com.future.timing;

import com.future.common.constants.CommonConstant;
import com.future.common.helper.PageInfoHelper;
import com.future.pojo.bo.order.UserMTAccountBO;
import com.future.service.account.FuAccountMtSevice;
import com.future.service.order.FuOrderCustomerService;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ：haijun
 * @date ：Created in 2019-10-17 15:17
 * @description：用户自交易订单定时同步
 * @modified By：
 * @version: 1/0$
 */
public class CustomerOrderlMonitor {

    Logger log= LoggerFactory.getLogger(CustomerOrderlMonitor.class);
    @Autowired
    FuOrderCustomerService fuOrderCustomerService;
    @Autowired
    FuAccountMtSevice fuAccountMtSevice;

    /**
     * 监听信号源 （每天晚上1点开始触发同步）
     */
    @Scheduled(cron = "0 0 1 * * ?")
    public void monitor(){

        /*1、同步用户自定义订单 计算佣金流水*/
        PageInfoHelper helper = new PageInfoHelper();
        PageHelper.startPage(helper.getPageNo(), helper.getPageSize());
        Map conditionMap=new HashMap();
        /*已验证的 合作平台的 MT账户*/
        conditionMap.put("isAccount", CommonConstant.COMMON_YES);
        conditionMap.put("isChief",CommonConstant.COMMON_YES);
        List<UserMTAccountBO> list = fuAccountMtSevice.getUserMTAccByCondition(conditionMap);
        while (list.size()>0){
            for(UserMTAccountBO userMTAccountBO: list){
                fuOrderCustomerService.synUserMTOrder(userMTAccountBO.getUserId(),userMTAccountBO.getUsername());
            }
            /*翻页*/
            helper.setPageNo(helper.getPageNo()+1);
            PageHelper.startPage(helper.getPageNo(), helper.getPageSize());
            list = fuAccountMtSevice.getUserMTAccByCondition(conditionMap);
        }


        /*2、计算佣金*/


    }
}
