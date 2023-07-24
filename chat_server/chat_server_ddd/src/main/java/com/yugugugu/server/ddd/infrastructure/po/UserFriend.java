package com.yugugugu.server.ddd.infrastructure.po;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class UserFriend {

  private long id;
  private String userId;
  private String userFriendId;
  private Date createTime;
  private Date updateTime;
  public UserFriend(String userId,String userFriendId){
    this.userId = userId;
    this.userFriendId = userFriendId;
  }


}
