package com.example.jobbook.moment.model;

import com.example.jobbook.bean.MomentBean;
import com.example.jobbook.bean.MomentCommentBean;

/**
 * Created by 椰树 on 2016/7/16.
 */
public interface MomentDetailModel {
    void loadMomentComments(int id, MomentDetailModelImpl.OnLoadMomentCommentsListener mListener);

    void loadMoment(MomentBean momentBean, MomentDetailModelImpl.OnLoadMomentListener mListener);

    void sendComment(int id, MomentCommentBean momentCommentBean, MomentDetailModelImpl.OnSendMomentCommentListener mListener);

    void commentLike(int com_id, String account, MomentDetailModelImpl.OnLikeListener listener);

    void commentUnlike(int com_id, String account, MomentDetailModelImpl.OnUnlikeListener listener);

}
