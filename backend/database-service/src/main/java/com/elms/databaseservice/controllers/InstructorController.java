package com.elms.databaseservice.controllers;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.elms.databaseservice.models.Course;
import com.elms.databaseservice.models.Instructor;
import com.elms.databaseservice.models.InstructorCourse;
import com.elms.databaseservice.models.Lesson;
import com.elms.databaseservice.models.Student;
import com.elms.databaseservice.services.CourseService;
import com.elms.databaseservice.services.InstructorService;

@RestController
public class InstructorController {

	private Logger log = LoggerFactory.getLogger(InstructorController.class);

	@Autowired
	InstructorService instructorService;
	@Autowired
	CourseService courseService;

	
	@GetMapping(path = "/instructors")
	public List<Instructor> getAllInstructors() {
		log.info("get all instructor");
		return instructorService.getAllInstructors();
	}

	@GetMapping(path = "/instructor/{id}/courses")
	public ResponseEntity<Set<Course>> getAllCreatedCourses(@PathVariable("id") int id) {
		log.info("inside course fetch");
		return instructorService.getCreatedCourses(id);
	}

	@GetMapping(path = "/instructor/{id}/courses/{courseId}")
	public ResponseEntity<Course> getCreatedCourseDetails(@PathVariable("id") int id,
			@PathVariable("courseId") int courseId) {
		log.info("inside course details fetch");
		return instructorService.getCreatedCourseDetails(id, courseId);
	}

	@GetMapping(path = "/instructor/{id}/profile")
	public ResponseEntity<Instructor> viewInstructorProfile(@PathVariable("id") int id) {
		log.info("inside instructor profile");
		return instructorService.viewProfile(id);
	}

	@PostMapping(path = "/instructor/{id}/create-course")
	public ResponseEntity<String> createCourse(@PathVariable("id") int id, @RequestBody Course course) {
		Instructor instructor = instructorService.getInstructorById(id);
		String instructorName = instructor.getInstructorName();
		course.setInstructorId(instructor);
		course.setDatePublished(new Date());
		course.setInstructorName(instructorName);
		InstructorCourse responseCourseId = instructorService.addCourse(course);
		if (responseCourseId!=null)
			return new ResponseEntity<>(responseCourseId + "", HttpStatus.CREATED);
		else
			return new ResponseEntity<>("Error occured while creating the course", HttpStatus.CREATED);

	}

	@PutMapping(path = "/instructor/{id}/create-course")
	public ResponseEntity<String> publishCourse(@PathVariable("id") int id, @RequestBody Course course,
			@RequestBody List<Lesson> lessons) {
		return instructorService.publishCourse(course, lessons);

	}

	@DeleteMapping(path = "/instructor/{id}/courses/{courseId}")
	public ResponseEntity<String> deleteCourse(@PathVariable("id") int id, @PathVariable("courseId") int courseId) {
		return instructorService.deleteCourse(id, courseId);
	}


	@PutMapping(path = "/instructor/{id}/profile")
	public ResponseEntity<Instructor> updateStudentProfil(@RequestBody Instructor i) {
		return instructorService.updateProfile(i);
	}

	@PutMapping(path = "/instructor/{id}/uploadProfilePic", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	public ResponseEntity<String> updateInstructorProfilPic(@PathVariable("id") int id, @RequestBody MultipartFile file)
			throws Exception {
		return instructorService.updateProfilePic(id, file);
	}

}
