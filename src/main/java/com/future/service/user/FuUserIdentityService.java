package com.future.service.user;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.future.common.exception.DataConflictException;
import com.future.entity.user.FuUserIdentity;
import com.future.mapper.user.FuUserIdentityMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class FuUserIdentityService extends ServiceImpl<FuUserIdentityMapper, FuUserIdentity> {

    Logger log= LoggerFactory.getLogger(FuUserIdentity.class);
    @Autowired
    FuUserIdentityMapper fuUserIdentityMapper;

    /**
     * 用户身份保存
     * @param fuUserIdentity
     */
    public void insertSelective(FuUserIdentity fuUserIdentity){
        if(fuUserIdentity!=null){
            fuUserIdentityMapper.insertSelective(fuUserIdentity);
        }else {
            log.error("用户身份保存,传入参数为空！");
            throw new DataConflictException("用户身份保存,传入参数为空！");
        }
    }

    /**
     * 用户身份修改
     * @param fuUserIdentity
     */
    public void updateByPrimaryKeySelective(FuUserIdentity fuUserIdentity){
        if(fuUserIdentity!=null||fuUserIdentity.getId()==0){
            fuUserIdentityMapper.updateByPrimaryKeySelective(fuUserIdentity);
        }else {
            log.error("用户身份修改,传入参数为空！");
            throw new DataConflictException("用户身份保存,传入参数为空！");
        }
    }

    /**
     * 根据用户 ID查询用户身份信息
     * @param userId
     * @return
     */
    public List<FuUserIdentity> selectByUserId(Integer userId){
        if(userId==null||userId==0){
            log.error("根据用户ID查询用户身份信息,传入参数为空！");
            throw new DataConflictException("根据用户ID查询用户身份信息,传入参数为空！");
        }
        Wrapper<FuUserIdentity> wrapper=new EntityWrapper<>();
        wrapper.eq(FuUserIdentity.USER_ID,userId);

        return selectList(wrapper);
    }

    /**
     * 根据条件查询用户身份信息
     * @param userId
     * @return
     */
    public FuUserIdentity selectByCondition(Integer userId, Integer identity){
        if(userId==null||userId==0||identity==null||identity==0){
            log.error("根据条件查询用户身份信息,传入参数为空！");
            throw new DataConflictException("根据条件查询用户身份信息,传入参数为空！");
        }
        Wrapper<FuUserIdentity> wrapper=new EntityWrapper<>();
        wrapper.eq(FuUserIdentity.USER_ID,userId);
        wrapper.eq(FuUserIdentity.IDENTITY,identity);

        return selectOne(wrapper);
    }

    /**
     * 删除用户身份
     * @param userId
     * @param identity
     */
    public void removeIdentity(Integer userId,Integer identity){
        if(userId==null||identity==null||userId==0||identity==0){
            log.error("删除用户身份,传入参数为空！");
            throw new DataConflictException("删除用户身份,传入参数为空！");
        }
        Wrapper<FuUserIdentity> wrapper=new EntityWrapper<>();
        wrapper.eq(FuUserIdentity.USER_ID,userId);
        wrapper.eq(FuUserIdentity.IDENTITY,identity);
        fuUserIdentityMapper.delete(wrapper);
    }

    /* 1.用户保存(普通用户不保存身份信息) 2.代理申请 代理升级，3 信号源申请 删除，4 管理员设置*/
}
