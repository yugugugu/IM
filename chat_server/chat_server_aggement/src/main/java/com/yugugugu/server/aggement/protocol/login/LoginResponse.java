package com.yugugugu.server.aggement.protocol.login;

import com.yugugugu.server.aggement.protocol.login.dto.ChatTalkDto;
import com.yugugugu.server.aggement.protocol.login.dto.GroupsDto;
import com.yugugugu.server.aggement.protocol.login.dto.UserFriendDto;
import com.yugugugu.server.aggement.protocol.Command;
import com.yugugugu.server.aggement.protocol.Packet;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class LoginResponse extends Packet {
    private boolean success;                    // 登陆反馈
    private String userId;                      // 用户ID
    private String userHead;                    // 用户头像
    private String userNickName;                // 用户昵称
    private List<ChatTalkDto> chatTalkList = new ArrayList<>();     // 聊天对话框数据[success is true]
    private List<GroupsDto> groupsList = new ArrayList<>();         // 群组列表
    private List<UserFriendDto> userFriendList = new ArrayList<>(); // 好友列表


    public LoginResponse(){}
    public LoginResponse(boolean success){
        this.success = success;
    }
    @Override
    public Byte getCommand() {
        return Command.LoginResponse;
    }
}
