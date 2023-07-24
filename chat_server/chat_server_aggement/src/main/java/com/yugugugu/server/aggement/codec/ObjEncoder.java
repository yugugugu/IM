package com.yugugugu.server.aggement.codec;


import com.yugugugu.server.aggement.protocol.Packet;
import com.yugugugu.server.aggement.util.SerializationUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * 对象编码器
 */
public class ObjEncoder extends MessageToByteEncoder<Packet> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Packet in, ByteBuf byteBuf) throws Exception {
        byte[] data = SerializationUtil.serialize(in);
        byteBuf.writeInt(data.length+1);//这要加1呢？？
        byteBuf.writeByte(in.getCommand());
        byteBuf.writeBytes(data);
    }
}
