package com.elms.databaseservice.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.elms.databaseservice.models.Student;

public interface StudentRepo extends JpaRepository<Student, Integer> {
	@Query(nativeQuery = true,value = "select * from student where stu_id=:id")
	public Student findById(int id);
}
