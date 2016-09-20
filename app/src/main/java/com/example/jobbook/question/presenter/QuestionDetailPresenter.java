package com.example.jobbook.question.presenter;

import com.example.jobbook.bean.QuestionBean;
import com.example.jobbook.bean.QuestionCommentBean;

/**
 * Created by 椰树 on 2016/7/16.
 */
public interface QuestionDetailPresenter {
    void loadQuestion(QuestionBean questionBean);

    void loadQuestionComments(int id);

    void sendComment(QuestionCommentBean questionCommentBean);

    void commentLike(int com_id, String account);

    void commentUnlike(int com_id, String account);

}
