package com.elms.registrationservice.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.elms.registrationservice.models.Student;

public interface StudentRepo extends JpaRepository<Student, Long>{

	Student findByEmail(String email);
}
