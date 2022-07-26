package com.elms.databaseservice.models;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Embeddable;

public class StudentCourseFeedbackId implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3355952881252320883L;
	private int feedbackId;
	private StudentCourseId studentCourseId;

	public StudentCourseFeedbackId() {
		super();
	}

	
	public StudentCourseFeedbackId(int feedbackId, StudentCourseId studentCourseId) {
		super();
		this.feedbackId = feedbackId;
		this.studentCourseId = studentCourseId;
	}


	public int getFeedbackId() {
		return feedbackId;
	}

	public void setFeedbackId(int feebackId) {
		this.feedbackId = feebackId;
	}

	public StudentCourseId getStudentCourseId() {
		return studentCourseId;
	}

	public void setStudentCourseId(StudentCourseId studentCourseId) {
		this.studentCourseId = studentCourseId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public int hashCode() {
		return Objects.hash(feedbackId, studentCourseId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StudentCourseFeedbackId other = (StudentCourseFeedbackId) obj;
		return feedbackId == other.feedbackId && Objects.equals(studentCourseId, other.studentCourseId);
	}

}
//package com.elms.databaseservice.models;
//
//import java.io.Serializable;
//import java.util.Objects;
//
//import javax.persistence.Embeddable;
//
//public class StudentCourseFeedbackId implements Serializable {
//	/**
//	 * 
//	 */
//	private static final long serialVersionUID = 3355952881252320883L;
//	private int feedbackId;
//	private StudentCourseId studentCourseId;
//
//	public StudentCourseFeedbackId() {
//		super();
//	}
//
//	
//	public StudentCourseFeedbackId(int feedbackId, StudentCourseId studentCourseId) {
//		super();
//		this.feedbackId = feedbackId;
//		this.studentCourseId = studentCourseId;
//	}
//
//
//	public int getFeedbackId() {
//		return feedbackId;
//	}
//
//	public void setFeedbackId(int feebackId) {
//		this.feedbackId = feebackId;
//	}
//
//	public StudentCourseId getStudentCourseId() {
//		return studentCourseId;
//	}
//
//	public void setStudentCourseId(StudentCourseId studentCourseId) {
//		this.studentCourseId = studentCourseId;
//	}
//
//	public static long getSerialversionuid() {
//		return serialVersionUID;
//	}
//
//	@Override
//	public int hashCode() {
//		return Objects.hash(feedbackId, studentCourseId);
//	}
//
//	@Override
//	public boolean equals(Object obj) {
//		if (this == obj)
//			return true;
//		if (obj == null)
//			return false;
//		if (getClass() != obj.getClass())
//			return false;
//		StudentCourseFeedbackId other = (StudentCourseFeedbackId) obj;
//		return feedbackId == other.feedbackId && Objects.equals(studentCourseId, other.studentCourseId);
//	}
//
//}