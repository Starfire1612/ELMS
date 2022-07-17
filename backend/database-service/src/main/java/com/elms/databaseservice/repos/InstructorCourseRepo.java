package com.elms.databaseservice.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.elms.databaseservice.models.InstructorCourse;
import com.elms.databaseservice.models.InstructorCourseId;

public interface InstructorCourseRepo extends JpaRepository<InstructorCourse, InstructorCourseId>{

	//void deleteByInstructorIdAndCourseId(int instructorId,int courseId);
}
