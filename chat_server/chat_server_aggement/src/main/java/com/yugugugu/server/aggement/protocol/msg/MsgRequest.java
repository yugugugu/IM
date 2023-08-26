package com.yugugugu.server.aggement.protocol.msg;

import com.yugugugu.server.aggement.protocol.Command;
import com.yugugugu.server.aggement.protocol.Packet;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class MsgRequest extends Packet {
    private String userId;
    private String friendId;
    private String msgText;
    private Integer msgType;// 消息类型；0文字消息、1固定表情
    private Date msgDate;

    @Override
    public Byte getCommand() {
        return Command.MsgRequest;
    }
}
