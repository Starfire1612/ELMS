package com.elms.databaseservice.models;

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
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.PrimaryKeyJoinColumns;
import javax.persistence.SecondaryTable;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "feedback")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@IdClass(StudentCourseId.class)
@SecondaryTable(name = "student_course", pkJoinColumns = {
	    @PrimaryKeyJoinColumn(name = "stu_id"),
	    @PrimaryKeyJoinColumn(name = "course_id") })
public class Feedback {

	@Id 
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "feedback_id", unique = true)
	private int feedbackId;
//,table = "student_course"
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "stu_id",table = "student_course")
	private Student studentId;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "course_id",table="student_course")
	private Course courseId;

	@Column(name = "feedback_content")
	private String content;

	@Column(name = "feedback_ratings")
	private int ratings;

	@Override
	public String toString() {
		return "Feedback [feedbackId=" + feedbackId + ", content=" + content + ", ratings=" + ratings + "]";
	}

}
