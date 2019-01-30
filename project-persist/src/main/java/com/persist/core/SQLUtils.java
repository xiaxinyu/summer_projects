package com.persist.core;

import java.util.ArrayList;
import java.util.List;

public class SQLUtils {

	public static String[] sqlInsertParameter(String sql) {
		int startIndex = sql.indexOf("values");
		int endIndex = sql.length();
		String substring = sql.substring(startIndex + 6, endIndex).replace("(", "").replace(")", "").replace("#{", "")
				.replace("}", "");
		String[] params = substring.split(",");
		return params;
	}

	public static List<String> sqlQueryParameter(String sql) {
		int startIndex = sql.indexOf("where");
		int endIndex = sql.length();
		String substring = sql.substring(startIndex + 5, endIndex);
		String[] items = substring.split("and");

		List<String> list = new ArrayList<>();
		for (String item : items) {
			String[] points = item.split("=");
			list.add(points[0].trim());
		}
		return list;
	}

	public static String paramQuestion(String sql, String[] parameterNames) {
		for (int i = 0; i < parameterNames.length; i++) {
			String parameterName = parameterNames[i];
			sql = sql.replace("#{" + parameterName + "}", "?");
		}
		return sql;
	}

	public static String paramQuestion(String sql, List<String> parameterNames) {
		for (int i = 0; i < parameterNames.size(); i++) {
			String parameterName = parameterNames.get(i);
			sql = sql.replace("#{" + parameterName + "}", "?");
		}
		return sql;
	}

	/*public static void main(String[] args) {
		String sql = "insert into user(userName,userAge) values (#{userName},#{userAge})";
		String[] sqlParameter = sqlInsertParameter(sql);
		for (String string : sqlParameter) {
			System.out.println(string);
		}

		List<String> sqlSelectParameter = SQLUtils
				.sqlSelectParameter("select * from User where userName=#{userName} and userAge=#{userAge} ");
		for (String string : sqlSelectParameter) {
			System.out.println(string);
		}
	}*/
}
