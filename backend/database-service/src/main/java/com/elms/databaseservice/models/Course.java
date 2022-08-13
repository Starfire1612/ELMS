package com.elms.databaseservice.models;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "course")
@Getter
@Setter
public class Course {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	//@Setter(value = AccessLevel.NONE)
	@Column(name = "course_id", unique = true)
	private int courseId;
	@Column(name = "course_name", nullable = false, unique = true)
	private String courseName;
	@Column(name = "course_description", nullable = true, unique = true, columnDefinition = "varchar(255) default 'This is the default generated empty course decsription'")
	private String courseDescription;
	@Lob
	@Column(name = "course_image")
	private byte[] courseImage;
	@Column(name = "course_duration", nullable = true, columnDefinition = "int default 0")
	private int totalDuration;
	
	// @Currency(value = "INR")
	@Column(name = "course_price", nullable = true, columnDefinition = "int default 0.0")
	private float coursePrice;
	
	// @DecimalMin(value = "10")
	@Column(name = "course_discount_percent", nullable = true)
	private float courseDiscount;

	@CreatedDate
	@Column(name = "course_published")
	private Date datePublished;

	@Column(name = "course_ratings", columnDefinition = "int default 5")
	private int ratings;

	@JsonIgnore
	@ManyToOne(targetEntity = Instructor.class, cascade = { CascadeType.PERSIST,
			CascadeType.MERGE }, fetch = FetchType.LAZY)
	@JoinColumn(name = "instructor_id", nullable = false)
	private Instructor instructorId;

	@Column(name = "instructor_name", nullable = true)
	private String instructorName;

	@Column(name = "total_lessons	", nullable = true, columnDefinition = "default int 0")
	private int lessonsCount;

	@JsonIgnore
	@OneToMany(mappedBy = "courseId", cascade = CascadeType.ALL)
	private Set<StudentCourse> studentCourseDetails = new HashSet<>();
	

	@JsonIgnore
	@OneToMany(mappedBy = "courseId", cascade = CascadeType.ALL,targetEntity = InstructorCourse.class)
	private Set<InstructorCourse> instructorCourseDetails = new HashSet<>();
//	
//	@JsonIgnore
//	@ManyToOne(targetEntity = Instructor.class, cascade = { CascadeType.PERSIST,
//			CascadeType.MERGE }, fetch = FetchType.EAGER)
//	private Set<Instructor> instructors = new HashSet<>();

	//@JsonIgnore
	@OneToMany(mappedBy = "courseId", cascade = { CascadeType.ALL }, fetch = FetchType.LAZY,targetEntity = Lesson.class)
	private List<Lesson> lessons;

	@JsonIgnore
	@OrderBy("lesson_id")
	@OneToMany(mappedBy = "courseId", cascade = CascadeType.ALL)
	private Set<StudentCourseLesson> studentCourseLessons = new HashSet<>();
	
	@JsonIgnore
	@ManyToMany(cascade = CascadeType.ALL,targetEntity = InstructorCourse.class)
	private Set<InstructorCourse> instructorCourses = new HashSet<>();
}
