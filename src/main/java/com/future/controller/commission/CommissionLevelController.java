package com.future.controller.commission;

import com.future.common.enums.GlobalResultCode;
import com.future.common.exception.ParameterInvalidException;
import com.future.common.helper.PageInfoHelper;
import com.future.common.result.RequestParams;
import com.future.entity.commission.FuCommissionLevel;
import com.future.service.commission.FuCommissionLevelSevice;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/commission/commissionLevel")
public class CommissionLevelController {

    Logger log= LoggerFactory.getLogger(CommissionLevelController.class);

    @Autowired
    FuCommissionLevelSevice fuCommissionLevelSevice;


    //获取返佣规则
    @RequestMapping(value= "/getPageCommissonLevel",method=RequestMethod.POST)
    public @ResponseBody
    PageInfo<FuCommissionLevel> getPageCommissonLevel(@RequestBody RequestParams<FuCommissionLevel> requestParams){
        // 获取请求参数
        FuCommissionLevel level = requestParams.getParams();
        PageInfoHelper helper = requestParams.getPageInfoHelper();
        return fuCommissionLevelSevice.getPageCommissonLevel(level,helper);
    }

    //获取返佣规则
    @RequestMapping(value= "/getCommissonLevelById",method=RequestMethod.POST)
    public @ResponseBody FuCommissionLevel getCommissonLevelById(@RequestBody RequestParams<FuCommissionLevel> requestParams){
        // 获取请求参数
        FuCommissionLevel level = requestParams.getParams();
        if(level==null||level.getId()==null||level.getId()==0){
            log.error("获取规则信息,ID为空");
            throw new ParameterInvalidException(GlobalResultCode.PARAM_NULL_POINTER);
        }
        return fuCommissionLevelSevice.getCommissonLevelById(level.getId());
    }
    //修改返佣规则
    @RequestMapping(value= "/upateCommissonLevel",method=RequestMethod.POST)
    public @ResponseBody boolean upateCommissonLevel(@RequestBody RequestParams<FuCommissionLevel> requestParams){
        // 获取请求参数
        FuCommissionLevel level = requestParams.getParams();
        fuCommissionLevelSevice.upateCommissonLevel(level);
        return true;
    }
    //保存返佣规则
    @RequestMapping(value= "/saveCommissonLevel",method=RequestMethod.POST)
    public @ResponseBody boolean saveCommissonLevel(@RequestBody RequestParams<FuCommissionLevel> requestParams){
        // 获取请求参数
        FuCommissionLevel level = requestParams.getParams();
        fuCommissionLevelSevice.saveCommissonLevel(level);
        return true;
    }
    //删除返佣规则
    @RequestMapping(value= "/deleteCommissonLevelById",method=RequestMethod.POST)
    public @ResponseBody boolean deleteCommissonLevelById(@RequestBody RequestParams<FuCommissionLevel> requestParams){
        // 获取请求参数
        FuCommissionLevel level = requestParams.getParams();
        if(level==null||level.getId()==null||level.getId()==0){
            log.error("删除规则信息,ID为空");
            throw new ParameterInvalidException(GlobalResultCode.PARAM_NULL_POINTER);
        }
        fuCommissionLevelSevice.deleteCommissonLevelById(level.getId());
        return true;
    }

}