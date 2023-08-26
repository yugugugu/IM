package com.yugugugu.server.aggement.protocol.talk;

import com.yugugugu.server.aggement.protocol.Command;
import com.yugugugu.server.aggement.protocol.Packet;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DelTalkRequest extends Packet {
    private String userId;//用户Id
    private String talkId;//对话框

    @Override
    public Byte getCommand() {
        return Command.DelTalkRequest;
    }
}
