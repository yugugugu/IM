package com.yugugugu.server.ddd.domain.user.model;

import java.util.Date;

public class TalkBoxInfo {

    private Integer talkType; // 对话框类型；0好友、1群组
    private String talkId;    // 对话框ID(好友ID、群组ID)
    private String talkName;  // 对话框名称
    private String talkHead;  // 对话框头像
    private String talkSketch;  // 消息简述
    private Date talkDate;      // 消息时间

    public Integer getTalkType() {
        return talkType;
    }

    public void setTalkType(Integer talkType) {
        this.talkType = talkType;
    }

    public String getTalkId() {
        return talkId;
    }

    public void setTalkId(String talkId) {
        this.talkId = talkId;
    }

    public String getTalkName() {
        return talkName;
    }

    public void setTalkName(String talkName) {
        this.talkName = talkName;
    }

    public String getTalkHead() {
        return talkHead;
    }

    public void setTalkHead(String talkHead) {
        this.talkHead = talkHead;
    }

    public String getTalkSketch() {
        return talkSketch;
    }

    public void setTalkSketch(String talkSketch) {
        this.talkSketch = talkSketch;
    }

    public Date getTalkDate() {
        return talkDate;
    }

    public void setTalkDate(Date talkDate) {
        this.talkDate = talkDate;
    }

}
