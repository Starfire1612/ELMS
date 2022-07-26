package com.elms.databaseservice.controllers;

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
	
	
	@PutMapping("/student/{id}/course/{courseId}/lesson/{lid}")
	public ResponseEntity<String> addLessonIdinStudentCourse(@RequestHeader(value = "Authorization", required = true) String requestTokenHeader,@PathVariable("id") int id,@PathVariable("courseId") int cid,@PathVariable("lid") int lid)
	{
		if(client.authorizeTheRequest(requestTokenHeader,id))
			return studentCourseService.addLessonIdInStudentCourse(id,cid,lid);
		else
			return new ResponseEntity<>("User authentication failed",HttpStatus.BAD_REQUEST);

	}
}
