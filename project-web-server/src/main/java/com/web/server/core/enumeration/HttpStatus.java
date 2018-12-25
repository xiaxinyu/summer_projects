package com.web.server.core.enumeration;

/**
 * Created by summer.xia on 2018.12.23
 * 
 * @author summer
 */
public enum HttpStatus {
	S_200(200), S_404(404), S_400(400);

	private Integer code;

	HttpStatus(Integer code) {
		this.code = code;
	}

	public Integer getCode() {
		return code;
	}
}
