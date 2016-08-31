package com.example.jobbook.bean;

import java.io.Serializable;

/**
 * Created by 椰树 on 2016/7/16.
 */
public class QuestionCommentBean implements Serializable{
    private static final long serialVersionUID = 1L;
    /**
     * 评论id
     */
    private int comment_id;
    /**
     * 问问id
     */
    private int q_id;
    /**
     * 文章评论者
     */
    private PersonBean applier;

    /**
     * 评论点赞数
     */
    private int good;
    /**
     * 评论踩数
     */
    private int bad;

    /**
     * 评论日期
     */
    private String ask_time;
    /**
     * 评论内容
     */
    private String content;

    public int getComment_id() {
        return comment_id;
    }

    public void setComment_id(int comment_id) {
        this.comment_id = comment_id;
    }

    public int getQ_id() {
        return q_id;
    }

    public void setQ_id(int q_id) {
        this.q_id = q_id;
    }

    public PersonBean getApplier() {
        return applier;
    }

    public void setApplier(PersonBean applier) {
        this.applier = applier;
    }

    public int getGood() {
        return good;
    }

    public void setGood(int good) {
        this.good = good;
    }

    public int getBad() {
        return bad;
    }

    public void setBad(int bad) {
        this.bad = bad;
    }

    public String getAsk_time() {
        return ask_time;
    }

    public void setAsk_time(String ask_time) {
        this.ask_time = ask_time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
