package com.heart.beat.client;

import java.util.concurrent.TimeUnit;

import com.heart.beat.core.HeartBeatReqHandler;
import com.heart.beat.core.NettyMessage;
import com.heart.beat.core.RpcDecoder;
import com.heart.beat.core.RpcEncoder;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

public class ChildChannelHandler extends ChannelInitializer<SocketChannel> {
	@Override
	protected void initChannel(final SocketChannel ch) throws Exception {
		// -8��ʾlengthAdjustment���ý�������0��ʼ��ȡ�ֽڣ����Ұ�����Ϣͷ
		ch.pipeline().addLast(new RpcEncoder(NettyMessage.class)).addLast(new RpcDecoder(NettyMessage.class))
				.addLast(new IdleStateHandler(120, 10, 0, TimeUnit.SECONDS)).addLast(new HeartBeatReqHandler());
	}
}