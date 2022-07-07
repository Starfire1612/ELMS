package com.example.authenticationexample.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.authenticationexample.models.Instructor;
import com.example.authenticationexample.models.Student;
import com.example.authenticationexample.repos.InstructorRepo;
import com.example.authenticationexample.repos.StudentRepo;

@Service
public class AuthDatabaseService {

	@Autowired
	StudentRepo studentRepo;
	@Autowired
	InstructorRepo instructorRepo;

	@Transactional
	public Student findStudentByEmail(String email) {
		return studentRepo.findByEmail(email);
	}
	@Transactional
	public Instructor findInstructorByEmail(String email) {
		return instructorRepo.findByEmail(email);
	}
//	public UserDao save(UserDTO user) {
//		DAOUser newUser = new DAOUser();
//		newUser.setUsername(user.getUsername());
//		newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
//		return userDao.save(newUser);
//	}
}
