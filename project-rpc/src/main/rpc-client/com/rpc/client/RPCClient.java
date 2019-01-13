package com.rpc.client;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import com.rpc.api.util.SerializeUtils;

public class RPCClient {
	public static Object send(byte[] bs) {
		try {
			Socket socket = new Socket("127.0.0.1", 48615);
			OutputStream outputStream = socket.getOutputStream();
			outputStream.write(bs);
			InputStream in = socket.getInputStream();
			byte[] buf = new byte[1024];
			in.read(buf);
			Object formatDate = SerializeUtils.deSerialize(buf);
			socket.close();
			return formatDate;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}