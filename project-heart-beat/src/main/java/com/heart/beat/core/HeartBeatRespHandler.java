package com.heart.beat.core;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

public class HeartBeatRespHandler extends SimpleChannelInboundHandler<NettyMessage> {

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, NettyMessage msg) throws Exception {
		if (msg.getHeader() != null && msg.getHeader().getType() == MessageType.HEARTBEAT_REQ.getType()) {
			NettyMessage heartBeat = buildHeartBeat(MessageType.HEARTBEAT_RESP.getType());
			ctx.writeAndFlush(heartBeat);
		} else {
			ctx.fireChannelRead(msg);
		}
	}

	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
		if (IdleStateEvent.class.isAssignableFrom(evt.getClass())) {
			IdleStateEvent event = (IdleStateEvent) evt;
			if (event.state() == IdleState.READER_IDLE) {
				System.out.println("read ø’œ– πÿ±’¡¥Ω”");
				ctx.disconnect();
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