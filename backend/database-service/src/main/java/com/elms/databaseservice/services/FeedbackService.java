package com.elms.databaseservice.services;

import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.elms.databaseservice.models.Feedback;
import com.elms.databaseservice.repos.FeedbackRepo;

@Service
public class FeedbackService {

	private Logger logger = org.slf4j.LoggerFactory.getLogger(FeedbackService.class);
	@Autowired
	FeedbackRepo repo;

	@Transactional
	public List<Feedback> getAllFeedbacks() {
		logger.info("Inside Feedback Service");
		return repo.findAll();
	}

	@Transactional
	public List<Feedback> getAllFeedbacks(int id) {
		logger.info("Inside Feedback Service");
		return repo.findByCourseId(id);
	}

//	@Transactional
//	public ResponseEntity<String> storeFeedback(Feedback feedback) {
//		Feedback feed = repo.findByStudentIdAndCourseId(feedback.getStudentId().getStudentId(), feedback.getCourseId().getCourseId());
//		if (feed == null) {
//			repo.save(feedback);
//			return new ResponseEntity<>("Stored Feedback " + feedback.getContent() + " successfully!",
//					HttpStatus.CREATED);
//
//		} else {
//			feed.setContent(feedback.getContent());
//			feed.setRatings(feedback.getRatings());
//			repo.save(feed);
//		}
//		logger.info("see " + feed);
//		return new ResponseEntity<>("Updated Feedback " + feedback.getContent() + " successfully!", HttpStatus.OK);
//	}

	@Transactional
	public ResponseEntity<String> deleteFeedback(int studentId, int courseId) {
		repo.deleteByStudentIdAndCourseId(studentId, courseId);
		return new ResponseEntity<>("Deleted Feedback Successfully", HttpStatus.ACCEPTED);
	}

	@Transactional
	public ResponseEntity<String> existFeebackById(int studentId, int courseId) {
		if (repo.findByStudentIdAndCourseId(studentId, courseId) == null)
			return new ResponseEntity<>("No Feedback given", HttpStatus.OK);
		else
			return new ResponseEntity<>("Feedback Already present", HttpStatus.BAD_REQUEST);
	}
}
