package com.elms.databaseservice.services;

import javax.transaction.Transactional;

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
	
	@Transactional
	public ResponseEntity<String> addLessonIdInStudentCourse(int sid, int cid, int lid) {
		try {
		StudentCourse sc=studentCourseRepo.findById(new StudentCourseId(sid, cid)).get();
		sc.setCurrentLessonId(lid);
		studentCourseRepo.save(sc);
		return new ResponseEntity<>("Current Lesson ID updated",HttpStatus.OK);
		}
		catch (Exception e) {
			return new ResponseEntity<>("Current Lesson ID not updated",HttpStatus.NOT_IMPLEMENTED);
		}
		
	}
	
}
