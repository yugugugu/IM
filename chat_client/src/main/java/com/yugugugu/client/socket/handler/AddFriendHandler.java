package com.yugugugu.client.socket.handler;

import com.yugugugu.client.application.UIService;
import com.yugugugu.client.socket.BaseHandler;
import com.yugugugu.server.aggement.protocol.friends.AddFriendResponse;
import com.yugugugu.view.chat.IChatMethod;
import io.netty.channel.Channel;
import javafx.application.Platform;

public class AddFriendHandler extends BaseHandler<AddFriendResponse> {

    public AddFriendHandler(UIService uiService) {
        super(uiService);
    }

    @Override
    public void channelRead(Channel channel, AddFriendResponse msg) {
        IChatMethod chat = uiService.getChat();
        Platform.runLater(()->{
            chat.addFriendUser(true,msg.getFriendId(),msg.getFriendNickName(), msg.getFriendHead());
        });
    }
}
