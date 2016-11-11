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
     * 文章id
     */
    private String article_id;

    /**
     * 文章类型
     */
    private int type;

    /**
     * 文章图片
     */
    private String image;

    /**
     * 文章标题
     */
    private String title;

    /**
     * 文章内容
     */
    private String content;

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

    public String getArticle_id() {
        return article_id;
    }

    public void setArticle_id(String article_id) {
        this.article_id = article_id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
