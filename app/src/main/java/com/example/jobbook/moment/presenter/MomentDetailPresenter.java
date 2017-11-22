package com.example.jobbook.moment.presenter;

import com.example.jobbook.model.bean.MomentBean;
import com.example.jobbook.model.bean.MomentCommentBean;

/**
 * Created by 椰树 on 2016/7/16.
 */
public interface MomentDetailPresenter {
    void loadMoment(MomentBean momentBean);

    void loadMomentById(int id, String account);

    void loadMomentComments(int id, int index);

    void sendComment(MomentCommentBean momentCommentBean);

    void commentLike(int com_id, String account);

    void commentUnlike(int com_id, String account);

}
