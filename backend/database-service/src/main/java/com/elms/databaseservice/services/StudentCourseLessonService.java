package com.elms.databaseservice.services;

import javax.transaction.Transactional;

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

	@Transactional
	public ResponseEntity<String> addLessonInStudentCourseLesson(int sid, int cid, int lid) {
		// TODO Auto-generated method stub
		try {
			Student s=studentRepo.findById(sid).get();
			Course c=courseRepo.findById(cid).get();
			Lesson l=lessonRepo.findById(lid).get();
			StudentCourseLesson scl=new StudentCourseLesson(s,c,l);
			studentCourseLessonRepo.save(scl);
			int completedLesson=studentCourseLessonRepo.getCompletedLessonCount(sid,cid);
			completePercent(sid,cid,completedLesson);
			return new ResponseEntity<>("Successully Completed Lesson and added to database",HttpStatus.CREATED);
		}
		catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<>("Lesson not completed",HttpStatus.NOT_IMPLEMENTED);
		}
	}
	
	@Transactional
	public void completePercent(int sid, int cid, int completedLesson)
	{
		StudentCourse sc=studentCourseRepo.findById(new StudentCourseId(sid,cid)).get();
		Course c=courseRepo.findById(cid).get();
		int completionPercent=(int)((completedLesson*100)/c.getLessonsCount());
		sc.setCourseCompletionPercent(completionPercent);
		studentCourseRepo.save(sc);
	}
	

}
