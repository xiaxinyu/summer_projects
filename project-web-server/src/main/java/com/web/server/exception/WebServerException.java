package com.web.server.exception;

public class WebServerException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public WebServerException(String message) {
		super(message);
	}
}
