package com.temp.inflow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = "com.temp.inflow")
@EnableJpaRepositories(basePackages = "com.temp.inflow.repository")
@EntityScan(basePackages = "com.temp.inflow.model")
public class InflowApplication {
	public static void main(String[] args) {
		SpringApplication.run(InflowApplication.class, args);
	}
}