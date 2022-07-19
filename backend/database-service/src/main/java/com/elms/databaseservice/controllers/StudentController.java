	package com.elms.databaseservice.controllers;

import java.io.FileNotFoundException;
import java.util.List;

import javax.mail.MessagingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.elms.databaseservice.models.Course;
import com.elms.databaseservice.models.Payment;
import com.elms.databaseservice.models.Student;
import com.elms.databaseservice.models.StudentCourse;
import com.elms.databaseservice.proxy.AuthClient;
import com.elms.databaseservice.services.CourseService;
import com.elms.databaseservice.services.StudentService;
import com.itextpdf.text.DocumentException;

@RestController
public class StudentController {

	private Logger log = LoggerFactory.getLogger(StudentController.class);

	@Autowired
	StudentService studentService;
	@Autowired
	AuthClient client;


//	@GetMapping(path = "/students")
//	public List<Student> getAllStudents() {
//		return studentRepo.findAll();
//	}

	@Autowired
	CourseService courseService;

	@GetMapping(path = "/student/{id}/enrolled-courses")
	public ResponseEntity<List<Course>> getAllEnrolledCourses(@RequestHeader(value = "Authorization", required = true) String requestTokenHeader,@PathVariable("id") int id) {
		if(client.authorizeTheRequest(requestTokenHeader))
			return studentService.getEnrolledCourses(id);
		else
			return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);

	}

	@GetMapping(path = "/student/{id}/profile")
	public ResponseEntity<Student> getStudentProfile(@RequestHeader(value = "Authorization", required = true) String requestTokenHeader,@PathVariable("id") int id) {
		if(client.authorizeTheRequest(requestTokenHeader))
			return studentService.getProfile(id);
		else
			return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);

	}

	@PutMapping(path = "/student/{id}/profile")
	public ResponseEntity<Student> updateStudentProfil(@RequestHeader(value = "Authorization", required = true) String requestTokenHeader,@RequestBody Student s) {
		if(client.authorizeTheRequest(requestTokenHeader))
			return studentService.updateProfile(s);
		else
			return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);

	}

	@PutMapping(path = "/student/{id}/uploadProfilePic", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	public ResponseEntity<String> updateStudentProfilPic(@RequestHeader(value = "Authorization", required = true) String requestTokenHeader,@PathVariable("id") int id, @RequestBody MultipartFile file)
			throws Exception {
		if(client.authorizeTheRequest(requestTokenHeader))
			return studentService.updateProfilePic(id, file);
		else
			return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);

	}

	@GetMapping(path = "/student/{id}/course/{courseId}/certficate")
	public ResponseEntity<String> sendCourseCompletionCertificate(@RequestHeader(value = "Authorization", required = true) String requestTokenHeader,@PathVariable("id") int id,
			@PathVariable("courseId") int certId) throws FileNotFoundException, DocumentException, MessagingException {
		if(client.authorizeTheRequest(requestTokenHeader))
			return studentService.sendCertificate(id, certId);
		else
			return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);

	}

	@GetMapping(path = "/student/{id}/courses/{courseId}/courseDetails")
	public ResponseEntity<StudentCourse> getCreatedCourseDetails(@RequestHeader(value = "Authorization", required = true) String requestTokenHeader,@PathVariable("id") int id,
			@PathVariable("courseId") int courseId) {
		log.info("inside course details fetch");
		if(client.authorizeTheRequest(requestTokenHeader))
			return studentService.getCourseDetails(id, courseId);
		else
			return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);

	}

	@GetMapping(path = "/student/{id}/published-courses")
	public ResponseEntity<List<Course>> getAllPublishedCourses(@RequestHeader(value = "Authorization", required = true) String requestTokenHeader) {
		if(client.authorizeTheRequest(requestTokenHeader))
			return courseService.getAllCourses();
		else
			return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);

	}

	@PostMapping(path = "/student/{id}/course/{courseId}/enroll")
	public ResponseEntity<String> enrollInCourse(@RequestHeader(value = "Authorization", required = true) String requestTokenHeader,@PathVariable("id") int id, @PathVariable("courseId") int courseId,
			@RequestBody Payment paymentResponse) {
		if(client.authorizeTheRequest(requestTokenHeader))
			return studentService.enrollStudentInCourse(id, courseId, paymentResponse.getPaymentStatus(),
					paymentResponse.getPaymentResponseMessage(), paymentResponse.getPaymentAmount());
		else
			return new ResponseEntity<>("User Authentication Failed",HttpStatus.BAD_REQUEST);

	}

}
