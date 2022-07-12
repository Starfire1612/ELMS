package com.elms.databaseservice.controllers;

import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.elms.databaseservice.models.Student;
import com.elms.databaseservice.models.StudentCourse;
import com.elms.databaseservice.repos.StudentRepo;
import com.elms.databaseservice.services.DatabaseService;

@RestController
public class StudentController {

	private Logger log = LoggerFactory.getLogger(StudentController.class);
	@Autowired
	DatabaseService databaseService;

	@Autowired
	StudentRepo studentRepo;

	@GetMapping(path = "/students")
	public List<Student> getAllStudents() {
		return studentRepo.findAll();
	}

	@GetMapping(path = "/students/{id}")
	public Set<StudentCourse> getStudentEnrolledCourseDetails(@PathVariable("id") int id) {
		Set<StudentCourse> courses = databaseService.findStudentEnrolledCouse(id).getStudentCoursesDetails();
		for(StudentCourse sc:courses) {
			log.info(sc.getCourseId().getCourseName());
		}
//		Gson g =new Gson();
		return (courses);
//		return new ResponseEntity<>(courses, HttpStatus.OK);
	}
}
