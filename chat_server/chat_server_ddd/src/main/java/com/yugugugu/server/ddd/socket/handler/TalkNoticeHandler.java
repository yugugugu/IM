package com.yugugugu.server.ddd.socket.handler;

import com.alibaba.fastjson.JSON;
import com.yugugugu.server.aggement.protocol.talk.TalkNoticeRequest;
import com.yugugugu.server.aggement.protocol.talk.TalkNoticeResponse;
import com.yugugugu.server.ddd.application.UserService;
import com.yugugugu.server.ddd.domain.user.model.UserInfo;
import com.yugugugu.server.ddd.infrastructure.common.SocketChannelUtil;
import com.yugugugu.server.ddd.socket.BaseHandler;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

@Slf4j
public class TalkNoticeHandler extends BaseHandler<TalkNoticeRequest> {
    public TalkNoticeHandler(UserService us) {
        super(us);
    }

    @Override
    public void channelRead(Channel channel, TalkNoticeRequest msg) {
        log.info("好友对话信息：{}", JSON.toJSONString(msg));
        if (msg.getTalkType() == 0){
            //表示对话框类型为好友,好友对话框落库
            userService.addTalkBoxInfo(msg.getUserId(),msg.getFriendUserId(),0);
            userService.addTalkBoxInfo(msg.getFriendUserId(), msg.getUserId(), 0);
            UserInfo userInfo = userService.queryUserInfo(msg.getUserId());
            //创建好友的对话框
            TalkNoticeResponse response = new TalkNoticeResponse();
            response.setTalkId(userInfo.getUserId());
            response.setTalkName(userInfo.getUserNickName());
            response.setTalkHead(userInfo.getUserHead());
            response.setTalkSketch(null);
            response.setTalkDate(new Date());
            Channel friendChannel = SocketChannelUtil.getChannel(msg.getFriendUserId());
            if (null == friendChannel){
                log.info("用户{}未登录",msg.getFriendUserId());
                return;
            }
            friendChannel.writeAndFlush(response);
        }else {
            userService.addTalkBoxInfo(msg.getUserId(), msg.getFriendUserId(), 1);
        }
    }
}
