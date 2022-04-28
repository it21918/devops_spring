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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequestMapping("/teacher")
public class TeacherController {

    @Autowired
    private UserService userService;

    @Autowired
    private RequestService requestService;

    @Autowired
    private LessonService lessonService;

    @Autowired
    private LetterService letterService;

    private Request global_request;

    public User getLoginUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User user = userService.getUserByUsername(username);
        return user;
    }

    private String getTime() {
        LocalDateTime myDateObj = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String formattedDate = myDateObj.format(myFormatObj);
        return formattedDate;
    }


    @GetMapping("/getTeacherRequestsById/{id}")
    public ResponseEntity<List<Request>> getTeacherRequestsById(@PathVariable("id") Long id) {
        List<Request> requestList = requestService.getTeacherRequests(id);
        return new ResponseEntity<>(requestList, HttpStatus.OK);
    }

    @PostMapping("/rejectRequest")
    public ResponseEntity<Request> rejectRequest(@RequestBody Long id) {
        Request request = requestService.getRequestById(id);
        request.setStatus("rejected");
        requestService.saveRequest(request);
        return new ResponseEntity<>(request, HttpStatus.OK);
    }

    @PostMapping("/saveLetter")
    public ResponseEntity<RecommendationLetter> saveRequest(@RequestBody RecommendationLetter letter) {
        letter.setTimestamp(getTime());
        Request request = requestService.getRequestById(letter.getRequests().getId());
        request.setStatus("accepted");
        requestService.saveRequest(request);
        letterService.saveLetter(letter);
        return new ResponseEntity<>(letter, HttpStatus.OK);
    }

}
