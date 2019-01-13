package com.learning.net.message.parser.core;

import org.apache.commons.lang.StringUtils;

import com.learning.net.message.parser.bean.IPBean;
import com.learning.net.message.parser.constant.IPTypeEnum;

public class IPParser {
	private static final int DEFAULT_SPLITTER = 2;
	private static final int MIN_SPLITTER = 1;

	public static IPBean parse(String ipMessage) {
		IPBean bean = new IPBean();
		bean.setVersion(getVersion(ipMessage));
		bean.setHeaderLength(getHeadLength(ipMessage));
		bean.setDiffServiceField(getDiffServiceField(ipMessage));
		bean.setTotalLength(getTotalLength(ipMessage));
		bean.setIdentification(getIdentification(ipMessage));
		bean.setFlags(getFlags(ipMessage));
		bean.setTimeToLive(geTimeToLive(ipMessage));
		bean.setProtocol(getProtocol(ipMessage));
		bean.setHeaderCheckSum(getHeaderCheckSum(ipMessage));
		bean.setSource(getSource(ipMessage));
		bean.setDestination(getDestination(ipMessage));
		bean.setTcpMessage(getTCPMessage(ipMessage));
		return bean;
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
	
	public static String getIdentification(String ipMessage) {
		return ipMessage.substring(getTotalLengthLastIndex(), getIdentificationLastIndex());
	}

	public static String getFlags(String ipMessage) {
		return ipMessage.substring(getIdentificationLastIndex(), getFlagsLastIndex());
	}

	public static int geTimeToLive(String ipMessage) {
		return DataConverter.convertHexToDecimal(ipMessage.substring(getFlagsLastIndex(), getTimeToLiveLastIndex()));
	}

	public static IPTypeEnum getProtocol(String ipMessage) {
		int protocol = DataConverter.convertHexToDecimal(ipMessage.substring(getTimeToLiveLastIndex(), getProtocolLastIndex()));
		return IPTypeEnum.of(protocol);
	}

	public static String getHeaderCheckSum(String ipMessage) {
		return ipMessage.substring(getProtocolLastIndex(), getHeaderCheckSumLastIndex());
	}

	public static String getSource(String ipMessage) {
		String original = ipMessage.substring(getHeaderCheckSumLastIndex(), getSourceLastIndex());
		return formatIPAddress(original);
	}

	public static String getDestination(String ipMessage) {
		String original = ipMessage.substring(getSourceLastIndex(), getDestinationLastIndex());
		return formatIPAddress(original);
	}
	
	public static String getTCPMessage(String ipMessage) {
		return ipMessage.substring(getDestinationLastIndex());
	}
	
	private static String formatIPAddress(String original) {
		int dataItemNumber = (original.length() / DEFAULT_SPLITTER);
		String[] dataItems = new String[dataItemNumber];

		for (int i = 0; i < dataItemNumber; i++) {
			String item = original.substring(i * DEFAULT_SPLITTER, (i + 1) * DEFAULT_SPLITTER);
			dataItems[i] = String.valueOf(DataConverter.convertHexToDecimal(item));
		}

		return StringUtils.join(dataItems, '.');
	}

	private static int getVersionLastIndex() {
		return 1 * MIN_SPLITTER;
	}

	private static int getHeaderLengthLastIndex() {
		return 1 * MIN_SPLITTER + getVersionLastIndex();
	}

	private static int getDiffServiceFeildLastIndex() {
		return 1 * DEFAULT_SPLITTER + getHeaderLengthLastIndex();
	}

	private static int getTotalLengthLastIndex() {
		return 2 * DEFAULT_SPLITTER + getDiffServiceFeildLastIndex();
	}

	private static int getIdentificationLastIndex() {
		return 2 * DEFAULT_SPLITTER + getTotalLengthLastIndex();
	}

	private static int getFlagsLastIndex() {
		return 2 * DEFAULT_SPLITTER + getIdentificationLastIndex();
	}

	private static int getTimeToLiveLastIndex() {
		return 1 * DEFAULT_SPLITTER + getFlagsLastIndex();
	}

	private static int getProtocolLastIndex() {
		return 1 * DEFAULT_SPLITTER + getTimeToLiveLastIndex();
	}

	private static int getHeaderCheckSumLastIndex() {
		return 2 * DEFAULT_SPLITTER + getProtocolLastIndex();
	}

	private static int getSourceLastIndex() {
		return 4 * DEFAULT_SPLITTER + getHeaderCheckSumLastIndex();
	}

	private static int getDestinationLastIndex() {
		return 4 * DEFAULT_SPLITTER + getSourceLastIndex();
	}
}