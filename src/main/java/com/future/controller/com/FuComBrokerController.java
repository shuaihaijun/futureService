package com.future.controller.com;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.future.common.exception.DataConflictException;
import com.future.common.helper.PageInfoHelper;
import com.future.common.result.RequestParams;
import com.future.entity.com.FuComBroker;
import com.future.service.com.FuComBrokerService;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/comBroker")
public class FuComBrokerController {

    Logger log= LoggerFactory.getLogger(FuComBrokerController.class);

    @Autowired
    FuComBrokerService fuComBrokerService;

    @RequestMapping("/saveComBroker")
    public boolean saveComBroker(@RequestBody @Validated RequestParams<FuComBroker> requestParams){
        FuComBroker broker = requestParams.getParams();
        Integer brokerId=broker.getId();
        if(brokerId==null ||  brokerId==0){
            fuComBrokerService.saveSelective(broker);
        } else {
            fuComBrokerService.updateSelectiveById(broker);
        }
        return true;
    }

    @RequestMapping("/queryComBroker")
    public @ResponseBody
    PageInfo<FuComBroker> queryComBroker(@RequestBody RequestParams<FuComBroker> requestParams){
        FuComBroker broker = requestParams.getParams();
        PageInfoHelper helper = requestParams.getPageInfoHelper();
        return fuComBrokerService.getPage(broker,helper);
    }

    @RequestMapping("/findComBrokerById")
    public @ResponseBody FuComBroker findComBrokerById(@RequestBody RequestParams<FuComBroker> requestParams){
        FuComBroker broker = requestParams.getParams();
        Integer brokerId=broker.getId();
        if(brokerId ==null ||  brokerId==0){
            log.error("ID数据为空！");
            throw new DataConflictException("ID数据为空！");
        }
        return fuComBrokerService.selectOne(new EntityWrapper<FuComBroker>().eq(FuComBroker.BROKER_ID,brokerId));
    }


    @RequestMapping("/deleteComBroker")
    public boolean deleteComBroker(@RequestBody RequestParams<FuComBroker> requestParams){
        FuComBroker broker = requestParams.getParams();
        Integer brokerId=broker.getId();
        if(brokerId ==null ||  brokerId==0){
            log.error("ID数据为空！");
            throw new DataConflictException("ID数据为空！");
        } else {
            fuComBrokerService.deleteById(brokerId);
        }
        return true;
    }


    @RequestMapping("/updateComBroker")
    public boolean updateComBroker(@RequestBody RequestParams<FuComBroker> requestParams){
        FuComBroker broker = requestParams.getParams();
        Integer brokerId=broker.getId();
        if(brokerId ==null ||  brokerId==0){
            log.error("ID数据为空！");
            throw new DataConflictException("ID数据为空！");
        } else {
            fuComBrokerService.updateSelectiveById(broker);
        }
        return true;
    }
}
