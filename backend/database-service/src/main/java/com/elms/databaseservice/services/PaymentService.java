package com.elms.databaseservice.services;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

	private Logger log = LoggerFactory.getLogger(PaymentService.class);
	@Autowired
	PaymentRepo paymentRepo;

	@Autowired
	StudentCourseRepo studentCourseRepo;

	@Autowired
	StudentCourseLessonRepo studentCourseLessonRepo;

	@Transactional
	public ResponseEntity<String> createPayment(Payment payment) {
		try {
			paymentRepo.save(payment);

			log.info(payment.toString());

			StudentCourse studentCourse = new StudentCourse();
			studentCourse.setCourseId(payment.getCourseId());
			log.info("course set");

			studentCourse.setStudentId(payment.getStudentId());
			log.info("student set");
			studentCourse.setCourseStatus("pending");
			log.info("status set" + studentCourse.toString());

			log.info(payment.getCourseId().getLessons().toArray().toString());
			int lesson = payment.getCourseId().getLessons().get(0).getLessonId();
			log.info("lesson set: " + lesson + "");
			
			studentCourse.setCurrentLessonId(lesson);
			
			studentCourseRepo.save(studentCourse);
			StudentCourseLessonId studentCourseLessonId = new StudentCourseLessonId(
					payment.getStudentId().getStudentId(), payment.getCourseId().getCourseId(), lesson);
			return new ResponseEntity<String>("Payment done successfully!", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>("Payment failed", HttpStatus.BAD_GATEWAY);
		}
	}

	@Transactional
	public ResponseEntity<Float> showTotalRevenueByCourseId(int courseId) {
		try {
			float totalCourseRevenue = paymentRepo.getTotalRevenueByCourseId(courseId);
			return new ResponseEntity<>(totalCourseRevenue, HttpStatus.OK);
		} catch (Exception e) {

			log.error("Could not fetch total revenue");
			return new ResponseEntity<>(null, HttpStatus.OK);
		}
	}

	@Transactional
	public ResponseEntity<List<Payment>> getAllPayments() {
		return new ResponseEntity<>(paymentRepo.findAll(), HttpStatus.OK);
	}
}
