package com.yugugugu.server.aggement.protocol;

import com.yugugugu.server.aggement.protocol.friends.AddFriendRequest;
import com.yugugugu.server.aggement.protocol.friends.AddFriendResponse;
import com.yugugugu.server.aggement.protocol.friends.SearchFriendRequest;
import com.yugugugu.server.aggement.protocol.friends.SearchFriendResponse;
import com.yugugugu.server.aggement.protocol.login.LoginRequest;
import com.yugugugu.server.aggement.protocol.login.LoginResponse;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class Packet {
    private final static Map<Byte, Class<? extends Packet>> packetType = new ConcurrentHashMap<>();

    static {
        //登录事件处理
        packetType.put(Command.LoginRequest, LoginRequest.class);
        packetType.put(Command.LoginResponse, LoginResponse.class);
        //搜索与添加好友
        packetType.put(Command.AddFriendRequest, AddFriendRequest.class);
        packetType.put(Command.AddFriendResponse, AddFriendResponse.class);
        packetType.put(Command.SearchFriendRequest, SearchFriendRequest.class);
        packetType.put(Command.SearchFriendResponse, SearchFriendResponse.class);
    }

    public static Class<? extends Packet> get(Byte command) {
        return packetType.get(command);
    }

    public abstract Byte getCommand();
}
