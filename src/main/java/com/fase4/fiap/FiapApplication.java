package com.fase4.fiap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.fase4.fiap")
public class FiapApplication {

	public static void main(String[] args) {
		SpringApplication.run(FiapApplication.class, args);
	}

}
