package com.example.jobbook.question.model;

/**
 * Created by 椰树 on 2016/7/16.
 */
public interface QuestionDetailModel {
    void loadQuestion(String url, QuestionDetailModelImpl.OnLoadQuestionListener mListener);

    void loadComments(String url, QuestionDetailModelImpl.OnLoadQuestionCommentsListener mListener);

    void sendComment(String url, QuestionDetailModelImpl.OnSendQuestionCommentListener mListener);
}
