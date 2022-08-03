package com.elms.databaseservice.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.elms.databaseservice.models.StudentCourse;
import com.elms.databaseservice.models.StudentCourseId;

public interface StudentCourseRepo extends JpaRepository<StudentCourse, StudentCourseId>,
		PagingAndSortingRepository<StudentCourse, StudentCourseId> {
	
	@Query(nativeQuery = true,value = "select count(stu_id) from student_course where course_id=:courseId")
	int findByCourseId(int courseId);
//
//	StudentCourse findByStudentIdAndCourseId(int stuId, int courseId);
}
