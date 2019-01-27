package com.web.mvc.core.servlet;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebDispatcherServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getLogger(WebDispatcherServlet.class);
	private Properties properties = new Properties();
	private List<String> classNames = new ArrayList<>();

	@Override
	public void init(ServletConfig config) throws ServletException {
		logger.info("Start to web mvc.");
		// 1. load configuration files.
		loadConfiguration(config.getInitParameter("contextConfigLocation"));
		// 2. load all class files
		loadAllClazzes(properties.getProperty("scanPackage"));
		// 3. initialize all class files
		// 4. map all WebRequestMapping
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doPost(req, resp);
	}

	private void doDispatch(HttpServletRequest req, HttpServletResponse resp) throws Exception {
	}

	private void loadConfiguration(String location) {
		InputStream in = this.getClass().getClassLoader().getResourceAsStream(location);
		try {
			properties.load(in);
		} catch (IOException e) {
			logger.error("Loading configuration has error.", e);
		} finally {
			if (null != in) {
				try {
					in.close();
				} catch (IOException e) {
					logger.error("Closing properties file input stream has error.", e);
				}
			}
		}
	}

	private void loadAllClazzes(String packageName) {
		URL url = this.getClass().getClassLoader()
				.getResource(File.separator + packageName.replaceAll("\\.", File.separator));
		File dir = new File(url.getFile());
		for (File file : dir.listFiles()) {
			if (file.isDirectory()) {
				loadAllClazzes(packageName + "." + file.getName());
			} else {
				String className = packageName + "." + file.getName().replaceFirst(".class", "");
				classNames.add(className);
			}
		}
	}
}
