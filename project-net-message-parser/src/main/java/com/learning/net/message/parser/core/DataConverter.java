package com.learning.net.message.parser.core;

public class DataConverter {
	public static int convertHexToDecimal(String value) {
		return Integer.valueOf(value, 16);
	}

	public static char convertHexToString(String value) {
		return (char) convertHexToDecimal(value);
	}
}