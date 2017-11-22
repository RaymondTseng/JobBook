package com.example.jobbook.moment.view;

import com.example.jobbook.base.IBaseView;
import com.example.jobbook.model.bean.MomentBean;
import com.example.jobbook.model.bean.MomentCommentBean;

import java.util.List;

/**
 * Created by 椰树 on 2016/7/16.
 */
public interface MomentDetailView extends IBaseView {

    void addMoment(MomentBean mMoment);

    void addComments(List<MomentCommentBean> mComments);

    void sendSuccess(MomentBean momentBean);

    String getComment();

    void editTextBlankError();

    void noLoginError();

    void sendComment(String comment);

    void likeSuccess(MomentBean momentBean);

    void likeFailure(String msg);

    void unlikeSuccess(MomentBean momentBean);

    void unlikeFailure(String msg);
}
