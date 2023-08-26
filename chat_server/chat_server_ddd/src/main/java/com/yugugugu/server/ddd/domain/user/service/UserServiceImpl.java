package com.yugugugu.server.ddd.domain.user.service;

import com.yugugugu.server.ddd.application.UserService;
import com.yugugugu.server.ddd.domain.user.model.*;
import com.yugugugu.server.ddd.domain.user.repository.IUserRepository;
import com.yugugugu.server.ddd.infrastructure.po.TalkBox;
import com.yugugugu.server.ddd.infrastructure.po.UserFriend;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Service
public class UserServiceImpl implements UserService {
    @Resource
    private IUserRepository userRepository;

    //默认线程池
    private static ExecutorService executorService = Executors.newFixedThreadPool(4);

    @Override
    public boolean checkAuth(String userId, String userPassword) {
        String passward = userRepository.queryUserPassward(userId);
        return userPassword.equals(passward);
    }

    @Override
    public List<String> queryGroupsIdList(String userId) {
        return userRepository.queyUserGroupIdList(userId);
    }

    @Override
    public UserInfo queryUserInfo(String userId) {
        return userRepository.queryUserInfo(userId);
    }

    @Override
    public List<TalkBoxInfo> queryTalkBoxInfoList(String userId) {
        return userRepository.queryTalkBoxInfoList(userId);
    }

    @Override
    public List<GroupsInfo> queryUserGroupInfoList(String userId) {
        return userRepository.queryUserGroupInfoList(userId);
    }

    @Override
    public List<ChatRecordInfo> queryChatRecordInfoList(String talkId, String userId, Integer talkType) {
        return userRepository.queryChatRecordInfoList(talkId, userId, talkType);
    }

    @Override
    public List<UserFriendInfo> queryUserFriendInfoList(String userId) {
        return userRepository.queryUserFriendInfoList(userId);
    }

    @Override
    public void addUserFriend(List<UserFriend> userFriendList) {
        userRepository.addUserFriend(userFriendList);
    }

    @Override
    public List<LuckUserInfo> queryFuzzUserInfoList(String userId, String searchKey) {
        return userRepository.queryFuzzUserInfoList(userId,searchKey);
    }

    @Override
    public void addTalkBoxInfo(String userId, String talkId, int talkType) {
        userRepository.addTalkBoxInfo(userId, talkId, talkType);
    }

    @Override
    public void deleteUserTalk(String userId, String talkId) {
        userRepository.deleteUserTalk(userId,talkId);
    }

    @Override
    public void asyncAppendChatRecord(ChatRecordInfo chatRecordInfo) {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                userRepository.appendChatRecord(chatRecordInfo);
            }
        });
    }
}
