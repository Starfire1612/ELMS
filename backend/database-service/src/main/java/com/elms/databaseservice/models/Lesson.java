package com.elms.databaseservice.models;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "lesson")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor


public class Lesson {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "lesson_id", unique = true)
//	@Setter(value = AccessLevel.NONE)
	private int lessonId;
	// @Id
//	@Setter(value = AccessLevel.NONE)
//	@ManyToOne(cascade = CascadeType.ALL,targetEntity = Course.class)
	@JoinColumn(name = "course_id", referencedColumnName = "course_id")
//	@JsonIgnore
	private int courseId;
	@Column(name = "lesson_name")
	private String lessonName;
	@Column(name = "lesson_duration")
	private int lessonDuration;
	@Column(name = "lesson_link")
	private String lessonLink;

	@JsonIgnore
	@OneToMany(mappedBy = "lessonId", cascade = CascadeType.ALL)
	private Set<StudentCourseLesson> studentCourseLessons = new HashSet<>();

}
