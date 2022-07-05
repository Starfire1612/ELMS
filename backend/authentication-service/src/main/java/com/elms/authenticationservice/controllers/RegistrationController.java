package com.elms.authenticationservice.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.elms.authenticationservice.models.Instructor;
import com.elms.authenticationservice.models.Student;
import com.elms.authenticationservice.repos.InstructorRepo;
import com.elms.authenticationservice.repos.StudentRepo;

@CrossOrigin("http://localhost:3000")
@RestController
public class RegistrationController {

	private static final Logger LOGGER = LoggerFactory.getLogger(RegistrationController.class);
	@Autowired
	StudentRepo studentRepo;
	@Autowired
	InstructorRepo instructorRepo;

	@PostMapping(path = "/register-user/{user}/type/{type}")
	public ResponseEntity<String> addUser(@PathVariable("user") Object user, @PathVariable("type") String type) {
		if (type.equalsIgnoreCase("student")) {
			Student s=(Student)user;
			LOGGER.info(s.toString());
			studentRepo.save(s);
			return new ResponseEntity<>("Student Registration Succesfull!", HttpStatus.CREATED);
		} else {
			instructorRepo.save((Instructor) user);
			return new ResponseEntity<>("Instructor Registration Succesfull!", HttpStatus.CREATED);
		}	
	}
	
	@GetMapping(path="/allstudents")
	public List<Student> getAllStudents(){
		return studentRepo.findAll();
	}
}
