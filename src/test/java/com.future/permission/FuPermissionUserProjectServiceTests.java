package com.future.permission;

import com.future.common.util.LogUtil;
import com.future.pojo.bo.BasicBO;
import com.future.pojo.bo.permission.FuPermissionUserProjectBO;
import com.future.service.permission.PermissionUserProjectService;
import com.google.common.collect.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FuPermissionUserProjectServiceTests {

    private LogUtil log = LogUtil.logger(this);
    @Autowired
    private PermissionUserProjectService permissionUserProjectService;

    /**
     * 新增用户工程项目关联信息
     */
    @Test
    public void testSave() {
        FuPermissionUserProjectBO fuPermissionUserProjectBO = new FuPermissionUserProjectBO();
        fuPermissionUserProjectBO.setUserId(1);
        fuPermissionUserProjectBO.setProjKeys(Lists.newArrayList(1, 2, 3, 4, 5, 8));
        permissionUserProjectService.save(fuPermissionUserProjectBO);
    }

    /**
     * 通过用户ID查询所管理的工程项目KEY集合
     */
    @Test
    public void testFindPorjKeysByUserId() {
        Integer userId = 1;
        List<Integer> list = permissionUserProjectService.findPorjKeysByUserId(userId);
        log.info(list.toString());
    }

    /**
     * 通过用户ID查询所管理的工程项目KEY集合
     */
    @Test
    public void testFindByUserId() throws Exception {
        BasicBO bo = new BasicBO();
        bo.setId(1);
        Map<String, Object> map = permissionUserProjectService.findByUserId(bo);
        log.info(map.toString());
    }

}