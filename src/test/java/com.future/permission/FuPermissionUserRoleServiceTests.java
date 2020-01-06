package com.future.permission;

import com.future.common.util.LogUtil;
import com.future.pojo.bo.permission.FuPermissionUserRoleBO;
import com.future.service.permission.PermissionUserRoleService;
import com.google.common.collect.Lists;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Ignore
public class FuPermissionUserRoleServiceTests {

    private LogUtil log = LogUtil.logger(this);
    @Autowired
    private PermissionUserRoleService permissionUserRoleService;

    /**
     * 新增用户角色关联信息
     */
    @Test
    public void testSave() {
        FuPermissionUserRoleBO fuPermissionUserRoleBO = new FuPermissionUserRoleBO();
        fuPermissionUserRoleBO.setUserId(1);
        fuPermissionUserRoleBO.setRoleIds(Lists.newArrayList(1, 2, 3, 4, 5, 6, 8));
        permissionUserRoleService.save(fuPermissionUserRoleBO);
    }

    /**
     * 通过用户ID查询所关联的角色ID集合
     */
    @Test
    public void testFindRoleIdsByUserId() {
        Integer userId = 1;
        List<Integer> list = permissionUserRoleService.findRoleIdsByUserId(userId);
        log.info(list.toString());
    }

    /**
     * 查询所有的用户ID集合并去重
     */
    @Test
    public void testFindUserIds() throws Exception {
        List<Integer> list = permissionUserRoleService.findUserIds();
        log.info(list.toString());
    }

    /**
     * 通过角色ID集合查询所关联的用户ID集合并去重
     */
    @Test
    public void testFindUserIdsByRoleIds() {
        List<Integer> list = permissionUserRoleService.findUserIdsByRoleIds(Lists.newArrayList(1, 2, 3, 4, 5, 6, 8));
        log.info(list.toString());
    }

    /**
     * 通过用户ID以及角色ID删除用户角色关联数据
     */
    @Test
    public void testRemove() {
        FuPermissionUserRoleBO fuPermissionUserRoleBO = new FuPermissionUserRoleBO();
        fuPermissionUserRoleBO.setUserId(1);
        //fuPermissionUserRoleBO.setRoleId(1);
        permissionUserRoleService.remove(fuPermissionUserRoleBO);
    }

    /**
     * 通过用户ID和角色ID集合删除用户角色关联数据
     */
    @Test
    public void testRemoveBySelective() {
        FuPermissionUserRoleBO fuPermissionUserRoleBO = new FuPermissionUserRoleBO();
        fuPermissionUserRoleBO.setUserId(1);
        fuPermissionUserRoleBO.setRoleIds(Lists.newArrayList(1, 2, 3, 4, 5, 6));
        permissionUserRoleService.removeBySelective(fuPermissionUserRoleBO);
    }

}