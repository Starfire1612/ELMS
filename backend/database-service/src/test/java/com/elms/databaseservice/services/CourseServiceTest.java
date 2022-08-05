package com.elms.databaseservice.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;

import com.elms.databaseservice.models.Course;
import com.elms.databaseservice.models.CourseFeedbackDetails;
import com.elms.databaseservice.models.Feedback;
import com.elms.databaseservice.models.StudentCourse;
import com.elms.databaseservice.repos.CourseRepo;
import com.elms.databaseservice.repos.FeedbackRepo;

import antlr.collections.List;
import io.micrometer.core.instrument.util.IOUtils;

class CourseServiceTest {

	private CourseService service;
	private CourseRepo mockCourseRepo;
	private FeedbackRepo mockFeedbackRepo;
	
	@BeforeEach
	void setup()
	{
		this.mockCourseRepo=mock(CourseRepo.class);
		this.mockFeedbackRepo=mock(FeedbackRepo.class);
		this.service = new CourseService();
		service.courseRepo=mockCourseRepo;
		service.feedbackRepo=mockFeedbackRepo;
		
	}

	@Test
	void testAddCourse() {
			Course c = new Course();
			c.setCoursePrice(1000);
			c.setCourseName("html");
			c.setCourseId(10);
			when(mockCourseRepo.save(c)).thenReturn(c);
			int actual = service.addCourse(c);
			assertEquals(10, actual);
	}
	
	@Test 
	void testPublishCourse() {
		Course c = new Course();
		c.setCoursePrice(1000);
		c.setCourseName("html");
		c.setCourseId(10);
		when(mockCourseRepo.save(c)).thenReturn(c);
		ResponseEntity<String> actual = service.publishCourse(c);
		ResponseEntity<String> expected=new ResponseEntity<>("Course published succssfully!", HttpStatus.CREATED);
		assertEquals(expected,actual);
		
	}
	
	
	@Test
	void testGetAllCourses()
	{
		Course c = new Course();
		c.setCoursePrice(1000);
		c.setCourseName("html");
		c.setCourseId(1);
		when(mockCourseRepo.findAll()).thenReturn(Collections.singletonList(c));
		ResponseEntity<Page<Course>> actual = service.getAllCourses(1, 1, 1);
		ResponseEntity<Page<Course>> expected= new ResponseEntity<>(HttpStatus.OK);	
	}
	
	@Test
	void testGetCourseRelatedDetails() {
		Course c = new Course();
		c.setCoursePrice(1000);
		c.setCourseName("html");
		c.setCourseId(10);
		
		StudentCourse sc = new StudentCourse();
		sc.setCourseId(c);
		sc.setCurrentLessonId(1);
		sc.setCourseStatus("pending");
		
		Feedback f = new Feedback();
		f.setContent("abc");
		f.setRatings(4);
		f.setStudentCourseId(sc);
		f.setStudentName("prateek");
		
		CourseFeedbackDetails courseFeedbackDetails = new CourseFeedbackDetails();
		courseFeedbackDetails.setCourse(c);
		courseFeedbackDetails.setFeedbacks(null);
		
		when(mockCourseRepo.findById(10)).thenReturn(c);
		when(mockFeedbackRepo.findByCourseId(10)).thenReturn(null);
		
		ResponseEntity<CourseFeedbackDetails> actual = service.getCourseRelatedDetails(10);
		ResponseEntity<CourseFeedbackDetails> expected = new ResponseEntity<CourseFeedbackDetails>(courseFeedbackDetails, HttpStatus.OK);
		assertEquals(expected,actual);
	}
	
}
