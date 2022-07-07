package com.example.authenticationexample.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.authenticationexample.models.Instructor;
import com.example.authenticationexample.models.Student;

@Repository
public interface InstructorRepo extends JpaRepository<Instructor, Long>{
	Instructor findByEmail(String email);
}
