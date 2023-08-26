package com.yugugugu.client.socket.handler;

import com.yugugugu.client.application.UIService;
import com.yugugugu.server.aggement.protocol.talk.TalkNoticeResponse;
import com.yugugugu.view.chat.IChatMethod;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import javafx.application.Platform;

public class TalkNoticeHandler extends SimpleChannelInboundHandler<TalkNoticeResponse> {

    private UIService uiService;

    public TalkNoticeHandler(UIService uiService){
        this.uiService = uiService;
    }
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, TalkNoticeResponse msg) throws Exception {
        IChatMethod chat = uiService.getChat();
        Platform.runLater(() -> {
            chat.addTalkBox(-1, 0, msg.getTalkId(), msg.getTalkName(), msg.getTalkHead(), msg.getTalkSketch(), msg.getTalkDate(), false);
        });
    }
}
