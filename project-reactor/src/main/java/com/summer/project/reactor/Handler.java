package com.summer.project.reactor;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

import javax.sql.CommonDataSource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.summer.project.reactor.common.CommomUtil;

/**
 * Handler
 * 
 * @author XIAXINYU3
 * @data 2019.5.22
 */
public class Handler implements Runnable {
	private static final Logger logger = LoggerFactory.getLogger(Handler.class);
	private static final int MAXIN = 1024;
	private static final int MAXOUT = 1024;
	private SocketChannel socket;
	private SelectionKey sk;
	private Selector selector;
	private ByteBuffer input = ByteBuffer.allocate(MAXIN);
	private ByteBuffer output = ByteBuffer.allocate(MAXOUT);

	public Handler(Selector selector, SocketChannel socketChannel) throws IOException {
		logger.info("Initialize Handler");
		this.socket = socketChannel;
		this.selector = selector;

		socketChannel.configureBlocking(false);
		SelectionKey selectionKey = socketChannel.register(selector, 0);
		this.sk = selectionKey;
		selectionKey.attach(this);
		selectionKey.interestOps(SelectionKey.OP_READ);
		selector.wakeup();
	}

	@Override
	public void run() {
		logger.info("Handler is working, sk.valid={}, eventName={}", sk.isValid(), CommomUtil.getEventName(sk.interestOps()));
		try {
			if (sk.isAcceptable()) {
				logger.info("Acception ready");
			} else if (sk.isConnectable()) {
				logger.info("Connection ready");
			} else if (sk.isReadable()) {
				read();
			} else if (sk.isWritable()) {
				send();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private boolean inputIsComplete() {
		return true;
	}

	private boolean outputIsComplete() {
		return true;
	}

	private void process() {
		logger.info("Process is working, position:{} limit:{} capacity:{}", input.position(), input.limit(), input.capacity());
		byte[] data = input.array();
		if (data!=null && data.length > 0) {
			String dataStr = new String(data);
			logger.info("Receive data: [{}]", StringUtils.isNotBlank(dataStr) ? dataStr.trim() : "null");
			input.clear();
		} else {
			logger.warn("No data is received");
		}
	}

	private void read() throws IOException {
		logger.info("Reading data");
		socket.read(input);
		if (inputIsComplete()) {
			process();
			sk.interestOps(SelectionKey.OP_WRITE);
		}
	}

	private void send() throws IOException {
		logger.info("Sending data");
		output.put("Receive mesage from your side".getBytes());
		socket.write(output);
		if (outputIsComplete()) {
			sk.cancel();
		}
	}
}