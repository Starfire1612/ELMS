package com.elms.authenticationservice.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "instructor")
public class Instructor {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "instructor_id")
	private long id;
	@Column(name = "instructor_name")
	private String name;
	@Column(name = "instructor_email")
	private String email;
	@Column(name = "instructor_password")
	private String password;

}

