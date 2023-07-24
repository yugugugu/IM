package com.yugugugu.server.ddd.infrastructure.po;


import lombok.Data;

@Data
public class ChatRecord {

  private long id;
  private String userId;
  private String friendId;
  private String msgContent;
  private java.sql.Timestamp msgDate;
  private java.sql.Timestamp createTime;
  private java.sql.Timestamp updateTime;
  private Integer talkType;
  private Integer msgType;



}
