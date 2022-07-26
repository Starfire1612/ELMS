package com.elms.databaseservice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.elms.databaseservice.models.StudentCourseLesson;
import com.elms.databaseservice.proxy.AuthClient;
import com.elms.databaseservice.repos.StudentCourseLessonRepo;
import com.elms.databaseservice.services.StudentCourseLessonService;

@RestController
public class StudentCourseLessonController {

	@Autowired
	StudentCourseLessonService studentcourselesson;
	
	@Autowired
	AuthClient client;
	
//1.create
	@PostMapping("/student/{id}/course/{courseId}/lesson/{lid}")
	public ResponseEntity<String> addLessonIdinStudentCourse(@RequestHeader(value = "Authorization", required = true) String requestTokenHeader,@PathVariable("id") int sid,@PathVariable("courseId") int cid,@PathVariable("lid") int lid)
	{
		if(client.authorizeTheRequest(requestTokenHeader,sid))
			return studentcourselesson.addLessonInStudentCourseLesson(sid,cid,lid);
		else
			return new ResponseEntity<>("User authentication failed",HttpStatus.BAD_REQUEST);	
		}
	
	
	
	
}
