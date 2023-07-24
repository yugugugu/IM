package com.yugugugu.server.ddd.infrastructure.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yugugugu.server.ddd.infrastructure.po.UserFriend;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
public interface IUserGroupDao{
    List<String> queryUserGroupsIdList(String userId);
}
