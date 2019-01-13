package com.learning.net.message.parser.core;

import com.learning.net.message.parser.bean.TCPBean;

public class TCPParser {
	private static final int DEFAULT_SPLITTER = 2;
	private static final int MIN_SPLITTER = 1;
	
	public static TCPBean parse(String tcpMessage) {
		TCPBean bean = new TCPBean();
		bean.setSourcePort(getSourcePort(tcpMessage));
		bean.setDestinationPort(getDestinationPort(tcpMessage));
		return bean;
	}
	
	public static int getSourcePort(String tcpMessage) {
		return DataConverter.convertHexToDecimal(tcpMessage.substring(0, getSourcePortLastIndex()));
	}

	public static int getDestinationPort(String tcpMessage) {
		return DataConverter.convertHexToDecimal(tcpMessage.substring(getSourcePortLastIndex(), getDestinationPortLastIndex()));
	}
	
	private static int getSourcePortLastIndex() {
		return 2 * DEFAULT_SPLITTER;
	}

	private static int getDestinationPortLastIndex() {
		return 2 * DEFAULT_SPLITTER + getSourcePortLastIndex();
	}
}