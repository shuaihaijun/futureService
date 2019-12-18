package com.future.controller.permission;


import com.alibaba.fastjson.JSONObject;
import com.future.common.exception.DataConflictException;
import com.future.common.result.RequestParams;
import com.future.common.util.StringUtils;
import com.future.common.util.ThreadCache;
import com.future.pojo.bo.permission.FuPermissionUserRoleBO;
import com.future.service.permission.PermissionUserRoleService;
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
    public boolean save(@RequestBody RequestParams<FuPermissionUserRoleBO> requestParams) throws Exception {
        FuPermissionUserRoleBO permissionUserRoleBO = requestParams.getParams();
        permissionUserRoleService.save(permissionUserRoleBO);
        return true;
    }


    @ApiOperation(value = "删除人员角色关系", notes = "删除人员角色关系")
    @PostMapping(value = "/remove")
    public boolean delete(@RequestBody RequestParams<FuPermissionUserRoleBO> requestParams) throws Exception {
        FuPermissionUserRoleBO bo = requestParams.getParams();
        permissionUserRoleService.remove(bo);
        return true;
    }

    @ApiOperation(value = "查询人员角色关系", notes = "查询人员角色关系")
    @PostMapping(value = "/queryUserRole")
    public PageInfo<FuPermissionUserRoleBO> queryUserRole(@RequestBody RequestParams<FuPermissionUserRoleBO> requestParams) throws Exception {
        // 获取请求参数
        String requestJSONStr = ThreadCache.getPostRequestParams();
        JSONObject requestMap = JSONObject.parseObject(requestJSONStr);
        return permissionUserRoleService.queryUserRole(requestMap);
    }
}