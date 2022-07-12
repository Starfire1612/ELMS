package com.elms.databaseservice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.elms.databaseservice.models.StudentCourseLesson;
import com.elms.databaseservice.repos.StudentCourseLessonRepo;

@RestController
public class StudentCourseLessonController {

	@Autowired
	StudentCourseLessonRepo courseLessonRepo;
}
