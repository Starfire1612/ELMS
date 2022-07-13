package com.elms.databaseservice.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.elms.databaseservice.models.Feedback;
import com.elms.databaseservice.services.FeedbackService;

@RestController
public class FeedbackController {

	private Logger logger = org.slf4j.LoggerFactory.getLogger(FeedbackController.class);
	@Autowired
	FeedbackService service;

	@GetMapping(path = "/feedbacks")
	public List<Feedback> fetchAllStudents() {
		return service.getAllFeedbacks();
	}

	@GetMapping(path = "/feedback/course/{courseId}")
	public ResponseEntity<List<Feedback>> fetchAllFeedbackByCourseId(@PathVariable("courseId") int courseId) {
		return service.getAllFeedbacksByCourseId(courseId);
	}

//	@GetMapping(path = "/feedback/student/{studentId}/course/{courseId}")
//	public ResponseEntity<String> isFeedbackPresent(@PathVariable("studentId") int studentId,
//			@PathVariable("courseId") int courseId) {
//		return service.existFeebackById(studentId, courseId);
//	}
//
//	@PostMapping(path = "/feedback/student/save")
//	public ResponseEntity<String> storeFeedback(@RequestBody Feedback feedback) {
//		logger.info("feedback savings");
//		return service.storeFeedback(feedback);
//	}
//
////	@DeleteMapping(path = "/feedback/student/{studentId}/course/{courseId}")
////	public ResponseEntity<String> deleteFeedback(@PathVariable("studentId") int studentId,
////			@PathVariable("courseId") int courseId) {
////		logger.info("feedback savings");
////		return service.deleteFeedback(studentId, courseId);
////	}

}
