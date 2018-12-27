package com.web.server.core.header;

public enum HeaderType {
	CONNECTION("Connection"), 
	CACHE_CONTROL("cache-control"), 
	USER_AGENT("User-Agent"), 
	HOST("Host"), 
	COOKIE("cookie"),
	ACCEPT_ENCODING("accept-encoding"),
	CONTENT_TYPE("content-type"),
	CONTENT_LENGTH("content-length"),
	CONTENT_DISPOSITION("Content-Disposition");

	HeaderType(String name) {
		this.name = name;
	}

	private String name;

	public String getName() {
		return this.name;
	}
}
