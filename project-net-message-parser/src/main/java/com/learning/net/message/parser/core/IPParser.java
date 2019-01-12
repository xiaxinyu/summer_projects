package com.learning.net.message.parser.core;

import com.learning.net.message.parser.bean.IPBean;

public class IPParser {
	private static final int DEFAULT_SPLITTER = 2;
	private static final  int MIN_SPLITTER = 1;

	public static IPBean parse(String ipMessage) {
		IPBean bean = new IPBean();
		bean.setVersion(getVersion(ipMessage));
		bean.setHeaderLength(getHeadLength(ipMessage));
		bean.setDiffServiceField(getDiffServiceField(ipMessage));
		bean.setTotalLength(getTotalLength(ipMessage));
		return bean;
	}

	private static int getVersionLastIndex() {
		return 1 * MIN_SPLITTER;
	}
	
	private static int getHeaderLengthLastIndex() {
		return 1* MIN_SPLITTER + getVersionLastIndex();
	}
	
	private static int getDiffServiceFeildLastIndex() {
		return 1* DEFAULT_SPLITTER + getHeaderLengthLastIndex();
	}
	
	private static int getTotalLengthLastIndex() {
		return 2* DEFAULT_SPLITTER + getDiffServiceFeildLastIndex();
	}

	public static int getVersion(String ipMessage) {
		return DataConverter.convertHexToDecimal(ipMessage.substring(0, getVersionLastIndex()));
	}
	
	public static int getHeadLength(String ipMessage) {
		return DataConverter.convertHexToDecimal(ipMessage.substring(0, getHeaderLengthLastIndex()));
	}
	
	public static String getDiffServiceField(String ipMessage) {
		return ipMessage.substring(getHeaderLengthLastIndex(), getDiffServiceFeildLastIndex());
	}
	
	public static int getTotalLength(String ipMessage) {
		return DataConverter.convertHexToDecimal(ipMessage.substring(getDiffServiceFeildLastIndex(), getTotalLengthLastIndex()));
	}
}
