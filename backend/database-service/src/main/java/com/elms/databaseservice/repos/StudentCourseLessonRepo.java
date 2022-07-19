package com.elms.databaseservice.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.elms.databaseservice.models.StudentCourseLesson;
import com.elms.databaseservice.models.StudentCourseLessonId;

public interface StudentCourseLessonRepo extends JpaRepository<StudentCourseLesson, StudentCourseLessonId> {

	@Query(nativeQuery = true,value = "select COUNT(lesson_id) from student_course_lesson where stu_id=:studentId and course_id=:courseId")
	int getCompletedLessonCount(int studentId,int courseId);

	
}
