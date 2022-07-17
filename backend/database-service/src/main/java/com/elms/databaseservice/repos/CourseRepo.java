package com.elms.databaseservice.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.elms.databaseservice.models.Course;

public interface CourseRepo extends PagingAndSortingRepository<Course, Integer> , JpaRepository<Course, Integer> {

	List<Course> findByInstructorId(int instructorId);

	void deleteByInstructorIdAndCourseId(int instructorId, int courseId);

}
