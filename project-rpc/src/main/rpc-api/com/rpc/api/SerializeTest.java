package com.rpc.api;

import java.io.IOException;

import com.rpc.api.bean.Person;
import com.rpc.api.util.SerializeUtils;

public class SerializeTest {
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		Person person = new Person();
		person.setAge(20);
		person.setName("zhangsan");
		byte[] byte1 = SerializeUtils.serialize(person);
		System.out.println("JDK:" + byte1.length);
		System.out.println(SerializeUtils.deSerialize(byte1));
	}
}