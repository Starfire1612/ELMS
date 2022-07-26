package com.elms.databaseservice.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "authentication-service", url = "http://localhost:8080")
public interface AuthClient {

	@PostMapping("/authorize/{id}")
	public boolean authorizeTheRequest(
			@RequestHeader(value = "Authorization", required = true) String requestTokenHeader,@PathVariable int id);
	
}
