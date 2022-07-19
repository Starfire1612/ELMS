package com.elms.databaseservice.services;

import java.io.IOException;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.elms.databaseservice.models.Course;
import com.elms.databaseservice.repos.CourseRepo;

@Service
public class CourseService {

	@Autowired
	CourseRepo courseRepo;

	@Transactional
	public Integer addCourse(Course c) {
		Course course = courseRepo.save(c);
		return course.getCourseId();
	}

	@Transactional

	public ResponseEntity<String> publishCourse(Course course) {
		// Course existingCourse=courseRepo.findById(course.getCourseId()).get();
		courseRepo.save(course);
		return new ResponseEntity<>("Course published succssfully!", HttpStatus.CREATED);

	}
	
	@Transactional
	public ResponseEntity<List<Course>> getAllCourses() {
		// Course existingCourse=courseRepo.findById(course.getCourseId()).get();
		return new ResponseEntity<>(courseRepo.findAll(),HttpStatus.OK);

	}

	@Transactional
	public ResponseEntity<String> updateCourseImage(int id, MultipartFile file) throws Exception {
		// TODO Auto-generated method stub
		// Normalize file name
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());

		try {
			// Check if the file's name contains invalid characters
			if (file.getContentType().contains("/png")) {
				Course course = courseRepo.findById(id).get();
				course.setCourseImage(file.getBytes());
				courseRepo.save(course);
				return new ResponseEntity<>(file.getName() + " " + file.getResource().getFilename(),
						HttpStatus.CREATED);
			}

			return new ResponseEntity<>("Only upload png images", HttpStatus.CREATED);
		} catch (IOException ex) {
			throw new Exception("Could not store file " + fileName + ". Please try again!", ex);
		}
	}
}
