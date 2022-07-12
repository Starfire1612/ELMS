package com.elms.databaseservice.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.elms.databaseservice.models.Feedback;

@Repository
public interface FeedbackRepo extends JpaRepository<Feedback,Long>  {


	List<Feedback> findByCourseId(int id);

	void deleteByStudentIdAndCourseId(int studentId, int courseId);


	Feedback findByStudentIdAndCourseId(int studentId, int courseId);
	
}
