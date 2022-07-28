package com.elms.databaseservice.services;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.mail.MessagingException;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import com.elms.databaseservice.models.Course;
import com.elms.databaseservice.models.Payment;
import com.elms.databaseservice.models.Student;
import com.elms.databaseservice.models.StudentCourse;
import com.elms.databaseservice.models.StudentCourseId;
import com.elms.databaseservice.repos.CourseRepo;
import com.elms.databaseservice.repos.StudentCourseRepo;
import com.elms.databaseservice.repos.StudentRepo;
import com.itextpdf.text.DocumentException;

@Service
public class StudentService {

	private Logger log =LoggerFactory.getLogger(StudentService.class);
	@Autowired
	StudentRepo studentRepo;

	@Autowired
	CourseRepo courseRepo;

	@Autowired
	StudentCourseRepo studentCourseRepo;

	@Autowired
	EmailService emailService;

	@Autowired
	PdfGenerationService pdfGenerationService;

	@Autowired
	PaymentService paymentService;

//	@Transactional
//	public ResponseEntity<String> enrollStudentInCourse(int studentId, int courseId) {
//		StudentCourse entry = new StudentCourse();
//		Optional<Student> studentDetails = studentRepo.findById(studentId);
//		Optional<Course> courseDetails = courseRepo.findById((int) courseId);
//		if (studentDetails.isPresent() && courseDetails.isPresent()) {
//			entry.setStudentId(studentDetails.get());
//			entry.setCourseId(courseDetails.get());
//			return new ResponseEntity<>(studentDetails.get().getStudentName() + " enrolled succesfully in "
//					+ courseDetails.get().getCourseName(), HttpStatus.CREATED);
//		} else {
//			return new ResponseEntity<>("No such course exits! ", HttpStatus.OK);
//		}
//
//	}

	@Transactional
	public ResponseEntity<List<Course>> getEnrolledCourses(int studentId) {
		try {
		StudentCourse entry = new StudentCourse();
		Student studentDetails = studentRepo.findById(studentId);
		
		List<Course> enrolledCourses = new ArrayList<>();
		if (studentDetails!=null) {
			Set<StudentCourse> studentCourseDetails = studentDetails.getStudentCoursesDetails();
			for (StudentCourse sc : studentCourseDetails) {
				enrolledCourses.add(sc.getCourseId());
			}
			log.info("Getting all details regarding enrolled course");
			return new ResponseEntity<List<Course>>(enrolledCourses, HttpStatus.OK);
		} else {
			
			return new ResponseEntity<>(enrolledCourses, HttpStatus.OK);
		}
		}
		catch (Exception e) {
			log.error("No Course FOund");
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}

	}

	@Transactional
	public ResponseEntity<Student> getProfile(int studentId) {
		Student studentDetails = studentRepo.findById(studentId);
		if(studentDetails!=null)
		{
			return new ResponseEntity<Student>(studentDetails, HttpStatus.OK);
		}else
		{
log.error("Student details not found");
			return new ResponseEntity<Student>(studentDetails, HttpStatus.NOT_FOUND);
		}
	}


	@Transactional
	public ResponseEntity<String> sendCertificate(int studentId, int courseId)
			throws FileNotFoundException, DocumentException, MessagingException {
		try {
		StudentCourseId studentCourseId = new StudentCourseId(studentId, courseId);
		StudentCourse studentCourseDetails = studentCourseRepo.findById(studentCourseId).get();
		log.info("finding student enrolled in course");
		pdfGenerationService.createPdfViaIText(studentCourseDetails);
		emailService.sendMailWithAttachment(studentCourseDetails);
		log.debug("Sending student certificate");
		return new ResponseEntity<>("Course Completion Certificate Mail Sent Successfully!", HttpStatus.CREATED);
		}
		catch (Exception e) {
			log.error("Certificate mail cannot be sent");
			return new ResponseEntity<>("Course Completion Certificate Mail Cannot Be sent due to some error", HttpStatus.NOT_IMPLEMENTED);
		}
	}

	@Transactional
	public ResponseEntity<StudentCourse> getCourseDetails(int studentId, int courseId) {
		try {
		StudentCourse studentCourseDetails = studentCourseRepo.findById(new StudentCourseId(studentId, courseId)).get();
//		studentCourseDetails.getCourseId().getLessons();
		return new ResponseEntity<>(studentCourseDetails, HttpStatus.OK);
		}
		catch (Exception e) {
log.error("Cannot fetch course details");
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		
		}
	}

	@Transactional
	public ResponseEntity<String> enrollStudentInCourse(int studentId, int courseId, String paymentStatus,
			String paymentMessage, float amount) {
		try {
		

		Payment payment = new Payment();
		payment.setPaymentAmount(amount);
		payment.setPaymentStatus(paymentStatus);
		payment.setPaymentResponseMessage(paymentMessage);
		log.info("Course Id"+courseId);
		Student s = studentRepo.findById(studentId);
		log.info(s.getStudentEmail());
		Course c = courseRepo.findById(courseId);
		payment.setStudentId(s);
		payment.setCourseId(c);
		log.info("Payment enroll process"+s.getStudentEmail());
		log.info("Payment enroll process"+c.getCourseName());
		return paymentService.createPayment(payment);
		}
		catch (Exception e) {

log.error("Cannot enroll student in course");
			return new ResponseEntity<String>("Cannot enroll student in this course due to some error",HttpStatus.NOT_IMPLEMENTED);
		}
	}

	@Transactional
	public ResponseEntity<Student> updateProfile(@RequestBody Student student) {
		Student studentDetails = studentRepo.findById(student.getStudentId());

log.info("Fetched Student Details");
		if(studentDetails!=null)
		{
			studentRepo.save(student);
			return new ResponseEntity<>(studentDetails, HttpStatus.CREATED);
		}

log.error("Cannot update profile");
		return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
	}

	@Transactional
	public ResponseEntity<String> updateProfilePic(int id, MultipartFile file) throws Exception {
		// TODO Auto-generated method stub
		// Normalize file name
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());

		try {
			// Check if the file's name contains invalid characters
			if (file.getContentType().contains("/png")) {
				Student studentDetails = studentRepo.findById(id);

log.info("fetched student details");
				studentDetails.setStudentImage(file.getBytes());
				studentRepo.save(studentDetails);
				return new ResponseEntity<>(file.getName() + " " + file.getResource().getFilename(),
						HttpStatus.CREATED);
			}

log.warn("Only upload png image");
			return new ResponseEntity<>("Only upload png images", HttpStatus.CREATED);
		} catch (IOException ex) {

log.error("Cannot store file");
			return new ResponseEntity<>("Could not store file " + fileName + ". Please try again!", HttpStatus.BAD_GATEWAY);
		}

	}

	public ResponseEntity<List<Course>> getSearchCourses(String search) {
		 List<Course> courses=courseRepo.searchCourse(search);
		 if(courses==null)
		 {
			 log.error("Cannot fetch search course");
			 return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
		 }
		 else
			 return new ResponseEntity<>(courses,HttpStatus.FOUND);
	}

}
