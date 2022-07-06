package com.elms.authenticationservice.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.elms.authenticationservice.models.Student;
import com.elms.authenticationservice.repos.StudentRepo;

@Service
public class StudentService {

	@Autowired
	StudentRepo repo;
	
	@Transactional
	public ResponseEntity<String> findStudentByEmail(String email) {
		Optional<Student> s=Optional.of(repo.findByEmail(email));
		if(s.isEmpty())
			return new ResponseEntity<>("No such user exists!",HttpStatus.NOT_FOUND);
		else
		{
			return new ResponseEntity<>("Logged In Successfully !",HttpStatus.FOUND);	
		}
	}
}
