package com.summer.project.reactor.common;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SelectionKey;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

public class CommomUtil {
	public static String getEventName(int interestOps) {
		String result = "UNKOWN";
		switch (interestOps) {
		case SelectionKey.OP_ACCEPT:
			result = "OP_ACCEPT";
			break;
		case SelectionKey.OP_CONNECT:
			result = "OP_ACCEPT";
			break;
		case SelectionKey.OP_READ:
			result = "OP_ACCEPT";
			break;
		case SelectionKey.OP_WRITE:
			result = "OP_ACCEPT";
			break;
		default:
			break;
		}
		return result;
	}

	public static String byteBufferToString(ByteBuffer buffer) throws CharacterCodingException {
		Charset charset = Charset.forName("UTF-8");
		CharsetDecoder decoder = charset.newDecoder();
		CharBuffer charBuffer = decoder.decode(buffer);
		return charBuffer.toString();

	}
}
