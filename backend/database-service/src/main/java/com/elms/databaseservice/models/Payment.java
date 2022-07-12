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
import org.hibernate.validator.constraints.Currency;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "payment")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@IdClass(StudentCoursePaymentId.class)
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Payment {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "payment_id", unique = true)
	private int paymentId;

	@Id
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, targetEntity = StudentCourse.class)
	@JoinColumns({ @JoinColumn(name = "stu_id"), @JoinColumn(name = "course_id") })
	private StudentCourse studentCourseId;

	@Column(name = "payment_date")
	@CreationTimestamp
	private Date paymentDate;
	@Column(name = "payment_amount")
	@Currency(value = "INR")
	private float paymentAmount;
	@Column(name = "payment_response_message")
	private String paymentResponseMessage;
	@Column(name = "payment_status")
	private String paymentStatus;

}
