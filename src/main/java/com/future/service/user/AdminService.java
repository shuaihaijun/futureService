package com.future.service.user;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.future.common.constants.CommonConstant;
import com.future.common.constants.GlobalConstant;
import com.future.common.constants.RedisConstant;
import com.future.common.constants.UserConstant;
import com.future.common.enums.GlobalResultCode;
import com.future.common.enums.UserResultCode;
import com.future.common.exception.*;
import com.future.common.helper.PageInfoHelper;
import com.future.common.util.CommonUtil;
import com.future.common.util.FileUtil;
import com.future.common.util.RedisManager;
import com.future.common.util.RequestContextHolderUtil;
import com.future.entity.com.FuComAgent;
import com.future.entity.permission.FuPermissionRole;
import com.future.entity.permission.FuPermissionUserProject;
import com.future.entity.permission.FuPermissionUserRole;
import com.future.entity.user.FuUser;
import com.future.mapper.user.FuUserMapper;
import com.future.pojo.bo.AdminInfo;
import com.future.pojo.bo.account.UserMTAccountBO;
import com.future.service.account.FuAccountInfoService;
import com.future.service.account.FuAccountMtService;
import com.future.service.com.FuComAgentService;
import com.future.service.permission.PermissionRoleService;
import com.future.service.permission.PermissionUserProjectService;
import com.future.service.permission.PermissionUserRoleService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import org.springframework.util.ObjectUtils;

import java.util.*;

@Service
public class AdminService extends ServiceImpl<FuUserMapper,FuUser> {

    Logger log= LoggerFactory.getLogger(AdminService.class);

    @Autowired
    FuUserMapper fuUserMapper;
    @Autowired
    FuAccountMtService fuAccountMtService;
    @Autowired
    FuAccountInfoService fuAccountInfoService;
    @Autowired
    PermissionUserRoleService permissionUserRoleService;
    @Autowired
    FuComAgentService fuComAgentService;
    @Autowired
    PermissionUserProjectService permissionUserProjectService;
    @Autowired
    PermissionRoleService permissionRoleService;
    @Autowired
    UserCommonService userCommonService;
    @Autowired
    RedisManager redisManager;
    @Value("${newUserRoleId}")
    public Integer newUserRoleId;

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
            log.error("用户不存在!");
            throw new BusinessException(UserResultCode.USER_NOTEXIST_ERROR);
        }

        if(!fuUser.getPassword().equalsIgnoreCase(DigestUtils.md5DigestAsHex(password.getBytes()))){
            /*"用户名或密码错误！")*/
            throw new BusinessException(UserResultCode.USER_PASSWORD_ERROR);
        }
        //(0 未审核，1 正常，2 待审核，3 删除）
        if(fuUser.getUserState()>2){
            /*用户状态异常*/
            throw new BusinessException(UserResultCode.USER_STATE_EXCEPTION);
        }

        /*设置session 用户+token,回头价格session共享*/
        String token=CommonUtil.getUUID();
        /*AdminInfo adminInfo = new AdminInfo();
        adminInfo.setAdminId(fuUser.getId());
        adminInfo.setAdminLogin(fuUser.getUsername());
        adminInfo.setAdminName(fuUser.getRefName());
        adminInfo.setAdminPassword(fuUser.getPassword());
        *//*adminInfo.setR_id();
        adminInfo.setR_name();*//*
        RequestContextHolderUtil.setAdminInfo(adminInfo);
        RequestContextHolderUtil.setAdmintoken(token);*/
        /*为了解决跨域问题，把token放到redis中*/
        redisManager.hsetExpireFirstTime(token,token,fuUser.getId().toString(), GlobalConstant.TOKEN_TIME_OUT);

        /*返回数据填充*/
        Map userMap=new HashMap();
        userMap.put("userId",fuUser.getId());
        userMap.put("username",fuUser.getUsername());
        userMap.put("refName",fuUser.getRefName());
        userMap.put("realName",fuUser.getRealName());
        userMap.put("userType",fuUser.getUserType());
        userMap.put("userState",fuUser.getUserState());
        userMap.put("token",token);

        resultMap.put("code",GlobalResultCode.SUCCESS.code());
        resultMap.put("msg",GlobalResultCode.SUCCESS.message());
        resultMap.put("data",JSONObject.toJSON(userMap));
        return resultMap;
    }


    /**
     * 通过用户token登录
     * @param token
     * @return
     */
    public Map tokenLogin(String token){

        if(StringUtils.isEmpty(token)){
            throw new DataConflictException(GlobalResultCode.PARAM_NULL_POINTER);
        }

        Map resultMap=new HashMap();

        Object key= redisManager.hget(token,token);
        if(ObjectUtils.isEmpty(key)){
            log.error("登录已过期！");
            throw new BusinessException(GlobalResultCode.LOGIN_PAST);
        }
        Integer userId = Integer.parseInt(String.valueOf(key));

        /*这块儿可以从redis里查询*/
        FuUser fuUser=fuUserMapper.selectByPrimaryKey(userId);
        if(ObjectUtils.isEmpty(fuUser)){
            /*用户不存在*/
            log.error("用户不存在!");
            throw new BusinessException(UserResultCode.USER_NOTEXIST_ERROR);
        }

        //(0 未审核，1 正常，2 待审核，3 删除）
        if(fuUser.getUserState()>UserConstant.USER_STATE_DELETE){
            /*用户状态异常*/
            throw new BusinessException(UserResultCode.USER_STATE_EXCEPTION);
        }

        /*设置session 用户+token,回头价格session共享*/
        /*AdminInfo adminInfo = new AdminInfo();
        adminInfo.setAdminId(fuUser.getId());
        adminInfo.setAdminLogin(fuUser.getUsername());
        adminInfo.setAdminName(fuUser.getRefName());
        adminInfo.setAdminPassword(fuUser.getPassword());
        RequestContextHolderUtil.setAdminInfo(adminInfo);
        RequestContextHolderUtil.setAdmintoken(token);*/

        /*返回数据填充*/
        Map userMap=new HashMap();
        userMap.put("userId",fuUser.getId());
        userMap.put("username",fuUser.getUsername());
        userMap.put("refName",fuUser.getRefName());
        userMap.put("realName",fuUser.getRealName());
        userMap.put("userType",fuUser.getUserType());
        userMap.put("userState",fuUser.getUserState());
        userMap.put("token",token);

        resultMap.put("code",GlobalResultCode.SUCCESS.code());
        resultMap.put("msg",GlobalResultCode.SUCCESS.message());
        resultMap.put("data",JSONObject.toJSON(userMap));
        return resultMap;
    }

    /**
     * 用户注销
     * @param userId
     * @param username
     * @param token
     */
    public void logout(Integer userId, String username,String token){
        if(userId==0){
            log.error("用户数据为空!");
            throw new DataConflictException(GlobalResultCode.PARAM_NULL_POINTER);
        }
        Map resultMap=new HashMap();
        FuUser fuUser=fuUserMapper.selectByPrimaryKey(userId);
        if(ObjectUtils.isEmpty(fuUser)){
            /*用户不存在*/
            log.error("用户不存在!");
            throw new BusinessException(UserResultCode.USER_NOTEXIST_ERROR);
        }

        /*设置session 用户+token,回头价格session共享*/
        RequestContextHolderUtil.removeAdminInfo();
        RequestContextHolderUtil.removeAdmintoken();
        /*为了解决跨域问题，把token放到redis中*/
        if(!StringUtils.isEmpty(token)){
            redisManager.del(token);
        }
    }

    /**
     * 注册用户信息
     * @param userJson
     * @return
     */
    @Transactional(propagation= Propagation.REQUIRED)
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
        if(userJson.get("introducer")==null){
            log.error("保存用户信息,邀请码不能为空！");
            throw new ParameterInvalidException("保存用户信息,邀请码不能为空！");
        }
        if(userJson.get("introducer")!=null
                &&!userJson.getString("introducer").equals("")
                &&!StringUtils.isNumber(userJson.getString("introducer"))){
            log.error("保存用户信息,邀请码错误！");
            throw new ParameterInvalidException("保存用户信息,邀请码错误！");
        }

        Map registeredInfo=new HashMap();
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

        /*if(!CommonUtil.checkName(fuUser.getUsername())){
            log.warn("注册用户信息 ,用户名验证失败！ 必须是6-10位字母、数字、下划线 不能以数字开头");
            throw new DataConflictException("注册用户信息 ,用户名验证失败！ 必须是6-10位字母、数字、下划线 不能以数字开头");
        }*/

        /*校验改用户是否已存在 此处可从redis中查询*/
        FuUser eUser=fuUserMapper.selectByUsername(fuUser.getUsername());
        /*此处还需判断用户状态（123）*/
        if(!ObjectUtils.isEmpty(eUser)){
            log.warn("注册用户信息 , 该用户已存在，username:"+fuUser.getUsername());
            throw new DataConflictException("注册用户信息 , 该用户已存在，username:"+fuUser.getUsername());
        }
        eUser=selectOne(new EntityWrapper<FuUser>().eq(FuUser.EMAIL, fuUser.getEmail()));
        if(!ObjectUtils.isEmpty(eUser)){
            log.warn("注册用户信息 , 邮箱已存在！");
            throw new DataConflictException("注册用户信息 , 邮箱已存在！");
        }
        eUser=selectOne(new EntityWrapper<FuUser>().eq(FuUser.MOBILE, fuUser.getMobile()));
        if(!ObjectUtils.isEmpty(eUser)){
            log.warn("注册用户信息 , 电话已存在！");
            throw new DataConflictException("注册用户信息 , 电话已存在！");
        }

        /*校验推荐人是否存在*/
        FuUser introducer=new FuUser();
        if(fuUser.getIntroducer()!=null && fuUser.getIntroducer()>0){
            introducer=fuUserMapper.selectByPrimaryKey(fuUser.getIntroducer());
            if(introducer==null){
                log.warn("注册用户信息 , 邀请码不存在！");
                    throw new BusinessException(UserResultCode.USER_INTRODUCE_NOTEXIST_ERROR);
            }
        }

        /*密码加密*/
        fuUser.setPassword(DigestUtils.md5DigestAsHex(fuUser.getPassword().getBytes()));

        /*保存数据*/
        int isSuccess= fuUserMapper.insertSelective(fuUser);
        if(isSuccess<1){
            log.warn("注册用户信息 , 注册失败！");
            throw new BusinessException("注册用户信息 , 注册失败！");
        }
        FuUser newUser=fuUserMapper.selectByUsername(fuUser.getUsername());
        if(newUser==null){
            log.warn("注册用户信息 , 注册失败！");
            throw new BusinessException("注册用户信息 , 注册失败！");
        }

        /*设置社区账户*/
        fuAccountInfoService.initAccountInfo(newUser.getId(),newUser.getPassword());

        /*跟新介绍人 信息*/
        if(introducer!=null&&introducer.getId()!=null&&introducer.getId()>0){
            introducer.setRecommend(introducer.getRecommend()+1);
            fuUserMapper.updateByPrimaryKeySelective(introducer);
            if(introducer.getUserType()==UserConstant.USER_TYPE_IB
                    ||introducer.getUserType()==UserConstant.USER_TYPE_MIB
                    ||introducer.getUserType()==UserConstant.USER_TYPE_PIB){
                FuComAgent agent=  fuComAgentService.selectOne(new EntityWrapper<FuComAgent>().eq(FuComAgent.USER_ID,introducer.getId()));
                agent.setAgentNumber(agent.getAgentNumber()+1);
                fuComAgentService.updateById(agent);
            }
        }

        /*设置用户所属资源*/
        FuPermissionUserProject introducerUserProject= permissionUserProjectService.selectOne(new EntityWrapper<FuPermissionUserProject>()
                .eq(FuPermissionUserProject.USER_ID,introducer.getId()));
        FuPermissionUserProject userProject=new FuPermissionUserProject();
        userProject.setUserId(newUser.getId());
        if(introducerUserProject==null){
            userProject.setProjKey(0);
        }else {
            userProject.setProjKey(introducerUserProject.getProjKey());
        }
        userProject.setCreateDate(new Date());
        userProject.setModifyDate(new Date());
        permissionUserProjectService.insert(userProject);


        /*设置普通用户角色*/
        FuPermissionRole defaultRole= permissionRoleService.getDefaultRoleByProject(userProject.getProjKey(),newUser.getUserType());
        FuPermissionUserRole userRole=new FuPermissionUserRole();
        userRole.setUserId(newUser.getId());
        if(defaultRole==null){
            userRole.setRoleId(newUserRoleId);
        }else {
            userRole.setRoleId(defaultRole.getId());
        }
        permissionUserRoleService.insert(userRole);

        /*更新缓存*//*
        String token=CommonUtil.getUUID();
        *//*为了解决跨域问题，把token放到redis中*//*
        redisManager.hset(RedisConstant.H_USER_LOGIN_TOKEN,token,newUser.getId().toString());*/

        //*返回数据填充/*
        Map userMap=new HashMap();
        userMap.put("userId",newUser.getId());
        userMap.put("username",fuUser.getUsername());
        userMap.put("refName",fuUser.getRefName());
        userMap.put("realName",fuUser.getRealName());
        userMap.put("userType",fuUser.getUserType());
        userMap.put("userState",fuUser.getUserState());

        registeredInfo.put("code",GlobalResultCode.SUCCESS.code());
        registeredInfo.put("msg",GlobalResultCode.SUCCESS.message());
        registeredInfo.put("data",JSONObject.toJSON(userMap));

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
        if(StringUtils.isEmpty(fuUser.getUsername()) && fuUser.getId()==0){
            throw new ParameterInvalidException(GlobalResultCode.PARAM_NULL_POINTER);
        }

        /*不能修改密码*/
        if(!StringUtils.isEmpty(fuUser.getPassword())){
            fuUser.setPassword(null);
        }
        /*此处可从redis中查询*/

        FuUser eUser;
        if(fuUser.getId()!=0){
            eUser=fuUserMapper.selectByPrimaryKey(fuUser.getId());
        }else {
            eUser=fuUserMapper.selectByUsername(fuUser.getUsername());
        }
        /*此处还需判断用户状态（123）*/
        if(ObjectUtils.isEmpty(eUser)){
            throw new DataNotFoundException(UserResultCode.USER_NOTEXIST_ERROR);
        }
        /*转换图片相对路径*/
        if(!StringUtils.isEmpty(fuUser.getAvatarUrl())){
            fuUser.setAvatarUrl(FileUtil.getFileRelativePath(fuUser.getAvatarUrl()));
        }
        if(!StringUtils.isEmpty(fuUser.getIdFront())){
            fuUser.setIdFront(FileUtil.getFileRelativePath(fuUser.getIdFront()));
        }
        if(!StringUtils.isEmpty(fuUser.getIdObverse())){
            fuUser.setIdObverse(FileUtil.getFileRelativePath(fuUser.getIdObverse()));
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
    public void updatePassword(Integer userId, String username,String oldPass,String newPass){

        if(StringUtils.isEmpty(username)
                ||StringUtils.isEmpty(oldPass)
                ||StringUtils.isEmpty(newPass)){
            throw new ParameterInvalidException(GlobalResultCode.PARAM_NULL_POINTER);
        }

        /*校验密码格式*/
        if(!CommonUtil.verifyPassword(newPass)){
            throw new UserException("校验密码格式不正确，必须是6-20位的字母、数字、下划线组合！");
        }
        FuUser fuUser=null;
        if(userId!=null && userId!=0){
            fuUser=fuUserMapper.selectByPrimaryKey(userId);
        }else if(!StringUtils.isEmpty(username)){
            fuUser=fuUserMapper.selectByUsername(username);
        }
        if(ObjectUtils.isEmpty(fuUser)){
            throw new DataNotFoundException(UserResultCode.USER_NOTEXIST_ERROR);
        }

        if(!fuUser.getPassword().equalsIgnoreCase(DigestUtils.md5DigestAsHex(oldPass.getBytes()))){
            throw new UserException("原密码错误！");
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
    public Page<FuUser> queryUserList(Map userMap, PageInfoHelper helper){
        /*判断查询条件*/
        if(userMap == null||userMap.get("operUserId")==null){
            log.error("查询用户列表,获取参数为空！");
            throw new ParameterInvalidException("查询用户列表,获取参数为空！");
        }
        /*判断权限*/
        String operUserId=String.valueOf(userMap.get("operUserId"));
        if(StringUtils.isEmpty(operUserId)){
            log.error("查询用户列表,用户未登录！");
            throw new ParameterInvalidException("查询用户列表,获取参数为空！");
        }
        Integer operUserProj=userCommonService.getUserProjKey(Integer.parseInt(operUserId));
        Boolean isProjAdmin=userCommonService.isAdministrator(Integer.parseInt(operUserId),operUserProj);
        if(operUserProj==null){
            log.error("查询用户列表,用户权限有误！");
            throw new ParameterInvalidException("查询用户列表,用户权限有误！");
        }

        if(isProjAdmin&&operUserProj==0){
            /*超管查询*/
            return queryAllUser(userMap,helper);
        }else if(isProjAdmin){
            /*资源组管理员查找*/
            userMap.put("projKey",operUserProj);
            return findUserByCondition(userMap,helper);
        }else {
            /*普通用户查找*/
            userMap.put("userId",operUserId);
            return queryAllUser(userMap,helper);
        }
    }


    /**
     * 全范围查询用户数据（不涉及权限）
     * @param userMap
     * @param helper
     * @return
     */
    public Page<FuUser> queryAllUser(Map userMap, PageInfoHelper helper){
        /*判断查询条件*/
        if(userMap == null||userMap.get("operUserId")==null){
            log.error("查询用户列表,获取参数为空！");
            throw new ParameterInvalidException("查询用户列表,获取参数为空！");
        }

        EntityWrapper<FuUser> wrapper=new EntityWrapper<FuUser>();

        if(userMap.get("userId")!=null
                &&!String.valueOf(userMap.get("userId")).equals("")){
            wrapper.eq(FuUser.USER_ID,userMap.get("userId"));
        }
        if(userMap.get("username")!=null
                &&!String.valueOf(userMap.get("username")).equals("")){
            wrapper.eq(FuUser.USER_NAME,userMap.get("username"));
        }
        if(userMap.get("userType")!=null
                &&!String.valueOf(userMap.get("userType")).equals("")){
            wrapper.eq(FuUser.USER_TYPE,userMap.get("userType"));
        }
        if(userMap.get("userState")!=null
                &&!String.valueOf(userMap.get("userState")).equals("")){
            wrapper.eq(FuUser.USER_STATE,userMap.get("userState"));
//        }else {
//            wrapper.eq(FuUser.USER_STATE,UserConstant.USER_STATE_NORMAL);
        }
        if(userMap.get("isVerified")!=null
                &&!String.valueOf(userMap.get("isVerified")).equals("")){
            wrapper.eq(FuUser.IS_VERIFIED,userMap.get("isVerified"));
        }
        if(userMap.get("isAccount")!=null
                &&!String.valueOf(userMap.get("isAccount")).equals("")){
            wrapper.eq(FuUser.IS_ACCOUNT,userMap.get("isAccount"));
        }
        if(userMap.get("introducer")!=null
                &&!String.valueOf(userMap.get("introducer")).equals("")){
            wrapper.eq(FuUser.INTRODUCER,userMap.get("introducer"));
        }
        wrapper.orderBy("id desc");

        if(helper==null){
            helper=new PageInfoHelper();
        }
        Page<FuUser> userPage= PageHelper.startPage(helper.getPageNo(),helper.getPageSize());
        selectList(wrapper);

        return userPage;
    }


    /**
     * 根据状态分页查询用户信息
     * @param userType
     * @param userState
     * @param isAccount
     * @param isVerified
     * @return
     */
    public Page<FuUser> findUserByCondition(Map userMap, PageInfoHelper helper){
        /*判断查询条件*/
        if(userMap == null||userMap.get("operUserId")==null){
            log.error("查询用户列表,获取参数为空！");
            throw new ParameterInvalidException("查询用户列表,获取参数为空！");
        }
        if(helper==null){
            helper=new PageInfoHelper();
        }
        Page<FuUser> userPage= PageHelper.startPage(helper.getPageNo(),helper.getPageSize());
        fuUserMapper.queryUserListByCondition(userMap);
        return userPage;
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

        if(Integer.parseInt(oper)==CommonConstant.CHECK_YES){
            //通过
            user.setUserState(UserConstant.USER_STATE_NORMAL);
            user.setIsAccount(CommonConstant.COMMON_YES);
            user.setIsVerified(CommonConstant.COMMON_YES);

            /*给MT账户 分配 URL和 端口*/
            fuAccountMtService.checkUserMtAccount(user.getId());

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
        List<UserMTAccountBO> accouts=  fuAccountMtService.getUserMTAccByCondition(conditonMap);
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

    /**
     * 根据用户ID 查询用户介绍人信息
     * @param userId
     * @return
     */
    public FuUser findUserIntroducer(Integer userId){
        /*判断查询条件*/
        if(userId==0){
            log.error(" 查询用户介绍人信息 参数为空！");
            throw new ParameterInvalidException("查询用户介绍人信息 参数为空！");
        }
        return fuUserMapper.findUserIntroducer(userId);
    }

    /**
     * 根据介绍人查询用户列表
     * @param condition
     * @return
     */
    public PageInfo<FuUser> queryAgentUserList(FuUser user, PageInfoHelper helper){
        /*判断查询条件*/
        if(user == null||user.getIntroducer()==null){
            log.error("查询用户列表,获取参数为空！");
            throw new ParameterInvalidException("查询用户列表,获取参数为空！");
        }

        EntityWrapper<FuUser> wrapper=new EntityWrapper<FuUser>();
        if(user.getId()!=null ){
            wrapper.eq(FuUser.USER_ID,user.getId());
        }
        if(user.getUsername()!=null ){
            wrapper.eq(FuUser.USER_NAME,user.getUsername());
        }
        if(user.getUserType()!=null){
            wrapper.eq(FuUser.USER_TYPE,user.getUserType());
        }
        if(user.getUserState()!=null){
            wrapper.eq(FuUser.USER_STATE,user.getUserState());
        }
        if(user.getIsVerified()!=null){
            wrapper.eq(FuUser.IS_VERIFIED,user.getIsVerified());
        }
        if(user.getIsAccount()!=null){
            wrapper.eq(FuUser.IS_ACCOUNT,user.getIsAccount());
        }
        if(user.getIntroducer()!=null){
            wrapper.eq(FuUser.INTRODUCER,user.getIntroducer());
        }

        if(helper==null){
            helper=new PageInfoHelper();
        }
        PageHelper.startPage(helper.getPageNo(),helper.getPageSize());
        List<FuUser> users= selectList(wrapper);

        return new PageInfo<FuUser>(users);
    }


    /**
     * 修改用户介绍人信息
     * @param userId
     * @param introducer
     */
    @Transactional
    public void  updateUserIntroducer(Integer userId,Integer introducer){
        if(userId==null || userId==0|| introducer==null||introducer==0){
            log.error("修改用户介绍人信息,数据为空！");
            throw new DataConflictException(GlobalResultCode.PARAM_VERIFY_ERROR);
        }
        FuUser user=new FuUser();
        user.setId(userId);
        user.setModifyDate(new Date());
        user.setIntroducer(introducer);

        FuUser introduceUser=selectOne(new EntityWrapper<FuUser>().eq(FuUser.USER_ID,introducer));
        if(introduceUser==null){
            log.error("介绍人不存在，请检查数据！");
            throw new DataConflictException("介绍人不存在，请检查数据！");
        }

        /*改变用户所属资源组*/
        /*查询推荐人所属资源组*/
        FuPermissionUserProject introducerProject= permissionUserProjectService.selectOne(new EntityWrapper<FuPermissionUserProject>()
                .eq(FuPermissionUserProject.USER_ID,introducer));
        if(introducerProject==null){
            log.error("介绍人资源组不存在，请检查数据！");
            throw new DataConflictException("介绍人资源组不存在，请检查数据！");
        }

        FuPermissionUserProject userProject= permissionUserProjectService.selectOne(new EntityWrapper<FuPermissionUserProject>()
                .eq(FuPermissionUserProject.USER_ID,userId));
        if(userProject==null){
            userProject=new FuPermissionUserProject();
            userProject.setUserId(userId);
            userProject.setProjKey(introducerProject.getProjKey());
            userProject.setModifyDate(new Date());
            userProject.setCreateDate(new Date());
            permissionUserProjectService.insert(userProject);
        }
        userProject.setModifyDate(new Date());
        userProject.setProjKey(introducerProject.getProjKey());
        permissionUserProjectService.updateById(userProject);

        /*修改用户数据！*/
        fuUserMapper.updateByPrimaryKeySelective(user);
    }
}
