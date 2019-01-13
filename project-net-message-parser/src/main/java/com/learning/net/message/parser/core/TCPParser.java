package com.learning.net.message.parser.core;

import com.learning.net.message.parser.bean.TCPBean;

public class TCPParser {
	private static final int DEFAULT_SPLITTER = 2;
	private static final int MIN_SPLITTER = 1;
	
	public static TCPBean parse(String tcpMessage) {
		TCPBean bean = new TCPBean();
		bean.setSourcePort(getSourcePort(tcpMessage));
		bean.setDestinationPort(getDestinationPort(tcpMessage));
		bean.setSequenceNumber(getSequenceNumber(tcpMessage));
		bean.setAckNumber(getAckNumber(tcpMessage));
		bean.setSegmentLength(getSegmentLength(tcpMessage));
		bean.setWindowSize(getWindowSize(tcpMessage));
		bean.setCheckSum(getCheckSum(tcpMessage));
		bean.setUrgentPointer(getUrgentPointer(tcpMessage));
		bean.setSegmentData(getSegmentData(tcpMessage));
		return bean;
	}
	
	public static int getSourcePort(String tcpMessage) {
		return DataConverter.convertHexToDecimal(tcpMessage.substring(0, getSourcePortLastIndex()));
	}

	public static int getDestinationPort(String tcpMessage) {
		return DataConverter.convertHexToDecimal(tcpMessage.substring(getSourcePortLastIndex(), getDestinationPortLastIndex()));
	}
	
	public static long getSequenceNumber(String tcpMessage) {
		return DataConverter.convertHexToLong(tcpMessage.substring(getDestinationPortLastIndex(), getSequenceNumberLastIndex()));
	}
	
	public static long getAckNumber(String tcpMessage) {
		return DataConverter.convertHexToLong(tcpMessage.substring(getSequenceNumberLastIndex(), getAckNumberLastIndex()));
	}
	
	public static int getSegmentLength(String tcpMessage) {
		return DataConverter.convertHexToDecimal(tcpMessage.substring(getAckNumberLastIndex(), getSegmentLengthLastIndex()));
	}
	
	public static int getWindowSize(String tcpMessage) {
		return DataConverter.convertHexToDecimal(tcpMessage.substring(getFlagsLastIndex(), getWindowSizeLastIndex()));
	}
	
	public static String getCheckSum(String tcpMessage) {
		return tcpMessage.substring(getWindowSizeLastIndex(), getCheckSumLastIndex());
	}
	
	public static int getUrgentPointer(String tcpMessage) {
		return DataConverter.convertHexToDecimal(tcpMessage.substring(getCheckSumLastIndex(), getUrgentPointerLastIndex()));
	}
	
	public static String getSegmentData(String tcpMessage) {
		return DataConverter.convertHexToString(tcpMessage.substring(getOptionsLastIndex()));
		//return tcpMessage.substring(getOptionsLastIndex());
	}
	
	private static int getSourcePortLastIndex() {
		return 2 * DEFAULT_SPLITTER;
	}

	private static int getDestinationPortLastIndex() {
		return 2 * DEFAULT_SPLITTER + getSourcePortLastIndex();
	}
	
	private static int getSequenceNumberLastIndex() {
		return 4 * DEFAULT_SPLITTER + getDestinationPortLastIndex();
	}
	
	private static int getAckNumberLastIndex() {
		return 4 * DEFAULT_SPLITTER + getSequenceNumberLastIndex();
	}
	
	private static int getSegmentLengthLastIndex() {
		return 1 * DEFAULT_SPLITTER + getAckNumberLastIndex();
	}
	
	private static int getFlagsLastIndex() {
		return 1 * DEFAULT_SPLITTER + getSegmentLengthLastIndex();
	}
	
	private static int getWindowSizeLastIndex() {
		return 2 * DEFAULT_SPLITTER + getFlagsLastIndex();
	}
	
	private static int getCheckSumLastIndex() {
		return 2 * DEFAULT_SPLITTER + getWindowSizeLastIndex();
	}
	
	private static int getUrgentPointerLastIndex() {
		return 2 * DEFAULT_SPLITTER + getCheckSumLastIndex();
	}
	
	private static int getOptionsLastIndex() {
		return 12 * DEFAULT_SPLITTER + getUrgentPointerLastIndex();
	}
}