package com.summer.project.oauth2.controller.auth;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {

	@RequestMapping("/oauth/index")
	public ModelAndView loginPage() {
		ModelAndView view = new ModelAndView();
		view.setViewName("oauth/index");
		view.addObject("loginProcessUrl", "/oauth/authorize");
		return view;
	}
}