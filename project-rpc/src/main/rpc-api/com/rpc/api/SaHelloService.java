package com.rpc.api;

import com.rpc.api.bean.Person;

public interface SaHelloService {
	String sayHello(String name);
	Person getPerson(String name);
}
