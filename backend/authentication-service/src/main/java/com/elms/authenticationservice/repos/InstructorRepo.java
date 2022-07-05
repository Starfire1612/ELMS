package com.elms.authenticationservice.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.elms.authenticationservice.models.Instructor;
import com.elms.authenticationservice.models.Student;

public interface InstructorRepo extends JpaRepository<Instructor, Long> {
	Instructor findByEmail(String email);
}
