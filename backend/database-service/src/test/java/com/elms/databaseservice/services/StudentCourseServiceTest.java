package com.elms.databaseservice.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.elms.databaseservice.models.Course;
import com.elms.databaseservice.models.Student;
import com.elms.databaseservice.models.StudentCourse;
import com.elms.databaseservice.models.StudentCourseId;
import com.elms.databaseservice.repos.StudentCourseRepo;

class StudentCourseServiceTest {

	private StudentCourseRepo mockStudentCourseRepo;
	private StudentCourseService service;
	
	
	@BeforeEach
	void setUp() throws Exception {
		this.service = new StudentCourseService();
		this.mockStudentCourseRepo=mock(StudentCourseRepo.class);
		service.studentCourseRepo=mockStudentCourseRepo;
	}

	@Test
	void testAddLessonIdInStudentCourse() {
		Student s = new Student();
		s.setStudentId(1);
		s.setStudentName("preteek");
		s.setStudentPassword("hello");
		
		
		Course c = new Course();
		c.setCourseId(1);
		c.setCourseName("css");
		c.setCoursePrice(1000);
		c.setInstructorName("prateek");
		c.setCourseDescription("abc");
		c.setCourseDiscount(10);
		c.setCourseImage(null);
		
		
		
		StudentCourse sc=  new StudentCourse();
		sc.setCourseId(c);
		sc.setCourseCompletionPercent(10);
		sc.setCourseStatus("pending");
		sc.setCurrentLessonId(1);
		sc.setStudentId(s);
		
		StudentCourseId sci = new StudentCourseId();
		sci.setCourseId(1);
		sci.setStudentId(1);
		
		
		when(mockStudentCourseRepo.findById(sci)).thenReturn(Optional.of(sc));
		
		ResponseEntity<String> actual = service.addLessonIdInStudentCourse(1, 1, 1);
		ResponseEntity<String> expected = new ResponseEntity<>("Current Lesson ID updated",HttpStatus.OK);
		
		assertEquals(expected, actual);
		 
	}

}
