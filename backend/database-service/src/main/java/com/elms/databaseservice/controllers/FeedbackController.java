package com.elms.databaseservice.controllers;

import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.elms.databaseservice.models.Feedback;
import com.elms.databaseservice.models.StudentCourse;
import com.elms.databaseservice.models.StudentCourseId;
import com.elms.databaseservice.proxy.AuthClient;
import com.elms.databaseservice.repos.FeedbackRepo;
import com.elms.databaseservice.repos.StudentCourseRepo;
import com.elms.databaseservice.services.FeedbackService;
import com.elms.databaseservice.services.StudentCourseService;
import com.elms.databaseservice.services.StudentService;

@RestController
//@CrossOrigin(origins = "http://localhost:3000")
public class FeedbackController {

	private Logger logger = org.slf4j.LoggerFactory.getLogger(FeedbackController.class);
	@Autowired
	FeedbackService service;
	@Autowired
	StudentCourseRepo studentCourseRepo;
	@Autowired
	FeedbackRepo repo;
	@Autowired
	AuthClient client;

	@Autowired
	StudentCourseService studentCourseService;

//	@GetMapping(path = "/feedbacks")
//	public ResponseEntity<List<Feedback>> fetchAllStudents(@RequestHeader(value = "Authorization", required = true) String requestTokenHeader) throws Exception {
//		if(client.authorizeTheRequest(requestTokenHeader))
//			return service.getAllFeedbacks();
//		else
//			return new ResponseEntity<List<Feedback>>(Collections.EMPTY_LIST,HttpStatus.BAD_REQUEST);
//
//	}

	//fetching feedbacks of the respective course
	@GetMapping(path = "/course/{courseId}/feedback")
	public ResponseEntity<List<Feedback>> fetchAllFeedbackByCourseId(@PathVariable("courseId") int courseId) {
//		if(client.authorizeTheRequest(requestTokenHeader))

		logger.info("Fetching all feedbacks by course Id : " + courseId);
		return service.getAllFeedbacksByCourseId(courseId);
//		else
//			return new ResponseEntity<List<Feedback>>(Collections.EMPTY_LIST,HttpStatus.BAD_REQUEST);
	}

	
	//fetching the feedback given to the respective course by the student via student ID.
	@GetMapping(path = "/student/{studentId}/course/{courseId}/feedback")
	public ResponseEntity<Feedback> isFeedbackPresent(
			@RequestHeader(value = "Authorization", required = true) String requestTokenHeader,
			@PathVariable("studentId") int studentId, @PathVariable("courseId") int courseId) {
		logger.info("Finding feedback by course id for perticular student");
		if (client.authorizeTheRequest(requestTokenHeader, studentId))
			return service.existFeedbackById(studentId, courseId);
		else {
			logger.error("User not authenticated");
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
	}

	//fetching the ratings given to the respective course by the student via student ID.
	@GetMapping(path = "/student/{studentId}/course/{courseId}/feedback/{rating}")
	public ResponseEntity<List<Feedback>> filterFeedbackForStudent(
			@RequestHeader(value = "Authorization", required = true) String requestTokenHeader,
			@PathVariable("studentId") int studentId, @PathVariable("courseId") int courseId,
			@PathVariable("rating") int rating) {
		logger.info("Finding feedback by course id for perticular student");
		if (client.authorizeTheRequest(requestTokenHeader, studentId))
			return service.filterFeedback(courseId, rating);
		else {
			logger.error("User not authenticated");
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
	}
	
	//fetching the feedbacks given to the respective course sorted via ratings.
	@GetMapping(path = "/instructor/{instructorId}/course/{courseId}/feedback/{rating}")
	public ResponseEntity<List<Feedback>> filterFeedbackForInstructor(
			@RequestHeader(value = "Authorization", required = true) String requestTokenHeader,
			@PathVariable("instructorId") int instructorId, @PathVariable("courseId") int courseId,
			@PathVariable("rating") int rating) {
		logger.info("Finding feedback by course id for instructor");
		if (client.authorizeTheRequest(requestTokenHeader, instructorId))
			return service.filterFeedback(courseId, rating);
		else {
			logger.error("User not authenticated");
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
	}

//
	//posting the feedback given by the student to the course
	@PostMapping(path = "/student/{studentId}/course/{courseId}/feedback")
	public ResponseEntity<String> storeFeedback(
			@RequestHeader(value = "Authorization", required = true) String requestTokenHeader,
			@PathVariable("studentId") int studentId, @PathVariable("courseId") int courseId,
			@RequestBody Feedback feedback) {
		if (client.authorizeTheRequest(requestTokenHeader, studentId)) {
			logger.info("Storing Feedback of Student in course");
			feedback.setStudentCourseId(studentCourseRepo.findById(new StudentCourseId(studentId, courseId)).get());
			feedback.setStudentName(feedback.getStudentCourseId().getStudentId().getStudentName());
			return service.storeFeedback(feedback, studentId, courseId);

		} else {
			logger.error("User not authenticated");
			return new ResponseEntity<>("User not authenticated", HttpStatus.BAD_REQUEST);
		}
	}

	//deleting the feedback given by the student to the course
	@DeleteMapping(path = "/student/{studentId}/course/{courseId}/feedback")
	public ResponseEntity<String> deleteFeedback(
			@RequestHeader(value = "Authorization", required = true) String requestTokenHeader,
			@PathVariable("studentId") int studentId, @PathVariable("courseId") int courseId) {
		logger.info("feedback savings");
		if (client.authorizeTheRequest(requestTokenHeader, studentId))
			return service.deleteFeedback(studentId, courseId);
		else {
			logger.error("User not authenticated");
			return new ResponseEntity<>("User not authenticated", HttpStatus.BAD_REQUEST);
		}
	}

}
