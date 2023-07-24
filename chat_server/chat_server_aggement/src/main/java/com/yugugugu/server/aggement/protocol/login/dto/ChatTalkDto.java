package com.yugugugu.server.aggement.protocol.login.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * ChatRecordDto表示的是一条记录，这是一个对话框的聊天记录
 */
@Data
public class ChatTalkDto {

    private String talkId;      // 对话框ID
    private Integer talkType;   // 对话框类型；0好友、1群组
    private String talkName;    // 用户昵称
    private String talkHead;    // 用户头像
    private String talkSketch;  // 消息简述
    private Date talkDate;      // 消息时间

    private List<ChatRecordDto> chatRecordList;  // 聊天记录


}
