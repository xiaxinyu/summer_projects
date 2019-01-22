package com.heart.beat.core;

import java.io.Serializable;

public class NettyMessage implements Serializable {
	private static final long serialVersionUID = 1L;

	private Header header;

	private Object body;

	public Header getHeader() {
		return header;
	}

	public void setHeader(Header header) {
		this.header = header;
	}

	public Object getBody() {
		return body;
	}

	public void setBody(Object body) {
		this.body = body;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "NettyMessage [header=" + header + ", body=" + body + "]";
	}
}
