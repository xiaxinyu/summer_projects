package com.heart.beat.core;

import com.heart.beat.util.SerializationUtil;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

@SuppressWarnings("rawtypes")
public class RPCEncoder extends MessageToByteEncoder {  
  
    private Class<?> genericClass;  
  
    public RPCEncoder(Class<?> genericClass) {  
        this.genericClass = genericClass;  
    }  
  
    @Override  
    public void encode(ChannelHandlerContext ctx, Object in, ByteBuf out) throws Exception {  
        if (genericClass.isInstance(in)) {  
            System.out.println("发送的请求是："+in);  
            byte[] data = SerializationUtil.serializer(in);  
            out.writeInt(data.length);  
            out.writeBytes(data);  
        }  
    }  
}