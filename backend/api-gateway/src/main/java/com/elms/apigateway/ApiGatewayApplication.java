package com.elms.apigateway;

import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApiGatewayApplication {
	private static final org.slf4j.Logger LOGGER=LoggerFactory.getLogger(ApiGatewayApplication.class);
	public static void main(String[] args) {
		LOGGER.info("<========================>");
		LOGGER.info("Starting API Gateway ");
		SpringApplication.run(ApiGatewayApplication.class, args);
	}

}
