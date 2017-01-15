package com.example.jobbook.moment.presenter;

import com.example.jobbook.bean.MomentBean;
import com.example.jobbook.bean.MomentCommentBean;

/**
 * Created by 椰树 on 2016/7/16.
 */
public interface MomentDetailPresenter {
    void loadMoment(MomentBean momentBean);

    void loadMomentComments(int id);

    void sendComment(int id, MomentCommentBean momentCommentBean);

    void commentLike(int com_id, String account);

    void commentUnlike(int com_id, String account);

}
