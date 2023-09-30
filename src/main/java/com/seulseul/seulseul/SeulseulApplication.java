package com.seulseul.seulseul;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@OpenAPIDefinition(servers = {@Server(url = "https://seulseul.kr", description = "Default Server URL")})
@EnableScheduling
@SpringBootApplication
public class SeulseulApplication {

	public static void main(String[] args) {
		SpringApplication.run(SeulseulApplication.class, args);
	}

}
