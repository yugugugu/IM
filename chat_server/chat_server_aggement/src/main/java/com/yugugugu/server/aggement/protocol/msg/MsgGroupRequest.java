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
public class MsgGroupRequest extends Packet {
    private String talkId;   // 对话框ID
    private String userId;   // 群员ID
    private String msgText;  // 传输消息内容
    private Integer msgType;  // 消息类型；0文字消息、1固定表情
    private Date msgDate;    // 消息时间
    @Override
    public Byte getCommand() {
        return Command.MsgGroupRequest;
    }
}
