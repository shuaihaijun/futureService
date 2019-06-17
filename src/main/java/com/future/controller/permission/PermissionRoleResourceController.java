package com.future.controller.permission;

import com.future.common.result.RequestParams;
import com.future.pojo.bo.BasicBO;
import com.future.pojo.bo.permission.FuPermissionRoleResourceBO;
import com.future.service.permission.PermissionRoleResourceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 角色权限资源模块controller类
 *
 * @author Admin
 * @version: 1.0
 */
@Api(value = "PermissionRoleController", description = "角色权限资源模块controller类")
@RestController
@RequestMapping("/permission/roleResource")
public class PermissionRoleResourceController {

    @Autowired
    private PermissionRoleResourceService permissionRoleResourceService;

    @ApiOperation(value = "权限与角色绑定", notes = "权限与角色绑定")
    @PostMapping(value = "/save")
    public void save(@RequestBody RequestParams<FuPermissionRoleResourceBO> requestParams) {
        FuPermissionRoleResourceBO permissionRoleResourceBO = requestParams.getParams();
        permissionRoleResourceService.save(permissionRoleResourceBO);
    }

    @ApiOperation(value = "权限与角色解除绑定", notes = "权限与角色解除绑定")
    @PostMapping(value = "/remove")
    public void remove(@RequestBody RequestParams<BasicBO> requestParams) throws Exception {
        BasicBO bo = requestParams.getParams();
        permissionRoleResourceService.remove(bo);
    }

}