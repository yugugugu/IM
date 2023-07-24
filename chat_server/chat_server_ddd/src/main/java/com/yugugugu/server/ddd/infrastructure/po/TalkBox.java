package com.yugugugu.server.ddd.infrastructure.po;

import lombok.Data;

import java.util.Date;

@Data
public class TalkBox {

  private long id;
  private String userId;
  private String talkId;
  private Integer talkType;
  private Date createTime;
  private Date updateTime;



}
