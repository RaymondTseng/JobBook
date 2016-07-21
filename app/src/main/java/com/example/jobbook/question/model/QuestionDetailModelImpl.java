package com.example.jobbook.question.model;

import com.example.jobbook.bean.QuestionBean;
import com.example.jobbook.bean.QuestionCommentBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 椰树 on 2016/7/16.
 */
public class QuestionDetailModelImpl implements QuestionDetailModel{


    @Override
    public void loadQuestion(String url, OnLoadQuestionListener mListener) {
        QuestionBean mQuestion = new QuestionBean();
        mListener.onSuccess(mQuestion);
    }

    @Override
    public void loadComments(String url, OnLoadQuestionCommentsListener mListener) {
        List<QuestionCommentBean> mComments = new ArrayList<>();
        mListener.onSuccess(mComments);
    }

    @Override
    public void sendComment(String url, OnSendQuestionCommentListener mListener) {

    }

    public interface OnLoadQuestionListener{
        void onSuccess(QuestionBean mQuestion);
        void onFailure(String msg, Exception e);
    }

    public interface OnLoadQuestionCommentsListener{
        void onSuccess(List<QuestionCommentBean> mComments);
        void onFailure(String msg, Exception e);
    }

    public interface OnSendQuestionCommentListener{

    }
}
