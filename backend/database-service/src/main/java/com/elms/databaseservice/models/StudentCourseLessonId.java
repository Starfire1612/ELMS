package com.elms.databaseservice.models;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class StudentCourseLessonId implements Serializable {

	private int studentId;
	private int courseId;
	private int lessonId;
	
}