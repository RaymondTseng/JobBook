package com.example.jobbook.bean;

import java.io.Serializable;

/**
 * Created by 椰树 on 2016/7/16.
 * 评论模型类
 */
public class ArticleCommentBean implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 文章评论者
     */
    private PersonBean author;

    /**
     * 评论点赞数
     */
    private int favourite;

    /**
     * 评论内容
     */
    private String content;

    public PersonBean getAuthor() {
        return author;
    }

    public void setAuthor(PersonBean author) {
        this.author = author;
    }

    public int getFavourite() {
        return favourite;
    }

    public void setFavourite(int favourite) {
        this.favourite = favourite;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
