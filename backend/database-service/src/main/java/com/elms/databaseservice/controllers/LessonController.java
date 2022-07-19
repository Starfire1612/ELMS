package com.elms.databaseservice.controllers;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.elms.databaseservice.models.Feedback;
import com.elms.databaseservice.models.Lesson;
import com.elms.databaseservice.proxy.AuthClient;
import com.elms.databaseservice.services.LessonService;

@RestController
public class LessonController {

	@Autowired
	LessonService lessonService;
	@Autowired
	AuthClient client;

	@PostMapping(path="/instructor/{id}/course/{cid}/lessons")
	public ResponseEntity<String> addAllLessonInCourse(@RequestHeader(value = "Authorization", required = true) String requestTokenHeader,@RequestBody List<Lesson> lessons){
		if(client.authorizeTheRequest(requestTokenHeader))
			return lessonService.addLessonsInCourse(lessons);
		else
			return new ResponseEntity<>("User Authentication Failed",HttpStatus.BAD_REQUEST);

	}
	
	//discuss with anurag about this lesson ui and functionality
//	@PutMapping()
//
//	@DeleteMapping()

	@GetMapping(path="/instructor/{id}/course/{cid}/lessons")
	public ResponseEntity<List<Lesson>> getAllLessonByCourseId(@RequestHeader(value = "Authorization", required = true) String requestTokenHeader,@PathVariable("cid") int cid){
		if(client.authorizeTheRequest(requestTokenHeader))
			return lessonService.getAllLessonByCourseId(cid);
		else
			return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);

	}
	
	@DeleteMapping(path="/instructor/{id}/course/{cid}/lessons")
	public  ResponseEntity<String> deleteAllLessons(@RequestHeader(value = "Authorization", required = true) String requestTokenHeader,@PathVariable("cid") int cid){
		if(client.authorizeTheRequest(requestTokenHeader))
			return lessonService.deleteAllLessonsByCourseId(cid);
		else
			return new ResponseEntity<>("User Authentication Failed",HttpStatus.BAD_REQUEST);

	}
}
