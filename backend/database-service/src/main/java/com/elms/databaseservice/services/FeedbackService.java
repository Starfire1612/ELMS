package com.elms.databaseservice.services;



import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
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
	public ResponseEntity<List<Feedback>> getAllFeedbacksByCourseId(int id) {
		List<Feedback> sc=getAllFeedbacks();
		List<Feedback> fc=new ArrayList<>();
		for(Feedback f:sc)
		{
			if(f.getStudentCourseId().getCourseId().getCourseId()==id)
				fc.add(f);
		}
		return new ResponseEntity<List<Feedback>>(fc, HttpStatus.CREATED);
	}
//	@Transactional
//	public ResponseEntity<String> storeFeedback(Feedback feedback) {
//		List<Feedback> sc=getAllFeedbacks();
//		logger.info(feedback)
//		Feedback feed=new Feedback();
//		for(Feedback f:sc)
//		{
//			if(f.getStudentCourseId().getCourseId().getCourseId()==feedback.getStudentCourseId().getCourseId().getCourseId()&&feedback.getStudentCourseId().getStudentId().getStudentId()==f.getStudentCourseId().getStudentId().getStudentId())
//			{	feed=(f);
//			break;}
//		}
//		if(feed==null) 
//		{
//			repo.save(feedback);
//			return new ResponseEntity<>("Stored Feedback "+feedback.getContent()+" successfully!", HttpStatus.CREATED);
//
//		}else
//		{	feed.setContent(feedback.getContent());
//			feed.setRatings(feedback.getRatings());
//			repo.save(feed);
//		}
//		logger.info("see "+feed);
//		return new ResponseEntity<>("Updated Feedback "+feedback.getContent()+" successfully!", HttpStatus.OK);
//	}
////	@Transactional
////	public ResponseEntity<String> deleteFeedback(int studentId,int courseId) {
////		repo.deleteByStudentIdAndCourseId(studentId, courseId);
////		return new ResponseEntity<>("Deleted Feedback Successfully", HttpStatus.ACCEPTED);
////	}
//	
//	@Transactional
//	public ResponseEntity<String> existFeebackById(int studentId, int courseId) {
//		if(repo.findByStudentIdAndCourseId(studentId, courseId)==null)
//			return new ResponseEntity<>("No Feedback given",HttpStatus.OK);
//		else
//			return new ResponseEntity<>("Feedback Already present",HttpStatus.BAD_REQUEST);	
//	}
}
