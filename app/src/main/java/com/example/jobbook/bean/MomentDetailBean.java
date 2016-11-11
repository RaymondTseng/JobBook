package com.example.jobbook.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 椰树 on 2016/8/30.
 */
public class MomentDetailBean implements Serializable{
    private static final long serialVersionUID = 1L;

    private MomentBean question;

    private List<MomentCommentBean> comments;

    public MomentBean getQuestionBean() {
        return question;
    }

    public void setQuestionBean(MomentBean momentBean) {
        this.question = momentBean;
    }

    public List<MomentCommentBean> getComments() {
        return comments;
    }

    public void setComments(List<MomentCommentBean> comments) {
        this.comments = comments;
    }
}
