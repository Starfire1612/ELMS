package com.elms.databaseservice.services;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import com.elms.databaseservice.models.Course;
import com.elms.databaseservice.models.Instructor;
import com.elms.databaseservice.models.InstructorCourse;
import com.elms.databaseservice.models.InstructorCourseId;
import com.elms.databaseservice.models.Lesson;
import com.elms.databaseservice.models.Student;
import com.elms.databaseservice.repos.CourseRepo;
import com.elms.databaseservice.repos.InstructorCourseRepo;
import com.elms.databaseservice.repos.InstructorRepo;
import com.elms.databaseservice.repos.StudentCourseRepo;

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
	@Autowired
	StudentCourseRepo studentCourseRepo;
	
	@Autowired
	InstructorCourseRepo instructorCourseRepo;
	private Logger log = LoggerFactory.getLogger(InstructorService.class);

	@Transactional
	public Instructor getInstructorById(int id) {
		return instructorRepo.findById(id).get();
	}

	@Transactional
	public ResponseEntity<Instructor> viewProfile(int instructorId) {
		Optional<Instructor> instructorDetails = instructorRepo.findById(instructorId);
		if (instructorDetails.isPresent())
			return new ResponseEntity<>(instructorDetails.get(), HttpStatus.OK);
		else {
			log.info("Instructor not found");
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
	}

//	@Transactional
//	public ResponseEntity<Instructor> updateProfile(int id,@RequestBody Instructor instructor) {
//		Optional<Instructor> instructorDetails = instructorRepo.findById(id);
//		instructorRepo.save(instructor);
//		return new ResponseEntity<>(instructorDetails.get(), HttpStatus.CREATED);
//	}
	@Transactional
	public ResponseEntity<Set<Course>> getCreatedCourses(int instructorId) {
		Set<Course> course=courseRepo.getByInstructorId(instructorId);
		if (course.isEmpty()) {

			log.info("Cannot get created course");
			return new ResponseEntity<>(course, HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<>(course, HttpStatus.OK);
		}
	}

	@Transactional
	public Integer addCourse(Course c) {
		try {
			log.info("Add course");
			log.info(c.getCourseName() + ":" + c.getInstructorName());

			int courseId = courseService.addCourse(c);
			log.info("=========================================================");
			InstructorCourse instructorCourse = new InstructorCourse(c.getInstructorId().getInstructorId(), courseId);
			log.info(instructorCourse.getCourseId() + ":" + instructorCourse.getInstructorId());
			c.setDatePublished(new Date());
//		c.setTotalDuration(lessonService.getCourseDuration(c.getCourseId()));
//		c.setLessonsCount(c.getLessonsCount());
			instructorCourseRepo.save(instructorCourse);
			return (instructorCourse.getCourseId());
		} catch (Exception e) {
			return null;
		}
	}

	@Transactional
	public ResponseEntity<String> publishCourse(Course c, List<Lesson> lesson) {
		try {
			lessonService.addLessonsInCourse(lesson);
			log.info("Adding lesson in course");
			c.setDatePublished(new Date());
			c.setTotalDuration(lessonService.getCourseDuration(c.getCourseId()));
			c.setLessonsCount(c.getLessonsCount());
			log.info("Publishing Course");
			return courseService.publishCourse(c);

		} catch (Exception e) {

			log.error("Error Publishing Course");
			return new ResponseEntity<>("Error Publishing Course", HttpStatus.NOT_IMPLEMENTED);
		}
	}

	@Transactional
	public ResponseEntity<List<Instructor>> getAllInstructors() {
		return new ResponseEntity<>(instructorRepo.findAll(), HttpStatus.OK);
	}

	@Transactional
	public ResponseEntity<Course> getCreatedCourseDetails(int id, int courseId) {
		Instructor instructor = instructorRepo.findById(id).get();
		for (Course c : instructor.getCourses()) {
			if (c.getCourseId() == courseId)
				return new ResponseEntity<>(c, HttpStatus.OK);
		}
		log.error("Cannot recieve created course details");
		return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
	}

	@Transactional
	public ResponseEntity<String> deleteCourse(int instructorId, int courseId) {
		try {

			log.info("Deleting from instructorcourse,course,lesson table");
			instructorCourseRepo.deleteById(new InstructorCourseId(instructorId, courseId));
			Course c=courseRepo.getById(courseId);
			log.info("Course "+c.getCourseName());
			c.setIs_Active("false");
			courseRepo.save(c);
			return new ResponseEntity<>("Deleted Course Successfully", HttpStatus.ACCEPTED);
		} catch (Exception e) {
			log.error("Course not found");
			return new ResponseEntity<>("Course Not Found", HttpStatus.NOT_FOUND);
		}
	}

	@Transactional
	public ResponseEntity<String> updateProfilePic(int id, MultipartFile file) throws Exception {
		// Normalize file name
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		try {
			// Check if the file's name contains invalid characters
			if (file.getContentType().contains("/png")) {
				log.info("Uploading profile pic");
				Instructor instructor = instructorRepo.findById(id).get();
				instructor.setInstructorImage(file.getBytes());
				instructorRepo.save(instructor);
				return new ResponseEntity<>(file.getName() + " " + file.getResource().getFilename(),
						HttpStatus.CREATED);
			}
			log.warn("Only upload png images");
			return new ResponseEntity<>("Only upload png images", HttpStatus.CREATED);
		} catch (IOException ex) {
			log.error("Could not store file");
			return new ResponseEntity<>("Could not store file " + fileName + ". Please try again!",
					HttpStatus.BAD_REQUEST);
		}
	}

	@Transactional
	public ResponseEntity<Instructor> updateProfile(@RequestBody Instructor instructor) {
		Optional<Instructor> instructorDetails = instructorRepo.findById(instructor.getInstructorId());
		if (instructorDetails.isPresent()) {
			instructorRepo.save(instructor);
			return new ResponseEntity<>(instructorDetails.get(), HttpStatus.OK);
		} else {
			log.error("Could not update profile");
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
	}

	@Transactional
	public ResponseEntity<String> updateCoursePic(int id, MultipartFile file) throws Exception {
		// Normalize file name
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		try {
			// Check if the file's name contains invalid characters
			if (file.getContentType().contains("/png")) {
				log.info("Uploading profile pic");
				Course course = courseRepo.findById(id);
				course.setCourseImage(file.getBytes());
				courseRepo.save(course);
				return new ResponseEntity<>(file.getName() + " " + file.getResource().getFilename(),
						HttpStatus.CREATED);
			}
			log.warn("Only upload png images");
			return new ResponseEntity<>("Only upload png images", HttpStatus.CREATED);
		} catch (IOException ex) {
			log.error("Could not store file");
			return new ResponseEntity<>("Could not store file " + fileName + ". Please try again!",
					HttpStatus.BAD_REQUEST);
		}
	}

	public ResponseEntity<Integer> getRegisteredStudent(int id, int courseId) {
		log.debug("getting students size from database");
		int registeredStudent=studentCourseRepo.findByCourseId(courseId);
		return new ResponseEntity<>(registeredStudent,HttpStatus.OK);
	}

}
