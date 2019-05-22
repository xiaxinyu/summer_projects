package com.summer.project.reactor;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * Reactor mode
 * @author XIAXINYU3
 * @date 2019.5.22
 */
public class Reactor implements Runnable {
	public final Selector selector;
	public final ServerSocketChannel serverSocketChannel;

	public Reactor(int port) throws IOException {
		this.selector = Selector.open();
		this.serverSocketChannel = ServerSocketChannel.open();
		
		InetAddress address = InetAddress.getLocalHost();
		System.out.println(address);
		this.serverSocketChannel.socket().bind(new InetSocketAddress(address, port));
		this.serverSocketChannel.configureBlocking(false);
		this.serverSocketChannel.register(this.selector, SelectionKey.OP_ACCEPT).attach(new Acceptor(this));
	}

	@Override	
	public void run() {
		try {
			while (selector.select() > 0) {
				Set<SelectionKey> selectkeys = selector.selectedKeys();
				Iterator<SelectionKey> iterator = selectkeys.iterator();
				while(iterator.hasNext()) {
					dispatch(iterator.next());
					selectkeys.clear();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void dispatch(SelectionKey selectKey) {
		Acceptor acceptor = (Acceptor)selectKey.attachment();
		if(acceptor != null) {
			acceptor.run();
		}
	}
}