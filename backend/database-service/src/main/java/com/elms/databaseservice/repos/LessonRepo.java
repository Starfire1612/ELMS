package com.elms.databaseservice.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.elms.databaseservice.models.Lesson;
import com.elms.databaseservice.models.LessonCourseId;

public interface LessonRepo extends JpaRepository<Lesson, Integer> {
	@Query(nativeQuery = true,value = "select count(*) from lesson where course_id=:courseId")
	public int countByCourseId(int courseId);

	@Query(nativeQuery = true, value = "select * from lesson where course_id=:courseId ")
	public List<Lesson> findAllByCourseId(int courseId);

	@Query(nativeQuery = true, value = "select * from lesson where course_id=:courseId ")
	public Lesson findByCourseId(int courseId);
	
	@Query(nativeQuery = true, value = "delete * from lesson where course_id=:courseId ")
	public void deleteLessonsByCourseId(int courseId);
	
	@Query(nativeQuery = true,value = "select sum(lesson_duration) from lesson where course_id=:courseId")
	public int courseDuration(int courseId);
}
