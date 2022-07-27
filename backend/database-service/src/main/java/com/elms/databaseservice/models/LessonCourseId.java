package com.elms.databaseservice.models;

import java.io.Serializable;
import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LessonCourseId implements Serializable {
	/**
	 * 
	 */

	private int lessonId;
	private int courseId;

	@Override
	public int hashCode() {
		return Objects.hash(lessonId, courseId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LessonCourseId other = (LessonCourseId) obj;
		return lessonId == other.lessonId && courseId == other.courseId;
	}

}