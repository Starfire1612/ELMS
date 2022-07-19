package com.elms.registrationservice.services;

import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.elms.registrationservice.models.Instructor;
import com.elms.registrationservice.models.Student;
import com.elms.registrationservice.repos.InstructorRepo;
import com.elms.registrationservice.repos.StudentRepo;

@Service
public class RegisterService {

	private Logger logger = org.slf4j.LoggerFactory.getLogger(RegisterService.class);
	@Autowired
	StudentRepo studentRepo;
	@Autowired
	InstructorRepo instructorRepo;

	@Transactional
	public List<Student> getAllStudents() {
		logger.info("Inside Student Service");
		return studentRepo.findAll();
	}

	@Transactional
	public ResponseEntity<String> registerStudent(Student s) {
		Student existingStudent = studentRepo.findByEmail(s.getEmail());
		if (existingStudent == null) {
			studentRepo.save(s);
			return new ResponseEntity<>("Registered Student "+s.getEmail()+" successfully!",  HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>("User email already existed "+s.getEmail()+" successfully!",  HttpStatus.NOT_IMPLEMENTED);
		}
	}
	@Transactional
	public ResponseEntity<String> registerInstructor(Instructor i) {
		Instructor existingInstructor = instructorRepo.findByEmail(i.getEmail());
		if (existingInstructor == null) {
			instructorRepo.save(i);
			return new ResponseEntity<>("Registered Instructor "+i.getEmail()+" successfully!",  HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>("User email already existed "+i.getEmail()+" successfully!",  HttpStatus.NOT_IMPLEMENTED);
		}
	}

}
