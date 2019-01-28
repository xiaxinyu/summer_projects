package com.web.mvc.test;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.web.mvc.core.annotation.WebController;
import com.web.mvc.core.annotation.WebRequestMapping;
import com.web.mvc.core.annotation.WebRequestParameter;

@WebController
@WebRequestMapping("/test")
public class TestController {
	@WebRequestMapping("/doTest")
	public void test1(HttpServletRequest request, HttpServletResponse response,
			@WebRequestParameter("param") String param) {
		System.out.println(param);
		try {
			response.getWriter().write("doTest method success! param:" + param);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@WebRequestMapping("/doTest2")
	public void test2(HttpServletRequest request, HttpServletResponse response) {
		try {
			response.getWriter().println("doTest2 method success!");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
