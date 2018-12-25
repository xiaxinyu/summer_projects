package com.web.server.exception;

public class HttpProtocolParsingException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public HttpProtocolParsingException(String message) {
		super(message);
	}
}
