package com.yugugugu.server.ddd.infrastructure.common.SensitiveWord;

import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;


import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SocketChannelUtil {
    //保存用户连接信息
    private static Map<String, Channel> userChannel = new ConcurrentHashMap<>();
    private static Map<String, String> userChannelId = new ConcurrentHashMap<>();
    private static Map<String, String> channelIdUser = new ConcurrentHashMap<>();

    //群组
    private static Map<String, ChannelGroup> channelGroupMap = new ConcurrentHashMap<>();

    public static void addChannel(String userId,Channel channel){
        userChannel.put(userId,channel);
        userChannelId.put(channel.id().toString(),userId);
    }

    public static void removeChannel(String channelId){
        String userId = userChannelId.get(channelId);
        if (null == userId) return;
        userChannel.remove(userId);
    }
    public static String getUserIdByChannelId(String channelId){
        return userChannelId.get(channelId);
    }

    public static void removeUserChannelByUserId(String userId){
        userChannel.remove(userId);
    }

    public static Channel getChannel(String userId) {
        return userChannel.get(userId);
    }

    /**
     * 添加群组成员通信管道
     *
     * @param talkId      对话框ID[群号]
     * @param userChannel 群员通信管道
     */
    public synchronized static void addChannelGroup(String talkId, Channel userChannel) {
        ChannelGroup channelGroup = channelGroupMap.get(talkId);
        if (null == channelGroup) {
            channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
            channelGroupMap.put(talkId, channelGroup);
        }
        channelGroup.add(userChannel);
    }

    public synchronized static void removeChannelGroup(String talkId, Channel userChannel){
        ChannelGroup channelGroup = channelGroupMap.get(talkId);
        if (null == channelGroup) return;
        channelGroup.remove(userChannel);
    }

    public static void removeChannelGroupByChannel(Channel channel){
        for (ChannelGroup next : channelGroupMap.values()) {
            next.remove(channel);
        }
    }

    /**
     * 获取群组通信管道
     *
     * @param talkId 对话框ID[群号]
     * @return 群组
     */
    public static ChannelGroup getChannelGroup(String talkId) {
        return channelGroupMap.get(talkId);
    }


}
