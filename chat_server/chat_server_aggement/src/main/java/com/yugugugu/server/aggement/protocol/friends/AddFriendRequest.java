package com.yugugugu.server.aggement.protocol.friends;

import com.yugugugu.server.aggement.protocol.Command;
import com.yugugugu.server.aggement.protocol.Packet;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddFriendRequest extends Packet {
    private String userId;    // 用户ID[自己的ID]
    private String friendId;  // 好友ID

    @Override
    public Byte getCommand() {
        return Command.AddFriendRequest;
    }
}
