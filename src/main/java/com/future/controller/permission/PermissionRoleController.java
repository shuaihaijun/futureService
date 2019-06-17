package com.future.controller.permission;


import com.future.common.helper.PageInfoHelper;
import com.future.common.result.RequestParams;
import com.future.pojo.bo.BasicBO;
import com.future.pojo.bo.permission.FuPermissionRoleBO;
import com.future.pojo.vo.permission.FuPermissionRoleVO;
import com.future.service.permission.PermissionRoleService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * 角色信息模块controller类
 *
 * @author Admin
 * @version: 1.0
 */
@Api(value = "PermissionRoleController", description = "角色信息模块controller类")
@RestController
@RequestMapping("/permission/role")

public class PermissionRoleController {

    @Autowired
    private PermissionRoleService permissionRoleService;


    /**
     * 角色信息保存
     *
     * @param requestParams 需要保存的对象数据
     */
    @ApiOperation(value = "新增角色信息", notes = "新增角色信息")
    @PostMapping(value = "/save")
    public void save(@RequestBody RequestParams<FuPermissionRoleBO> requestParams) {
        FuPermissionRoleBO permissionRoleBO = requestParams.getParams();
        permissionRoleService.save(permissionRoleBO);
    }

    /**
     * 角色信息更新，根据主键
     *
     * @param requestParams 需要更新的数据
     */
    @ApiOperation(value = "角色信息更新", notes = "角色信息更新")
    @PostMapping(value = "/modify")
    public void modify(@RequestBody RequestParams<FuPermissionRoleBO> requestParams) {
        FuPermissionRoleBO permissionRoleBO = requestParams.getParams();
        permissionRoleService.modify(permissionRoleBO);
    }

    /**
     * 角色信息删除，单条删除或者批量删除，根据主键ID
     *
     * @param requestParams
     */
    @ApiOperation(value = "角色信息删除", notes = "角色信息删除")
    @PostMapping(value = "/remove")
    public void remove(@RequestBody RequestParams<BasicBO> requestParams) {
        String ids = requestParams.getParams().getDelIds();
        permissionRoleService.remove(ids);
    }

    /**
     * 角色信息分页查询
     *
     * @param requestParams 分页参数
     * @return 分页数据
     */
    @ApiOperation(value = "角色信息分页查询", notes = "角色信息分页查询")
    @PostMapping(value = "/queryPage")
    public PageInfo<FuPermissionRoleVO> queryPage(@RequestBody RequestParams<FuPermissionRoleBO> requestParams) {
        FuPermissionRoleBO permissionRoleBO = requestParams.getParams();
        PageInfoHelper helper = requestParams.getPageInfoHelper();
        PageInfo<FuPermissionRoleVO> result = permissionRoleService.findPageList(permissionRoleBO, helper);
        return result;
    }

    /**
     * 查询角色详情
     *
     * @param requestParams 查询参数
     * @return 角色详情对象
     */
    @ApiOperation(value = "角色信息详情", notes = "角色信息详情")
    @PostMapping(value = "/queryDetail")
    public FuPermissionRoleVO queryDetail(@RequestBody RequestParams<FuPermissionRoleBO> requestParams) {
        FuPermissionRoleBO PermissionRoleBO = requestParams.getParams();
        Integer id = PermissionRoleBO.getId();
        FuPermissionRoleVO PermissionRoleVO = permissionRoleService.findInfo(id);
        return PermissionRoleVO;
    }

    /**
     * 根据权限ID查询该权限所关联的角色
     *
     * @param requestParams 查询参数
     * @return 所关联的角色信息
     */
    @ApiOperation(value = "根据权限ID查询所关联角色", notes = "根据权限ID查询所关联角色")
    @PostMapping(value = "/queryByResId")
    public PageInfo<FuPermissionRoleVO> queryByResId(@RequestBody RequestParams<BasicBO> requestParams) throws Exception {
        BasicBO basicBO = requestParams.getParams();
        PageInfoHelper helper = requestParams.getPageInfoHelper();
        PageInfo<FuPermissionRoleVO> result = permissionRoleService.findByResId(basicBO, helper);
        return result;
    }

    /**
     * 根据系统ID查询关联的角色
     *
     * @param requestParams
     * @return
     */
    @ApiOperation(value = "根据系统ID查询关联的角色，不分页", notes = "根据系统ID查询关联的角色，不分页")
    @PostMapping(value = "/queryByProjKey")
    public List<FuPermissionRoleVO> queryByProjKey(@RequestBody RequestParams<FuPermissionRoleBO> requestParams) {
        FuPermissionRoleBO permissionRoleBO = requestParams.getParams();
        List<FuPermissionRoleVO> result = permissionRoleService.findByProjKey(permissionRoleBO);
        return result;
    }

}