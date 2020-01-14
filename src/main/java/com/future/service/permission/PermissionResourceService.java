package com.future.service.permission;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.future.common.constants.CommonConstant;
import com.future.common.enums.GlobalResultCode;
import com.future.common.enums.RmsResultCode;
import com.future.common.exception.BusinessException;
import com.future.common.helper.PageInfoHelper;
import com.future.common.util.RequestContextHolderUtil;
import com.future.common.util.StringUtils;
import com.future.common.util.TreeBuilder;
import com.future.entity.permission.FuPermissionProject;
import com.future.entity.permission.FuPermissionResource;
import com.future.entity.permission.FuPermissionRoleResource;
import com.future.mapper.permission.FuPermissionResourceMapper;
import com.future.pojo.bo.AdminInfo;
import com.future.pojo.bo.Node;
import com.future.pojo.bo.permission.FuPermissionResourceBO;
import com.future.pojo.vo.permission.FuPermissionResourceVO;
import com.future.pojo.vo.permission.FuPermissionRoleVO;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


/**
 * 权限资源信息模块业务实现类
 *
 * @author Admin
 * @version: 1.0
 */
@Service
@Slf4j
public class PermissionResourceService extends ServiceImpl<FuPermissionResourceMapper, FuPermissionResource> {

    @Autowired
    private PermissionUserProjectService permissionUserProjectService;
    @Autowired
    private FuPermissionResourceMapper fuPermissionResourceMapper;
    @Autowired
    private PermissionRoleService permissionRoleService;
    @Autowired
    private PermissionRoleResourceService permissionRoleResourceService;

    /**
     * 注入超级管理员数据
     */
    @Value("${SUPER_ADMINISTRATOR}")
    private String superAdministrator;

    /**
     * 新增权限资源信息
     *
     * @param fuPermissionResourceBO 保存的数据信息
     */
    @Transactional(rollbackFor = Exception.class)
    public int save(FuPermissionResourceBO fuPermissionResourceBO) {
        //验证参数对象是否为空
        if (fuPermissionResourceBO == null || StringUtils.isEmpty(fuPermissionResourceBO.getResName())) {
            throw new BusinessException(GlobalResultCode.PARAM_NULL_POINTER);
        }
        //系统默认为0
        if(fuPermissionResourceBO.getProjKey() == null){
            fuPermissionResourceBO.setProjKey(0);
        }
        //父类节点ID默认为0
        if (fuPermissionResourceBO.getResPid() == null) {
            fuPermissionResourceBO.setResPid(0);
        }
        //查询是否有重复值，相同父节点下不能存在同名的节点
        List<FuPermissionResource> result = selectList(new EntityWrapper<FuPermissionResource>()
                .eq(FuPermissionResource.RES_NAME, fuPermissionResourceBO.getResName())
                .eq(FuPermissionResource.RES_PID, fuPermissionResourceBO.getResPid()));
        if (StringUtils.isNotEmpty(result)) {
            throw new BusinessException(RmsResultCode.PERMISSION_RESOURCE_DATA_IS_EXIST);
        }

        //保存数据
        FuPermissionResource param = FuPermissionResourceBO.boToModel(fuPermissionResourceBO);
        //获取当前登录用户信息
        AdminInfo user = RequestContextHolderUtil.getAdminInfo();
        //TODO:测试数据
        user = new AdminInfo();
        user.setAdminName("test");
        param.setCreater(user.getAdminName());
        int isSuccess = fuPermissionResourceMapper.insert(param);

        if (isSuccess < 0) {
            throw new BusinessException(RmsResultCode.PERMISSION_RESOURCE_DATA_SAVE_FAILURE);
        }

        return isSuccess;
    }

    /**
     * 通过主键更新权限资源信息
     *
     * @param fuPermissionResourceBO 更新的数据信息
     */
    @Transactional(rollbackFor = Exception.class)
    public void modify(FuPermissionResourceBO fuPermissionResourceBO) {
        //验证参数对象是否为空
        if (fuPermissionResourceBO == null) {
            throw new BusinessException(GlobalResultCode.PARAM_NULL_POINTER);
        }
        if (fuPermissionResourceBO.getId() == null) {
            throw new BusinessException(RmsResultCode.PERMISSION_RESOURCE_INDEX_IS_NULL);
        }
        //保存数据
        FuPermissionResource param = FuPermissionResourceBO.boToModel(fuPermissionResourceBO);
        int isSuccess = fuPermissionResourceMapper.updateById(param);

        if (isSuccess < 0) {
            throw new BusinessException(RmsResultCode.PERMISSION_RESOURCE_DATA_UPDATE_FAILURE);
        }
    }

    /**
     * 权限资源信息分页查询，超级管理员查看所有，普通用户查看所管理的权限资源数据
     *
     * @param fuPermissionResourceBO 查询条件
     * @param helper               分页条件
     * @return 分页对象
     */
    public Page<FuPermissionResourceVO> findPageList(FuPermissionResourceBO fuPermissionResourceBO, PageInfoHelper helper) {
        //验证参数对象是否为空
        /*if (fuPermissionResourceBO == null) {
            throw new BusinessException(GlobalResultCode.PARAM_NULL_POINTER);
        }*/

        //获取当前登录用户信息
        AdminInfo user = RequestContextHolderUtil.getAdminInfo();
        //TODO:测试数据
        user = new AdminInfo();
        user.setAdminId(11);
        //获取配置文件中超级管理员，判断当前登录用户是否为超级管理员,true为超管
        String[] superAdministrators = superAdministrator.split(",");
        boolean contains = false;
        if (user != null && superAdministrators != null) {
            contains = Arrays.asList(superAdministrators).contains(user.getAdminId().toString());
        }

        //由于分页组件默认执行startPage的第一个sql，因此提前单独执行
        List<Integer> porjKeys = null;
        if (!contains) {
            //获取用户所管理的工程项目KEY集合
            porjKeys = permissionUserProjectService.findPorjKeysByUserId(user.getAdminId());
        }

        //设置分页信息
        if (helper == null) {
            helper = new PageInfoHelper();
        }
        Page<FuPermissionResourceVO> resourceVOS =PageHelper.startPage(helper.getPageNo(), helper.getPageSize());
        List<FuPermissionResourceVO> result = Lists.newArrayList();
        //超级管理员用户查看全部内容，其他用户获取当前有拥有的工程项目
        if (contains) {
            result = fuPermissionResourceMapper.selectPageList(fuPermissionResourceBO, null);
        } else {
            /*if (porjKeys != null && porjKeys.size() > 0) {
                result = fuPermissionResourceMapper.selectPageList(fuPermissionResourceBO, porjKeys);
            }*/
            result = fuPermissionResourceMapper.selectPageList(fuPermissionResourceBO, porjKeys);
        }
        System.out.println(resourceVOS.getTotal());
        return resourceVOS;
    }

    /**
     * 查詢所有权限资源信息
     * @return
     */
    public List<FuPermissionResource> findAll(){
        /*查找所有可用的资源情况*/
        List<FuPermissionResource> result = selectList(new EntityWrapper<FuPermissionResource>()
                .eq(FuPermissionResource.RES_STATUS, CommonConstant.COMMON_STATE_USABLE));
        return result;
    }

    /**
     * 通过主键获取权限资源信息
     *
     * @param id 主键ID
     * @return 权限资源信息
     */
    public FuPermissionResourceVO findInfo(Integer id) {
        //验证必要参数值是否为空
        if (id == null) {
            throw new BusinessException(GlobalResultCode.PARAM_NULL_POINTER);
        }
        FuPermissionResource permissionResource = fuPermissionResourceMapper.selectById(id);
        if (permissionResource == null) {
            throw new BusinessException(GlobalResultCode.RESULE_DATA_NONE);
        }
        return FuPermissionResourceVO.modelToVO(permissionResource);
    }

    /**
     * 通过权限资源主键集合删除权限资源信息以及角色权限资源关系数据
     *
     * @param ids 权限资源ID集合
     */
    @Transactional(rollbackFor = Exception.class)
    public void remove(String ids) {
        //验证必要参数值是否为空
        if (StringUtils.isEmpty(ids)) {
            throw new BusinessException(GlobalResultCode.PARAM_NULL_POINTER);
        }

        String[] strArr = ids.split(",");
        List<String> pids = Arrays.asList(strArr);

        List<Node> treeNode = fuPermissionResourceMapper.selectTreeNodeByProjKeys(null);

        TreeBuilder builder = new TreeBuilder(treeNode);
        List<String> childNodes = builder.getChildNodes(pids);

        List<Integer> paramList = Lists.newArrayList();
        for (int i = 0; i < childNodes.size(); i++) {
            paramList.add(Integer.valueOf(childNodes.get(i)));
        }

        int isSuccess = fuPermissionResourceMapper.deleteBatchIds(paramList);

        if (isSuccess < 0) {
            throw new BusinessException(RmsResultCode.PERMISSION_RESOURCE_DATA_DEL_FAILURE);
        }
        //删除角色与权限资源关系
        boolean b = permissionRoleResourceService.delete(new EntityWrapper<FuPermissionRoleResource>().in(FuPermissionRoleResource.RES_ID, paramList));
        if (!b) {
            throw new BusinessException(RmsResultCode.PERMISSION_ROLE_RESOURCE_DATA_DEL_FAILURE);
        }
        //获取当前登录用户信息
        AdminInfo user = RequestContextHolderUtil.getAdminInfo();
        //TODO:测试数据
        user = new AdminInfo();
        user.setAdminLogin("test");
        log.warn("user:{},remove permission resource:[{}]", user.getAdminLogin(), ids);
    }

    /**
     * 权限树信息查询
     *
     * @param user 当前登录用户信息
     * @return 权限树信息字符串
     */
    public String findTreeNode(AdminInfo user) {
        //验证参数对象是否为空
        if (user == null) {
            throw new BusinessException(GlobalResultCode.PARAM_NULL_POINTER);
        }
        Integer userId = user.getAdminId();
        //验证必要参数值是否为空
        if (userId == null) {
            throw new BusinessException(GlobalResultCode.PARAM_NULL_POINTER);
        }

        //获取配置文件中超级管理员，判断当前登录用户是否为超级管理员,true为超管
        String[] superAdministrators = superAdministrator.split(",");
        boolean contains = false;
        if (user != null && superAdministrators != null) {
            contains = Arrays.asList(superAdministrators).contains(userId.toString());
        }

        //菜单树结构
        List<Node> nodes = Lists.newArrayList();
        //超级管理员用户查看全部内容，其他用户获取当前有拥有的工程项目
        if (contains) {
            nodes = fuPermissionResourceMapper.selectTreeNodeByProjKeys(null);
        } else {
            //获取用户所管理的工程项目KEY集合
            List<Integer> projKeys = permissionUserProjectService.findPorjKeysByUserId(userId);
            if (StringUtils.isNotEmpty(projKeys)) {
                nodes = fuPermissionResourceMapper.selectTreeNodeByProjKeys(projKeys);
            }
        }
        //构建树形结构
        String jsonTree;
        jsonTree = new TreeBuilder().buildTree(nodes);
        return jsonTree;
    }

    /**
     * 角色功能授权权限树生成
     * <p>menu:</p>
     *
     * @param user 当前登录用户信息
     * @return 权限树数据
     */
    public Map<String, Object> findAuthorizationTree(AdminInfo user, Integer roleId) {
        //验证参数对象是否为空
        if (user == null) {
            throw new BusinessException(GlobalResultCode.PARAM_NULL_POINTER);
        }

        //获取配置文件中超级管理员，判断当前登录用户是否为超级管理员,true为超管
        String[] superAdministrators = superAdministrator.split(",");
        boolean contains = false;
        if (user != null && superAdministrators != null) {
            contains = Arrays.asList(superAdministrators).contains(user.getAdminId().toString());
        }

        //声明权限结构树
        List<Node> permenuList;

        //权限菜单树字符串
        String menuStr = "";

        List<Integer> projKeys = Lists.newArrayList();
        // 超级管理员无工程项目KEY约束
        if (!contains) {
            //通过角色主键获取关联的工程项目KEY
            FuPermissionRoleVO fuPermissionRoleVO = permissionRoleService.findInfo(roleId);
            projKeys.add(fuPermissionRoleVO.getProjKey());
        }
        //通过工程项目KEY集合查询树形节点集合
        permenuList = fuPermissionResourceMapper.selectTreeNodeByProjKeysSort(projKeys);
        //构建树形结构
        menuStr = new TreeBuilder().buildTree(permenuList);
        JSONArray array = JSONArray.parseArray(menuStr);
        JSONObject jsonObject = new JSONObject();
        for (int i = 0; i < array.size(); i++) {
            jsonObject = array.getJSONObject(i);
        }
        Map<String, Object> result = new HashMap<>();
        result.put("menu", jsonObject);
        //获取当前角色权限
        List<String> resIds = permissionRoleResourceService.findResIdsByRoleId(roleId);
        result.put("resIds", resIds);
        return result;
    }


    /**
     * 通过工程项目KEY集合查询权限资源信息集合
     *
     * @param projKeys 工程项目KEY集合
     * @return 权限资源信息集合
     */
    public List<FuPermissionResource> findByProjKeys(List<Integer> projKeys) {
        //验证必要参数值是否为空
        if (projKeys == null || projKeys.size() <= 0) {
            throw new BusinessException(GlobalResultCode.PARAM_NULL_POINTER);
        }
        List<FuPermissionResource> result = fuPermissionResourceMapper.selectByProjKeys(projKeys);
        return result;
    }

    /**
     * 通过工程项目KEY集合查询相关联的权限资源ID集合
     *
     * @param projKeys 工程项目KEY集合
     * @return 权限资源ID集合
     */
    public List<Integer> findResIdsByKeys(List<Integer> projKeys) {
        //验证必要参数值是否为空
        if (projKeys == null || projKeys.size() <= 0) {
            throw new BusinessException(GlobalResultCode.PARAM_NULL_POINTER);
        }
        List<Integer> result = fuPermissionResourceMapper.selectResIdsByProjKeys(projKeys);
        return result;
    }

    /**
     * 通过角色ID查询所关联的权限资源集合
     *
     * @param roleId 角色ID
     * @return 权限资源集合
     */
    public List<FuPermissionResourceVO> findByRoleId(Integer roleId) {
        //验证必要参数值是否为空
        if (roleId == null) {
            throw new BusinessException(GlobalResultCode.PARAM_NULL_POINTER);
        }
        List<FuPermissionResourceVO> result = fuPermissionResourceMapper.selectByRoleId(roleId);
        return result;
    }


    /**
     * 查找权限树
     * <p>menu:</p>
     * @return 权限树数据
     */
    public String findResourceTree() {
        //声明权限结构树
        List<Node> permenuList=new ArrayList<>();

        /*获取所有权限信息*/
        List<FuPermissionResource> resources=findAll();
        /*根据角色权限 筛选 角色资源*/
        for(FuPermissionResource res:resources){
            Node node = new Node();
            BeanUtils.copyProperties(res,node);
            node.setId(res.getId().toString());
            node.setResPid(res.getResPid().toString());
            node.setResStatus(res.getResStatus().toString());
            permenuList.add(node);
        }

        //构建树形结构
        String menuStr = new TreeBuilder().buildTree(permenuList);
        JSONArray array = JSONArray.parseArray(menuStr);

        return array.toJSONString();
    }
}