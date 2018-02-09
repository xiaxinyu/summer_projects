package com.account.web.rest.face;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.account.core.tool.StringTool;
import com.account.persist.model.User;
import com.account.persist.model.UserCollection;
import com.account.web.exception.AppLoginException;

@Controller
public class ApplicationResource {
	private static final Logger logger = LoggerFactory.getLogger(ApplicationResource.class);

	@Autowired
	private UserCollection userCollection;
	
	@RequestMapping("/application/login")
	public ModelAndView login(HttpServletRequest request, HttpServletResponse response) throws AppLoginException{
		ModelAndView result = new ModelAndView();
		
		User user = new User();
		user.setUserName(request.getParameter("userName"));
		user.setPassword(request.getParameter("password"));
		String viewName = StringTool.EMPTY;
		String message = StringTool.EMPTY;
		boolean flag = true;
		
		try {
			if(userCollection != null && userCollection.isNotEmpty()){
				if(verify(user)){
					message = user.getUserName();
					request.getSession().setAttribute("app_username",message);
				}else{
					flag = false;
					message = "Your username or password is wrong, please try again!";
				}
				viewName = "main";
			}else {
				message = "user collection not exsist, config error!";
				throw new AppLoginException(message); 
			}
		} catch (AppLoginException e) {
			message = "login failed. params[username = " + user.getUserName() + "], error message:" + e.getMessage();
			logger.error(message, e);
			throw new AppLoginException(message); 
		}
		
		result.addObject("flag", flag);
		result.addObject("message", message);
		result.setViewName(viewName);
		return result;
	}
	
	private boolean verify(User user) throws AppLoginException{
		boolean reuslt = false;
		if(user != null && !StringTool.isNullOrEmpty(user.getUserName()) && !StringTool.isNullOrEmpty(user.getPassword())){
			User coachUser = userCollection.getUser(user.getUserName());
			if(coachUser != null){
				if(!StringTool.isNullOrEmpty(coachUser.getUserName()) && !StringTool.isNullOrEmpty(coachUser.getPassword())){
					if(coachUser.getPassword().equals(user.getPassword())){
						reuslt = true;
					}
				}else{
					throw new AppLoginException("user collection config error!"); 
				}
			}
		}else{
			throw new AppLoginException("login info is empty!"); 
		}
		return reuslt;
	}
	
	@RequestMapping("/application/logout")
	public ModelAndView logout(HttpServletRequest request, HttpServletResponse response) throws AppLoginException{
		ModelAndView result = new ModelAndView();
		
		User user = new User();
		user.setUserName(request.getParameter("userName"));
		String viewName = StringTool.EMPTY;
		String message = StringTool.EMPTY;
		boolean flag = true;
		
		try {
			request.getSession().removeAttribute("app_username");
			viewName = "main";
		} catch (Exception e) {
			message = "logout failed. params[username = " + user.getUserName() + "], error message:" + e.getMessage();
			logger.error(message, e);
			throw new AppLoginException(message); 
		}
		
		result.addObject("flag", flag);
		result.addObject("message", message);
		result.setViewName(viewName);
		return result;
	}
}
