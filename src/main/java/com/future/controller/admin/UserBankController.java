package com.future.controller.admin;

import com.future.common.exception.DataConflictException;
import com.future.common.result.RequestParams;
import com.future.entity.user.FuUserBank;
import com.future.service.user.FuUserBankService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/userBank")
public class UserBankController {

    Logger log= LoggerFactory.getLogger(UserBankController.class);

    @Autowired
    FuUserBankService fuUserBankService;

    /**
     * 银行卡信息保存
     *
     * @param requestParams 需要保存的对象数据
     */
    @ApiOperation(value = "新增银行卡信息", notes = "新增银行卡信息")
    @PostMapping(value = "/saveOrUpdate")
    public boolean saveOrUpdate(@RequestBody RequestParams<FuUserBank> requestParams) {
        FuUserBank bank = requestParams.getParams();
        fuUserBankService.saveOrUdateBankSelective(bank);
        return true;
    }

    /**
     * 银行卡信息更新，根据主键
     *
     * @param requestParams 需要更新的数据
     */
    @ApiOperation(value = "银行卡信息更新", notes = "银行卡信息更新")
    @PostMapping(value = "/modify")
    public boolean modify(@RequestBody RequestParams<FuUserBank> requestParams) {
        FuUserBank bank = requestParams.getParams();
        fuUserBankService.updateBankSelective(bank);
        return true;
    }

    /**
     * 银行卡信息更新，根据主键
     *
     * @param requestParams 需要更新的数据
     */
    @ApiOperation(value = "银行卡信息更新", notes = "银行卡信息更新")
    @PostMapping(value = "/find")
    public FuUserBank find(@RequestBody RequestParams<FuUserBank> requestParams) {
        FuUserBank bank = requestParams.getParams();
        if(bank==null){
            throw new DataConflictException("传入参数为空！");
        }
        return fuUserBankService.getBankByUserId(bank.getId(),bank.getUserId());
    }
}