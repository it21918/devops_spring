package com.bezkoder.springjwt.service;

import com.bezkoder.springjwt.models.Lesson;
import com.bezkoder.springjwt.models.Request;
import com.bezkoder.springjwt.repository.LessonRepository;
import com.bezkoder.springjwt.repository.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LessonService {
    @Autowired
    private LessonRepository repoLesson;

    @Autowired
    private RequestRepository requestRepository;

    public List<Lesson> getAllLessons() {
        return repoLesson.findAll();
    }

    public void saveLesson(Lesson lesson) {
        this.repoLesson.save(lesson);
    }

    public List<Lesson> getLessonsByRequestId(Long id) {
        List<Lesson> lessonList = repoLesson.getLessonByRequestId(id);
        return lessonList;
    }

    public List<Lesson> getStudentLesson(Long id) {
        List<Lesson> lessons = getAllLessons();
        for (int i = lessons.size(); i > 0; i--) {
            if (!lessons.get(i - 1).getRequests().getId().equals(id)) {
                lessons.remove(lessons.get(i - 1));
            }
        }
        return lessons;
    }

}
