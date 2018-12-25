package com.web.server.exception;

public class NotFoundServletException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public NotFoundServletException(String message) {
		super(message);
	}
}
