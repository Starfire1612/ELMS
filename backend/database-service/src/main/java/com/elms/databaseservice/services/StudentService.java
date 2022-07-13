package com.elms.databaseservice.services;

import java.io.FileNotFoundException;
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
import org.springframework.web.bind.annotation.RequestBody;

import com.elms.databaseservice.models.Course;
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

	@Transactional
	public ResponseEntity<String> enrollStudentInCourse(int studentId, int courseId) {
		StudentCourse entry = new StudentCourse();
		Optional<Student> studentDetails = studentRepo.findById(studentId);
		Optional<Course> courseDetails = courseRepo.findById((long) courseId);
		if (studentDetails.isPresent() && courseDetails.isPresent()) {
			entry.setStudentId(studentDetails.get());
			entry.setCourseId(courseDetails.get());
			return new ResponseEntity<>(studentDetails.get().getStudentName() + " enrolled succesfully in "
					+ courseDetails.get().getCourseName(), HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>("No such course exits! ", HttpStatus.OK);
		}

	}

	@Transactional
	public ResponseEntity<List<Course>> viewEnrolledCourses(int studentId) {
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

	@Transactional
	public ResponseEntity<Student> viewProfile(int studentId) {
		Optional<Student> studentDetails = studentRepo.findById(studentId);
		return new ResponseEntity<Student>(studentDetails.get(), HttpStatus.CREATED);
	}

	@Transactional
	public ResponseEntity<Student> updateProfile(@RequestBody Student student) {
		Optional<Student> studentDetails = studentRepo.findById(student.getStudentId());
		studentRepo.save(student);
		return new ResponseEntity<>(studentDetails.get(), HttpStatus.CREATED);
	}
	
	@Transactional
	public ResponseEntity<String> sendCertificate(int studentId,int courseId) throws FileNotFoundException, DocumentException, MessagingException{
		StudentCourseId studentCourseId=new StudentCourseId(studentId, courseId);
		StudentCourse studentCourseDetails=studentCourseRepo.findById(studentCourseId).get();
		pdfGenerationService.createPdfViaIText(studentCourseDetails);
		emailService.sendMailWithAttachment(studentCourseDetails);
		return new ResponseEntity<>("Course Completion Certificate Mail Sent Successfully!", HttpStatus.CREATED);
	}

//	@Transactional
//	public ResponseEntity<String> enrollStudentInCourse(int studentId,int courseId){
//		return new ResponseEntity<>();
//	}
}
