package com.elms.apigateway;

import java.util.Arrays;
import java.util.Collections;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

@Component
public class ApiGatewayConfiguration {
	@Bean
	public RouteLocator gatewayRouter(RouteLocatorBuilder builder) {
		return builder.routes().route(p -> p.path("/authenticate/**").uri("lb://authentication-service"))
				.route(p -> p.path("/greetings/**").uri("lb://authentication-service"))
				.route(p -> p.path("/authorize/**").uri("lb://authentication-service"))
				.route(p -> p.path("/forgot-password/**").uri("lb://authentication-service"))
				.route(p -> p.path("/course/**").uri("lb://database-service"))
				.route(p -> p.path("/student/**").uri("lb://database-service"))
				.route(p -> p.path("/instructor/**").uri("lb://database-service"))
				.route(p -> p.path("/all-payments/**").uri("lb://database-service"))
				.route(p -> p.path("/register-user/**").uri("lb://registration-service"))
				.route(p -> p.path("/registration/**").uri("lb://registration-service")).build();
	}

	@Bean
	public CorsWebFilter corsWebFilter() {

		final CorsConfiguration config = new CorsConfiguration();
		config.setAllowedOrigins(Collections.singletonList("http://localhost:3000"));
		config.setAllowedMethods(Collections.singletonList("*"));
		config.setAllowCredentials(true);
		config.setAllowedHeaders(Collections.singletonList("*"));
		config.setMaxAge(3600L);
		config.setAllowedHeaders(Collections.singletonList("*"));
		config.addExposedHeader("Otp");
//		config.setExposedHeaders(Arrays.asList("Otp"));

		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", config);

		return new CorsWebFilter(source);
	}
}
