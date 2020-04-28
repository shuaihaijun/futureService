package com.future.controller.permission;


import com.future.common.helper.PageInfoHelper;
import com.future.common.result.RequestParams;
import com.future.pojo.bo.BasicBO;
import com.future.pojo.bo.permission.FuPermissionResourceBO;
import com.future.pojo.vo.permission.FuPermissionResourceVO;
import com.future.service.permission.PermissionResourceService;
import com.github.pagehelper.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;


/**

 * 权限资源信息模块controller类
 *
 * @author Admin
 * @version: 1.0
 */
@Api(value = "PermissionResourceController", description = "权限资源信息模块controller类")
@RestController
@RequestMapping("/permission/resource")
public class PermissionResourceController {

    @Autowired
    private PermissionResourceService permissionResourceService;

    /**
     * 权限信息注册保存
     *
     * @param requestParams 需要保存的参数
     */
    @ApiOperation(value = "权限信息注册保存", notes = "权限信息注册保存")
    @PostMapping(value = "/save")
    public void save(@RequestBody RequestParams<FuPermissionResourceBO> requestParams) {
        FuPermissionResourceBO permenuBO = requestParams.getParams();
        if(permenuBO.getId()!=null){
            permissionResourceService.modify(permenuBO);
        }else {
            permissionResourceService.save(permenuBO);
        }
    }

    /**
     * 权限信息编辑更新
     *
     * @param requestParams 被更新的参数
     */
    @ApiOperation(value = "权限信息更新", notes = "权限信息更新")
    @PostMapping(value = "/modify")
    public void modify(@RequestBody RequestParams<FuPermissionResourceBO> requestParams) {
        FuPermissionResourceBO permissionResourceBO = requestParams.getParams();
        permissionResourceService.modify(permissionResourceBO);
    }

    /**
     * 权限信息分页查询
     *
     * @param requestParams 查询条件
     * @return 分页对象
     */
    @ApiOperation(value = "权限信息分页查询", notes = "权限信息分页查询")
    @PostMapping(value = "/queryPage")
    public Page<FuPermissionResourceVO> queryPage(@RequestBody RequestParams<FuPermissionResourceBO> requestParams) {
        FuPermissionResourceBO permenuBO = requestParams.getParams();
        PageInfoHelper helper = requestParams.getPageInfoHelper();
        return permissionResourceService.findPageList(permenuBO, helper);
    }

    /**
     * 权限信息详情查询，根据主键
     *
     * @param requestParams 条件主键
     * @return 详情对象
     */
    @ApiOperation(value = "权限信息详情查询", notes = "权限信息详情查询")
    @PostMapping(value = "/queryDetail")
    public FuPermissionResourceVO queryDetail(@RequestBody RequestParams<FuPermissionResourceBO> requestParams) {
        FuPermissionResourceBO permissionResourceBO = requestParams.getParams();
        FuPermissionResourceVO permissionResourceVO = permissionResourceService.findInfo(permissionResourceBO.getId());
        return permissionResourceVO;
    }

    /**
     * 权限信息删除，根据单个ID或者ID集合
     *
     * @param requestParams
     */
    @ApiOperation(value = "权限信息删除", notes = "权限信息删除")
    @PostMapping(value = "/remove")
    public void remove(@RequestBody RequestParams<BasicBO> requestParams) {
        String ids = requestParams.getParams().getDelIds();
        permissionResourceService.remove(ids);
    }

    @ApiOperation(value = "查询资源树", notes = "查询角色权限树")
    @PostMapping(value = "/findResourceTree")
    public Map findResourceTree(@RequestBody RequestParams<BasicBO> requestParams) throws Exception {
        String treeJson= permissionResourceService.findResourceTree();
        System.out.println(treeJson);
        Map dataMap= new HashMap();
        dataMap.put("data",treeJson);
        return dataMap;
    }
}