package com.hispeed;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

@Component
@SpringBootApplication
public class JiexunApplication {

	public static void main(String[] args) {
		SpringApplication.run(JiexunApplication.class, args);

		System.out.println("Springboot start success!!!!!");
	}
}
