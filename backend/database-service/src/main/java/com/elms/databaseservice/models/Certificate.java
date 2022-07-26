package com.elms.databaseservice.models;

import java.sql.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "certificate")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@IdClass(StudentCourseCertificateId.class)
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Certificate {

	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "certificate_id", unique = true)
	private int certificateId;

	@Id
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, targetEntity = StudentCourse.class)
	@JoinColumns({
		@JoinColumn(name = "stu_id"),
		@JoinColumn(name="course_id")
	})
	private StudentCourse studentCourseId;

	@Column(name = "cert_issue_date")
	@CreationTimestamp
	private Date certficateIssueDate;
 
	@Column(name = "cert_link")
	private String certficateLink;

	@Override
	public String toString() {
		return "Certificate [certificateId=" + certificateId + ", certficateIssueDate=" + certficateIssueDate
				+ ", certficateLink=" + certficateLink + "]";
	}

	

}
