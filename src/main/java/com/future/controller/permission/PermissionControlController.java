package com.future.controller.permission;


import com.future.common.helper.PageInfoHelper;
import com.future.common.result.RequestParams;
import com.future.entity.permission.FuPermissionControl;
import com.future.service.permission.PermissionControlService;
import com.github.pagehelper.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


/**
 * 控制信息模块controller类
 * @author Admin
 * @version: 1.0
 */
@Api(value = "PermissioncontrolController", description = "控制信息模块controller类")
@RestController
@RequestMapping("/permission/control")

public class PermissionControlController {

    @Autowired
    PermissionControlService permissionControlService;

    /**
     * 根据权限ID查询该权限所关联的角色
     *
     * @param requestParams 查询参数
     * @return 所关联的角色信息
     */
    @ApiOperation(value = "根据权限条件查询控制信息", notes = "根据权限条件查询控制信息")
    @PostMapping(value = "/findOne")
    public FuPermissionControl findOne(@RequestBody RequestParams<Map> requestParams) {
        Map dataMap = requestParams.getParams();
        FuPermissionControl result = permissionControlService.findByConditon(dataMap);
        return result;
    }

    /**
     * 根据权限条件查询控制信息
     * @param requestParams
     * @return
     */
    @ApiOperation(value = "根据权限条件查询控制信息", notes = "根据权限条件查询控制信息")
    @PostMapping(value = "/findPage")
    public Page<FuPermissionControl> findPage(@RequestBody RequestParams<Map> requestParams) {
        Map dataMap  = requestParams.getParams();
        PageInfoHelper helper=requestParams.getPageInfoHelper();
        return permissionControlService.findPageByConditon(dataMap,helper);
    }

}