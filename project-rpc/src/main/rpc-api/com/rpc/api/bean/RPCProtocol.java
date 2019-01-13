package com.rpc.api.bean;

import java.io.Serializable;
import java.util.Arrays;

public class RPCProtocol implements Serializable {
	private static final long serialVersionUID = 1L;

	private String className;// 接口名
	private String method;// 方法名
	private Object[] args;// 参数
	private String[] types;// 参数类型

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public Object[] getArgs() {
		return args;
	}

	public void setArgs(Object[] args) {
		this.args = args;
	}

	public String[] getTypes() {
		return types;
	}

	public void setTypes(String[] types) {
		this.types = types;
	}

	@Override
	public String toString() {
		return "NetModel [className=" + className + ", method=" + method + ", args=" + Arrays.toString(args)
				+ ", types=" + Arrays.toString(types) + "]";
	}
}
