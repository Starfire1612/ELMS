package com.elms.registrationservice.models;

import java.math.BigInteger;
import java.sql.Blob;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "instructor")
@Data
public class Instructor {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "instructor_id",unique = true)
	private int id;
	@Column(name = "instructor_name", length = 50, nullable = false, unique = false)
	private String name;
	@Column(name = "instructor_email", length = 50, nullable = false, unique = true)
	private String email;
	@Column(name = "instructor_password", length = 50, nullable = false, unique = false)
	private String password;
	@Column(name="instructor_image",columnDefinition = "blob default 'https://img-c.udemycdn.com/user/200_H/anonymous_3.png'")
	private Blob image;
}
