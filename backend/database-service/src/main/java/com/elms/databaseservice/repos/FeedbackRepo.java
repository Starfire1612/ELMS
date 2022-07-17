package com.elms.databaseservice.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.elms.databaseservice.models.Feedback;
import com.elms.databaseservice.models.StudentCourseFeedbackId;
import com.elms.databaseservice.models.StudentCourseId;

public interface FeedbackRepo extends JpaRepository<Feedback, StudentCourseId> {
	@Query(nativeQuery = true,value = "select * from feedback where stu_id=:studentId and course_id=:courseId")
	Feedback findByStudentCourseId(int studentId,int courseId);

//	List<Feedback> findByCourseId(int id);
//
//	Feedback findByStudentIdAndCourseId(int studentId, int courseId);
}