package com.yugugugu.server.ddd.infrastructure.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yugugugu.server.ddd.infrastructure.po.TalkBox;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
public interface ITalkBoxDao extends BaseMapper<TalkBox> {
    List<TalkBox> queryTalkBoxList(String userId);
}
