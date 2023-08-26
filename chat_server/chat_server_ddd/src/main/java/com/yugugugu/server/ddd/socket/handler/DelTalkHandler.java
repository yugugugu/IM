package com.yugugugu.server.ddd.socket.handler;

import com.yugugugu.server.aggement.protocol.talk.DelTalkRequest;
import com.yugugugu.server.ddd.application.UserService;
import com.yugugugu.server.ddd.socket.BaseHandler;
import io.netty.channel.Channel;

public class DelTalkHandler extends BaseHandler<DelTalkRequest> {
    public DelTalkHandler(UserService userService) {
        super(userService);
    }

    @Override
    public void channelRead(Channel channel, DelTalkRequest msg) {
        userService.deleteUserTalk(msg.getUserId(), msg.getTalkId());
    }
}
