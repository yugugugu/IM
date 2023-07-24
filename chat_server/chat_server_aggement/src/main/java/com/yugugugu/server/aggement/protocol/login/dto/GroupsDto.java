package com.yugugugu.server.aggement.protocol.login.dto;

import lombok.Data;

/**
 * 返回群组信息
 */
@Data
public class GroupsDto {

    private String groupId;     // 群组ID
    private String groupName;   // 群组名称
    private String groupHead;   // 群组头像


}
