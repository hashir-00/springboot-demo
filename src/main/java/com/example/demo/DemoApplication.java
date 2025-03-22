package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
@EnableJpaRepositories(basePackages = "com.example.demo.data.repository")
@EntityScan(basePackages = "com.example.demo.data.entity")
public class DemoApplication {

	private static final Logger logger = LoggerFactory.getLogger(DemoApplication.class);

	public static void main(String[] args) {
		try{
			// Start the application and get the ApplicationContext
			ApplicationContext context = SpringApplication.run(DemoApplication.class, args);

			// Retrieve the server.port from the environment
			Environment env = context.getEnvironment();
			String port = env.getProperty("server.port", "8080"); // Default to 8080 if not set

			logger.info("Server Running on port " + port);
		}catch(Exception e){
			logger.error(e.getMessage());
		}
	}


}