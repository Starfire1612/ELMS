package com.elms.databaseservice.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.elms.databaseservice.models.Student;

public interface StudentRepo extends JpaRepository<Student, Integer> {
	public Student findByStudentId(int id);
}
