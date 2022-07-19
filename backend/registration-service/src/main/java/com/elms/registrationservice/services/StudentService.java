package com.elms.registrationservice.services;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.elms.registrationservice.models.Student;
import com.elms.registrationservice.repos.StudentRepo;

@Service
public class StudentService {

	private Logger logger = org.slf4j.LoggerFactory.getLogger(StudentService.class);
	@Autowired
	StudentRepo repo;

	@Transactional
	public List<Student> getAllStudents() {
		logger.info("Inside Student Service");
		return repo.findAll();
	}

	@Transactional
	public ResponseEntity<String> registerNewStudent(Student s) {
		logger.info("Registering New Student");
		Optional<Student> existingStudent=Optional.ofNullable(repo.findByEmail(s.getEmail()));
		if(!existingStudent.isEmpty())
			return new ResponseEntity<>("Student with this email id already  exists",
				HttpStatus.NOT_IMPLEMENTED);
		else {
			repo.save(s);
			return new ResponseEntity<>("Student registered successfully!",
					HttpStatus.CREATED);
		}
	}

}
