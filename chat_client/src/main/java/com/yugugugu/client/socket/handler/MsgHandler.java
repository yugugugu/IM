package com.yugugugu.client.socket.handler;

import com.yugugugu.client.application.UIService;
import com.yugugugu.server.aggement.protocol.msg.MsgResponse;
import com.yugugugu.view.chat.IChatMethod;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import javafx.application.Platform;

public class MsgHandler extends SimpleChannelInboundHandler<MsgResponse> {
    private UIService uiService;

    public MsgHandler(UIService uiService) {
        this.uiService = uiService;
    }
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, MsgResponse msg) throws Exception {
        IChatMethod chat = uiService.getChat();
        Platform.runLater(() -> {
            chat.addTalkMsgUserLeft(msg.getFriendId(), msg.getMsgText(), msg.getMsgType(), msg.getMsgDate(), true, false, true);
        });
    }
}
