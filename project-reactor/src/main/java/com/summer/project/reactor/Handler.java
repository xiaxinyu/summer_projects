package com.summer.project.reactor;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
		logger.info("Handler is working, sk.valid={}", sk.isValid());
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
		logger.info("Process is working");
		byte[] data = new byte[input.remaining()];
		if (data != null && data.length > 0) {
			input.get(data, 0, data.length);
			logger.info("Receive data: {}", new String(data));
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
		output.clear();
		if (outputIsComplete()) {
			sk.cancel();
		}
	}
}