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
		Optional<Student> studentDetails = studentRepo.findById(studentId);
		List<Course> enrolledCourses = new ArrayList<>();
		if (studentDetails.isPresent()) {
			Set<StudentCourse> studentCourseDetails = studentDetails.get().getStudentCoursesDetails();
			for (StudentCourse sc : studentCourseDetails) {
				enrolledCourses.add(sc.getCourseId());
			}
			return new ResponseEntity<List<Course>>(enrolledCourses, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(enrolledCourses, HttpStatus.OK);
		}
		}
		catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}

	}

	@Transactional
	public ResponseEntity<Student> getProfile(int studentId) {
		Optional<Student> studentDetails = studentRepo.findById(studentId);
		if(studentDetails.isPresent())
			return new ResponseEntity<Student>(studentDetails.get(), HttpStatus.OK);
		else
			return new ResponseEntity<Student>(studentDetails.get(), HttpStatus.NOT_FOUND);
		
	}


	@Transactional
	public ResponseEntity<String> sendCertificate(int studentId, int courseId)
			throws FileNotFoundException, DocumentException, MessagingException {
		try {
		StudentCourseId studentCourseId = new StudentCourseId(studentId, courseId);
		StudentCourse studentCourseDetails = studentCourseRepo.findById(studentCourseId).get();
		pdfGenerationService.createPdfViaIText(studentCourseDetails);
		emailService.sendMailWithAttachment(studentCourseDetails);
		return new ResponseEntity<>("Course Completion Certificate Mail Sent Successfully!", HttpStatus.CREATED);
		}
		catch (Exception e) {
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
		Student s = studentRepo.findByStudentId(studentId);
		Course c = courseRepo.findById(courseId).get();
		payment.setStudentId(s);
		payment.setCourseId(c);
		return paymentService.createPayment(payment);
		}
		catch (Exception e) {
			return new ResponseEntity<String>("Cannot enroll student in this course due to some error",HttpStatus.NOT_IMPLEMENTED);
		}
	}

	@Transactional
	public ResponseEntity<Student> updateProfile(@RequestBody Student student) {
		Optional<Student> studentDetails = studentRepo.findById(student.getStudentId());
		if(studentDetails.isPresent())
		{
			studentRepo.save(student);
			return new ResponseEntity<>(studentDetails.get(), HttpStatus.CREATED);
		}
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
				Student studentDetails = studentRepo.findById(id).get();
				studentDetails.setStudentImage(file.getBytes());
				studentRepo.save(studentDetails);
				return new ResponseEntity<>(file.getName() + " " + file.getResource().getFilename(),
						HttpStatus.CREATED);
			}
			return new ResponseEntity<>("Only upload png images", HttpStatus.CREATED);
		} catch (IOException ex) {
			return new ResponseEntity<>("Could not store file " + fileName + ". Please try again!", HttpStatus.BAD_GATEWAY);
		}

	}

}
