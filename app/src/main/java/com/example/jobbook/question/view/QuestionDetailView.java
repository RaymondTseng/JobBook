package com.example.jobbook.question.view;

import com.example.jobbook.bean.QuestionBean;
import com.example.jobbook.bean.QuestionCommentBean;

import java.util.List;

/**
 * Created by 椰树 on 2016/7/16.
 */
public interface QuestionDetailView {

    void showProgress();

    void addQuestion(QuestionBean mQuestion);

    void addComments(List<QuestionCommentBean> mComments);

    void hideProgress();

    void showLoadFailMsg();

    String getComment();
}
