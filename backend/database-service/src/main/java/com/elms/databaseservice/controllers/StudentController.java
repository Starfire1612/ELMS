	package com.elms.databaseservice.controllers;

import java.io.FileNotFoundException;
import java.util.List;

import javax.mail.MessagingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.elms.databaseservice.models.Course;
import com.elms.databaseservice.models.CourseFeedbackDetails;
import com.elms.databaseservice.models.Payment;
import com.elms.databaseservice.models.Student;
import com.elms.databaseservice.models.StudentCourse;
import com.elms.databaseservice.proxy.AuthClient;
import com.elms.databaseservice.services.CourseService;
import com.elms.databaseservice.services.StudentService;
import com.itextpdf.text.DocumentException;
//@CrossOrigin(origins="http://localhost:3000")
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
		log.info("Getting student enrolled course");
		if(client.authorizeTheRequest(requestTokenHeader,id))
			return studentService.getEnrolledCourses(id);
		else
		{
			log.error("User not authenticated");
			return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping(path = "/student/{id}/profile")
	public ResponseEntity<Student> getStudentProfile(@RequestHeader(value = "Authorization", required = true) String requestTokenHeader,@PathVariable("id") int id) {
		log.info("Getting Student Profile");
		if(client.authorizeTheRequest(requestTokenHeader,id))
			return studentService.getProfile(id);
		else
		{
			log.error("User not authenticated");
			
			return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
		}
	}

	@PatchMapping(path = "/student/{id}/profile")
	public ResponseEntity<Student> updateStudentProfil(@RequestHeader(value = "Authorization", required = true) 
	String requestTokenHeader,@PathVariable("id") int id,@RequestBody Student s) {
		log.info("Updating Student Profile");
		if(client.authorizeTheRequest(requestTokenHeader,id))
			return studentService.updateProfile(s);
		else
		{
			log.error("User not authenticated");
			return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
		}
	}

	@PatchMapping(path = "/student/{id}/uploadProfilePic", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	public ResponseEntity<Student> updateStudentProfilPic(@RequestHeader(value = "Authorization", required = true) String requestTokenHeader,
			@PathVariable("id") int id,
			@RequestBody MultipartFile file
			)
			throws Exception {
		log.info("Updating profile pic");
		
		if(client.authorizeTheRequest(requestTokenHeader,id))
			return studentService.updateProfilePic(id, file);
		else
		{
			log.error("User not authenticated");
			
			return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping(path = "/student/{id}/course/{courseId}/certficate")
	public ResponseEntity<String> sendCourseCompletionCertificate(@RequestHeader(value = "Authorization", required = true) String requestTokenHeader,@PathVariable("id") int id,
			@PathVariable("courseId") int courseId) throws FileNotFoundException, DocumentException, MessagingException {
		log.info("sending certificate");
		if(client.authorizeTheRequest(requestTokenHeader,id))
			return studentService.sendCertificate(id, courseId);
		else
		{
			log.error("User not authenticated");
			
			return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
		}
	}
	@GetMapping(path = "/student/{id}/search/{search}")
	public ResponseEntity<List<Course>> getSearchCourses(@RequestHeader(value = "Authorization", required = true) String requestTokenHeader,@PathVariable("id") int id,@PathVariable("search") String search) {
		log.info("getting searched course");
		if(client.authorizeTheRequest(requestTokenHeader,id))
			return studentService.getSearchCourses(id,search);
		else
		{
			log.error("User not authenticated");
			
			return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
		}
	}


	@GetMapping(path = "/student/{id}/courses/{courseId}/courseDetails")
	public ResponseEntity<StudentCourse> getCreatedCourseDetails(@RequestHeader(value = "Authorization", required = true) String requestTokenHeader,@PathVariable("id") int id,
			@PathVariable("courseId") int courseId) {
		log.info("inside course details fetch");
		if(client.authorizeTheRequest(requestTokenHeader,id))
			return studentService.getCourseDetails(id, courseId);
		else
		{
			log.error("User not authenticated");			
			return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping(path = "/student/{id}/course/{cid}/view-course-details")
	public ResponseEntity<CourseFeedbackDetails> getCourseRelateDetails(@RequestHeader(value = "Authorization", required = true) String requestTokenHeader,@PathVariable("id") int id,@PathVariable("cid") int cid) {
		log.info("Getting all course related details ");
		if(client.authorizeTheRequest(requestTokenHeader,id))
			return courseService.getCourseRelatedDetails(cid);
		else
		{
			log.error("User not authenticated");
			return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
		}
	}
	
	//fetch all publish course except the enrolled ones
	@GetMapping(path = "/student/{id}/published-courses/{page}")
	public ResponseEntity<Page<Course>> getAllPublishedCourses(@RequestHeader(value = "Authorization", required = true) String requestTokenHeader,@PathVariable("id") int id,@PathVariable("page") int page) {
		log.info("Getting all course "+requestTokenHeader);
		if(client.authorizeTheRequest(requestTokenHeader,id))
			return courseService.getAllCourses(page,3,id);
		else
		{
			log.error("User not authenticated");
			return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
		}
	}
	
	
	@PostMapping(path = "/student/{id}/course/{courseId}/enroll")
	public ResponseEntity<String> enrollInCourse(@RequestHeader(value = "Authorization", required = true) String requestTokenHeader,@PathVariable("id") int id, @PathVariable("courseId") int courseId,
			@RequestBody Payment paymentRequest) {
		if(client.authorizeTheRequest(requestTokenHeader,id)) {
			log.info("Enrolling course with Course Id:"+courseId+"");
			
			return studentService.enrollStudentInCourse(id, courseId, paymentRequest.getPaymentStatus(),
					paymentRequest.getPaymentResponseMessage(), paymentRequest.getPaymentAmount());
			

		}
		else
		{
			log.error("User not authenticated");			
			return new ResponseEntity<>("User Authentication Failed",HttpStatus.BAD_REQUEST);
		}
	}

}
