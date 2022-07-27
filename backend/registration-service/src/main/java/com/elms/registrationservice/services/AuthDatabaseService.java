package com.elms.registrationservice.services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.elms.registrationservice.models.Instructor;
import com.elms.registrationservice.models.Student;
import com.elms.registrationservice.repos.InstructorRepo;
import com.elms.registrationservice.repos.StudentRepo;

@Service
public class AuthDatabaseService {

	@Autowired
	StudentRepo studentRepo;
	@Autowired
	InstructorRepo instructorRepo;

//	@Autowired
//	PasswordEncoder encoder;
	@Transactional
	public Student findStudentByEmail(String email) {
		return studentRepo.findByEmail(email);
	}
	@Transactional
	public Instructor findInstructorByEmail(String email) {
		return instructorRepo.findByEmail(email);
	}

	//make necesasary change in encoding part
	@Transactional
	public int updateStudentPasswordByEmail(String email,String password)
	{
		return studentRepo.updateStudentPassword(email, (password));
	}
	//make necesasary change in encoding part
	@Transactional
	public int updateInsructorPasswordByEmail(String email,String password)
	{
		return instructorRepo.updateInstructorPassword(email, (password));
	}
}
