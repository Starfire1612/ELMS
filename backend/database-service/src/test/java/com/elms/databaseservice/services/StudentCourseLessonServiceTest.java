package com.elms.databaseservice.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;

import javax.mail.internet.NewsAddress;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.elms.databaseservice.models.Course;
import com.elms.databaseservice.models.Lesson;
import com.elms.databaseservice.models.Student;
import com.elms.databaseservice.models.StudentCourse;
import com.elms.databaseservice.models.StudentCourseId;
import com.elms.databaseservice.models.StudentCourseLesson;
import com.elms.databaseservice.repos.CourseRepo;
import com.elms.databaseservice.repos.LessonRepo;
import com.elms.databaseservice.repos.StudentCourseLessonRepo;
import com.elms.databaseservice.repos.StudentCourseRepo;
import com.elms.databaseservice.repos.StudentRepo;

class StudentCourseLessonServiceTest {
	
	private StudentCourseLessonService service;
	private StudentRepo mockStudentRepo;
	private CourseRepo mockCourseRepo;
	private StudentCourseRepo mockStudentCourseRepo;
	private StudentCourseLessonRepo mockStudentCourseLessonRepo;
	private LessonRepo mockLessonRepo;

	@BeforeEach
	void setUp() throws Exception {
		this.service= new StudentCourseLessonService();
		this.mockStudentRepo=mock(StudentRepo.class);
		this.mockCourseRepo=mock(CourseRepo.class);
		this.mockLessonRepo=mock(LessonRepo.class);
		this.mockStudentCourseRepo=mock(StudentCourseRepo.class);
		this.mockStudentCourseLessonRepo = mock(StudentCourseLessonRepo.class);
		service.courseRepo=mockCourseRepo;
		service.lessonRepo=mockLessonRepo;
		service.studentCourseLessonRepo=mockStudentCourseLessonRepo;
		service.studentCourseRepo=mockStudentCourseRepo;
		service.studentRepo=mockStudentRepo;
	}

	@Test
	void testAddLessonInStudentCourseLesson() {
		Student stu = new Student();
		stu.setStudentId(1);
		stu.setStudentEmail("prateek@123");
		stu.setStudentImage(null);
		stu.setStudentPassword("hello");
		
		when(mockStudentRepo.findById(1)).thenReturn(stu);
		
		Course c = new Course();
		c.setCoursePrice(1000);
		c.setCourseName("html");
		c.setCourseId(10);
		c.setLessonsCount(10);
		
		when(mockCourseRepo.findById(10)).thenReturn(c);
		
		Lesson lesson = new Lesson();
		lesson.setCourseId(10);
		lesson.setLessonId(1);
		lesson.setLessonName("basic");
		
		when(mockLessonRepo.findById(1)).thenReturn(Optional.of(lesson));
		
		StudentCourseLesson scl = new StudentCourseLesson();
		scl.setCourseId(c);
		scl.setLessonId(lesson);
		scl.setStudentId(stu);
		
		StudentCourse sc = new StudentCourse();
		sc.setCourseCompletionPercent(10);
		sc.setCourseId(c);
		sc.setCourseStatus("Pending");
		sc.setCurrentLessonId(2);
		sc.setStudentId(stu);
		
		when(mockStudentCourseLessonRepo.getCompletedLessonCount(1, 10)).thenReturn(1);
		
		when(mockStudentCourseLessonRepo.save(scl)).thenReturn(scl);
		
		//inside another method
		
		StudentCourseId sci = new StudentCourseId();
		sci.setCourseId(10);
		sci.setStudentId(1);
		when(mockStudentCourseRepo.findById(sci)).thenReturn(Optional.of(sc));
		when(mockStudentCourseRepo.save(sc)).thenReturn(sc);
		
		ResponseEntity<String> actual = service.addLessonInStudentCourseLesson(1, 10, 1);
		ResponseEntity<String> expected = new ResponseEntity<>("Successully Completed Lesson and added to database",HttpStatus.CREATED);
		System.out.println(expected);
		System.out.println(actual);
		assertEquals(expected,actual);
	}
	
}
