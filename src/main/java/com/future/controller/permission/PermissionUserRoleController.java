package com.future.controller.permission;


import com.future.common.result.RequestParams;
import com.future.pojo.bo.permission.FuPermissionUserRoleBO;
import com.future.service.permission.PermissionUserRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户角色模块controller类
 *
 * @author Admin
 * @version: 1.0
 */
@Api(value = "PermissionUserRoleController", description = "用户角色模块controller类")
@RestController
@RequestMapping("/permission/userRole")
public class PermissionUserRoleController {

    @Autowired
    private PermissionUserRoleService permissionUserRoleService;

    @ApiOperation(value = "保存人员角色关系", notes = "保存人员角色关系")
    @PostMapping(value = "/save")
    public void save(@RequestBody RequestParams<FuPermissionUserRoleBO> requestParams) throws Exception {
        FuPermissionUserRoleBO permissionUserRoleBO = requestParams.getParams();
        permissionUserRoleService.save(permissionUserRoleBO);
    }


    @ApiOperation(value = "删除人员角色关系", notes = "删除人员角色关系")
    @PostMapping(value = "/remove")
    public void delete(@RequestBody RequestParams<FuPermissionUserRoleBO> requestParams) throws Exception {
        FuPermissionUserRoleBO bo = requestParams.getParams();
        permissionUserRoleService.remove(bo);
    }
}