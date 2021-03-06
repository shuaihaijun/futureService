package com.future;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.servlet.MultipartConfigElement;

@EnableSwagger2
@SpringBootApplication
@EnableTransactionManagement(proxyTargetClass = true)
@MapperScan("com.future.mapper")
public class FutureApplication {
	public static void main(String[] args) {
		SpringApplication.run(FutureApplication.class,args);
	}
	/**
	 * 文件上传临时路径
	 */
	@Bean
	MultipartConfigElement multipartConfigElement() {
		MultipartConfigFactory factory = new MultipartConfigFactory();
		factory.setLocation("D:/upload/tmp");
		return factory.createMultipartConfig();
	}
}
