package com.heart.beat.server;

import com.heart.beat.core.Header;
import com.heart.beat.core.MessageType;
import com.heart.beat.core.NettyMessage;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

public class HeartBeatServerHandler extends SimpleChannelInboundHandler<NettyMessage> {

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, NettyMessage msg) throws Exception {
		if (msg.getHeader() != null && msg.getHeader().getType() == MessageType.HEARTBEAT_REQ.getType()) {
			System.out.println("[" + ctx.name() + "] Server receive heart beat from client. " + msg.getBody());
			NettyMessage pong = buildHeartBeat(MessageType.HEARTBEAT_RESP.getType());
			ctx.writeAndFlush(pong);
		} else {
			ctx.fireChannelRead(msg);
		}
	}

	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
		if (IdleStateEvent.class.isAssignableFrom(evt.getClass())) {
			IdleStateEvent event = (IdleStateEvent) evt;
			if (event.state() == IdleState.READER_IDLE) {
				System.out
						.println("Don't receive heart beat from client,  destory connection[name=" + ctx.name() + "]");
				ctx.disconnect();
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