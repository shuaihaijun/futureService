package com.future.permission;

import com.future.common.util.LogUtil;
import com.future.pojo.bo.BasicBO;
import com.future.pojo.bo.permission.FuPermissionRoleResourceBO;
import com.future.service.permission.PermissionRoleResourceService;
import com.google.common.collect.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FuFuPermissionRoleResourceServiceTests {

    private LogUtil log = LogUtil.logger(this);
    @Autowired
    private PermissionRoleResourceService permissionRoleResourceService;

    /**
     * 新增角色权限关联信息
     */
    @Test
    public void testSave() {
        FuPermissionRoleResourceBO fuPermissionRoleResourceBO = new FuPermissionRoleResourceBO();
        fuPermissionRoleResourceBO.setRoleId(1);
        fuPermissionRoleResourceBO.setResIds(Lists.newArrayList(1, 2, 3, 4, 5, 8));
        permissionRoleResourceService.save(fuPermissionRoleResourceBO);
    }

    /**
     * 通过角色ID以及权限资源ID删除角色权限资源相关联数据
     */
    @Test
    public void testRemove() throws Exception {
        BasicBO bo = new BasicBO();
        bo.setDelIds("1-1,2-1,3-1,4-1");
        permissionRoleResourceService.remove(bo);
    }

    /**
     * 通过角色ID集合查询角色所关联的权限资源ID集合并去重
     */
    @Test
    public void testFindResIdByRoleIds() {
        List<Integer> list = permissionRoleResourceService.findResIdByRoleIds(Lists.newArrayList(1, 2));
        log.info(list.toString());
    }

    /**
     * 通过权限资源ID查询所关联的角色ID集合
     */
    @Test
    public void testFindRoleIdsByResId() {
        Integer id = 1;
        List<Integer> list = permissionRoleResourceService.findRoleIdsByResId(id);
        log.info(list.toString());
    }


    /**
     * 通过角色ID查询所关联的权限资源ID拼接的字符串
     */
    @Test
    public void testFindResIdsByRoleId() {
        Integer roleId = 1;
        log.info(permissionRoleResourceService.findResIdsByRoleId(roleId).toString());
    }
}