package com.web.server.core.enumeration;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by summer.xia on 2018.12.23
 * 
 * @author summer
 */
public enum MethodType {
	GET("get"), POST("post"), PUT("put"), DELETE("delete"), PATCH("patch");

	MethodType(String method) {
		this.method = method;
	}

	private String method;

	public String getMethod() {
		return method;
	}

	public static MethodType of(String method) {
		if (StringUtils.isNotBlank(method)) {
			for (MethodType methodType : MethodType.values()) {
				if (methodType.getMethod().equals(method.toLowerCase())) {
					return methodType;
				}
			}
		}
		return null;
	}
}
