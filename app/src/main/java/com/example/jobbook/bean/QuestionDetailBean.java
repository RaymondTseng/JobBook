package com.example.jobbook.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 椰树 on 2016/8/30.
 */
public class QuestionDetailBean implements Serializable{
    private static final long serialVersionUID = 1L;
    /**
     * id
     */
    private int id;
    /**
     * 问题作者
     */
    private PersonBean author;
    /**
     * 问题题目
     */
    private String title;
    /**
     *问题内容
     */
    private String content;
    /**
     *提出日期
     */
    private String date;
    /**
     *问题阅读量
     */
    private int readingquantity;
    /**
     *问题评论
     */
    private List<QuestionCommentBean> comments;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public PersonBean getAuthor() {
        return author;
    }

    public void setAuthor(PersonBean author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public int getReadingquantity() {
        return readingquantity;
    }

    public void setReadingquantity(int readingquantity) {
        this.readingquantity = readingquantity;
    }

    public List<QuestionCommentBean> getComments() {
        return comments;
    }

    public void setComments(List<QuestionCommentBean> comments) {
        this.comments = comments;
    }
}
