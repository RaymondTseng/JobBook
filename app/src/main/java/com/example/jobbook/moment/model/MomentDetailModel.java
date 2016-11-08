package com.example.jobbook.moment.model;

import com.example.jobbook.bean.MomentBean;
import com.example.jobbook.bean.MomentCommentBean;

/**
 * Created by 椰树 on 2016/7/16.
 */
public interface MomentDetailModel {
    void loadQuestionComments(int id, MomentDetailModelImpl.OnLoadQuestionCommentsListener mListener);

    void loadQuestion(MomentBean momentBean, MomentDetailModelImpl.OnLoadQuestionListener mListener);

    void sendComment(MomentCommentBean momentCommentBean, MomentDetailModelImpl.OnSendQuestionCommentListener mListener);

    void commentLike(int com_id, String account, MomentDetailModelImpl.OnLikeListener listener);

    void commentUnlike(int com_id, String account, MomentDetailModelImpl.OnUnlikeListener listener);

}
