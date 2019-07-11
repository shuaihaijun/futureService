package com.future.service.user;

import com.alibaba.druid.util.StringUtils;
import com.future.common.enums.GlobalResultCode;
import com.future.common.enums.ResultCode;
import com.future.common.enums.UserResultCode;
import com.future.common.exception.BusinessException;
import com.future.common.exception.DataNotFoundException;
import com.future.common.exception.ParameterInvalidException;
import com.future.common.exception.UserException;
import com.future.common.result.Result;
import com.future.common.result.ResultMsg;
import com.future.entity.user.FuUser;
import com.future.mapper.user.FuUserMapper;
import com.future.util.CommonUtil;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.ObjectUtils;

import java.util.HashMap;
import java.util.Map;

@Service
public class AdminService {

    Logger log= LoggerFactory.getLogger(AdminService.class);

    @Autowired
    FuUserMapper fuUserMapper;

    /**
     * 通过用户名密码登录
     * @param username
     * @param password
     * @return
     */
    public Map login(String username, String password){

        Map resultMap=new HashMap();

        if(StringUtils.isEmpty(username)||StringUtils.isEmpty(password)){
           throw new BusinessException(GlobalResultCode.PARAM_NULL_POINTER);
        }

        /*这块儿可以从redis里查询*/
        FuUser fuUser=fuUserMapper.selectByUsername(username);
        if(ObjectUtils.isEmpty(fuUser)){
            /*用户不存在*/
            throw new BusinessException(UserResultCode.USER_NOTEXIST_ERROR);
        }

        if(!fuUser.getPassword().equalsIgnoreCase(DigestUtils.md5DigestAsHex(password.getBytes()))){
            /*"用户名或密码错误！")*/
            throw new BusinessException(UserResultCode.USER_PASSWORD_ERROR);
        }

        resultMap.put("code",GlobalResultCode.SUCCESS.code());
        resultMap.put("msg",GlobalResultCode.SUCCESS.message());
        return resultMap;
    }

    /**
     * 保存用户信息
     * @param fuUser
     * @return
     */
    public void save(FuUser fuUser) throws Exception{

        /*验证*/
        if(ObjectUtils.isEmpty(fuUser)){
            throw new ParameterInvalidException(GlobalResultCode.PARAM_NULL_POINTER);
        }
        if(StringUtils.isEmpty(fuUser.getUsername())
                ||StringUtils.isEmpty(fuUser.getPassword())
                ||StringUtils.isEmpty(fuUser.getEmail())
                ||StringUtils.isEmpty(fuUser.getMobile())
                ||StringUtils.isEmpty(fuUser.getRealName())){
            throw new ParameterInvalidException(GlobalResultCode.PARAM_NULL_POINTER);
        }

        /*此处可从redis中查询*/
        FuUser eUser=fuUserMapper.selectByUsername(fuUser.getUsername());
        /*此处还需判断用户状态（123）*/
        if(!ObjectUtils.isEmpty(eUser)){
            throw new DataNotFoundException(UserResultCode.USER_NOTEXIST_ERROR);
        }

        /*密码加密*/
        fuUser.setPassword(DigestUtils.md5DigestAsHex(fuUser.getPassword().getBytes()));

        /*保存数据*/
        fuUserMapper.insertSelective(fuUser);

    }


    /**
     * 修改用户信息
     * @param fuUser
     * @return
     */
    public void updateAdmin(FuUser fuUser) throws Exception{

        /*验证*/
        if(ObjectUtils.isEmpty(fuUser)){
            throw new ParameterInvalidException(GlobalResultCode.PARAM_NULL_POINTER);
        }
        if(StringUtils.isEmpty(fuUser.getUsername())){
            throw new ParameterInvalidException(GlobalResultCode.PARAM_NULL_POINTER);
        }

        /*不能修改密码*/
        if(!StringUtils.isEmpty(fuUser.getPassword())){
            fuUser.setPassword(null);
        }
        /*此处可从redis中查询*/
        FuUser eUser=fuUserMapper.selectByUsername(fuUser.getUsername());
        /*此处还需判断用户状态（123）*/
        if(ObjectUtils.isEmpty(eUser)){
            throw new DataNotFoundException(UserResultCode.USER_NOTEXIST_ERROR);
        }

        /*copy信息*/
        BeanUtils.copyProperties(fuUser,eUser);
        /*修改数据*/
        fuUserMapper.updateByPrimaryKeySelective(fuUser);
    }

    /**
     * 用户修改密码
     * @param username
     * @param oldPass
     * @param newPass
     * @return
     */
    public void updatePassword(String username,String oldPass,String newPass) throws Exception{

        if(StringUtils.isEmpty(username)
                ||StringUtils.isEmpty(oldPass)
                ||StringUtils.isEmpty(newPass)){
            throw new ParameterInvalidException(GlobalResultCode.PARAM_NULL_POINTER);
        }

        /*校验密码格式*/
        if(!CommonUtil.verifyPassword(newPass)){
            throw new UserException("校验密码格式不正确，规则(6-18位/字符与数据同时出现)");
        }

        FuUser fuUser=fuUserMapper.selectByUsername(username);
        if(ObjectUtils.isEmpty(fuUser)){
            throw new DataNotFoundException(UserResultCode.USER_NOTEXIST_ERROR);
        }

        if(!fuUser.getPassword().equalsIgnoreCase(DigestUtils.md5DigestAsHex(oldPass.getBytes()))){
            throw new UserException("旧密码错误！");
        }

        FuUser user=new FuUser();
        user.setId(fuUser.getId());
        user.setPassword(DigestUtils.md5DigestAsHex(newPass.getBytes()));
        /*修改密码*/
        fuUserMapper.updateByPrimaryKeySelective(user);

    }

    /**
     * 根据用户账号查询用户信息
     * @param username
     * @return
     */
    public FuUser findByUsername(String username){
        if(StringUtils.isEmpty(username)){
            log.error("根据用户账号查询用户信息，用户名为空！");
            return null;
        }
        FuUser fuUser=null;
        try {
            fuUser= fuUserMapper.selectByUsername(username);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            return null;
        }
        return fuUser;
    }


    /**
     * 根据状态分页查询用户信息
     * @param userType
     * @param userState
     * @param isAccount
     * @param isVerified
     * @return
     */
    public PageInfo findByCondition(int userType, int userState, int isAccount, int isVerified){
        return null;
    }

}
