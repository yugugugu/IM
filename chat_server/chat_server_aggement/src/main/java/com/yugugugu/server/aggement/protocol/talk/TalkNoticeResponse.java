package com.yugugugu.server.aggement.protocol.talk;

import com.yugugugu.server.aggement.protocol.Command;
import com.yugugugu.server.aggement.protocol.Packet;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TalkNoticeResponse extends Packet {
    private String talkId;     // 对话框ID[好友ID、群ID]
    private String talkName;   // 对话框名称[好友名称、群名称]
    private String talkHead;   // 对话框头像[好友头像、群头像]
    private String talkSketch; // 消息简讯
    private Date talkDate;     // 消息时间


    @Override
    public Byte getCommand() {
        return Command.TalkNoticeResponse;
    }
}
