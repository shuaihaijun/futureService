package com.future.service.user;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.future.common.exception.DataConflictException;
import com.future.common.helper.PageInfoHelper;
import com.future.common.util.StringUtils;
import com.future.entity.com.FuComAgent;
import com.future.entity.user.FuUserBank;
import com.future.mapper.com.FuComAgentMapper;
import com.future.mapper.user.FuUserBankMapper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Arrays;


@Service
public class FuUserBankService extends ServiceImpl<FuUserBankMapper, FuUserBank> {

    Logger log= LoggerFactory.getLogger(FuUserBankService.class);
    @Autowired
    FuUserBankMapper fuUserBankMapper;

    /**
     * 根据用户ID 获取用户银行卡！
     * @param id
     * @param userId
     * @return
     */
    public FuUserBank getBankByUserId(Integer id, Integer userId){
        //获取配置文件中超级管理员，判断当前登录用户是否为超级管理员,true为超管

        if((id==null || id==0)&&(userId==null || userId==0)){
            log.error("根据用户ID 获取用户银行失败！用户ID 为空！");
            throw new DataConflictException("\"根据用户ID 获取用户银行失败！用户ID 为空！");
        }

        FuUserBank bank=new FuUserBank();
        if(id!=null && id>0){
            bank=selectOne(new EntityWrapper<FuUserBank>().eq(FuUserBank.Bank_ID,id));
        }else {
            bank=selectOne(new EntityWrapper<FuUserBank>().eq(FuUserBank.USER_ID,userId));
        }

        return bank;
    }

    /**
     * 根据用户ID 获取用户银行卡！
     * @param bank
     * @return
     */
    public PageInfo<FuUserBank> getPage(FuUserBank bank, PageInfoHelper helper){

        /*if(bank==null){
            //获取配置文件中超级管理员，判断当前登录用户是否为超级管理员,true为超管
        }

        if(bank==null){
            selectList(new EntityWrapper<FuUserBank>());
        }else if(id!=null && id>0){
            bank=selectOne(new EntityWrapper<FuUserBank>().eq(FuUserBank.ID,id));
        }else {
            bank=selectOne(new EntityWrapper<FuUserBank>().eq(FuUserBank.USER_ID,userId));
        }

        return bank;*/
        return new PageInfo<FuUserBank>();
    }


    /**
     * 插入银行卡信息
     * @return
     */
    public int saveOrUdateBankSelective(FuUserBank bank){
        if(bank==null){
            log.error("数据为空！");
            throw new DataConflictException("数据为空！");
        }

        if(bank.getId()!=null&&bank.getId()>0){
            return updateBankSelective(bank);
        }

        FuUserBank userBank= selectOne(new EntityWrapper<FuUserBank>().eq(FuUserBank.USER_ID,bank.getUserId()));
        if(userBank!=null&&userBank.getId()!=null){
            bank.setId(userBank.getId());
            return updateBankSelective(bank);
        }

        if(StringUtils.isEmpty(bank.getBankName())){
            log.error("银行名称为空！");
            throw new DataConflictException("银行名称为空！！");
        }
        if(StringUtils.isEmpty(bank.getCode())){
            log.error("银行卡为空！");
            throw new DataConflictException("银行卡为空！！");
        }
        if(StringUtils.isEmpty(bank.getHostName())){
            log.error("银行户主为空！");
            throw new DataConflictException("银行户主为空！！");
        }
        return fuUserBankMapper.insertSelective(bank);
    }


    /**
     * 修改银行卡信息
     * @return
     */
    public int updateBankSelective(FuUserBank bank){
        if(bank==null){
            log.error("数据为空！");
            throw new DataConflictException("数据为空！");
        }
        if(bank.getId()==null||bank.getId()==0){
            log.error("ID为空！");
            throw new DataConflictException("ID为空！");
        }
        return fuUserBankMapper.updateByPrimaryKey(bank);
    }
}
