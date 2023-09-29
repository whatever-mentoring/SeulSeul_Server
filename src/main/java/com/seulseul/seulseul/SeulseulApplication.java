package com.seulseul.seulseul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class SeulseulApplication {

	public static void main(String[] args) {
		SpringApplication.run(SeulseulApplication.class, args);
	}

}
