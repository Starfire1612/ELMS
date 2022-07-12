package com.elms.databaseservice.models;

import java.io.Serializable;
import java.util.Objects;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StudentCourseLessonId implements Serializable {

	private int studentId;
	private int courseId;
	private int lessonId;
	
}