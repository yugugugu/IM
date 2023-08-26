package com.yugugugu.server.ddd.socket;

import com.yugugugu.server.aggement.codec.ObjDecoder;
import com.yugugugu.server.aggement.codec.ObjEncoder;
import com.yugugugu.server.ddd.application.UserService;
import com.yugugugu.server.ddd.socket.handler.*;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

import javax.swing.*;

public class MyChannelInitializer extends ChannelInitializer<SocketChannel> {

    private UserService userService;

    public MyChannelInitializer(UserService userService){
        this.userService = userService;
    }

    @Override
    protected void initChannel(SocketChannel sc) throws Exception {
        ChannelPipeline pipeline = sc.pipeline();
        pipeline.addLast(new ObjDecoder());//添加解码器，先把bytebuf数据解码成对象
        pipeline.addLast(new LoginHandler(userService));
        pipeline.addLast(new SearchFriendHandler(userService));
        pipeline.addLast(new AddFriendHandler(userService));
        pipeline.addLast(new DelTalkHandler(userService));
        pipeline.addLast(new TalkNoticeHandler(userService));
        pipeline.addLast(new MsgHandler(userService));
        pipeline.addLast(new MsgGroupHandler(userService));
        pipeline.addLast(new ObjEncoder());//处理完之后把数据对象转为二进制字节发出去
    }
}
