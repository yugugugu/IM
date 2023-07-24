package com.yugugugu.server.ddd.infrastructure.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yugugugu.server.ddd.infrastructure.po.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IUserDao extends BaseMapper<User> {
    String queryUserPassword(String userId);

    User queryUserById(String userId);

    List<User> queryFuzzUserList(String userId, String searchKey);
}
