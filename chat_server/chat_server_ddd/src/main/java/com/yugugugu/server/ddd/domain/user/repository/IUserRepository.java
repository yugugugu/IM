package com.yugugugu.server.ddd.domain.user.repository;

import com.yugugugu.server.ddd.domain.user.model.*;
import com.yugugugu.server.ddd.infrastructure.po.UserFriend;

import java.util.List;

public interface IUserRepository {
    /**
     * 查询用户密码
     * @param userId
     * @return
     */
    String queryUserPassward(String userId);


    /**
     * 查询用户信息
     * @param userId
     * @return
     */
    public UserInfo queryUserInfo(String userId);

    public List<TalkBoxInfo> queryTalkBoxInfoList(String userId);

    List<String> queyUserGroupIdList(String userId);

    List<GroupsInfo> queryUserGroupInfoList(String userId);

    List<ChatRecordInfo> queryChatRecordInfoList(String talkId, String userId, Integer talkType);

    List<UserFriendInfo> queryUserFriendInfoList(String userId);

    void addUserFriend(List<UserFriend> userFriendList);

    List<LuckUserInfo> queryFuzzUserInfoList(String userId, String searchKey);
}
