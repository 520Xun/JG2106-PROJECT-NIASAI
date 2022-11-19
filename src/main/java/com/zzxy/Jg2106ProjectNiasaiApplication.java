package com.zzxy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;
@EnableAsync //启动异步类注解
@SpringBootApplication
@EnableCaching //启动缓存注解
public class Jg2106ProjectNiasaiApplication {

	public static void main(String[] args) {
		SpringApplication.run(Jg2106ProjectNiasaiApplication.class, args);
	}

}
