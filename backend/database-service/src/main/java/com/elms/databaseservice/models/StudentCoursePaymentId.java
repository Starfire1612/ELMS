package com.elms.databaseservice.models;

import java.io.Serializable;
import java.util.Objects;

public class StudentCoursePaymentId implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1814474146706585185L;
	private int paymentId;
	private int studentId;
	private int courseId;

	public StudentCoursePaymentId(int paymentId, int studentId, int courseId) {
		super();
		this.paymentId = paymentId;
		this.studentId = studentId;
		this.courseId = courseId;
	}

	public StudentCoursePaymentId() {
		super();
	}

	public int getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(int paymentId) {
		this.paymentId = paymentId;
	}

	public int getstudentId() {
		return studentId;
	}

	public void setstudentId(int studentId) {
		this.studentId = studentId;
	}

	public int getCourseId() {
		return courseId;
	}

	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public int hashCode() {
		return Objects.hash( paymentId, studentId , courseId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StudentCoursePaymentId other = (StudentCoursePaymentId) obj;
		return paymentId == other.paymentId && studentId == other.studentId &&courseId == other.courseId;
	}

}
