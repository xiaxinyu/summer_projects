package com.summer.project.oauth2.controller.auth;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.summer.project.oauth2.core.contant.AuthConstant;

@Controller
@SessionAttributes({"authorizationRequest"})
public class AuthController {
	@GetMapping("/oauth/index")
	public String loginPage(Model model) {
		model.addAttribute("loginProcessUrl", "/oauth/authorize");
		return "index";
	}

	@RequestMapping(AuthConstant.AFTER_LOGING_PAGE)
	public String login() {
		return AuthConstant.oauthLogin;
	}

	/**
	 * 自定义授权页面，注意：一定要在类上加@SessionAttributes({"authorizationRequest"})
	 *
	 * @param model   model
	 * @param request request
	 * @return String
	 * @throws Exception Exception
	 */
	@RequestMapping("/oauth/confirm_access")
	public String getAccessConfirmation(Map<String, Object> model, HttpServletRequest request) throws Exception {
		@SuppressWarnings("unchecked")
		Map<String, String> scopes = (Map<String, String>) (model.containsKey("scopes") ? model.get("scopes")
				: request.getAttribute("scopes"));
		List<String> scopeList = new ArrayList<>();
		if (scopes != null) {
			scopeList.addAll(scopes.keySet());
		}
		model.put("scopeList", scopeList);
		return AuthConstant.oauthGrant;
	}
}