package com.web.server.exception;

public class NotSupportMethodTypeException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public NotSupportMethodTypeException(String message) {
		super(message);
	}
}
