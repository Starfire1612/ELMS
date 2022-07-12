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
import javax.persistence.JoinColumns;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

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
@IdClass(StudentCourseFeedbackId.class)
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Feedback {

	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "feedback_id", unique = true)
	private int feedbackId;

	@Id
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, targetEntity = StudentCourse.class)
	@JoinColumns({
		@JoinColumn(name = "stu_id"),
		@JoinColumn(name="course_id")
	})
	private StudentCourse studentCourseId;

	@Column(name = "feedback_content")
	private String content;

	@Column(name = "feedback_ratings")
	private int ratings;

	@Override
	public String toString() {
		return "Feedback [feedbackId=" +  ", content=" + content + ", ratings=" + ratings + "]";
	}

}
