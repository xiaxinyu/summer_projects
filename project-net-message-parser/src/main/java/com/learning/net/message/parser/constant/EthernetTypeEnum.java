package com.learning.net.message.parser.constant;

public enum EthernetTypeEnum {
	IP("0800"), ARP("0806"), UNKNOW("0000");

	EthernetTypeEnum(String code) {
		this.code = code;
	}

	private String code;

	public String getCode() {
		return code;
	}

	public static EthernetTypeEnum of(String code) {
		for (EthernetTypeEnum typeEnum : EthernetTypeEnum.values()) {
			if (typeEnum.getCode().equals(code)) {
				return typeEnum;
			}
		}
		return EthernetTypeEnum.UNKNOW;
	}
}