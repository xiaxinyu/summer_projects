package com.web.server.utils;

import com.web.server.core.header.HeaderType;

public class RequestHelper {
	public static String getHttpProtocolLine(HeaderType headerType, String[] httpProtocolLines) {
		if (null != httpProtocolLines && httpProtocolLines.length > 0) {
			for (String line : httpProtocolLines) {
				if (line.contains(headerType.getName())) {
					return line;
				}
			}
		}
		return null;
	}
}
