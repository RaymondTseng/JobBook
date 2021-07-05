package com.example.jobbook.model.bean;

import java.io.Serializable;

/**
 * Created by Xu on 2016/7/23.
 * 公司评论模型类
 */
public class CompanyCommentBean implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 文章评论者
     */
    private PersonBean author;

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
