package com.learning.net.message.parser.core;

import org.apache.commons.lang.StringUtils;

import com.learning.net.message.parser.bean.EthernetBean;
import com.learning.net.message.parser.constant.EthernetTypeEnum;

public class EthernetParser {
	private static final int DEFAULT_SPLITTER = 2;

	public static EthernetBean parse(String ethernetMessage) {
		EthernetBean bean = new EthernetBean();
		bean.setDestination(getDestination(ethernetMessage));
		bean.setSource(getSource(ethernetMessage));
		bean.setType(getType(ethernetMessage));
		bean.setIPMessage(getIPMessage(ethernetMessage));
		return bean;
	}

	public static int getDestinationLastIndex() {
		return 6 * DEFAULT_SPLITTER;
	}

	public static int getSourceLastIndex() {
		return getDestinationLastIndex() + 6 * DEFAULT_SPLITTER;
	}

	public static int getTypeLastIndex() {
		return getSourceLastIndex() + 2 * DEFAULT_SPLITTER;
	}

	public static String getDestination(String ethernetMessage) {
		String original = ethernetMessage.substring(0, getDestinationLastIndex());
		return formatMacAddress(original);
	}

	public static String getSource(String ethernetMessage) {
		String original = ethernetMessage.substring(getDestinationLastIndex(), getSourceLastIndex());
		return formatMacAddress(original);
	}

	public static EthernetTypeEnum getType(String ethernetMessage) {
		String original = ethernetMessage.substring(getSourceLastIndex(), getTypeLastIndex());
		return EthernetTypeEnum.of(original);
	}

	public static String getIPMessage(String ethernetMessage) {
		return ethernetMessage.substring(getTypeLastIndex());
	}

	private static String formatMacAddress(String original) {
		int dataItemNumber = (original.length() / DEFAULT_SPLITTER);
		String[] dataItems = new String[dataItemNumber];

		for (int i = 0; i < dataItemNumber; i++) {
			dataItems[i] = original.substring(i * DEFAULT_SPLITTER, (i + 1) * DEFAULT_SPLITTER);
		}

		return StringUtils.join(dataItems, ':');
	}
}
