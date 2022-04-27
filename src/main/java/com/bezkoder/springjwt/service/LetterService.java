package com.bezkoder.springjwt.service;

import com.bezkoder.springjwt.models.RecommendationLetter;
import com.bezkoder.springjwt.models.Request;
import com.bezkoder.springjwt.repository.LetterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LetterService {

    @Autowired
    LetterRepository letterRepository;

    public void saveLetter(RecommendationLetter letter) {
        letterRepository.save(letter);
    }

    public RecommendationLetter getLetterById(Long id) {
        return letterRepository.getRecommendationLetterById(id);
    }

    public List<RecommendationLetter> getStudentLettersById(Long id) {
        return letterRepository.getStudentLettersById(id);
    }
    public RecommendationLetter getLetterByRequestId(Long id) {
        return letterRepository.getLetterByRequestId(id);
    }

}
