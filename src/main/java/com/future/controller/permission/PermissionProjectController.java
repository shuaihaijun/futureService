package com.future.controller.permission;


import com.future.common.helper.PageInfoHelper;
import com.future.entity.com.FuComNet;
import com.future.entity.permission.FuPermissionProject;
import com.future.pojo.bo.BasicBO;
import com.future.common.result.RequestParams;
import com.future.pojo.bo.permission.FuPermissionProjectBO;
import com.future.pojo.vo.permission.FuPermissionProjectVO;
import com.future.service.permission.PermissionProjectService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;


/**
 * 工程项目信息模块controller类
 *
 * @author Admin
 * @version: 1.0
 */
@Api(value = "PermissionProjectController", description = "工程项目信息模块controller类")
@RestController
@RequestMapping("/permission/project")
public class PermissionProjectController {

    @Autowired
    private PermissionProjectService permissionProjectService;

    /**
     * 工程项目信息注册保存
     *
     * @param requestParams
     */
    @ApiOperation(value = "新增工程项目信息", notes = "新增工程项目信息")
    @PostMapping(value = "/save")
    public boolean save(@RequestBody @Validated RequestParams<FuPermissionProjectBO> requestParams) throws Exception {
        FuPermissionProjectBO fuPermissionProjectBO = requestParams.getParams();
        permissionProjectService.save(fuPermissionProjectBO);
        return true;
    }

    /**
     * 工程项目信息更新保存
     *
     * @param requestParams
     */
    @ApiOperation(value = "工程项目信息更新保存", notes = "工程项目信息更新保存")
    @PostMapping(value = "/modify")
    public boolean modify(@RequestBody RequestParams<FuPermissionProjectBO> requestParams) throws Exception {
        FuPermissionProjectBO fuPermissionProjectBO = requestParams.getParams();
        permissionProjectService.modify(fuPermissionProjectBO);
        return true;
    }

    /**
     * 工程项目信息分页查询
     *
     * @param requestParams
     * @return
     */
    @ApiOperation(value = "工程项目信息分页查询", notes = "工程项目信息分页查询")
    @PostMapping(value = "/queryPage")
    public PageInfo<FuPermissionProjectVO> queryPage(@RequestBody RequestParams<FuPermissionProjectBO> requestParams) throws Exception {
        FuPermissionProjectBO fuPermissionProjectBO = requestParams.getParams();
        PageInfoHelper helper = requestParams.getPageInfoHelper();
        PageInfo<FuPermissionProjectVO> resultPage = permissionProjectService.findPageList(fuPermissionProjectBO, helper);
        return resultPage;
    }

    /**
     * 工程项目信息详情
     *
     * @param requestParams
     * @return
     */
    @ApiOperation(value = "工程项目信息详情查询", notes = "工程项目信息详情查询")
    @PostMapping(value = "/queryDetail")
    public FuPermissionProjectVO queryDetail(@RequestBody RequestParams<FuPermissionProjectBO> requestParams) throws Exception {
        FuPermissionProjectBO fuPermissionProjectBO = requestParams.getParams();
        FuPermissionProjectVO fuPermissionProjectVO = permissionProjectService.findInfo(fuPermissionProjectBO.getId());
        return fuPermissionProjectVO;
    }

    /**
     * 工程项目信息删除
     *
     * @param requestParams
     */
    @ApiOperation(value = "工程项目信息删除", notes = "工程项目信息删除")
    @PostMapping(value = "/remove")
    public boolean delete(@RequestBody RequestParams<BasicBO> requestParams) throws Exception {
        String ids = requestParams.getParams().getDelIds();
        permissionProjectService.remove(ids);
        return true;
    }

    /**
     * 查询全部工程项目信息
     * @param requestParams
     * @return
     */
    @ApiOperation(value = "工程项目信息下拉列表", notes = "工程项目信息下拉列表")
    @PostMapping(value = "/queryAll")
    public List<FuPermissionProjectVO> queryAll(@RequestBody RequestParams requestParams) throws Exception {
        List<FuPermissionProjectVO> resultList = permissionProjectService.findAllList();
        return resultList;
    }


    /**
     * 工程项目信息详情
     * @param requestParams
     * @return
     */
    @ApiOperation(value = "工程项目信息详情查询", notes = "工程项目信息详情查询")
    @PostMapping(value = "/queryDetailByCondition")
    public FuPermissionProject queryDetailByCondition(@RequestBody RequestParams<Map> requestParams) throws Exception {
        Map permissionProject = requestParams.getParams();
        FuPermissionProject project = permissionProjectService.findProject(permissionProject);
        return project;
    }
    /**
     * 工程项目信息详情
     * @param requestParams
     * @return
     */
    @ApiOperation(value = "工程项目信息详情查询", notes = "工程项目信息详情查询")
    @PostMapping(value = "/queryProjectByUrl")
    public FuPermissionProject queryProjectByUrl(@RequestBody RequestParams<Map> requestParams) throws Exception {
        Map permissionProject = requestParams.getParams();
        FuPermissionProject project = permissionProjectService.queryProjectByUrl(permissionProject);
        return project;
    }

    /**
     * 查询工程项目中的链接信息
     * @param requestParams
     * @return
     */
    @ApiOperation(value = "查询工程项目中的链接信息", notes = "工程项目信息详情查询")
    @PostMapping(value = "/queryComNetByCondition")
    public List<FuComNet> queryComNetByCondition(@RequestBody RequestParams<Map> requestParams) throws Exception {
        Map permissionProject = requestParams.getParams();
        return permissionProjectService.queryComNetByCondition(permissionProject);
    }
}