package com.elms.databaseservice.services;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.elms.databaseservice.models.Course;
import com.elms.databaseservice.models.Instructor;
import com.elms.databaseservice.models.Lesson;
import com.elms.databaseservice.models.Student;
import com.elms.databaseservice.repos.CourseRepo;
import com.elms.databaseservice.repos.InstructorRepo;

@Service
public class InstructorService {

	@Autowired
	InstructorRepo instructorRepo;
	@Autowired
	CourseRepo courseRepo;
	@Autowired
	CourseService courseService;
	@Autowired
	LessonService lessonService;

	private Logger log = LoggerFactory.getLogger(InstructorService.class);

	@Transactional
	public Instructor getInstructorById(int id) {
		return instructorRepo.findById(id).get();
	}

	@Transactional
	public ResponseEntity<Instructor> viewProfile(int instructorId) {
		Optional<Instructor> instructorDetails = instructorRepo.findById(instructorId);
		return new ResponseEntity<Instructor>(instructorDetails.get(), HttpStatus.CREATED);
	}

//	@Transactional
//	public ResponseEntity<Instructor> updateProfile(int id,@RequestBody Instructor instructor) {
//		Optional<Instructor> instructorDetails = instructorRepo.findById(id);
//		instructorRepo.save(instructor);
//		return new ResponseEntity<>(instructorDetails.get(), HttpStatus.CREATED);
//	}
	@Transactional
	public ResponseEntity<Set<Course>> getCreatedCourses(int instructorId) {
		Set<Course> instructorCourse = instructorRepo.findById(instructorId).get().getCourses();
		return new ResponseEntity<Set<Course>>(instructorCourse, HttpStatus.OK);
	}

	@Transactional
	public Integer addCourse(Course c) {
		return courseService.addCourse(c);
	}

	@Transactional
	public ResponseEntity<String> publishCourse(Course c, List<Lesson> lesson) {
		lessonService.addLessonsInCourse(lesson);
		c.setDatePublished(new Date());
		c.setTotalDuration(lessonService.getCourseDuration(c.getCourseId()));
		c.setLessonsCount(c.getLessonsCount());
		return courseService.publishCourse(c);
	}

	@Transactional
	public List<Instructor> getAllInstructors() {
		return instructorRepo.findAll();
	}

	@Transactional
	public ResponseEntity<Course> getCreatedCourseDetails(int id, int courseId) {
		Instructor instructor = instructorRepo.findById(id).get();
		for (Course c : instructor.getCourses()) {
			if (c.getCourseId() == courseId)
				return new ResponseEntity<>(c, HttpStatus.OK);
		}
		return new ResponseEntity<>(null, HttpStatus.OK);
	}

	@Transactional
	public ResponseEntity<String> deleteCourse(int instructorId, int courseId) {
		lessonService.deleteAllLessonsByCourseId(courseId);
		courseRepo.deleteByInstructorIdAndCourseId(instructorId, courseId);
		return new ResponseEntity<>("Deleted Course Successfully", HttpStatus.ACCEPTED);
	}

	@Transactional
	public ResponseEntity<String> updateProfilePic(int id, MultipartFile file) throws Exception {
		// TODO Auto-generated method stub
		// Normalize file name
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());

		try {
			// Check if the file's name contains invalid characters
			if (file.getContentType().contains("/png")) {

				Instructor instructor = instructorRepo.findById(id).get();
				instructor.setInstructorImage(file.getBytes());
				instructorRepo.save(instructor);
				return new ResponseEntity<>(file.getName() + " " + file.getResource().getFilename(),
						HttpStatus.CREATED);
			}
			return new ResponseEntity<>("Only upload png images", HttpStatus.CREATED);
		} catch (IOException ex) {
			throw new Exception("Could not store file " + fileName + ". Please try again!", ex);
		}
	}
}
