package com.yugugugu.server.aggement.protocol.login.dto;

import lombok.Data;

/**
 * 好友信息
 */
@Data
public class UserFriendDto {

    private String friendId;    // 好友ID
    private String friendName;  // 好友名称
    private String friendHead;  // 好友头像
}
