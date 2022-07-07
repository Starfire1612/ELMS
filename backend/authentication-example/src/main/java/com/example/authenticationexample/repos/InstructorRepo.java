package com.example.authenticationexample.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.authenticationexample.models.Instructor;
import com.example.authenticationexample.models.Student;

@Repository
public interface InstructorRepo extends JpaRepository<Instructor, Long>{
	Instructor findByEmail(String email);
	@Modifying
	@Query(value = "update instructor set instructor_password=:password where instructor_email=:usermail",nativeQuery = true)
	boolean updateInstructorPassword(@Param("usermail") String useremail,@Param("password") String password);
}
