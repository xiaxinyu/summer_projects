package com.heart.beat.core;

import java.io.Serializable;

public enum MessageType implements Serializable {
	LOGIN_REQ((byte) 1), LOGIN_RESP((byte) 2), HEARTBEAT_REQ((byte) 3), HEARTBEAT_RESP((byte) 4);
	private byte type;

	/**
	 * @param type
	 */
	private MessageType(byte type) {
		this.type = type;
	}

	public byte getType() {
		return type;
	}

	public void setType(byte type) {
		this.type = type;
	}

	public static MessageType getMessageType(byte type) {
		for (MessageType b : MessageType.values()) {
			if (b.getType() == type) {
				return b;
			}
		}
		return null;
	}
}
