package com.summer.project.oauth2.controller.auth;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.oauth2.provider.AuthorizationRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

@Controller
//自定义授权页面，注意：一定要在类上加@SessionAttributes({"authorizationRequest"})
@SessionAttributes({"authorizationRequest"})
public class AuthController {

	@RequestMapping("/oauth/confirm_access")
	public ModelAndView getAccessConfirmation(Map<String, Object> model, HttpServletRequest request) throws Exception {
		AuthorizationRequest authorizationRequest = (AuthorizationRequest) model.get("authorizationRequest");
		ModelAndView view = new ModelAndView();
		view.setViewName("oauth/grant");
		view.addObject("clientId", authorizationRequest.getClientId());
		return view;
	}
}