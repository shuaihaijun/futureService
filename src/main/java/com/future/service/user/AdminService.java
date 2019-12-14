package com.future.service.user;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.future.common.constants.CommonConstant;
import com.future.common.constants.UserConstant;
import com.future.common.enums.GlobalResultCode;
import com.future.common.enums.ResultCode;
import com.future.common.enums.UserResultCode;
import com.future.common.exception.*;
import com.future.common.result.Result;
import com.future.common.result.ResultMsg;
import com.future.common.util.CommonUtil;
import com.future.entity.order.FuOrderCustomer;
import com.future.entity.user.FuUser;
import com.future.mapper.user.FuUserMapper;
import com.future.pojo.bo.order.UserMTAccountBO;
import com.future.service.account.FuAccountMtSevice;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.ObjectUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AdminService extends ServiceImpl<FuUserMapper,FuUser> {

    Logger log= LoggerFactory.getLogger(AdminService.class);

    @Autowired
    FuUserMapper fuUserMapper;
    @Autowired
    FuAccountMtSevice fuAccountMtSevice;

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
        //(0 未审核，1 正常，2 待审核，3 删除）
        if(fuUser.getUserState()>1){
            /*用户状态异常*/
            throw new BusinessException(UserResultCode.USER_STATE_EXCEPTION);
        }
        /*返回数据填充*/
        Map userMap=new HashMap();
        userMap.put("userId",fuUser.getId());
        userMap.put("username",fuUser.getUsername());
        userMap.put("refName",fuUser.getRefName());
        userMap.put("realName",fuUser.getRealName());
        userMap.put("userType",fuUser.getUserType());
        userMap.put("userState",fuUser.getUserState());

        resultMap.put("code",GlobalResultCode.SUCCESS.code());
        resultMap.put("msg",GlobalResultCode.SUCCESS.message());
        resultMap.put("data",JSONObject.toJSON(userMap));
        return resultMap;
    }

    /**
     * 注册用户信息
     * @param userJson
     * @return
     */
    public Map registered(JSONObject userJson){

        if(userJson==null||userJson.toJSONString().equalsIgnoreCase("")){
            log.error("保存用户信息,获取参数为空！");
            throw new ParameterInvalidException("保存用户信息,获取参数为空！");
        }
        /*校验*/
        if(!userJson.getString("password").equals(userJson.getString("password2"))){
            log.error("保存用户信息,两次输入的密码不一致！");
            throw new ParameterInvalidException("保存用户信息,两次输入的密码不一致！");
        }

        Map registeredInfo=new HashMap();
        try{
            FuUser fuUser=JSONObject.parseObject(userJson.toJSONString(),FuUser.class);
            /*验证*/
            if(ObjectUtils.isEmpty(fuUser)){
                throw new ParameterInvalidException(GlobalResultCode.PARAM_NULL_POINTER);
            }
            if(StringUtils.isEmpty(fuUser.getUsername())
                    ||StringUtils.isEmpty(fuUser.getPassword())
                    ||StringUtils.isEmpty(fuUser.getEmail())
                    ||StringUtils.isEmpty(fuUser.getMobile())){
                throw new ParameterInvalidException(GlobalResultCode.PARAM_NULL_POINTER);
            }

            /*校验改用户是否已存在 此处可从redis中查询*/
            FuUser eUser=fuUserMapper.selectByUsername(fuUser.getUsername());
            /*此处还需判断用户状态（123）*/
            if(!ObjectUtils.isEmpty(eUser)){
                log.warn("注册用户信息 , 该用户已存在，username:"+fuUser.getUsername());
                throw new DataConflictException(UserResultCode.LOGINISEXIST);
            }

            /*校验推荐人是否存在*/
            FuUser introducer=fuUserMapper.selectByPrimaryKey(fuUser.getIntroducer());
            if(introducer==null){
                log.warn("注册用户信息 , 介绍人不存在！");
                throw new DataConflictException("介绍人不存在！");
            }

            /*密码加密*/
            fuUser.setPassword(DigestUtils.md5DigestAsHex(fuUser.getPassword().getBytes()));

            /*保存数据*/
            int userId= fuUserMapper.insertSelective(fuUser);
            /*跟新介绍人 信息*/
            fuUserMapper.updateByPrimaryKeySelective(introducer);


            /*更新缓存*/

//            FuUser user=fuUserMapper.selectByUsername(fuUser.getUsername());
            //*返回数据填充/*
            Map userMap=new HashMap();
            userMap.put("userId",userId);
            /*userMap.put("username",fuUser.getUsername());
            userMap.put("refName",fuUser.getRefName());
            userMap.put("realName",fuUser.getRealName());
            userMap.put("userType",fuUser.getUserType());
            userMap.put("userState",fuUser.getUserState());*/

            registeredInfo.put("code",GlobalResultCode.SUCCESS.code());
            registeredInfo.put("msg",GlobalResultCode.SUCCESS.message());
            registeredInfo.put("data",JSONObject.toJSON(userMap));

        }catch (Exception e){
            log.error(e.getMessage(),e);
            throw new BusinessException(e);
        }
        return registeredInfo;
    }


    /**
     * 修改用户信息
     * @param fuUser
     * @return
     */
    public void updateAdmin(FuUser fuUser){

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
        try {
            fuUserMapper.updateByPrimaryKeySelective(fuUser);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            throw new BusinessException(e);
        }
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
     * 根据用户username 或 id 查找用户信息
     * @param dataJson
     * @return
     */
    public FuUser getUserByIdOrName(JSONObject dataJson){
        /*判断查询条件*/
        if(dataJson == null || dataJson.toJSONString().equalsIgnoreCase("")){
            log.error("查找用户信息,获取参数为空！");
            throw new ParameterInvalidException("查找用户信息,获取参数为空！");
        }

        String username=dataJson.getString("username");
        int userId=dataJson.getInteger("id");

        FuUser user=new FuUser();
        if(userId>0){
            user=fuUserMapper.selectByPrimaryKey(userId);
        }else if(!StringUtils.isEmpty(username)){
            user=fuUserMapper.selectByUsername(username);
        }else {
            log.error("查找用户信息,参数错误！");
            throw new ParameterInvalidException("查找用户信息,参数错误！");
        }
        return user;
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
     * 查询用户列表
     * @param condition
     * @return
     */
    public Page<FuUser> queryUserList(JSONObject condition){
        /*判断查询条件*/
        if(condition == null || condition.toJSONString().equalsIgnoreCase("")){
            log.error("查询用户列表,获取参数为空！");
            throw new ParameterInvalidException("查询用户列表,获取参数为空！");
        }
        /*判断权限*/
        /*String operUserId=condition.getString("operUserId");
        if(StringUtils.isEmpty(operUserId)){
            log.error("查询用户列表,用户未登录！");
            throw new ParameterInvalidException("查询用户列表,获取参数为空！");
        }*/

        int pageSize=0;
        int pageNum=20;
        if(!com.future.common.util.StringUtils.isEmpty(condition.getString("pageSize"))){
            pageSize=Integer.parseInt(condition.getString("pageSize"));
        }
        if(!com.future.common.util.StringUtils.isEmpty(condition.getString("pageNum"))){
            pageNum=Integer.parseInt(condition.getString("pageNum"));
        }
        Page page=new Page();
        page.setSize(pageSize);
        page.setCurrent(pageNum);
        EntityWrapper<FuUser> wrapper=new EntityWrapper<FuUser>();
        if(condition.getString("userId")!=null
            &&!condition.getString("userId").equals("")){
            wrapper.eq(FuUser.USER_ID,condition.getString("userId"));
        }
        if(condition.getString("username")!=null
                &&!condition.getString("username").equals("")){
            wrapper.eq(FuUser.USER_NAME,condition.getString("username"));
        }
        if(condition.getString("userType")!=null
                &&!condition.getString("userType").equals("")){
            wrapper.eq(FuUser.USER_TYPE,condition.getString("userType"));
        }
        if(condition.getString("userState")!=null
                &&!condition.getString("userState").equals("")){
            wrapper.eq(FuUser.USER_STATE,condition.getInteger("userState"));
        }
        if(condition.getString("isVerified")!=null
                &&!condition.getString("isVerified").equals("")){
            wrapper.eq(FuUser.IS_VERIFIED,condition.getString("isVerified"));
        }
        if(condition.getString("isAccount")!=null
                &&!condition.getString("isAccount").equals("")){
            wrapper.eq(FuUser.IS_ACCOUNT,condition.getString("isAccount"));
        }
        if(condition.getString("introducer")!=null
                &&!condition.getString("introducer").equals("")){
            wrapper.eq(FuUser.INTRODUCER,condition.getString("introducer"));
        }
        Page<FuUser> pageInfo=selectPage(page,wrapper);

        return pageInfo;
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


    /**
     *  用户绑定申请审核
     * @param dataJson
     * @return
     */
    public FuUser checkUserBinding(JSONObject dataJson){
        /*判断查询条件*/
        if(dataJson == null || dataJson.toJSONString().equalsIgnoreCase("")){
            log.error("查找用户信息,获取参数为空！");
            throw new ParameterInvalidException("查找用户信息,获取参数为空！");
        }

        String username=dataJson.getString("username");
        int userId=dataJson.getInteger("userId");
        String oper=dataJson.getString("oper");
        if(StringUtils.isEmpty(oper)){
            log.error("查找用户信息,参数错误！");
            throw new ParameterInvalidException("查找用户信息,参数错误！");
        }

        FuUser user=new FuUser();
        if(userId>0){
            user=fuUserMapper.selectByPrimaryKey(userId);
        }else if(!StringUtils.isEmpty(username)){
            user=fuUserMapper.selectByUsername(username);
        }else {
            log.error("查找用户信息,参数错误！");
            throw new ParameterInvalidException("查找用户信息,参数错误！");
        }

        if(oper.equals(CommonConstant.CHECK_YES)){
            //通过
            user.setUserState(UserConstant.USER_STATE_NORMAL);
            user.setIsAccount(CommonConstant.COMMON_YES);
            user.setIsVerified(CommonConstant.COMMON_YES);

            /*给MT账户 分配 URL和 端口*/
            fuAccountMtSevice.checkUserMtAccount(user.getId());

        }else {
            //未通过
            user.setUserState(UserConstant.USER_STATE_UNCHECK);
        }

        fuUserMapper.updateByPrimaryKeySelective(user);

        return user;
    }

    /**
     * 提交用户绑定申请
     * @param dataJson
     * @return
     */
    public FuUser submitUserBinding(JSONObject dataJson){
        /*判断查询条件*/
        if(dataJson == null || dataJson.toJSONString().equalsIgnoreCase("")){
            log.error("提交用户绑定申请,获取参数为空！");
            throw new ParameterInvalidException("提交用户绑定申请,获取参数为空！");
        }

        String username=dataJson.getString("username");
        int userId=dataJson.getInteger("userId");

        /*校验证件*/
        FuUser user=new FuUser();
        if(userId>0){
            user=fuUserMapper.selectByPrimaryKey(userId);
        }else if(!StringUtils.isEmpty(username)){
            user=fuUserMapper.selectByUsername(username);
        }else {
            log.error("提交用户绑定申请,参数错误！");
            throw new ParameterInvalidException("提交用户绑定申请,参数错误！");
        }
        if(StringUtils.isEmpty(user.getIdFront())||StringUtils.isEmpty(user.getIdFront())){
            //未上传用户证件信息
            log.error("提交用户绑定申请,证件信息未上传，请先保存！");
            throw new ParameterInvalidException("提交用户绑定申请,证件信息未上传，请先保存！");
        }

        /*校验MT账户*/
        Map conditonMap=new HashMap();
        /*默认查询主账户号*/
        conditonMap.put("username",username);
        conditonMap.put("isChief",1);
        List<UserMTAccountBO> accouts=  fuAccountMtSevice.getUserMTAccByCondition(conditonMap);
        if(accouts==null || accouts.size()==0){
            //未查找用户MT账户信息
            log.error("提交用户绑定申请,MT账户信息未上传，请先保存！");
            throw new ParameterInvalidException("提交用户绑定申请,MT账户信息未上传，请先保存！");
        }

        /*非管理用户需要填写交易密码*/
        if(user.getUserType()<4 && StringUtils.isEmpty(accouts.get(0).getMtPasswordTrade())){
            //未查找用户MT账户交易密码信息
            log.error("提交用户绑定申请,MT账户交易密码未找到，请先保存！");
            throw new ParameterInvalidException("提交用户绑定申请,MT账户交易密码未找到，请先保存！");
        }

        user.setUserState(UserConstant.USER_STATE_PENDING);

        fuUserMapper.updateByPrimaryKeySelective(user);

        return user;
    }
}
