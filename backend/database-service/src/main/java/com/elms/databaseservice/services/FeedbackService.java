package com.elms.databaseservice.services;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.elms.databaseservice.models.Feedback;
import com.elms.databaseservice.models.StudentCourseFeedbackId;
import com.elms.databaseservice.models.StudentCourseId;
import com.elms.databaseservice.repos.FeedbackRepo;

@Service
public class FeedbackService {

	private Logger logger = org.slf4j.LoggerFactory.getLogger(FeedbackService.class);
	@Autowired
	FeedbackRepo repo;

	@Transactional
	public ResponseEntity<List<Feedback>> getAllFeedbacks() {
		logger.info("Inside Feedback Service");
		return new ResponseEntity<>(repo.findAll(), HttpStatus.OK);
	}

	@Transactional
	public ResponseEntity<List<Feedback>> getAllFeedbacksByCourseId(int id) {
		List<Feedback> sc = getAllFeedbacks().getBody();
		List<Feedback> fc = new ArrayList<>();
		for (Feedback f : sc) {
			if (f.getStudentCourseId().getCourseId().getCourseId() == id)
				fc.add(f);
		}
		return new ResponseEntity<List<Feedback>>(fc, HttpStatus.OK);
	}

	@Transactional
	public ResponseEntity<String> storeFeedback(Feedback feedback) {
//		List<Feedback> sc = getAllFeedbacks().getBody();
//		logger.info(feedback.toString());
//		Feedback feed = new Feedback();
//		for (Feedback f : sc) {
//			if (f.getStudentCourseId().getCourseId().getCourseId() == feedback.getStudentCourseId().getCourseId()
//					.getCourseId()
//					&& feedback.getStudentCourseId().getStudentId().getStudentId() == f.getStudentCourseId()
//							.getStudentId().getStudentId()) {
//				feed = (f);
//				break;
//			}
//		}

		logger.info(feedback.getStudentCourseId().getCourseStatus());
		repo.save(feedback);
		return new ResponseEntity<>("Stored Feedback " + feedback.getContent() + " successfully!", HttpStatus.CREATED);

	}

	@Transactional
	public ResponseEntity<String> deleteFeedback(int studentId, int courseId) {
		Feedback feedback = repo.findByStudentCourseId(studentId, courseId);
		if (feedback == null) {
			return new ResponseEntity<>("No such feedback exits", HttpStatus.NOT_FOUND);
		}
		repo.delete(feedback);
		return new ResponseEntity<>("Deleted Feedback Successfully", HttpStatus.OK);
	}

	@Transactional
	public ResponseEntity<String> existFeedbackById(int studentId, int courseId) {
		Feedback feedback = repo.findByStudentCourseId(studentId, courseId);
		if (feedback == null)
			return new ResponseEntity<>("No Feedback given", HttpStatus.NOT_FOUND);
		else
			return new ResponseEntity<>("Feedback Already present", HttpStatus.BAD_REQUEST);
	}
}
