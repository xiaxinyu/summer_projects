package com.rpc.server;

import com.rpc.api.SaHelloService;
import com.rpc.api.bean.Person;

public class SayHelloServiceImpl implements SaHelloService {
	public String sayHello(String name) {
		return "hello,"+name;
	}

	public Person getPerson(String name) {
		Person person = new Person();
		person.setName(name);
		person.setAge(20);
		return person;
	}
}
