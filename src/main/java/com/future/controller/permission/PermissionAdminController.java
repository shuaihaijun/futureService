package com.future.controller.permission;


import com.future.common.helper.PageInfoHelper;
import com.future.common.result.RequestParams;
import com.future.entity.permission.FuPermissionAdmin;
import com.future.service.permission.PermissionAdminService;
import com.github.pagehelper.Page;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


/**
 * 工程项目信息模块controller类
 *
 * @author Admin
 * @version: 1.0
 */
@RestController
@RequestMapping("/permission/admin")
public class PermissionAdminController {

    @Autowired
    private PermissionAdminService permissionAdminService;

    /**
     * 工程项目信息注册保存
     *
     * @param requestParams
     */
    @ApiOperation(value = "新增工程项目信息", notes = "新增工程项目信息")
    @PostMapping(value = "/addPermissionAdmin")
    public boolean addPermissionAdmin(@RequestBody @Validated RequestParams<FuPermissionAdmin> requestParams) throws Exception {
        FuPermissionAdmin permissionAdmin = requestParams.getParams();
        permissionAdminService.addPermissionAdmin(permissionAdmin);
        return true;
    }



    /**
     * 工程项目信息分页查询
     *
     * @param requestParams
     * @return
     */
    @ApiOperation(value = "工程项目信息分页查询", notes = "工程项目信息分页查询")
    @PostMapping(value = "/queryPermissionAdmin")
    public Page<FuPermissionAdmin> queryPermissionAdmin(@RequestBody RequestParams<Map> requestParams) throws Exception {
        Map permissionMap = requestParams.getParams();
        PageInfoHelper helper = requestParams.getPageInfoHelper();
        return permissionAdminService.queryPermissionAdmin(permissionMap,helper);
    }



    /**
     * 工程项目信息删除
     *
     * @param requestParams
     */
    @ApiOperation(value = "工程项目信息删除", notes = "工程项目信息删除")
    @PostMapping(value = "/removePermissionAdmin")
    public boolean removePermissionAdmin(@RequestBody RequestParams<FuPermissionAdmin> requestParams) throws Exception {
        FuPermissionAdmin permissionAdmin = requestParams.getParams();
        permissionAdminService.removePermissionAdmin(permissionAdmin);
        return true;
    }

}