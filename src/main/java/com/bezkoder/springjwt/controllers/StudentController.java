package com.bezkoder.springjwt.controllers;

import com.bezkoder.springjwt.models.Lesson;
import com.bezkoder.springjwt.models.RecommendationLetter;
import com.bezkoder.springjwt.models.Request;
import com.bezkoder.springjwt.models.User;
import com.bezkoder.springjwt.service.LessonService;
import com.bezkoder.springjwt.service.LetterService;
import com.bezkoder.springjwt.service.RequestService;
import com.bezkoder.springjwt.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private UserService service;

    @Autowired
    private RequestService requestService;

    @Autowired
    private LessonService lessonService;

    @Autowired
    private LetterService letterService;

    public User getLoginUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User user = service.getUserByUsername(username);
        return user;
    }

    @PostMapping("/saveLesson")
    public ResponseEntity<Lesson> saveLesson(@RequestBody Lesson lesson) {
        // save request to database
        lesson.setRequests(global_request);
        lessonService.saveLesson(lesson);
        return new ResponseEntity<>(lesson, HttpStatus.OK);
    }

    Request global_request ;
    @PostMapping("/saveRequest")
    public ResponseEntity<Request> saveRequest(@RequestBody Request request) {
        request.setTimestamp(getTime());
        request.setStatus("pending");
        request.setSender(getLoginUser());
        global_request = request;
        requestService.saveRequest(request);
        return new ResponseEntity<>(request, HttpStatus.OK);
    }

    @GetMapping("/getTeachers")
    public ResponseEntity<List<User>> getTeachers() {
        List<User> teacherList = service.getAllTeachers();
        return new ResponseEntity<>(teacherList, HttpStatus.OK);
    }

    @GetMapping("/getLessons")
    public ResponseEntity<List<Lesson>> getLessons() {
        List<Lesson> lessonList = lessonService.getStudentLesson(getLoginUser().getId());
        return new ResponseEntity<>(lessonList, HttpStatus.OK);
    }

    @GetMapping("/getLessonsByRequestId/{id}")
    public ResponseEntity<List<Lesson>> getLessonsByRequestId(@PathVariable("id") Long id) {
        List<Lesson> lessonList = lessonService.getLessonsByRequestId(id);
        return new ResponseEntity<>(lessonList, HttpStatus.OK);
   }

    @GetMapping("/getRequestById/{id}")
    public ResponseEntity<Request> getRequestByRequestId(@PathVariable("id") Long id) {
            Request request = requestService.getRequestById(id);
        return new ResponseEntity<>(request, HttpStatus.OK);
    }


    @GetMapping("/getStudentRequestsById/{id}")
    public ResponseEntity<List<Request>> getStudentRequestsById(@PathVariable("id") Long id) {
        List<Request> requestList = requestService.getStudentRequests(id);
        return new ResponseEntity<>(requestList, HttpStatus.OK);
    }

    private String getTime() {
        LocalDateTime myDateObj = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String formattedDate = myDateObj.format(myFormatObj);
        return formattedDate;
    }

    @GetMapping("/showRequests")
    public ResponseEntity<List<Request>> showRequest() {
        User user = getLoginUser();
        List<Request> requestList = requestService.getStudentRequests(user.getId());
        return new ResponseEntity<>(requestList, HttpStatus.OK);
    }


    @GetMapping("/getLetterByRequestId/{id}")
    public ResponseEntity<RecommendationLetter> getLetterByRequestId(@PathVariable("id") Long id) {
        RecommendationLetter recommendationLetter = letterService.getLetterByRequestId(id);
        return new ResponseEntity<>(recommendationLetter, HttpStatus.OK);
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<RecommendationLetter> downloadLetter(@PathVariable("id") Long id, HttpServletResponse response) throws IOException {
        RecommendationLetter letter = letterService.getLetterById(id);

        File file = new File(new File(".").getAbsolutePath()+" letter.txt");

        if(!file.canWrite())
            file.setWritable(true);

        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        writer.write(letter.getText());
        writer.close();

        response.setContentType("application/octec-stream");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=" + file.getName();

        response.setHeader(headerKey,headerValue);
        ServletOutputStream outputStream = response.getOutputStream();
        BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(file));

        byte[] buffer = new byte[8192];
        int bytesRead = -1;
        while ((bytesRead = inputStream.read(buffer)) != -1){
            outputStream.write(buffer,0,bytesRead);
        }

        inputStream.close();
        outputStream.close();
        return new ResponseEntity<>(letter, HttpStatus.OK);

    }


}