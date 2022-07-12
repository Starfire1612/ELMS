package com.elms.databaseservice.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.elms.databaseservice.models.Lesson;

public interface LessonRepo extends JpaRepository<Lesson, Long> {

}
