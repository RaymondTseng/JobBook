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
        void loadMoment(MomentBean momentBean);

        void loadComments(List<MomentCommentBean> mComments);

        void sendSuccess(MomentBean momentBean);

        void likeSuccess(MomentBean momentBean);

        void unlikeSuccess(MomentBean momentBean);
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
