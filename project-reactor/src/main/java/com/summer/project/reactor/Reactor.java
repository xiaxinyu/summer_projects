package com.summer.project.reactor;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Reactor mode
 * 
 * @author XIAXINYU3
 * @date 2019.5.22
 */
public class Reactor implements Runnable {
	private static final Logger logger = LoggerFactory.getLogger(Reactor.class);
	public final Selector selector;
	public final ServerSocketChannel serverSocketChannel;

	public Reactor(int port) throws IOException {
		this.selector = Selector.open();
		this.serverSocketChannel = ServerSocketChannel.open();

		InetAddress address = InetAddress.getLocalHost();
		logger.info("Listen {}:{}", address.toString(), port);
		this.serverSocketChannel.socket().bind(new InetSocketAddress(address, port));
		this.serverSocketChannel.configureBlocking(false);
		this.serverSocketChannel.register(this.selector, SelectionKey.OP_ACCEPT).attach(new Acceptor(this));
	}

	@Override
	public void run() {
		try {
			while (true) {
				logger.info("Waiting ready socket channels");
				int readyChannels = selector.select();
				logger.info("Have {} socket channels are ready", readyChannels);
				if (readyChannels == 0) {
					continue;
				}

				Set<SelectionKey> selectkeys = selector.selectedKeys();
				Iterator<SelectionKey> iterator = selectkeys.iterator();
				while (iterator.hasNext()) {
					dispatch(iterator.next());
					iterator.remove();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void dispatch(SelectionKey selectKey) {
		Runnable runnable = (Runnable) selectKey.attachment();
		if (runnable instanceof Acceptor) {
			logger.info("Acceptor gonna process");
		} else if (runnable instanceof Handler) {
			logger.info("Handler gonna process");
		}
		runnable.run();
	}
}