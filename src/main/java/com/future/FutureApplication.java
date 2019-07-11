package com.future;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@SpringBootApplication
@EnableTransactionManagement(proxyTargetClass = true)
@MapperScan("com.future.mapper")
public class FutureApplication {

	public static void main(String[] args) {
		SpringApplication.run(FutureApplication.class,args);
	}

}
