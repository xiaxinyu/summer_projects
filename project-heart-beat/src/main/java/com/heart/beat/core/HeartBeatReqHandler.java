package com.heart.beat.core;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

public class HeartBeatReqHandler extends ChannelDuplexHandler {

	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
		if (IdleStateEvent.class.isAssignableFrom(evt.getClass())) {
			IdleStateEvent event = (IdleStateEvent) evt;
			if (event.state() == IdleState.READER_IDLE) {
				System.out.println("read ø’œ–");
				ctx.disconnect();
			} else if (event.state() == IdleState.WRITER_IDLE) {
				System.out.println("write ø’œ–");
				ctx.writeAndFlush(buildHeartBeat(MessageType.HEARTBEAT_REQ.getType()));
			}
		}
	}

	private NettyMessage buildHeartBeat(byte type) {
		NettyMessage msg = new NettyMessage();
		Header header = new Header();
		header.setType(type);
		msg.setHeader(header);
		return msg;
	}
}