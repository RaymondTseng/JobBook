package com.example.jobbook.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Xu on 2016/7/5.
 * 文章模型类
 */
public class ArticleBean implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 文章标题
     */
    private String title;

    /**
     * 文章内容
     */
    private String content;

    /**
     * 文章作者
     */
    private PersonBean author;

    /**
     * 文章发表日期
     */
    private String date;

    /**
     * 文章阅读量
     */
    private int readingquantity;

    /**
     * 文章的评论
     */
    private List<ArticleCommentBean> comments;

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

    public PersonBean getAuthor() {
        return author;
    }

    public void setAuthor(PersonBean author) {
        this.author = author;
    }

    public int getReadingquantity() {
        return readingquantity;
    }

    public void setReadingquantity(int readingquantity) {
        this.readingquantity = readingquantity;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<ArticleCommentBean> getComments() {
        return comments;
    }

    public void setComments(List<ArticleCommentBean> comments) {
        this.comments = comments;
    }
}
