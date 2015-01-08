package com.beerbuddy.web.apprunner;

import org.springframework.boot.SpringApplication;

import com.beerbuddy.web.config.AppConfig;

public class AppRunner {
	
	public static void main(String[] args) throws Exception {
		SpringApplication.run(AppConfig.class, args);
	}
}
