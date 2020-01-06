package com.future.permission;

import com.future.common.helper.PageInfoHelper;
import com.future.entity.permission.FuPermissionProject;
import com.future.pojo.bo.permission.FuPermissionProjectBO;
import com.future.pojo.vo.permission.FuPermissionProjectVO;
import com.future.service.permission.PermissionProjectService;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
@Ignore
public class PermissionProjectServiceTests {

    @Autowired
    private PermissionProjectService permissionProjectService;


    /**
     * 新增工程项目信息
     */
    @Test
    public void testSave() throws Exception {
        FuPermissionProjectBO fuPermissionProjectBO = new FuPermissionProjectBO();
        fuPermissionProjectBO.setProjAdmin("zhangsan");
        fuPermissionProjectBO.setProjKey(6);
        fuPermissionProjectBO.setProjName("test");
        permissionProjectService.save(fuPermissionProjectBO);
    }

    /**
     * 通过主键更新工程项目信息
     */
    @Test
    public void testModify() throws Exception {
        FuPermissionProject permissionProject = permissionProjectService.selectById(6);
        FuPermissionProjectBO fuPermissionProjectBO = new FuPermissionProjectBO();
        BeanUtils.copyProperties(permissionProject, fuPermissionProjectBO);
        fuPermissionProjectBO.setProjAdmin("zhangsan-2");
        fuPermissionProjectBO.setProjName("test-1");
        permissionProjectService.modify(fuPermissionProjectBO);
    }


    /**
     * 通过工程项目BO查询工程项目VO分页对象
     */
    @Test
    public void testFindPageList() throws Exception {
        PageInfoHelper pageInfoHelper = new PageInfoHelper();
        pageInfoHelper.setPageNo(1);
        pageInfoHelper.setPageSize(5);

        FuPermissionProjectBO fuPermissionProjectBO = new FuPermissionProjectBO();
        PageInfo pageInfo = permissionProjectService.findPageList(fuPermissionProjectBO, pageInfoHelper);
        log.info(pageInfo.toString());
    }

    /**
     * 通过主键获取工程项目详情
     */
    @Test
    public void testFindInfo() throws Exception {
        Integer id = 1;
        log.info(permissionProjectService.findInfo(id).toString());
    }

    /**
     * 通过主键批量删除工程项目信息以及相关联数据
     */
    @Test
    public void testRemove() throws Exception {
        String ids = "2,111";
        permissionProjectService.remove(ids);
    }

    /**
     * 获取所有工程项目信息
     */
    @Test
    public void testFindAllList() throws Exception {
        List<FuPermissionProjectVO> list = permissionProjectService.findAllList();
        if (!list.isEmpty()) {
            log.info(list.toString());
        }
    }

    /**
     * 通过工程项目KEY查询工程项目信息
     */

    @Test
    public void testFindByKey() throws Exception {
        Integer projKey = 1;
        log.info(permissionProjectService.findByKey(projKey).toString());
    }

    /**
     * 查询全部工程项目信息
     */
    @Test
    public void testFindAllProj() {
        Map<Integer, FuPermissionProject> map = permissionProjectService.findAllProj();
        Iterator<Map.Entry<Integer, FuPermissionProject>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Integer, FuPermissionProject> entry = iterator.next();
            log.info(entry.getKey() + "/" + entry.getValue());
        }
    }
}