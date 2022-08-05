package com.elms.registrationservice.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import java.math.BigInteger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.elms.registrationservice.models.Instructor;
import com.elms.registrationservice.models.Student;
import com.elms.registrationservice.repos.InstructorRepo;
import com.elms.registrationservice.repos.StudentRepo;


class AuthDatabaseServiceTest {
	
	private StudentRepo mockStudentRepo;
	private InstructorRepo mockInstructorRepo;
	private AuthDatabaseService service;
	@BeforeEach
	void setUp()
	{
		this.mockInstructorRepo  = mock(InstructorRepo.class);
		this.mockStudentRepo = mock(StudentRepo.class);
		this.service = new AuthDatabaseService();
		service.instructorRepo=mockInstructorRepo;
		service.studentRepo=mockStudentRepo;
	}
	
	@Test
	void findingStudentByEmail() {
		Student paramStu = new Student();
		paramStu.setEmail("prateek@123");
		paramStu.setImage(null);
		paramStu.setName("prateek");
		paramStu.setPassword("hello");
		when(mockStudentRepo.findByEmail("prateek@123")).thenReturn(paramStu);
		
		Student actual = service.findStudentByEmail("prateek@123");
		String actualName=actual.getName();
		assertEquals("prateek", actualName);
	}
	

	@Test
	void findingInstructorByEmail()
	{
		Instructor paramInst=new Instructor();
		paramInst.setEmail("prateek@123");
		paramInst.setImage(null);
		paramInst.setName("prateek");
		paramInst.setPassword("hello");
	
		
		when(this.mockInstructorRepo.findByEmail("prateek@123")).thenReturn(paramInst);
		
		Instructor actual = service.findInstructorByEmail("prateek@123");
		String actualName=actual.getName();
		assertEquals("prateek", actualName);
	}
	
	
	@Test
	void isPasswordChangedByEmailForStudent()
	{
		when(this.mockStudentRepo.updateStudentPassword("prateek@123","hello")).thenReturn(1);
		int actual = service.updateStudentPasswordByEmail("prateek@123", "hello");
		assertEquals(1,actual);
	}
	
	@Test
	void isPasswordChangedByEmailForInstructor()
	{
		when(this.mockInstructorRepo.updateInstructorPassword("prateek@123","hello")).thenReturn(1);
		int expected=1;
		assertEquals(expected,service.updateInsructorPasswordByEmail("prateek@123","hello"));
	}

}

