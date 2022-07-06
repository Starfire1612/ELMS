package com.elms.registrationservice.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.elms.registrationservice.models.Student;
import com.elms.registrationservice.services.StudentService;

@CrossOrigin("http://localhost:3000")
@RestController
public class RegistrationController {

	private Logger logger = org.slf4j.LoggerFactory.getLogger(RegistrationController.class);
	@Autowired
	StudentService service;

	@GetMapping(path = "/students")
	public List<Student> fetchAllStudents() {
		return service.getAllStudents();
	}

	@PostMapping(path = "/register-user/type/{type}")
	public ResponseEntity<String> registerUser( @PathVariable("type") String type,@RequestBody Student user) {
	
		logger.info(type);
		logger.info(user.toString());
		if(type.equalsIgnoreCase("student")) {
			Student s=(Student)user;
			logger.info(s.toString());
			return service.registerNewStudent(s);
		} else {
			return null;
		}
	}
}
