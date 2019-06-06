package com.future;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@Ignore
@RunWith(SpringRunner.class)
@SpringBootTest
//由于是Web项目，Junit需要模拟ServletContext，因此我们需要给我们的测试类加上@WebAppConfiguration。
@WebAppConfiguration
public class MainApplicationTest {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Before
    public void init() {
        logger.info("开始测试-----------------");
    }

    @After
    public void after() {
        logger.info("测试结束-----------------");
    }

}