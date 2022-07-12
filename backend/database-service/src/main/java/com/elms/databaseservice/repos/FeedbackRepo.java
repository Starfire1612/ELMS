package com.elms.databaseservice.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.elms.databaseservice.models.Feedback;
import com.elms.databaseservice.models.StudentCourseFeedbackId;

public interface FeedbackRepo extends JpaRepository<Feedback, StudentCourseFeedbackId>,
		PagingAndSortingRepository<Feedback, StudentCourseFeedbackId> {
//	List<Feedback> findByCourseId(int id);
//
//	void deleteByStudentIdAndCourseId(int studentId, int courseId);
//
//
//	Feedback findByStudentIdAndCourseId(int studentId, int courseId);
}