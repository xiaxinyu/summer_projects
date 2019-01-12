package com.project.utils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

public class FileHelper {
	private static final String ENCODING = "UTF-8";
	public static final String LINE_BREAK = "\n";

	public static String readAsPlainText(String path) throws IOException {
		StringBuffer texts = new StringBuffer();
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(path), ENCODING));
			String text = StringUtils.EMPTY;
			while (null != (text = br.readLine()) && StringUtils.isNotBlank(text)) {
				texts.append(text).append(LINE_BREAK);
			}
		} finally {
			if (br != null) {
				br.close();
			}
		}
		return texts.toString();
	}

	public List<String> readAsLIst(String path) throws IOException {
		List<String> texts = new ArrayList<String>();
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(path), ENCODING));
			String text = StringUtils.EMPTY;
			while (null != (text = br.readLine()) && StringUtils.isNotBlank(text)) {
				texts.add(text);
			}
		} finally {
			if (br != null) {
				br.close();
			}
		}
		return texts;
	}
}