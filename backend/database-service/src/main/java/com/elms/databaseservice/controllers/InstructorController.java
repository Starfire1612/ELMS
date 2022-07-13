package com.elms.databaseservice.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.elms.databaseservice.models.Course;
import com.elms.databaseservice.models.Instructor;
import com.elms.databaseservice.repos.InstructorRepo;
import com.elms.databaseservice.services.InstructorService;

@RestController
public class InstructorController {

	private Logger log = LoggerFactory.getLogger(InstructorController.class);
	

	@Autowired
	InstructorService instructorService;
	
	@GetMapping(path="/instructors")
	public List<Instructor> getAllInstructor(){
		log.info("get all instructor");
		return instructorService.getAllInstructors();
	}
	
	@GetMapping(path="/instructor/{id}/courses")
	public ResponseEntity<List<Course>> fetchAllCreatedCourses(@PathVariable("id") int id){
		log.info("inside course fetch");
		return instructorService.viewCreatedCourses(id);
	}
	@GetMapping(path="/instructor/{id}/courses/{courseId}")
	public ResponseEntity<Course> fetchAllCreatedCoursesDetails(@PathVariable("id") int id,@PathVariable("id") int courseId){
		log.info("inside course details fetch");
		return instructorService.viewCreatedCourseDetails(id,courseId);
	}
	
	
	@GetMapping(path="/instructor/{id}/profile")
	public ResponseEntity<Instructor> viewInstructorProfile(@PathVariable("id") int id){
		log.info("inside instructor profile");
		return instructorService.viewProfile(id);
	}
	
//	@DeleteMapping(path="/instructor/{id}/courses/{courseId}")
//	public ResponseEntity<String> deleteCourse(@PathVariable("id") int id,@PathVariable("id") int courseId){
//		log.info("inside course details fetch");
//		return instructorService.deleteCourses(id,courseId);
//	}
	
//	@PutMapping(path="/instructor/{id}/profile")
//	public ResponseEntity<Instructor> updateInstructorProfile(@PathVariable("id") int id,@RequestBody Instructor instructor){
//		log.info("update profile");
//		return instructorService.updateProfile(id,instructor);
//	}
	
	
}
