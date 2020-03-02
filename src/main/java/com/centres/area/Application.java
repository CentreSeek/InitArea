package com.centres.area;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@MapperScan(value = "com.centres.area.mapper")
@EnableCaching //开启基于注解的缓存
public class Application {


	public static void main(String[] args) {
    	SpringApplication.run(Application.class, args);
	}

}
