package com.yugugugu.server.ddd.socket.handler;

import com.alibaba.fastjson.JSON;
import com.yugugugu.server.aggement.protocol.login.LoginResponse;
import com.yugugugu.server.aggement.protocol.login.dto.ChatRecordDto;
import com.yugugugu.server.aggement.protocol.login.dto.ChatTalkDto;
import com.yugugugu.server.aggement.protocol.login.dto.GroupsDto;
import com.yugugugu.server.aggement.protocol.login.dto.UserFriendDto;
import com.yugugugu.server.ddd.application.UserService;
import com.yugugugu.server.aggement.protocol.login.LoginRequest;
import com.yugugugu.server.ddd.domain.user.model.*;
import com.yugugugu.server.ddd.infrastructure.common.Constants;
import com.yugugugu.server.ddd.infrastructure.common.SensitiveWord.SocketChannelUtil;
import com.yugugugu.server.ddd.socket.BaseHandler;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class LoginHandler extends BaseHandler<LoginRequest> {
    public LoginHandler(UserService us) {
        super(us);
    }

    @Override
    public void channelRead(Channel channel, LoginRequest msg) {
        log.info("登陆请求处理：{} ", JSON.toJSONString(msg));
        boolean auth = userService.checkAuth(msg.getUserId(),msg.getPassword());
        //登录失败
        if (!auth){
            channel.writeAndFlush(new LoginResponse(false));
            return;
        }

        //登录成功
        SocketChannelUtil.addChannel(msg.getUserId(), channel);
        //绑定群组
        List<String> groupIdList = userService.queryGroupsIdList(msg.getUserId());
        for(String groupId:groupIdList){
            SocketChannelUtil.addChannelGroup(groupId,channel);
        }

        //把用户的信息返回回去：用户个人信息，群组列表，好友列表，对话框列表
        LoginResponse response = new LoginResponse();
        //用户信息
        UserInfo userInfo = userService.queryUserInfo(msg.getUserId());
        //对话框
        List<TalkBoxInfo> talkBoxInfoList =  userService.queryTalkBoxInfoList(msg.getUserId());
        for (TalkBoxInfo talkBoxInfo : talkBoxInfoList) {
            ChatTalkDto chatTalk = new ChatTalkDto();
            chatTalk.setTalkId(talkBoxInfo.getTalkId());
            chatTalk.setTalkType(talkBoxInfo.getTalkType());
            chatTalk.setTalkName(talkBoxInfo.getTalkName());
            chatTalk.setTalkHead(talkBoxInfo.getTalkHead());
            chatTalk.setTalkSketch(talkBoxInfo.getTalkSketch());
            chatTalk.setTalkDate(talkBoxInfo.getTalkDate());
            response.getChatTalkList().add(chatTalk);

            // 好友；聊天消息
            if (Constants.TalkType.Friend.getCode().equals(talkBoxInfo.getTalkType())) {
                List<ChatRecordDto> chatRecordDtoList = new ArrayList<>();
                List<ChatRecordInfo> chatRecordInfoList = userService.queryChatRecordInfoList(talkBoxInfo.getTalkId(), msg.getUserId(), Constants.TalkType.Friend.getCode());
                for (ChatRecordInfo chatRecordInfo : chatRecordInfoList) {
                    ChatRecordDto chatRecordDto = new ChatRecordDto();
                    chatRecordDto.setTalkId(talkBoxInfo.getTalkId());
                    boolean msgType = msg.getUserId().equals(chatRecordInfo.getUserId());
                    // 自己发的消息
                    if (msgType) {
                        chatRecordDto.setUserId(chatRecordInfo.getUserId());
                        chatRecordDto.setMsgUserType(0); // 消息类型[0自己/1好友]
                    }
                    // 好友发的消息
                    else {
                        chatRecordDto.setUserId(chatRecordInfo.getFriendId());
                        chatRecordDto.setMsgUserType(1); // 消息类型[0自己/1好友]
                    }
                    chatRecordDto.setMsgContent(chatRecordInfo.getMsgContent());
                    chatRecordDto.setMsgType(chatRecordInfo.getMsgType());
                    chatRecordDto.setMsgDate(chatRecordInfo.getMsgDate());
                    chatRecordDtoList.add(chatRecordDto);
                }
                chatTalk.setChatRecordList(chatRecordDtoList);
            }
            // 群组；聊天消息
            else if (Constants.TalkType.Group.getCode().equals(talkBoxInfo.getTalkType())) {
                List<ChatRecordDto> chatRecordDtoList = new ArrayList<>();
                List<ChatRecordInfo> chatRecordInfoList = userService.queryChatRecordInfoList(talkBoxInfo.getTalkId(), msg.getUserId(), Constants.TalkType.Group.getCode());
                for (ChatRecordInfo chatRecordInfo : chatRecordInfoList) {
                    UserInfo memberInfo = userService.queryUserInfo(chatRecordInfo.getUserId());
                    ChatRecordDto chatRecordDto = new ChatRecordDto();
                    chatRecordDto.setTalkId(talkBoxInfo.getTalkId());
                    chatRecordDto.setUserId(memberInfo.getUserId());
                    chatRecordDto.setUserNickName(memberInfo.getUserNickName());
                    chatRecordDto.setUserHead(memberInfo.getUserHead());
                    chatRecordDto.setMsgContent(chatRecordInfo.getMsgContent());
                    chatRecordDto.setMsgDate(chatRecordInfo.getMsgDate());
                    boolean msgType = msg.getUserId().equals(chatRecordInfo.getUserId());
                    chatRecordDto.setMsgUserType(msgType ? 0 : 1); // 消息类型[0自己/1好友]
                    chatRecordDto.setMsgType(chatRecordInfo.getMsgType());
                    chatRecordDtoList.add(chatRecordDto);
                }
                chatTalk.setChatRecordList(chatRecordDtoList);
            }

        }
        // 3.3 群组
        List<GroupsInfo> groupsInfoList = userService.queryUserGroupInfoList(msg.getUserId());
        for (GroupsInfo groupsInfo : groupsInfoList) {
            GroupsDto groups = new GroupsDto();
            groups.setGroupId(groupsInfo.getGroupId());
            groups.setGroupName(groupsInfo.getGroupName());
            groups.setGroupHead(groupsInfo.getGroupHead());
            response.getGroupsList().add(groups);
        }
        // 3.4 好友
        List<UserFriendInfo> userFriendInfoList = userService.queryUserFriendInfoList(msg.getUserId());
        for (UserFriendInfo userFriendInfo : userFriendInfoList) {
            UserFriendDto userFriend = new UserFriendDto();
            BeanUtils.copyProperties(userFriendInfo,userFriend);
            response.getUserFriendList().add(userFriend);
        }

        response.setSuccess(true);
        response.setUserId(userInfo.getUserId());
        response.setUserNickName(userInfo.getUserNickName());
        response.setUserHead(userInfo.getUserHead());
        // 传输消息
        channel.writeAndFlush(response);

    }
}
