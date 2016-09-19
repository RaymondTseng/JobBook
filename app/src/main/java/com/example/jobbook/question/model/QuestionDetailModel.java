package com.example.jobbook.question.model;

import com.example.jobbook.bean.QuestionBean;
import com.example.jobbook.bean.QuestionCommentBean;

/**
 * Created by 椰树 on 2016/7/16.
 */
public interface QuestionDetailModel {
    void loadQuestionComments(int id, QuestionDetailModelImpl.OnLoadQuestionCommentsListener mListener);

    void loadQuestion(QuestionBean questionBean, QuestionDetailModelImpl.OnLoadQuestionListener mListener);

    void sendComment(QuestionCommentBean questionCommentBean, QuestionDetailModelImpl.OnSendQuestionCommentListener mListener);

    void commentLike(int com_id, String account, QuestionDetailModelImpl.OnLikeListener listener);

    void commentUnlike(int com_id, String account, QuestionDetailModelImpl.OnUnlikeListener listener);

}
