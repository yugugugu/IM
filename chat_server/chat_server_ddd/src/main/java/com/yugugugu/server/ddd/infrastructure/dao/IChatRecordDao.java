package com.yugugugu.server.ddd.infrastructure.dao;

import com.yugugugu.server.ddd.domain.user.model.ChatRecordInfo;
import com.yugugugu.server.ddd.infrastructure.po.ChatRecord;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 博  客：http://bugstack.cn
 * 公众号：bugstack虫洞栈 | 沉淀、分享、成长，让自己和他人都能有所收获！
 * create by 小傅哥 on @2020
 */
@Mapper
public interface IChatRecordDao {

    void appendChatRecord(ChatRecord req);

    List<ChatRecord> queryUserChatRecordList(String talkId, String userId);

    List<ChatRecord> queryGroupsChatRecordList(String talkId, String userId);

    void asyncAppendChatRecord(ChatRecordInfo chatRecordInfo);
}
