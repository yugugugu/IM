package com.yugugugu.client.socket.handler;

import com.yugugugu.client.application.UIService;
import com.yugugugu.client.socket.BaseHandler;
import com.yugugugu.server.aggement.protocol.msg.MsgGroupResponse;
import com.yugugugu.view.chat.IChatMethod;
import io.netty.channel.Channel;
import javafx.application.Platform;

public class MsgGroupHandler extends BaseHandler<MsgGroupResponse> {
    public MsgGroupHandler(UIService uiService) {
        super(uiService);
    }

    @Override
    public void channelRead(Channel channel, MsgGroupResponse msg) {
        IChatMethod chat = uiService.getChat();
        Platform.runLater(() -> {
            chat.addTalkMsgGroupLeft(msg.getTalkId(), msg.getUserId(), msg.getUserNickName(), msg.getUserHead(), msg.getMsgText(), msg.getMsgType(), msg.getMsgDate(), true, false, true);
        });
    }
}
