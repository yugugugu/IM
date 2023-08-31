package com.yugugugu.server.ddd.socket.handler;

import com.yugugugu.server.aggement.protocol.login.ReconnectRequest;
import com.yugugugu.server.ddd.application.UserService;
import com.yugugugu.server.ddd.infrastructure.common.SocketChannelUtil;
import com.yugugugu.server.ddd.socket.BaseHandler;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class ReconnectHandler extends BaseHandler<ReconnectRequest> {

    public ReconnectHandler(UserService userService) {
        super(userService);
    }

    @Override
    public void channelRead(Channel channel, ReconnectRequest msg) {
        log.info("客户端断线重连处理。userId：{}", msg.getUserId());
        // 添加用户Channel
        SocketChannelUtil.removeUserChannelByUserId(msg.getUserId());
        SocketChannelUtil.addChannel(msg.getUserId(), channel);
        // 添加群组Channel
        List<String> groupsIdList = userService.queryGroupsIdList(msg.getUserId());
        for (String groupsId : groupsIdList) {
            SocketChannelUtil.addChannelGroup(groupsId, channel);
        }
    }

}
