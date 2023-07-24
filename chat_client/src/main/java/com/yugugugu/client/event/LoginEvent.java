package com.yugugugu.client.event;


import com.yugugugu.client.infrastructure.util.BeanUtil;
import com.yugugugu.client.infrastructure.util.CacheUtil;
import com.yugugugu.server.aggement.protocol.login.LoginRequest;
import com.yugugugu.view.login.ILoginEvent;
import io.netty.channel.Channel;

public class LoginEvent implements ILoginEvent {
    @Override
    public void doLoginCheck(String userId, String userPassword) {
        Channel channel = BeanUtil.getBean("channel", Channel.class);
        channel.writeAndFlush(new LoginRequest(userId, userPassword));
        CacheUtil.userId = userId;
    }
}
