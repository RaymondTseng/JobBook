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

    void sendSuccess();

    void hideProgress();

    void showLoadFailMsg(int error);

    String getComment();

    void editTextBlankError();

    void noLoginError();

    void sendComment(String comment);

    void commentLikeSuccess(int num_like, int num_unlike);

    void commentLikeFailure(String msg);

    void commentUnlikeSuccess(int num_like, int num_unlike);

    void commentUnlikeFailure(String msg);
}
