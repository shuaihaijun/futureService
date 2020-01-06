package com.future.common;

import com.future.common.util.FileUtil;
import com.future.service.com.FuComService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
@Ignore
public class CommonTests {

    @Autowired
    FuComService fuComService;

    @Test
    public void testGetHistoryOrder(){
        String path=fuComService.getLoactionPath();
        System.out.println(path);
        path=FileUtil.getFileRelativePath("D:\\test\\2019\\12\\f703738da97739120aeff204f5198618377ae28e.png");
        System.out.println(path);
    }

}