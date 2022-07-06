package com.elms.authenticationservice.controllers;

import java.util.Base64;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.elms.authenticationservice.models.User;
import com.elms.authenticationservice.service.StudentService;

@CrossOrigin("http://localhost:3000")
@RestController
public class LogInController {

	private Logger logger = LoggerFactory.getLogger(LogInController.class);

	@Autowired
	StudentService studentService;

	@PostMapping(path = "/authenticate/type/{type}")
	public ResponseEntity<String> loginViaUsernameAndEmail(@PathVariable("type") String type,
			@RequestBody User user) {
		logger.info(user.toString());
		return null;

	}
	@GetMapping(value = "/greet")
	public String greetUser() {
		logger.info("Greet user");
		return "Hello everyone";

	}

	private String getUser(String authHeader) {
		authHeader = authHeader.substring(6);
		String s = (new String(Base64.getDecoder().decode(authHeader)));
		return s;
	}

}
//@PostMapping(path = "/authenticate/type/{type}")
//public ResponseEntity<String> loginViaUsernameAndEmail(@PathVariable("type") String type,
//		@RequestHeader("Authorization") String auth) {
//	logger.info(auth);
//	if (type.equalsIgnoreCase("student")) {
//		String usercredentials = getUser(auth);
//		logger.info(usercredentials);
//		return studentService.findStudentByEmail(usercredentials.split(":")[0]);
//	} else {
//		return null;
//	}
//
//}