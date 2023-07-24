package com.yugugugu.server.ddd.application;

import com.yugugugu.server.ddd.domain.user.model.*;
import com.yugugugu.server.ddd.infrastructure.po.UserFriend;

import java.util.List;

public interface UserService {

    /**
     * 登陆校验
     *
     * @param userId       用户ID
     * @param userPassword 用户密码
     * @return 登陆状态
     */
    boolean checkAuth(String userId, String userPassword);
    
    

    List<String> queryGroupsIdList(String userId);

    UserInfo queryUserInfo(String userId);

    List<TalkBoxInfo> queryTalkBoxInfoList(String userId);

    List<GroupsInfo> queryUserGroupInfoList(String userId);

    List<ChatRecordInfo> queryChatRecordInfoList(String talkId, String userId, Integer code);

    List<UserFriendInfo> queryUserFriendInfoList(String userId);

    void addUserFriend(List<UserFriend> userFriendList);

    List<LuckUserInfo> queryFuzzUserInfoList(String userId, String searchKey);
}
