package com.example.authenticationexample.models;

import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "student")
@Data
public class Student {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "stu_id",unique = true)
	private int id;
	@Column(name = "stu_name", length = 50, nullable = false, unique = false)
	private String name;
	@Column(name = "stu_email", length = 50, nullable = false, unique = true)
	private String email;
	@Column(name = "stu_password", length = 50, nullable = false, unique = false)
	private String password;

}
