package com.future.permission;

import com.future.common.helper.PageInfoHelper;
import com.future.common.util.LogUtil;
import com.future.mapper.permission.FuPermissionResourceMapper;
import com.future.pojo.bo.AdminInfo;
import com.future.pojo.bo.permission.FuPermissionResourceBO;
import com.future.pojo.vo.permission.FuPermissionResourceVO;
import com.future.service.permission.PermissionResourceService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Ignore
public class FuPermissionResourceServiceTests {

    static List<Integer> projKeys = null;
    static AdminInfo user = null;
    LogUtil log = LogUtil.logger(this);

    @BeforeClass
    public static void setUp() throws Exception {
        projKeys = new ArrayList<Integer>();
        projKeys.add(1);
        user = new AdminInfo();
        user.setAdminId(2);
    }

    @Autowired
    private PermissionResourceService permissionResourceService;
    @Autowired
    FuPermissionResourceMapper fuPermissionResourceMapper;


    /**
     * 新增权限资源信息
     */
    @Test
    public void testSave() {
        FuPermissionResourceBO fuPermissionResourceBO = new FuPermissionResourceBO();
        fuPermissionResourceBO.setResName("demo-resource");
        fuPermissionResourceBO.setProjKey(1);
        fuPermissionResourceBO.setResPid(4);
        permissionResourceService.save(fuPermissionResourceBO);
    }

    /**
     * 通过主键更新权限资源信息
     */
    @Test
    public void testModify() {
        FuPermissionResourceBO fuPermissionResourceBO = new FuPermissionResourceBO();
        fuPermissionResourceBO.setId(7);
        fuPermissionResourceBO.setResName("demo");
//        fuPermissionResourceBO.setProjKey(1);
        permissionResourceService.modify(fuPermissionResourceBO);
    }

    /**
     * 权限资源信息分页查询
     */
    @Test
    public void testFindPageList() {
        FuPermissionResourceBO fuPermissionResourceBO = new FuPermissionResourceBO();
//        fuPermissionResourceBO.setId(2);
//        fuPermissionResourceBO.setResName("demo1");
        fuPermissionResourceBO.setProjKey(1);
        PageInfoHelper pageInfoHelper = new PageInfoHelper();
        pageInfoHelper.setPageNo(1);
        pageInfoHelper.setPageSize(5);
        PageInfo<FuPermissionResourceVO> pageInfo = permissionResourceService.findPageList(fuPermissionResourceBO, pageInfoHelper);
        log.info(pageInfo.toString());
    }

    /**
     * 通过主键获取权限资源详情
     */
    @Test
    public void testFindInfo() {
        Integer id = 1;
        FuPermissionResourceVO fuPermissionResourceVO = permissionResourceService.findInfo(id);
        log.info(fuPermissionResourceVO.toString());
    }

    /**
     * 通过权限资源主键集合删除权限资源信息以及角色权限资源关系数据
     */
    @Test
    public void testRemove() {
        String ids = "77,6";
        permissionResourceService.remove(ids);
    }

    /**
     * 权限树信息查询
     */
    @Test
    public void testFindTreeNode() {
        log.info(permissionResourceService.findTreeNode(user));
    }

    /**
     * 角色功能授权权限树生成
     * <p>menu:</p>
     */
    @Test
    public void testFindAuthorizationTree() {
        Integer roleId = 1;
        log.info(permissionResourceService.findAuthorizationTree(user, roleId).toString());

    }

    /**
     * 通过工程项目KEY集合查询权限资源信息集合
     */
    @Test
    public void testFindByProjKey() {
        log.info(permissionResourceService.findByProjKeys(projKeys).toString());
    }

    /**
     * 通过工程项目KEY集合查询相关联的权限资源ID集合
     */
    @Test
    public void testFindResIdsByKeys() {
        log.info(permissionResourceService.findByProjKeys(projKeys).toString());
    }

    /**
     * 通过角色ID查询所关联的权限资源集合
     */
    @Test
    public void testFindByRoleId() {
        //设置分页信息
        Integer pro=0;
        List<Integer> pros=new ArrayList<>();
        pros.add(pro);
        PageInfoHelper   helper = new PageInfoHelper();
        PageHelper.startPage(helper.getPageNo(), helper.getPageSize());
        // 超级管理员用户查看全部内容，其他用户获取当前有拥有的工程项目
        List<FuPermissionResourceVO>  list  = fuPermissionResourceMapper.selectPageList(null, pros);
        while (list.size()>0){
            System.out.println(list.size());

            helper.setPageNo(helper.getPageNo()+1);
            System.out.println(helper.getPageNo());

            PageHelper.startPage(helper.getPageNo(), helper.getPageSize());
            list  = fuPermissionResourceMapper.selectPageList(null, pros);
        }

    }
}