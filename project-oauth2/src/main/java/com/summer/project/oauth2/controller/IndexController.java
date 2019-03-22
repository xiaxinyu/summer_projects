package com.summer.project.oauth2.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

	@GetMapping("/sayHello")
	private String sayHello() {
		System.out.println("Hello World");
		return "Hello World";
	}

}