package com.elms.registrationservice.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.math.BigInteger;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import com.elms.registrationservice.models.Student;
import com.elms.registrationservice.models.Instructor;
import com.elms.registrationservice.repos.InstructorRepo;
import com.elms.registrationservice.repos.StudentRepo;

class RegisterServiceTest {
	
	private RegisterService service; 
	private StudentRepo mockStudentRepo;
	private InstructorRepo mockInstructorRepo;
	
	@BeforeEach
	void setUp() throws Exception {
		this.service = new RegisterService();
		this.mockInstructorRepo=mock(InstructorRepo.class);
		this.mockStudentRepo=mock(StudentRepo.class);
		service.instructorRepo=mockInstructorRepo;
		service.studentRepo=mockStudentRepo;
	}

	@Test
	void testGetAllStudents() {
		Student stu = new Student();
		stu.setId(1);
		stu.setImage(null);
		stu.setName("prateek");
		stu.setEmail("prateek@123");
		
		when(mockStudentRepo.findAll()).thenReturn(Collections.singletonList(stu));
		List<Student> list= service.getAllStudents();
		Student actual=list.get(0);
		String actualName=actual.getName();
		String expectedName="prateek";
		assertEquals(expectedName,actualName);
	}

	@Test
	void testRegisterStudent() {
		Student stu = new Student();
		stu.setId(1);
		stu.setImage(null);
		stu.setName("prateek");
		stu.setEmail("prateek@123");
		stu.setPassword("hello");
		when(mockStudentRepo.findByEmail("prateek@123")).thenReturn(stu);
		ResponseEntity expected = new ResponseEntity<>("User email already existed "+stu.getEmail()+" successfully!",  HttpStatus.NOT_IMPLEMENTED);
		ResponseEntity actual = service.registerStudent(stu);
		assertEquals(expected, actual);
	}
	
	void testRegisterInstructor()
	{
		Instructor inst = new Instructor();
		inst.setEmail("prateek@123");
		inst.setId(1);
		inst.setImage(null);
		inst.setName("prateek");
		inst.setPassword("password");
		
		when(mockInstructorRepo.findByEmail("prateek@123")).thenReturn(inst);
		ResponseEntity<String> actual = service.registerInstructor(inst);
		ResponseEntity expected = new ResponseEntity<>("User email already existed "+inst.getEmail()+" successfully!",  HttpStatus.NOT_IMPLEMENTED);
		assertEquals(expected,actual);
		
	}
	
}
