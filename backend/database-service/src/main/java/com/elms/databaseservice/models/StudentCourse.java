package com.elms.databaseservice.models;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "student_course")
@IdClass(StudentCourseId.class)

@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class StudentCourse {

	@JsonIgnore
	@Id
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "stu_id")
	//@Setter(value = AccessLevel.NONE)
	private Student studentId;
	
	@JsonIgnore
	@Id
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "course_id")
	//@Setter(value = AccessLevel.NONE)
	private Course courseId;
	
	
	@Column(name = "course_status", columnDefinition = " default varchar(255) 'pending'",nullable = true)
	private String courseStatus;
	
	@Column(name = "course_completion_percent", columnDefinition = " default int 0")
	private int courseCompletionPercent;
	
	@Column(name = "current_lesson_id")
	private int currentLessonId;


	@Override
	public String toString() {
		return "StudentCourse [courseStatus=" + courseStatus + ", courseCompletionPercent=" + courseCompletionPercent
				+ ", currentLessonId=" + currentLessonId + "]";
	}

}
