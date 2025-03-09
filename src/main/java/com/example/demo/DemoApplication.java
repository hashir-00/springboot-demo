package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.example.demo.data.repository")
@EntityScan(basePackages = "com.example.demo.data.entity")
public class DemoApplication {

	public static void main(String[] args) {
		// Start the application and get the ApplicationContext
		ApplicationContext context = SpringApplication.run(DemoApplication.class, args);

		// Retrieve the server.port from the environment
		Environment env = context.getEnvironment();
		String port = env.getProperty("server.port", "8080"); // Default to 8080 if not set

		System.out.println("Server Running on port " + port);
	}
}