package com.yugugugu.server.ddd.socket.handler;

import com.yugugugu.server.aggement.protocol.heart.HeartRequest;
import com.yugugugu.server.ddd.application.UserService;
import com.yugugugu.server.ddd.infrastructure.common.SensitiveWord.SocketChannelUtil;
import com.yugugugu.server.ddd.socket.BaseHandler;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@Slf4j
public class HeartHandler extends BaseHandler<HeartRequest> {
    private final static int MAX_NOREADTIME = 3;
    Map<String,Integer> noReadEventTimeMap = new ConcurrentHashMap<>();


    public HeartHandler(UserService us) {
        super(us);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        IdleStateEvent event =   (IdleStateEvent)evt;
        String channelId =  ctx.channel().id().toString();
        if (!noReadEventTimeMap.containsKey(channelId)) {
            noReadEventTimeMap.put(channelId,1);
        }else{
            noReadEventTimeMap.put(channelId,noReadEventTimeMap.get(channelId)+1);
        }

        if (noReadEventTimeMap.get(channelId) == MAX_NOREADTIME){
            ctx.close();
            noReadEventTimeMap.remove(channelId);
            String userId = SocketChannelUtil.getUserIdByChannelId(channelId);
            log.info("用户{}的连接异常，已经断开",userId);
        }
    }

    @Override
    public void channelRead(Channel channel, HeartRequest msg) {
    }

}
