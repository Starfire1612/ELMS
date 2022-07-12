package com.elms.databaseservice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.elms.databaseservice.repos.CourseRepo;

@RestController
public class CourseController {

	@Autowired
	CourseRepo courseRepo;
}
