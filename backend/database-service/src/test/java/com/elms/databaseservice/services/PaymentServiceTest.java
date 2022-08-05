package com.elms.databaseservice.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.math.BigInteger;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.elms.databaseservice.models.Course;
import com.elms.databaseservice.models.Instructor;
import com.elms.databaseservice.models.InstructorCourse;
import com.elms.databaseservice.models.Lesson;
import com.elms.databaseservice.models.Payment;
import com.elms.databaseservice.models.Student;
import com.elms.databaseservice.models.StudentCourse;
import com.elms.databaseservice.repos.PaymentRepo;
import com.elms.databaseservice.repos.StudentCourseLessonRepo;
import com.elms.databaseservice.repos.StudentCourseRepo;

class PaymentServiceTest {

	private PaymentService service;
	private PaymentRepo mockPaymentRepo;
	private StudentCourseRepo mockStudentCourseRepo;
	private StudentCourseLessonRepo mockStudentCourseLessonRepo;

	@BeforeEach
	void setup() {
		this.service = new PaymentService();
		this.mockPaymentRepo = mock(PaymentRepo.class);
		this.mockStudentCourseRepo = mock(StudentCourseRepo.class);
		this.mockStudentCourseLessonRepo = mock(StudentCourseLessonRepo.class);
		service.paymentRepo=mockPaymentRepo;
		service.studentCourseLessonRepo=mockStudentCourseLessonRepo;
		service.studentCourseRepo=mockStudentCourseRepo;
	}

	@Test
	@Disabled
	void testCreatePayment() {
		InstructorCourse ic = new InstructorCourse();
		ic.setCourseId(1);
		ic.setInstructorId(5);

		Set<InstructorCourse> icl = new HashSet<InstructorCourse>();
		icl.add(ic);
		
		Lesson l1 = new Lesson();
		l1.setCourseId(1);
		l1.setLessonDuration(10);
		l1.setLessonId(1);
		
		Lesson l2 = new Lesson();
		l2.setCourseId(2);
		l2.setLessonDuration(20);
		l2.setLessonId(2);

		BigInteger acNumber = BigInteger.valueOf(10);
		Instructor inst = new Instructor();
		inst.setInstructorPassword("hello");
		inst.setInstructorName("Prateek");
		inst.setInstructorImage(null);
		inst.setInstructorId(1);
		inst.setInstructorEmail("prateek@123");
		inst.setBankIfscCode("abc");
		inst.setAccountNumber(acNumber);

		Course c = new Course();
		c.setCourseId(1);
		c.setCourseName("css");
		c.setCoursePrice(1000);
		c.setInstructorName("prateek");
		c.setCourseDescription("abc");
		c.setCourseDiscount(10);
		c.setCourseImage(null);
		c.setDatePublished(new Date(2021, 12, 2));
		c.setInstructorCourseDetails(icl);
		c.setInstructorId(inst);
		c.setLessons(List.of(l1,l2));

		Student stu = new Student();
		stu.setStudentId(5);
		stu.setStudentName("Radhika");
		stu.setStudentPassword("hello");

		Payment payment = new Payment();
		payment.setCourseId(c);;
		payment.setPaymentId(10);
		payment.setStudentId(stu);

		StudentCourse sc = new StudentCourse();
		sc.setCourseId(c);
		sc.setCourseStatus("pending");
		sc.setCurrentLessonId(1);
		sc.setStudentId(stu);

		when(mockPaymentRepo.save(payment)).thenReturn(payment);
		when(mockStudentCourseRepo.save(sc)).thenReturn(sc);

		ResponseEntity<String> actual = service.createPayment(payment);
		ResponseEntity<String> expected = new ResponseEntity<String>("Payment done successfully!", HttpStatus.OK);

		assertEquals(expected, actual);
	}
	
	@Test
	void testShowTotalRevenueByCourseId() {
		when(mockPaymentRepo.getTotalRevenueByCourseId(5)).thenReturn((float) 100);
		ResponseEntity<Float> actual = service.showTotalRevenueByCourseId(5);
		float totalcourserevenue=(float)100; 
		ResponseEntity<Float> expected = new ResponseEntity<>(totalcourserevenue,HttpStatus.OK);
		assertEquals(expected,actual);
	}
	
	@Test
	void testGetAllPayments() {
		
		Course c = new Course();
		c.setCourseId(1);
		c.setCourseName("css");
		c.setCoursePrice(1000);
		c.setInstructorName("prateek");
		c.setCourseDescription("abc");
		c.setCourseDiscount(10);
		c.setCourseImage(null);
		c.setDatePublished(new Date(2021, 12, 2));
//		c.setInstructorCourseDetails(icl);
//		c.setInstructorId(inst);
//		c.setLessons(List.of(l1,l2));
		
		Student stu = new Student();
		stu.setStudentId(5);
		stu.setStudentName("Radhika");
		stu.setStudentPassword("hello");
		
		Payment payment = new Payment();
		payment.setCourseId(c);;
		payment.setPaymentId(10);
		payment.setStudentId(stu);

		
		when(mockPaymentRepo.findAll()).thenReturn(List.of(payment));
		ResponseEntity<List<Payment>> actual = service.getAllPayments();
		ResponseEntity<List<Payment>> expected = new ResponseEntity<>(List.of(payment), HttpStatus.OK);
		
	}
}
