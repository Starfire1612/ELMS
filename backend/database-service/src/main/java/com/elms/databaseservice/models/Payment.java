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
@Table(name = "payment")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@IdClass(StudentCoursePaymentId.class)

public class Payment {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "payment_id") 
	private int paymentId;
 
	@Id
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, targetEntity = Student.class)
	@JoinColumn(name = "stu_id",unique = false)
	private Student studentId;

	@Id 
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, targetEntity = Course.class)
	@JoinColumn(name = "course_id",unique = false)
	private Course courseId;

	@Column(name = "payment_date")
	@CreationTimestamp
	private Date paymentDate;
	@Column(name = "payment_amount")

	private float paymentAmount;

	@Column(name = "payment_response_message",columnDefinition = "default varchar(30) 'SUCCESS'")
	private String paymentResponseMessage;

	@Column(name = "payment_status")
	private String paymentStatus;

}
