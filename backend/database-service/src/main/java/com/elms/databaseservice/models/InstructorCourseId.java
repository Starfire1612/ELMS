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
public class InstructorCourseId implements Serializable {

	private int instructorId;
	private int courseId;
	@Override
	public int hashCode() {
		return Objects.hash(courseId, instructorId);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		InstructorCourseId other = (InstructorCourseId) obj;
		return courseId == other.courseId && instructorId == other.instructorId;
	}
	
	
}
