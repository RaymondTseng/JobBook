package com.example.jobbook.moment.view;

import com.example.jobbook.bean.MomentBean;
import com.example.jobbook.bean.MomentCommentBean;

import java.util.List;

/**
 * Created by 椰树 on 2016/7/16.
 */
public interface MomentDetailView {

    void showProgress();

    void addMoment(MomentBean mMoment);

    void addComments(List<MomentCommentBean> mComments);

    void sendSuccess(MomentBean momentBean);

    void hideProgress();

    void showLoadFailMsg(int error);

    String getComment();

    void editTextBlankError();

    void noLoginError();

    void sendComment(String comment);

    void likeSuccess(MomentBean momentBean);

    void likeFailure(String msg);

    void unlikeSuccess(MomentBean momentBean);

    void unlikeFailure(String msg);
}
