package com.example.authenticationexample.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.example.authenticationexample.config.JwtTokenUtil;
import com.example.authenticationexample.models.Instructor;
import com.example.authenticationexample.models.Student;
import com.example.authenticationexample.service.AuthDatabaseService;

@RestController
@CrossOrigin("http://localhost:3000")
public class GreetingController {

	@Autowired
	JwtTokenUtil jwtTokenUtil;
	
	@Autowired
	AuthDatabaseService databaseService;
	@GetMapping("/greetings")
	public String greetUser(@RequestHeader("Authorization") String authorization) {
		String token=fetchToken(authorization);
		String username=jwtTokenUtil.getUsernameFromToken(token);
		String userType=jwtTokenUtil.getUserTypeFromToken(token);
		if(userType.equalsIgnoreCase("student")) {
			Student s=databaseService.findStudentByEmail(username);
			return s.toString();
		}
		else {
			Instructor i=databaseService.findInstructorByEmail(username);
			return i.toString();
		}
	}
	
	private String fetchToken(String authHeader) {
		return authHeader.substring(7);
	}
}
