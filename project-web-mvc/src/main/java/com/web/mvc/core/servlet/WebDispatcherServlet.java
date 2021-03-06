package com.web.mvc.core.servlet;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.web.mvc.core.annotation.WebController;
import com.web.mvc.core.annotation.WebRequestMapping;
import com.web.mvc.core.constant.Response;

public class WebDispatcherServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getLogger(WebDispatcherServlet.class);
	private Properties properties = new Properties();
	private List<String> classNames = new ArrayList<>();
	private Map<String, Class<?>> ioc = new ConcurrentHashMap<>();
	private Map<String, Method> handlerMapping = new ConcurrentHashMap<>();
	private Map<String, Object> controllerMap = new ConcurrentHashMap<>();

	@Override
	public void init(ServletConfig config) throws ServletException {
		logger.info("Start to load WebMVC framework.");
		// 1. load configuration files.
		loadConfiguration(config.getInitParameter("contextConfigLocation"));
		// 2. load all class files
		loadAllClazzFiles(properties.getProperty("scanPackage"));
		// 3. load all classes
		loadAllClazzes();
		// 4. map all WebRequestMapping
		initializeHandlerMapping();
		logger.info("Finish to load WebMVC framework.");
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			doDispatch(req, resp);
		} catch (Exception e) {
			resp.getWriter().write(Response.RESP_500);
		}
	}

	private void doDispatch(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		if (handlerMapping.isEmpty()) {
			return;
		}

		// Get clean URL
		String reqUrl = req.getRequestURI();
		String contextPath = req.getContextPath();
		// TODO In the future, the logic about request parameter will be added
		reqUrl = reqUrl.replace(contextPath, "").replaceAll("/+", "/");
		logger.info("Request from client, ReqUrl = {}", reqUrl);

		if (!this.handlerMapping.containsKey(reqUrl)) {
			resp.getWriter().write(Response.RESP_404);
			return;
		}

		// Get request parameters
		Map<String, String[]> parameterMap = req.getParameterMap();
		Method method = this.handlerMapping.get(reqUrl);
		// Get parameter types in method
		Class<?>[] methodParamTypes = method.getParameterTypes();
		// Parameters value in method
		Object[] methodParaValues = new Object[methodParamTypes.length];

		// package http request, http response, request parameters to method
		for (int i = 0; i < methodParamTypes.length; i++) {
			String requestParam = methodParamTypes[i].getSimpleName();
			// package http request
			if (requestParam.equals("HttpServletRequest")) {
				methodParaValues[i] = req;
			}

			// package http response
			if (requestParam.equals("HttpServletResponse")) {
				methodParaValues[i] = resp;
			}

			// package all request parameter
			if (requestParam.equals("String")) {
				for (Entry<String, String[]> reqParam : parameterMap.entrySet()) {
					String value = Arrays.toString(reqParam.getValue()).replaceAll("\\[|\\]", "").replaceAll(",\\s",
							",");
					methodParaValues[i] = value;
				}
			}
		}

		try {
			method.invoke(this.controllerMap.get(reqUrl), methodParaValues);
		} catch (Exception e) {
			logger.error("Invoking WebController has error, WebController = {}.",
					this.controllerMap.get(reqUrl).getClass().getName(), e);
			resp.getWriter().write(Response.RESP_500);
		}
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

	private void loadAllClazzFiles(String packageName) {
		URL url = this.getClass().getClassLoader()
				.getResource(File.separator + packageName.replaceAll("\\.", File.separator));
		File dir = new File(url.getFile());
		for (File file : dir.listFiles()) {
			if (file.isDirectory()) {
				loadAllClazzFiles(packageName + "." + file.getName());
			} else {
				String className = packageName + "." + file.getName().replaceFirst(".class", "");
				classNames.add(className);
			}
		}
	}

	private void loadAllClazzes() {
		if (classNames.isEmpty()) {
			return;
		}
		for (String className : classNames) {
			try {
				Class<?> clazz = Class.forName(className);
				if (clazz.isAnnotationPresent(WebController.class)) {
					logger.info("Loaded class  is {}", className);
					ioc.put(toLowerFirstWord(clazz.getSimpleName()), clazz);
				}
			} catch (ClassNotFoundException e) {
				logger.error("Initialize WebController has error, WebController = {}.", className, e);
			}
		}
	}

	private String toLowerFirstWord(String name) {
		char[] charArray = name.toCharArray();
		charArray[0] += 32;
		return String.valueOf(charArray);
	}

	private void initializeHandlerMapping() {
		if (ioc.isEmpty()) {
			return;
		}
		for (Entry<String, Class<?>> entry : ioc.entrySet()) {
			Class<? extends Object> clazz = entry.getValue();
			// Only process WebController
			if (!clazz.isAnnotationPresent(WebController.class)) {
				continue;
			}

			// Only process WebRequestMapping in class
			String baseUrl = "";
			if (clazz.isAnnotationPresent(WebRequestMapping.class)) {
				WebRequestMapping annotation = clazz.getAnnotation(WebRequestMapping.class);
				baseUrl = annotation.value();
			}

			Method[] methods = clazz.getMethods();
			for (Method method : methods) {
				// filter methods that needn't RequestMapping
				if (!method.isAnnotationPresent(WebRequestMapping.class)) {
					continue;
				}
				// Only process WebRequestMapping in method
				WebRequestMapping annotation = method.getAnnotation(WebRequestMapping.class);
				String url = annotation.value();
				url = (baseUrl + "/" + url).replaceAll("/+", "/");
				logger.info("Loaded url is {}", url);
				handlerMapping.put(url, method);
				try {
					controllerMap.put(url, clazz.newInstance());
				} catch (Exception e) {
					controllerMap.remove(url);
					handlerMapping.remove(url);
					logger.error("Instantiate web contorller has error. WebController={}", clazz.getName(), e);
				}
			}
		}
	}
}
