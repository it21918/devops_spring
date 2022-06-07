package com.bezkoder.springjwt.controllers;

import com.bezkoder.springjwt.models.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class controller {

	@GetMapping("/test")
	public ResponseEntity<String> getTeachers() {
		return new ResponseEntity<>("All good!", HttpStatus.OK);
	}

}
