package com.web.server.utils;

public class RequestHelper {
	/**
	 * get uri in http request line to client, that is uri after the blank of method
	 * and before the blank of protocol/version<br>
	 * eg: for a http get request, the request line maybe: GET /user?name=jxshen
	 * HTTP/1.1<br>
	 * then the function return "user"<br>
	 * 
	 */
	public static String parse(String source) {
		if (source == null || source.length() == 0) {
			return new String();
		}

		int startIndex;
		startIndex = source.indexOf(' ');
		if (startIndex != -1) {
			int paramIndex = source.indexOf('?', startIndex + 1);
			int secondBlankIndex = source.indexOf(' ', startIndex + 1);
			int endIndex = -1;
			if (secondBlankIndex > paramIndex) {
				endIndex = secondBlankIndex;
			} else {
				endIndex = paramIndex;
			}
			if (endIndex > startIndex)
				return source.substring(startIndex + 1, endIndex);
		}
		return new String();
	}
}
