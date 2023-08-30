package com.yugugugu.server.aggement.protocol.heart;

import com.yugugugu.server.aggement.protocol.Command;
import com.yugugugu.server.aggement.protocol.Packet;

public class HeartRequest extends Packet {
    String Str = "hello";

    @Override
    public Byte getCommand() {
        return Command.HeartRequest;
    }
}
