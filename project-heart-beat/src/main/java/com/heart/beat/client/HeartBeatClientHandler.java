package com.heart.beat.client;

import com.heart.beat.core.Header;
import com.heart.beat.core.MessageType;
import com.heart.beat.core.NettyMessage;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

public class HeartBeatClientHandler extends ChannelDuplexHandler {

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		if (msg instanceof NettyMessage) {
			NettyMessage message = (NettyMessage) msg;
			if (message.getHeader() != null && message.getHeader().getType() == MessageType.HEARTBEAT_RESP.getType()) {
				System.out.println("[" + ctx.name() + "] Client receive heart beat from client. " + message.getBody());
			}
		}
	}

	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
		if (IdleStateEvent.class.isAssignableFrom(evt.getClass())) {
			IdleStateEvent event = (IdleStateEvent) evt;
			if (event.state() == IdleState.READER_IDLE) {
				System.out.println("[" + ctx.name() + "] Don't receive heart bean from server,  destroy connection.");
				ctx.disconnect();
			} else if (event.state() == IdleState.WRITER_IDLE) {
				System.out.println("[" + ctx.name() + "] Send heart bean to server.");
				ctx.writeAndFlush(buildHeartBeat(MessageType.HEARTBEAT_REQ.getType()));
			}
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}
	
	private NettyMessage buildHeartBeat(byte type) {
		NettyMessage msg = new NettyMessage();
		Header header = new Header();
		header.setType(type);
		msg.setHeader(header);
		return msg;
	}
}