package com.example.jobbook.moment.model;

import com.example.jobbook.bean.MomentBean;
import com.example.jobbook.bean.MomentCommentBean;

/**
 * Created by 椰树 on 2016/7/16.
 */
public interface MomentDetailModel {
    void loadMomentComments(int id, int index ,MomentDetailModelImpl.OnLoadMomentCommentsListener mListener);

    void loadMoment(MomentBean momentBean, MomentDetailModelImpl.OnLoadMomentListener mListener);

    void loadMomentById(String id, String account, MomentDetailModelImpl.OnLoadMomentListener mListener);

    void sendComment(MomentCommentBean momentCommentBean, MomentDetailModelImpl.OnSendMomentCommentListener mListener);

    void like(int com_id, String account, MomentDetailModelImpl.OnLikeListener listener);

    void unlike(int com_id, String account, MomentDetailModelImpl.OnUnlikeListener listener);

}
