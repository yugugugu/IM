package com.yugugugu.server.aggement.codec;


import com.yugugugu.server.aggement.protocol.Packet;
import com.yugugugu.server.aggement.util.SerializationUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * 对象解码器
 */
public class ObjDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        //数据包由datalength(4字节)+指令（1字节）+具体数据data组成
        //先判断数据长度可不可以读出一个int类型
        if (in.readableBytes() < 4){
            //数据包有问题，长度都读不出来
            return;

        }

        in.markReaderIndex();//记录一下起始位置，后面读数据有问题需要复原buf
        int dataLen =in.readInt();
        if (in.readableBytes() < dataLen){
            in.resetReaderIndex();
            return;
        }
        byte command =in.readByte();
        byte[] data = new byte[dataLen-1];
        in.readBytes(data);
        out.add(SerializationUtil.deserialize(data, Packet.get(command)));
    }
}
