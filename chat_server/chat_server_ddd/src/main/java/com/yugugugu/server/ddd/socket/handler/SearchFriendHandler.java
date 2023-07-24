package com.yugugugu.server.ddd.socket.handler;

import com.alibaba.fastjson.JSON;
import com.yugugugu.server.aggement.protocol.friends.SearchFriendRequest;
import com.yugugugu.server.aggement.protocol.friends.SearchFriendResponse;
import com.yugugugu.server.aggement.protocol.friends.dto.UserDto;
import com.yugugugu.server.ddd.application.UserService;
import com.yugugugu.server.ddd.domain.user.model.LuckUserInfo;
import com.yugugugu.server.ddd.socket.BaseHandler;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class SearchFriendHandler extends BaseHandler<SearchFriendRequest> {
    public SearchFriendHandler(UserService us) {
        super(us);
    }

    @Override
    public void channelRead(Channel channel, SearchFriendRequest msg) {
        log.info("搜索好友请求{}", JSON.toJSONString(msg));
        List<UserDto> userDtoList = new ArrayList<>();
        List<LuckUserInfo> userInfoList = userService.queryFuzzUserInfoList(msg.getUserId(),msg.getSearchKey());
        for (LuckUserInfo userInfo : userInfoList) {
            UserDto userDto = new UserDto();
            userDto.setUserId(userInfo.getUserId());
            userDto.setUserNickName(userInfo.getUserNickName());
            userDto.setUserHead(userInfo.getUserHead());
            userDto.setStatus(userInfo.getStatus());
            userDtoList.add(userDto);
        }
        SearchFriendResponse response = new SearchFriendResponse();
        response.setList(userDtoList);
        channel.writeAndFlush(response);
    }
}
