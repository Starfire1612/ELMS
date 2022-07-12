package com.elms.databaseservice.models;

import java.io.Serializable;
import java.util.Objects;

public class StudentCoursePaymentId implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1814474146706585185L;
	private int paymentId;
	private StudentCourseId studentCourseId;

	public StudentCoursePaymentId() {
		super();
	}

	public StudentCoursePaymentId(int paymentId, StudentCourseId studentCourseId) {
		super();
		this.paymentId = paymentId;
		this.studentCourseId = studentCourseId;
	}

	public int getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(int paymentId) {
		this.paymentId = paymentId;
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
		return Objects.hash(paymentId, studentCourseId);
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
		return paymentId == other.paymentId && Objects.equals(studentCourseId, other.studentCourseId);
	}

}
