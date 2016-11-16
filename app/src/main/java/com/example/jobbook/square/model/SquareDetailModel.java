package com.example.jobbook.square.model;

import com.example.jobbook.bean.MomentBean;
import com.example.jobbook.bean.MomentCommentBean;

/**
 * Created by 椰树 on 2016/7/16.
 */
public interface SquareDetailModel {
    void loadQuestionComments(int id, SquareDetailModelImpl.OnLoadQuestionCommentsListener mListener);

    void loadQuestion(MomentBean momentBean, SquareDetailModelImpl.OnLoadQuestionListener mListener);

    void sendComment(MomentCommentBean momentCommentBean, SquareDetailModelImpl.OnSendQuestionCommentListener mListener);

    void commentLike(int com_id, String account, SquareDetailModelImpl.OnLikeListener listener);

    void commentUnlike(int com_id, String account, SquareDetailModelImpl.OnUnlikeListener listener);

}
