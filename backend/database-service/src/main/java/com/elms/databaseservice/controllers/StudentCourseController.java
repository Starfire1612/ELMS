package com.elms.databaseservice.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.elms.databaseservice.proxy.AuthClient;
import com.elms.databaseservice.services.StudentCourseService;

@RestController
public class StudentCourseController {
	
	@Autowired
	StudentCourseService studentCourseService;
	
	@Autowired
	AuthClient client;

	private Logger log = LoggerFactory.getLogger(StudentCourseController.class);
	
	@PutMapping("/student/{id}/course/{courseId}/lesson/{lid}")
	public ResponseEntity<String> addLessonIdinStudentCourse(@RequestHeader(value = "Authorization", required = true) String requestTokenHeader,@PathVariable("id") int id,@PathVariable("courseId") int cid,@PathVariable("lid") int lid)
	{
		log.info("Updating current lessons id in student Course table");
		if(client.authorizeTheRequest(requestTokenHeader,id))
			return studentCourseService.addLessonIdInStudentCourse(id,cid,lid);
		else
		{
			log.error("User not authenticated");
			
			return new ResponseEntity<>("User authentication failed",HttpStatus.BAD_REQUEST);
		}
	}
}
