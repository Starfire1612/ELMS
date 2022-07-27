package com.elms.databaseservice.controllers;

import java.util.Collections;
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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.elms.databaseservice.models.Course;
import com.elms.databaseservice.models.Feedback;
import com.elms.databaseservice.models.Instructor;
import com.elms.databaseservice.models.InstructorCourse;
import com.elms.databaseservice.models.Lesson;
import com.elms.databaseservice.models.Student;
import com.elms.databaseservice.proxy.AuthClient;
import com.elms.databaseservice.services.CourseService;
import com.elms.databaseservice.services.InstructorService;

@RestController
public class InstructorController {

	private Logger log = LoggerFactory.getLogger(InstructorController.class);

	@Autowired
	InstructorService instructorService;
	@Autowired
	CourseService courseService;
	@Autowired
	AuthClient client;
	
//	@GetMapping(path = "/instructors")
//	public ResponseEntity<List<Instructor>> getAllInstructors(@RequestHeader(value = "Authorization", required = true) String requestTokenHeader) {
//		log.info("get all instructor");
//		if(client.authorizeTheRequest(requestTokenHeader,id))
//			return instructorService.getAllInstructors();
//		else
//			return new ResponseEntity<List<Instructor>>(Collections.EMPTY_LIST,HttpStatus.BAD_REQUEST);
//	}

	@GetMapping(path = "/instructor/{id}/courses")
	public ResponseEntity<Set<Course>> getAllCreatedCourses(@RequestHeader(value = "Authorization", required = true) String requestTokenHeader,@PathVariable("id") int id) {
		log.info("inside course fetch");
		if(client.authorizeTheRequest(requestTokenHeader,id))
			return instructorService.getCreatedCourses(id);
		else
			return new ResponseEntity<>(Collections.EMPTY_SET,HttpStatus.BAD_REQUEST);
	}

	@GetMapping(path = "/instructor/{id}/courses/{courseId}")
	public ResponseEntity<Course> getCreatedCourseDetails(@RequestHeader(value = "Authorization", required = true) String requestTokenHeader,@PathVariable("id") int id,
			@PathVariable("courseId") int courseId) {
		log.info("inside course details fetch");
		if(client.authorizeTheRequest(requestTokenHeader,id))	
			return instructorService.getCreatedCourseDetails(id, courseId);
		else
			return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
		}

	@GetMapping(path = "/instructor/{id}/profile")
	public ResponseEntity<Instructor> viewInstructorProfile(@RequestHeader(value = "Authorization", required = true) String requestTokenHeader,@PathVariable("id") int id) {
		log.info("inside instructor profile");
		if(client.authorizeTheRequest(requestTokenHeader,id))	
			return instructorService.viewProfile(id);
		else
			return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
	}

	@PostMapping(path = "/instructor/{id}/create-course")
	public ResponseEntity<String> createCourse(@RequestHeader(value = "Authorization", required = true) String requestTokenHeader,@PathVariable("id") int id, @RequestBody Course course) {
		if(client.authorizeTheRequest(requestTokenHeader,id))	
		{
			Instructor instructor = instructorService.getInstructorById(id);
			String instructorName = instructor.getInstructorName();
			course.setInstructorId(instructor);
			course.setDatePublished(new Date());
			course.setInstructorName(instructorName);
			log.info(course.toString()+course.getCourseName());

			InstructorCourse responseCourseId = instructorService.addCourse(course).getBody();
			if (responseCourseId!=null)
				return new ResponseEntity<>("Course Created Successfully" + "", HttpStatus.CREATED);
			else
				return new ResponseEntity<>("Error occured while creating the course", HttpStatus.OK);
		}
		else
			return new ResponseEntity<>("User authentication failed",HttpStatus.BAD_REQUEST);
	
		
	}

	@PutMapping(path = "/instructor/{id}/create-course")
	public ResponseEntity<String> publishCourse(@RequestHeader(value = "Authorization", required = true) String requestTokenHeader,@PathVariable("id") int id, @RequestBody Course course,
			@RequestBody List<Lesson> lessons) {
		if(client.authorizeTheRequest(requestTokenHeader,id))
			return instructorService.publishCourse(course, lessons);
		else
			return new ResponseEntity<>("User authentication failed",HttpStatus.BAD_REQUEST);

	}

	@DeleteMapping(path = "/instructor/{id}/courses/{courseId}")
	public ResponseEntity<String> deleteCourse(@RequestHeader(value = "Authorization", required = true) String requestTokenHeader,@PathVariable("id") int id, @PathVariable("courseId") int courseId) {
		if(client.authorizeTheRequest(requestTokenHeader,id))	
			return instructorService.deleteCourse(id, courseId);
		else
			return new ResponseEntity<>("User authentication failed",HttpStatus.BAD_REQUEST);
	}


	@PutMapping(path = "/instructor/{id}/profile")
	public ResponseEntity<Instructor> updateInstructorProfil(@RequestHeader(value = "Authorization", required = true) String requestTokenHeader,@PathVariable int id,@RequestBody Instructor i) {
		
		if(client.authorizeTheRequest(requestTokenHeader,id))	
			return instructorService.updateProfile(i);
		else
			return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
	}

	@PutMapping(path = "/instructor/{id}/uploadProfilePic", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	public ResponseEntity<String> updateInstructorProfilPic(@RequestHeader(value = "Authorization", required = true) String requestTokenHeader,@PathVariable("id") int id, @RequestBody MultipartFile file)
			throws Exception {
		if(client.authorizeTheRequest(requestTokenHeader,id))	
			return instructorService.updateProfilePic(id, file);
		else
			return new ResponseEntity<>("User authentication failed",HttpStatus.BAD_REQUEST);
	}

}
