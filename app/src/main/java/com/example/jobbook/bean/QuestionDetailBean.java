package com.example.jobbook.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 椰树 on 2016/8/30.
 */
public class QuestionDetailBean implements Serializable{
    private static final long serialVersionUID = 1L;

    private QuestionBean question;

    private List<QuestionCommentBean> comments;

    public QuestionBean getQuestionBean() {
        return question;
    }

    public void setQuestionBean(QuestionBean questionBean) {
        this.question = questionBean;
    }

    public List<QuestionCommentBean> getComments() {
        return comments;
    }

    public void setComments(List<QuestionCommentBean> comments) {
        this.comments = comments;
    }
}
