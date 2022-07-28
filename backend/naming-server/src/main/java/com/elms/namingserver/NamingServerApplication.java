package com.elms.namingserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class NamingServerApplication {

	private Logger log = LoggerFactory.getLogger(NamingServerApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(NamingServerApplication.class, args);
	}

}
