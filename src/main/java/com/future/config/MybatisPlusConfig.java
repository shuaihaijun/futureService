package com.future.config;

import com.github.pagehelper.PageHelper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * @author ：mmzs
 * @date ：Created in 2019-12-20 22:54
 * @description：mybits分页助手
 * @modified By：
 * @version: 1.0$
 */
@Configuration
public class MybatisPlusConfig {
        @Bean
        public PageHelper pageHelper() {
            PageHelper pageHelper = new PageHelper();
            Properties p = new Properties();
            p.setProperty("dialect", "Mysql");
            p.setProperty("offsetAsPageNum", "true");
            p.setProperty("rowBoundsWithCount", "true");
            pageHelper.setProperties(p);
            return pageHelper;
        }


}
