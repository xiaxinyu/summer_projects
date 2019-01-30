package com.persist.core;

import java.lang.reflect.Proxy;

public class SqlSession {
	@SuppressWarnings("unchecked")
	public static <T> T getMapper(Class<T> clazz) {
		return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class[] { clazz }, new PersistInvocationHandler());
	}
}
