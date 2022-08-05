package com.elms.databaseservice.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.mail.MessagingException;

import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.elms.databaseservice.models.Course;
import com.elms.databaseservice.models.Student;
import com.elms.databaseservice.models.StudentCourse;
import com.elms.databaseservice.models.StudentCourseId;
import com.elms.databaseservice.repos.CourseRepo;
import com.elms.databaseservice.repos.StudentCourseRepo;
import com.elms.databaseservice.repos.StudentRepo;
import com.itextpdf.text.DocumentException;

class StudentServiceTest {
	
	private StudentService service;
	private StudentRepo mockStudentRepo;
	private CourseRepo mockCourseRepo;
	private StudentCourseRepo mockStudentCourseRepo;
	private EmailService mockEmailService;
	private PdfGenerationService mockPdfGenerationService;
	private PaymentService mockPaymentService;

	@BeforeEach
	void setUp() throws Exception {
		this.service= new StudentService();
		this.mockCourseRepo=mock(CourseRepo.class);
		this.mockEmailService=mock(EmailService.class);
		this.mockPaymentService=mock(PaymentService.class);
		this.mockPdfGenerationService=mock(PdfGenerationService.class);
		this.mockStudentCourseRepo=mock(StudentCourseRepo.class);
		this.mockStudentRepo=mock(StudentRepo.class);
		service.courseRepo = mockCourseRepo;
		service.emailService=mockEmailService;
		service.paymentService = mockPaymentService;
		service.pdfGenerationService=mockPdfGenerationService;
		service.studentCourseRepo = mockStudentCourseRepo;
		service.studentRepo = mockStudentRepo;
	}

	@Test
	void testGetEnrolledCourse() {
		int studentId=1;
		Student s = new Student();
		s.setStudentId(1);
		s.setStudentName("preteek");
		s.setStudentPassword("hello");
		when(mockStudentRepo.findById(studentId)).thenReturn(s);
		ResponseEntity<List<Course>> actual = service.getEnrolledCourses(1);
		System.out.println(actual);
		List<Course> enrolledCourses = new ArrayList<Course>();
		ResponseEntity<List<Course>> expected = new ResponseEntity<List<Course>>(enrolledCourses, HttpStatus.OK);
		assertEquals(expected,actual);
	}
	@Test
	void testGetEnrolledCourse2() {
		int studentId=1;
		when(mockStudentRepo.findById(studentId)).thenReturn(null);
		ResponseEntity<List<Course>> actual = service.getEnrolledCourses(1);
		List<Course> enrolledCourses = new ArrayList<Course>();
		ResponseEntity<List<Course>> expected = new ResponseEntity<>(enrolledCourses, HttpStatus.OK);
		assertEquals(expected,actual);
	}
	
	@Test
	void testGetProfile() {
		Student s = new Student();
		s.setStudentId(1);
		s.setStudentName("preteek");
		s.setStudentPassword("hello");
		
		when(mockStudentRepo.findById(1)).thenReturn(s);
		ResponseEntity<Student> expected = new ResponseEntity<Student>(s, HttpStatus.OK);
		ResponseEntity<Student> actual = service.getProfile(1);
		assertEquals(expected, actual);
	}
	
	@Test
	void testGetProfile2() {
		
		when(mockStudentRepo.findById(1)).thenReturn(null);
		Student studentDetails=null;
		ResponseEntity<Student> expected = new ResponseEntity<Student>(studentDetails, HttpStatus.NOT_FOUND);
		ResponseEntity<Student> actual = service.getProfile(1);
		assertEquals(expected, actual);
	}
	
	@Test
	void testSendCertificate() throws FileNotFoundException, DocumentException, MessagingException {
		
		StudentCourseId sci = new StudentCourseId();
		sci.setCourseId(1);
		sci.setStudentId(1);
		
		StudentCourse sc = new StudentCourse();
		sc.setCourseCompletionPercent(100);
		sc.setCourseStatus("Completed");
		
		when(mockStudentCourseRepo.findById(sci)).thenReturn(Optional.of(sc));
		ResponseEntity<String> actual = service.sendCertificate(1,1);
		ResponseEntity<String> expected = new ResponseEntity<>("Course Completion Certificate Mail Sent Successfully!", HttpStatus.CREATED);
		assertEquals(expected,actual);
	}
	
	@Test
	void testGetCourseDetails()
	{

		StudentCourseId sci = new StudentCourseId();
		sci.setCourseId(1);
		sci.setStudentId(1);
		
		StudentCourse sc = new StudentCourse();
		sc.setCourseCompletionPercent(100);
		sc.setCourseStatus("Completed");
		
		when(mockStudentCourseRepo.findById(sci)).thenReturn(Optional.of(sc));
		ResponseEntity<StudentCourse> expected = new ResponseEntity<>(sc, HttpStatus.OK);
		ResponseEntity<StudentCourse> actual = service.getCourseDetails(1, 1);
		assertEquals(expected,actual);
	}
	
	@Test
	void testGetCourseDetails2()
	{

		StudentCourseId sci = new StudentCourseId();
		sci.setCourseId(1);
		sci.setStudentId(1);
		
		StudentCourse sc = new StudentCourse();
		sc.setCourseCompletionPercent(100);
		sc.setCourseStatus("Completed");
		
		when(mockStudentCourseRepo.findById(sci)).thenReturn(null);
		ResponseEntity<StudentCourse> expected = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		ResponseEntity<StudentCourse> actual = service.getCourseDetails(1, 1);
		assertEquals(expected,actual);
	}
	
	@Test
	void testGetSearchCourse()
	{
		Course c = new Course();
		c.setCourseId(1);
		List<Course>courses =List.of(c);
		when(mockCourseRepo.searchCourse(1,"css")).thenReturn(courses);
		ResponseEntity<List<Course>> actual = service.getSearchCourses(1,"css");
		ResponseEntity<List<Course>> expected = new ResponseEntity<>(courses, HttpStatus.OK);
		assertEquals(expected,actual);
	}
	
	@Test
	void testGetSearchCourse2()
	{
		List<Course>courses = null;
		when(mockCourseRepo.searchCourse(1,"css")).thenReturn(courses);
		ResponseEntity<List<Course>> actual = service.getSearchCourses(1,"css");
		ResponseEntity<List<Course>> expected = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		assertEquals(expected,actual);
	}
	
	
	@Test
	void testUpdateProfile()
	{
		Student s = new Student();
		s.setStudentId(1);
		s.setStudentName("preteek");
		s.setStudentPassword("hello");
		
		when(mockStudentRepo.findById(1)).thenReturn(s);
		when(mockStudentRepo.save(s)).thenReturn(s);
		ResponseEntity<Student> actual = service.updateProfile(s);
		ResponseEntity<Student> expected = new ResponseEntity<>(s, HttpStatus.CREATED);
		assertEquals(expected,actual);
	}
	
	@Test
	void testUpdateProfile2()
	{
		Student s = new Student();
		s.setStudentId(1);
		s.setStudentName("preteek");
		s.setStudentPassword("hello");
		when(mockStudentRepo.findById(1)).thenReturn(null);
		ResponseEntity<Student> actual = service.updateProfile(s);
		ResponseEntity<Student> expected = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		assertEquals(expected,actual);
	}


}
