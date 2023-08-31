package com.yugugugu.server.ddd.socket.handler;

import com.alibaba.fastjson.JSON;
import com.yugugugu.server.aggement.protocol.msg.MsgRequest;
import com.yugugugu.server.aggement.protocol.msg.MsgResponse;
import com.yugugugu.server.aggement.protocol.talk.TalkNoticeResponse;
import com.yugugugu.server.ddd.application.UserService;
import com.yugugugu.server.ddd.domain.user.model.ChatRecordInfo;
import com.yugugugu.server.ddd.domain.user.model.UserInfo;
import com.yugugugu.server.ddd.infrastructure.common.Constants;
import com.yugugugu.server.ddd.infrastructure.common.SocketChannelUtil;
import com.yugugugu.server.ddd.socket.BaseHandler;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

@Slf4j
public class MsgHandler extends BaseHandler<MsgRequest> {
    public MsgHandler(UserService us) {
        super(us);
    }

    @Override
    public void channelRead(Channel channel, MsgRequest msg) {
        log.info("服务端接受消息：{}", JSON.toJSONString(msg));
        //对话异步写库
        userService.asyncAppendChatRecord(new ChatRecordInfo(msg.getUserId(),
                msg.getFriendId(),
                msg.getMsgText(),
                msg.getMsgType(),
                msg.getMsgDate()
                ));
        //对方没有对话框则需要添加
        userService.addTalkBoxInfo(msg.getFriendId(), msg.getUserId(), Constants.TalkType.Friend.getCode());
        // 获取好友通信管道
        Channel friendChannel = SocketChannelUtil.getChannel(msg.getFriendId());
        if (null == friendChannel) {
            log.info("用户id：{}未登录！", msg.getFriendId());
            return;
        }
        // 发送消息,需要先打开好友的对话框
        UserInfo userInfo = userService.queryUserInfo(msg.getUserId());
        friendChannel.writeAndFlush(new TalkNoticeResponse(msg.getUserId(),
                userInfo.getUserNickName(),
                userInfo.getUserHead(),
                "",
                new Date()
                ));
        friendChannel.writeAndFlush(new MsgResponse(msg.getUserId(), msg.getMsgText(), msg.getMsgType(), msg.getMsgDate()));
    }
}
