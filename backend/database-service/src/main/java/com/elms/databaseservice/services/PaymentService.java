package com.elms.databaseservice.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.elms.databaseservice.models.Payment;
import com.elms.databaseservice.models.StudentCourse;
import com.elms.databaseservice.models.StudentCourseId;
import com.elms.databaseservice.models.StudentCourseLesson;
import com.elms.databaseservice.models.StudentCourseLessonId;
import com.elms.databaseservice.repos.PaymentRepo;
import com.elms.databaseservice.repos.StudentCourseLessonRepo;
import com.elms.databaseservice.repos.StudentCourseRepo;

@Service
public class PaymentService {

	@Autowired
	PaymentRepo paymentRepo;

	@Autowired
	StudentCourseRepo studentCourseRepo;

	@Autowired
	StudentCourseLessonRepo studentCourseLessonRepo;

	@Transactional
	public ResponseEntity<String> createPayment(Payment payment) {
		paymentRepo.save(payment);
		StudentCourse studentCourse = new StudentCourse();
		studentCourse.setCourseId(payment.getCourseId());
		studentCourse.setStudentId(payment.getStudentId());
		studentCourse.setCourseStatus("pending");
		int lesson = payment.getCourseId().getLessons().get(0).getLessonId();
		studentCourse.setCurrentLessonId(lesson);
		studentCourseRepo.save(studentCourse);
		StudentCourseLessonId studentCourseLessonId = new StudentCourseLessonId(payment.getStudentId().getStudentId(),
				payment.getCourseId().getCourseId(), lesson);
		studentCourseRepo.save(null);
		return new ResponseEntity<String>("Payment done successfully!", HttpStatus.OK);
	}

	@Transactional
	public ResponseEntity<Float> showTotalRevenueByCourseId(int courseId) {
		try {
			float totalCourseRevenue = paymentRepo.getTotalRevenueByCourseId(courseId);
			return new ResponseEntity<Float>(totalCourseRevenue, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Float>(0.0f, HttpStatus.OK);
		}
	}

	@Transactional
	public List<Payment> getAllPayments() {
		return paymentRepo.findAll();
	}
}
