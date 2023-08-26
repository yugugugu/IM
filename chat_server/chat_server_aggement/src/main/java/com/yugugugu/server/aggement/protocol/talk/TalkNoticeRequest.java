package com.yugugugu.server.aggement.protocol.talk;

import com.yugugugu.server.aggement.protocol.Command;
import com.yugugugu.server.aggement.protocol.Packet;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TalkNoticeRequest extends Packet {
    private String userId;//用户Id
    private String friendUserId;//好友id
    private Integer talkType;//对话框类型（0好友1群组）
    @Override
    public Byte getCommand() {
        return Command.TalkNoticeRequest;
    }
}
