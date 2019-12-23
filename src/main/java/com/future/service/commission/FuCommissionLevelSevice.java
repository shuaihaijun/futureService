package com.future.service.commission;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.future.common.enums.GlobalResultCode;
import com.future.common.exception.ParameterInvalidException;
import com.future.common.helper.PageInfoHelper;
import com.future.entity.commission.FuCommissionLevel;
import com.future.mapper.commission.FuCommissionLevelMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FuCommissionLevelSevice extends ServiceImpl<FuCommissionLevelMapper, FuCommissionLevel> {

    Logger log= LoggerFactory.getLogger(FuCommissionLevelSevice.class);

    @Autowired
    FuCommissionLevelMapper fuCommissionLevelMapper;

    /**
     * 获取佣金规则
     * @return
     */
    public PageInfo<FuCommissionLevel> getPageCommissonLevel(FuCommissionLevel level, PageInfoHelper helper){

        Wrapper<FuCommissionLevel> wrapper=new EntityWrapper<>();
        if(level.getCommissionType()!=null){
            wrapper.eq(FuCommissionLevel.LEVEL_NAME,level.getLevelName());
        }
        if(level.getCommissionType()!=null){
            wrapper.eq(FuCommissionLevel.COMMISSION_TYPE,level.getCommissionType());
        }
        if(level.getOrderType()!=null){
            wrapper.eq(FuCommissionLevel.ORDER_TYPE,level.getOrderType());
        }
        if(level.getCommissionUserType()!=null){
            wrapper.eq(FuCommissionLevel.COMMISSION_USER_TYPE,level.getCommissionUserType());
        }
        if(level.getCommissionUserLevel()!=null){
            wrapper.eq(FuCommissionLevel.COMMISSION_USER_LEVEL,level.getCommissionUserLevel());
        }
        if(level.getRateType()!=null){
            wrapper.eq(FuCommissionLevel.RATE_TYPE,level.getRateType());
        }
        if(helper==null){
            helper=new PageInfoHelper();
        }
        PageHelper.startPage(helper.getPageNo(),helper.getPageSize());
        List<FuCommissionLevel> commissionLevels= selectList(wrapper);
        return new PageInfo<>(commissionLevels);
    }

    /**
     * 修改规则
     * @param level
     * @return
     */
    public void saveCommissonLevel(FuCommissionLevel level){
        if(level==null){
            log.error("保存规则信息,数据为空");
            throw new ParameterInvalidException(GlobalResultCode.PARAM_NULL_POINTER);
        }
        if(level.getLevelName()==null) {
            log.error("保存规则信息,规则名称levelName为空");
            throw new ParameterInvalidException(GlobalResultCode.PARAM_NULL_POINTER);
        }
        if(level.getCommissionType()==null){
            log.error("保存规则信息,佣金类型为空");
            throw new ParameterInvalidException(GlobalResultCode.PARAM_NULL_POINTER);
        }
        if(level.getOrderType()==null){
            log.error("保存规则信息,订单类型为空");
            throw new ParameterInvalidException(GlobalResultCode.PARAM_NULL_POINTER);
        }
        if(level.getCommissionUserType()==null){
            log.error("保存规则信息,用户类型为空");
            throw new ParameterInvalidException(GlobalResultCode.PARAM_NULL_POINTER);
        }
        if(level.getCommissionUserLevel()==null){
            log.error("保存规则信息,返佣用户等级为空");
            throw new ParameterInvalidException(GlobalResultCode.PARAM_NULL_POINTER);
        }
        if(level.getRateType()==null){
            log.error("保存规则信息,比率计算类型为空");
            throw new ParameterInvalidException(GlobalResultCode.PARAM_NULL_POINTER);
        }
        if(level.getRate()==null){
            log.error("保存规则信息,返佣比率为空");
            throw new ParameterInvalidException(GlobalResultCode.PARAM_NULL_POINTER);
        }
        fuCommissionLevelMapper.insertSelective(level);
    }


    /**
     * 根据ID获取规则信息
     * @param id
     * @return
     */
    public FuCommissionLevel getCommissonLevelById(Integer id){
        if(id==null||id==0){
            log.error("据ID获取规则信息,ID为空");
            throw new ParameterInvalidException(GlobalResultCode.PARAM_NULL_POINTER);
        }
        return fuCommissionLevelMapper.selectByPrimaryKey(id);
    }

    /**
     * 修改规则
     * @param level
     * @return
     */
    public void upateCommissonLevel(FuCommissionLevel level){
        if(level==null||level.getId()==null||level.getId()==0){
            log.error("修改规则信息,ID为空");
            throw new ParameterInvalidException(GlobalResultCode.PARAM_NULL_POINTER);
        }
        fuCommissionLevelMapper.updateByPrimaryKeySelective(level);
    }

    /**
     * 删除规则
     * @param id
     * @return
     */
    public void deleteCommissonLevelById(Integer id){
        if(id==null||id==0){
            log.error("删除规则信息,ID为空");
            throw new ParameterInvalidException(GlobalResultCode.PARAM_NULL_POINTER);
        }
        fuCommissionLevelMapper.deleteByPrimaryKey(id);
    }

}
