package com.summer.project.oauth2.controller.resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

	@GetMapping("/api/sayHello")
	private String sayHello() {
		return "Hello World";
	}

}