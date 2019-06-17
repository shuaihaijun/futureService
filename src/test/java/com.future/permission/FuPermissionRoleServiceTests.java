package com.future.permission;

import com.future.common.helper.PageInfoHelper;
import com.future.pojo.bo.BasicBO;
import com.future.pojo.bo.permission.FuPermissionRoleBO;
import com.future.pojo.vo.permission.FuPermissionRoleVO;
import com.future.service.permission.PermissionRoleService;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class FuPermissionRoleServiceTests {

    @Autowired
    private PermissionRoleService permissionRoleService;

    /**
     * 新增角色信息
     */
    @Test
    public void testSave() {
        FuPermissionRoleBO fuPermissionRoleBO = new FuPermissionRoleBO();
        fuPermissionRoleBO.setRoleName("demo-role1");
        fuPermissionRoleBO.setProjKey(1);
        permissionRoleService.save(fuPermissionRoleBO);
    }

    /**
     * 通过主键更新角色信息
     */
    @Test
    public void testModify() {
        FuPermissionRoleBO fuPermissionRoleBO = new FuPermissionRoleBO();
        fuPermissionRoleBO.setId(9);
        fuPermissionRoleBO.setRoleName("demo1");
        permissionRoleService.modify(fuPermissionRoleBO);
    }

    /**
     * 通过主键批量删除角色信息以及相关联数据
     */
    @Test
    public void testRemove() {
        String ids = "21,52,9";
        permissionRoleService.remove(ids);
    }

    /**
     * 角色信息分页查询，超级管理员查看所有，普通用户查看所管理工程项目数据
     */
    @Test
    public void testFindPageList() {
        FuPermissionRoleBO fuPermissionRoleBO = new FuPermissionRoleBO();
        PageInfoHelper pageInfoHelper = new PageInfoHelper();
        pageInfoHelper.setPageNo(1);
        pageInfoHelper.setPageSize(5);
        PageInfo<FuPermissionRoleVO> pageInfo = permissionRoleService.findPageList(fuPermissionRoleBO, pageInfoHelper);
        log.info(pageInfo.toString());
    }

    /**
     * 通过主键获取角色信息
     */
    @Test
    public void testFindInfo() {
        Integer id = 1;
        log.info(permissionRoleService.findInfo(id).toString());
    }

    /**
     * 角色信息分页查询，通过权限资源ID查询所关联的角色信息集合
     */
    @Test
    public void testFindByResId() {
        BasicBO basicBO = new BasicBO();
        basicBO.setId(1);
        PageInfoHelper pageInfoHelper = new PageInfoHelper();
        pageInfoHelper.setPageNo(1);
        pageInfoHelper.setPageSize(5);
        PageInfo<FuPermissionRoleVO> pageInfo = permissionRoleService.findByResId(basicBO, pageInfoHelper);
        log.info(pageInfo.toString());
    }

    /**
     * 通过查询条件查询角色信息集合
     */
    @Test
    public void testFindByProjKey() {
        FuPermissionRoleBO fuPermissionRoleBO = new FuPermissionRoleBO();
        //fuPermissionRoleBO.setProjKey(2);
        List<FuPermissionRoleVO> list = permissionRoleService.findByProjKey(fuPermissionRoleBO);
        log.info(list.toString());
    }

}