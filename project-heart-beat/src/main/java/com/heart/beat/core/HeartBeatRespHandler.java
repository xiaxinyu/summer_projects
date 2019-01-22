package com.heart.beat.core;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

public class HeartBeatRespHandler   extends SimpleChannelInboundHandler<NettyMessage> {  
  
    /** 
     * @see io.netty.channel.SimpleChannelInboundHandler#channelRead0(io.netty.channel.ChannelHandlerContext, 
     *      java.lang.Object) 
     */  
    @Override  
    protected void channelRead0(ChannelHandlerContext ctx, NettyMessage msg) throws Exception {  
        if (msg.getHeader() != null && msg.getHeader().getType() == MessageType.HEARTBEAT_REQ.getType()) {  
            NettyMessage heartBeat = buildHeartBeat(MessageType.HEARTBEAT_RESP.getType());  
            ctx.writeAndFlush(heartBeat);  
        } else {  
            ctx.fireChannelRead(msg);  
        }  
    }  
      
  
    /** 
     * @see io.netty.channel.ChannelInboundHandlerAdapter#userEventTriggered(io.netty.channel.ChannelHandlerContext, 
     *      java.lang.Object) 
     */  
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
      
  
    /** 
     *  
     * @return 
     * @author zhangwei<wei.zw@corp.netease.com> 
     */  
    private NettyMessage buildHeartBeat(byte type) {  
        NettyMessage msg = new NettyMessage();  
        Header header = new Header();  
        header.setType(type);  
        msg.setHeader(header);  
        return msg;  
    }  
  
}  