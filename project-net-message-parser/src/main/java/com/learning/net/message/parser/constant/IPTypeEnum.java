package com.learning.net.message.parser.constant;

public enum IPTypeEnum {
	TCP(6), UDP(17), ICMP(1), IGMP(2), UNKNOW(0);

	IPTypeEnum(int code) {
		this.code = code;
	}

	private int code;

	public int getCode() {
		return code;
	}

	public static IPTypeEnum of(int code) {
		for (IPTypeEnum typeEnum : IPTypeEnum.values()) {
			if (typeEnum.getCode() == code) {
				return typeEnum;
			}
		}
		return IPTypeEnum.UNKNOW;
	}
}
