package com.yugugugu.server.aggement.protocol.login.dto;

import lombok.Data;

import java.util.Date;


@Data

/**
 * 聊天的消息
 */
public class ChatRecordDto {

    private String talkId;          // 对话框ID
    private String userId;          // 用户ID[自己/好友]
    private String userNickName;    // 用户昵称[群组聊天]
    private String userHead;        // 用户头像[群组聊天]
    private Integer msgUserType;    // 消息类型[0自己/1好友]
    private String msgContent;      // 消息内容
    private Integer msgType;        // 消息类型；0文字消息、1固定表情
    private Date msgDate;           // 消息时间

}
