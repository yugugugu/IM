package com.yugugugu.server.ddd.infrastructure.po;


import lombok.Data;

import java.util.Date;

@Data
public class User {

  private String id;
  private String userId;
  private String userNickName;
  private String userHead;
  private String userPassword;
  private Date createTime;
  private Date updateTime;



}
