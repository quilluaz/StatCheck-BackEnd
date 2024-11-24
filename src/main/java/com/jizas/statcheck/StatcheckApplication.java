package com.jizas.statcheck;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class StatcheckApplication {

	public static void main(String[] args) {
		SpringApplication.run(StatcheckApplication.class, args);
	}

}