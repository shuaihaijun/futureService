package com.future.controller.permission;


import com.future.common.result.RequestParams;
import com.future.pojo.bo.BasicBO;
import com.future.pojo.bo.permission.FuPermissionUserProjectBO;
import com.future.service.permission.PermissionUserProjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**

 * 用户工程项目模块controller类
 *
 * @author Admin
 * @version: 1.0
 */
@Api(value = "PerAdministratorsysController", description = "用户工程项目模块controller类")
@RestController
@RequestMapping("/permission/peradministratorsys")
public class PermissionUserProjectController {

    @Autowired
    private PermissionUserProjectService permissionUserProjectService;

    /**
     *
     * @param requestParams
     * @throws Exception
     */
    @ApiOperation(value = "保存系统管理员", notes = "保存系统管理员")
    @PostMapping(value = "/save")
    public void save(@RequestBody RequestParams<FuPermissionUserProjectBO> requestParams) throws Exception{
        FuPermissionUserProjectBO bo = requestParams.getParams();
        permissionUserProjectService.save(bo);
    }

    /**
     *
     * @param requestParams
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "设置系统管理员", notes = "设置系统管理员")
    @PostMapping(value = "/queryData")
    public Map<String, Object> getData(@RequestBody RequestParams<BasicBO> requestParams) throws Exception{
        BasicBO bo = requestParams.getParams();
        Map<String, Object> result = permissionUserProjectService.findByUserId(bo);
        return result;
    }
}