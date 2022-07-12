package com.elms.databaseservice.models;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Entity
@Table(name = "lesson")
@Data

public class Lesson {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "lesson_id", unique = true)
	private int lessonId;
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "course_id")
	private Course courseId;
	@Column(name = "lesson_name")
	private String lessonName;
	@Column(name = "lesson_duration")
	private int lesonDuration;
	@Column(name = "lesson_link")
	private String lessonLink;
	@JsonIgnore
	@OneToMany(mappedBy = "lessonId", cascade = CascadeType.ALL)
	private Set<StudentCourseLesson> studentCourseLessons = new HashSet<>();

}
