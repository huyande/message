package com.zwxq;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication() //exclude={DataSourceAutoConfiguration.class,HibernateJpaAutoConfiguration.class}
@MapperScan("com.zwxq.mapper")
public class SentMaggessApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(SentMaggessApplication.class, args);
	}
}
