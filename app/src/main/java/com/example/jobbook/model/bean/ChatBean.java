package com.example.jobbook.model.bean;

import java.io.Serializable;

/**
 * Created by Xu on 2016/7/19.
 * 聊天模型类
 */
public class ChatBean implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 发送用户
     */
    private PersonBean author_from;

    /**
     * 接收用户
     */
    private PersonBean author_to;

    /**
     * 聊天内容
     */
    private String content;

    /**
     * 发送时间
     */
    private String time;

    /**
     * 标识
     */
    private boolean isComing;

    public PersonBean getAuthor_from() {
        return author_from;
    }

    public void setAuthor_from(PersonBean author_from) {
        this.author_from = author_from;
    }

    public PersonBean getAuthor_to() {
        return author_to;
    }

    public void setAuthor_to(PersonBean author_to) {
        this.author_to = author_to;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean isComing() {
        return isComing;
    }

    public void setComing(boolean coming) {
        isComing = coming;
    }
}
