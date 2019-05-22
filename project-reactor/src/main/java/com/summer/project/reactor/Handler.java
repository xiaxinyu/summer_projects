package com.summer.project.reactor;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

public class Handler implements Runnable {
	private static final int READING = 0, SENDING = 1;
	private static final int MAXIN = 1024;
	private static final int MAXOUT = 1024;
	private SocketChannel socket;
	private SelectionKey sk;
	private ByteBuffer input = ByteBuffer.allocate(MAXIN);
	private ByteBuffer output = ByteBuffer.allocate(MAXOUT);
	private int state = READING;

	public Handler(Selector selector, SocketChannel socketChannel) throws IOException {
		this.socket = socketChannel;

		socketChannel.configureBlocking(false);
		SelectionKey selectionKey = socketChannel.register(selector, 0);
		this.sk = selectionKey;
		selectionKey.attach(this);
		selectionKey.interestOps(SelectionKey.OP_READ);
		selector.wakeup();
	}

	@Override
	public void run() {
		try {
			if (state == READING) {
				read();
			} else if (state == SENDING) {
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
		System.out.println("I am handlering.");
	}

	private void read() throws IOException {
		socket.read(input);
		if (inputIsComplete()) {
			process();
			state = SENDING;
			// Normally also do first write now
			sk.interestOps(SelectionKey.OP_WRITE);
		}
	}

	private void send() throws IOException {
		socket.write(output);
		if (outputIsComplete()) {
			sk.cancel();
		}
	}
}