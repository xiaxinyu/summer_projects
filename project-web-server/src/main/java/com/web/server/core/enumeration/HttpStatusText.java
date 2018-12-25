package com.web.server.core.enumeration;

/**
 * Created by summer.xia on 2018.12.23
 * 
 * @author summer
 */
public enum HttpStatusText {
	TXT_200(HttpStatus.S_200, "200-OK"), 
	TXT_404(HttpStatus.S_404, "404-Not Found"), 
	TXT_400(HttpStatus.S_400, "400-Bad Request");

	HttpStatusText(HttpStatus status, String text) {
		this.status = status;
		this.text = text;
	}

	private HttpStatus status;
	private String text;

	public HttpStatus getStatus() {
		return status;
	}

	public String getText() {
		return text;
	}

	public static HttpStatusText of(HttpStatus status) {
		for (HttpStatusText text : HttpStatusText.values()) {
			if (text.getStatus() == status) {
				return text;
			}
		}
		return HttpStatusText.TXT_400;
	}
}