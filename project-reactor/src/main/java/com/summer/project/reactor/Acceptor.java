package com.summer.project.reactor;

import java.io.IOException;
import java.nio.channels.SocketChannel;

public class Acceptor implements Runnable {
	private Reactor reactor;

	public Acceptor(Reactor reactor) {
		this.reactor = reactor;
	}

	@Override
	public void run() {
		try {
			SocketChannel socketChannel = this.reactor.serverSocketChannel.accept();
			if (socketChannel != null) {
				new Handler(this.reactor.selector, socketChannel).run();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}