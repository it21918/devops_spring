package com.bezkoder.springjwt.repository;

import com.bezkoder.springjwt.models.Lesson;
import com.bezkoder.springjwt.models.RecommendationLetter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface LetterRepository extends JpaRepository<RecommendationLetter, Long> {

    @Query("SELECT COUNT(l.id) FROM RecommendationLetter l WHERE l.requests.id=:id")
    Long countLetters(Long id);

    @Query("SELECT l FROM RecommendationLetter l WHERE l.id=:id")
    RecommendationLetter getRecommendationLetterById(Long id);

    @Query("SELECT l FROM RecommendationLetter l WHERE l.requests.id = ?1")
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    RecommendationLetter getLetterByRequestId(Long id);
    @Query("SELECT r FROM RecommendationLetter r WHERE r.requests.receiver.id = ?1")
    List<RecommendationLetter> getTeacherLettersById(Long id);

    @Query("SELECT r FROM RecommendationLetter r WHERE r.requests.sender.id = ?1")
    List<RecommendationLetter> getStudentLettersById(Long id);
}