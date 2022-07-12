package com.elms.databaseservice.models;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;

import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
//@MappedSuperclass
@Table(name = "student_course")
@IdClass(StudentCourseId.class)
public class StudentCourse {

	@JsonIgnore
	@Id
//	@OneToOne(mappedBy = "stu_id",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "stu_id")
	private Student studentId;
	
	@JsonIgnore
	@Id
//	@OneToOne(mappedBy = "stu_id",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "course_id")
	private Course courseId;
	
	@Column(name = "course_status", columnDefinition = "varchar default 'pending'")
	private String courseStatus;
	
	@Column(name = "course_completion_percent", columnDefinition = "int default 0")
	private int courseCompletionPercent;
	
	@Column(name = "current_lesson_id", columnDefinition = "int default 1")
	private int currentLessonId;

	@Override
	public String toString() {
		return "StudentCourse [courseStatus=" + courseStatus + ", courseCompletionPercent=" + courseCompletionPercent
				+ ", currentLessonId=" + currentLessonId + "]";
	}

}
