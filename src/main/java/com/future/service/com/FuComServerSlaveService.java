package com.future.service.com;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.future.common.constants.RedisConstant;
import com.future.common.exception.DataConflictException;
import com.future.common.util.RedisManager;
import com.future.common.util.StringUtils;
import com.future.entity.com.FuComServerSlave;
import com.future.mapper.com.FuComServerSlaveMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FuComServerSlaveService extends ServiceImpl<FuComServerSlaveMapper, FuComServerSlave> {

    Logger log= LoggerFactory.getLogger(FuComServerSlaveService.class);

    @Autowired
    FuComServerSlaveMapper serverSlaveMapper;
    @Autowired
    RedisManager redisManager;

    /**
     * 保存或修改从服务器信息
     * @param serverSlave
     * @return
     */
    public boolean saveOrUpdate(FuComServerSlave serverSlave){
        if(serverSlave==null||serverSlave.getServerName()==null||serverSlave.getServerSlaveName()==null){
            log.error("保存从服务器信息,参数为空！");
            throw  new DataConflictException("保存从服务器信息,参数为空！");
        }
        if(serverSlave.getId()!=null&& serverSlave.getId()>0){
            serverSlaveMapper.updateByPrimaryKeySelective(serverSlave);
        }else {
            serverSlaveMapper.insertSelective(serverSlave);
        }
        redisManager.hset(RedisConstant.H_SERVER_SLAVE_INFO,serverSlave.getServerName(),serverSlave.getServerSlaveName());
        return true;
    }

    /**
     * 保存从服务器信息
     * @param serverSlave
     * @return
     */
    public boolean insertSelective(FuComServerSlave serverSlave){
        if(serverSlave==null||serverSlave.getServerName()==null||serverSlave.getServerSlaveName()==null){
            log.error("保存从服务器信息,参数为空！");
            throw  new DataConflictException("保存从服务器信息,参数为空！");
        }
        serverSlaveMapper.insertSelective(serverSlave);
        redisManager.hset(RedisConstant.H_SERVER_SLAVE_INFO,serverSlave.getServerName(),serverSlave.getServerSlaveName());
        return true;
    }

    /**
     * 根据主服务器名称 查找从服务器名称
     * @param serverName
     * @return
     */
    public FuComServerSlave getSlaveByServerName(String serverName){
        if(StringUtils.isEmpty(serverName)){
            log.error("根据主服务器名称 查找从服务器名称失败,参数为空！");
            throw  new DataConflictException("根据主服务器名称 查找从服务器名称失败,参数为空！");
        }
        Wrapper<FuComServerSlave> wrapper=new EntityWrapper<>();
        wrapper.eq(FuComServerSlave.SERVER_NAME,serverName);
        FuComServerSlave serverSlave= selectOne(wrapper);
        return serverSlave;
    }

}
