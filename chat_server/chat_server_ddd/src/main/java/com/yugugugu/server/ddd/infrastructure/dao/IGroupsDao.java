package com.yugugugu.server.ddd.infrastructure.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yugugugu.server.ddd.infrastructure.po.Groups;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
public interface IGroupsDao extends BaseMapper<Groups> {

    Groups queryGroupsById(String talkId);
}
