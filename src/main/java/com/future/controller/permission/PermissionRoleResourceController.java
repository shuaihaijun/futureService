package com.future.controller.permission;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.future.common.enums.GlobalResultCode;
import com.future.common.exception.ParameterInvalidException;
import com.future.common.result.RequestParams;
import com.future.common.util.StringUtils;
import com.future.common.util.ThreadCache;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public Boolean save(@RequestBody RequestParams<FuPermissionRoleResourceBO> requestParams) {
        FuPermissionRoleResourceBO permissionRoleResourceBO = requestParams.getParams();
        permissionRoleResourceService.save(permissionRoleResourceBO);
        return true;
    }

    @ApiOperation(value = "权限与角色解除绑定", notes = "权限与角色解除绑定")
    @PostMapping(value = "/remove")
    public void remove(@RequestBody RequestParams<BasicBO> requestParams) throws Exception {
        BasicBO bo = requestParams.getParams();
        permissionRoleResourceService.remove(bo);
    }

    @ApiOperation(value = "查询角色权限树", notes = "查询角色权限树")
    @PostMapping(value = "/findRoleResourceTree")
    public JSONArray findRoleResourceTree(@RequestBody RequestParams<BasicBO> requestParams) throws Exception {
        String requestJSONStr = ThreadCache.getPostRequestParams();
        JSONObject requestMap = JSONObject.parseObject(requestJSONStr);
        String userId=requestMap.getString("userId");
        if(StringUtils.isEmpty(userId)){
            throw new ParameterInvalidException(GlobalResultCode.PARAM_NULL_POINTER);
        }
        return permissionRoleResourceService.findRoleResourceTree(Integer.parseInt(userId));
    }

    @ApiOperation(value = "权限与角色解除绑定", notes = "权限与角色解除绑定")
    @PostMapping(value = "/findResIdsByRoleId")
    public List<String> findResIdsByRoleId(@RequestBody RequestParams<BasicBO> requestParams) throws Exception {
        String requestJSONStr = ThreadCache.getPostRequestParams();
        JSONObject requestMap = JSONObject.parseObject(requestJSONStr);
        String roleId=requestMap.getString("id");
        if(StringUtils.isEmpty(roleId)){
            throw new ParameterInvalidException(GlobalResultCode.PARAM_NULL_POINTER);
        }
        return permissionRoleResourceService.findResIdsByRoleId(Integer.parseInt(roleId));
    }
}