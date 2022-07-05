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
@Table(name = "student")
public class Student {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "stu_id")
	private long id;
	@Column(name = "stu_name")
	private String name;
	@Column(name = "stu_email")
	private String email;
	@Column(name = "stu_password")
	private String password;

}
