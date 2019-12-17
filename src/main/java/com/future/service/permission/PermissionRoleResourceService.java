package com.future.service.permission;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.future.common.enums.GlobalResultCode;
import com.future.common.exception.BusinessException;
import com.future.common.util.RequestContextHolderUtil;
import com.future.common.util.StringUtils;
import com.future.common.util.TreeBuilder;
import com.future.entity.permission.FuPermissionResource;
import com.future.entity.permission.FuPermissionRoleResource;
import com.future.entity.user.FuUser;
import com.future.mapper.permission.FuPermissionRoleResourceMapper;
import com.future.pojo.bo.AdminInfo;
import com.future.pojo.bo.BasicBO;
import com.future.pojo.bo.Node;
import com.future.pojo.bo.permission.FuPermissionResourceBO;
import com.future.pojo.bo.permission.FuPermissionRoleResourceBO;
import com.future.service.user.AdminService;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 角色权限资源关联信息模块业务实现类
 *
 * @author Admin
 * @version: 1.0
 */
@Service
@Slf4j
public class PermissionRoleResourceService extends ServiceImpl<FuPermissionRoleResourceMapper, FuPermissionRoleResource> {

    @Autowired
    private FuPermissionRoleResourceMapper fuPermissionRoleResourceMapper;
    @Autowired
    private PermissionUserProjectService permissionUserProjectService;
    @Autowired
    PermissionUserRoleService permissionUserRoleService;
    @Autowired
    PermissionResourceService permissionResourceService;

    /**
     * 注入超级管理员数据
     */
    @Value("${SUPER_ADMINISTRATOR}")
    private String superAdministrator;

    /**
     * 新增角色权限关联信息
     * <p>如果仅仅传入角色ID，将删除与角色相关的关联数据</p>
     *
     * @param fuPermissionRoleResourceBO 保存的数据信息
     */
    @Transactional(rollbackFor = Exception.class)
    public void save(FuPermissionRoleResourceBO fuPermissionRoleResourceBO) {
        //验证参数对象是否为空
        if (fuPermissionRoleResourceBO == null) {
            throw new BusinessException(GlobalResultCode.PARAM_NULL_POINTER);
        }
        //验证必要参数值是否为空
        Integer roleId = fuPermissionRoleResourceBO.getRoleId();
        if (roleId == null) {
            throw new BusinessException(GlobalResultCode.PARAM_NOT_COMPLETE);
        }

        // 获取当前用户信息
        /*AdminInfo user = RequestContextHolderUtil.getAdminInfo();
        //获取配置文件中超级管理员，判断当前登录用户是否为超级管理员,true为超管
        String[] superAdministrators = superAdministrator.split(",");
        boolean contains = false;
        if (user != null && superAdministrators != null) {
            contains = Arrays.asList(superAdministrators).contains(user.getAdminId().toString());
        }*/

        //组装批量插入对象
        List<FuPermissionRoleResource> param = null;
        List<Integer> resIds = fuPermissionRoleResourceBO.getResIds();
        if (StringUtils.isNotEmpty(resIds)) {
            param = Lists.newArrayList();
            for (Integer resId : resIds) {
                FuPermissionRoleResource permissionRoleResource = FuPermissionRoleResourceBO.boToModel(fuPermissionRoleResourceBO);
                permissionRoleResource.setResId(resId);
                param.add(permissionRoleResource);
            }
        }

        //超级管理员无工程项目KEY约束
        //先通过角色ID删除再保存
        delete(new EntityWrapper<FuPermissionRoleResource>().eq(FuPermissionRoleResource.ROLE_ID, roleId));

        /*if (contains) {
        } else {
            //获取用户所管理的工程项目KEY列表
            List<Integer> porjKeys = permissionUserProjectService.findPorjKeysByUserId(user.getAdminId());
            if (StringUtils.isNotEmpty(porjKeys)) {
                fuPermissionRoleResourceMapper.deleteByRoleIdProjKeys(roleId.toString(), porjKeys);
            }
        }*/
        if (StringUtils.isNotEmpty(param)) {
            // 保存权限
            boolean isSuccess = insertBatch(param);
            if (!isSuccess) {
                throw new BusinessException(GlobalResultCode.RESULE_DATA_NONE);
            }
        }
    }

    /**
     * 通过角色ID以及权限资源ID删除角色权限资源相关联数据
     *
     * @param bo 删除条件[resID-roleID,resID-roleID,...]
     */
    @Transactional(rollbackFor = Exception.class)
    public void remove(BasicBO bo) throws Exception {
        //验证参数对象是否为空
        if (bo == null) {
            throw new BusinessException(GlobalResultCode.PARAM_NULL_POINTER);
        }
        //验证必要参数值是否为空
        String delIds = bo.getDelIds();
        if (StringUtils.isAnyBlank(delIds)) {
            throw new BusinessException(GlobalResultCode.PARAM_NULL_POINTER);
        }

        String[] ids = delIds.split(",");
        for (int i = 0; i < ids.length; i++) {
            String[] id = ids[i].split("-");
            if (id.length < 2) {
                throw new BusinessException(GlobalResultCode.PARAM_NULL_POINTER);
            }
            FuPermissionRoleResource permissionRoleResource = new FuPermissionRoleResource();
            permissionRoleResource.setResId(Integer.valueOf(id[0]));
            permissionRoleResource.setRoleId(Integer.valueOf(id[1]));
            //通过角色ID以及权限资源ID删除数据
            delete(new EntityWrapper<FuPermissionRoleResource>().eq(FuPermissionRoleResource.RES_ID, Integer.valueOf(id[0])).and().eq(FuPermissionRoleResource.ROLE_ID, Integer.valueOf(id[1])));
        }
    }

    /**
     * 通过角色ID集合查询角色所关联的权限资源ID集合并去重
     *
     * @param roleIds 角色ID集合
     * @return 权限资源ID集合
     */
    public List<Integer> findResIdByRoleIds(List<Integer> roleIds) {
        //验证必要参数值是否为空
        if (StringUtils.isEmpty(roleIds)) {
            throw new BusinessException(GlobalResultCode.PARAM_NULL_POINTER);
        }
        List<Integer> resIds = fuPermissionRoleResourceMapper.selectResIdByRoleIds(roleIds);
        return resIds;
    }

    /**
     * 通过权限资源ID查询所关联的角色ID集合
     *
     * @param resId 权限ID
     * @return 角色ID集合
     */
    public List<Integer> findRoleIdsByResId(Integer resId) {
        //验证必要参数值是否为空
        if (resId == null) {
            throw new BusinessException(GlobalResultCode.PARAM_NULL_POINTER);
        }
        List<Integer> roleIds = fuPermissionRoleResourceMapper.selectRoleIdsByResId(resId);
        return roleIds;
    }

    /**
     * 通过角色ID查询所关联的权限资源ID拼接的字符串
     *
     * @param roleId 角色ID
     * @return 权限ID集合
     */
    public List<String> findResIdsByRoleId(Integer roleId) {
        //验证必要参数值是否为空
        if (roleId == null) {
            throw new BusinessException(GlobalResultCode.PARAM_NULL_POINTER);
        }
        List<String> list = fuPermissionRoleResourceMapper.selectResIdsByRoleId(roleId);
        return list;
    }

    /**
     * 通过角色ID查询所关联的权限资源ID拼接的字符串
     *
     * @param roleId 角色ID
     * @return 权限ID集合
     */
    public List<FuPermissionResourceBO> findResByRoleId(Integer roleId) {
        //验证必要参数值是否为空
        if (roleId == null) {
            throw new BusinessException(GlobalResultCode.PARAM_NULL_POINTER);
        }
        List<FuPermissionResourceBO> list = fuPermissionRoleResourceMapper.findResByRoleId(roleId);
        return list;
    }


    /**
     * 查找角色权限树
     * <p>menu:</p>
     * @param userId 当前登录用户ID
     * @return 权限树数据
     */
    public JSONArray findRoleResourceTree(Integer userId) {

        //验证参数对象是否为空
        if (userId == null) {
            throw new BusinessException(GlobalResultCode.PARAM_NULL_POINTER);
        }
        /*查询用户信息*/
        List<Integer> roles= permissionUserRoleService.findRoleIdsByUserId(userId);

        //获取配置文件中超级管理员，判断当前登录用户是否为超级管理员,true为超管
        String[] superAdministrators = superAdministrator.split(",");
        boolean contains = false;
        if (userId != null && superAdministrators != null) {
            contains = Arrays.asList(superAdministrators).contains(userId.toString());
        }

        //声明权限结构树
        List<Node> permenuList=new ArrayList<>();

        //获取当前角色权限
        List<FuPermissionResourceBO> resourceBOS = new ArrayList<>();

        if(contains){
            /*超级管理员 获取所有权限信息*/
            List<FuPermissionResource> resources=permissionResourceService.findAll();
            /*根据角色权限 筛选 角色资源*/
            for(FuPermissionResource res:resources){
                Node node = new Node();
                BeanUtils.copyProperties(res,node);
                node.setId(res.getId().toString());
                node.setResPid(res.getResPid().toString());
                node.setResStatus(res.getResStatus().toString());
                permenuList.add(node);
            }
        }else {
            // 普通用户 根据权限查询
            for(Integer roleId:roles){
                List<FuPermissionResourceBO> resource=findResByRoleId(roleId);
                resourceBOS.addAll(resource);
            }
            /*根据角色权限 筛选 角色资源*/
            for(FuPermissionResourceBO res:resourceBOS){
                Node node = new Node();
                BeanUtils.copyProperties(res,node);
                node.setId(res.getId().toString());
                node.setResPid(res.getResPid().toString());
                node.setResStatus(res.getResStatus().toString());
                permenuList.add(node);
            }
        }

        //构建树形结构
        String menuStr = new TreeBuilder().buildTree(permenuList);
        JSONArray array = JSONArray.parseArray(menuStr);

        return array;
    }
}