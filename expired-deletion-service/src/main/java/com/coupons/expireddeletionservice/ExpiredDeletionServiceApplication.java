package com.coupons.expireddeletionservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ExpiredDeletionServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExpiredDeletionServiceApplication.class, args);
	}

}
