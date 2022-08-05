package com.elms.authenticationservice.service;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import java.math.BigInteger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.elms.authenticationservice.models.Instructor;
import com.elms.authenticationservice.models.Student;
import com.elms.authenticationservice.repos.InstructorRepo;
import com.elms.authenticationservice.repos.StudentRepo;


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
		paramStu.setStudentEmail("prateek@123");
		paramStu.setStudentImage(null);
		paramStu.setStudentName("prateek");
		paramStu.setStudentPassword("hello");
		when(mockStudentRepo.findByStudentEmail("prateek@123")).thenReturn(paramStu);
		
		Student actual = service.findStudentByEmail("prateek@123");
		String actualName=actual.getStudentName();
		assertEquals("prateek", actualName);
	}
	

	@Test
	void findingInstructorByEmail()
	{
		Instructor paramInst=new Instructor();
		BigInteger i = BigInteger.valueOf(123);
		paramInst.setAccountNumber(i);
		paramInst.setBankIfscCode("IFSC");
		paramInst.setInstructorEmail("prateek@123");
		paramInst.setInstructorImage(null);
		paramInst.setInstructorName("prateek");
		paramInst.setInstructorPassword("hello");
		
		when(this.mockInstructorRepo.findByInstructorEmail("prateek@123")).thenReturn(paramInst);
		
		Instructor actual = service.findInstructorByEmail("prateek@123");
		String actualName=actual.getInstructorName();
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

