package com.future.service.permission;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.future.common.enums.GlobalResultCode;
import com.future.common.enums.RmsResultCode;
import com.future.common.exception.BusinessException;
import com.future.common.helper.PageInfoHelper;
import com.future.common.util.RequestContextHolderUtil;
import com.future.common.util.StringUtils;
import com.future.entity.permission.FuPermissionRole;
import com.future.entity.permission.FuPermissionRoleResource;
import com.future.mapper.permission.FuPermissionRoleMapper;
import com.future.pojo.bo.AdminInfo;
import com.future.pojo.bo.BasicBO;
import com.future.pojo.bo.permission.FuPermissionRoleBO;
import com.future.pojo.vo.permission.FuPermissionRoleVO;
import com.future.service.user.UserCommonService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

/**
 * 角色信息模块业务实现类
 *
 * @author Admin
 * @version: 1.0
 */
@Slf4j
@Service
public class PermissionRoleService extends ServiceImpl<FuPermissionRoleMapper, FuPermissionRole> {


    @Autowired
    private PermissionProjectService permissionProjectService;
    @Autowired
    private FuPermissionRoleMapper fuPermissionRoleMapper;
    @Autowired
    private PermissionRoleResourceService permissionRoleResourceService;
    @Autowired
    private PermissionUserProjectService permissionUserProjectService;
    @Autowired
    private PermissionUserRoleService permissionUserRoleService;
    @Autowired
    UserCommonService userCommonService;

    /**
     * 新增角色信息
     * 超级管理员可新增管理员、普通用户
     * 超级管理员可新增特殊角色、普通角色
     * 管理员仅可新增普通用户、普通角色
     *
     * @param fuPermissionRoleBO 保存的数据信息
     */
    @Transactional(rollbackFor = Exception.class)
    public void save(FuPermissionRoleBO fuPermissionRoleBO) {
        //验证参数对象是否为空
        if (fuPermissionRoleBO == null) {
            throw new BusinessException(GlobalResultCode.PARAM_NULL_POINTER);
        }
        //验证必要参数值是否为空
        if (StringUtils.isEmpty(fuPermissionRoleBO.getRoleName()) || fuPermissionRoleBO.getProjKey() == null) {
            throw new BusinessException(GlobalResultCode.PARAM_NOT_COMPLETE);
        }

        //组装保存的角色数据
        FuPermissionRole permissionRole = FuPermissionRoleBO.boToModel(fuPermissionRoleBO);
        permissionRole.setRoleStatus(1);
                if (permissionRole.getRoleLevel() == null) {
            permissionRole.setRoleLevel(3);//级别默认为普通用户
        }
        if (permissionRole.getRoleSign() == null) {
            permissionRole.setRoleSign(2);//级别默认为普通用户
        }
        //获取当前登录用户信息
        AdminInfo user = RequestContextHolderUtil.getAdminInfo();
        //TODO:
        user = new AdminInfo();
        permissionRole.setCreater(user.getAdminName());
        boolean isSuccess = true;
        if (permissionRole.getId()>0) {
            /*修改*/
            isSuccess = updateById(permissionRole);
        }else {
            // 保存
            isSuccess = insert(permissionRole);
        }
        if (!isSuccess) {
            throw new BusinessException(RmsResultCode.PERMISSION_ROLE_DATA_SAVE_FAILURE);
        }

        //角色权限资源复制流程
        List<Integer> roleIds = fuPermissionRoleBO.getRoleIds();
        if (StringUtils.isNotEmpty(roleIds)) {
            Integer roleId = permissionRole.getId();
            //复制角色关联的权限资源
            List<Integer> resIds = permissionRoleResourceService.findResIdByRoleIds(roleIds);
            List<FuPermissionRoleResource> permissionRoleResources = Lists.newArrayList();
            //将权限资源关联到新角色
            for (Integer resId : resIds) {
                FuPermissionRoleResource permissionRoleResource = new FuPermissionRoleResource();
                permissionRoleResource.setRoleId(roleId);
                permissionRoleResource.setResId(resId);
                permissionRoleResources.add(permissionRoleResource);
            }
            boolean isBatchSuccess = permissionRoleResourceService.insertBatch(permissionRoleResources);
            if (!isBatchSuccess) {
                throw new BusinessException(RmsResultCode.PERMISSION_ROLE_DATA_BATCHSAVE_FAILURE);
            }
        }
    }

    /**
     * 通过主键更新角色信息
     *
     * @param fuPermissionRoleBO 更新的数据信息
     */
    @Transactional(rollbackFor = Exception.class)
    public void modify(FuPermissionRoleBO fuPermissionRoleBO) {
        //验证参数对象是否为空
        if (fuPermissionRoleBO == null) {
            throw new BusinessException(GlobalResultCode.PARAM_NULL_POINTER);
        }
        //验证必要参数值是否为空
        if (fuPermissionRoleBO.getId() == null) {
            throw new BusinessException(RmsResultCode.PERMISSION_ROLE_INDEX_IS_NULL);
        }

        //保存数据
        FuPermissionRole permissionRole = FuPermissionRoleBO.boToModel(fuPermissionRoleBO);
        boolean isSuccess = updateById(permissionRole);
        if (!isSuccess) {
            throw new BusinessException(RmsResultCode.PERMISSION_ROLE_DATA_UPDATE_FAILURE);
        }
    }

    /**
     * 通过主键批量删除角色信息以及相关联数据
     *
     * @param ids 工程项目信ID集合
     */
    @Transactional(rollbackFor = Exception.class)
    public void remove(String ids) {
        //验证必要参数值是否为空
        if (StringUtils.isAnyBlank(ids)) {
            throw new BusinessException(GlobalResultCode.PARAM_NULL_POINTER);
        }

        String[] strArr = ids.split(",");
        List<Integer> paramList = Lists.newArrayList();
        for (int i = 0; i < strArr.length; i++) {
            paramList.add(Integer.valueOf(strArr[i]));
        }
        //通过角色ID集合查询角色所关联的人员ID集合
        //角色与用户存在关联关系不能删除
        List<Integer> userIds = permissionUserRoleService.findUserIdsByRoleIds(paramList);
        if (userIds != null && userIds.size() > 0) {
            throw new BusinessException(RmsResultCode.PERMISSION_USER_ROLE_BIND);
        }
        //批量删除角色
        int isSuccess = fuPermissionRoleMapper.deleteByIds(paramList);
        if (isSuccess < 0) {
            throw new BusinessException(RmsResultCode.PERMISSION_ROLE_DATA_DEL_FAILURE);
        }
        //批量删除角色权限资源关联数据
        boolean roleResourcSuccess = permissionRoleResourceService.delete(new EntityWrapper<FuPermissionRoleResource>().in(FuPermissionRoleResource.ROLE_ID, paramList));
        if (!roleResourcSuccess) {
            throw new BusinessException(RmsResultCode.PERMISSION_ROLE_DATA_DEL_FAILURE);
        }
        //获取当前登录用户信息
        AdminInfo user = RequestContextHolderUtil.getAdminInfo();
        //TODO
        user = new AdminInfo();
        user.setAdminLogin("test");
        log.warn("user:{},remove permission role[{}]", user.getAdminLogin(), ids);
    }

    /**
     * 角色信息分页查询，超级管理员查看所有，普通用户查看所管理工程项目数据
     *
     * @param fuPermissionRoleBO 查询条件
     * @param helper           分页条件
     * @return 分页对象
     */
    public PageInfo<FuPermissionRoleVO> findPageList(FuPermissionRoleBO fuPermissionRoleBO, PageInfoHelper helper) {
        //验证参数对象是否为空
        if (fuPermissionRoleBO == null) {
            throw new BusinessException(GlobalResultCode.PARAM_NULL_POINTER);
        }
        //获取当前登录用户信息
//        AdminInfo user = RequestContextHolderUtil.getAdminInfo();
        //获取配置文件中超级管理员，判断当前登录用户是否为超级管理员,true为超管
        boolean contains = userCommonService.isAdministrator(fuPermissionRoleBO.getUserId().toString());

        //由于分页组件默认执行startPage的第一个sql，因此提前单独执行
        List<Integer> porjKeys = null;
        if (!contains) {
            //获取用户所管理的工程项目KEY集合
            //porjKeys = permissionUserProjectService.findPorjKeysByUserId(user.getAdminId());
        }

        // 设置分页信息
        if (helper == null) {
            helper = new PageInfoHelper();
        }
        PageHelper.startPage(helper.getPageNo(), helper.getPageSize());
        List<FuPermissionRoleVO> result = Lists.newArrayList();
        //超级管理员用户查看全部内容，其他用户获取当前有拥有的工程项目
        if (contains) {
            result = fuPermissionRoleMapper.selectAllPageList(fuPermissionRoleBO);
        } else {
            result = fuPermissionRoleMapper.selectPageList(fuPermissionRoleBO, porjKeys);
            /*if (StringUtils.isNotEmpty(porjKeys)) {
                result = fuPermissionRoleMapper.selectPageList(fuPermissionRoleBO, porjKeys);
            }*/
        }

        return new PageInfo(result);
    }

    /**
     * 通过主键获取角色信息
     *
     * @param id 主键ID
     * @return 角色信息
     */
    public FuPermissionRoleVO findInfo(Integer id) {
        //验证必要参数值是否为空
        if (id == null) {
            throw new BusinessException(GlobalResultCode.PARAM_NULL_POINTER);
        }
        FuPermissionRole permissionRole = selectById(id);
        if (permissionRole == null) {
            throw new BusinessException(GlobalResultCode.RESULE_DATA_NONE);
        }
        return FuPermissionRoleVO.modelToVO(permissionRole);
    }

    /**
     * 角色信息分页查询，通过权限资源ID查询所关联的角色信息集合
     *
     * @param basicBO 查询条件（权限资源ID）
     * @return 分页对象
     */
    public PageInfo<FuPermissionRoleVO> findByResId(BasicBO basicBO, PageInfoHelper helper) {
        //验证参数对象是否为空
        if (basicBO == null) {
            throw new BusinessException(GlobalResultCode.PARAM_NULL_POINTER);
        }
        Integer resId = basicBO.getId();
        String roleName = basicBO.getName();
        if (resId == null) {
            throw new BusinessException(GlobalResultCode.PARAM_NULL_POINTER);
        }
        //通过权限资源ID查询所关联的角色ID集合
        List<Integer> roleIds = permissionRoleResourceService.findRoleIdsByResId(resId);

        //设置分页信息
        if (helper == null) {
            helper = new PageInfoHelper();
        }
        Page page = PageHelper.startPage(helper.getPageNo(), helper.getPageSize());
        List<FuPermissionRoleVO> result = Lists.newArrayList();
        PageInfo pageInfo = null;
        if (StringUtils.isNotEmpty(roleIds)) {
            //通过查询条件查询角色信息
            //List<FuPermissionRole> roleList = selectList(new EntityWrapper<FuPermissionRole>().like(!StringUtils.isAnyBlank(roleName), "Role_name", roleName).eq("Role_status", 1).in("Id", roleIds));
            result = fuPermissionRoleMapper.selectByRoleNameAndIds(roleName, roleIds);
        }
        return new PageInfo(result);
    }

    /**
     * 通过查询条件查询角色信息集合
     *
     * @param fuPermissionRoleBO 查询条件（工程项目KEY）
     * @return 角色信息集合
     */
    public List<FuPermissionRoleVO> findByProjKey(FuPermissionRoleBO fuPermissionRoleBO) {
        //验证参数对象是否为空
        if (fuPermissionRoleBO == null) {
            throw new BusinessException(GlobalResultCode.PARAM_NULL_POINTER);
        }
        Integer porjKey = fuPermissionRoleBO.getProjKey();
        List<FuPermissionRoleVO> list = Lists.newArrayList();
        if (porjKey != null) {
            list = fuPermissionRoleMapper.selectByProjKey(porjKey);
        } else {
            //获取当前登录用户信息
            AdminInfo user = RequestContextHolderUtil.getAdminInfo();
            //TODO
            user = new AdminInfo();
            user.setAdminId(1);
            //获取配置文件中超级管理员，判断当前登录用户是否为超级管理员,true为超管
            boolean contains = userCommonService.isAdministrator(user.getAdminId().toString());;
            
            if (contains) {
                list = fuPermissionRoleMapper.selectByProjKey(null);
            } else {
                //获取当前登录用户所管理的工程项目关联的角色
                List<Integer> porjKeys = permissionUserProjectService.findPorjKeysByUserId(user.getAdminId());
                list = fuPermissionRoleMapper.selectByProjKeys(porjKeys);
            }
        }
        return list;
    }

}