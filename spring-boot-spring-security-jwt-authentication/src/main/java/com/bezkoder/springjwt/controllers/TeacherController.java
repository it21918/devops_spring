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


    @GetMapping("/selectRequest/{id}")
    public String selectRequest(@PathVariable(value = "id") Long id, Model model) {
        Request request = requestService.getRequestById(id);
        global_request = request;
        List<Lesson> lessons = lessonService.getStudentLesson(request.getId());
        model.addAttribute("request", request);
        model.addAttribute("lessons", lessons);

        return "check_request";
    }

    @GetMapping("/getTeacherRequestsById/{id}")
    public ResponseEntity<List<Request>> getTeacherRequestsById(@PathVariable("id") Long id) {
        List<Request> requestList = requestService.getTeacherRequests(id);
        return new ResponseEntity<>(requestList, HttpStatus.OK);
    }

    @RequestMapping(value = "/acceptRequest", method = RequestMethod.GET)
    public String acceptRequest() {
        Request request = requestService.getRequestById(global_request.getId());
        request.setTimestamp(getTime());
        request.setStatus("Accepted");
        requestService.saveRequest(request);
        return "redirect:/teacher/writeLetter";
    }

    @RequestMapping(value = "/rejectRequest", method = RequestMethod.GET)
    public String rejectRequest() {
        Request request = requestService.getRequestById(global_request.getId());
        request.setTimestamp(getTime());
        request.setStatus("Rejected");
        requestService.saveRequest(request);
        return "redirect:/teacher/home";
    }


    @PostMapping("/saveLetter")
    public ResponseEntity<RecommendationLetter> saveRequest(@RequestBody RecommendationLetter letter) {
        letter.setTimestamp(getTime());
        letter.getRequests().setStatus("accepted");
        letterService.saveLetter(letter);
        return new ResponseEntity<>(letter, HttpStatus.OK);
    }

}
