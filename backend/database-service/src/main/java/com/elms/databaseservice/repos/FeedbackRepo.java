package com.elms.databaseservice.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.elms.databaseservice.models.Feedback;
import com.elms.databaseservice.models.StudentCourseId;

public interface FeedbackRepo extends JpaRepository<Feedback, StudentCourseId> {
	@Query(nativeQuery = true,value = "select * from feedback where stu_id=:studentId and course_id=:courseId")
	Feedback findByStudentCourseId(int studentId,int courseId);


	@Query(nativeQuery = true,value = "select * from feedback where course_id=:courseId")
	List<Feedback> findByCourseId(int courseId);

	List<Feedback> findByRatings(int rating);


	
}