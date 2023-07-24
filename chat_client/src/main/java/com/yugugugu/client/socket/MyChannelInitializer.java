package com.yugugugu.client.socket;

import com.yugugugu.client.application.UIService;
import com.yugugugu.client.socket.handler.AddFriendHandler;
import com.yugugugu.client.socket.handler.LoginHandler;
import com.yugugugu.client.socket.handler.SearchFriendHandler;
import com.yugugugu.server.aggement.codec.ObjDecoder;
import com.yugugugu.server.aggement.codec.ObjEncoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

import java.nio.channels.Channel;

public class MyChannelInitializer extends ChannelInitializer<SocketChannel> {
   private UIService uiService;

   public MyChannelInitializer(UIService uiService){
       this.uiService = uiService;
   }
    @Override
    protected void initChannel(SocketChannel sc) throws Exception {
        ChannelPipeline pipeline = sc.pipeline();
        pipeline.addLast(new ObjDecoder());
        //登录
        pipeline.addLast(new LoginHandler(uiService));
        //添加好友
        pipeline.addLast(new AddFriendHandler(uiService));
        pipeline.addLast(new SearchFriendHandler(uiService));

        pipeline.addLast(new ObjEncoder());
    }
}
