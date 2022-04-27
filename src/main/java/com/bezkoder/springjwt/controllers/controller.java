package com.bezkoder.springjwt.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class controller {

	@GetMapping("/403")
	public String errorPage() {
		return "<html><h1>Error, you don't have permission!</h1></html>";
	}



}
