package com.persist.core;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.persist.core.annotation.Insert;
import com.persist.core.annotation.Param;
import com.persist.core.annotation.Query;

/**
 * To get sql statement by JDK dynamic proxy
 */
public class PersistInvocationHandler implements InvocationHandler {
	private Logger logger = LoggerFactory.getLogger(PersistInvocationHandler.class);

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		Insert insert = method.getAnnotation(Insert.class);
		if (insert != null) {
			return insert(insert, method, args);
		}

		Query query = method.getAnnotation(Query.class);
		if (query != null) {
			return insert(insert, method, args);
		}
		return null;
	}

	public int insert(Insert insert, Method method, Object[] args) {
		String insertSql = insert.value();
		logger.info("Invoke original insert sql: {}", insertSql);

		// Package parameter names and values from method to map
		Parameter[] parameters = method.getParameters();
		Map<String, Object> parameterMap = getParams(parameters, args);

		// Convert parameter values from map to list
		String[] sqlParameters = SQLUtils.sqlInsertParameter(insertSql);
		List<Object> parameterValues = new ArrayList<>();
		for (int i = 0; i < sqlParameters.length; i++) {
			String sqlParameter = sqlParameters[i];
			Object object = parameterMap.get(sqlParameter);
			parameterValues.add(object);
		}

		// Build new insert sql
		String newInsertSql = SQLUtils.paramQuestion(insertSql, sqlParameters);
		logger.info("Invoke new insert sql: {}", newInsertSql);
		int result = JDBCUtils.insert(newInsertSql, false, parameterValues);
		return result;
	}

	public List<Object> query(Query query, Method method, Object[] args) {
		String querySql = query.value();
		logger.info("Invoke original query sql: {}", querySql);

		// Package parameter names and values from method to map
		Parameter[] parameters = method.getParameters();
		Map<String, Object> parameterMap = getParams(parameters, args);

		// Convert parameter values from map to list
		List<String> sqlSelectParameter = SQLUtils.sqlQueryParameter(querySql);
		List<Object> parameValues = new ArrayList<>();
		for (int i = 0; i < sqlSelectParameter.size(); i++) {
			String parameterName = sqlSelectParameter.get(i);
			Object object = parameterMap.get(parameterName);
			parameValues.add(object.toString());
		}

		// Build new query sql
		String newQuerySql = SQLUtils.paramQuestion(querySql, sqlSelectParameter);
		logger.info("Invoke new query sql: {}", newQuerySql);

		// Package result set
		ResultSet rs = JDBCUtils.query(newQuerySql, parameValues);
		try {
			if (!rs.next()) {
				return null;
			}

			rs.previous();
			Class<?> returnType = method.getReturnType();
			List<Object> returnList = new ArrayList<>();
			while (rs.next()) {
				Object returnInstance = returnType.newInstance();
				for (String parameterName : sqlSelectParameter) {
					Object value = rs.getObject(parameterName);
					Field field = returnType.getDeclaredField(parameterName);
					if (field != null) {
						field.setAccessible(true);
						field.set(returnInstance, value);
					} else {
						throw new RuntimeException("Can't find matched field by parameterName: " + parameterName);
					}
				}
				returnList.add(returnInstance);
			}
		} catch (Exception e) {
			logger.error("Package result set has error.", e);
		}
		return null;
	}

	private Map<String, Object> getParams(Parameter[] parameters, Object[] args) {
		Map<String, Object> map = new ConcurrentHashMap<String, Object>();
		for (int i = 0; i < parameters.length; i++) {
			Parameter parameter = parameters[i];
			Param paramAnnotation = parameter.getDeclaredAnnotation(Param.class);
			String paramName = paramAnnotation.value();
			Object obj = args[i];
			map.put(paramName, obj);
		}
		return map;
	}
}
