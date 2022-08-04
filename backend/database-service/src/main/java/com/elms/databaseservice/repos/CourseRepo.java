package com.elms.databaseservice.repos;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.elms.databaseservice.models.Course;

public interface CourseRepo extends PagingAndSortingRepository<Course, Integer> , JpaRepository<Course, Integer> {

	List<Course> findByInstructorId(int instructorId);

	void deleteByInstructorIdAndCourseId(int instructorId, int courseId);

	@Query(nativeQuery = true,value = "select * from course where course_id=:courseId")
	Course findById(int courseId);

	@Query(nativeQuery=true,value="select * from course where "
			+ "course_name LIKE CONCAT('%',:query, '%') and course_id not in (select course_id "
			+ "from student_course where stu_id=:studentId)")
    List<Course> searchCourse(int studentId,String query);
	
	//SELECT * FROM course c , student_course sc "
	//+ " WHERE c.course_id != sc.course_id and sc.stu_id=:studentId)
	@Query(nativeQuery=true,value="select * from course where course_id not in (select course_id from student_course where stu_id=:studentId)")
	Page<Course> findallCoursesExceptEnrolled(PageRequest of,int studentId);

	
}
