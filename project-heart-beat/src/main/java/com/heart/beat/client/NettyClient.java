package com.heart.beat.client;

import java.util.concurrent.TimeUnit;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

public class NettyClient {
	public void connect(String remoteServer, int port) throws Exception {
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			Bootstrap b = new Bootstrap();
			b.group(workerGroup).channel(NioSocketChannel.class).remoteAddress(remoteServer, port)
					.handler(new ChildChannelHandler());

			ChannelFuture f = b.connect();
			System.out.println("Netty time Client connected at port " + port);

			f.channel().closeFuture().sync();
		} finally {
			try {
				TimeUnit.SECONDS.sleep(5);
				try {
					System.out.println("ÖØÐÂÁ´½Ó¡£¡£¡£");
					connect(remoteServer, port);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		try {
			new NettyClient().connect("127.0.0.1", 12000);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
