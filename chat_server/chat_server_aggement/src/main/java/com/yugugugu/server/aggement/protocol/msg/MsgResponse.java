package com.yugugugu.server.aggement.protocol.msg;

import com.yugugugu.server.aggement.protocol.Command;
import com.yugugugu.server.aggement.protocol.Packet;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class MsgResponse extends Packet {
    private String friendId;
    private String msgText;
    private Integer msgType;
    private Date msgDate;


    @Override
    public Byte getCommand() {
        return Command.MsgResponse;
    }
}
