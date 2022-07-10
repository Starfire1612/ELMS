package com.elms.registrationservice.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.elms.registrationservice.models.Instructor;
import com.elms.registrationservice.models.RegisterUser;
import com.elms.registrationservice.models.Student;
import com.elms.registrationservice.services.RegisterService;

@CrossOrigin("http://localhost:3000")
@RestController
public class RegistrationController {

	private Logger logger = org.slf4j.LoggerFactory.getLogger(RegistrationController.class);
	@Autowired
	RegisterService service;
	
	@Autowired
	PasswordEncoder encoder;

	@GetMapping(path = "/students")
	public List<Student> fetchAllStudents() {
		return service.getAllStudents();
	}

	@PostMapping(path = "/register-user/type/{type}")
	public ResponseEntity<String> registerUser(@PathVariable("type") String type, @RequestBody RegisterUser user) {
		logger.info(user.toString());
		if (type.equalsIgnoreCase("student")) {
			Student s = new Student();
			s.setName(user.getUsername());
			s.setEmail(user.getUseremail());
			s.setPassword(encoder.encode(user.getPassword()));
			return service.registerStudent(s);
		} else {
			Instructor i = new Instructor();
			i.setName(user.getUsername());
			i.setEmail(user.getUseremail());
			i.setPassword(encoder.encode(user.getPassword()));
			return service.registerInstructor(i);
		}
	}
}
