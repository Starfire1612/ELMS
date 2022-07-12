package com.elms.databaseservice.models;

import java.io.Serializable;
import java.util.Objects;

public class StudentCourseId implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3355952881252320883L;
	private int studentId;
	private int courseId;

	public StudentCourseId() {
		
	}
	public StudentCourseId(int studentId, int courseId) {
		super();
		this.studentId = studentId;
		this.courseId = courseId;
	}
	

	public int getStudentId() {
		return studentId;
	}

	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}

	public int getCourseId() {
		return courseId;
	}

	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}

	@Override
	public int hashCode() {
		return Objects.hash(courseId, studentId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StudentCourseId other = (StudentCourseId) obj;
		return courseId == other.courseId && studentId == other.studentId;
	}

	
}