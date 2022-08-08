package com.elms.databaseservice.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.math.BigInteger;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.elms.databaseservice.models.Course;
import com.elms.databaseservice.models.Instructor;
import com.elms.databaseservice.models.InstructorCourse;
import com.elms.databaseservice.models.InstructorCourseId;
import com.elms.databaseservice.models.Lesson;
import com.elms.databaseservice.repos.CourseRepo;
import com.elms.databaseservice.repos.InstructorCourseRepo;
import com.elms.databaseservice.repos.InstructorRepo;
import com.elms.databaseservice.repos.StudentCourseRepo;

class InstructorServiceTest {

	private InstructorService service;
	private InstructorRepo mockInstructorRepo;
	private CourseRepo mockCourseRepo;
	private CourseService mockCourseService;
	private LessonService mockLessonService;
	private InstructorCourseRepo mockInstructorCourseRepo;
	private StudentCourseRepo mockStudentCourseRepo;

	@BeforeEach
	void setUp() {
		this.service = new InstructorService();
		this.mockCourseRepo = mock(CourseRepo.class);
		this.mockCourseService = mock(CourseService.class);
		this.mockInstructorCourseRepo = mock(InstructorCourseRepo.class);
		this.mockInstructorRepo = mock(InstructorRepo.class);
		this.mockLessonService = mock(LessonService.class);
		this.mockStudentCourseRepo=mock(StudentCourseRepo.class);

		service.courseRepo = mockCourseRepo;
		service.courseService = mockCourseService;
		service.instructorCourseRepo = mockInstructorCourseRepo;
		service.instructorRepo = mockInstructorRepo;
		service.lessonService = mockLessonService;
		service.studentCourseRepo=mockStudentCourseRepo;
	}

	@Test
	void testGetInstructorById() {
		BigInteger acNumber = BigInteger.valueOf(10);
		Instructor inst = new Instructor();
		inst.setInstructorPassword("hello");
		inst.setInstructorName("Prateek");
		inst.setInstructorImage(null);
		inst.setInstructorId(1);
		inst.setInstructorEmail("prateek@123");
		inst.setBankIfscCode("abc");
		inst.setAccountNumber(acNumber);

		when(mockInstructorRepo.findById(1)).thenReturn(Optional.of(inst));
		Instructor actual = service.getInstructorById(1);
		Instructor expected = inst;
		assertEquals(expected, actual);
	}

	@Test
	void testViewProfile() {
		BigInteger acNumber = BigInteger.valueOf(10);
		Instructor inst = new Instructor();
		inst.setInstructorPassword("hello");
		inst.setInstructorName("Prateek");
		inst.setInstructorImage(null);
		inst.setInstructorId(1);
		inst.setInstructorEmail("prateek@123");
		inst.setBankIfscCode("abc");
		inst.setAccountNumber(acNumber);

		Optional<Instructor> dummy = Optional.of(inst);

		when(mockInstructorRepo.findById(1)).thenReturn(dummy);
		when(mockInstructorRepo.findById(2)).thenReturn(Optional.empty());
		ResponseEntity<Instructor> actual1 = service.viewProfile(inst.getInstructorId());
		ResponseEntity<Instructor> actual2 = service.viewProfile(2);
		ResponseEntity<Instructor> expected1 = new ResponseEntity<>(dummy.get(), HttpStatus.OK);
		ResponseEntity<Instructor> expected2 = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		assertEquals(expected2, actual2);
	}

	@Test
	void testViewProfile2() {

		when(mockInstructorRepo.findById(2)).thenReturn(Optional.empty());

		ResponseEntity<Instructor> actual2 = service.viewProfile(2);

		ResponseEntity<Instructor> expected2 = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		assertEquals(expected2, actual2);
	}

	@Test
	void testGetCreatedCourse() {
		BigInteger acNumber = BigInteger.valueOf(10);
		Instructor inst = new Instructor();
		inst.setInstructorPassword("hello");
		inst.setInstructorName("Prateek");
		inst.setInstructorImage(null);
		inst.setInstructorId(1);
		inst.setInstructorEmail("prateek@123");
		inst.setBankIfscCode("abc");
		inst.setAccountNumber(acNumber);

		when(mockInstructorRepo.findById(1)).thenReturn(Optional.of(inst));
		when(mockInstructorRepo.findById(2)).thenReturn(Optional.empty());

		Set<Course> courses = new HashSet<Course>();
		Course e = new Course();
		courses.add(e);
		Set<Course> courses2 = new HashSet<Course>();

		ResponseEntity<Set<Course>> actual1 = service.getCreatedCourses(1);
		ResponseEntity<Set<Course>> actual2 = service.getCreatedCourses(2);

		ResponseEntity<Set<Course>> expected1 = new ResponseEntity<>(courses, HttpStatus.OK);

		ResponseEntity<Set<Course>> expected2 = new ResponseEntity<>(courses2, HttpStatus.NO_CONTENT);
		assertEquals(expected2, actual2);
	}

	@Test
	void testAddCourse() {
		Course c = new Course();
		c.setTotalDuration(10);
		c.setRatings(5);
		c.setLessonsCount(5);
		c.setCourseId(1);

		BigInteger acNumber = BigInteger.valueOf(10);
		Instructor inst = new Instructor();
		inst.setInstructorPassword("hello");
		inst.setInstructorName("Prateek");
		inst.setInstructorImage(null);
		inst.setInstructorId(1);
		inst.setInstructorEmail("prateek@123");
		inst.setBankIfscCode("abc");
		inst.setAccountNumber(acNumber);

		c.setInstructorId(inst);

		InstructorCourse ic = new InstructorCourse();
		ic.setCourseId(1);
		ic.setInstructorId(1);

		when(mockCourseService.addCourse(c)).thenReturn(1);
		when(mockInstructorCourseRepo.save(ic)).thenReturn(ic);

		Integer actual = service.addCourse(c);
		Integer expected = 1;
		assertEquals(expected, actual);
	}

	@Test
	void testPublishCourse() {
		Course c = new Course();
		c.setCourseId(1);
		c.setCourseDiscount(10);
		c.setRatings(4);
		c.setCourseName("html");

		Lesson lesson = new Lesson();
		lesson.setCourseId(1);
//			lesson.setLesonDuration(10);
		lesson.setLessonId(2);
		lesson.setLessonName("html basics");

		when(mockLessonService.addLessonsInCourse(Collections.singletonList(lesson)))
				.thenReturn(new ResponseEntity<>("Added lessons to the course ", HttpStatus.CREATED));
		when(mockCourseService.publishCourse(c))
				.thenReturn(new ResponseEntity<>("Course published succssfully!", HttpStatus.CREATED));

		ResponseEntity<String> actual = service.publishCourse(c, Collections.singletonList(lesson));
		ResponseEntity<String> expected = new ResponseEntity<>("Course published succssfully!", HttpStatus.CREATED);

		assertEquals(expected, actual);

	}

	@Test
	void testGetAllInstructors() {
		BigInteger acNumber = BigInteger.valueOf(10);
		Instructor inst = new Instructor();
		inst.setInstructorPassword("hello");
		inst.setInstructorName("Prateek");
		inst.setInstructorImage(null);
		inst.setInstructorId(1);
		inst.setInstructorEmail("prateek@123");
		inst.setBankIfscCode("abc");
		inst.setAccountNumber(acNumber);
		when(mockInstructorRepo.findAll()).thenReturn(Collections.singletonList(inst));
		ResponseEntity<List<Instructor>> actual = service.getAllInstructors();
		ResponseEntity<List<Instructor>> expected = new ResponseEntity<>(mockInstructorRepo.findAll(), HttpStatus.OK);
		assertEquals(expected, actual);
	}

	@Test
	void testGetCreatedCourseDetails() {

		BigInteger acNumber = BigInteger.valueOf(10);
		Instructor inst = new Instructor();
		inst.setInstructorPassword("hello");
		inst.setInstructorName("Prateek");
		inst.setInstructorImage(null);
		inst.setInstructorId(1);
		inst.setInstructorEmail("prateek@123");
		inst.setBankIfscCode("abc");
		inst.setAccountNumber(acNumber);

		when(mockInstructorRepo.findById(1)).thenReturn(Optional.of(inst));
		ResponseEntity<Course> actual = service.getCreatedCourseDetails(1, 1);
		ResponseEntity<Course> expected = new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
		assertEquals(expected, actual);
	}
	

	@Test
	void testDeleteCourse() {
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
		c.setCourseDiscount(10);
		c.setRatings(4);
		c.setCourseName("html");
		c.setInstructorId(inst);

		InstructorCourse ic = new InstructorCourse();
		ic.setCourseId(2);
		ic.setInstructorId(1);

		when(mockInstructorCourseRepo.findById(null)).thenReturn(Optional.of(ic));

		ResponseEntity<String> actual = service.deleteCourse(1, 1);
		ResponseEntity<String> expected = new ResponseEntity<>("Deleted Course Successfully", HttpStatus.ACCEPTED);

		assertEquals(expected, actual);
	}

	@Test
	void testUpdateProfile() {
		BigInteger acNumber = BigInteger.valueOf(10);
		Instructor inst = new Instructor();
		inst.setInstructorPassword("hello");
		inst.setInstructorName("Prateek");
		inst.setInstructorImage(null);
		inst.setInstructorId(1);
		inst.setInstructorEmail("prateek@123");
		inst.setBankIfscCode("abc");
		inst.setAccountNumber(acNumber);

		when(mockInstructorRepo.findById(1)).thenReturn(Optional.of(inst));

		ResponseEntity<Instructor> actual = service.updateProfile(inst);
		ResponseEntity<Instructor> expected = new ResponseEntity<>(inst, HttpStatus.OK);
		assertEquals(expected, actual);
	}
	
	@Test
	void testUpdateProfile2() {
		BigInteger acNumber = BigInteger.valueOf(10);
		Instructor inst = new Instructor();
		inst.setInstructorPassword("hello");
		inst.setInstructorName("Prateek");
		inst.setInstructorImage(null);
		inst.setInstructorId(1);
		inst.setInstructorEmail("prateek@123");
		inst.setBankIfscCode("abc");
		inst.setAccountNumber(acNumber);

		when(mockInstructorRepo.findById(2)).thenReturn(null);

		ResponseEntity<Instructor> actual = service.updateProfile(inst);
		ResponseEntity<Instructor> expected = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		assertEquals(expected, actual);
	}
	
	@Test
	void testGetRegisteredStudent()
	{
		when(mockStudentCourseRepo.findByCourseId(1)).thenReturn(10);
		ResponseEntity<Integer> actual = service.getRegisteredStudent(1,1);
		 ResponseEntity<Integer> expected = new ResponseEntity<>(10,HttpStatus.OK);
	}

}
