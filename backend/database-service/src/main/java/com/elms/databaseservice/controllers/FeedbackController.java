package com.elms.databaseservice.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.elms.databaseservice.models.Feedback;
import com.elms.databaseservice.models.StudentCourse;
import com.elms.databaseservice.models.StudentCourseId;
import com.elms.databaseservice.repos.FeedbackRepo;
import com.elms.databaseservice.repos.StudentCourseRepo;
import com.elms.databaseservice.services.FeedbackService;

@RestController
public class FeedbackController {

	private Logger logger = org.slf4j.LoggerFactory.getLogger(FeedbackController.class);
	@Autowired
	FeedbackService service;
	@Autowired
	StudentCourseRepo studentCourseRepo;
	@Autowired
	FeedbackRepo repo;

	@GetMapping(path = "/feedbacks")
	public List<Feedback> fetchAllStudents() {
		return service.getAllFeedbacks();
	}

	@GetMapping(path = "/course/{courseId}/feedback")
	public ResponseEntity<List<Feedback>> fetchAllFeedbackByCourseId(@PathVariable("courseId") int courseId) {
		return service.getAllFeedbacksByCourseId(courseId);
	}

	@GetMapping(path = "/student/{studentId}/course/{courseId}/feedback")
	public ResponseEntity<String> isFeedbackPresent(@PathVariable("studentId") int studentId,
			@PathVariable("courseId") int courseId) {
		return service.existFeedbackById(studentId, courseId);
	}

//
	@PostMapping(path = "/student/{id}/course/{courseId}/feedback")
	public ResponseEntity<String> storeFeedback(@PathVariable("id") int id,
			@PathVariable("courseId") int courseId,
			@RequestBody Feedback feedback) {
		StudentCourse studentCourse=studentCourseRepo.findById(new StudentCourseId(id,courseId)).get();
		feedback.setStudentCourseId(studentCourse);
		 return service.storeFeedback(feedback);
	}

	@DeleteMapping(path = "/student/{studentId}/course/{courseId}/feedback")
	public ResponseEntity<String> deleteFeedback(@PathVariable("studentId") int studentId,
			@PathVariable("courseId") int courseId) {
		logger.info("feedback savings");
		return service.deleteFeedback(studentId, courseId);
	}

}
