package com.heart.beat.core;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Header implements Serializable{  
	/**  */
	private static final long serialVersionUID = 1L;
	private int crcCode = 0xabef0101;
	private int length;
	private long sessionId;
	private byte type;
	private byte priority;
	private Map<String, Object> attachment = new HashMap<>();

	public int getCrcCode() {
		return crcCode;
	}

	public void setCrcCode(int crcCode) {
		this.crcCode = crcCode;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public long getSessionId() {
		return sessionId;
	}

	public void setSessionId(long sessionId) {
		this.sessionId = sessionId;
	}

	public byte getType() {
		return type;
	}

	public void setType(byte type) {
		this.type = type;
	}

	public byte getPriority() {
		return priority;
	}

	public void setPriority(byte priority) {
		this.priority = priority;
	}

	public Map<String, Object> getAttachment() {
		return attachment;
	}

	public void setAttachment(Map<String, Object> attachment) {
		this.attachment = attachment;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Header [crcCode=" + crcCode + ", length=" + length + ", sessionId=" + sessionId + ", type=" + type
				+ ", priority=" + priority + ", attachment=" + attachment + "]";
	}
}
