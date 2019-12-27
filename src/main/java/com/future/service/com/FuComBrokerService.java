package com.future.service.com;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.future.common.helper.PageInfoHelper;
import com.future.entity.com.FuComBroker;
import com.future.entity.com.FuComServer;
import com.future.mapper.com.FuComBrokerMapper;
import com.future.mapper.com.FuComServerMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FuComBrokerService extends ServiceImpl<FuComBrokerMapper, FuComBroker> {

    @Autowired
    FuComBrokerMapper fuComBrokerMapper;

    /**
     * 保存数据
     * @param fuComBroker
     */
    public int saveSelective(FuComBroker fuComBroker){
        return fuComBrokerMapper.insertSelective(fuComBroker);
    }

    /**
     * 修改数据
     * @param fuComBroker
     * @return
     */
    public int updateSelectiveById(FuComBroker fuComBroker){
        if(fuComBroker.getId()==null || fuComBroker.getId()==0){
            return 0;
        }
        return fuComBrokerMapper.updateByPrimaryKeySelective(fuComBroker);
    }

    /**
     * 删除数据
     * @param brokerId
     * @return
     */
    public int deleteById(Integer brokerId){
        return fuComBrokerMapper.deleteById(brokerId);
    }

    /**
     * 查詢信息
     * @param broker
     * @param helper
     * @return
     */
    public PageInfo<FuComBroker> getPage(FuComBroker broker, PageInfoHelper helper){
        if(helper==null){
            helper=new PageInfoHelper();
        }
        PageHelper.startPage(helper.getPageNo(),helper.getPageSize());
        List<FuComBroker> list = Lists.newArrayList();
        if(broker!=null && broker.getBrokerName()!=null){
            list=selectList(new EntityWrapper<FuComBroker>().eq(FuComBroker.BROKER_NAME,broker.getBrokerName()));
        }else {
            list=selectList(new EntityWrapper<>());
        }
        return new PageInfo<>(list);
    }
}
