package com.elms.databaseservice.repos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RestController;

import com.elms.databaseservice.models.Instructor;

public interface InstructorRepo extends JpaRepository<Instructor, Long> {

	
}
