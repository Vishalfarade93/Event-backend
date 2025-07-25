package com.event;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class EventModuleApplication {

	public static void main(String[] args) {
		SpringApplication.run(EventModuleApplication.class, args);
	}

}
