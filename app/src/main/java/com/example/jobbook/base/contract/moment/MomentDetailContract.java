package com.example.jobbook.base.contract.moment;

import com.example.jobbook.base.IBasePresenter;
import com.example.jobbook.base.IBaseView;
import com.example.jobbook.model.bean.MomentBean;
import com.example.jobbook.model.bean.MomentCommentBean;

import java.util.List;

/**
 * Created by Xu on 2018/2/16.
 */

public interface MomentDetailContract {

    interface View extends IBaseView {
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

    interface Presenter extends IBasePresenter<View> {
        void loadMoment(MomentBean momentBean);

        void loadMomentById(int id, String account);

        void loadMomentComments(int id, int index);

        void sendComment(MomentCommentBean momentCommentBean);

        void commentLike(int com_id, String account);

        void commentUnlike(int com_id, String account);
    }
}
