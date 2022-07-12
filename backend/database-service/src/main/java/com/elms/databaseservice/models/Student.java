package com.elms.databaseservice.models;

import java.sql.Blob;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Entity
@Table(name = "student")
@Data
public class Student {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "stu_id", unique = true)
	private int studentId;
	@Column(name = "stu_name", length = 50, nullable = false, unique = false)
	private String studentName;
	@Column(name = "stu_email", length = 50, nullable = false, unique = true)
	private String studentEmail;
	@Column(name = "stu_password", length = 255, nullable = false, unique = false)
	private String studentPassword;
//  @JsonIgnore
//	@Lob
//	@Column(name="stu_image")
//	private Blob studentImage;

	@JsonIgnore
	@OneToMany(mappedBy = "studentId", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<StudentCourse> studentCoursesDetails = new HashSet<>();
	@JsonIgnore
	@OneToMany(mappedBy = "studentId", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<StudentCourseLesson> studentCourseLessons = new HashSet<>();
}
