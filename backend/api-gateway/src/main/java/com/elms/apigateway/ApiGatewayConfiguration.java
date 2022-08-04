package com.elms.apigateway;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class ApiGatewayConfiguration {
	@Bean
	public RouteLocator gatewayRouter(RouteLocatorBuilder builder) {
		return builder.routes()
				.route(p -> p.path("/authenticate/**").uri("lb://authentication-service"))
				.route(p -> p.path("/greetings/**").uri("lb://authentication-service"))
				.route(p -> p.path("/authorize/**").uri("lb://authentication-service"))
				.route(p -> p.path("/forgot-password/**").uri("lb://authentication-service"))
				.route(p -> p.path("/course/**").uri("lb://database-service"))
				.route(p -> p.path("/student/**").uri("lb://database-service"))
				.route(p -> p.path("/instructor/**").uri("lb://database-service"))
				.route(p -> p.path("/all-payments/**").uri("lb://database-service"))
				.route(p -> p.path("/register-user/**").uri("lb://registration-service"))
				.route(p -> p.path("/registertration/**").uri("lb://registration-service"))
				.build();
	}

}
