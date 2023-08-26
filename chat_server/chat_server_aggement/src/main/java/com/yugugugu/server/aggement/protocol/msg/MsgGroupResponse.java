package com.yugugugu.server.aggement.protocol.msg;

import com.yugugugu.server.aggement.protocol.Command;
import com.yugugugu.server.aggement.protocol.Packet;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MsgGroupResponse extends Packet {
    private String talkId;      // 对话框ID
    private String userId;      // 群员用户ID
    private String userNickName;// 群员用户昵称
    private String userHead;    // 群员用户头像
    private String msgText;         // 群员用户发送消息内容
    private Integer msgType;     // 消息类型；0文字消息、1固定表情
    private Date msgDate;       // 群员用户发送消息时间

    @Override
    public Byte getCommand() {
        return Command.MsgGroupResponse;
    }
}
