package com.future.service.user;

import com.alibaba.druid.util.StringUtils;
import com.future.common.Response;
import com.future.common.ResultMsg;
import com.future.entity.user.FuUser;
import com.future.mapper.user.FuUserMapper;
import com.future.util.CommonUtil;
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
    public Response login(String username, String password){
        Response response=new Response();
        response.setCode(ResultMsg.FAILED.getCode());

        if(StringUtils.isEmpty(username)||StringUtils.isEmpty(password)){
            response.setMsg("用户名或密码为空！");
           return response;
        }

        /*这块儿可以从redis里查询*/
        FuUser fuUser=fuUserMapper.selectByUsername(username);
        if(ObjectUtils.isEmpty(fuUser)){
            response.setMsg("用户不存在！");
            return response;
        }

        if(!fuUser.getPassword().equalsIgnoreCase(DigestUtils.md5DigestAsHex(password.getBytes()))){
            response.setMsg("用户名或密码错误！");
            return response;
        }

        response.setCode(ResultMsg.SUCCESS.getCode());
        response.setMsg("用户名、密码验证通过！");
        return response;
    }

    /**
     * 保存用户信息
     * @param fuUser
     * @return
     */
    public Response save(FuUser fuUser){

        Response response=new Response();
        response.setCode(ResultMsg.FAILED.getCode());

        /*验证*/
        if(ObjectUtils.isEmpty(fuUser)){
            response.setMsg("用户数据为空！");
            return response;
        }
        if(StringUtils.isEmpty(fuUser.getUsername())
                ||StringUtils.isEmpty(fuUser.getPassword())
                ||StringUtils.isEmpty(fuUser.getEmail())
                ||StringUtils.isEmpty(fuUser.getMobile())
                ||StringUtils.isEmpty(fuUser.getRealName())){
            response.setMsg("用户数据不完整！");
            return response;
        }

        try{
            /*此处可从redis中查询*/
            FuUser eUser=fuUserMapper.selectByUsername(fuUser.getUsername());
            /*此处还需判断用户状态（123）*/
            if(!ObjectUtils.isEmpty(eUser)){
                response.setMsg("用户已存在！");
                return response;
            }

            /*密码加密*/
            fuUser.setPassword(DigestUtils.md5DigestAsHex(fuUser.getPassword().getBytes()));

            /*保存数据*/
            fuUserMapper.insertSelective(fuUser);

        }catch (Exception e){
            log.error(e.getMessage(),e);
            response.setMsg("保存用户数据失败！");
            return response;
        }

        response.setCode(ResultMsg.SUCCESS.getCode());
        response.setMsg("保存成功！");
        return response;
    }


    /**
     * 修改用户信息
     * @param fuUser
     * @return
     */
    public Response updateAdmin(FuUser fuUser){

        Response response=new Response();
        response.setCode(ResultMsg.FAILED.getCode());

        /*验证*/
        if(ObjectUtils.isEmpty(fuUser)){
            response.setMsg("用户数据为空！");
            return response;
        }
        if(StringUtils.isEmpty(fuUser.getUsername())){
            response.setMsg("用户数据不完整！");
            return response;
        }

        /*不能修改密码*/
        if(!StringUtils.isEmpty(fuUser.getPassword())){
            response.setMsg("修改用户信息时 不能修改密码！");
            return response;
        }

        try{
            /*此处可从redis中查询*/
            FuUser eUser=fuUserMapper.selectByUsername(fuUser.getUsername());
            /*此处还需判断用户状态（123）*/
            if(ObjectUtils.isEmpty(eUser)){
                response.setMsg("用户不存在！");
                return response;
            }

            /*copy信息*/
            BeanUtils.copyProperties(fuUser,eUser);
            /*修改数据*/
            fuUserMapper.updateByPrimaryKeySelective(fuUser);

        }catch (Exception e){
            log.error(e.getMessage(),e);
            response.setMsg("保存用户数据失败！");
            return response;
        }

        response.setCode(ResultMsg.SUCCESS.getCode());
        response.setMsg("修改成功！");
        return response;
    }

    /**
     * 用户修改密码
     * @param username
     * @param oldPass
     * @param newPass
     * @return
     */
    public Response updatePassword(String username,String oldPass,String newPass){
        Response response=new Response();
        response.setCode(ResultMsg.FAILED.getCode());


        if(StringUtils.isEmpty(username)
                ||StringUtils.isEmpty(oldPass)
                ||StringUtils.isEmpty(newPass)){
            response.setMsg("输入信息不完整！");
            return response;
        }

        /*校验密码格式*/
        if(!CommonUtil.verifyPassword(newPass)){
            response.setMsg("密码格式不正确！ 请输入6-18位数字加字母组合！");
            return response;
        }

        try {
            FuUser fuUser=fuUserMapper.selectByUsername(username);
            if(ObjectUtils.isEmpty(fuUser)){
                response.setMsg("用户名称错误，请输入正确的用户名！");
                return response;
            }

            if(!fuUser.getPassword().equalsIgnoreCase(DigestUtils.md5DigestAsHex(oldPass.getBytes()))){
                response.setMsg("旧密码错误！");
                return response;
            }

            FuUser user=new FuUser();
            user.setId(fuUser.getId());
            user.setPassword(DigestUtils.md5DigestAsHex(newPass.getBytes()));
            /*修改密码*/
            fuUserMapper.updateByPrimaryKeySelective(user);

        }catch (Exception e){
            log.error(e.getMessage(),e);
            response.setMsg("密码修改失败！");
            return response;
        }

        response.setCode(ResultMsg.SUCCESS.getCode());
        response.setMsg("修改成功！");
        return response;
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
}
