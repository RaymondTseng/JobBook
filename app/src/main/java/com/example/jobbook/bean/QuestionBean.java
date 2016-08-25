package com.example.jobbook.bean;

import java.util.List;

/**
 * Created by Xu on 2016/7/5.
 * 问题模型类
 */
public class QuestionBean {
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
}
