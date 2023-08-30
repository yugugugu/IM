package com.yugugugu.client;

import com.yugugugu.client.application.UIService;
import com.yugugugu.client.event.ChatEvent;
import com.yugugugu.client.event.LoginEvent;
import com.yugugugu.client.infrastructure.util.CacheUtil;
import com.yugugugu.client.socket.NettyClient;
import com.yugugugu.server.aggement.protocol.heart.HeartRequest;
import com.yugugugu.server.aggement.protocol.login.ReconnectRequest;
import com.yugugugu.view.chat.ChatController;
import com.yugugugu.view.chat.IChatMethod;
import com.yugugugu.view.login.ILoginMethod;
import com.yugugugu.view.login.LoginController;
import io.netty.channel.Channel;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;



@Slf4j
public class ClientApplication extends javafx.application.Application{

    //默认线程池
    private static ExecutorService executorService = Executors.newFixedThreadPool(2);
    private ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);

    @Override
    public void start(Stage primaryStage) throws Exception {
        // 1. 启动窗口
        IChatMethod chat = new ChatController(new ChatEvent());
        ILoginMethod login = new LoginController(new LoginEvent(), chat);
        login.doShow();

        UIService uiService = new UIService();
        uiService.setChat(chat);
        uiService.setLogin(login);

        // 2. 启动socket连接
        log.info("NettyClient连接服务开始 inetHost：{} inetPort：{}", "127.0.0.1", 7397);
        NettyClient nettyClient = new NettyClient(uiService);
        Future<Channel> future = executorService.submit(nettyClient);
        Channel channel = null;
        while (!future.isDone()){
            channel = future.get();
        }
        if (null == channel) throw new RuntimeException("netty client start error channel is null");

        while (!nettyClient.isActive()) {
            log.info("NettyClient启动服务 ...");
            Thread.sleep(500);
        }
        log.info("NettyClient连接服务完成 {}", channel.localAddress());

        // Channel状态定时巡检；3秒后每5秒执行一次
        scheduledExecutorService.scheduleAtFixedRate(() -> {
            while (!nettyClient.isActive()) {
                log.info("通信管道巡检：通信管道状态 " + nettyClient.isActive());
                try {
                    log.info("通信管道巡检：断线重连开始,channelId为：{}",nettyClient.channel().id().toString());
                    Channel freshChannel = executorService.submit(nettyClient).get();
                    if (null == CacheUtil.userId) continue;
                    freshChannel.writeAndFlush(new ReconnectRequest(CacheUtil.userId));
                } catch (InterruptedException | ExecutionException e) {

                    log.info("通信管道巡检：断线重连失败");
                }
            }
            Channel myChannel = nettyClient.channel();
            myChannel.writeAndFlush(new HeartRequest());
        }, 3, 5, TimeUnit.SECONDS);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
