package com.elms.databaseservice.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.elms.databaseservice.models.StudentCourseLesson;
import com.elms.databaseservice.models.StudentCourseLessonId;

public interface StudentCourseLessonRepo extends JpaRepository<StudentCourseLesson, StudentCourseLessonId> {

}
