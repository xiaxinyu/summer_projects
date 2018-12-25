package com.web.server.utils;

import java.util.List;

import com.web.server.core.enumeration.HttpStatus;

public class ResponseHelper {
	private static final String RES_TPL = "HTTP/1.1 %d %s\r\n" + "Content-Type: text/html\r\n"
			+ "Content-Length: %d\r\n" + "\r\n" + "%s";

	public static String fmtResponse(HttpStatus status, String respStr) {
		String responseCode = (HttpStatus.S_200 == status ? "OK" : "");
		return String.format(RES_TPL, status.getCode(), responseCode, respStr.length(), respStr);
	}

	public static String fmtResponseText(List<String> texts) {
		StringBuilder builder = new StringBuilder();
		if (null != texts && texts.size() > 0) {
			for (String text : texts) {
				builder.append(text + "\r\n");
			}
		}
		return builder.toString();
	}
}
