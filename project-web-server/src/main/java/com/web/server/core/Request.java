package com.web.server.core;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.web.server.core.enumeration.MethodType;
import com.web.server.exception.HttpProtocolParsingException;

/**
 * Created by summer.xia on 2018.12.19
 * 
 * @author summer
 */
public class Request {
	private final static Logger logger = LoggerFactory.getLogger(Request.class);
	public static final int SMALL_BUF_SIZE = 1024;

	private String[] httpProtocolLines;
	private String httpProtocol;
	private String url;
	private MethodType method;
	private Map<String, String> parameter = new HashMap<>();

	public Request(InputStream is) {
		try {
			this.httpProtocol = getHttpProtocol(is);
			parseHttpProtocol(this.httpProtocol);
		} catch (IOException e) {
			logger.error("Parse http protocol has error.", e);
		}
	}

	private String getHttpProtocol(InputStream is) throws IOException {
		String httpProtocol = null;
		byte[] buffer = new byte[SMALL_BUF_SIZE];
		int len = 0;
		if ((len = is.read(buffer)) > 0) {
			httpProtocol = new String(buffer, 0, len);
		}
		return httpProtocol;
	}

	private void parseHttpProtocol(String httpProtocol) {
		httpProtocolLines = httpProtocol.split("\\n");
		if (httpProtocol.length() > 0) {
			parseBaseProtocolLine(httpProtocolLines[0]);
		} else {
			throw new HttpProtocolParsingException("Http protocol has error.");
		}
	}

	private void parseBaseProtocolLine(String protocolLine) {
		String[] segments = protocolLine.split("\\s");

		// Parse method
		MethodType methodType = MethodType.of(segments[0]);
		if (null != methodType) {
			this.method = methodType;
		} else {
			throw new HttpProtocolParsingException("Can't recongnize method type.");
		}

		String[] items = segments[1].split("\\?");
		this.url = items[0];

		if (items.length <= 1) {
			return;
		}

		// Parse parameters
		String[] params = items[1].split("\\&");
		if (params != null && params.length > 0) {
			for (String param : params) {
				String[] pairs = param.split("\\=");
				this.parameter.put(pairs[0], pairs[1]);
			}
		}
	}

	public String getUrl() {
		return this.url;
	}

	public MethodType getMethod() {
		return this.method;
	}

	public String getHttpProtocol() {
		return httpProtocol;
	}

	public Map<String, String> getParameter() {
		return this.parameter;
	}

	@Override
	public String toString() {
		return "Request [url=" + url + ", method=" + method + "]";
	}
}