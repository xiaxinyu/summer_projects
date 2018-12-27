package com.web.server.core;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.web.server.core.enumeration.MethodType;
import com.web.server.core.header.HeaderType;
import com.web.server.exception.HttpProtocolParsingException;
import com.web.server.utils.RequestHelper;

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
	private Map<String, String> parameters = new HashMap<>();
	private String contentType;
	private String boundary;
	private String protocolType;

	public Request(InputStream is) {
		try {
			this.httpProtocol = getHttpProtocol(is);
			logger.info(this.httpProtocol);
			parseHttpProtocol(this.httpProtocol);
			praseHttpProtocolDeeply(httpProtocolLines);
			logger.info("Request Parameters: {}", this.parameters.toString());
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
		if (StringUtils.isNotBlank(httpProtocol)) {
			httpProtocolLines = httpProtocol.split("\\r\\n");
			if (httpProtocol.length() > 0) {
				parseBaseProtocolLine(httpProtocolLines[0]);
			} else {
				throw new HttpProtocolParsingException("Http protocol has error.");
			}
		} else {
			logger.warn("Http Protocols is blank.");
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

		// Parse protocol type
		this.protocolType = segments[2];

		// Parse URL
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
				this.parameters.put(pairs[0], pairs[1]);
			}
		}
	}

	public void praseHttpProtocolDeeply(String[] httpProtocolLines) {
		if (MethodType.POST == this.method) {
			// Parse content type
			String contentType = RequestHelper.getHttpProtocolLine(HeaderType.CONTENT_TYPE, httpProtocolLines);
			String[] contentTypeItems = contentType.split("\\:");
			String[] contentItems = contentTypeItems[1].split("\\;");
			this.contentType = contentItems[0];

			this.boundary = contentItems[1].split("\\=")[1];
			if (StringUtils.isNotBlank(this.boundary)) {
				this.boundary = this.boundary.trim().replaceAll("-", "");
			}

			// Parse post parameters
			if (null != httpProtocolLines && httpProtocolLines.length > 0) {
				String parameterName = null, parameterValue = null;
				boolean flag = false;
				for (String httpProtocolLine : httpProtocolLines) {
					if (StringUtils.isBlank(httpProtocolLine)) {
						continue;
					}
					String cleanHttpProtocolLine = httpProtocolLine.trim().replaceAll("-", "");
					if (cleanHttpProtocolLine.trim().equals(this.boundary)) {
						flag = true;
						continue;
					}
					if (flag) {
						if (httpProtocolLine.contains(HeaderType.CONTENT_DISPOSITION.getName())) {
							String[] items = httpProtocolLine.split("\\=");
							parameterName = items[1].replaceAll("\"", "");
						} else {
							if (StringUtils.isNotBlank(httpProtocolLine)) {
								parameterValue = httpProtocolLine.trim();
								this.parameters.put(parameterName, parameterValue);
								flag = false;
							}
						}
					}
				}
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
		return this.parameters;
	}

	public String getContentType() {
		return contentType;
	}

	public String getBoundary() {
		return boundary;
	}

	@Override
	public String toString() {
		return "Request [url=" + url + ", method=" + method + ", contentType=" + contentType + ", protocolType="
				+ protocolType + "]";
	}
}