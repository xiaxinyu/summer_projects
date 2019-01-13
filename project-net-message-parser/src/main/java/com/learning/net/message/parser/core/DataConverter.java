package com.learning.net.message.parser.core;

public class DataConverter {
	public static int convertHexToDecimal(String value) {
		return Integer.valueOf(value, 16);
	}

	public static long convertHexToLong(String value) {
		return Long.valueOf(value, 16);
	}

	public static String convertHexToString(String s) {
		if (s == null || s.equals("")) {
			return null;
		}
		byte[] baKeyword = new byte[s.length() / 2];
		for (int i = 0; i < baKeyword.length; i++) {
			try {
				baKeyword[i] = (byte) (0xff & Integer.parseInt(s.substring(i * 2, i * 2 + 2), 16));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		try {
			s = new String(baKeyword, "UTF-8");
			new String();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return s;
	}
}