package com.example.jobbook.square.view;

import com.example.jobbook.bean.MomentBean;
import com.example.jobbook.bean.MomentCommentBean;

import java.util.List;

/**
 * Created by 椰树 on 2016/7/16.
 */
public interface SquareDetailView {

    void showProgress();

    void addQuestion(MomentBean mQuestion);

    void addComments(List<MomentCommentBean> mComments);

    void sendSuccess();

    void hideProgress();

    void showLoadFailMsg(int error);

    String getComment();

    void editTextBlankError();

    void noLoginError();

    void sendComment(String comment);

    void commentLikeSuccess(int num_like, int num_unlike);

    void commentLikeFailure(String msg);

    void commentUnlikeSuccess(int num_like, int num_unlike);

    void commentUnlikeFailure(String msg);
}
