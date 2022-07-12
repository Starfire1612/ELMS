package com.elms.databaseservice.models;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Embeddable;

public class StudentCourseCertificateId implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3355952881252320883L;
	private int certificateId;
	private StudentCourseId studentCourseId;

	public StudentCourseCertificateId() {
		super();
	}

	public StudentCourseCertificateId(int certificateId, StudentCourseId studentCourseId) {
		super();
		this.certificateId = certificateId;
		this.studentCourseId = studentCourseId;
	}

	public int getcertificateId() {
		return certificateId;
	}

	public void setcertificateId(int certificateId) {
		this.certificateId = certificateId;
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
		return Objects.hash(certificateId, studentCourseId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StudentCourseCertificateId other = (StudentCourseCertificateId) obj;
		return certificateId == other.certificateId && Objects.equals(studentCourseId, other.studentCourseId);
	}

	

}