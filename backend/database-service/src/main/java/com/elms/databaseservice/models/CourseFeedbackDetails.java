package com.elms.databaseservice.models;

import java.util.List;

import lombok.Data;

@Data
public class CourseFeedbackDetails {

	private Course course;
	private List<Feedback> feedbacks;
}
