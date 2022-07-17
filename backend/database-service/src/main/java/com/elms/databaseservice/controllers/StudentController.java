package com.elms.databaseservice.controllers;

import java.io.FileNotFoundException;
import java.util.List;

import javax.mail.MessagingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.elms.databaseservice.models.Course;
import com.elms.databaseservice.models.Payment;
import com.elms.databaseservice.models.Student;
import com.elms.databaseservice.models.StudentCourse;
import com.elms.databaseservice.services.CourseService;
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

	@Autowired
	CourseService courseService;

	@GetMapping(path = "/student/{id}/enrolled-courses")
	public ResponseEntity<List<Course>> getAllEnrolledCourses(@PathVariable("id") int id) {
		return studentService.getEnrolledCourses(id);
	}

	@GetMapping(path = "/student/{id}/profile")
	public ResponseEntity<Student> getStudentProfile(@PathVariable("id") int id) {
		return studentService.getProfile(id);
	}

	@PutMapping(path = "/student/{id}/profile")
	public ResponseEntity<Student> updateStudentProfil(@RequestBody Student s) {
		return studentService.updateProfile(s);
	}

	@PutMapping(path = "/student/{id}/uploadProfilePic", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	public ResponseEntity<String> updateStudentProfilPic(@PathVariable("id") int id, @RequestBody MultipartFile file)
			throws Exception {
		return studentService.updateProfilePic(id, file);
	}

	@GetMapping(path = "/student/{id}/course/{courseId}/certficate")
	public ResponseEntity<String> sendCourseCompletionCertificate(@PathVariable("id") int id,
			@PathVariable("courseId") int certId) throws FileNotFoundException, DocumentException, MessagingException {
		return studentService.sendCertificate(id, certId);
	}

	@GetMapping(path = "/student/{id}/courses/{courseId}/courseDetails")
	public ResponseEntity<StudentCourse> getCreatedCourseDetails(@PathVariable("id") int id,
			@PathVariable("courseId") int courseId) {
		log.info("inside course details fetch");
		return studentService.getCourseDetails(id, courseId);
	}

	@GetMapping(path = "/student/{id}/published-courses")
	public List<Course> getAllPublishedCourses() {
		return courseService.getAllCourses();
	}

	@PostMapping(path = "/student/{id}/course/{courseId}/enroll")
	public ResponseEntity<String> enrollInCourse(@PathVariable("id") int id, @PathVariable("courseId") int courseId,
			@RequestBody Payment paymentResponse) {
		return studentService.enrollStudentInCourse(id, courseId, paymentResponse.getPaymentStatus(),
				paymentResponse.getPaymentResponseMessage(), paymentResponse.getPaymentAmount());
	}

}
