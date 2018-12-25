package com.web.server.core.external;

import com.web.server.core.Request;
import com.web.server.core.Response;
import com.web.server.core.Servlet;

public class WebServerServlet extends Servlet {
	@Override
	public void doGet(Request request, Response response) throws Exception {
		this.doPost(request, response);
	}

	@Override
	public void doPost(Request request, Response response) throws Exception {
		response.write("<html>");
		response.write("<head><title>WebServer</title></head>");
		response.write("<body><h1>Welcome to WebServer</h1></body>");
		response.write("</html>");
	}
}