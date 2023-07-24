package com.yugugugu.client.socket.handler;

import com.yugugugu.client.application.UIService;
import com.yugugugu.client.socket.BaseHandler;
import com.yugugugu.server.aggement.protocol.friends.SearchFriendResponse;
import com.yugugugu.server.aggement.protocol.friends.dto.UserDto;
import com.yugugugu.view.chat.IChatMethod;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import javafx.application.Platform;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class SearchFriendHandler extends SimpleChannelInboundHandler<SearchFriendResponse> {
    private UIService uiService;

    public SearchFriendHandler(UIService uiService) {
        this.uiService = uiService;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, SearchFriendResponse msg) throws Exception {
        List<UserDto> list = msg.getList();
        if (null == list || list.isEmpty()) return;
        IChatMethod chat = uiService.getChat();
        Platform.runLater(() -> {
            for (UserDto user : list) {
                chat.addLuckFriend(user.getUserId(), user.getUserNickName(), user.getUserHead(), user.getStatus());
            }
        });
    }
}
