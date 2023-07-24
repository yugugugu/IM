package com.yugugugu.client.socket;

import com.yugugugu.client.application.UIService;
import com.yugugugu.client.infrastructure.util.BeanUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;


import java.util.concurrent.Callable;

@Slf4j
public class NettyClient implements Callable<Channel> {

    private String INETHOST = "127.0.0.1";
    private int INETPORT = 7397;

    private EventLoopGroup worker = new NioEventLoopGroup();
    private Channel channel;

    private UIService uiService;
    public  NettyClient(UIService uiService){
        this.uiService = uiService;
    }
    @Override
    public Channel call() throws Exception {
        try {
            Bootstrap b = new Bootstrap();
            b.group(worker);
            b.channel(NioSocketChannel.class);
            b.option(ChannelOption.AUTO_READ,true);
            b.handler(new MyChannelInitializer(uiService));
            ChannelFuture channelFuture = b.connect(INETHOST, INETPORT).syncUninterruptibly();
            this.channel = channelFuture.channel();
            BeanUtil.addBean("channel", channel);
        }catch (Exception e){
            log.error("socket client start error:",e.getMessage());
        }
        return channel;
    }

    public boolean isActive() {
        return channel.isActive();
    }
    public Channel channel(){
        return channel;
    }
}
