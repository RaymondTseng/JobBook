package com.example.jobbook.bean;

import java.io.Serializable;

/**
 * Created by Xu on 2016/7/5.
 * 问题模型类
 */

public class MomentBean implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * id
     */
    private int q_id;
    /**
     * 工作圈作者
     */
    private PersonBean author;
//    /**
//     * 工作圈题目
//     */
//    private String title;
    /**
     * 工作圈内容
     */
    private String content;
    /**
     * 提出日期
     */
    private String date;
    /**
     * 工作圈阅读量
     */
    private int commentnum;
    /**
     * 工作圈点赞量
     */
    private int favouritenum;

    private int likenum;

    public int getLikenum() {
        return likenum;
    }

    public void setLikenum(int likenum) {
        this.likenum = likenum;
    }

    public int getId() {
        return q_id;
    }

    public void setId(int id) {
        this.q_id = id;
    }

    public PersonBean getAuthor() {
        return author;
    }

    public void setAuthor(PersonBean author) {
        this.author = author;
    }

//    public String getTitle() {
//        return title;
//    }
//
//    public void setTitle(String title) {
//        this.title = title;
//    }


    public int getQ_id() {
        return q_id;
    }

    public void setQ_id(int q_id) {
        this.q_id = q_id;
    }

    public int getFavouritenum() {
        return favouritenum;
    }

    public void setFavouritenum(int favouritenum) {
        this.favouritenum = favouritenum;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getCommentnum() {
        return commentnum;
    }

    public void setCommentnum(int commentnum) {
        this.commentnum = commentnum;
    }

}
