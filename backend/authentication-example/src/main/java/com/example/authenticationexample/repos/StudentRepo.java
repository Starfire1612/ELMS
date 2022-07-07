package com.example.authenticationexample.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.authenticationexample.models.Student;

@Repository
public interface StudentRepo extends JpaRepository<Student, Long>{
	Student findByEmail(String email);
}
