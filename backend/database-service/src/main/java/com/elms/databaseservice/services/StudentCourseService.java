package com.elms.databaseservice.services;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.elms.databaseservice.models.StudentCourse;
import com.elms.databaseservice.models.StudentCourseId;
import com.elms.databaseservice.repos.StudentCourseRepo;

@Service
public class StudentCourseService {

	@Autowired
	StudentCourseRepo studentCourseRepo;

	private Logger log = LoggerFactory.getLogger(StudentCourseService.class);
	
	@Transactional
	public ResponseEntity<String> addLessonIdInStudentCourse(int sid, int cid, int lid) {
		try {
		StudentCourse sc=studentCourseRepo.findById(new StudentCourseId(sid, cid)).get();
		sc.setCurrentLessonId(lid);
		studentCourseRepo.save(sc);
		log.info("Saved in Studentcourse repo");
		return new ResponseEntity<>("Current Lesson ID updated",HttpStatus.OK);
		}
		catch (Exception e) {
			log.error("Current Lesson id not updated");
			return new ResponseEntity<>("Current Lesson ID not updated",HttpStatus.NOT_IMPLEMENTED);
		}
		
	}
	
//	@Transactional
//	public StudentCourse getStudentCourseById(int sid,int cid) {
//			return studentCourseRepo.findByStudentIdAndCourseId(sid, cid);
//	}
}
