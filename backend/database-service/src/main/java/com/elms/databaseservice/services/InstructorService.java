package com.elms.databaseservice.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.elms.databaseservice.controllers.StudentController;
import com.elms.databaseservice.models.Course;
import com.elms.databaseservice.models.Instructor;
import com.elms.databaseservice.models.Instructor;
import com.elms.databaseservice.repos.CourseRepo;
import com.elms.databaseservice.repos.InstructorRepo;

@Service
public class InstructorService {
	
	@Autowired
	InstructorRepo instructorRepo;
	@Autowired
	CourseRepo courseRepo;
	
	private Logger log = LoggerFactory.getLogger(StudentController.class);
	
	
	@Transactional
	public ResponseEntity<Instructor> viewProfile(int instructorId) {
		Optional<Instructor> instructorDetails = instructorRepo.findById(instructorId);
		return new ResponseEntity<Instructor>(instructorDetails.get(), HttpStatus.CREATED);
	}

//	@Transactional
//	public ResponseEntity<Instructor> updateProfile(int id,@RequestBody Instructor instructor) {
//		Optional<Instructor> instructorDetails = instructorRepo.findById(id);
//		instructorRepo.save(instructor);
//		return new ResponseEntity<>(instructorDetails.get(), HttpStatus.CREATED);
//	}
	@Transactional
	public ResponseEntity<List<Course>> viewCreatedCourses(int instructorId) {
		List<Course> instructorCourse=courseRepo.findByInstructorId(instructorId);
		return new ResponseEntity<List<Course>>(instructorCourse, HttpStatus.OK);
	}
	@Transactional
	public List<Instructor> getAllInstructors() {
		return instructorRepo.findAll();
	}

	public ResponseEntity<Course> viewCreatedCourseDetails(int id, int courseId) {
		List<Course> instructorCourse=courseRepo.findByInstructorId(id);
		Course c=new Course();
		for(Course co:instructorCourse)
		{
			if(co.getCourseId()==courseId)
			{	c=co;
				break;
			}
		}
		return new ResponseEntity<Course>(c, HttpStatus.OK);
	}

//	@Transactional
//		public ResponseEntity<String> deleteCourses(int instructorId,int courseId) {
//			courseRepo.deleteByInstructorIdAndCourseId(instructorId, courseId);
//			return new ResponseEntity<>("Deleted Feedback Successfully", HttpStatus.ACCEPTED);
//		}
//		

	
	
}
