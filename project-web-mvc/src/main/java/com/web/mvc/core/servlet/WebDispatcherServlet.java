package com.web.mvc.core.servlet;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
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

public class WebDispatcherServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getLogger(WebDispatcherServlet.class);
	private Properties properties = new Properties();
	private List<String> classNames = new ArrayList<>();
	private Map<String, Object> ioc = new ConcurrentHashMap<>();
	private String baseUrl = "";
	private Map<String, Method> handlerMapping = new ConcurrentHashMap<>();
	private Map<String, Object> controllerMap = new ConcurrentHashMap<>();

	@Override
	public void init(ServletConfig config) throws ServletException {
		logger.info("Start to web mvc.");
		// 1. load configuration files.
		loadConfiguration(config.getInitParameter("contextConfigLocation"));
		// 2. load all class files
		loadAllClazzes(properties.getProperty("scanPackage"));
		// 3. initialize all class files
		initializeWebControllers();
		// 4. map all WebRequestMapping
		initializeHandlerMapping();
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
			resp.getWriter().write("500 !! Server Exeception.");
		}
	}

	private void doDispatch(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		if (handlerMapping.isEmpty()) {
			return;
		}
		String url = req.getRequestURI();
		String contextPath = req.getContextPath();
		url = url.replace(contextPath, "").replaceAll("/+", "/");

		if (!this.handlerMapping.containsKey(url)) {
			resp.getWriter().write("404 Not Found");
			return;
		}

		Method method = this.handlerMapping.get(url);
		// Get parameter types
		Class<?>[] parameterTypes = method.getParameterTypes();
		// Get request parameters
		Map<String, String[]> parameterMap = req.getParameterMap();
		// Save parameter value
		Object[] parameterValues = new Object[parameterTypes.length];
		// method list
		for (int i = 0; i < parameterTypes.length; i++) {
			String parameterName = parameterTypes[i].getSimpleName();
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

	private void initializeWebControllers() {
		if (classNames.isEmpty()) {
			return;
		}
		for (String className : classNames) {
			try {
				Class<?> clazz = Class.forName(className);
				if (clazz.isAnnotationPresent(WebController.class)) {
					ioc.put(toLowerFirstWord(className), clazz);
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
		for (Entry<String, Object> entry : ioc.entrySet()) {
			Class<? extends Object> clazz = entry.getValue().getClass();
			if (!clazz.isAnnotationPresent(WebController.class)) {
				return;
			}

			Method[] methods = clazz.getMethods();
			for (Method method : methods) {
				// filter methods that needn't RequestMapping
				if (!method.isAnnotationPresent(WebRequestMapping.class)) {
					continue;
				}
				WebRequestMapping annotation = method.getAnnotation(WebRequestMapping.class);
				String url = annotation.value();
				url = (baseUrl + "/" + url).replaceAll("/+", "/");
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
