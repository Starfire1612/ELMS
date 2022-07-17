package com.elms.databaseservice.models;

import java.math.BigInteger;
import java.sql.Blob;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "instructor")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Instructor {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "instructor_id", unique = true)
	private int instructorId;
	@Column(name = "instructor_name", length = 50, nullable = false, unique = false)
	private String instructorName;
	@Column(name = "instructor_email", length = 50, nullable = false, unique = true)
	private String instructorEmail;
	@Column(name = "instructor_password", length = 255, nullable = false, unique = false)
	private String instructorPassword;
	@Lob
	@Column(name = "instructor_image", columnDefinition = "blob default 'https://www.personality-insights.com/wp-content/uploads/2017/12/default-profile-pic-e1513291410505.jpg'")
	private byte[] instructorImage;
	@Column(name = "bank_ifsc_code", columnDefinition = "varchar(20) default 'SBI0005845221'")
	private String bankIfscCode;
	@Column(name = "account_number")
	private BigInteger accountNumber;
	
	@JsonIgnore
	@OneToMany(mappedBy = "instructorId")
//	@JoinTable(name = "instructor_course", 
//	joinColumns = @JoinColumn(name = "instructor_id", referencedColumnName = "instructor_id"),
//	inverseJoinColumns = @JoinColumn(name = "course_id", referencedColumnName = "course_id"))
	private Set<Course> courses = new HashSet<>();
	

	@JsonIgnore
	@OneToMany(mappedBy = "instructorId", cascade = CascadeType.ALL,targetEntity = InstructorCourse.class)
	private Set<InstructorCourse> instructorCourseDetails = new HashSet<>();
}