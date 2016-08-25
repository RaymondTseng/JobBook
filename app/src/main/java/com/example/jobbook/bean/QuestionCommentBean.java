package com.example.jobbook.bean;

/**
 * Created by 椰树 on 2016/7/16.
 */
public class QuestionCommentBean {
    private static final long serialVersionUID = 1L;
    /**
     * id
     */
    private int id;
    /**
     * 文章评论者
     */
    private PersonBean author;

    /**
     * 评论点赞数
     */
    private int favourite;
    /**
     * 评论踩数
     */
    private int disapproval;

    /**
     * 评论日期
     */
    private String date;
    /**
     * 评论内容
     */
    private String content;
}
