package com.elms.databaseservice.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.elms.databaseservice.models.Lesson;
import com.elms.databaseservice.services.LessonService;

@RestController
public class LessonController {

	@Autowired
	LessonService lessonService;

	@PostMapping(path="/instructor/{id}/course/{cid}/lessons")
	public ResponseEntity<String> addAllLessonInCourse(@RequestBody List<Lesson> lessons){
		return lessonService.addLessonsInCourse(lessons);
	}
	
	//discuss with anurag about this lesson ui and functionality
//	@PutMapping()
//
//	@DeleteMapping()

	@GetMapping(path="/instructor/{id}/course/{cid}/lessons")
	public  List<Lesson> getAllLessonByCourseId(@PathVariable("cid") int cid){
		return lessonService.getAllLessonByCourseId(cid);
	}
	
	@DeleteMapping(path="/instructor/{id}/course/{cid}/lessons")
	public  ResponseEntity<String> deleteAllLessons(@PathVariable("cid") int cid){
		return lessonService.deleteAllLessonsByCourseId(cid);
	}
}
