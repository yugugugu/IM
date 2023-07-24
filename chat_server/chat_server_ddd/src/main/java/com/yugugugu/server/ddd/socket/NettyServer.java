package com.yugugugu.server.ddd.socket;


import com.yugugugu.server.ddd.application.UserService;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.Callable;

@Service("nettyServer")
@Slf4j
public class NettyServer implements Callable<Channel> {

    @Resource
    private UserService userService;

    private Channel channel;

    private final EventLoopGroup parentGroup = new NioEventLoopGroup(2);
    private final EventLoopGroup childGroup = new NioEventLoopGroup();

    @Override
    public Channel call() throws Exception {
        ChannelFuture channelFuture = null;
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(parentGroup,childGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG,128)
                    .childHandler(new MyChannelInitializer(userService));
            channelFuture = serverBootstrap.bind(7397).syncUninterruptibly();
            this.channel = (Channel) channelFuture.channel();
        }catch (Exception e){
            log.error("socket server start error",e.getMessage());
        }finally {
            if (null != channelFuture && channelFuture.isSuccess()) {
                log.info("socket server start done. ");
            } else {
                log.error("socket server start error. ");
            }
        }

        return channel;
    }

    public void destroy() {
        if (null == channel) return;
        channel.close();
        parentGroup.shutdownGracefully();
        childGroup.shutdownGracefully();
    }

    public io.netty.channel.Channel channel() {
        return channel;
    }
}
