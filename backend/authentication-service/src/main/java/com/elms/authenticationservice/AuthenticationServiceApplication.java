package com.elms.authenticationservice;

import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@ComponentScan({"com.elms.authenticationservice.services","com.elms.authenticationservice.config","com.elms.authenticationservice.*"})
public class AuthenticationServiceApplication {
	private static final org.slf4j.Logger LOGGER=LoggerFactory.getLogger(AuthenticationServiceApplication.class);
	public static void main(String[] args) {
		LOGGER.info("<===========================>");
		LOGGER.info("Starting Authentication Service");
		SpringApplication.run(AuthenticationServiceApplication.class, args);
	}

}
