package com.elms.databaseservice.controllers;

import java.io.FileNotFoundException;
import java.util.List;

import javax.mail.MessagingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.elms.databaseservice.models.Course;
import com.elms.databaseservice.models.Student;
import com.elms.databaseservice.services.StudentService;
import com.itextpdf.text.DocumentException;

@RestController
public class StudentController {

	private Logger log = LoggerFactory.getLogger(StudentController.class);
	

	@Autowired
	StudentService studentService;

//	@GetMapping(path = "/students")
//	public List<Student> getAllStudents() {
//		return studentRepo.findAll();
//	}
	
	@GetMapping(path="/student/{id}/courses")
	public ResponseEntity<List<Course>> fetchAllEnrolledCourses(@PathVariable("id") int id){
		return studentService.viewEnrolledCourses(id);
	}
	@GetMapping(path="/student/{id}/profile")
	public ResponseEntity<Student> viewStudentProfile(@PathVariable("id") int id){
		return studentService.viewProfile(id);
	}
//	@PutMapping(path="/student/{id}/profile")
//	public ResponseEntity<Student> updateStudentProfile(@PathVariable("id") int id){
//		return studentService.updateProfile(id);
//	}
	
	@GetMapping(path="/student/{id}/course/{courseId}")
	public ResponseEntity<String> sendCourseCompletionCertificate(@PathVariable("id") int id,
			@PathVariable("courseId") int certId) throws FileNotFoundException, DocumentException, MessagingException{
		return studentService.sendCertificate(id, certId);
	}
	
//	@GetMapping(path = "/students/{id}")
//	public Set<StudentCourse> getStudentEnrolledCourseDetails(@PathVariable("id") int id) {
//		Set<StudentCourse> courses = databaseService.findStudentEnrolledCouse(id).getStudentCoursesDetails();
//		for(StudentCourse sc:courses) {
//			log.info(sc.getCourseId().getCourseName());
//		}
////		Gson g =new Gson();
//		return (courses);
////		return new ResponseEntity<>(courses, HttpStatus.OK);
//	}
}
