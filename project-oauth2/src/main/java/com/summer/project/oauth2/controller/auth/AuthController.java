package com.summer.project.oauth2.controller.auth;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.summer.project.oauth2.core.contant.AuthConstant;

@Controller
public class AuthController {
	@GetMapping(AuthConstant.LOGIN_PAGE_URL)
	public String loginPage(Model model) {
		model.addAttribute("loginProcessUrl", AuthConstant.LOGIN_PROCESSING_URL);
		return "login";
	}
}