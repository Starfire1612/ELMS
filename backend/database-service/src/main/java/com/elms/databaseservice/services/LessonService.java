package com.elms.databaseservice.services;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.elms.databaseservice.models.Lesson;
import com.elms.databaseservice.models.LessonCourseId;
import com.elms.databaseservice.repos.LessonRepo;

@Service
public class LessonService {

	private Logger log=LoggerFactory.getLogger(LessonService.class);
	@Autowired
	LessonRepo lessonRepo;

	@Autowired
	CourseService courseService;

	@Transactional
	public int getTotalLessonsCountByCourseId(int courseId) {
		return lessonRepo.countByCourseId(courseId);
	}

	@Transactional
	public int getCourseDuration(int courseId) {
		return lessonRepo.courseDuration(courseId);
	}

	@Transactional
	public List<Lesson> getAllLessonByCourseId(int courseId) {
		return lessonRepo.findAllByCourseId(courseId);

	}

	@Transactional
	public ResponseEntity<String> addLessonsInCourse(List<Lesson> lessons) {
		lessonRepo.saveAll(lessons);
		// boolean areLessonAdded=lessonRepo.addLessonsByCourseId(0)
		return new ResponseEntity<>("Added lessons to the course ", HttpStatus.CREATED);
	}

	@Transactional
	public ResponseEntity<String> updateLessonByCourseIdAndLessonId(int courseId, int lessonId, Lesson lesson) {
		LessonCourseId lessonCourseId = new LessonCourseId(courseId, lessonId);
		Lesson existingLesson = lessonRepo.findById(lessonId).get();
		existingLesson.setLessonName(lesson.getLessonName());
		existingLesson.setLessonLink(lesson.getLessonLink());
		existingLesson.setLesonDuration(lesson.getLesonDuration());
		lessonRepo.save(existingLesson);
		return new ResponseEntity<>("Updated lesson to the course ", HttpStatus.CREATED);
	}

//	@Transactional
//	public ResponseEntity<String> deleteLessonByCourseIdAnsLessonId(int courseId, int lessonId) {
//		LessonCourseId lessonCourseId = new LessonCourseId(courseId, lessonId);
//		lessonRepo.deleteById(lessonId);
//		return new ResponseEntity<>("Removed lesson from the course ", HttpStatus.CREATED);
//	}
 
	@Transactional
	public ResponseEntity<String> deleteAllLessonsByCourseId(int courseId) {
		log.info("in delet method");
		//lessonRepo.de
		lessonRepo.deleteAllLessons(courseId);
		return new ResponseEntity<>("Lessons removal from course successfull!", HttpStatus.CREATED);
	}

}
