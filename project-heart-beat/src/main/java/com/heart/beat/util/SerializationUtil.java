package com.heart.beat.util;

import java.io.IOException;

import org.msgpack.MessagePack;

public class SerializationUtil {
	public static <T> byte[] serializer(T obj) {
		MessagePack msgPack = new MessagePack();
		byte[] raw = null;
		try {
			raw = msgPack.write(obj);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return raw;
	}

	public static <T> T deserializer(byte[] raw, Class<T> clazz) {
		MessagePack msgPack = new MessagePack();
		T obj = null;
		try {
			obj = msgPack.read(raw, clazz);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return obj;
	}
}
