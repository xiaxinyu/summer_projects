package com.web.server.boot;

import java.util.Map;

import com.web.server.core.Servlet;
import com.web.server.core.WebServer;
import com.web.server.core.configuration.ConfigurationFactory;

public class Bootstrap {
	public static void main(String[] args) {
		Map<String, Servlet> configuration = ConfigurationFactory.getConfiguration();
		new WebServer().start(configuration);
	}
}