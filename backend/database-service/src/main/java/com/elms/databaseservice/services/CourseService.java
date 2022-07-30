package com.elms.databaseservice.services;

import java.io.IOException;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.elms.databaseservice.models.Course;
import com.elms.databaseservice.models.CourseFeedbackDetails;
import com.elms.databaseservice.repos.CourseRepo;
import com.elms.databaseservice.repos.FeedbackRepo;

@Service
public class CourseService {

	@Autowired
	CourseRepo courseRepo;

	@Autowired
	FeedbackRepo feedbackRepo;
	private Logger log = LoggerFactory.getLogger(CourseService.class);
	@Transactional
	public Integer addCourse(Course c) {
		
		Course course = courseRepo.save(c);
		return course.getCourseId();
	}

	@Transactional

	public ResponseEntity<String> publishCourse(Course course) {
		// Course existingCourse=courseRepo.findById(course.getCourseId()).get();
		courseRepo.save(course);
		log.info("Course Published");
		return new ResponseEntity<>("Course published succssfully!", HttpStatus.CREATED);

	}
	
	@Transactional
	public ResponseEntity<Page<Course>> getAllCourses(int page,int size) {
			Page<Course> coursePage=courseRepo.findAll(PageRequest.of(page,size));
			log.info("Searched course");
			return new ResponseEntity<>(coursePage,HttpStatus.OK);
	}

	@Transactional
	public ResponseEntity<String> updateCourseImage(int id, MultipartFile file) throws Exception {
		// TODO Auto-generated method stub
		// Normalize file name
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());

		try {
			// Check if the file's name contains invalid characters
			if (file.getContentType().contains("/png")) {
				Course course = courseRepo.findById(id);
				course.setCourseImage(file.getBytes());
				courseRepo.save(course);
				return new ResponseEntity<>(file.getName() + " " + file.getResource().getFilename(),
						HttpStatus.CREATED);
			}

			log.warn("only png images allowed");
			return new ResponseEntity<>("Only upload png images", HttpStatus.CREATED);
		} catch (IOException ex) {
			log.error("Could not store file");		
			throw new Exception("Could not store file " + fileName + ". Please try again!", ex);
		}
	}
	
	@Transactional
	public ResponseEntity<CourseFeedbackDetails> getCourseRelatedDetails(int cid){
			CourseFeedbackDetails courseFeedbackDetails=new CourseFeedbackDetails();
			courseFeedbackDetails.setCourse(courseRepo.findById(cid));
			courseFeedbackDetails.setFeedbacks(feedbackRepo.findByCourseId(cid));
			return new ResponseEntity<CourseFeedbackDetails>(courseFeedbackDetails,HttpStatus.OK);
	}
}
