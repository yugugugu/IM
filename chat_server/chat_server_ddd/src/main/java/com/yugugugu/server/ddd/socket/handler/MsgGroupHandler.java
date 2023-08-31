package com.yugugugu.server.ddd.socket.handler;

import com.alibaba.fastjson.JSON;
import com.yugugugu.server.aggement.protocol.msg.MsgGroupRequest;
import com.yugugugu.server.aggement.protocol.msg.MsgGroupResponse;
import com.yugugugu.server.ddd.application.UserService;
import com.yugugugu.server.ddd.domain.user.model.ChatRecordInfo;
import com.yugugugu.server.ddd.domain.user.model.UserInfo;
import com.yugugugu.server.ddd.infrastructure.common.Constants;
import com.yugugugu.server.ddd.infrastructure.common.SocketChannelUtil;
import com.yugugugu.server.ddd.socket.BaseHandler;
import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

@Slf4j
public class MsgGroupHandler extends BaseHandler<MsgGroupRequest> {
    public MsgGroupHandler(UserService us) {
        super(us);
    }

    @Override
    public void channelRead(Channel channel, MsgGroupRequest msg) {
        log.info("服务端接收到群组消息：{}", JSON.toJSONString(msg));
        //群组发送消息需要
        Channel userChannel = SocketChannelUtil.getChannel(msg.getUserId());
        ChannelGroup channelGroup = SocketChannelUtil.getChannelGroup(msg.getTalkId());
        if (null == channelGroup){
            SocketChannelUtil.addChannelGroup(msg.getTalkId(),userChannel);
            channelGroup = SocketChannelUtil.getChannelGroup(msg.getTalkId());
        }
        userService.asyncAppendChatRecord(new ChatRecordInfo(msg.getUserId(),msg.getTalkId(),
                msg.getMsgText(),Constants.TalkType.Group.getCode(),msg.getMsgDate()));
        UserInfo userInfo = userService.queryUserInfo(msg.getUserId());
        MsgGroupResponse msgGroupResponse = new MsgGroupResponse();
        msgGroupResponse.setTalkId(msg.getTalkId());
        msgGroupResponse.setUserId(msg.getUserId());
        msgGroupResponse.setMsgText(msg.getMsgText());
        msgGroupResponse.setMsgDate(new Date());
        msgGroupResponse.setUserHead(userInfo.getUserHead());
        msgGroupResponse.setUserNickName(userInfo.getUserNickName());
        msgGroupResponse.setMsgType(Constants.TalkType.Group.getCode());
        msgGroupResponse.setMsgType(msg.getMsgType());
        channelGroup.writeAndFlush(msgGroupResponse);
    }
}
