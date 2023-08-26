package com.yugugugu.server.ddd.infrastructure.po;


import lombok.Data;

import java.util.Date;

@Data
public class ChatRecord {

  private long id;
  private String userId;
  private String friendId;
  private String msgContent;
  private Date msgDate;
  private Date createTime;
  private Date updateTime;
  private Integer talkType;
  private Integer msgType;



}
