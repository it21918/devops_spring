package com.bezkoder.springjwt.repository;

import com.bezkoder.springjwt.models.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Long> {
    @Query("SELECT r FROM Lesson r WHERE r.requests.sender.id = ?1")
    List<Lesson> getStudentLesson(Long id);

    @Query("SELECT r FROM Lesson r WHERE r.requests.receiver.id = ?1")
    List<Lesson> getTeacherLesson(Long id);

    @Query("SELECT r FROM Lesson r WHERE r.requests.id = ?1")
    List<Lesson> getLessonByRequestId(Long id);



}
