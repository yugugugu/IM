package com.yugugugu.server.aggement.protocol.login;

import com.yugugugu.server.aggement.protocol.Command;
import com.yugugugu.server.aggement.protocol.Packet;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginRequest extends Packet {
    //用户账号
    private String UserId;
    //用户密码
    private String Password;

    @Override
    public Byte getCommand() {
        return Command.LoginRequest;
    }
}
