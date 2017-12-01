package com.example.jobbook.base.contract.square;

import com.example.jobbook.base.IBasePresenter;
import com.example.jobbook.base.IBaseView;
import com.example.jobbook.model.bean.MomentBean;

import java.util.List;

/**
 * Created by zhaoxuzhang on 2017/12/1.
 */

public interface SquareFollowContract {

    interface View extends IBaseView {
        void addSquareFollows(List<MomentBean> squareFollowList);

        void showLoadFailMsg();

        void like(int position);

        void unlike(int position);

        void NoLoginError();

        void likeSuccess(MomentBean momentBean, int position);

        void unlikeSuccess(MomentBean momentBean, int position);

        void likeError();

        void unlikeError();
    }

    interface Presenter extends IBasePresenter<View> {
        void loadSquareFollows(int pageIndex);

        void like(int squareId, int position);

        void unlike(int squareId, int position);
    }

}
