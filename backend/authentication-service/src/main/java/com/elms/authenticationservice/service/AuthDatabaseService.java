package com.elms.authenticationservice.service;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.elms.authenticationservice.models.Instructor;
import com.elms.authenticationservice.models.Student;
import com.elms.authenticationservice.repos.InstructorRepo;
import com.elms.authenticationservice.repos.StudentRepo;

@Service
public class AuthDatabaseService {

	@Autowired
	StudentRepo studentRepo;
	@Autowired
	InstructorRepo instructorRepo;

//	@Autowired
//	PasswordEncoder encoder;
	

	private Logger log = LoggerFactory.getLogger(AuthDatabaseService.class);
	
	@Transactional
	public Student findStudentByEmail(String email) {
		log.info("finding student by email");
		return studentRepo.findByStudentEmail(email);
	}
	@Transactional
	public Instructor findInstructorByEmail(String email) {
		log.info("finding instructor by Id");
		return instructorRepo.findByInstructorEmail(email);
	}

	//make necesasary change in encoding part
	@Transactional
	public int updateStudentPasswordByEmail(String email,String password)
	{
		log.info("updating student password");
		return studentRepo.updateStudentPassword(email, (password));
	}
	//make necesasary change in encoding part
	@Transactional
	public int updateInsructorPasswordByEmail(String email,String password)
	{
		log.info("updating instructor password");
		return instructorRepo.updateInstructorPassword(email, (password));
	}
}
