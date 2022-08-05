package com.elms.databaseservice.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.elms.databaseservice.models.Course;
import com.elms.databaseservice.models.Feedback;
import com.elms.databaseservice.models.Instructor;
import com.elms.databaseservice.models.Student;
import com.elms.databaseservice.models.StudentCourse;
import com.elms.databaseservice.repos.FeedbackRepo;

class FeedbackServiceTest {

	private FeedbackService service;
	private FeedbackRepo mockFeedbackRepo;

	@BeforeEach
	void setUp() {
		this.mockFeedbackRepo = mock(FeedbackRepo.class);
		this.service = new FeedbackService();
		service.repo = mockFeedbackRepo;
	}

	@Test
	void testGetAllFeedbacks() {
		Feedback feedback = new Feedback();
		feedback.setFeedbackId(10);
		feedback.setContent("good");
		feedback.setRatings(3);
		feedback.setStudentName("prateek");
		List<Feedback> feedbacks = Collections.singletonList(feedback);
		when(mockFeedbackRepo.findAll()).thenReturn(feedbacks);
		ResponseEntity<List<Feedback>> actual = service.getAllFeedbacks();
		ResponseEntity<List<Feedback>> expected = new ResponseEntity<List<Feedback>>(feedbacks, HttpStatus.OK);
		assertEquals(expected, actual);
	}

	@Test
	void testGetAllFeedbacksById() {
		Feedback feedback = new Feedback();

		feedback.setContent("good");
		feedback.setRatings(3);
		feedback.setStudentName("prateek");

		Student stu = new Student();
		stu.setStudentId(1);
		stu.setStudentEmail("prateek@123");
		stu.setStudentImage(null);
		stu.setStudentPassword("hello");

		Instructor inst = new Instructor();
		inst.setInstructorName("prateek");
		inst.setBankIfscCode("abc");
		inst.setInstructorEmail("prateek@123");
		inst.setInstructorImage(null);

		Course c = new Course();
		c.setCourseId(1);
		c.setCourseDescription("abcd");
		c.setCourseDiscount(10);
		c.setCourseImage(null);
		c.setTotalDuration(10);
		c.setRatings(5);
		c.setInstructorName("prateek");
		c.setInstructorId(inst);
		c.setCourseName("hmtl");

		StudentCourse sc = new StudentCourse();

		sc.setCourseCompletionPercent(50);
		sc.setCourseId(c);
		sc.setStudentId(stu);

		feedback.setStudentCourseId(sc);
		List<Feedback> feedbacks = Collections.singletonList(feedback);

		when(mockFeedbackRepo.findByStudentCourseId(1, 1)).thenReturn(feedback);
		when(mockFeedbackRepo.findByCourseId(1)).thenReturn(feedbacks);
		when(mockFeedbackRepo.save(feedback)).thenReturn(feedback);
		ResponseEntity<List<Feedback>> actual = service.getAllFeedbacksByCourseId(1);
		ResponseEntity<List<Feedback>> expected = new ResponseEntity<List<Feedback>>(feedbacks, HttpStatus.OK);
		assertEquals(expected, actual);
	}

	@Test
	void testStoreFeedback() {
		
		
		Student stu = new Student();
		stu.setStudentId(1);
		stu.setStudentEmail("prateek@123");
		stu.setStudentImage(null);
		stu.setStudentPassword("hello");

		Course c = new Course();
		c.setCourseId(1);
		c.setCourseDescription("abcd");
		c.setCourseDiscount(10);
		c.setCourseImage(null);
		c.setTotalDuration(10);
		c.setRatings(5);
		c.setInstructorName("prateek");
		c.setCourseName("hmtl");
		
		StudentCourse sc = new StudentCourse();

		sc.setCourseCompletionPercent(50);
		sc.setCourseId(c);
		sc.setStudentId(stu);
		
		
		Feedback feedback = new Feedback();
		feedback.setContent("good");
		feedback.setRatings(3);
		feedback.setStudentName("prateek");
		feedback.setStudentCourseId(sc);


		when(mockFeedbackRepo.findByStudentCourseId(2, 2)).thenReturn(null);

		when(mockFeedbackRepo.save(feedback)).thenReturn(feedback);

		ResponseEntity<String> expected = new ResponseEntity<>(
		"Stored Feedback " + feedback.getContent() + " successfully!", HttpStatus.CREATED);
		ResponseEntity<String> actual = service.storeFeedback(feedback, 2, 2);
		assertEquals(expected, actual);
	}
	
	
	@Test
	void testDeleteFeedback()
	{
		Student stu = new Student();
		stu.setStudentId(1);
		stu.setStudentEmail("prateek@123");
		stu.setStudentImage(null);
		stu.setStudentPassword("hello");

		Course c = new Course();
		c.setCourseId(1);
		c.setCourseDescription("abcd");
		c.setCourseDiscount(10);
		c.setCourseImage(null);
		c.setTotalDuration(10);
		c.setRatings(5);
		c.setInstructorName("prateek");
		c.setCourseName("hmtl");
		
		StudentCourse sc = new StudentCourse();

		sc.setCourseCompletionPercent(50);
		sc.setCourseId(c);
		sc.setStudentId(stu);
		
		
		Feedback feedback = new Feedback();
		feedback.setContent("good");
		feedback.setRatings(3);
		feedback.setStudentName("prateek");
		feedback.setStudentCourseId(sc);
		
		when(mockFeedbackRepo.findByStudentCourseId(1, 1)).thenReturn(feedback);
		ResponseEntity<String> actual = service.deleteFeedback(1, 1);
		 ResponseEntity<String> expected = new ResponseEntity<>("Deleted Feedback Successfully", HttpStatus.OK);
		 assertEquals(expected,actual);
	}
	
	void testExistFeedbackById()
	{
		Student stu = new Student();
		stu.setStudentId(1);
		stu.setStudentEmail("prateek@123");
		stu.setStudentImage(null);
		stu.setStudentPassword("hello");

		Course c = new Course();
		c.setCourseId(1);
		c.setCourseDescription("abcd");
		c.setCourseDiscount(10);
		c.setCourseImage(null);
		c.setTotalDuration(10);
		c.setRatings(5);
		c.setInstructorName("prateek");
		c.setCourseName("hmtl");
		
		StudentCourse sc = new StudentCourse();

		sc.setCourseCompletionPercent(50);
		sc.setCourseId(c);
		sc.setStudentId(stu);
		
		
		Feedback feedback = new Feedback();
		feedback.setContent("good");
		feedback.setRatings(3);
		feedback.setStudentName("prateek");
		feedback.setStudentCourseId(sc);
		
		when(mockFeedbackRepo.findByStudentCourseId(1, 1)).thenReturn(feedback);
		ResponseEntity<Feedback> actual = service.existFeedbackById(1, 1);
		ResponseEntity<Feedback> expected = new ResponseEntity<>(feedback, HttpStatus.OK);
		
		assertEquals(expected,actual);
	}
	
	@Test
	void testExistFeedbackById2()
	{
		when(mockFeedbackRepo.findByStudentCourseId(1, 1)).thenReturn(null);
		ResponseEntity<Feedback> actual = service.existFeedbackById(1, 1);
		ResponseEntity<Feedback> expected = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		assertEquals(expected,actual);
	}

}
