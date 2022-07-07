package com.example.authenticationexample.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.authenticationexample.models.Student;

@Repository
public interface StudentRepo extends JpaRepository<Student, Long>{
	Student findByEmail(String email);
	@Modifying
	@Query(value = "update student set stu_password=:password where stu_email=:usermail",nativeQuery = true)
	boolean updateStudentPassword(@Param("usermail") String useremail,@Param("password") String password);
}
