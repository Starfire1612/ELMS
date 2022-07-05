package com.elms.authenticationservice.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.elms.authenticationservice.models.Student;

public interface StudentRepo extends JpaRepository<Student, Long> {
	Student findByEmail(String email);
}
