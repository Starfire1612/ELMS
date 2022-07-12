package com.elms.databaseservice.models;

import java.sql.Blob;
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
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMin;

import org.hibernate.validator.constraints.Currency;
import org.springframework.data.annotation.CreatedDate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Entity
@Table(name = "course")
@Data
public class Course {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "course_id", unique = true)
	private int courseId;
	@Column(name = "course_name", nullable = false, unique = true)
	private String courseName;
	@Column(name = "course_description", nullable = false, unique = true)
	private String courseDescription;
	@Lob
	@Column(name = "course_image", columnDefinition = "blob default 'https://www.personality-insights.com/wp-content/uploads/2017/12/default-profile-pic-e1513291410505.jpg'")
	private Blob courseImage;
	@Column(name = "course_duration", nullable = true)
	private int totalDuration;
	@Currency(value = "INR")
	@Column(name = "course_price", nullable = true)
	private float coursePrice;
	@DecimalMin(value = "10")
	@Column(name = "course_discount_percent", columnDefinition = "decimal default 0.0")
	private float courseDiscount;

	@CreatedDate
	@Column(name = "course_published")
	private Date datePublished;

	@Column(name = "course_ratings", columnDefinition = "int default 5.0")
	private int ratings;

	@Column(name = "instructor_id", nullable = false)
	private int instructorId;
	@Column(name = "instructor_name", nullable = false)
	private String instructorName;
	@Column(name = "total_lessons	", nullable = true)
	private int lessonsCount;

	@JsonIgnore
	@OneToMany(mappedBy = "courseId", cascade = CascadeType.ALL)
	private Set<StudentCourse> studentCourseDetails = new HashSet<>();
	
	@JsonIgnore
	@ManyToOne(targetEntity = Instructor.class, cascade = { CascadeType.PERSIST,
			CascadeType.MERGE }, fetch = FetchType.EAGER)
	private Set<Instructor> instructors = new HashSet<>();
	
	@JsonIgnore
	@OneToMany(mappedBy = "courseId", cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.EAGER)
	private List<Lesson> lessons;
	
	@JsonIgnore
	@OrderBy("lesson_id")
	@OneToMany(mappedBy = "courseId", cascade = CascadeType.ALL)
	private Set<StudentCourseLesson> studentCourseLessons = new HashSet<>();
}
