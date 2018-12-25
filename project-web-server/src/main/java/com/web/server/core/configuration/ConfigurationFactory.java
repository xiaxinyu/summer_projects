package com.web.server.core.configuration;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.web.server.core.Servlet;
import com.web.server.exception.WebServerException;

public class ConfigurationFactory {
	private final static Logger logger = LoggerFactory.getLogger(ConfigurationFactory.class);
	private static Properties properties = new Properties();

	public static Map<String, Servlet> getConfiguration() {
		Map<String, Servlet> configuration = null;
		InputStream inputStream = null;
		try {
			String basePath = ConfigurationFactory.class.getResource("/").getPath();
			inputStream = new FileInputStream(basePath + "web.properties");
			properties.load(inputStream);

			Set<Object> keys = properties.keySet();
			if (keys != null && !keys.isEmpty()) {
				configuration = new ConcurrentHashMap<>();
				for (Object k : keys) {
					String key = k.toString();
					if (key.endsWith(".url")) {
						String servletName = key.replaceAll("\\.url$", "") + ".className";
						String url = properties.getProperty(key);
						String className = properties.getProperty(servletName);
						configuration.put(url, (Servlet) Class.forName(className).newInstance());
					}
				}
			}
		} catch (Exception e) {
			throw new WebServerException("Parse web configuration has error.");
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					logger.error("Startring server has error", e);
				}
			}
		}
		return configuration;
	}
}