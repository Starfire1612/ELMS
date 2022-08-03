package com.elms.databaseservice.services;

import java.io.FileNotFoundException;

import javax.mail.MessagingException;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.elms.databaseservice.models.Course;
import com.elms.databaseservice.models.Lesson;
import com.elms.databaseservice.models.Student;
import com.elms.databaseservice.models.StudentCourse;
import com.elms.databaseservice.models.StudentCourseId;
import com.elms.databaseservice.models.StudentCourseLesson;
import com.elms.databaseservice.repos.CourseRepo;
import com.elms.databaseservice.repos.LessonRepo;
import com.elms.databaseservice.repos.StudentCourseLessonRepo;
import com.elms.databaseservice.repos.StudentCourseRepo;
import com.elms.databaseservice.repos.StudentRepo;
import com.itextpdf.text.DocumentException;

@Service
public class StudentCourseLessonService {
	@Autowired
	StudentRepo studentRepo;

	@Autowired
	CourseRepo courseRepo;

	@Autowired
	StudentCourseRepo studentCourseRepo;

	@Autowired
	StudentCourseLessonRepo studentCourseLessonRepo;

	@Autowired
	LessonRepo lessonRepo;

	@Autowired
	StudentService studentService;
	private static Logger log = LoggerFactory.getLogger(StudentCourseLessonService.class);

	@Transactional
	public ResponseEntity<String> addLessonInStudentCourseLesson(int sid, int cid, int lid) {
		// TODO Auto-generated method stub
		log.info("Adding lesson in student course lesson table");

		try {
			Student s = studentRepo.findById(sid);
			Course c = courseRepo.findById(cid);
			Lesson l = lessonRepo.findById(lid).get();
			log.info("Getting student,course and lessons");
			StudentCourseLesson scl = new StudentCourseLesson(s, c, l);
			studentCourseLessonRepo.save(scl);
			log.info("Student Course lesson repo data added");
			int completedLesson = studentCourseLessonRepo.getCompletedLessonCount(sid, cid);
			log.info("Completed Lesson till now:"+completedLesson);
			int completionPercent= completePercent(sid, cid, completedLesson);
			log.info("Completed Prcent"+completionPercent);
			return new ResponseEntity<>(""+completionPercent, HttpStatus.CREATED);
		} catch (Exception e) {
			// TODO: handle exception
			log.error("Cannot add lesson in studentcourselesson table");
			return new ResponseEntity<>("Lesson not completed", HttpStatus.NOT_IMPLEMENTED);
		}
	}

	@Transactional
	public int completePercent(int sid, int cid, int completedLesson) throws MessagingException, FileNotFoundException, DocumentException {
		StudentCourse sc = studentCourseRepo.findById(new StudentCourseId(sid, cid)).get();

		Course c = courseRepo.findById(cid);
		log.info("fetched course and student course");
		int completionPercent = (int) ((completedLesson * 100) / c.getLessonsCount());
		sc.setCourseCompletionPercent(completionPercent);
		studentCourseRepo.save(sc);
		if (completionPercent == 100)
			{
			sc.setCourseStatus("Completed");
//			log.info("send mail process started ...");
//			ResponseEntity<String>response=studentService.sendCertificate(sid, cid);
			}
		log.info("saved all in studentcourse repo");
		return completionPercent;
	}

	
}
