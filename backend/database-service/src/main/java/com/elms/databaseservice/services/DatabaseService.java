package com.elms.databaseservice.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.elms.databaseservice.models.Course;
import com.elms.databaseservice.models.Student;
import com.elms.databaseservice.repos.CourseRepo;
import com.elms.databaseservice.repos.InstructorRepo;
import com.elms.databaseservice.repos.LessonRepo;
import com.elms.databaseservice.repos.StudentCourseLessonRepo;
import com.elms.databaseservice.repos.StudentRepo;

@Service
public class DatabaseService {

	@Autowired
	CourseRepo courseRepo;

	@Autowired
	StudentRepo studentRepo;

	@Autowired
	InstructorRepo instructorRepo;

	@Autowired
	LessonRepo lessonRepo;

	@Autowired
	StudentCourseLessonRepo courseLessonRepo;
	
	@Transactional
	public List<Student> getAllStudents(){
		return studentRepo.findAll();
	}
	@Transactional
	public Student findStudentEnrolledCouse(int studentID){
		Student s=studentRepo.findByStudentId(studentID);
		return s;
	}
	
	@Transactional
	public List<Course> findAllAvailableCourses(){
		return courseRepo.findAll();
	}
}
