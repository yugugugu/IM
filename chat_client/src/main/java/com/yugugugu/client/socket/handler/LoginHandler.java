package com.yugugugu.client.socket.handler;

import com.yugugugu.client.application.UIService;
import com.yugugugu.server.aggement.protocol.login.LoginResponse;
import com.yugugugu.server.aggement.protocol.login.dto.ChatRecordDto;
import com.yugugugu.server.aggement.protocol.login.dto.ChatTalkDto;
import com.yugugugu.server.aggement.protocol.login.dto.GroupsDto;
import com.yugugugu.server.aggement.protocol.login.dto.UserFriendDto;
import com.yugugugu.view.chat.IChatMethod;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import javafx.application.Platform;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class LoginHandler extends SimpleChannelInboundHandler<LoginResponse> {

    private UIService uiService;

    public LoginHandler(UIService uiService) {
        this.uiService = uiService;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginResponse msg) throws Exception {
        log.debug("msg handler start---------");
        log.debug("发出去的消息内容为：{}",msg);
        if (!msg.isSuccess()){
            log.warn("登录失败");
            return;
        }
        Platform.runLater(()->{
            uiService.getLogin().doLoginSuccess();
            IChatMethod chat = uiService.getChat();
            chat.setUserInfo(msg.getUserId(), msg.getUserNickName(), msg.getUserHead());
            List<ChatTalkDto> chatTalkList = msg.getChatTalkList();

            //对话框
            if (chatTalkList != null){
                chatTalkList.forEach(talk -> {
                    chat.addTalkBox(0, talk.getTalkType(), talk.getTalkId(), talk.getTalkName(), talk.getTalkHead(), talk.getTalkSketch(), talk.getTalkDate(), true);
                    switch (talk.getTalkType()) {
                        // 好友
                        case 0:
                            List<ChatRecordDto> userRecordList = talk.getChatRecordList();
                            if (null == userRecordList || userRecordList.isEmpty()) return;
                            for (int i = userRecordList.size() - 1; i >= 0; i--) {
                                ChatRecordDto chatRecord = userRecordList.get(i);
                                //  自己的消息
                                if (0 == chatRecord.getMsgUserType()) {
                                    chat.addTalkMsgRight(chatRecord.getTalkId(), chatRecord.getMsgContent(), chatRecord.getMsgType(), chatRecord.getMsgDate(), true, false, false);
                                    continue;
                                }
                                // 好友的消息
                                if (1 == chatRecord.getMsgUserType()) {
                                    chat.addTalkMsgUserLeft(chatRecord.getTalkId(), chatRecord.getMsgContent(), chatRecord.getMsgType(), chatRecord.getMsgDate(), true, false, false);
                                }
                            }
                            break;
                        // 群组
                        case 1:
                            List<ChatRecordDto> groupRecordList = talk.getChatRecordList();
                            if (null == groupRecordList || groupRecordList.isEmpty()) return;
                            for (int i = groupRecordList.size() - 1; i >= 0; i--) {
                                ChatRecordDto chatRecord = groupRecordList.get(i);
                                //  自己的消息
                                if (0 == chatRecord.getMsgUserType()) {
                                    chat.addTalkMsgRight(chatRecord.getTalkId(), chatRecord.getMsgContent(), chatRecord.getMsgType(), chatRecord.getMsgDate(), true, false, false);
                                    continue;
                                }
                                // 他人的消息
                                if (1 == chatRecord.getMsgUserType()) {
                                    chat.addTalkMsgGroupLeft(chatRecord.getTalkId(), chatRecord.getUserId(), chatRecord.getUserNickName(), chatRecord.getUserHead(), chatRecord.getMsgContent(), chatRecord.getMsgType(), chatRecord.getMsgDate(), true, false, false);
                                }
                            }
                            break;
                        default:
                            break;
                    }
                });

                //群组列表
                List<GroupsDto> groupsList = msg.getGroupsList();
                if (groupsList!=null){
                    groupsList.forEach( group ->{
                        chat.addFriendGroup(group.getGroupId(), group.getGroupName(),group.getGroupHead());
                    });
                }

                //好友列表
                List<UserFriendDto> userFriendList = msg.getUserFriendList();
                userFriendList.forEach(friend ->{
                    chat.addFriendUser(false, friend.getFriendId(), friend.getFriendName(), friend.getFriendHead());
                });
            }
        });

    }
}
