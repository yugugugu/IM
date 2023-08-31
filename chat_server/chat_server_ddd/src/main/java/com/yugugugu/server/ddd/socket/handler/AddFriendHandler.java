package com.yugugugu.server.ddd.socket.handler;

import com.yugugugu.server.aggement.protocol.friends.AddFriendRequest;
import com.yugugugu.server.aggement.protocol.friends.AddFriendResponse;
import com.yugugugu.server.ddd.application.UserService;
import com.yugugugu.server.ddd.domain.user.model.UserInfo;
import com.yugugugu.server.ddd.infrastructure.common.SensitiveWord.SocketChannelUtil;
import com.yugugugu.server.ddd.infrastructure.po.UserFriend;
import com.yugugugu.server.ddd.socket.BaseHandler;
import io.netty.channel.Channel;

import java.util.ArrayList;
import java.util.List;

public class AddFriendHandler extends BaseHandler<AddFriendRequest> {
    public AddFriendHandler(UserService us) {
        super(us);
    }

    @Override
    public void channelRead(Channel channel, AddFriendRequest msg) {
        //先保存好友关系到数据库中，然后同时推送给两个人
        List<UserFriend> userFriendList = new ArrayList<>();
        userFriendList.add(new UserFriend(msg.getUserId(), msg.getFriendId()));
        userFriendList.add(new UserFriend(msg.getFriendId(), msg.getUserId()));
        userService.addUserFriend(userFriendList);

        UserInfo friendInfo = userService.queryUserInfo(msg.getFriendId());
        channel.writeAndFlush(new AddFriendResponse(friendInfo.getUserId(),
                friendInfo.getUserNickName(),
                friendInfo.getUserNickName()));

        Channel friendChannel = SocketChannelUtil.getChannel(msg.getFriendId());
        if (null == friendChannel) return;
        UserInfo userInfo = userService.queryUserInfo(msg.getFriendId());
        friendChannel.writeAndFlush(new AddFriendResponse(userInfo.getUserId(),
                userInfo.getUserNickName(),
                userInfo.getUserNickName()));

    }
}
