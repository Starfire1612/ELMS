package com.elms.databaseservice.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.elms.databaseservice.models.Lesson;
import com.elms.databaseservice.proxy.AuthClient;
import com.elms.databaseservice.repos.LessonRepo;
import com.elms.databaseservice.services.LessonService;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class LessonController {

	private Logger log = LoggerFactory.getLogger(LessonController.class);
	@Autowired
	LessonService lessonService;
	@Autowired
	AuthClient client;

	@Autowired
	LessonRepo lessonRepo;

	@PostMapping(path = "/instructor/{id}/course/{cid}/lessons")
	public ResponseEntity<String> addAllLessonInCourse(
			@RequestHeader(value = "Authorization", required = true) String requestTokenHeader,
			@PathVariable("id") int id, @PathVariable("cid") int cid, @RequestBody List<Lesson> lessons) {
		log.info(lessons.get(0).getCourseId() + "");

		if (client.authorizeTheRequest(requestTokenHeader, id))
			return lessonService.addLessonsInCourse(lessons);
		else
		{

			log.error("User not authenticated");
			return new ResponseEntity<>("User Authentication Failed", HttpStatus.BAD_REQUEST);
		}
	}

	// discuss with anurag about this lesson ui and functionality
//	@PutMapping()
//
//	@DeleteMapping()

	@GetMapping(path = "/instructor/{id}/course/{cid}/lessons")
	public ResponseEntity<List<Lesson>> getAllLessonByCourseId(
			@RequestHeader(value = "Authorization", required = true) String requestTokenHeader,
			@PathVariable("id") int id, @PathVariable("cid") int cid) {
		log.info("getting all lessons by course");
		if (client.authorizeTheRequest(requestTokenHeader, id))
			return lessonService.getAllLessonByCourseId(cid);
		else
		{

			log.error("User not authenticated");
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

		}	}

	@DeleteMapping(path = "/instructor/{id}/course/{cid}/lessons")
	public ResponseEntity<String> deleteAllLessons(
			@RequestHeader(value = "Authorization", required = true) String requestTokenHeader,
			@PathVariable("id") int id, @PathVariable("cid") int cid) {
		log.info("deleting all lessons");
		if (client.authorizeTheRequest(requestTokenHeader, id))
			return lessonService.deleteAllLessonsByCourseId(cid);
		else
		{

			log.error("User not authenticated");
			return new ResponseEntity<>("User Authentication Failed", HttpStatus.BAD_REQUEST);
		}
	}

	@DeleteMapping(path = "/instructor/{id}/course/{cid}/lessons/{lessonId}")
	public ResponseEntity<String> deleteLesson(
			@RequestHeader(value = "Authorization", required = true) String requestTokenHeader,
			@PathVariable("id") int id, @PathVariable("cid") int cid, @PathVariable("lessonId") int lid) {
		log.info("deleting individual lessons");
		
		if (client.authorizeTheRequest(requestTokenHeader, id))
			return lessonService.deleteLessonByCourseIdAnsLessonId(cid, lid);
		else
		{
			log.error("User not authenticated");
			
			return new ResponseEntity<>("User Authentication Failed", HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping(path = "/instructor/{id}/course/{cid}/lessons/{lessonId}")
	public ResponseEntity<String> updateLessons(
			@RequestHeader(value = "Authorization", required = true) String requestTokenHeader,
			@PathVariable("id") int id, @PathVariable("cid") int cid, @PathVariable("lessonId") int lid,
			@RequestBody Lesson newLesson) {
		log.info("Updating Lessons");
		
		if (client.authorizeTheRequest(requestTokenHeader, id))

			return lessonService.updateLessonByCourseIdAndLessonId(cid, lid, newLesson);
		else
		{log.error("User not authenticated");
		
			return new ResponseEntity<>("User Authentication Failed", HttpStatus.BAD_REQUEST);
		}
	}
}
