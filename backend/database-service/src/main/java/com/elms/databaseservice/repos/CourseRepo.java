package com.elms.databaseservice.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.elms.databaseservice.models.Course;

public interface CourseRepo extends PagingAndSortingRepository<Course, Integer> , JpaRepository<Course, Integer> {

	List<Course> findByInstructorId(int instructorId);

	void deleteByInstructorIdAndCourseId(int instructorId, int courseId);

	@Query(nativeQuery = true,value = "select * from course where course_id=:courseId")
	Course findById(int courseId);
	
	@Query(nativeQuery=true,value="SELECT * FROM course c WHERE " +
            "c.course_name LIKE CONCAT('%',:query, '%')")
    List<Course> searchCourse(String query);
}
