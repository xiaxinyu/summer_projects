package com.rpc.client;

import com.rpc.api.SaHelloService;
import com.rpc.client.proxy.ProxyFactory;

public class Test {
	public static void main(String[] args) {
		SaHelloService saHelloService = ProxyFactory.getInstance(SaHelloService.class);
		System.out.println("Response from server: " + saHelloService.sayHello("张三"));
		System.out.println("Response from server: " + saHelloService.getPerson("张三"));
	}
}
