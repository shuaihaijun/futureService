package com.future.service.permission;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.pagination.PageHelper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.future.common.constants.CommonConstant;
import com.future.common.enums.GlobalResultCode;
import com.future.common.enums.RmsResultCode;
import com.future.common.exception.BusinessException;
import com.future.common.exception.RmsrException;
import com.future.common.helper.PageInfoHelper;
import com.future.common.util.*;
import com.future.entity.permission.FuPermissionAdmin;
import com.future.entity.permission.FuPermissionProject;
import com.future.entity.permission.FuPermissionRoleResource;
import com.future.mapper.permission.FuPermissionAdminMapper;
import com.future.mapper.permission.FuPermissionProjectMapper;
import com.future.pojo.bo.AdminInfo;
import com.future.pojo.bo.permission.FuPermissionProjectBO;
import com.future.pojo.vo.permission.FuPermissionProjectVO;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 工程项目信息模块业务实现类
 *
 * @author Admin
 * @version: 1.0
 */
@Service
@Slf4j
public class PermissionProjectService extends ServiceImpl<FuPermissionProjectMapper, FuPermissionProject> {

    @Autowired
    private FuPermissionProjectMapper fuPermissionProjectMapper;
    @Autowired
    private PermissionResourceService permissionResourceService;
    @Autowired
    private PermissionRoleResourceService permissionRoleResourceService;
    @Autowired
    private PermissionUserProjectService permissionUserProjectService;
    @Autowired
    private FuPermissionAdminMapper fuPermissionAdminMapper;

    /**
     * 注入超级管理员数据
     */
    @Value("${SUPER_ADMINISTRATOR}")
    private String superAdministrator;

    /**
     * 新增工程项目信息
     *
     * @param fuPermissionProjectBO 保存的数据信息
     */
    @Transactional(rollbackFor = Exception.class)
    public void save(FuPermissionProjectBO fuPermissionProjectBO) throws Exception {
        //验证参数对象是否为空
        if (fuPermissionProjectBO == null || fuPermissionProjectBO.getProjKey() == null) {
            throw new BusinessException(GlobalResultCode.PARAM_NULL_POINTER);
        }
        //工程项目KEY不能重复
        List<FuPermissionProject> result = selectList(new EntityWrapper<FuPermissionProject>().eq(FuPermissionProject.PROJ_STATUS, 1).eq(FuPermissionProject.PROJ_KEY, fuPermissionProjectBO.getProjKey()));
        if (StringUtils.isNotEmpty(result)) {
            throw new RmsrException(RmsResultCode.PERMISSION_PROJECT_DATA_IS_EXIST);
        }

        //组装并保存数据
        FuPermissionProject proj = FuPermissionProjectBO.boToModel(fuPermissionProjectBO);
        proj.setProjStatus(CommonConstant.COMMON_YES);
        int isSuccess = fuPermissionProjectMapper.insertSelective(proj);
        if (isSuccess<=0) {
            throw new RmsrException(RmsResultCode.PERMISSION_PROJECT_DATA_SAVE_FAILURE);
        }
    }

    /**
     * 通过主键更新工程项目信息
     *
     * @param fuPermissionProjectBO 更新的数据信息
     */
    @Transactional(rollbackFor = Exception.class)
    public void modify(FuPermissionProjectBO fuPermissionProjectBO) throws Exception {
        //验证参数对象是否为空
        if (fuPermissionProjectBO == null) {
            throw new BusinessException(GlobalResultCode.PARAM_NULL_POINTER);
        }
        Integer id = fuPermissionProjectBO.getId();
        Integer projKey = fuPermissionProjectBO.getProjKey();
        //验证必要参数值是否为空
        if (id == null) {
            throw new RmsrException(RmsResultCode.PERMISSION_RESOURCE_INDEX_IS_NULL);
        }
        if (projKey == null) {
            throw new BusinessException(GlobalResultCode.PARAM_NOT_COMPLETE);
        }

        //通过工程KEY查询除本数据记录之外是否存在此KEY的数据
        FuPermissionProject result = selectOne(new EntityWrapper<FuPermissionProject>()
                /*.eq(FuPermissionProject.PROJ_STATUS, 1)*/
                .eq(FuPermissionProject.PROJ_KEY, fuPermissionProjectBO.getProjKey())
                .ne(FuPermissionProject.PROJ_ID, fuPermissionProjectBO.getId()));
        if (result != null) {
            throw new RmsrException(RmsResultCode.PERMISSION_PROJECT_DATA_IS_EXIST);
        }

        //保存数据
        FuPermissionProject proj = FuPermissionProjectBO.boToModel(fuPermissionProjectBO);
        int isSuccess = fuPermissionProjectMapper.updateByPrimaryKeySelective(proj);
        if (isSuccess<=0) {
            throw new RmsrException(RmsResultCode.PERMISSION_PROJECT_DATA_SAVE_FAILURE);
        }
    }

    /**
     * 通过工程项目BO查询工程项目VO分页对象
     *
     * @param fuPermissionProjectBO 工程项目BO
     * @param helper              分页信息
     * @return 工程项目VO分页对象
     */
    public PageInfo<FuPermissionProjectVO> findPageList(FuPermissionProjectBO fuPermissionProjectBO, PageInfoHelper helper) throws Exception {
        //验证参数对象是否为空
        if (fuPermissionProjectBO == null) {
            throw new BusinessException(GlobalResultCode.PARAM_NULL_POINTER);
        }


        boolean contains = true;
        List<Integer> porjKeys = null;
        // 获取当前用户信息
       /* AdminInfo user = new AdminInfo();
        //获取配置文件中超级管理员，判断当前登录用户是否为超级管理员,true为超管
        String[] superAdministrators = superAdministrator.split(",");

        if (user != null && superAdministrators != null) {
            contains = Arrays.asList(superAdministrators).contains(user.getAdminId().toString());
        }

        //由于分页组件默认执行startPage的第一个sql，因此提前单独执行
        if (!contains) {
            //获取用户所管理的工程项目KEY集合
            porjKeys = permissionUserProjectService.findPorjKeysByUserId(user.getAdminId());
        }*/

        //设置分页信息
        if (helper == null) {
            helper = new PageInfoHelper();
        }
        PageHelper.startPage(helper.getPageNo(), helper.getPageSize());
        // 超级管理员用户查看全部内容，其他用户获取当前有拥有的工程项目
        List<FuPermissionProjectVO> list = Lists.newArrayList();
        if (contains) {
            list = fuPermissionProjectMapper.selectPageList(fuPermissionProjectBO);
        } else {
            if (StringUtils.isNotEmpty(porjKeys)) {
                fuPermissionProjectBO.setProjKeys(porjKeys);
                list = fuPermissionProjectMapper.selectPageList(fuPermissionProjectBO);
            }
        }
        return new PageInfo(list);
    }

    /**
     * 通过主键获取工程项目详情
     *
     * @param id 主键ID
     * @return PermissionProjectVO对象
     */
    public FuPermissionProjectVO findInfo(Integer id) throws Exception {
        //验证必要参数值是否为空
        if (id == null) {
            throw new BusinessException(GlobalResultCode.PARAM_NULL_POINTER);
        }

        FuPermissionProject proj = fuPermissionProjectMapper.selectById(id);
        if (proj == null) {
            throw new BusinessException(GlobalResultCode.RESULE_DATA_NONE);
        }
        return FuPermissionProjectVO.modelToVO(proj);
    }

    /**
     * 通过主键获取工程项目详情
     * @param conditionMap
     * @return
     */
    public FuPermissionProject findProject(Map conditionMap) throws Exception {
        //验证必要参数值是否为空
        if (conditionMap == null) {
            throw new BusinessException(GlobalResultCode.PARAM_NULL_POINTER);
        }
        FuPermissionProject conditionProject=new FuPermissionProject();
        if(!ObjectUtils.isEmpty(conditionMap.get("userId"))){
            // 使用用户id进行查找
            Integer userId=Integer.parseInt(String.valueOf(conditionMap.get("userId")));
            List<Integer> prokeys= permissionUserProjectService.findPorjKeysByUserId(userId);
            if(prokeys==null||prokeys.size()==0){
                throw new BusinessException(GlobalResultCode.RESULE_DATA_NONE);
            }
            conditionProject.setProjKey(prokeys.get(0));

        }
        if(!ObjectUtils.isEmpty(conditionMap.get("id"))){
            conditionProject.setId(Integer.parseInt(String.valueOf(conditionMap.get("id"))));
        }
        if(!ObjectUtils.isEmpty(conditionMap.get("projKey"))){
            conditionProject.setProjKey(Integer.parseInt(String.valueOf(conditionMap.get("projKey"))));
        }
        if(!ObjectUtils.isEmpty(conditionMap.get("projName"))){
            conditionProject.setProjName(String.valueOf(conditionMap.get("projName")));
        }
        FuPermissionProject proj = fuPermissionProjectMapper.selectOne(conditionProject);
        if (proj == null) {
            throw new BusinessException(GlobalResultCode.RESULE_DATA_NONE);
        }
        return proj;

    }

    /**
     * 通过主键批量删除工程项目信息以及相关联数据
     *
     * @param ids 工程项目信ID集合
     */
    @Transactional(rollbackFor = Exception.class)
    public void remove(String ids) throws Exception {
        //验证必要参数值是否为空
        if (StringUtils.isAnyBlank(ids)) {
            throw new BusinessException(GlobalResultCode.PARAM_NULL_POINTER);
        }

        String[] strArr = ids.split(",");
        List<Integer> paramList = Lists.newArrayList();
        for (int i = 0; i < strArr.length; i++) {
            paramList.add(Integer.valueOf(strArr[i]));
        }

        //根据工程项目ID查询工程项目key
        List<Integer> projKeys = fuPermissionProjectMapper.selectKeyById(paramList);
        /*校验项目下是否有管理员*/
        Map conditionMap=new HashMap();
        for(Integer projKey:projKeys){
            conditionMap.put(FuPermissionAdmin.PROJ_KEY,projKey);
            List<FuPermissionAdmin> admins= fuPermissionAdminMapper.selectByMap(conditionMap);
            if(admins!=null&&admins.size()>0){
                throw new RmsrException(RmsResultCode.PERMISSION_PROJECT_DATA_DEL_FAILURE_INFOS);
            }
        }

        //批量删除工程项目
        Integer isSuccess = fuPermissionProjectMapper.deleteBatchIds(paramList);
        if (isSuccess < 0) {
            throw new RmsrException(RmsResultCode.PERMISSION_PROJECT_DATA_DEL_FAILURE);
        }
        //通过工程项目KEY集合查询所关联的权限资源信息
        List<Integer> resIds = permissionResourceService.findResIdsByKeys(projKeys);
        //删除工程项目所关联的权限资源信息以及权限资源与角色的关联关系
        if (StringUtils.isNotEmpty(resIds)) {
            boolean success = permissionResourceService.deleteBatchIds(resIds);
            boolean perRole = permissionRoleResourceService.delete(new EntityWrapper<FuPermissionRoleResource>().in(FuPermissionRoleResource.RES_ID, resIds));
        }
    }

    /**
     * 获取所有工程项目信息
     *
     * @return 工程项目信息集合
     */
    public List<FuPermissionProjectVO> findAllList() throws Exception {
        // 获取当前用户信息
        AdminInfo user = RequestContextHolderUtil.getAdminInfo();
        //TODO
        user = new AdminInfo();
        user.setAdminId(11);
        //获取配置文件中超级管理员，判断当前登录用户是否为超级管理员,true为超管
        String[] superAdministrators = superAdministrator.split(",");
        boolean contains = false;
        if (user != null && superAdministrators != null) {
            contains = Arrays.asList(superAdministrators).contains(user.getAdminId().toString());
        }

        List<FuPermissionProject> result = Lists.newArrayList();
        EntityWrapper<FuPermissionProject> entityWrapper = new EntityWrapper<FuPermissionProject>();
        // 超级管理员用户查看全部内容，其他用户获取当前有拥有的工程项目
        if (contains) {
            result = fuPermissionProjectMapper.selectList(entityWrapper.eq(FuPermissionProject.PROJ_STATUS, 1));
        } else {
            //查找用户与工程项目关系
            List<Integer> projKeys = permissionUserProjectService.findPorjKeysByUserId(user.getAdminId());
            if (StringUtils.isNotEmpty(projKeys)) {
                result = fuPermissionProjectMapper.selectList(entityWrapper.eq(FuPermissionProject.PROJ_STATUS, 1).in(FuPermissionProject.PROJ_KEY, projKeys));
            }
        }
        List<FuPermissionProjectVO> resultList = Lists.newArrayList();
        for (FuPermissionProject proj : result) {
            FuPermissionProjectVO projVO = FuPermissionProjectVO.modelToVO(proj);
            resultList.add(projVO);
        }
        return resultList;
    }

    /**
     * 通过工程项目KEY查询工程项目信息
     *
     * @param projKey 工程项目KEY
     * @return 工程项目信息
     */
    public FuPermissionProject findByKey(Integer projKey) throws Exception {
        //验证必要参数值是否为空
        if (projKey == null) {
            throw new BusinessException(GlobalResultCode.PARAM_NULL_POINTER);
        }

        FuPermissionProject result = selectOne(new EntityWrapper<FuPermissionProject>().eq(FuPermissionProject.PROJ_STATUS, 1).eq(FuPermissionProject.PROJ_KEY, projKey));
        return result;
    }

    /**
     * 查询全部工程项目信息
     *
     * @return 全部工程项目集合，以工程项目KEY为map的key
     */
    public Map<Integer, FuPermissionProject> findAllProj() {
        return fuPermissionProjectMapper.selectAllProjet();
    }

}